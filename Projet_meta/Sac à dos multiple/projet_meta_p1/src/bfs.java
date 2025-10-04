

import java.util.*;

public class bfs {


	public static  typeretourbfs recherchebFS(objet [] obj,Sacàdos [] sacs) {
		long startTime = System.nanoTime();
		int prof=0;
		int niv_cour=0;
		int solcourante=0;
		int nbrn=0;
		ArrayList <Sacàdos[]> ferme = new ArrayList<>();
		Sacàdos[] sol=new Sacàdos[sacs.length];
		
		Queue<pile> ouvert = new LinkedList<>();
		pile element_pile=new pile(niv_cour,sacs);
	       
		ouvert.offer(element_pile);
		int nbfaux=0;
		int nbfils=1;
		
		while (!ouvert.isEmpty()) 
		{
		
			element_pile= ouvert.poll();
			
			
			if(ferme.size()-1==obj.length || nbfaux==nbfils) {
				nbfaux=0;
				if(element_pile.niv==niv_cour) {
					ferme.remove(ferme.size() - 1);
				}
			else {
					for (int i = 0; i <= niv_cour - element_pile.niv+2; i++) 
				    {	if(ferme.size()>=2) {
				        ferme.remove(ferme.size() - 1);
				    }}
					    niv_cour = element_pile.niv;
				
			
			}
			
			}
			
			
			
			int cpe=0;
			if(ferme.size()!=0) {
			
				for(int e=0;e<sacs.length;e++) {
				if(element_pile.sac[e].poidcour>element_pile.sac[e].poidsmax) {
		          	  cpe++;
	            }  
				}
			}
			if(cpe>=1) {
				nbfaux++;
			}
			
			else {
				ferme.add(element_pile.sac);
				nbrn++;
      
                
				if(ferme.size()-1 <obj.length) { 
					//c pas ici 
					niv_cour++;
					
					if(niv_cour>prof)  prof=niv_cour;
					nbfils=0;
			    for(int i=0;i<obj.length;i++) {
			    	
			    		int k=0;
			    		
			    		boolean a=true;
			    		while(k<ferme.size() && a==true) {
			    			int cpt=0;
			    			//Sacàdos [] s=Arrays.copyOf(ferme.get(k),ferme.get(k).length);
			    		
			    		for(int e=0;e<sacs.length;e++) {
			    		if(ferme.get(k)[e].tab[i]!=1) {
			    		cpt++;
			    		
			    		}
			    		
			    		}
			    		if (cpt!=sacs.length) {
			    			a=false;
			    		}
			    		k++;
			    		}
			    	
				    		if(a==true) {
				    			
				    			for(int j=0;j<sacs.length;j++) {
				    				nbfils++;
			
				    				Sacàdos[] sac = new Sacàdos[sacs.length];

					                 // Obtenez les sacs à dos actuels de ferme
					                 Sacàdos[] sacsCourants = ferme.get(ferme.size() - 1);

					                 // Copiez les valeurs des sacs à dos actuels dans le nouveau tableau sac
					                 for (int p = 0; p < sacsCourants.length; p++) {
					                     sac[p] = new Sacàdos(sacsCourants[p].poidsmax,sacsCourants[p].poidcour,sacsCourants[p].valcour,Arrays.copyOf(sacsCourants[p].tab, sacsCourants[p].tab.length)); // Créez une nouvelle instance de Sacàdos
					                     
					                     
					                 }
				    			sac[j].tab[i]=1;
				    			
				    			//jsp si = ou +=
				    			sac[j].poidcour+=obj[i].poids;
				    			sac[j].valcour+=obj[i].valeur;
				    			//pile element=new pile(niv_cour+1,sac);
				    			pile element=new pile(niv_cour,sac);
				    			
				    			ouvert.offer(element);
				    			

				    							    		
				    		}
				    			
			    	}
			    }
			}
			}
			
			if(ferme.size()-1==obj.length || nbfaux==nbfils) {
								
					

				if(nbfaux==nbfils) {
				niv_cour--;}
				int y=0;
				for(int b=0;b<sacs.length;b++) {
					y=y + ferme.get(ferme.size() - 1)[b].valcour;
				}	
				if(solcourante<y)						
					{							
						sol=Arrays.copyOf(ferme.get(ferme.size()-1),ferme.get(ferme.size()-1).length);						
						solcourante=y;

					}
				
				
			
			}
			
			
		       
			
		   
				
				
		
		}
		
		long endTime = System.nanoTime();

		        //Calculer le temps �coul�
		      long duration = endTime - startTime;
				
				double tempsEcouleEnSecondes = duration / 1_000_000_000.0; // Conversion de nanosecondes en secondes

				// Afficher le temps écoulé en secondes
				System.out.println("Temps écoulé en secondes : " + tempsEcouleEnSecondes);
				System.out.print("noeud : "+nbrn);
				System.out.println("profendeur"+prof);
				System.out.print("la valeurtotale : "+solcourante);
				System.out.println("");
				
				/*	
					for (int e = 0; e < sol.length; e++) {
						System.out.print("sac: "+e+"mes objets---> ");
						
						for (int f= 0; f < sol[e].tab.length; f++) {	
							if(sol[e].tab[f]==1) {
								
												System.out.print(" oui "+f  );	
						}
								
							
						}  
					}*/
					System.out.println("");
			return new typeretourbfs(duration,nbrn,prof,sol,solcourante);
		

	}
	

}


			
			
			
			
			
			
			
			
			
			
			