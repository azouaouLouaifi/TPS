import streamlit as st  # Importe la bibliothèque Streamlit pour créer une interface graphique interactive en Python, souvent utilisée pour des applications web de machine learning et de data science.
import numpy as np      # Importe la bibliothèque NumPy, qui est utilisée pour manipuler des tableaux multidimensionnels (arrays) et effectuer des calculs mathématiques sur des données.
import cv2 as cv        # Importe OpenCV (cv2), une bibliothèque populaire pour la vision par ordinateur et le traitement d'images.
import os               # Importe le module os, utilisé pour interagir avec le système d'exploitation, notamment pour la gestion des fichiers et répertoires.
import pandas as pd     # Importe Pandas, une bibliothèque pour la manipulation et l'analyse de données, notamment des structures de données comme les DataFrames.
import math             # Importe le module math qui fournit des fonctions mathématiques standard comme les calculs trigonométriques, logarithmiques, etc.



# Des Fonction Pour Algorithme de yolov3 et de load data de calibrages inisial:------------------------------------------
def initialize_yolo(yolo_dir):
    
    # Chemins des fichiers YOLO
    weights_path = os.path.join(yolo_dir, "yolov3.weights")  # Poids du modèle
    cfg_path = os.path.join(yolo_dir, "yolov3.cfg")  # Configuration du modèle
    names_path = os.path.join(yolo_dir, "coco.names")  # Liste des classes

    # Vérifie que les fichiers existent
    if not os.path.exists(weights_path) or not os.path.exists(cfg_path) or not os.path.exists(names_path):
        raise FileNotFoundError("Les fichiers YOLO nécessaires sont manquants. Vérifiez vos fichiers.")

    # Charge le modèle YOLO avec OpenCV
    net = cv.dnn.readNetFromDarknet(cfg_path, weights_path)

    # Charge les noms des classes depuis coco.names
    with open(names_path, 'r') as f:
        classes = [line.strip() for line in f.readlines()]

    return net, classes  # Retourne le modèle YOLO et les noms de classes

def load_data_yolo():
    # Répertoire contenant les fichiers YOLO
    current_dir = os.path.dirname(os.path.abspath(__file__))
    yolo_dir = os.path.join(current_dir, "yolo_files")

    try:
        # Initialiser le modèle YOLO
        net, classes = initialize_yolo(yolo_dir)
        return net, classes
    except FileNotFoundError as e:
        print(e)
        st.error('Manque de fichier YOLOv3')

@st.cache_data
def load_data_stereo():
    # Charger le fichier .xml
    cv_file = cv.FileStorage("stereo_params.xml", cv.FILE_STORAGE_READ)

    # Charger les paramètres intrinsèques
    mtx_left = cv_file.getNode("mtx_left").mat()
    dist_left = cv_file.getNode("dist_left").mat()
    mtx_right = cv_file.getNode("mtx_right").mat()
    dist_right = cv_file.getNode("dist_right").mat()

    # Charger les paramètres extrinsèques
    R = cv_file.getNode("R").mat()
    T = cv_file.getNode("T").mat()

    R1 = cv_file.getNode("R1").mat()
    R2 = cv_file.getNode("R2").mat()


    P1 = cv_file.getNode("P1").mat()
    P2 = cv_file.getNode("P2").mat()

    Q = cv_file.getNode("Q").mat()
    
    baseline = np.linalg.norm(T)

    # Extraire les paramètres intrinsèques pour chaque caméra
    fx1, fy1 = mtx_left[0, 0], mtx_left[1, 1]
    cx1, cy1 = mtx_left[0, 2], mtx_left[1, 2]

    fx2, fy2 = mtx_right[0, 0], mtx_right[1, 1]
    cx2, cy2 = mtx_right[0, 2], mtx_right[1, 2]

    # Créer un dictionnaire pour stocker les paramètres
    stereo_params = {
        "intrinsic_left": {
            "fx": fx1,
            "fy": fy1,
            "cx": cx1,
            "cy": cy1,
            "mtx": mtx_left,
            "dist": dist_left
        },
        "intrinsic_right": {
            "fx": fx2,
            "fy": fy2,
            "cx": cx2,
            "cy": cy2,
            "mtx": mtx_right,
            "dist": dist_right
        },
        "extrinsic": {
            "R": R,
            "T": T,

            "P1": P1,
            "P2": P2,

            "R1": R1,
            "R2": R2,
            
            "Q": Q
        },
        "baseline": baseline
    }

    # Libérer les ressources
    cv_file.release()
    return stereo_params

def algorithme_yolov3(net, classes, frame, conf_threshold=0.2, nms_threshold=0.4, type_class=''):
    """
    Détection d'objets avec YOLOv3 en filtrant par type de classe.

    Args:
        net: Modèle YOLOv3 chargé.
        classes: Liste des classes du modèle.
        frame: Image d'entrée.
        conf_threshold: Seuil de confiance pour filtrer les détections.
        nms_threshold: Seuil pour la suppression des redondances (NMS).
        type_class: Classe spécifique à détecter (nom en texte). Si vide, détecte toutes les classes.

    Returns:
        frame: Image annotée avec les détections.
        centers: Liste des centres des objets détectés.
    """
    # Convertir l'image en "blob"
    blob = cv.dnn.blobFromImage(frame, 0.00392, (320, 320), (0, 0, 0), True, crop=False)
    net.setInput(blob)
    outs = net.forward(net.getUnconnectedOutLayersNames())

    # Initialisation des listes
    class_ids = []
    confidences = []
    boxes = []

    # Parcours des sorties
    for out in outs:
        for detection in out:
            scores = detection[5:]
            class_id = np.argmax(scores)
            confidence = scores[class_id]

            # Filtrer par seuil de confiance
            if confidence > conf_threshold:
                center_x = int(detection[0] * frame.shape[1])
                center_y = int(detection[1] * frame.shape[0])
                w = int(detection[2] * frame.shape[1])
                h = int(detection[3] * frame.shape[0])

                # Ajuster la taille des boîtes
                w = int(w * 1.2)
                h = int(h * 1.2)

                x = int(center_x - w / 2)
                y = int(center_y - h / 2)

                # Vérifier si la classe correspond à `type_class` (si spécifié)
                if not type_class or (type_class == classes[class_id]):
                    boxes.append([x, y, w, h])
                    confidences.append(float(confidence))
                    class_ids.append(class_id)

    # Appliquer NMS pour supprimer les doublons
    indices = cv.dnn.NMSBoxes(boxes, confidences, conf_threshold, nms_threshold)

    centers = []
    if len(indices) > 0:
        for i in indices.flatten():
            x, y, w, h = boxes[i]
            center_x = x + w // 2
            center_y = y + h // 2

            # Dessiner la boîte englobante
            label = f"{classes[class_ids[i]]} {round(confidences[i], 1)} "
            color = (0, 255, 0)  # Vert
            cv.rectangle(frame, (x, y), (x + w, y + h), color, 1)
            cv.putText(frame, label, (x, y - 10), cv.FONT_HERSHEY_SIMPLEX, 0.5, color, 1)

            # Dessiner le centre
            cv.circle(frame, (center_x, center_y), 5, (0, 0, 255), -1)
            cv.putText(frame, f"({center_x}, {center_y})", 
                        (center_x + 10, center_y), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 1)

            # Ajouter le centre à la liste
            centers.append((center_x, center_y))

    return frame, centers

# Partie 1:--------------------------------------------------------------------------------------------------------------

# Cette fonction effectue la détection d'objets sur une vidéo provenant d'une seule caméra en utilisant le modèle YOLOv3.
def detection_une_camera():
    
    # Charge les données nécessaires (le modèle et les classes) pour YOLO
    net, classes = load_data_yolo()

    # Crée une copie des classes et ajoute une valeur `None` à la fin pour permettre de sélectionner une classe ou de ne rien choisir.
    classes_type = classes.copy()
    classes_type.append(None)

    # Crée une interface utilisateur avec Streamlit pour configurer les paramètres
    col1,  col2, col3, col4 = st.columns(4)

    # Paramètres de la caméra et de la détection dans les colonnes
    with col1:
        # Permet à l'utilisateur de choisir l'ID de la caméra
        camera_id = col1.number_input(label='Id caméra 1', min_value=0, max_value=5, value=1, step=1)
    with col2:
        # Permet de définir le seuil minimal de probabilité pour la détection
        conf_threshold = col2.number_input(label='Seuil minimal de probabilité', min_value=0.0, max_value=1.0, value=0.2, step=0.05)
    with col3:
        # Permet de définir le seuil de chevauchement (IoU) pour la suppression des doublons d'objets détectés
        nms_threshold = col3.number_input(label='Seuil de chevauchement (IoU)', min_value=0.0, max_value=1.0, value=0.4, step=0.05)
    with col4:
        # Permet de choisir la classe d'objet à détecter parmi les classes disponibles
        type_class = col4.selectbox("Choisissez la classe de l'objet", classes_type)
    
    # Lorsque l'utilisateur appuie sur le bouton "Applique", la détection est lancée
    if st.button('Applique'):
        # Affiche un titre avec l'ID de la caméra sélectionnée
        st.title(f"camera {camera_id}")
        
        # Création de deux espaces réservés (placeholders) pour afficher les coordonnées et la vidéo
        coordon = st.empty()
        video_placeholder = st.empty()
  
        # Ouvre le flux vidéo de la caméra spécifiée
        cap = cv.VideoCapture(camera_id, cv.CAP_DSHOW)

        # Si la caméra ne peut pas être ouverte, affiche une erreur
        if not cap.isOpened():
            col1.error(f"Erreur lors de l'ouverture de la caméra {camera_id}.")
            return

        # Boucle pour lire chaque image (frame) de la vidéo
        while cap.isOpened():
            ret, frame = cap.read()

            # Si une image a été lue correctement
            if ret:
                # Applique l'algorithme YOLOv3 pour détecter les objets dans l'image
                frame, centers = algorithme_yolov3(net=net, classes=classes, frame=frame, type_class=type_class, conf_threshold=conf_threshold, nms_threshold=nms_threshold)
                
                # Si des objets ont été détectés (coordonnées des objets)
                if centers:
                    # Affiche les coordonnées de l'objet détecté sur l'interface
                    coordon.success(f"(U, V) : {centers}")
                
                # Convertit l'image en format RGB pour l'affichage
                frame = cv.cvtColor(frame, cv.COLOR_BGR2RGB)

                # Affiche la vidéo dans le placeholder, avec les images mises à jour à chaque itération
                video_placeholder.image(frame, channels="RGB", use_container_width=True)

# Partie 2:--------------------------------------------------------------------------------------------------------------

#Cette fonction permet à l'utilisateur de définir les paramètres nécessaires pour la calibration d'une caméra.
# Elle affiche un formulaire dans l'interface Streamlit pour collecter ces informations.
def param_une_camera():

    # Affichage du titre de la page
    st.title("Calibration des Caméras")

    # Création de 4 colonnes pour organiser les différents champs de saisie
    col1, col2, col3, col4 = st.columns(4)

    # Paramètre pour définir l'ID de la caméra (dans la première colonne)
    with col1:
        # L'utilisateur peut choisir l'ID de la caméra à calibrer, entre 0 et 10
        id = col1.number_input(label="ID Camera", value=1, min_value=0, max_value=10, step=1)

    # Paramètres pour définir les dimensions du damier pour la calibration (dans la deuxième colonne)
    with col2:
        col2.subheader("Dimensions")
        # L'utilisateur peut définir le nombre de colonnes du damier
        col = col2.number_input(label="Nbr Colonnes", value=7, min_value=1, max_value=40, step=1)
        # L'utilisateur peut définir le nombre de lignes du damier
        ligne = col2.number_input(label="Nbr Lignes", value=9, min_value=1, max_value=40, step=1)
        # L'utilisateur peut définir la taille des cases du damier en millimètres
        square_size = col2.number_input(label="Taille (mm)", value=20, min_value=1, max_value=100, step=1)
        # La variable `chess_dim` contient les dimensions du damier (nombre de colonnes et lignes)
        chess_dim = (col, ligne)

    # Paramètres pour l'algorithme de calibration (dans la troisième colonne)
    with col3:
        col3.subheader("Algorithme")
        # L'utilisateur peut définir le nombre d'itérations pour la calibration
        iterations = col3.number_input(label="Nombre d'itérations", value=30, min_value=1, max_value=100, step=1)
        # L'utilisateur peut définir le seuil de précision pour l'algorithme
        precision = col3.number_input(label="Seuil de précision", value=0.01, min_value=0.0, max_value=1.0, step=0.01)
    
    # Description des paramètres de l'algorithme de calibration
    info = """
                Nombre d'itérations : L'algorithme fera jusqu'à 30 itérations pour affiner les coins ou optimiser les paramètres.\n
                Seuil de précision : L'algorithme s'arrêtera si la différence entre deux itérations consécutives est inférieure à 0.001.
                """
    st.write(info)  # Affiche cette description dans l'interface Streamlit
    
    # Bouton pour appliquer la calibration
    if col4.button("Appliquer"):
        # Si l'utilisateur clique sur le bouton, on appelle la fonction de calibration
        try:
            # Appelle la fonction `calibrage_une_camera` avec les paramètres définis par l'utilisateur
            n = calibrage_une_camera(id, chess_dim, square_size, iterations, precision)
            
            # Si la calibration est réussie (valeur différente de 0), affiche un message de succès
            if n != 0:
                st.success("Calibration effectuée avec succès.")
            return True
        except Exception as e:
            # Si une erreur survient, affiche un message d'erreur
            st.error(f"Erreur lors de la calibration : {e}")

# Préparer les points 3D du damier
def prepare_object_points(dim, size):

    """
    Crée un tableau 3D contenant les coordonnées des points du damier. Ces points sont utilisés pour 
    la calibration en 3D. Les coordonnées sont multiples de la taille réelle des carrés du damier.

    Arguments :
        dim (tuple) : Les dimensions du damier (nombre de colonnes, nombre de lignes).
        size (float) : La taille réelle de chaque carré du damier en millimètres.

    Retourne :
        np.ndarray : Un tableau 3D contenant les coordonnées des points du damier.
    """
    obj_3d = np.zeros((dim[0] * dim[1], 3), np.float32)  # Initialisation des points 3D
    obj_3d[:, :2] = np.mgrid[:dim[0], :dim[1]].T.reshape(-1, 2)  # Création de la grille 2D du damier
    obj_3d *= size  # Mise à l'échelle par la taille réelle des carrés
    return obj_3d

# une fonction pour du calibrege d'une seul camera 
def calibrage_une_camera(camera_id, chess_dim, square_size, iterations, precision):
    """
    Cette fonction calibre une caméra en utilisant un damier pour détecter et enregistrer les images nécessaires 
    pour l'étalonnage de la caméra. Elle capture des images du damier, détecte ses coins, puis calcule les 
    paramètres de la caméra (matrice intrinsèque, coefficients de distorsion, vecteurs de rotation et de 
    translation) à l'aide de ces images.

    Arguments :
        camera_id (int) : L'identifiant de la caméra à utiliser pour la calibration (ex: 0 pour la caméra par défaut).
        chess_dim (tuple) : Les dimensions du damier (nombre de colonnes et de lignes du damier).
        square_size (float) : La taille réelle de chaque carré du damier (en mm).
        iterations (int) : Le nombre maximal d'itérations pour l'algorithme d'optimisation des coins détectés.
        precision (float) : Le seuil de précision pour l'optimisation des coins lors de la calibration.

    Retourne :
        int : Le nombre d'images capturées pour la calibration.
    """  

    # Prépare les points 3D du damier
    obj_3d = prepare_object_points(chess_dim, square_size)
    # Critères d'arrêt pour l'optimisation : nombre d'itérations et seuil de précision
    criteria = (cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER, iterations, precision)
    
    # Ouvre la caméra spécifiée
    cap = cv.VideoCapture(camera_id)

    # Listes pour stocker les points 3D et 2D
    obj_points_3D = []  # Points 3D dans le monde réel
    img_points_2D = []  # Points 2D dans l'image

    # Vérifie si la caméra est correctement ouverte
    if not cap.isOpened():
        st.error(f"Impossible d'accéder à la caméra avec l'ID {camera_id}.")
        return  # Si la caméra ne peut pas être ouverte, la fonction retourne

    st.info("Appuyez sur (s) pour sauvegarder une image ou (q) pour quitter.")  # Instructions pour l'utilisateur
    n = 0  # Compteur pour les images sauvegardées

    # Boucle de capture des images
    while True:
        ret, frame = cap.read()  # Capture d'une image depuis la caméra

        if not ret:
            st.warning("Impossible de lire la vidéo. Vérifiez la caméra.")
            break  # Si la capture échoue, quitte la boucle

        gray = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)  # Conversion de l'image en niveaux de gris
        ret, corners = cv.findChessboardCorners(gray, chess_dim)  # Recherche des coins du damier

        if ret:
            # Si les coins sont détectés, on les affine pour améliorer la précision
            corners_refined = cv.cornerSubPix(gray, corners, (11, 11), (-1, -1), criteria)
            frame = cv.drawChessboardCorners(frame, chess_dim, corners_refined, ret)
            cv.putText(frame, f"Damier détecté. Appuyez sur (s) pour sauvegarder l'image {n}", (20, 20),
                       cv.FONT_HERSHEY_SIMPLEX, 0.6, (0, 0, 255), 2)  # Affiche un message à l'écran
        else:
            # Si aucun damier n'est détecté
            cv.putText(frame, "Damier non detecte", (20, 20), cv.FONT_HERSHEY_SIMPLEX, 0.6, (0, 0, 255), 2)

        # Affiche l'image avec les coins détectés
        cv.imshow("Camera", frame)

        # Capture des touches clavier pour sauvegarder ou quitter
        key = cv.waitKey(1) & 0xFF
        if key == ord('q'):  # Quitte la boucle si 'q' est pressé
            break
        if key == ord('s') and ret:  # Sauvegarde l'image si 's' est pressé
            obj_points_3D.append(obj_3d)  # Ajoute les points 3D
            img_points_2D.append(corners_refined)  # Ajoute les points 2D
            n += 1  # Incrémente le compteur d'images sauvegardées

    # Libère la caméra et ferme toutes les fenêtres OpenCV
    cap.release()
    cv.destroyAllWindows()

    st.success(f"Capture terminée. {n} images capturées pour la calibration.")  # Affiche un message de succès

    if n > 0:
        # Si des images ont été capturées, effectue la calibration
        ret, mtx, dist, rvecs, tvecs = cv.calibrateCamera(
            obj_points_3D, img_points_2D, gray.shape[::-1], None, None
        )  # Calibrage de la caméra en utilisant les points 3D et 2D

        # Affiche la matrice intrinsèque de la caméra
        st.title('Matrice intrinsèque de la caméra (mtx)')
        col1, col2 = st.columns(2)
        with col1:
            col1.table(mtx)  # Affiche la matrice
        with col2:
            st.write("""
                fx, 0, cx.
                0, fy, cy.
                0, 0,  1.
                """)  # Description de la matrice intrinsèque

        # Affiche les coefficients de distorsion
        st.title('Coefficients de distorsion (dist)')
        col1, col2 = st.columns(2)
        with col1:
            st.table(pd.DataFrame(dist, columns=['k1', 'k2', 'p1', 'p2', 'k3']))  # Affiche les coefficients
        with col2:
            st.write("""
                k1, k2  : Distorsion radiale.
                p1, p2  : Distorsion tangentielle.
                k3      : Correction de distorsion radiale de haut degré.
                """)

        # Affiche les vecteurs de rotation
        st.title('Vecteurs de rotation (rvecs)')
        arrays = [arr.flatten() for arr in rvecs]
        st.table(pd.DataFrame(arrays, columns=['rx', 'ry', 'rz']))

        # Affiche les vecteurs de translation
        st.title('Vecteurs de translation (tvecs)')
        arrays = [arr.flatten() for arr in tvecs]
        st.table(pd.DataFrame(arrays, columns=['tx', 'ty', 'tz']))

    return n  # Retourne le nombre d'images capturées


# Partie 3:--------------------------------------------------------------------------------------------------------------

# Une fonction pour la detection de deux camera
def detection_deux_camera():
    """
    Fonction qui effectue la détection d'objets en temps réel à l'aide de deux caméras stéréo.
    Utilise YOLOv3 pour détecter des objets spécifiques et calcule la distance réelle entre eux,
    en affichant les résultats dans l'interface Streamlit.
    """

    # Chargement des données nécessaires à l'utilisation de YOLO et des informations stéréoscopiques
    net, classes = load_data_yolo()  # Charge le modèle YOLO et ses classes d'objets
    data = load_data_stereo()  # Charge les données nécessaires à la stéréovision (calibration, etc.)

    classes_type = classes.copy()  # Copie des classes d'objets et ajout de "None" pour laisser l'utilisateur choisir
    classes_type.append(None)

    # Création de 5 colonnes pour organiser l'interface Streamlit
    col1, col2, col3, col4, col5 = st.columns(5)

    # Section pour saisir l'ID des caméras et les paramètres de détection
    with col1:
        camera_id1 = col1.number_input(label='Id caméra Left', min_value=0, max_value=5, value=1, step=1)
    with col2:
        camera_id2 = col2.number_input(label='Id caméra Right', min_value=0, max_value=5, value=2, step=1)
    with col3:
        conf_threshold = col3.number_input(label='Seuil minimal de probabilité', min_value=0.0, max_value=1.0, value=0.2, step=0.05)
    with col4:
        nms_threshold = col4.number_input(label='Seuil de chevauchement (IoU)', min_value=0.0, max_value=1.0, value=0.4, step=0.05)
    with col5:
        type_class = col5.selectbox("Choisissez la classe de l'objet", classes_type)  # L'utilisateur choisit la classe d'objet à détecter

    # Vérifie si les deux caméras ont des IDs différents pour éviter de traiter la même caméra deux fois
    if camera_id1 != camera_id2:   
        if st.button('Applique'):  # Si l'utilisateur clique sur le bouton "Applique", la détection commence

            # Création de deux colonnes pour afficher les flux vidéo côte à côte
            col1, col2 = st.columns(2)
            col1.title(f"Camera Left {camera_id1}")
            col2.title(f"Camera Right {camera_id2}")

            # Crée des placeholders pour afficher les vidéos des deux caméras
            video_placeholder1 = col1.empty()
            video_placeholder2 = col2.empty()

            centre_left_affiche = col1.empty()
            centre_right_affiche = col2.empty()

            distance_left_affiche = col1.empty()
            distance_right_affiche = col2.empty()

            # Placeholder pour afficher la distance calculée
            distance = st.empty()

            # Initialisation des deux caméras
            cap1 = cv.VideoCapture(camera_id1, cv.CAP_DSHOW)
            cap2 = cv.VideoCapture(camera_id2, cv.CAP_DSHOW)

            # Vérification de l'ouverture des deux caméras
            if not cap1.isOpened():
                col1.error(f"Erreur lors de l'ouverture de la caméra {camera_id1}.")
                return

            if not cap2.isOpened():
                col2.error(f"Erreur lors de l'ouverture de la caméra {camera_id2}.")
                return
            
            while (cap1.isOpened() and cap2.isOpened()):  # Boucle pour lire les flux vidéo des deux caméras

                ret1, frame1 = cap1.read()  # Lecture d'une image de la caméra gauche
                ret2, frame2 = cap2.read()  # Lecture d'une image de la caméra droite

                # Si les deux images sont lues correctement
                if ret1 and ret2:
                    # Application de l'algorithme YOLOv3 sur chaque image pour détecter les objets
                    frame1, centers1 = algorithme_yolov3(net=net, classes=classes, frame=frame1, type_class=type_class, conf_threshold=conf_threshold, nms_threshold=nms_threshold)
                    frame2, centers2 = algorithme_yolov3(net=net, classes=classes, frame=frame2, type_class=type_class, conf_threshold=conf_threshold, nms_threshold=nms_threshold)
                    
                    # Conversion des images BGR en RGB pour les afficher sur Streamlit
                    frame1 = cv.cvtColor(frame1, cv.COLOR_BGR2RGB)
                    frame2 = cv.cvtColor(frame2, cv.COLOR_BGR2RGB)

                    # Si des objets sont détectés dans les deux images
                    if len(centers1) != 0 and len(centers2) != 0:
                        # Calcul de la distance réelle entre les objets détectés
                        distance_left, distance_right = calculate_real_distance(centers1[0], centers2[0], data)

                        # Affichage de la distance sur chaque image
                        cv.putText(frame1, f"distance {distance_left:.2f}", (20, 20), cv.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
                        cv.putText(frame2, f"distance {distance_right:.2f}", (20, 20), cv.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)

                        # Mise à jour des titres des colonnes avec la distance
                        centre_left_affiche.write(f"centre left {centers1[0]}")
                        centre_right_affiche.write(f"centre right {centers2[0]}")

                        # Mise à jour des titres des colonnes avec la distance
                        distance_left_affiche.title(f"distance {distance_left:.2f}")
                        distance_right_affiche.title(f"distance {distance_right:.2f}")


                    # Paramètres pour la détection de la "mi-distance" (probablement une forme de calibration stéréoscopique)
                    chess_dim = (7, 9)  # Dimensions du damier utilisé pour la calibration
                    criteria = (cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER, 30, 0.01)

                    # Calcul de la "mi-distance" entre les deux caméras
                    frame1, frame2, Z = mi_distance(frame1, frame2, chess_dim, criteria, data)
                    
                    # Si la mi-distance est calculée, afficher la valeur
                    if Z is not None:
                        cv.putText(frame1, f"mi-distance {Z:.2f}", (20, 70), cv.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
                        cv.putText(frame2, f"mi-distance {Z:.2f}", (20, 70), cv.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
                        distance.title(f"mi-distance {Z:.2f}")

                    # Affichage des images des deux caméras dans les placeholders
                    video_placeholder1.image(frame1, channels="RGB", use_container_width=True)
                    video_placeholder2.image(frame2, channels="RGB", use_container_width=True)
                
                # Si une des caméras ne retourne pas d'image, afficher uniquement l'image de l'autre caméra
                elif ret1:
                    frame1 = cv.cvtColor(frame1, cv.COLOR_BGR2RGB)
                    video_placeholder1.image(frame1, channels="RGB", use_container_width=True)
                elif ret2:
                    frame2 = cv.cvtColor(frame2, cv.COLOR_BGR2RGB)
                    video_placeholder2.image(frame2, channels="RGB", use_container_width=True)

    else:
        # Si les deux caméras ont le même ID, afficher un message d'erreur
        st.error('Veuillez choisir des IDs de caméra différents.')

#Cette fonction permet à l'utilisateur de définir les paramètres nécessaires pour la calibration des deux caméras.
# Elle affiche un formulaire dans l'interface Streamlit pour collecter ces informations.
def param_deux_camera():
    # Titre général
    st.title("Calibration des Caméras")

    # Disposition en colonnes
    col1, col2, col3, col4 = st.columns(4)

    # ID des caméras
    with col1:
        st.subheader("Caméras")
        id1 = st.number_input(label="ID Caméra Left", value=1, min_value=0, max_value=10, step=1)
        id2 = st.number_input(label="ID Caméra Right", value=2, min_value=0, max_value=10, step=1)

    # Dimensions de l'échiquier
    with col2:
        st.subheader("Dimensions")
        cols = st.number_input(label="Nombre de Colonnes", value=7, min_value=1, max_value=40, step=1)
        rows = st.number_input(label="Nombre de Lignes", value=9, min_value=1, max_value=40, step=1)
        square_size = st.number_input(label="Taille des cases (mm)", value=20, min_value=1, max_value=100, step=1)
        chess_dim = (cols, rows)

    # Paramètres de l'algorithme
    with col3:
        st.subheader("Algorithme")
        iterations = st.number_input(label="Nombre d'itérations", value=30, min_value=1, max_value=100, step=1)
        precision = st.number_input(label="Seuil de précision", value=0.01, min_value=0.0, max_value=1.0, step=0.01)

    # Afficher des informations complémentaires
    info = """
    - **Nombre d'itérations** : L'algorithme effectuera jusqu'à 30 itérations pour affiner les coins ou optimiser les paramètres.
    - **Seuil de précision** : L'algorithme s'arrêtera si la différence entre deux itérations consécutives est inférieure à 0.001.
    """
    st.write(info)

    # Vérifier que les IDs des caméras sont différents
    if id1 != id2:
        # Bouton pour appliquer la calibration
        if col4.button("Appliquer"):
            try:
                # Appeler la fonction de calibration
                calibrage_deux_camera(id1, id2, chess_dim, square_size,  iterations, precision)
                st.success("Calibration effectuée avec succès.")
                return True
            except Exception as e:
                st.error(f"Erreur lors de la calibration : {e}")
    else:
        st.error("Vous avez choisi les mêmes caméras, veuillez modifier l'un des IDs.")

# Une fonction pour du calibrege de deux cameras
def calibrage_deux_camera(camera_id1, camera_id2, chess_dim, square_size, iterations, precision):
    """
    Fonction qui effectue le calibrage stéréo de deux caméras à l'aide d'un damier.
    Elle capture des images de chaque caméra, détecte les coins du damier, 
    et calcule les paramètres intrinsèques et extrinsèques des caméras pour 
    effectuer une calibration stéréo.

    Arguments:
    camera_id1 : int : ID de la première caméra (caméra gauche).
    camera_id2 : int : ID de la deuxième caméra (caméra droite).
    chess_dim : tuple : Dimensions du damier utilisé pour la calibration (nombre de coins internes).
    square_size : float : Taille d'un carré du damier en millimètres.
    iterations : int : Nombre d'itérations pour l'algorithme de raffinage des coins.
    precision : float : Précision pour l'algorithme de raffinage des coins.
    """

    # Initialisation des points 3D et critères pour la recherche de coins
    obj_3d = prepare_object_points(chess_dim, square_size)  # Prépare les points 3D du damier
    criteria = (cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER, iterations, precision)  # Critères de raffinement des coins

    # Initialisation des caméras
    cap_left = cv.VideoCapture(camera_id1)  # Caméra gauche
    cap_right = cv.VideoCapture(camera_id2)  # Caméra droite

    # Listes pour stocker les points 3D et 2D des deux caméras
    obj_points_3D = []  # Points 3D réels du monde
    img_points_left_2D = []  # Points 2D de la caméra gauche
    img_points_right_2D = []  # Points 2D de la caméra droite

    # Vérification de l'ouverture des caméras
    if not cap_left.isOpened():
        st.error(f"Impossible d'accéder à la caméra avec l'ID {camera_id1}.")
        return

    if not cap_right.isOpened():
        st.error(f"Impossible d'accéder à la caméra avec l'ID {camera_id2}.")
        return

    # Instructions à l'utilisateur pour la sauvegarde des images
    st.info("Appuyez sur (s) pour sauvegarder une image ou (q) pour quitter.")
    n = 0  # Compteur d'images sauvegardées

    # Boucle pour capturer des images et détecter le damier
    while True:
        ret_left, frame_left = cap_left.read()  # Capture de l'image de la caméra gauche
        ret_right, frame_right = cap_right.read()  # Capture de l'image de la caméra droite

        # Si une caméra ne renvoie pas d'image, on arrête
        if not ret_left:
            st.warning(f"Impossible de lire la vidéo. Vérifiez la caméra {camera_id1}.")
            break
        if not ret_right:
            st.warning(f"Impossible de lire la vidéo. Vérifiez la caméra {camera_id2}.")
            break

        # Conversion en niveaux de gris pour la détection des coins
        gray_left = cv.cvtColor(frame_left, cv.COLOR_BGR2GRAY)
        gray_right = cv.cvtColor(frame_right, cv.COLOR_BGR2GRAY)

        # Recherche des coins du damier sur chaque caméra
        ret_left, corners_left = cv.findChessboardCorners(gray_left, chess_dim)  # Recherche sur la caméra gauche
        ret_right, corners_right = cv.findChessboardCorners(gray_right, chess_dim)  # Recherche sur la caméra droite

        # Si les coins sont trouvés sur la caméra gauche
        if ret_left:
            # Raffinement des coins détectés
            corners_refined_left = cv.cornerSubPix(gray_left, corners_left, (11, 11), (-1, -1), criteria)
            frame_left = cv.drawChessboardCorners(frame_left, chess_dim, corners_refined_left, ret_left)
            cv.putText(frame_left, f"Damier est detecte taper (s) pour save l'image {n}", (20, 20), cv.FONT_HERSHEY_SIMPLEX, 0.6, (0, 0, 255), 2)
        else:
            cv.putText(frame_left, "Damier non detecte", (20, 20), cv.FONT_HERSHEY_SIMPLEX, 0.6, (0, 0, 255), 2)

        # Si les coins sont trouvés sur la caméra droite
        if ret_right:
            # Raffinement des coins détectés
            corners_refined_right = cv.cornerSubPix(gray_right, corners_right, (11, 11), (-1, -1), criteria)
            frame_right = cv.drawChessboardCorners(frame_right, chess_dim, corners_refined_right, ret_right)
            cv.putText(frame_right, f"Damier est detecte taper (s) pour save l'image {n}", (20, 20), cv.FONT_HERSHEY_SIMPLEX, 0.6, (0, 0, 255), 2)
        else:
            cv.putText(frame_right, "Damier non detecte", (20, 20), cv.FONT_HERSHEY_SIMPLEX, 0.6, (0, 0, 255), 2)

        # Affichage des images avec les coins du damier détectés
        cv.imshow("Camera left", frame_left)
        cv.imshow("Camera right", frame_right)
        
        # Capture des touches clavier pour sauvegarder ou quitter
        key = cv.waitKey(1) & 0xFF
        if key == ord('q'):
            break  # Quitter
        if key == ord('s') and ret_left and ret_right:
            # Sauvegarder les points 3D et 2D pour chaque caméra
            obj_points_3D.append(obj_3d)
            img_points_left_2D.append(corners_refined_left)
            img_points_right_2D.append(corners_refined_right)
            n += 1  # Incrémenter le compteur d'images sauvegardées

    # Libérer les caméras et fermer les fenêtres d'affichage
    cap_left.release()
    cap_right.release()
    cv.destroyAllWindows()

    # Si aucune image n'a été capturée, afficher une erreur
    if len(img_points_left_2D) == 0 or len(obj_points_3D) == 0 or len(img_points_right_2D) == 0:
        st.error("Erreur dans l'enregistrement des images.")
        return

    # Effectuer le calibrage des caméras
    ret_left, mtx_left, dist_left, _, _ = cv.calibrateCamera(obj_points_3D, img_points_left_2D, gray_left.shape[::-1], None, None)
    ret_right, mtx_right, dist_right, _, _ = cv.calibrateCamera(obj_points_3D, img_points_right_2D, gray_right.shape[::-1], None, None)

    # Calibrage stéréo pour obtenir les paramètres extrinsèques (rotation, translation)
    flags = cv.CALIB_FIX_INTRINSIC
    ret_stereo, _, _, _, _, R, T, E, F = cv.stereoCalibrate(
        obj_points_3D, img_points_left_2D, img_points_right_2D,
        mtx_left, dist_left, mtx_right, dist_right,
        gray_left.shape[::-1], criteria=criteria, flags=flags
    )

    # Rectification des images pour la stéréovision
    rectify_scale = 1
    R1, R2, P1, P2, Q, roi_left, roi_right = cv.stereoRectify(
        mtx_left, dist_left, mtx_right, dist_right,
        gray_left.shape[::-1], R, T, alpha=rectify_scale
    )

    # Calcul du baseline (distance entre les deux caméras)
    baseline = np.linalg.norm(T)

    # Sauvegarde des paramètres de calibration dans un fichier XML
    cv_file = cv.FileStorage("stereo_params.xml", cv.FILE_STORAGE_WRITE)
    cv_file.write("mtx_left", mtx_left)
    cv_file.write("dist_left", dist_left)
    cv_file.write("mtx_right", mtx_right)
    cv_file.write("dist_right", dist_right)
    cv_file.write("baseline", baseline)
    cv_file.write("R", R)
    cv_file.write("T", T)
    cv_file.write("R1", R1)
    cv_file.write("P1", P1)
    cv_file.write("R2", R2)
    cv_file.write("P2", P2)
    cv_file.write("Q", Q)

    # Affichage des matrices et informations de calibration sur Streamlit
    st.title('La matrice intrinsèque de la caméra (mtx)')
    col1, col2, col3 = st.columns(3)
    with col1:
        col1.write(f'Camera left {camera_id1}')
        col1.table(mtx_left)
    with col2:
        col2.write(f'Camera right {camera_id2}')
        col2.table(mtx_right)


    with col3:
            info = """
            fx, 0, cx.\n
            0, fy, cy.\n
            0, 0,  1.\n

            fx, fy	Distances focales (en pixels) sur les axes x et y.\n
            cx, cy	Coordonnées du point principal (centre optique).\n
            [0, 0, 1]	Indique que la matrice est homogène.	
            """
            col3.write(info)


    st.title(' Coefficients de distorsion (dist)')

    col1, col2 = st.columns(2)
    with col1:
        col1.write(f'Camera left {camera_id1}')
        col1.table(pd.DataFrame(dist_left, columns=['k1', 'k2', 'p1', 'p2', 'k3']))
    
    with col2:
        col2.write(f'Camera right {camera_id2}')
        col2.table(pd.DataFrame(dist_right, columns=['k1', 'k2', 'p1', 'p2', 'k3']))

    
    info = """
        k1, k2	Distorsion radiale.\n
        p1, p2	Distorsion tangentielle.\n
        k3	Correction de distorsion radiale de haut degré.
        """
    st.write(info)

    st.title('Matrice de rotation (rvecs)')
    arrays = [arr.flatten() for arr in R]
    st.table(pd.DataFrame(arrays))

    st.title('Vecteurs de translation (tvecs)')
    arrays = [arr.flatten() for arr in T]

    st.table(pd.DataFrame(arrays))

    st.title(f'Baseline (distance entre les deux caméras) {baseline: .2f} mm')

    st.success("Stereo calibration complete.")

# Une fonction qui calcule les coordonnées (Xc, Yc, Zc) des deux camera left et right
def calculate_camera_coordinates(pos_left, pos_right,data):

    # Paramètres intrinsèques pour les caméras gauche et droite
    intrinsic_left = data["intrinsic_left"]
    intrinsic_right = data["intrinsic_right"]

    # Coordonnées optiques et focales pour les caméras
    ox_left, oy_left, fx_left, fy_left = intrinsic_left['cx'], intrinsic_left['cy'], intrinsic_left['fx'], intrinsic_left['fy']
    ox_right, oy_right, fx_right, fy_right = intrinsic_right['cx'], intrinsic_right['cy'], intrinsic_right['fx'], intrinsic_right['fy']

    # Baseline (distance entre les caméras)
    baseline = data['baseline']
    
    # Calcul de la disparité (d = u_L - u_R)
    d = abs(pos_left[0] - pos_right[0])

    # Vérification si la disparité est nulle pour éviter une division par zéro
    if d == 0:
        return None, None  # Impossible de calculer la profondeur

    # Calcul de la profondeur Z dans le repère de la caméra pour les deux caméras
    Zc_left = (baseline * fx_left) / d
    Zc_right = (baseline * fx_right) / d

    # Calcul des coordonnées Xc et Yc dans le repère caméra pour chaque caméra
    Xc_left = Zc_left * (pos_left[0] - ox_left) / fx_left
    Yc_left = Zc_left * (pos_left[1] - oy_left) / fy_left

    Xc_right = Zc_right * (pos_right[0] - ox_right) / fx_right
    Yc_right = Zc_right * (pos_right[1] - oy_right) / fy_right

    return (Xc_left, Yc_left, Zc_left), (Xc_right, Yc_right, Zc_right)

# Une fonction qui calcule la distance entre la camera left ou right et l'objet detectee
def calculate_real_distance(pos_left, pos_right, data):
    """
    Calcule la distance réelle entre deux positions en utilisant les paramètres des caméras.

    :param pos_left: Position 2D du point dans l'image de la caméra 1 (u, v)
    :param pos_right: Position 2D du point dans l'image de la caméra 2 (u, v)
    :param data: Dictionnaire contenant les paramètres intrinsèques et extrinsèques des caméras
    :param name: Nom de la caméra (ex. 'Camera Left' ou 'Camera Right')
    :return: Distance réelle entre les deux points (ou None si la disparité est nulle)
    """
    # Matrice de rotation et vecteur de translation (extrinsèques)
    T = np.array(data['extrinsic']['T'])  # Vecteur de translation (3,)

    R1 = np.array(data['extrinsic']['R1'])  # Matrice de projection caméra gauche
    R2 = np.array(data['extrinsic']['R2']) 

    # Calcul des coordonnées Xc et Yc dans le repère caméra pour chaque caméra
    camera_left, camera_right = calculate_camera_coordinates(pos_left, pos_right, data)

    # Coordonnées caméra (Xc, Yc, Zc) en vecteur homogène
    point_cam_left = np.array([camera_left[0], camera_left[1], camera_left[2], 1])
    point_cam_right = np.array([camera_right[0], camera_right[1], camera_right[2], 1])

    # Transformation dans le repère monde (application de la rotation et de la translation)
    T_homogeneous_left = np.eye(4)  # Matrice homogène 4x4
    T_homogeneous_left[:3, :3] = R1  # Rotation
    T_homogeneous_left[:3, 3] = T.flatten()  # Translation

    T_homogeneous_right = np.eye(4)  # Matrice homogène 4x4
    T_homogeneous_right[:3, :3] = R2  # Rotation
    T_homogeneous_right[:3, 3] = T.flatten()  # Translation

    point_world_left = np.dot(T_homogeneous_left, point_cam_left)
    point_world_right = np.dot(T_homogeneous_right, point_cam_right)

    # Coordonnées dans le repère monde
    Xw_left, Yw_left, Zw_left = point_world_left[:3]
    Xw_right, Yw_right, Zw_right = point_world_right[:3]

    # Calcul de la distance entre le point dans le repère caméra et celui dans le repère monde
    distance_left = math.sqrt((Xw_left - camera_left[0])**2 + (Yw_left - camera_left[1])**2 + (Zw_left - camera_left[2])**2)
    distance_right = math.sqrt((Xw_right - camera_right[0])**2 + (Yw_right - camera_right[1])**2 + (Zw_right - camera_right[2])**2)

    return distance_left, distance_right

# Une fonction qui calcule la mi-distance entre le point centre la camera left et right et l'objet detectee
def mi_distance(frame_left, frame_right, chess_dim, criteria, data):
    """
    Calcule la distance réelle entre les centres du checkerboard détecté dans les images gauche et droite.

    :param frame_left: Image de la caméra gauche
    :param frame_right: Image de la caméra droite
    :param chess_dim: Dimensions du checkerboard (nombre de coins dans le checkerboard)
    :param criteria: Critères pour le raffinement des coins détectés
    :param data: Dictionnaire contenant les paramètres extrinsèques et intrinsèques des caméras
    :return: Les images avec les centres du checkerboard dessinés et la distance entre ces centres dans le repère monde
    """
    #print('cc')
    # Conversion en niveaux de gris
    gray_left = cv.cvtColor(frame_left, cv.COLOR_BGR2GRAY)
    gray_right = cv.cvtColor(frame_right, cv.COLOR_BGR2GRAY)

    # Détection des coins du checkerboard
    ret_left, corners_left = cv.findChessboardCorners(gray_left, chess_dim)
    ret_right, corners_right = cv.findChessboardCorners(gray_right, chess_dim)

    if not ret_left or not ret_right:
        #print("Les coins du checkerboard n'ont pas été détectés correctement dans une ou les deux images.")
        return frame_left, frame_right, None

    # Raffinement des coins détectés
    corners_refined_left = cv.cornerSubPix(gray_left, corners_left, (11, 11), (-1, -1), criteria)
    corners_refined_right = cv.cornerSubPix(gray_right, corners_right, (11, 11), (-1, -1), criteria)

    # Dessiner les coins et calculer le centre du checkerboard
    frame_left = cv.drawChessboardCorners(frame_left, chess_dim, corners_refined_left, ret_left)
    frame_right = cv.drawChessboardCorners(frame_right, chess_dim, corners_refined_right, ret_right)

    center_left = np.mean(corners_refined_left, axis=0)[0]
    center_right = np.mean(corners_refined_right, axis=0)[0]

    cx_left, cy_left = int(center_left[0]), int(center_left[1])
    cx_right, cy_right = int(center_right[0]), int(center_right[1])

    # Dessiner les centres sur les images
    cv.circle(frame_left, (cx_left, cy_left), 5, (0, 255, 0), -1)
    cv.putText(frame_left, f"Center: ({cx_left}, {cy_left})", (cx_left + 10, cy_left - 10),
               cv.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 1)

    cv.circle(frame_right, (cx_right, cy_right), 5, (0, 255, 0), -1)
    cv.putText(frame_right, f"Center: ({cx_right}, {cy_right})", (cx_right + 10, cy_right - 10),
               cv.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 1)
    
    #data = calibrage_deux_images(frame_left, frame_right, chess_dim, square_size= 20, iterations=30, precision=0.01)

    # Calcul des coordonnées dans le repère caméra
    camera_left, camera_right = calculate_camera_coordinates((cx_left, cy_left), (cx_right, cy_right), data)
    center_baseline = (
        camera_left[0],
        (camera_left[1] + camera_right[1]) / 2,
        camera_left[2]
    )

    point_cam = np.array([center_baseline[0], center_baseline[1], center_baseline[2], 1])

    # Transformation dans le repère monde
    T_homogeneous = np.eye(4)
    T_homogeneous[:3, :3] = np.array(data['extrinsic']['R'])
    T_homogeneous[:3, 3] = np.array(data['extrinsic']['T']).flatten()

    point_world = np.dot(T_homogeneous, point_cam)
    Xw, Yw, Zw = point_world[:3]

    # Calcul de la distance
    distance = math.sqrt((Xw - center_baseline[0])**2 +
                         (Yw - center_baseline[1])**2 +
                         (Zw - center_baseline[2])**2)

    return frame_left, frame_right, distance