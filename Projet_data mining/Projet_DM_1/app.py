import streamlit as st
import pandas as pd
import fonction_projet as fp

def main():

    path_data_soil = 'soil_dz_allprops.csv'
    path_data_climat = 'climate_dz.csv'

    path_data_soil_cleaning = 'soil_dz_cleaning.csv'
    path_data_climat_cleaning = 'climate_dz_cleaning.csv'

    path_data_final = 'data_final.csv'


    st.title('Application d\'analyses et préparation des données')
    st.subheader("Auteur: Azouaou Louaifi")

    

    # Barre latérale avec une option radio exclusive
    st.sidebar.header("Afficher les données")
    option = st.sidebar.radio("Choisir les données à afficher", 
                              ["Aucune", "Data Climate DZ", "Data Soil DZ", "Data Climate DZ cleaning", "Data Soil DZ cleaning", "Data Final"])

    # Action basée sur le choix du radio bouton
    if option == "Aucune":
        data = None
        bol = False

    if option == "Data Climate DZ":
        st.subheader(option)
        data = fp.table_interactif(path_data_climat)
        bol = True
    
    elif option == "Data Soil DZ":
        st.subheader(option) 
        data = fp.table_interactif(path_data_soil)
        bol = True
        
    elif option == "Data Climate DZ cleaning":
        st.subheader(option)
        data = fp.table_interactif(path_data_climat_cleaning)

    elif option == "Data Soil DZ cleaning":

        st.subheader(option)
        data = fp.table_interactif(path_data_soil_cleaning)
        bol = True

    elif option == "Data Final":

        st.subheader(option)
        data =fp.table_interactif(path_data_final)
        bol = True
    
    if data is not None:
        option_attribut = list(data.head(0).columns) 
        bol = True  
    
    

    
    if option != "Aucune" and option != "Data Final" and  bol:

       
        # Ajouter une checkbox pour afficher ou masquer le boxplot
        show_plot = st.sidebar.checkbox("Les Plots")

        if show_plot:
           
            option_plot = st.sidebar.radio("Choisir le type de plot avec quel attribute", 
                                ["Box Plot", "Scatter Plot", "Histogram Plot"])
            
            option_attribut.append('Aucune')

            if option_plot == "Scatter Plot":
                att1 = fp.list_choix(option_attribut)
                if att1:
                    option_attribut.remove(att1)
                    att2 = fp.list_choix(option_attribut)

                    

                    if(att1 != att2 and att1 != 'Aucune' and att2 != 'Aucune'):
                        fp.scatter_plot(data, att1, att2)


            else:
                att = fp.list_choix(option_attribut)
                
                if(att != 'Aucune'):
                    if(option_plot == "Box Plot"):
                        fp.box_plot(data, att)
                    else:
                        fp.histogram_plot(data, att)
        
        # Ajouter une checkbox pour afficher ou masquer le boxplot
        show_tandance = st.sidebar.checkbox("Central tendency")
        show_dispersion = st.sidebar.checkbox("Dispersion")
        show_outliers = st.sidebar.checkbox("Outliers and Missing")

        if (show_tandance):
            fp.tendance(data)
        
        if(show_dispersion):
            fp.dispersion (data)


        if(show_outliers):
            fp.outliers(data)

        if(option in [ "Data Climate DZ", "Data Soil DZ"]):
            show_data_cleaning = st.sidebar.checkbox("Data cleaning")

            if(show_data_cleaning):
                
               
                option_valeur_invalide = st.sidebar.radio("Choisir le type de gestion des valeurs invalide", 
                                    ["Aucune","Moyenne", "Médiane", 'Supprimant les lignes',"Inconnue"])
                
                show_doublons = st.sidebar.checkbox("Supprimer les lignes dupliquées")

                if( option_valeur_invalide != "Aucune" ):

                    buton = st.sidebar.button(label='Appliquer')

                    if buton:
                        fp.data_cleaning(data, option, option_valeur_invalide, show_doublons)
    if  option ==  "Aucune" :
        
        show_data_processing = st.sidebar.checkbox("Data Processing")

        if(show_data_processing):
            
            

            st.markdown('''
            ### Les étapes du prétraitement des données

            - **1. Réduction par agrégation saisonnière (C)**  
            Regroupement des données par saison pour réduire leur granularité et simplifier les analyses.

            - **2. Intégration des données (D)**  
            Fusion des données de **Data Soil Dz** et **Data Climate Dz**.
            
            - **3. Gestion des valeurs manquantes et aberrantes (E)**  
            Identification et correction des valeurs manquantes ou outliers pour garantir la cohérence des données.

            - **4. Discrétisation des données continues (G)**  
            Transformation des variables continues en catégories pour faciliter leur analyse.
    
            - **5. Réduction des redondances (H)**  
            Suppression des doublons et des colonnes ou lignes redondantes pour simplifier les données.
            
            
            - **6. Normalisation (F)**  
            Mise à l'échelle des données pour assurer une uniformité entre les différentes variables.
            
            ''') 

            option_valeur_manquantes_outliers = st.radio("Choisir le type de gestion des valeurs manquantes ou Outliers", 
                                ["Aucune","Moyenne", "Locale", "Médiane", 'Supprimant les lignes',"Inconnue"])

            option_discretization = st.radio("Choisir le type de discretization", 
                                ["Aucune","equal_frequency", "equal_width"])
            
            bins = 0

            if option_discretization != "Aucune":

                bins = st.number_input(
                    "Entrer un nombre entier pour les bins :", 
                    min_value=1,  # Valeur minimale autorisée
                    step=1,       # Les incréments se font par 1
                    value=5,      # Valeur par défaut
                    format="%d"   # Format entier
                )


            option_data_normalize = st.radio("Choisir la méthode de Data Normalization", 
                                ["Aucune","min_max", "z_score"])
            min = 0
            max = 0
            if( option_data_normalize == "min_max" ):

                min = st.number_input(label="Le minimum", value=0,)
                max = st.number_input(label="Le maximum", value=1)
            
            condition_applique = option_valeur_manquantes_outliers != "Aucune" and ( option_data_normalize == "z_score" or (option_data_normalize == "min_max" and min != max ))
            
            # Condition pour afficher le bouton
            c1 = (option_valeur_manquantes_outliers != "Aucune" and  option_discretization != "Aucune" and bins != 0)

            condition_applique = (
                c1 and ( option_data_normalize == "z_score" or 
                        (option_data_normalize == "min_max" and min != max ))
            )
                
            
            if condition_applique :
                buton = st.button(label='Appliquer')

                if buton:
                    fp.data_preprocissing( option_valeur_manquantes_outliers, option_discretization, bins, option_data_normalize, min  , max)
            


                
    
if __name__ == '__main__':
    main()
