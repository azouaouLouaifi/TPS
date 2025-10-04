package SystemProduit;

import java.util.ArrayList;

public class Produit {
    protected String nom ;
    protected String referonce ;
    protected String descriptif ;
    protected double prix ;
    protected ArrayList<String> ensembleInformationDeRecherche = new ArrayList<String>();
    protected ArrayList<String> ensembleCaracteristiques = new ArrayList<String>();
    public  int categorie = 0;


    public Produit (String nom , String referonce , String descriptif , ArrayList<String> ensembleCaracteristiques ,ArrayList<String> ensembleInformationDeRecherche,double prix)
    {
        this.nom = nom ;
        this.referonce = referonce;
        this. descriptif=descriptif;
        this.ensembleCaracteristiques=ensembleCaracteristiques;
        this.prix=prix;
        this.ensembleInformationDeRecherche=ensembleInformationDeRecherche;

    }
    public Produit (String nom , String referonce, ArrayList<String> ensembleCaracteristiques ,ArrayList<String> ensembleInformationDeRecherche,double prix)
    {
        this.nom =nom ;
        this.referonce = referonce;
        this.ensembleCaracteristiques=ensembleCaracteristiques;
        this.ensembleInformationDeRecherche=ensembleInformationDeRecherche;

        this.prix=prix;

    }

    public void affichage(){
        System.out.println("le nom de produit  :"+nom);
        System.out.println("la référonce de produit :"+referonce );
        System.out.println("l'ensemble Caracteristiques de produit:");
        for(String mots : ensembleCaracteristiques)
        System.out.println(mots);
        System.out.println("l'ensemble d'informations de la recherch de produit:");
        for(String mots : ensembleInformationDeRecherche)
        System.out.println(mots);
        System.out.println("entre le prix de produit  :"+prix+" DA");
    }

    public int recherchParInformation(ArrayList<String> info){
        int i = 0 ;
        for(String information : info){
            for(String mots : ensembleInformationDeRecherche){
                if(information.equals(mots))
                i++;
            }
        }
        return i ;
    }

    public String getReferonce (){return this.referonce ;} 
    public double getPrix(){return prix ;}
    //public int getCategorie(){return categorie ;}
    public void setCategorie(int categorie){ this.categorie=categorie ;}

    public void setPrix(Double prix){this.prix = prix;}
}
