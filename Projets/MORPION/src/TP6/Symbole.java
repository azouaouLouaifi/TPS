package TP6;

public enum Symbole {
	VIDE,CROIX,ROND;
	private String libelle;

	private Symbole(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	private Symbole() {
	}
	
}
