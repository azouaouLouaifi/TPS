
import streamlit as st

from st_aggrid import AgGrid, GridUpdateMode, JsCode, AgGridTheme
from st_aggrid.grid_options_builder import GridOptionsBuilder

import pandas as pd  # For general data manipulation
import matplotlib.pyplot as plt  # For visualization
import seaborn as sns  # For enhanced visualization
import geopandas as gpd #for handling the shapefile
import numpy as np
from shapely.geometry import Point
from collections import Counter
from scipy.stats import skew
from shapely import wkt



# def list_choix ( options):
    
#     # Créer un sélecteur déroulant
#     choix = st.sidebar.selectbox("Choisissez une Attribut", options, index=len(options)-1)
#     return choix

# Fonction pour crrer un tableau interactive


def afficher_description(df):
    # Créer un DataFrame vide pour stocker les informations de description
    description_df = pd.DataFrame(columns=['Type', 'Count', 'Valeurs Uniques', 'Valeurs Manquantes', 'Valeurs == 0', 'Min', 'Max'])

    # Remplir les informations pour chaque colonne du DataFrame
    for column in df.columns:
        description_df.loc[column] = [
            df[column].dtype,  # Type
            df[column].count(),  # Nombre de valeurs non nulles
            df[column].nunique(),  # Nombre de valeurs uniques
            df[column].isnull().sum(),  # Valeurs manquantes
            (df[column] == 0).sum(),  # Valeurs égales à 0
            df[column].min(),  # Valeur minimale
            df[column].max()   # Valeur maximale
        ]

    # Afficher le tableau sous forme de DataFrame dans Streamlit
    st.subheader("Description sur Dataset")
    st.dataframe(description_df)


@st.cache_data
def load_data(path):
    # Charger les données depuis un fichier CSV
    return pd.read_csv(path)

def table_interactif( path):

    bol = False

    try:

        data = load_data(path)
        
        _function = st.checkbox('Update / Delete', value=False)

    
        if   _function:

            save = st.button(label='Save', key="save")   

            # JavaScript pour la suppression des lignes sélectionnées
            js =JsCode( """
            function (e) {
                let api = e.api;
                let sel = api.getSelectedRows();
                api.applyTransaction({remove: sel});
            }
            """)

            # Configuration des options de la table
            gd = GridOptionsBuilder.from_dataframe(data)
            gd.configure_pagination(enabled=True)
            gd.configure_default_column(editable=True, groupable=True)
            gd.configure_selection(selection_mode="multiple", use_checkbox=True)
            
            # Ajouter du JavaScript personnalisé

            gridOption = gd.build()
            gridOption["onRowSelected"] = js

            if "climate" in path :
                d = data.head(100000)
            else:
                d = data


            # Enregistrer le tableau AgGrid dans une variable
            t = AgGrid(
                d,
                gridOptions=gridOption,
                update_mode=GridUpdateMode.VALUE_CHANGED,
            
                height=450,
                width="100%",
                reload_data=True,
                allow_unsafe_jscode=True,
                theme=AgGridTheme.BALHAM
            )

            if save :
                # Récupérer les données mises à jour après suppression ou modification
                updated_data = pd.DataFrame(t['data'])
                updated_data.to_csv(path, index=False)  # Sauvegarder dans le fichier CSV

                st.success('Les données ont été sauvegardées avec succès.')


        else:

            if "climate" in path :
                d = data.head(100000)
            else:
                d = data
            
            # Enregistrer le tableau AgGrid dans une variable
            AgGrid(
                d,
                height=450,
                width="100%",
                theme=AgGridTheme.BALHAM
            )

            afficher_description(data)
        return data

    except FileNotFoundError as e:
        st.error(f"Erreur de chargement : Le fichier spécifié est introuvable. Détails : {e.filename}")
    except pd.errors.EmptyDataError:
        st.error("Erreur de chargement : Le fichier est vide ou mal formaté.")
    except Exception as e:
        st.error(f"Une erreur inattendue s'est produite : {e}")

        
# Fonction Plots

def box_plot(data, column):
    plt.figure(figsize=(10, 5))

    # Boxplot with outliers
    sns.boxplot(data=data, y=column, color='skyblue')
    plt.title(f'Boxplot of {column} (with outliers)', fontsize=14)
    plt.ylabel(column, fontsize=12)
    plt.xlabel('Value', fontsize=12)


    # Display the boxplot with outliers
    st.subheader(f"Boxplot with outliers de {column}")
    st.pyplot(plt)

    # Boxplot without outliers
    plt.figure(figsize=(10, 5))
    sns.boxplot(data=data, y=column, color='lightgreen', showfliers=False)
    plt.title(f'Boxplot of {column} (without outliers)', fontsize=14)
    plt.ylabel(column, fontsize=12)
    plt.xlabel('Value', fontsize=12)

    # Display the boxplot without outliers
    st.subheader(f"Boxplot without outliers de {column}")
    st.pyplot(plt)

def scatter_plot (data, col1, col2):

    plt.figure(figsize=(10, 6))
    sns.scatterplot(data=data, x=col1, y=col2, color='blue', alpha=0.6)
    plt.title(f'Scatter Plot of {col1} vs {col2}', fontsize=14)
    plt.xlabel(col1, fontsize=12)
    plt.ylabel(col2, fontsize=12)
    
    # Display the plot
    st.subheader(f"Scatter Plot entre {col1} et {col2}")
    st.pyplot(plt)

def histogram_plot (data, column):
    plt.figure(figsize=(10, 5))
    sns.histplot(data=data, x=column, kde=True, color='blue', bins=30)
    plt.title(f'Histogram of {column}', fontsize=14)
    plt.xlabel(column, fontsize=12)
    plt.ylabel('Frequency', fontsize=12)

    
    # Display the plot
    st.subheader(f"Histogram de {column}")
    st.pyplot(plt)
    

# Fonctions Data Cleaning

def calculate_outlier_bounds(series):
    Q1 = series.quantile(0.25)
    Q3 = series.quantile(0.75)
    IQR = Q3 - Q1
    lower_bound = Q1 - 1.5 * IQR
    upper_bound = Q3 + 1.5 * IQR
    return lower_bound, upper_bound

def dispersion (data):
    Dispersion = []

    for column in data.columns:
    # Vérification si la colonne est numérique
        if pd.api.types.is_numeric_dtype(data[column]):
            data_column = data[column]


            # Dispersion
            std_dev = data_column.std()
            variance = data_column.var()
            minimum = data_column.min()
            maximum = data_column.max()
            Q1 = data_column.quantile(0.25)
            Q3 = data_column.quantile(0.75)
            IQR = Q3 - Q1

            Dispersion.append([column, std_dev, variance, minimum, maximum, Q1, Q3, IQR])
    
    Dispersion_df = pd.DataFrame(Dispersion, columns=[
        "Columns", "Std_dev", "Variance", 'Minimum', 'Maximum', 'Q1 (25%)', 'Q3 (75%)', 'IQR'])

    st.title('Dispersion')
    st.write(Dispersion_df)
    
def outliers(data):
    Outliers = []
    for column in data.columns:
    # Vérification si la colonne est numérique
        if pd.api.types.is_numeric_dtype(data[column]):
            data_column = data[column]

            # Outliers
            lower_bound, upper_bound = calculate_outlier_bounds(data_column)
            outliers = data_column[(data_column < lower_bound) | (data_column > upper_bound)].count()

            # Missing and unique values
            missing = data_column.isna().sum()
            unique = data_column.nunique()

            Outliers.append([column, outliers, lower_bound, upper_bound, missing, unique])
    
    Outliers_df = pd.DataFrame(Outliers, columns=[
        "Columns", 'Outliers', 'Lower bound', 'Upper bound', 'Missing', 'Unique values'])

    st.title('Outliers and Missing and unique values')
    st.write(Outliers_df)

def tendance(data):
    Central_tendency = []

    for column in data.columns:
        # Vérification si la colonne est numérique
        if pd.api.types.is_numeric_dtype(data[column]):
            data_column = data[column]

            # Central tendency
            mean = data_column.mean()
            median = data_column.median()
            mode = data_column.mode().iloc[0] if not data_column.mode().empty else np.nan
            skewness = skew(data_column.dropna())  # Ignorer les NaN pour le calcul
            

            Central_tendency.append([column, mean, median, mode, skewness])



    # Création des DataFrames
    Central_tendency_df = pd.DataFrame(Central_tendency, columns=[
        'Columns', "Mean", "Median", "Mode", 'Skewness'])
    
    st.title('Central tendency')
    st.write(Central_tendency_df)

def custom_info_to_dataframe(data):
    """
    Fonction qui extrait les informations sur un DataFrame sans utiliser `data.info()`,
    et les retourne sous forme de DataFrame.
    
    Arguments:
    - data : pd.DataFrame : Le DataFrame dont on veut extraire les informations.
    
    Retourne:
    - pd.DataFrame : Un DataFrame contenant les informations sur les colonnes, les valeurs non-nulles et les types de données.
    """
    # Initialiser une liste pour stocker les informations des colonnes
    info_data = []
    
    # Itérer sur les colonnes du DataFrame
    for column in data.columns:
        # Nombre de valeurs non-nulles dans la colonne
        non_null_count = data[column].notnull().sum()
        # Type de données de la colonne
        dtype = data[column].dtype
        # Ajouter les informations dans la liste
        info_data.append([column, non_null_count, dtype])
    
    # Créer un DataFrame avec les informations extraites
    info_df = pd.DataFrame(info_data, columns=["Column", "Non-Null Count", "Dtype"])
    
    return info_df


def verefecation_soil_data(data, validation_ranges):
    """
    Valide les données sur le sol en vérifiant plusieurs attributs selon des plages spécifiées.
    Retourne un DataFrame avec les résultats de la validation.
    """
    # Créer une liste pour collecter les résultats
    validation_results = []


    # Vérification des colonnes de pourcentage (0-100)
    for col, (min_val, max_val) in validation_ranges.items():
        if col in data.columns:
            # Identifie les valeurs invalides (en dehors de la plage min_val-max_val)
            invalid_rows = data[(data[col] < min_val) | (data[col] > max_val)]
            validation_results.append({
                'Column': col,
                'Validation': 'Valid' if invalid_rows.empty else 'Invalid',
                'Invalid Count': invalid_rows.shape[0],
                'Range': f"{min_val} - {max_val}",
                'Missing Values': data[col].isna().sum()  # Nombre de valeurs manquantes pour la colonne
           
            })

    # Convertir les résultats en DataFrame pour un affichage facile
    validation_df = pd.DataFrame(validation_results)
    
    return validation_df


def validate_soil_data(data,  gestion_valeur_invalide="Inconnue", validation_ranges = {}, not_columns=['geometry']):
    

    # Vérification des colonnes de pourcentage (0-100) et gestion des valeurs invalides
    for col, (min_val, max_val) in validation_ranges.items():
        if col in data.columns and col not in not_columns:
             
            # Correction des valeurs invalides selon la stratégie choisie
            
            if gestion_valeur_invalide == "Moyenne":
                method = data[col].mean()  # Calcul de la moyenne de la colonne
                data.loc[(data[col] < min_val) | (data[col] > max_val), col] = method
            elif gestion_valeur_invalide == "Médiane":
                method = data[col].median()
                data.loc[(data[col] < min_val) | (data[col] > max_val), col] = method

            elif gestion_valeur_invalide == "Inconnue":
                method = None
                # Remplacer les valeurs en dehors de la plage par la moyenne
                data.loc[(data[col] < min_val) | (data[col] > max_val), col] = method

            elif gestion_valeur_invalide == "Supprimant les lignes":
                invalid_rows = data[(data[col] < min_val) | (data[col] > max_val)]
                data = data[~data.index.isin(invalid_rows.index)]  # Supprimer les lignes invalides

    return  data


def data_cleaning(data, type_data, option_valeur_invalide,  doublons):

    #Display basic dataset info for soil (same for climat )
    
    st.write(f"### Des informations sur {type_data}")
    info = custom_info_to_dataframe(data)
    st.dataframe(info)

    if type_data == "Data Soil DZ":

        validation_ranges = {
        'sand % topsoil': (0, 100),
        'sand % subsoil': (0, 100),
        'silt % topsoil': (0, 100),
        'silt% subsoil': (0, 100),
        'clay % topsoil': (0, 100),
        'clay % subsoil': (0, 100),
        'OC % topsoil': (0, 100),
        'OC % subsoil': (0, 100),
        'CaCO3 % topsoil': (0, 100),
        'CaCO3 % subsoil': (0, 100),
        'BS % topsoil': (0, 100),
        'BS % subsoil': (0, 100),
        
        'pH water topsoil': (0, 14),
        'pH water subsoil': (0, 14),
        
        'BD topsoil': (0.9, 2.65),
        'BD subsoil': (0.9, 2.65),
        
        'C/N topsoil': (8, 30),
        'C/N subsoil': (8, 30),
        
        'CEC topsoil': (1, 50),
        'CEC subsoil': (1, 50),
        'CEC clay topsoil': (1, 50),
        'CEC Clay subsoil': (1, 50),
    }
        not_columns=['geometry']

        
        description ="""
            ### L'étape de validation des données

            L'étape de validation des données consiste à vérifier que les valeurs présentes dans chaque attribut (colonne) du jeu de données respectent les plages de valeurs attendues pour garantir la qualité et la cohérence des données. Cette validation permet de détecter et signaler les erreurs ou anomalies dans les données avant qu'elles ne soient utilisées pour des analyses ou des modèles.

            Les plages de validation utilisées dans cette étape sont les suivantes :
            - **Pourcentages (ex : sable, argile, matière organique)** : doivent être compris entre 0 et 100%.
            - **pH** : doit être compris entre 0 et 14.
            - **Densité apparente (BD)** : doit être compris entre 0.9 et 2.65 g/cm³.
            - **Ratio C/N** : doit être compris entre 8 et 30.
            - **Capacité d'échange cationique (CEC)** : doit être compris entre 1 et 50 cmol/kg.

            Cette étape permet de s'assurer que les données respectent les critères de validité avant d'être utilisées pour des analyses plus approfondies.
            """ 
    else:
        validation_ranges = {
        # Attributs liés aux variables météorologiques
        'PSurf': (900, 1080),  # Pression atmosphérique à la surface (en hPa)
        'Qair': (0, 0.02),  # Humidité spécifique de l'air à proximité de la surface (en kg/kg)
        'Rainf': (0, 1),  # Flux de précipitation (en kg/m²/s)
        'Snowf': (0, 1),  # Flux de neige (en kg/m²/s)
        'Tair': (-70, 60),  # Température de l'air à la surface (en C°)
        'Wind': (0, 30),  # Vitesse du vent à 10 mètres de la surface (en m/s)

        }

        not_columns=['geometry', 'time', 'lat', 'lon', 'season']

        description = """
            ### L'étape de validation des données

            L'étape de validation des données a pour objectif de garantir la qualité et la cohérence des valeurs contenues dans les différentes colonnes du jeu de données. En vérifiant que chaque attribut respecte les plages de valeurs attendues, cette étape permet de détecter les erreurs, incohérences ou anomalies potentielles avant que les données ne soient utilisées pour des analyses ou des modèles prédictifs.

            - **Pression atmosphérique (PSurf)** : doit être compris entre 900 et 1080 hPa.
            - **Humidité spécifique de l'air (Qai)** : doit être compris entre 0 et 0.02 kg/kg.
            - **Flux de précipitation (Rainf)** : doit être compris entre 0 et 1 kg/m²/s.
            - **Flux de neige (Snowf)** : doit être compris entre 0 et 1 kg/m²/s.
            - **Température de l'air (Tair)** : doit être compris entre -70 et 60 C°.
            - **Vitesse du vent (Wind)** : doit être compris entre 0 et 30 m/s.

            Cette étape de validation est essentielle pour assurer la fiabilité des données avant qu'elles ne soient utilisées pour des analyses détaillées ou des prévisions.
            """

    data_valide = verefecation_soil_data(data, validation_ranges)
    st.dataframe(data_valide)
    
    data = validate_soil_data(data,   option_valeur_invalide, validation_ranges, not_columns)
   
    st.write(description)
    # st.dataframe(data_valide)

    st.write(f"### Après la vérification et la correction des valeurs manquantes et invalides ({type_data})")

    data_valide = verefecation_soil_data(data, validation_ranges)
    st.dataframe(data_valide)

    if option_valeur_invalide == 'Supprimant les lignes' :
        st.write(f"### New data Après la vérification et la correction des valeurs manquantes et invalides ({type_data})")
        
        st.dataframe(data)
    

    if doublons:
        # Vérification des doublons
        duplicated_rows = data[data.duplicated(keep=False)]  # Trouver toutes les lignes dupliquées
        
        # Affichage des résultats
        st.write(f"### La vérification des lignes dupliquées ({type_data})")
        
        if not duplicated_rows.empty:  # Vérifie s'il y a des lignes dupliquées
            st.write(f"##### Il y a des lignes dupliquées ({type_data})")
            st.dataframe(duplicated_rows)  # Afficher les lignes dupliquées

            # Suppression des doublons en gardant la première occurrence
            data_cleaned = data.drop_duplicates(keep='first')  # Garde la première occurrence et supprime les autres doublons
            
            # Affichage du DataFrame après suppression des doublons
            st.write(f"### DataFrame après suppression des doublons ({type_data})")
            st.dataframe(data_cleaned)

        else:
            st.write(f"##### Aucune ligne dupliquée ({type_data})")
    
    if type_data == "Data Soil DZ":
        # Sauvegarder le DataFrame en CSV
        data.to_csv('soil_dz_cleaning.csv', index=False)  
    else:
        data.to_csv('climate_dz_cleaning.csv', index=False)


    st.success(f'Le nettoyage des données ({type_data})  a été enregistré avec succès.')  
        

# Fonctions Data Preprocissing

def missing_and_outliers(data, type_option="Inconnue", not_columns=['geometry']):
    """
    Fonction pour gérer les valeurs manquantes et les outliers dans un DataFrame.
    """
    for col in data.columns:
        if col in data.columns and col not in not_columns and pd.api.types.is_numeric_dtype(data[col]):
            
            # Détection des outliers avec la méthode de l'IQR
            Q1 = data[col].quantile(0.25)
            Q3 = data[col].quantile(0.75)
            IQR = Q3 - Q1
            lower_bound = Q1 - 1.5 * IQR
            upper_bound = Q3 + 1.5 * IQR

            # Identifier les lignes avec des outliers ou des valeurs manquantes
            condition_outliers_or_missing = (data[col] < lower_bound) | (data[col] > upper_bound) | data[col].isnull()

            # Traitement des valeurs manquantes et des outliers selon l'option choisie
            if type_option == "Moyenne":
                method = data[col].mean()  # Calcul de la moyenne de la colonne
                data.loc[condition_outliers_or_missing, col] = method

            elif type_option == "Médiane":
                method = data[col].median()  # Calcul de la médiane
                data.loc[condition_outliers_or_missing, col] = method

            elif type_option == "Locale":
                # Calculer une moyenne locale (deux valeurs avant et après)
                def local_mean(series):
                    return series.fillna(
                        (series.shift(1) + series.shift(-1)) / 2
                    ).fillna(series.mean())  # En dernier recours, utiliser la moyenne globale

                data[col] = local_mean(data[col])
            
            elif type_option == "Inconnue":
                method = None
                data.loc[condition_outliers_or_missing, col] = method
            
            elif type_option == "Supprimant les lignes":
                # Supprimer les lignes contenant des outliers ou des valeurs manquantes
                data = data[~data.index.isin(data[condition_outliers_or_missing].index)] 

    return data


def data_aggregation(data_climate):
    
    # Filtrer les données par saison et renommer les colonnes
    data_climat_Winter = data_climate[data_climate['season'] == "Winter"].rename(columns={
        'PSurf': 'PSurf_Winter', 
        'Qair': 'Qair_Winter', 
        'Rainf': 'Rainf_Winter', 
        'Snowf': 'Snowf_Winter', 
        'Tair': 'Tair_Winter', 
        'Wind': 'Wind_Winter'
    }).drop(columns = [ 'time','season' ]).sort_values(by=['lat', 'lon'], ascending=[True, True]).reset_index(drop=True)
    
    data_climat_Fall = data_climate[data_climate['season'] == "Fall"].rename(columns={  
        'PSurf': 'PSurf_Fall',
        'Qair': 'Qair_Fall',
        'Rainf': 'Rainf_Fall', 
        'Snowf': 'Snowf_Fall', 
        'Tair': 'Tair_Fall', 
        'Wind': 'Wind_Fall'
    }).drop(columns = [ 'time','season' ]).sort_values(by=['lat', 'lon'], ascending=[True, True]).reset_index(drop=True)
    
    data_climat_Summer = data_climate[data_climate['season'] == "Summer"].rename(columns={  
        'PSurf': 'PSurf_Summer',
        'Qair': 'Qair_Summer',
        'Rainf': 'Rainf_Summer', 
        'Snowf': 'Snowf_Summer', 
        'Tair': 'Tair_Summer', 
        'Wind': 'Wind_Summer'
    }).drop(columns = ['time', 'season' ]).reset_index(drop=True)
    
    data_climat_Spring = data_climate[data_climate['season'] == "Spring"].rename(columns={  
        'PSurf': 'PSurf_Spring',
        'Qair': 'Qair_Spring',
        'Rainf': 'Rainf_Spring', 
        'Snowf': 'Snowf_Spring', 
        'Tair': 'Tair_Spring', 
        'Wind': 'Wind_Spring'
    }).drop(columns = ['time', 'season' ]).reset_index(drop=True)

    winter = data_climat_Winter.groupby(['lat', 'lon']).mean().reset_index()
    fall = data_climat_Fall.groupby(['lat', 'lon']).mean().reset_index()
    summer = data_climat_Summer.groupby(['lat', 'lon']).mean().reset_index()
    spring = data_climat_Spring.groupby(['lat', 'lon']).mean().reset_index()

    del data_climate

    data_climate = winter.merge(spring, on=['lat', 'lon']) \
                       .merge(summer, on=['lat', 'lon']) \
                       .merge(fall, on=['lat', 'lon'])
    
    
   

    return data_climate

def data_fusion(data_climate, data_soil):

    data_climate_gdf = gpd.GeoDataFrame(
    data_climate,
    geometry=gpd.points_from_xy(data_climate['lon'], data_climate['lat']),
    crs="EPSG:4326"  # Assuming the coordinates are in WGS84
    )

    data_soil['geometry'] = data_soil['geometry'].apply(wkt.loads)
    
    # Convert to a GeoDataFrame
    soil_data_gdf = gpd.GeoDataFrame(data_soil, geometry='geometry', crs="EPSG:4326")

    

    
    # Reproject soil GeoDataFrame to match climate CRS
    data_soil_gdf = soil_data_gdf.to_crs(data_climate_gdf.crs)
    
    # Perform spatial join
    data_climate_soil_dz = gpd.sjoin(data_climate_gdf, data_soil_gdf, how="left", predicate="within").drop(columns = [ 'geometry' ])
    
    return data_climate_soil_dz


def data_discretization(data, method="equal_frequency", bins=4):
    
    discretized_data = data.copy()
    intervals = {}

    for col in data.columns:
        if pd.api.types.is_numeric_dtype(data[col]):
            if method == "equal_frequency":
                # Discrétisation par fréquences égales
                discretized_data[col], bins_intervals = pd.qcut(
                    discretized_data[col], q=bins, retbins=True, labels=False, duplicates='drop'
                )
            elif method == "equal_width":
                # Discrétisation par amplitudes égales
                discretized_data[col], bins_intervals = pd.cut(
                    discretized_data[col], bins=bins, retbins=True, labels=False
                )
            else:
                raise ValueError("Méthode invalide. Utilisez 'equal_frequency' ou 'equal_width'.")

            # Stocker les intervalles pour la colonne
            intervals[col] = bins_intervals

            
        else:
            discretized_data[col] = data[col]  # Laisser les colonnes non numériques inchangées

    return discretized_data, intervals


def data_reduction(data):
    # Créer une copie du DataFrame pour ne pas modifier l'original
    reduced_data = data.copy()

    # Supprimer les colonnes qui sont entièrement NaN
    reduced_data = reduced_data.dropna(axis=1, how='all')

    # Supprimer les lignes qui sont entièrement NaN (de type numpy.nan)
    reduced_data = reduced_data[~reduced_data.isna().all(axis=1)]

    # Supprimer les lignes dupliquées
    reduced_data = reduced_data.drop_duplicates()

    # Supprimer les colonnes dupliquées
    reduced_data = reduced_data.loc[:, ~reduced_data.T.duplicated()]

    return reduced_data


def data_normalization(data, method, new_min, new_max):
    
    normalized_data = data.copy()

    for col in normalized_data:
        if col not in ['lat', 'lon']:
            if pd.api.types.is_numeric_dtype(normalized_data[col]):
            
                if method == "min_max":
                    # Normalisation Min-Max
                    
                    old_min = normalized_data[col].min()
                
                    old_max = normalized_data[col].max()

                    if old_max != old_min:
                        
                        normalized_data[col] = ((normalized_data[col]  - old_min) / (old_max - old_min)) * (new_max - new_min) + new_min
                    
                    
                
                elif method == "z_score":
                    # Normalisation Z-score
                    mean = normalized_data[col].mean()
                    std = normalized_data[col].std()
                    if std != 0:
                        normalized_data[col] = (normalized_data[col] - mean) / std
                
                else:
                    raise ValueError("Méthode invalide. Utilisez 'min_max' ou 'z_score'.")
            else:
                # Laisser les colonnes non numériques inchangées
                continue
            
    print('fin de normalization')

    return normalized_data


def injection_intervale(data, intervale):
    # Dictionnaire pour stocker les intervalles
    inter = {}

    new_data = {}

    # Remplir le dictionnaire d'intervalles
    for col in data:
        # Récupérer l'intervalle pour la colonne courante
        i = intervale[col]

        
        # Créer les intervalles sous forme de chaînes de caractères

        bins = len(data[col].unique())

        #if np.isnan(data[col].unique()[0]) != True:

        if bins >= 2  :
        
            for k in range(bins):
                if k < bins-1:
                    inter[k] = f"[{i[k]:.6f} ; {i[k+1]:.6f} ["
                else:
                    inter[k] = f"[{i[k]:.6f} ; {i[k+1]:.6f} ]"
        elif bins == 1 :
            inter[0] = f"[ {i[0] }]"
        else:
            inter[0] = f"[{i[0], i[1]}]"

        new_data[col] = []

        data[col].apply(lambda x : new_data[col].append(inter[x]) )
        
        inter = {}
    
    data = pd.DataFrame(new_data)
    
    return data


def data_preprocissing( option_valeur_manquantes_outliers, option_discretization, bins, option_data_normalize, min  , max):

    # Load data

    data_climate, data_soil = None, None

    try:
        # Chargement des fichiers CSV
        data_climate = load_data("climate_dz_cleaning.csv")
        data_soil = load_data("soil_dz_cleaning.csv")

        # 1. Réduction par agrégation saisonnière (C)
        data_climate = data_aggregation(data_climate)
        st.success("Fin de la réduction par agrégation saisonnière")

        # 2. Intégration des données (D)
        data_climate_soil_dz = data_fusion(data_climate, data_soil)
        st.success("Fin de l'intégration des données")

        # 3. Gestion des valeurs manquantes et aberrantes (E)
        data = missing_and_outliers(data_climate_soil_dz, option_valeur_manquantes_outliers, ['geometry', 'lat', 'lon'])
        st.success("Fin de la gestion des valeurs manquantes et aberrantes")

        # 4. Discrétisation des données continues (G)
        data, intervale = data_discretization(data, option_discretization, bins)
        st.success("Fin de la discrétisation des données continues")

        # 5. Réduction des redondances (H)
        data = data_reduction(data)
        st.success("Fin de la réduction des redondances")

        # 6. Normalisation (F)
        intervale = data_normalization(intervale, option_data_normalize, min, max)
        st.success("Fin de la normalisation")

        # injecter les intervale dans dataframe
        data = injection_intervale ( data, intervale)

        # Sauvegarder le DataFrame en CSV
        data.to_csv('data_final.csv', index=False)

    
        st.success('Le prétrairtement des données a été enregistré avec succès.') 

        
    except FileNotFoundError as e:
        st.error(f"Erreur de chargement : Le fichier spécifié est introuvable. Détails : {e.filename}")
    except pd.errors.EmptyDataError:
        st.error("Erreur de chargement : Le fichier est vide ou mal formaté.")
    except Exception as e:
        st.error(f"Une erreur inattendue s'est produite : {e}")
 


