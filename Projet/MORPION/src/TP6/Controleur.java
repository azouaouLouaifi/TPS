package TP6;

public class Controleur implements InterfaceControleurMorpion{
	protected Model model;
	protected InterfaceVueMorpion vue;

	
	public Controleur(Model model) {
		
		this.model = model;
		
	}
	public Controleur() {
		
	}
	

	public void gererSaisirCoup() {
		int l=vue.saisirLigne();
		int c=vue.saisirColonne();
		model.enregistreCoupPropose(l, c);
		boolean v=model.coupValide();
		if (v==true) {
			model.joueCoup();
			
			boolean f=model.partieFinie();
			if(f==true) {
				this.gererFinPartie();
				
			}
			else {
				model.changeJoueur();
				vue.activerVueMorpion();
			
			}
			
		}
		else { 
			vue.afficheErreurCoupPropose();
			vue.activerVueMorpion();
				
		}
		
	}

	@Override
	public void gererFinPartie() {
		vue.afficheGrille();
		int i=vue.afficheFinpartie();
		if(i==1)
			vue.activerVueMorpion();
			model.modifierEtatGrille();
	}

	@Override
	public void associerVue(InterfaceVueMorpion iVueMorpion) {
		this.vue=iVueMorpion;
		
	}

}
