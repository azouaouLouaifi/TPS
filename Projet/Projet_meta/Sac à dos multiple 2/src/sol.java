import java.util.ArrayList;

public class sol{
		
		int fitness ;
		int poid_total;
		int valeur_total;
		ArrayList<Sac> sacs = new ArrayList<>();
		ArrayList<objet> objets_restant = new ArrayList<>();

		
		
		public sol(int fitness, int poid_total, ArrayList<Sac> listObjet,int valeur_total,ArrayList<objet> objets_restant) {
	        this.fitness = fitness;
	        this.poid_total = poid_total;
	        this.sacs = listObjet;
	        this.valeur_total=valeur_total;
	        this.objets_restant=objets_restant;
	    }
		 public sol() {
		}
		public sol copieProfonde() {
		        return new sol(fitness, poid_total, sacs, valeur_total, objets_restant);
		    }
		
	}