package TP6;

public class Coup {

	private int ligne;
	private int colone;
	private Symbole symbole;
	public Coup(int ligne, int colone, Symbole symbole) {
		
		this.ligne = ligne;
		this.colone = colone;
		this.symbole = symbole;
	}
	public Coup() {
		// TODO Auto-generated constructor stub
	}
	public int getLigne() {
		return ligne;
	}
	public int getColone() {
		return colone;
	}
	public Symbole getSymbole() {
		return symbole;
	}
	public String toString() {
		return ("la ligne:"+this.ligne+"la col:"+this.colone+"le sym:"+this.symbole);
		
	}
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}
	public void setColone(int colone) {
		this.colone = colone;
	}
	public void setSymbole(Symbole symbole) {
		this.symbole = symbole;
	}
	
	

}
