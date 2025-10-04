package SystemGestion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import SystemCarte.Carte;
import SystemComonde.Command;
import SystemDeStock.Stock;
import SystemProduit.Produit;

public abstract class  Magasin implements GVente , GRetour {
    Scanner sc = new  Scanner (System.in);
    // liste des carte de fidélité
    protected static ArrayList<Carte> carte = new ArrayList<Carte>();
    // liste des commandes validées
    protected static ArrayList<Command> listCommandValid = new ArrayList<Command>();
    // liste des commandes
    protected static ArrayList<Command> listCommand = new ArrayList<Command>();
    //le stock 
    protected static Stock stock = new Stock();
    // pour les vents 
    // ajouter une commande Au niveau du showroom

    public void creationCommand() {
        Command com = new Command();

        System.out.println("entrer les référonces des produit ");
        int i = 1;
        String plus = null;
        String r;
        do {
            System.out.println("entrer la référonce de  " + i + " produit");
            r = sc.next();
            if (stock.recherchDisponible(r)) {
                com.ajouterReferenceDeProduit(r);
                stock.retirerProduitDisponible(r);
                i++;
            } else
                System.out.println("le produit n'est pas désponible");
            System.out.println("si vous avait encour des produits entrer 'oui' ");
            plus = sc.next();
            plus = plus.toLowerCase();
        } while (plus.equals("oui"));
        com.calcule();
        com.afficheCommand();
        Carte cart = null;
        System.out.println("si vous avez une carte ");
        int choix;
        do {
            System.out.println("entrer 1 pour 'oui' et 2 pour 'non' .");
            choix = sc.nextInt();
        } while (choix != 1 && choix != 2);
        if (choix == 1) {
            String choixDeSauter = "oui", mots = null;
            do {
                System.out.println("entrer le mote passe de la carte");
                mots = sc.next();
                cart = carte(mots);
                if (cart == null) {
                    System.out.println(
                            "désolé le mote pas et incorrect si c'est vous voulez sauter la carte entre 'oui' ");
                    choixDeSauter = sc.next();
                    choixDeSauter = choixDeSauter.toLowerCase();
                } else {
                    cart.ajoutePrixProduitCarte(com);
                    cart.calcule(com);
                    com.afficheDeMontonCumule();
                }

            } while (cart == null && !choixDeSauter.equals("oui"));

        }
        listCommand.add(com);
        plus = null;
        System.out.println("si vous voulez valider lla commande entrer 'oui' ");
        plus = sc.next();
        plus = plus.toLowerCase();

        if (plus.equals("oui")) {
            choix = 2;
            if (cart != null) {
                System.out.println("si vous voulez utiliser votre carte ");
                do {
                    System.out.println("entrer 1 pour 'oui' et 2 pour 'non' .");
                    choix = sc.nextInt();
                } while (choix != 1 && choix != 2);
                if (choix == 1) {
                    cart.ajoutePrixProduitCarte(com);
                    cart.calcule(com);
                    com.afficheDeMontonCumule();
                    cart.valideCumul();
                } else
                    cart.nonValideCumul();
                validCommand(com, choix);
            }

        }

    }

     // ajouter une commande chez lui

    public void creationCommandEnLine(Client c) {
        Command com = new Command();

        System.out.println("vous voulez créer une carte il faut aller chez le magasin");

        System.out.println("entrer les référonces des produit ");
        int i = 1;
        String plus = null;
        do {
            System.out.println("entrer la référonce de  " + i + " produit");
            String r = sc.next();
            if (stock.recherchDisponible(r)) {
                com.ajouterReferenceDeProduit(r);
                stock.retirerProduitDisponible(r);
            } else
                System.out.println("le produit n'est pas désponible");
            System.out.println("si vous avait encour des produits entrer 'oui' ");
            plus = sc.next();
            plus = plus.toLowerCase();
        } while (plus.equals("oui"));
        com.calcule();
        com.afficheCommand();
        listCommand.add(com);
        c.ajoutComm(com);
        Carte cart = null;
        System.out.println("si vous voulez regarder votre remise ");
        int choix;
        do {
            System.out.println("entrer 1 pour 'oui' et 2 pour 'non' .");
            choix = sc.nextInt();
        } while (choix != 1 && choix != 2);
        if (choix == 1) {
            String choixDeSauter = "oui", mots = null;
            do {
                System.out.println("entrer le mote passe de la carte");
                mots = sc.next();
                cart = carte(mots);
                if (cart == null) {
                    System.out.println(
                            "désolé le mote pas et incorrect si c'est vous voulez sauter la carte entre 'oui' ");
                    choixDeSauter = sc.next();
                    choixDeSauter = choixDeSauter.toLowerCase();
                } else {
                    cart.ajoutePrixProduitCarte(com);
                    cart.calcule(com);
                    com.afficheDeMontonCumule();
                }

            } while (cart == null && !choixDeSauter.equals("oui"));

        }
        System.out.println("si vous voulez valider la commande  il faut aller chez le magasin ");

    }

    // valider une commande
    public void validCommand(Command com, int choix) {
        if (rechercheCommandeNonValid(com.getRefComond())) {
            for (String r : com.getListProduit()) {
                Produit p = stock.supprimerReserver(r);
                if (p != null)
                    com.addProduit(p);
            }
            LocalDateTime Date = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            com.setDateValidation(Date.format(myFormatObj));
            System.out.println("la commande est bien validé à " + com.getDateValidation());
            if (choix == 1)
                System.out.println("voilà votre tarif : " + com.getMontonCumul());
            else
                System.out.println("voilà votre tarif : " + com.getMonton());

            listCommandValid.add(com);
            listCommand.remove(com);
        }
    }

    public void valideCommandEnline() {

        System.out.println("entre la référonce de la command :");
        String ref = sc.next();
        Carte car = null;

        System.out.println("vous avait une carte fidélité ");
        int choix;
        String mote = null;
        do {
            System.out.println("entrer 1 pour 'oui' et 2 pour 'non' .");
            choix = sc.nextInt();
        } while (choix != 1 && choix != 2);
        if (choix == 1) {
            String choixDeSauter = null;
            do {
                System.out.println("entrer le mote passe de la carte");
                mote = sc.next();

                if (!recherchCarte(mote)) {
                    System.out.println(
                            "désolé le mote pas et incorrect si c'est vous voulez sauter la carte entre 'oui' ");
                    choixDeSauter = sc.next();
                    choixDeSauter = choixDeSauter.toLowerCase();
                } else {
                    car = carte(mote);
                }

            } while (!recherchCarte(mote) && !choixDeSauter.equals("oui"));

        }
        int trv = 0;
        for (Command com : listCommand) {
            if (com.getRefComond().equals(ref)) {
                trv = 1;
                if (car != null) {
                    System.out.println("si vous voulez utiliser votre carte ");
                    do {
                        System.out.println("entrer 1 pour 'oui' et 2 pour 'non' .");
                        choix = sc.nextInt();
                    } while (choix != 1 && choix != 2);
                    if (choix == 1) {
                        car.valideCumul();
                    } else
                        car.nonValideCumul();
                }
                validCommand(com, choix);
                break;
            }

        }
        if (trv == 0)
            System.out.println("votre commande n'existe pas .");
    }

    // pour le retour
    // gestion de retour
    public void retourCommand() {

        System.out.println("entre la référonce de la command :");
        String ref = sc.next();
        Command com = rechercheCommandeValid(ref);
        if (com == null)
            System.out.println(" la command n'esxist pas dans la liste des commande validé:");
        else {
            System.out.println(" la command existe voilà le tarif qu'il faudra rembourser: " + com.getMonton() + " DA");
            com.romoveProduits();
        }
    }

    // les recherch 

    public void recherchAvecInformation() {
        System.out.println("entre l'ensemble d'information pour la recherch:");
        ArrayList<String> ensembleInformationDeRecherche = new ArrayList<String>();
        int nbrInf;
        do {
            System.out.println("entrer le nombre  des information pour la recherch entre 1 et 5:");
            nbrInf = sc.nextInt();
        } while (nbrInf < 1 || nbrInf > 5);
        sc.nextLine();
        for (int i = 0; i < nbrInf; i++) {
            String car;
            if (i == 0)
                System.out.println("la 1er information ");
            else
                System.out.println("la " + (i + 1) + "eme information ");
            car = sc.nextLine();
            ensembleInformationDeRecherche.add(car);
        }
        stock.recherchAvecInformation(ensembleInformationDeRecherche);

    }

    public boolean recherchCarte(String mote) {

        for (Carte c : carte)
            if (c.getMotePasse().equals(mote))
                return true;
        return false;
    }

    public boolean recherchDisponible(String r) {
        return stock.recherchDisponible(r);
    }

    public void recherch() {
        System.out.println("entre la référonce de produit :");
        String ref = sc.next();
        stock.affichageProduitDisponible(ref);
    }

    public boolean recherchReserver(String r) {
        return stock.recherchReserver(r);
    }

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

    // les affichage

    public void affCarte() {
        System.out.println("entre la référonce de la carte:");
        String mote = sc.next();
        for (Carte c : carte)
            if (c.getMotePasse().equals(mote))
                c.affichage();
        System.out.println("votre mots passe est incorect");
    }

    public void affichageDeTousProduitsDisponible() {
        stock.affichageDeTousProduitsDisponible();
    }

    // récupiration d'une carte

    public Carte carte(String mote) {
        for (Carte c : carte)
            if (c.getMotePasse().equals(mote))
                return c;
        return null;
    }

    // pour le responsable commercial
    public abstract void modifPrix();
    // pour l'agent commercial
    public abstract Carte newCarte();
    // pour la command
    public abstract  void calcule(Command com);


}
