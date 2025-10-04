package SystemProduit;

import java.util.ArrayList;



public class Electrionique  extends Produit {
    protected final int categorie = 2;
    public Electrionique (String nom , String referonce, String descriptif , ArrayList<String> ensembleCaracteristiques ,ArrayList<String> ensembleInformationDeRecherche ,Double prix){
        super(nom ,referonce,descriptif ,ensembleCaracteristiques ,ensembleInformationDeRecherche,prix);
        setCategorie(2);
    }
    public void affichage(){
        super.affichage();
        System.out.println("la descriptif de produit: "+ descriptif);
    }
}
