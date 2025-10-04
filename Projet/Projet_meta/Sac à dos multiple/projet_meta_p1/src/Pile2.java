

import java.util.*;
public class Pile2 {
	int[] poidscour ;
	int f ;
	protected int niv;
	ArrayList<ArrayList<objet>> sacs = new ArrayList<>();
	
	public Pile2(int niv, int f, ArrayList<ArrayList<objet>> listObjet,int poidscour[]) {
        this.niv = niv;
        this.f = f;
        this.sacs = listObjet;
        this.poidscour=poidscour;
    }
}


