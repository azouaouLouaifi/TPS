package SystemComonde;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import SystemCarte.Carte;
import SystemGestion.*;
import SystemProduit.Produit;

public class Command extends Magasin {
    //la référonce de la comonde création automatique
    private String refComond ; 

    //liste contient les références des produits
    private ArrayList <String> listRefProduit = new  ArrayList <String>();

    //liste contient  des produits
    private ArrayList <Produit> listProduit = new  ArrayList <Produit>();

    //montant total des produits
    private double  monton= 0;

    //moutons avec remise
    private double montonCumul = 0  ;

    // date de ctéation et validation  de la  comonde
    private String dateDemende ;
    private String dateValidation ;

    // pour la création de la référence 
    static private String dateDernierComonde ="28/12/2021" ;
    static private int NbrComonde = 1;

    // constrecteur vide 
    public Command (){
            LocalDateTime Date = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dateDemende = Date.format(myFormatObj);
            if(!dateDemende.equals(dateDernierComonde))
                NbrComonde=1;
            dateDernierComonde=dateDemende;
            refComond = creationReferenceCommand(dateDemende);
            NbrComonde++;
        }

    // ajouter un produit dans la liste des produit
    public void addProduit (Produit p){
        listProduit.add(p);
    }

    // supprimer un produit dans la liste des produit
    public void romoveProduits (){
        for(Produit p : listProduit){
            stock.listProduitDisponible.add(p);
        }
    }

    // méthode de la création de référance
    public String creationReferenceCommand(String d){
        String tab[] = d.split("/");
        String  ref = tab[0] ;
        ref =ref.concat("."+tab[1]);
        ref =ref.concat("."+tab[2]);
        ref =ref.concat("."+String.valueOf(NbrComonde));
        NbrComonde++;
        return ref ;
    }
    
    //calcule de la monton 
     public void calcule(){
        double monton1=0;
        for(String ref : listRefProduit){
            monton1 = monton1 + stock.prixProduit(ref);
        }
        this.monton=monton1;
        this.montonCumul=monton1;
    }

    // ajouter un référence d'un produit
    public void ajouterReferenceDeProduit(String p){
        listRefProduit.add(p);
    }

    // l'affichage d'une comonde 
    public void afficheCommand(){
        System.out.println("la référonce de la command: "+refComond);
        System.out.println("la date de commende "+dateDemende);
        System.out.println("les produites :");
        for(String ref : listRefProduit){
            System.out.println("la référonce : "+ref+"\t le prix : "+stock.prixProduit(ref));
        }
        System.out.println("la monton totale : "+this.monton+" DA");
    }
    
    public void afficheDeMontonCumule(){
        System.out.println("la monton Cumule : "+montonCumul+" DA");
    }

   
    // les gaters et les seters
    public double getMonton() {
        return this.monton;
    }
    public double getMontonCumul() {
        return this.montonCumul;
    }
    public String getDateDemende() {
        return dateDemende;
    }
    public String getDateValidation() {
        return dateValidation;
    }
    public String getRefComond() {
        return refComond;
    }
    public void setMonton(double monton) {
        this.monton = monton;
    }
    public void setMontonCumul(double montonCumul) {
        this.montonCumul = montonCumul;
    }
    public void setDateValidation(String date) {
        this.dateValidation = date;
    }   
    public ArrayList <String> getListProduit() {
        return listRefProduit;
    }



    @Override
    public void valideCommand(Command com, int choix) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void retourCommande() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void modifPrix() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Carte newCarte() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void calcule(Command com) {
        // TODO Auto-generated method stub
        
    }
    

}    