package TP6;

public interface InterfaceModelMorpion {
	public Symbole[][] grille();
	public void enregistreCoupPropose(int ligne,int colone);
	public Coup coupPropose();
	public int dimenssionGrille();
	public void joueCoup();
	public boolean coupValide();
	public Joueur joueurActif();
	public void changeJoueur();
	public boolean partieFinie();
	
	
}
