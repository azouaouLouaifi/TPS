import streamlit as st  # Importe la bibliothèque Streamlit pour créer une interface graphique interactive en Python.
import fonction_gui      # Importe un module personnalisé, probablement contenant des fonctions liées à l'interface graphique et aux traitements.
#import visage            # Importe un autre module personnalisé pour des opérations liées à la détection ou au traitement d'images (probablement du traitement de visage).

# Fonction principale qui gère l'interface utilisateur de l'application Streamlit.
def main():
    # Titre de la barre latérale (sidebar) de l'interface utilisateur
    st.sidebar.title("Projet Vision")

    # Un autre titre pour la section des options dans la barre latérale
    st.sidebar.title("Options")

    # Liste des types d'options disponibles dans la barre latérale pour l'utilisateur
    type_div = ["Aucune",'Detection une camera', 'Calibrage une camera', 'Detection deux cameras', 'Calibrage deux cameras', "Partie 4"]
    
    # Crée un menu radio dans la barre latérale pour que l'utilisateur choisisse parmi les options de type_div
    option = st.sidebar.radio('Options', type_div)

    # Selon l'option choisie, une fonction spécifique est appelée depuis le module fonction_gui ou visage
    if option ==  'Detection une camera':
        # Lance la détection avec une caméra via la fonction correspondante du module fonction_gui
        fonction_gui.detection_une_camera()

    elif option ==  'Detection deux cameras':
        # Lance la détection avec deux caméras via la fonction correspondante du module fonction_gui
        fonction_gui.detection_deux_camera()
    
    elif option == 'Calibrage une camera':
        # Lance le calibrage d'une caméra via la fonction correspondante du module fonction_gui
        fonction_gui.param_une_camera()

    elif option == 'Calibrage deux cameras':
        # Lance le calibrage de deux caméras via la fonction correspondante du module fonction_gui
        fonction_gui.param_deux_camera()

    # elif option == "Partie 4":
    #     # Lance une fonction spécifique du module visage pour la partie 4 du projet
    #     visage.parti4()

# Condition pour vérifier si ce script est exécuté directement (et non importé comme module).
if __name__ == "__main__":
    # Appelle la fonction principale lorsque le script est exécuté directement
    main()
