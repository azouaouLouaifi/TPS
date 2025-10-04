import streamlit as st
import fonction as ri
import re

def afficher_mesures_evaluation(mesures_evaluation):
    st.subheader("Mesures d'Évaluation")
    st.table({"Mesure": list(mesures_evaluation.keys()), "Valeur": list(mesures_evaluation.values())})

    st.write("### Détails des Mesures :")
    st.write(f"- **Précision globale (P)** : {mesures_evaluation['P']:.4f}")
    st.write(f"- **Précision aux 5 premiers (P5)** : {mesures_evaluation['P5']:.4f}")
    st.write(f"- **Précision aux 10 premiers (P10)** : {mesures_evaluation['P10']:.4f}")
    st.write(f"- **Rappel (R)** : {mesures_evaluation['R']:.4f}")
    st.write(f"- **Score F-mesure (F)** : {mesures_evaluation['F']:.4f}")

def afficher_document(id, data, docs):
    data_search = data.loc[data["N°Doc"] == int(id)]
    st.dataframe(data_search)
    
    size_doc = data_search['Freq'].sum()
    size_voc = data_search['Terme'].count()
    st.subheader(f"Taille du Document : {size_doc}")
    st.subheader(f"Taille du Vocabulaire : {size_voc}")
    st.write(docs[id-1]['texte'])

def charger_donnees():
    chemin_queries = r"Collection\queries.txt"
    chemin_docs = r"Collection\docs.txt"
    chemin_jugements = r"Collection\jugements.txt"

    queries = ri.charger_file(chemin_queries)
    docs = ri.charger_file(chemin_docs)
    jugements = ri.charger_jugements(chemin_jugements)

    return queries, docs, jugements

def main():
    st.sidebar.title('Azouaou louaifi')

    if st.sidebar.button(label='Mise à jour'):
        ri.traitement_documment(r"Collection\docs.txt")

    queries, docs, jugements = charger_donnees()
    nbr_max_queries = queries[-1]['id']

    st.title('Application RI')

    tokenize = ['RegEx', 'Split']
    normalisation = ['PorterStemmer', 'LancasterStemmer']
    type_doc = ['Doc_Terme', 'Term_Doc']

    token = st.sidebar.radio("Token", tokenize)
    norm = st.sidebar.radio("Stemmer", normalisation)
    doc = st.sidebar.radio("Index", type_doc)

    data = ri.load_table(token, norm, doc)
    query = None

    applique_query = st.checkbox(label='Queries Dataset', value=False)

    if applique_query:
        num_query = st.number_input(label='Entrer ID', value=1, min_value=1, max_value=nbr_max_queries)
        st.write(queries[num_query-1]['texte'])
    else:
        query = st.text_area("Query", "", key='1')

    model_ri = st.sidebar.radio("Search Model", ["Aucune", 'Vectorial', 'Probabilist BM25', 'Boolean'])
    model_vec = ''
    K, B = None, None

    if model_ri == 'Vectorial':
        model_vec = st.sidebar.radio("Vector Space Model", ["Scalar Product", 'Cosine Measure', 'Jaccard Measure'])

    if model_ri == 'Probabilist BM25':
        K = st.sidebar.number_input("Entrer K :", step=0.01, value=0.5, format="%.2f")
        B = st.sidebar.number_input("Entrer B :", step=0.01, value=0.5, format="%.2f")

    if not query and not applique_query:
        st.dataframe(data)
    elif applique_query:
        if model_ri == 'Vectorial' and model_vec:
            st.title(f'Algorithme de {model_vec}')
            d, mesures_evaluation = ri.model_vector(data, queries[num_query-1]['texte'], model_vec, token, norm, num_query=num_query, test=1, jugements=jugements)
            st.dataframe(d)
            afficher_mesures_evaluation(mesures_evaluation)
        elif model_ri == 'Probabilist BM25' and K and B:
            d, mesures_evaluation = ri.BM25(data, queries[num_query-1]['texte'], token, norm, K, B, num_query=num_query, test=1, jugements=jugements)
            st.dataframe(d)
            afficher_mesures_evaluation(mesures_evaluation)

    elif query:
        if query.isdigit() and int(query):
            afficher_document(int(query), data, docs)
        else:
            word_query = re.sub(r'\s+', ' ', query).strip().upper().split(' ')
            if len(word_query) == 1 and model_ri != 'Boolean':
                word = ri.valider_mot(word_query[0], norm)
                data_search = data[data["Terme"].str.contains(word, case=False, na=False)]
                st.dataframe(data_search)
            else:
                if model_ri == 'Vectorial' and model_vec:
                    st.title(f'Algorithme de {model_vec}')
                    d = ri.model_vector(data, query, model_vec, token, norm)
                    st.dataframe(d)
                elif model_ri == 'Probabilist BM25' and K and B:
                    d = ri.BM25(data, query, token, norm, K, B)
                    st.dataframe(d)
                elif model_ri == 'Boolean':
                    ri.evaluer_expression_logique(data, query, norm)

if __name__ == "__main__":
    main()