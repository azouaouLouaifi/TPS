import java.util.ArrayList;

public class Sac {
	
		int poidsmax;
		int valmin;
		int poidcour;
		int valcour;
		ArrayList<objet> objets =new ArrayList<>();
	
		public Sac(int poidsm ,int valmin,int poidsc,int valcour,ArrayList<objet> objets) {
			this.poidsmax=poidsm;
			this.poidcour=poidsc;
			this.objets=objets;
			this.valcour=valcour;
			this.valmin=valmin;
			
		}

		public int getValmin() {
			return valmin;
		}

		public void setValmin(int valmin) {
			this.valmin = valmin;
		}

		public int getPoidsmax() {
			return poidsmax;
		}

		public void setPoidsmax(int poidsmax) {
			this.poidsmax = poidsmax;
		}

		public int getPoidcour() {
			return poidcour;
		}

		public void setPoidcour(int poidcour) {
			this.poidcour = poidcour;
		}

		public int getValcour() {
			return valcour;
		}

		public void setValcour(int valcour) {
			this.valcour = valcour;
		}

		public ArrayList<objet> getObjets() {
			return objets;
		}

		public void setObjets(ArrayList<objet> objets) {
			this.objets = objets;
		}

		
		
				
			

		
	}


