package TP6;

public class Model implements InterfaceModelMorpion{
    
	protected Joueur joueurActif=Joueur.JOUEUR_CROIX;
	protected Symbole[][] grille= new Symbole[3][3]  ;
	protected Coup coup=new Coup();
	private int i=0;
	
		
	

	public Symbole[][] grille() {
		if (i==0) {
			for(int i=0; i<3;i++)
				for(int j=0; j<3;j++) this.grille[i][j]=Symbole.VIDE;
			i=1;
			
		}
		
		return this.grille;
	}

	@Override
	public void enregistreCoupPropose(int ligne, int colone) {
		
		if (this.joueurActif==Joueur.JOUEUR_CROIX) {
			this.coup=new Coup(ligne,colone,Symbole.CROIX);
		}
		else 	
			this.coup=new Coup(ligne,colone,Symbole.ROND);

	}

	public Coup coupPropose() {
		
		return this.coup;
	}

	@Override
	public int dimenssionGrille() {
		return 0;
	}

	@Override
	public void joueCoup() {
		
		
		grille[coup.getLigne()][coup.getColone()]=coup.getSymbole();
	}

	@Override
	public boolean coupValide() {
		Coup c=this.coupPropose();
		Symbole [][] g=grille();
		if (g[c.getLigne()][c.getColone()]==Symbole.VIDE)
			return true;
		else
		    return false;
	}

	public void modifierEtatGrille() {
		for(int i=0; i<3;i++)
			for(int j=0; j<3;j++) this.grille[i][j]=Symbole.VIDE;
		
		}

	@Override
	public Joueur joueurActif() {
		return this.joueurActif;
	}

	
	public void changeJoueur() {
	 Joueur j =joueurActif();
	 if (j==Joueur.JOUEUR_CROIX)
		 this.joueurActif=Joueur.JOUEUR_ROND;
	 else 
		 this.joueurActif=Joueur.JOUEUR_CROIX;
	}

	@Override

	public boolean partieFinie() {
		int i,j=0;
		j=0;
		i=0;
		boolean fin=false;
		
		Symbole etat=this.coup.getSymbole();
		Coup c=this.coupPropose();
		Symbole [][] g=grille();
		
		
		if (g[c.getLigne()][0]==etat && g[c.getLigne()][1]==etat && g[c.getLigne()][2]==etat)
			 return true;
		if(g[0][c.getColone()]==etat && g[1][c.getColone()]==etat && g[2][c.getColone()]==etat)
			return true;
		if(g[0][0]==etat && g[1][1]==etat && g[2][2]==etat )
			return true;
		if (g[2][0]==etat && g[1][1]==etat && g[0][2]==etat)
				return true;
		
			fin=true;
			while(i<3 && fin==true) {
				while(j<3 && fin==true) {
					if (this.grille[i][j]==Symbole.VIDE) 
						fin=false;
					j++;
				
			}
				i++;
		}
		return fin;
		

	}
	/*
	public boolean partieFinie() {
	
	boolean b=true;
	// recherch à la colonne du coup
	
	for(int i=0; i<3;i++) if( grille[i][coup.getColone()] == Symbole.VIDE) b=false;
	if(b) {
		boolean bb=true;
		for(int i=0; i<2;i++)
			for(int j=1; j<3;j++) if( grille[i][coup.getColone()] != grille[j][coup.getColone()]) bb=false;
		if(bb)return true;
	} 
	// recherch à la ligne du coup
	 b=true;
		for(int i=0; i<3;i++) if( grille[coup.getLigne()][i] == Symbole.VIDE) b=false;
		if(b) {
			boolean bb=true;
			for(int i=0; i<2;i++)
				for(int j=1; j<3;j++) if( grille[coup.getLigne()][i] != grille[coup.getLigne()][j]) bb=false;
			if(bb)return true;
		}
	// recherch à la 1er diagonale 
	b=true;
	for(int i=0; i<3;i++) if( grille[i][i] == Symbole.VIDE) b=false;
	if(b) {
		boolean bb=true;
		for(int i=0; i<2;i++)
			for(int j=1; j<3;j++) if( grille[i][i] != grille[j][j]) bb=false;
		if(bb)return true;
	}
	// recherch à la 2er diagonale 
	b=true;
	for(int i=0; i<3;i++) if(grille[i][2-i] == Symbole.VIDE) b=false;
	if(b) {
		boolean bb=true;
		for(int i=0; i<2;i++)
			for(int j=1; j<3;j++) if( grille[i][2-i] != grille[j][2-j]) bb=false;
		if(bb)return true;
	}
	// recherch si la grille est complèle
	b=false;
	int i=0;
	int j=0;
	while(!b && i<3) {
		 j=0;
		while(!b && j<3) {
			if(grille[i][j] == Symbole.VIDE) b=true;
		}
	}
	return !b ; 
}
*/

	
}

	

	

