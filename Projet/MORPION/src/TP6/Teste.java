package TP6;



public class Teste {

	public static void main(String[] args) {
		/*Symbole sym=Symbole.CROIX;
		Coup coup=new Coup(1,2,sym);
		Model model=new Model();
		model.enregistreCoupPropose(2, 2);
		Coup coup=model.coupPropose();
		/*if(coup.getLigne()==2)
			System.out.println("oui");
			*/
		/*Symbole[][] s=model.grille;
		if(s[1][1]==Symbole.VIDE)
			System.out.println("OUI");
			/*
		boolean t=model.coupValide();
		if(t==true)
			System.out.println("OUI");
		
		/*	
		model.joueCoup();
		boolean t=model.partieFinie();
		if (t) 
			System.out.println("  ");
		else 
			System.out.println("non");
		Symbole[][] g=model.grille;
		
		if(g[1][2]==Symbole.CROIX)
			System.out.println("oui");*/
		
		
		Model model=new Model();
		Controleur controleur=new Controleur(model);
		Vue v =new Vue(model,controleur);
		
		controleur.associerVue(v);
		
	
	
	
		
		
		
		
		/*boolean t=true;
		
		v.afficheGrille();*/
		/* X JOUE 0,0*/
		/*
		model.enregistreCoupPropose(0, 0);
		model.joueCoup();
		model.changeJoueur();
		v.afficheGrille();
		model.enregistreCoupPropose(1, 0);
		model.joueCoup();
		model.changeJoueur();
		v.afficheGrille();
		model.enregistreCoupPropose(0, 1);
		model.joueCoup();
		model.changeJoueur();
		v.afficheGrille();
		model.enregistreCoupPropose(2, 0);
		model.joueCoup();
		model.changeJoueur();
		v.afficheGrille();
		model.enregistreCoupPropose(0, 2);
		model.joueCoup();
		model.changeJoueur();
		/*1t=model.partieFinie();
		if (t==true)
			v.afficheFinpartie();
		v.afficheGrille();*/
		
		
		
		
		
		
		
	


		
	
		
		
		
		
	}

	}


