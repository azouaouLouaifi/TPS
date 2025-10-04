package App;

import java.util.Scanner;

import SystemGestion.Client;
import SystemGestion.Magasinier;
import SystemGestion.Shawroom;
import SystemGestion.agentCommercial;
import SystemGestion.responsableCommercial;

public class Application {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Scanner lire = new Scanner(System.in);
        Shawroom shawroom = new Shawroom();
        agentCommercial agCommercial = new agentCommercial();
        responsableCommercial rCommercial = new responsableCommercial();
        Magasinier magasinier = new Magasinier(); Client client = new Client();

        while (true) {
            System.out.println("entre 1 pour l'espace des client");
            System.out.println("entre 2 pour l'espace de le magasin");
            System.out.println("taper 3 pour pour accéder à la fenêtre de la responsable");
            System.out.println("entre 4 pour quitter l'application");

            int choixEspase;
            do {
                choixEspase = sc.nextInt();
            } while (choixEspase <1 && choixEspase > 4 );
            if (choixEspase == 4)
                break;
            if (choixEspase == 1) {
               
                while (true) {
                    System.out.println("taper 1 pour la liste des produits");
                    System.out.println("taper 2 pour visiter votre carte fidémité ");
                    System.out.println("taper 3 pour faire un command ");
                    System.out.println("taper 4 pour recherch d'un produit par ensemble des information ");
                    System.out.println("taper 5 pour quitter l'espace des client");
                    int choixClient;
                    do {
                        choixClient = sc.nextInt();
                    } while (choixClient < 1 || choixClient > 5);
                    if (choixClient == 5)
                        break;
                    switch (choixClient) {
                        case 1:
                            client.ControlAnnuleeCommande();
                            shawroom.affichageDeTousProduitsDisponible();
                            break;
                        case 2:
                            shawroom.affCarte();
                            break;
                        case 3:
                            client.ControlAnnuleeCommande();
                            agCommercial.creationCommandEnLine(client);
                            break;
                        case 4:
                            client.ControlAnnuleeCommande();
                            shawroom.recherchAvecInformation();
                        break;
                    }
                }
            } else {
                if(choixEspase == 2){
                int choixMagasin; 
               
                System.out.println("entre le mote passe ");
                System.out.println("par défaut le mots passe est 'shawroom'");
                String motsPasse = sc.next();
                if (motsPasse.equals(shawroom.getMotePasseShawroom()))
                    while (true) {
                        System.out.println("taper 1 pour ajouté un produit");
                        System.out.println("taper 2 pour ajouté in command ");
                        System.out.println("taper 3 pour validé un commande");
                        System.out.println("taper 4 pour créer une carte de fidélité");
                        System.out.println("taper 5 pour recherch d'un produit par référence ");
                        System.out.println("taper 6 pour recherch d'un produit par ensemble des information ");
                        System.out.println("taper 7 pour retour d'une command");
                        System.out.println("taper 8 pour pour accéder à la fenêtre de la responsable");
                        System.out.println("taper 9 pour quitter l'espace de le magasin");
                        do {
                            choixMagasin = lire.nextInt();
                        } while (choixMagasin < 1 || choixMagasin > 9);
                        if (choixMagasin == 9)
                            break;
                        switch (choixMagasin) {
                            case 1:
                                client.ControlAnnuleeCommande();
                                magasinier.ajoutProduit();
                                break;
                            case 2:
                                client.ControlAnnuleeCommande();
                                agCommercial.creationCommand();
                                break;
                            case 3:
                                client.ControlAnnuleeCommande();
                                agCommercial.valideCommandEnline();
                                break;
                            case 4:
                                agCommercial.newCarte();
                                break;
                            case 5:
                                client.ControlAnnuleeCommande();
                               agCommercial.recherch();
                                break;
                            case 6:
                                client.ControlAnnuleeCommande();
                                agCommercial.recherchAvecInformation();

                                break;
                            case 7:
                                client.ControlAnnuleeCommande();
                                agCommercial.retourCommand();
                                break;
                            
                        }
                    }
                else
                    System.out.println("le mots passe est incorect");

            }else{
                if(choixEspase == 3 ) {
                    System.out.println("entre le mote passe ");
                    System.out.println("par défaut le mots passe est 'responsable'");
                    String motsPasse = sc.next();
                    if (motsPasse.equals(rCommercial.getMotePasseResponsable()))
                        while (true) {
                            System.out.println("taper 1 pour modéfier le prix d'un produit");
                            System.out.println("taper 3 pour modéfier le mots passe de responsable");
                            System.out.println("taper 4 pour quitter l'espace de le responsable");
                            int  responsable ;
                            do {
                                responsable = lire.nextInt();
                            } while (responsable < 1 || responsable > 3);
                                        if (responsable == 3)
                                            break;
                                        switch (responsable) {
                                            case 1:
                                                rCommercial.modifPrix();
                                                break;
                                            case 2:
                                                rCommercial.modifMotsPasseRessponsable();
                                                break;
                                        }
                                    }
                                else
                                    System.out.println("le mots passe est incorect");
                }
        }

        }

        }
        sc.close();
        lire.close();
    }

}


