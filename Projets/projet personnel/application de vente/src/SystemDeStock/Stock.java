package SystemDeStock;

import java.util.ArrayList;
import java.util.Scanner;

import SystemProduit.Produit;

public class Stock {
    public ArrayList <Produit> listProduitDisponible = new  ArrayList <Produit>();
    public ArrayList <Produit> listProduitReservation = new  ArrayList <Produit>();
    public Stock(){}

    Scanner sc = new Scanner(System.in);

    // affichage
    public void affichageDeTousProduitsDisponible (){
        for(Produit pro : listProduitDisponible){
           pro.affichage();
        }
    }

    public void affichageProduitDisponible (String r){
        int test = 0 ;
        if(!listProduitDisponible.isEmpty())
        for(Produit pro : listProduitDisponible)
                if(pro.getReferonce().equals(r)){
                 pro.affichage();
                 test=1;
                 break;
            }
        if(test == 0)
        System.out.println("le produit produit n'est exist pas");
    }

    public void affichageProduitReserver (String r){
        if(!listProduitReservation.isEmpty())
        for(Produit pro : listProduitReservation)
                if(pro.getReferonce().equals(r)){
                 pro.affichage();
            }
        System.out.println("le produit produit n'est exist pas");
    }
    
    public void recherchAvecInformation(ArrayList<String> info){
        ArrayList<Integer> egalInfo = new ArrayList<Integer>(); 
        for(Produit pro : listProduitDisponible)
            egalInfo.add(pro.recherchParInformation(info));
        int i = 0 ;
        for(int j = 5 ; 0<j ; j-- ){
            for(int k : egalInfo){
                if(k == j){
                    listProduitDisponible.get(i).affichage();;
                }
                i++;
            }
            i=0;
        }    
        
    }
    //ajouter un nouveau produit
    public void ajouteNewProduit (Produit p){
        listProduitDisponible.add(p);
        for(Produit pro : listProduitDisponible){
            if(pro.getReferonce().equals(p.getReferonce())){
                 System.out.println("l'ajoute de produit est bien enregistre");
                 break ;
            } else{
                System.out.println("l'ajoute de produit n'est pas bien enregistre");
            }
        }
    }
    
    //suppression d'un produit disponible
    public void  retirerProduitDisponible (String r){
        Produit p=null;
        if(!listProduitDisponible.isEmpty())
        for(Produit pro : listProduitDisponible)
        if(pro.getReferonce().equals(r)){
            p =pro;
            break;
        }
        if(p != null){
            listProduitReservation.add(p);
            int i = listProduitDisponible.indexOf(p);
            listProduitDisponible.remove(i);
        }
    }
        
    //suppression d'un produit réserver
    public void retirerProduitReserver (String r){
        Produit p=null;
        if(!listProduitReservation.isEmpty())
        for(Produit pro : listProduitReservation)
        if(pro.getReferonce().equals(r)){
            p =pro;
            break;
        }
        if(p != null){
            listProduitDisponible.add(p);
            int i = listProduitReservation.indexOf(p);
            listProduitReservation.remove(i);
        }
    }

    //suppression d'un produit réserver
    public Produit supprimerReserver (String r){
        for(Produit pro : listProduitReservation){
            if(pro.getReferonce().equals(r)){
                listProduitReservation.remove(pro);
                return pro ;
            } 
        }
        return null ;
    }

    // les recherches   
    public boolean recherchDisponible (String r){ 
        for(Produit pro : listProduitDisponible)
            if(pro.getReferonce().equals(r))
                return true;
        return false;
     }

    public boolean recherchReserver (String r){
        for(Produit pro : listProduitReservation){
            if(pro.getReferonce().equals(r)){
                return true;
            } 
        }
        return false;
     }

     public Produit returnPriduitDisponible (String r){ 
        for(Produit pro : listProduitDisponible){
            if(pro.getReferonce().equals(r)){
                return pro;
            } 
        }
        return null;
     }

     public Produit returnPriduitReserver (String r){
        for(Produit pro : listProduitReservation){
            if(pro.getReferonce().equals(r)){
                return pro;
            } 
        }
        return null;
     }
    
    public double prixProduit (String r){
        for(Produit pro : listProduitReservation){
            if(pro.getReferonce().equals(r)){
                return pro.getPrix();
            } 
        }
        return 0;
    }
    
    public boolean modificationDePrix (String r , double p){
        for(Produit pro : listProduitDisponible){
            if(pro.getReferonce().equals(r)){
                pro.setPrix(p);
                return true;
            } 
        }
        return false;
    }

}
