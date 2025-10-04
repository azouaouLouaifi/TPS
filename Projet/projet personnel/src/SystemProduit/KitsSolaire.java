package SystemProduit;

import java.util.ArrayList;



public class KitsSolaire extends Produit {
    protected final int categorie = 3;
    

    public KitsSolaire(String nom, String referonce, String descriptif, ArrayList<String> ensembleCaracteristiques,
			ArrayList<String> ensembleInformationDeRecherche, double prix) {
    	 super(nom ,referonce,descriptif ,ensembleCaracteristiques,ensembleInformationDeRecherche ,prix);
         setCategorie(3);
		// TODO Auto-generated constructor stub
	}

	
	public void affichage(){
        super.affichage();
        System.out.println("la descriptif de produit: "+ descriptif);
    }
}
