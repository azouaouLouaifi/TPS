package SystemProduit;

import java.util.*;
public class Informatique extends Produit {
    protected final int categorie = 1;
    public Informatique (String nom , String referonce, String descriptif , ArrayList<String> ensembleCaracteristiques ,ArrayList<String> ensembleInformationDeRecherche,double prix){
        super(nom ,referonce,descriptif ,ensembleCaracteristiques , ensembleInformationDeRecherche ,prix);
        setCategorie(1);
    }
    public void affichage(){
        super.affichage();
        System.out.println("la descriptif de produit: "+ descriptif);
    }
}
