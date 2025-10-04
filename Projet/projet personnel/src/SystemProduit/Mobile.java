package SystemProduit;

import java.util.ArrayList;



public class Mobile extends Produit {

    

    public Mobile (String nom , String referonce,  ArrayList<String> ensembleCaracteristiques ,ArrayList<String> ensembleInformationDeRecherche,double prix){
        super(nom , referonce,ensembleCaracteristiques ,ensembleInformationDeRecherche, prix);
        descriptif=null ;
        setCategorie(1);

    }}
