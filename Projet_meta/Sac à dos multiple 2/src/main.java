
import java.util.Random;
import java.util.Scanner;



public class main {

	public static void main(String[] args) {
		
		int nbroject=40;
		objet[] objets = new objet[nbroject];
	
		int nbrsac=15;
		
		Sacàdos[] sacs=new Sacàdos[nbrsac];
		Random random = new Random();

		
		
		
		for(int i=0;i<nbroject;i++) {
			int poid=random.nextInt(10- 1 + 1) + 1;
			int valeur=random.nextInt(11 - 1 + 1) + 1;
			objet objet=new objet(poid, valeur,i);			
			objets[i]=objet;

		}
		
		int [] tab_init_0= new int[nbroject];
		for(int i=0;i<nbroject;i++) {
					
			tab_init_0[i]=0;

		}
		for(int i=0;i<nbrsac;i++) {
			int poid=random.nextInt(40 - 15 + 1) + 15;
			int valeur=random.nextInt(20- 1 + 1) + 1;
		//	System.out.println("num "+i+"  poid "+poid+"   val "+valeur);
			Sacàdos sac=new Sacàdos(poid,valeur ,0, 0, tab_init_0);		
			sacs[i]=sac;

		}
		
		bso bso=new bso();
		GeneticAlgo2 g=new GeneticAlgo2();
		
		String ppath=System.getProperty("user.home") +"/Documents/fichier objets2/objets200_10_10.txt";
		objet[] obj=conv.convertire_tab_obj(ppath);
		
		String ppath2=System.getProperty("user.home") + "/Documents/fichier sacados2/sacsados40_10_10.txt";;
		Sacàdos[]sac=conv.convertir_tab_sac(ppath2, objets.length);
		
		for (int i=1;i<100;i++) {
			g.genetique_algo(i, 40, sac, obj);
			i=i+4;
		}
		

		//bso.generation_solution(sref, 2, 0);
		/*conv c=new conv();
		int maxpoid=10;
		int minpoid=1;
		int maxval=10;
		int minval=2;
		
		String ppath=cree_fichier.cree_fichie_objet(maxpoid, minpoid, maxval, minval,5000);
		objet[] obj=conv.convertire_tab_obj(ppath);
		
		String ppath2=cree_fichier.cree_fichie_sac(maxpoid, minpoid,maxval, minval,10,1000);
		Sacàdos[]sac=conv.convertir_tab_sac(ppath2, objets.length);
		sol sref= bso.generation_sol2(sac,obj);
		
			
			bso.bso1(sac, obj,600 ,500 , 5);
		*/
		//sol sref=bso.generation_sol2(sacs,objets);
		
		//bso.bso1(sacs, objets,3 ,4 , 0);

		

	}

}
