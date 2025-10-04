package TP6;
import java.util.*;

public class Vue implements InterfaceVueMorpion {

	protected InterfaceModelMorpion model;
	protected InterfaceControleurMorpion controleur;
	public Scanner cs=new Scanner(System.in);
	
	
	
	
	public Vue(InterfaceModelMorpion model, InterfaceControleurMorpion controleur) {
		
		this.model = model;
		this.controleur = controleur;
	}
	public Vue() {
		
	}
	public void activerVueMorpion() {
		this.afficheGrille();
		int a=this.saisirChoisMenu();
		if (a==1) 
			controleur.gererSaisirCoup();
		else 
			controleur.gererFinPartie();
			
	
	}

	@Override
	public void afficheCoupeJoueur() {		
	}

	@Override
	public void afficheGrille() {
		Symbole[][] g =this.model.grille();
		System.out.println("La Grille courante:");
		System.out.println("  0 1 2");
		for(int i=0; i<3;i++) {
			System.out.print(i+"|");
			for(int j=0; j<3;j++) {
				if(g[i][j] == Symbole.CROIX) System.out.print("X|");
				else if(g[i][j] == Symbole.ROND) System.out.print("O|");
					else System.out.print("-|");
			}
			System.out.println("");
		}
		
	}

	@Override
	public int saisirChoisMenu() {
		Joueur j=model.joueurActif();
		if (j==Joueur.JOUEUR_CROIX)
		System.out.println("C'est joueur X de jouer");
		else 
			System.out.println("C'est joueur O de jouer");
		System.out.println("1- entrer un coup");
		System.out.println("2- sortir du  jeu");
		System.out.println("entre votre choix (puis Entree)");
			int a=cs.nextInt();
			cs.close();
			return a ;
		}
		
		
	

	@Override
	public int saisirLigne() {
		System.out.println("saisir la ligne");
		int a=cs.nextInt();
			return a ;
	}
	@Override
	public int saisirColonne() {
System.out.println("saisir la colonne");
			int a=cs.nextInt();
			return a;	
	}

	@Override
	public void afficheErreurCoupPropose() {
		System.out.println("erreur coup propser");
	}

	@Override
	public int afficheFinpartie() {
		System.out.println("Fin partie");
		System.out.println("1-REJOUER");
		System.out.println("2-QUITER");
		System.out.println("entre votre choix (puis Entree)");
		
		int a=cs.nextInt();
		return a;
		
		
	}

}
