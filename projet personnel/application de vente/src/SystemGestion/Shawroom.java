package SystemGestion;

import SystemCarte.Carte;
import SystemComonde.Command;

public class Shawroom extends Magasin {
    // le mot de passe de le magasein
    static private String motePasseShawroom = "shawroom";
   

    public Shawroom (){}
    public Carte newCarte() {
        System.out.println("entre le nom : ");
        String nom = sc.next();
        System.out.println("entre le prénom : ");
        String prenom = sc.next();
        System.out.println("entre l'addresse : ");
        String add = sc.next();
        Carte c = new Carte(nom, prenom, add);
        carte.add(c);
        for (Carte car : carte)
            if (car.getMotePasse().equals(c.getMotePasse()))
                System.out.println("la carte de fidélité est bien enregistrer ");
       
                return c;
    }

    // pour le responsable commercial
    public  void modifPrix(){}
     // pour la command
     public  void calcule(Command com){}
    
    @Override
    public void valideCommand(Command com, int choix) {
       super.validCommand(com, choix);
        
    }
    @Override
    public void retourCommande() {
        super.retourCommand();
        
    }

      // les recherche des command
      public Command rechercheCommandeValid(String ref) {
        for (Command com : listCommandValid)
            if (com.getRefComond().equals(ref))
                return com;
        return null;
    }

    public boolean rechercheCommandeNonValid(String ref) {
        for (Command com : listCommand)
            if (com.getRefComond().equals(ref))
                return true;
        return false;
    }

    // pour modifier un mot de passe de le shawroom
   

    public void modifMotsPasseRessponsable() {
        System.out.println("entre le mote passe  de le responsable ");
        String mots1 = sc.next();
        if (mots1.equals(motePasseShawroom)) {
            System.out.println("entre nouveau le mote passe  de le responsable ");
            String mots2 = sc.next();
            setMotePasseShawroom(mots2);
            System.out.println("le mote passe est bien modifier : " + motePasseShawroom);
        }

    }


    public void setMotePasseShawroom(String motePasseShawroom) {
        Shawroom.motePasseShawroom = motePasseShawroom;
    }

    public String getMotePasseShawroom() {
        return motePasseShawroom;
    }

   
    
}
    

