package SystemGestion;

import SystemCarte.Carte;
import SystemComonde.Command;

public class Serv extends Magasin {
    
    public void modifPrix() {
        System.out.println("entre la référonce de le produit  :");
        String ref = sc.next();
        System.out.println("entre le nouveau prix de produit  :");
        double p = sc.nextDouble();
        if (stock.modificationDePrix(ref, p))
            System.out.println("le prix de produit est bien modifer .");
        else
            System.out.println("le prix de produit n'est pas modifier .");

    }
     // pour l'agent commercial
     public Carte newCarte(){return null ;}
     // pour la command
     public  void calcule(Command com){}
     
    @Override
    public void valideCommand(Command com, int choix) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void retourCommande() {
        // TODO Auto-generated method stub
        
    }
}
