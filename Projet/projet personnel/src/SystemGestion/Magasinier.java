package SystemGestion;

import java.util.ArrayList;

import SystemComonde.Command;
import SystemProduit.*;

public class Magasinier extends Shawroom implements GStock{

    public Magasinier (){}

   

    @Override
    public void valideCommand(Command com, int choix) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void retourCommande() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void ajoutProduit() {

            System.out.println("vous voulez ajouté un produit de catégorie:");
            System.out.println("1) Mobile");
            System.out.println("2) informatique");
            System.out.println("3) Electronique");
            System.out.println("4) Electrominage");
            System.out.println("5) kits solaire");
            int choixCategorie = 0, choixProduit = 0;
            do {
                choixCategorie = sc.nextInt();
            } while (choixCategorie < 1 || choixCategorie > 5);
    
            switch (choixCategorie) {
                case 1:
                    System.out.println("les produit de la catégorie mobile ");
                    System.out.println("1) tablette tactile");
                    System.out.println("2) téléphone mobile");
                    System.out.println("3)  objet connecté");
                    do {
                        choixProduit = sc.nextInt();
                    } while (choixProduit < 1 || choixProduit > 3);
                    break;
                case 2:
                    System.out.println("les produit de la catégorie d'informatique");
                    System.out.println("1) pc bureau");
                    System.out.println("2) pc portable");
                    System.out.println("3) pc all in one");
                    System.out.println("4) serveur");
                    System.out.println("5) station de travail");
                    System.out.println("6) ultrabook");
                    System.out.println("7) périphériques et accessoires");
                    do {
                        choixProduit = sc.nextInt();
                    } while (choixProduit < 1 || choixProduit > 7);
                    break;
                case 3:
                    System.out.println("les produit de la catégorie electronique");
                    System.out.println("1) téléviseurs");
                    System.out.println("2) démodulateur");
                    System.out.println("3) audio/vidéo");
                    do {
                        choixProduit = sc.nextInt();
                    } while (choixProduit < 1 || choixProduit > 3);
                    break;
                case 4:
                    System.out.println("les produit de la catégorie d'electrominager");
                    System.out.println("1) Climatiseurs");
                    System.out.println("2) équipements de cuisine (cuisinière, hotte de cuisine, plaque de cuisson, etc)");
                    System.out.println("3) lave linge");
                    System.out.println("4) réfrigérateurs");
                    System.out.println("5) petit électroménager (aspirateur et ventilateur)");
                    System.out.println("6) congélateur");
                    System.out.println("7) chaud et froid");
                    do {
                        choixProduit = sc.nextInt();
                    } while (choixProduit < 1 || choixProduit > 7);
                    break;
                case 5:
                    System.out.println("les produit de la catégorie kits solaire");
                    System.out.println("1) module photovoltaïque");
                    System.out.println("2) batteries solaires");
                    System.out.println("3) régulateurs de charge solaires");
                    System.out.println("4) les onduleurs solaires");
                    do {
                        choixProduit = sc.nextInt();
                    } while (choixProduit < 1 || choixProduit > 4);
                    break;
            }
    
            System.out.println("entre le nom de produit  :");
            String nom = sc.next();
            System.out.println("entre la référonce de produit :");
            String ref = sc.next();
            System.out.println("entre l'ensemble Caracteristiques de le produit:");
            ArrayList<String> ensembleCaracteristiques = new ArrayList<String>();
            int nbrCar;
            do {
                System.out.println("entrer le nombre des Caracteristiques de le produit entre 1 et 20:");
                nbrCar = sc.nextInt();
            } while (nbrCar < 1 || nbrCar > 20);
            sc.nextLine();
            for (int i = 0; i < nbrCar; i++) {
                String car;
                if (i == 0)
                    System.out.println("la 1er caractéristique ");
                else
                    System.out.println("la " + (i + 1) + "eme caractéristique ");
                car = sc.nextLine();
                ensembleCaracteristiques.add(car);
            }
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
            String des = null;
            if (choixCategorie != 1) {
                System.out.println("entre la descriptif de produit:");
                des = sc.nextLine();
    
            }
            double prix = 0;
            try {
                System.out.println("entre le prix de produit  :");
                prix = (double) sc.nextDouble();
            } catch (Exception e) {
                System.out.println("il y a un problem dans la licteur d'un réel " + e.toString());
            }
    
            switch (choixCategorie) {
                case 1:
                    switch (choixProduit) {
                        case 1:
                            TabTactile pro1 = new TabTactile(nom, ref, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro1);
                            break;
                        case 2:
                            Tel pro2 = new Tel(nom, ref, ensembleCaracteristiques, ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro2);
                            break;
                        case 3:
                            ObjetConnecte pro3 = new ObjetConnecte(nom, ref, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro3);
                            break;
                    }
                    break;
                case 2:
                    switch (choixProduit) {
                        case 1:
                            PcBureau pro1 = new PcBureau(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro1);
                            break;
                        case 2:
                            PcPortable pro2 = new PcPortable(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro2);
                            break;
                        case 3:
                            PcAllinOne pro3 = new PcAllinOne(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro3);
    
                            break;
                        case 4:
                        Serveur pro4 = new Serveur(nom, ref, des, ensembleCaracteristiques,
                        ensembleInformationDeRecherche, prix);
                stock.ajouteNewProduit(pro4);
                break;
                        case 5:
                            StationDeTravil pro5 = new StationDeTravil(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro5);
                            break;
                        case 6:
                            Ultrabook pro6 = new Ultrabook(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro6);
                            break;
                        case 7:
                            PeripheriqueEtAccessoire pro7 = new PeripheriqueEtAccessoire(nom, ref, des,
                                    ensembleCaracteristiques, ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro7);
                            break;
    
                    }
                    break;
                case 3:
                    switch (choixProduit) {
                        case 1:
                            Televiseurs pro1 = new Televiseurs(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro1);
                            break;
                        case 2:
                            Demodulateur pro2 = new Demodulateur(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro2);
                            break;
                        case 3:
                            AudioVideo pro3 = new AudioVideo(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro3);
    
                            break;
                    }
                    break;
                case 4:
                    switch (choixProduit) {
                        case 1:
                            Climatiseurs pro1 = new Climatiseurs(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro1);
                            break;
                        case 2:
                            EquipementsDeCuisine pro2 = new EquipementsDeCuisine(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro2);
                            break;
                        case 3:
                            LaveLinge pro3 = new LaveLinge(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro3);
    
                            break;
                        case 4:
                            Refrigerateurs pro4 = new Refrigerateurs(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro4);
                            break;
                        case 5:
                            PetitElectromenager pro5 = new PetitElectromenager(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro5);
                            break;
                        case 6:
                            Congelateur pro6 = new Congelateur(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro6);
                            break;
                        case 7:
                            ChaudEtFroid pro7 = new ChaudEtFroid(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro7);
                            break;
                    }
                    break;
                case 5:
                    switch (choixProduit) {
                        case 1:
                            ModulePhotovoltaique pro1 = new ModulePhotovoltaique(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro1);
                            break;
                        case 2:
                            BatteriesSolaires pro2 = new BatteriesSolaires(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro2);
                            break;
                        case 3:
                            ReglateursDeCharge pro3 = new ReglateursDeCharge(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro3);
    
                            break;
                        case 4:
                            LesOnduleurSolaire pro4 = new LesOnduleurSolaire(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro4);
                            break;
                        case 5:
                            PetitElectromenager pro5 = new PetitElectromenager(nom, ref, des, ensembleCaracteristiques,
                                    ensembleInformationDeRecherche, prix);
                            stock.ajouteNewProduit(pro5);
                            break;
                    }
                    break;
            }
        }


    @Override
    public void modifPrix() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void calcule(Command com) {
        // TODO Auto-generated method stub
        
    }
    
}
