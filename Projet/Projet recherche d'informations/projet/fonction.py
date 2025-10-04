import nltk
import streamlit as st
from nltk.tokenize import RegexpTokenizer
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
from nltk.stem import LancasterStemmer
import math
import numpy as np
import pandas as pd
import re
import matplotlib.pyplot as plt

# preprocesing
def charger_file(chemin_fichier):

    liste_requetes = []  
    id_courant = None  
    texte_courant = []  

    with open(chemin_fichier, 'r') as fichier:
        for ligne in fichier:
            ligne = ligne.strip()  
            if ligne.startswith(".I"):  
                if id_courant is not None:  
                    liste_requetes.append({"id": id_courant, "texte": " ".join(texte_courant)})
                id_courant = int(ligne.split()[1])  
                texte_courant = []  
            elif ligne.startswith(".W"):  
                texte_courant = []  
            else:
                texte_courant.append(ligne)  

        # Ajouter la dernière requête
        if id_courant is not None:
            liste_requetes.append({"id": id_courant, "texte": " ".join(texte_courant)})

    return liste_requetes

def charger_jugements(chemin_fichier):
    resultats_juges = {}

    with open(chemin_fichier, 'r') as fichier:
        for ligne in fichier:
            elements = ligne.strip().split()
            if len(elements) == 4:
                id_requete = int(elements[0])
                id_document = int(elements[2])

                if id_requete not in resultats_juges:
                    resultats_juges[id_requete] = []
                resultats_juges[id_requete].append(id_document)

    return resultats_juges

def extraire_mots(texte, mode):
    if mode == "Split":
        mots = texte.split(' ')
    else:
        tokenizer = nltk.RegexpTokenizer('(?:[A-Za-z]\.)+|[A-Za-z]+[\-@]\d+(?:\.\d+)?|\d+[A-Za-z]+|\d+(?:[\.\,\-]\d+)?%?|\w+(?:[\-/]\w+)*')
        mots = tokenizer.tokenize(texte)

    mots = [mot.lower() for mot in mots]
    return mots

def obtenir_stopwords():
    try:
        if 'A:\\python\\nltk_data' not in nltk.data.path:
            nltk.data.path.append('A:\\python\\nltk_data')
        mots_vides = set(stopwords.words('english'))
    except LookupError:
        print(LookupError)
    
    return mots_vides

def valider_mot(mot, type = "PorterStemmer"):
    mot = re.sub(r'\s+', ' ', mot).strip().upper()
    mots_vides = obtenir_stopwords()

    if mot in mots_vides:
        st.error(f"Le mot '{mot}' est un stopword")

    if type == "PorterStemmer":
        stemmer = PorterStemmer()
    else:
        stemmer = LancasterStemmer()
    
    return stemmer.stem(mot)

def supprimer_stopwords(donnees):
    mots_vides = obtenir_stopwords()
    mots_filtrés = [mot for mot in donnees if mot not in mots_vides]
    return mots_filtrés

def normaliser_donnees(donnees, type):
    if type == "PorterStemmer":
        stemmer = PorterStemmer()
    else:
        stemmer = LancasterStemmer()
    
    donnees_normalisees = [stemmer.stem(mot) for mot in donnees]
    return donnees_normalisees

def position(data, type):

    if type == "PorterStemmer":
        stemmer = PorterStemmer()
    else :
        stemmer = LancasterStemmer()
    
    stop_words = obtenir_stopwords()

    # for file in data:
    words = {}  # Réinitialisation du dictionnaire pour chaque fichier
    for index, word in enumerate(data):  # Parcours des (index, mot)
        if word not in stop_words:
            word_stem = stemmer.stem(word)
            
            if word_stem in words:
                words[word_stem].append(index+1)
            else:
                words[word_stem] = [index+1]
            
    return words

def calcul_poids_termes(data, position_words, nbr_documment):
    # Initialisation des variables
    terms_data = []
    total_docs = len(data)

    # Calcul des fréquences de termes dans chaque document
    doc_term_freq = []
    for doc in data:
        term_freq = {}
        for term in doc:
            term_freq[term] = term_freq.get(term, 0) + 1  # Fréquence des termes
        doc_term_freq.append(term_freq)

    # Parcours des documents pour calculer les poids
    for doc_index, term_freq in enumerate(doc_term_freq):
        max_freq = max(term_freq.values())  # Fréquence maximale du terme dans le document

        for term, freq in term_freq.items():
            # Calcul du nombre de documents contenant le terme
            doc_containing_term = sum(1 for doc in doc_term_freq if term in doc)
            
            # Calcul du poids du terme selon la formule TF-IDF
            weight = (freq / max_freq) * math.log10(nbr_documment / (doc_containing_term + 1))
            
            # Récupérer la position du terme dans le document
            position = position_words[doc_index].get(term, -1)  # -1 si le terme n'est pas trouvé

            # Enregistrement des résultats
            terms_data.append([doc_index + 1, term, freq, weight, position])

    # Conversion en DataFrame
    result_df = pd.DataFrame(terms_data, columns=['N°Doc', 'Terme', 'Freq', 'Poids', 'Position'])
    
    return result_df

def traitement_documment(path):

    data = charger_file(path)

    nbr_doc =  max([id['id'] for id in data])

    data_cleaning = []

    type_div = ['Split', 'RegEx']
    type_norm = ['PorterStemmer', 'LancasterStemmer']
    list_doc = ['Doc_Terme', 'Term_Doc']

    for t1 in type_div:
        for t2 in type_norm:

            data_cleaning = []
            position_words = []

            for file in data:

                words = extraire_mots(file['texte'], t1)

                m = position(words, t2)
                
                position_words.append(m)

                words = supprimer_stopwords(words)

                words = normaliser_donnees(words, t2 )
                

                data_cleaning.append(words)

            
            data_final = calcul_poids_termes(data_cleaning, position_words, nbr_doc)

            f = f'index/{list_doc[0]}_{t1}_{t2}'

            data_final.to_csv(f,  index=False)

            colonnes = list(data_final.columns)  # Liste des colonnes
            colonnes[0], colonnes[1] = colonnes[1], colonnes[0]  # Échange des positions
            
            data_final = data_final.sort_values(by= 'Terme')
            f = f'index/{list_doc[1]}_{t1}_{t2}'

            data_final.to_csv(f,  index=False)
    st.success('fin de mise a jour')
    return(data_final)

def load_table(list_token, list_norm, type_doc):

    f = f'index/{type_doc}_{list_token}_{list_norm}'

    data = pd.read_csv(f)


    return data

# model

def data_query(query, token, stemmer):

    words = extraire_mots(query, token)

    words = supprimer_stopwords(words)

    words = normaliser_donnees(words, stemmer )

    return list(set(words))

def model_vector(data, query, model, token, stemmer,test= 0,  num_query = 1, jugements = []):

    words_query = data_query(query, token, stemmer)
    

    if(model == 'Scalar Product'):

        nbr_doc = len(data['N°Doc'].unique())

        data_model = []

        for nbr in range(1, nbr_doc + 1):
            # Filtrer les données pour le document actuel
            data_doc = data[data['N°Doc'] == nbr]

            # Calcul de la somme des poids des termes de la requête présents dans le document
            vw = data_doc[data_doc['Terme'].isin(words_query)]['Poids'].sum()

            # Calcul de la pertinence uniquement si les dénominateurs ne sont pas nuls
            if vw > 0 : 
                data_model.append([nbr, vw])
        
        sum_data_poids = pd.DataFrame(data_model, columns=['N°Doc', 'Relevance']).sort_values(by = 'Relevance', ascending = False )
        
    elif (model == "Cosine Measure"):

        # Récupérer le nombre total de documents
        nbr_doc = len(data['N°Doc'].unique())
        data_model = []  # Liste pour stocker les résultats [N°Doc, Relevance]

        # Calcul de la norme de la requête (v2)
        v2 = len(words_query) ** 0.5  # Racine carrée de la somme des carrés des fréquences dans la requête

        for nbr in range(1, nbr_doc + 1):
            # Filtrer les données pour le document actuel
            data_doc = data[data['N°Doc'] == nbr]

            # Calcul de la norme du document (w2)
            w2 = (data_doc['Poids'] ** 2).sum() ** 0.5

            # Calcul de la somme des poids des termes de la requête présents dans le document
            vw = data_doc[data_doc['Terme'].isin(words_query)]['Poids'].sum()

            # Calcul de la pertinence uniquement si les dénominateurs ne sont pas nuls
            if w2 > 0 and v2 > 0:
                r = vw / (v2 * w2)  # Calcul de RSV(Q, d)
                data_model.append([nbr, r])

        # Convertir les résultats en DataFrame et trier par pertinence décroissante
        sum_data_poids= pd.DataFrame(data_model, columns=['N°Doc', 'Relevance']).sort_values(by='Relevance', ascending=False)

    else:
        # Récupérer le nombre total de documents
        nbr_doc = len(data['N°Doc'].unique())
        data_model = []  # Liste pour stocker les résultats [N°Doc, Relevance]

        # Calcul de la norme de la requête (v2)
        v2 = len(words_query)  # Racine carrée de la somme des carrés des fréquences dans la requête

        for nbr in range(1, nbr_doc + 1):
            # Filtrer les données pour le document actuel
            data_doc = data[data['N°Doc'] == nbr]

            # Calcul de la norme du document (w2)
            w2 = (data_doc['Poids'] ** 2).sum() 

            # Calcul de la somme des poids des termes de la requête présents dans le document
            vw = data_doc[data_doc['Terme'].isin(words_query)]['Poids'].sum()

            # Calcul de la pertinence uniquement si les dénominateurs ne sont pas nuls
            if w2 > 0 and v2 > 0:
                r = vw / (v2 + w2 - vw)  # Calcul de RSV(Q, d)
                data_model.append([nbr, r])

        # Convertir les résultats en DataFrame et trier par pertinence décroissante
        sum_data_poids= pd.DataFrame(data_model, columns=['N°Doc', 'Relevance']).sort_values(by='Relevance', ascending=False)

    
    
    if test == 1:
        jugement = jugements[num_query]
        doc_query = list(sum_data_poids['N°Doc'])

        # Calcul de la précision globale
        P = len([d for d in doc_query if d in jugement]) / len(doc_query) if len(doc_query) > 0 else 0

        # Initialisation de P5 et P10
        P5, P10 = 0, 0

        # Précision pour les 5 premiers documents
        if len(doc_query) > 0:
            P5 = len([d for d in doc_query[:5] if d in jugement]) / min(5, len(doc_query))

        # Précision pour les 10 premiers documents
        if len(doc_query) > 0:
            P10 = len([d for d in doc_query[:10] if d in jugement]) / min(10, len(doc_query))

        R =  len([d for d in doc_query if d in jugement]) / len(jugement) if len(jugement) > 0 else 0

        F = (2 * P * R)/(P + R)

        Mesures_evaluation = {'P': P, 'P5':P5, 'P10': P10, 'R': R, 'F': F}

        courbe(sum_data_poids, jugement)


        return sum_data_poids, Mesures_evaluation

    else:
        return sum_data_poids

def BM25(data, query, token, stemmer, k=1.2, B=0.75,test= 0,  num_query = 1, jugements = []):
    # Récupérer le nombre total de documents
    N = data['N°Doc'].nunique()
    
    # Extraire les mots-clés de la requête
    words_query = data_query(query, token, stemmer)

    # Calculer la longueur moyenne des documents
    dl = data.groupby('N°Doc')['Freq'].sum().to_numpy()
    
    avgdl = np.mean(dl[:])

    dl =  k * ((1 - B) + B *(dl / avgdl))


    data_model = []  # Stocker les résultats [N°Doc, Relevance]

    # Boucler sur chaque document
    for nbr in range(1, N+1):
        relevance = 0  # Initialiser la pertinence du document
        doc_terms = data[data['N°Doc'] == nbr]  # Filtrer les termes du document

        # Boucler sur chaque terme dans la requête
        for word in words_query:
            # Vérifier si le mot existe dans le document
            term_data = doc_terms[doc_terms['Terme'] == word]

            if not term_data.empty:
                # Récupérer la fréquence du terme dans le document
                freq = term_data['Freq'].iloc[0]

                # Nombre de documents contenant le terme
                n = data[data['Terme'] == word]['N°Doc'].nunique()

                # Calcul de la pertinence pour le mot
                partie1 = freq / (dl[nbr-1] + freq)
                partie2 = np.log10((N - n + 0.5) / (n + 0.5))
                relevance += partie1 * partie2  # Ajouter à la pertinence totale du document

        # Ajouter la pertinence du document à la liste
        data_model.append([nbr, relevance])

    sum_data_poids = pd.DataFrame(data_model, columns=['N°Doc', 'Relevance']).sort_values(by='Relevance', ascending=False)

    if test == 1:
        jugement = jugements[num_query]
        doc_query = list(sum_data_poids['N°Doc'])

        # Calcul de la précision globale
        P = len([d for d in doc_query if d in jugement]) / len(doc_query) if len(doc_query) > 0 else 0

        # Initialisation de P5 et P10
        P5, P10 = 0, 0

        # Précision pour les 5 premiers documents
        if len(doc_query) > 0:
            P5 = len([d for d in doc_query[:5] if d in jugement]) / min(5, len(doc_query))

        # Précision pour les 10 premiers documents
        if len(doc_query) > 0:
            P10 = len([d for d in doc_query[:10] if d in jugement]) / min(10, len(doc_query))

        R =  len([d for d in doc_query if d in jugement]) / len(jugement) if len(jugement) > 0 else 0

        F = (2 * P * R)/(P + R)

        Mesures_evaluation = {'P': P, 'P5':P5, 'P10': P10, 'R': R, 'F': F}

        courbe(sum_data_poids, jugement)

        return sum_data_poids, Mesures_evaluation

    else:
        return sum_data_poids

def evaluer_expression_logique(data, query, stemmer):
    # Supprimer les espaces superflus de l'expression et la mettre en majuscules
    expression = re.sub(r'\s+', ' ', query).strip().upper()

    # Cas invalides avec seulement un opérateur logique
    cas_invalides = {"AND", "OR", "NOT"}
    if expression in cas_invalides:
        st.error("L'expression n'est pas valide")
        return False
    
    # Cas invalides où l'expression commence ou se termine par un opérateur
    if expression.startswith(("AND", "OR")) or expression.endswith(("AND", "OR", "NOT")):
        st.error("L'expression n'est pas valide")
        return False
    
    # Cas invalides avec des combinaisons d'opérateurs logiques invalides
    cas_invalides_comb = { "AND OR", "OR AND", "NOT AND", "NOT OR", "NOT NOT", "OR OR", "AND AND" }
    for e in cas_invalides_comb:
        if e in expression:
            st.error("L'expression n'est pas valide")
            return False
    
    table_exp = expression.split(' ')

    # Vérification de la validité de la syntaxe des opérateurs dans l'expression
    borne_sup = len(table_exp) - 1
    for i in range(0, borne_sup):
        if table_exp[i] not in ("AND", "OR", "NOT", "(NOT") and table_exp[i + 1] not in ("AND", "OR"):
            st.error("L'expression n'est pas valide")
            return False
    
    # Vérification des parenthèses équilibrées
    pile = []
    for char in expression:
        if char == '(':
            pile.append(char)
        elif char == ')':
            if not pile:
                st.error("Les parenthèses ne sont pas équilibrées")
                return False
            pile.pop()
    if len(pile) != 0:
        st.error("Les parenthèses ne sont pas équilibrées")
        return False

    st.success("L'expression est valide")

    # Préparation de l'expression logique
    expression = expression.replace('(', '( ').replace(')', ' )')
    expression = re.sub(r'\s+', ' ', expression).strip().upper()
    table_exp = expression.split(' ')

    result = []
    for terme in table_exp:
        if terme in ("AND", "OR", "NOT", "(", ")"):
            if terme in ("AND", "OR", "NOT"):
                result.append(terme.lower())
            else:
                result.append(terme)
        else: 
            s = valider_mot(terme, stemmer)
            result.append(f"'{s}' in d")

    # Conversion de l'expression en une chaîne exécutable
    exp_valide = ' '.join(result)

    # Récupérer le nombre total de documents
    nbr_doc = len(data['N°Doc'].unique())

    data_model = []

    # Evaluation pour chaque document
    for nbr in range(1, nbr_doc + 1):
        data_doc = data[data['N°Doc'] == nbr]
        d = data_doc['Terme'].tolist()  # Liste des termes pour ce document
        local_vars = {'d': d, 'valide': False}

        # Exécution sécurisée de l'expression logique
        code = f"""
if {exp_valide}:
    valide = True
"""
        exec(code, {}, local_vars)

        # Ajouter le résultat au modèle de données
        data_model.append([nbr, local_vars['valide']])

    # Convertir en DataFrame et trier par pertinence décroissante
    fin = pd.DataFrame(data_model, columns=['N°Doc', 'Relevance']).sort_values(by='Relevance', ascending=False)

    # Afficher les résultats
    st.dataframe(fin)

def courbe(resultat, jugement):
    # Liste des documents retournés
    res = list(resultat['N°Doc'])

    # Liste pour stocker précision réelle à chaque étape
    precisin_reel = []

    # Calcul de la précision réelle à chaque rappel
    for i in range(len(res)):
        sum = 0
        for g in range(i + 1):
            if res[g] in jugement:
                sum += 1
        precisin_reel.append([sum / (i + 1), sum / len(res)])  # précision à l'instant i et global

    # Liste des rappels interpolés (de 0.0 à 1.0 avec pas de 0.1)
    rappel_interpoli = []

    # Remplir la liste des rappels interpolés
    for rappel in [i / 10 for i in range(11)]:
        # Trouver la précision maximale à ce niveau de rappel
        rappel_sup = max([reel[0] for reel in precisin_reel if reel[1] >= rappel], default=0)
        rappel_interpoli.append([rappel, rappel_sup])

    # Séparer les rappels et les précisions pour le tracé
    rappels = [item[0] for item in rappel_interpoli]
    precisions = [item[1] for item in rappel_interpoli]

    # Créer la courbe
    plt.figure(figsize=(8, 6))
    plt.plot(rappels, precisions, marker='o', linestyle='-', color='brown')
    plt.title("Courbe Rappel-Précision")
    plt.xlabel("Rappel (R)")
    plt.ylabel("Précision (P)")
    plt.grid()
    plt.xlim(0, 1)
    plt.ylim(0, 1)
    
    st.title("Courbe Rappel-Précision")
    st.pyplot(plt)
