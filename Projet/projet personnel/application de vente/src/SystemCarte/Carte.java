package SystemCarte;

import java.util.ArrayList;

import SystemComonde.Command;
import SystemGestion.Magasin;
import SystemProduit.*;


public class Carte extends Magasin implements Remise {
    // les information de la carte
    private String nom ;
    private String prenom ;
    private String addresse ;
    // pour ctéation d'un mote passe 
    private String motePasse ;
    static private int NbCarte = 1 ;
    // les monton et les nombre des  rubriques cumulé 
    private double montonCumulCat1 ;
    private double montonCumulCat2;
    private double montonCumulCat3 ;
    private int  NbproduitCumulCat1 ;
    private int  NbproduitCumulCat2 ;
    private int  NbproduitCumulCat3 ;
    private int nb1 , nb2 , nb3 ;
    private double montonCumulCatProvisoire1 ;
    private double montonCumulCatProvisoire2;
    private double montonCumulCatProvisoire3 ;

    public Carte (String nom ,String prenom ,String addresse){
        this.nom=nom;
        this.prenom=prenom;
        this.addresse=addresse;
        this.motePasse = CreationMotePasse() ;
        montonCumulCatProvisoire1= 0;
        montonCumulCatProvisoire2= 0;
        montonCumulCatProvisoire3= 0;
        montonCumulCat1= 0;
        montonCumulCat2= 0;
        montonCumulCat3= 0;
        NbproduitCumulCat1=0 ;
        NbproduitCumulCat2=0 ;
        NbproduitCumulCat3=0;
        nb1=0 ; nb2=0;nb3=0;
        System.out.println("la création est bien fait voilà votre mote passe "+this.motePasse);
    }
    
    private String  CreationMotePasse(){
        char [] mote = {this.nom.charAt(0) , this.prenom.charAt(0) , this.addresse.charAt(0)};
        String m = new String(mote);
        String str =m.concat(String.valueOf(NbCarte)) ;
        NbCarte++;
        return str ;
    }

    public String getMotePasse(){return motePasse ;}

    @Override
    public void  ajoutePrixProduitCarte (Command com) {
        ArrayList <String> liste = com.getListProduit();
        for(String r : liste){
            Produit p = stock.returnPriduitReserver(r);
            if(p.categorie == 1){
                montonCumulCatProvisoire1=montonCumulCatProvisoire1+p.getPrix();
                nb1++;
            }else{
                if( p.categorie==2){
                    montonCumulCatProvisoire2=montonCumulCatProvisoire2+p.getPrix();
                    nb2++;
                }else{
                    if(p.categorie==3){
                        montonCumulCatProvisoire3=montonCumulCatProvisoire3+p.getPrix();
                        nb3++;
                    }
                }
            }
        }
        System.out.println("le prix total de 1er catégorie  : "+montonCumulCatProvisoire1);
        System.out.println("le prix total de 2eme catégorie  : "+montonCumulCatProvisoire2);
        System.out.println("le prix total de 3eme catégorie  : "+montonCumulCatProvisoire3);
    }
    @Override
    public void calcule (Command com){
        double montonCumul = com.getMonton() ;
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        ArrayList <String> liste = com.getListProduit();
        for(String r : liste){
            Produit p = stock.returnPriduitReserver(r);
            if(p.categorie == 1) c1++;   
            else if( p.categorie == 2) c2++; 
                else  if(p.categorie == 3) c3++;
            }
        if(c1 == 1)    montonCumul=montonCumul - (montonCumulCat1+montonCumulCatProvisoire1)*cat1;
        if(c2 == 1)    montonCumul=montonCumul - (montonCumulCat2+montonCumulCatProvisoire1)*cat2;
        if(c3 == 1)    montonCumul=montonCumul - (montonCumulCat3+montonCumulCatProvisoire1)*cat3;
        com.setMontonCumul(montonCumul);
    }
    @Override
    public void valideCumul(){
        montonCumulCat1=0;
        NbproduitCumulCat1=NbproduitCumulCat1+nb1;
        montonCumulCat2=0;
        NbproduitCumulCat2=NbproduitCumulCat2+nb2;
        montonCumulCat3=0;
        NbproduitCumulCat3=NbproduitCumulCat3+nb3;
        nb1=0 ; nb2=0;nb3=0;
        montonCumulCatProvisoire1= 0;
        montonCumulCatProvisoire2= 0;
        montonCumulCatProvisoire3= 0;
    }
    @Override
    public void nonValideCumul(){
        montonCumulCat1= montonCumulCat1+montonCumulCatProvisoire1;
        montonCumulCat2= montonCumulCat2+montonCumulCatProvisoire2;
        montonCumulCat3= montonCumulCat3+montonCumulCatProvisoire3;
    }

    // l'affichage 
    @Override
    public void affichage(){
        System.out.println("la montant cumulé dans 1er catégories  les produits informatiques et mobiles : "+montonCumulCat1+"\n le remis :"+montonCumulCat1*cat1);
        System.out.println("la montant cumulé dans 2eme catégories les produits électronique et électroménager : "+montonCumulCat2+"\n le remis :"+montonCumulCat2*cat2);
        System.out.println("la montant cumulé dans 3eme catégories  les kits solaires : "+montonCumulCat3+"\n le remis :"+montonCumulCat3*cat3);
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
}

