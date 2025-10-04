


import java.util.*;

public class dfs {


	public static  typeretourbfs rechercheDFS2(objet [] obj,Sacàdos [] sacs) {
		long startTime = System.nanoTime();
		int prof=0;
		int niv_cour=0;
		int solcourante=0;
		int nbrn=0;
		ArrayList <Sacàdos[]> ferme = new ArrayList<>();
		Sacàdos[] sol=new Sacàdos[sacs.length];
		
		Deque<pile> ouvert = new ArrayDeque<>();
		pile element_pile=new pile(niv_cour,sacs);
	       
		ouvert.push(element_pile);
		int nbfaux=0;
		int nbfils=0;
		
		while (!ouvert.isEmpty()) 
		{
			/*Deque<pile> temps = new ArrayDeque<>();
			System.out.println("je suis ouvert");

			while (!ouvert.isEmpty()) {
				pile p=ouvert.pop();
				temps.push(p);
				
				for (int e = 0; e < p.sac.length; e++) {
					System.out.print("sac: "+e+"mes objets---> ");
					
					for (int f= 0; f < p.sac[e].tab.length; f++) {	
						if(p.sac[e].tab[f]==1) {
							
											System.out.print(" oui "+f  );	
					}
						else 
								System.out.print(" non "+f);	
						
					    
				}
				
				System.out.println("");
				}
				
				
			}
			System.out.println("");
			while (!temps.isEmpty()) {
				pile p=temps.pop();
				ouvert.push(p);
				
			}

			
			*/
			
			//System.out.println(it);
			
			//********************************************
			// il me reste de voir avec nivcourant 
			element_pile= ouvert.pop();
			
			
			if(ferme.size()-1==obj.length || nbfaux==sacs.length) {
				nbfaux=0;
				if(element_pile.niv==niv_cour) {
					ferme.remove(ferme.size() - 1);
				}
				else {
					for (int i = 0; i <= niv_cour - element_pile.niv; i++) 
				    {	if(ferme.size()>=2) {
				        ferme.remove(ferme.size() - 1);
				    }}
					    niv_cour = element_pile.niv;
				}
			}
			
			
			
			
			
			//jai rajoute
			
			//si cest pas valide on le mets pas dans ferme 
			//au moin ferme a un seul element 
			int cpe=0;
			if(ferme.size()!=0) {
			
				for(int e=0;e<sacs.length;e++) {
					//ici cest ferme pas elementpile (element_pile.sac[e].poidsmax)
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

        /*        System.out.println("this is ferme ");
                
                for (int i = 0; i < ferme.size(); i++) {
    				System.out.println("element ferme  "+i);
    				for(int r=0;r<sacs.length;r++) {
        				System.out.print(" sac: "+i);

    				for (int j = 0; j < obj.length; j++) {	
    					Sacàdos[] rrr=new Sacàdos[sacs.length];
    					rrr=ferme.get(i);
    					if(rrr[r].tab[j]==1) {
    						
    						System.out.print("   objet:"+j );	
    				}
    					
    				    
    			}
    			
    			
    			}
                }	
             */   
                
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
				    			
				    			ouvert.push(element);
				    			//System.out.println("nbfils"+nbfils);
				    							    		
				    		}
				    			
			    	}
			    }
			}
			}
			//evaluation 
			//nbfaux=nbfilsouvert
			if(ferme.size()-1==obj.length || nbfaux==nbfils) {
								//jsp si cest vrai
				//jai rajoute cette cond
				/*boolean p=false ;
				for(int b=0;b<sacs.length;b++) {
					if(ferme.get(ferme.size() - 1)[b].poidcour>sacs[b].poidsmax)
							p=true;

				}
				if(!p) {*/
					

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
			
			
		       
			
		   
				
				
			//System.out.println("");
		   //System.out.println(" temps �coul� : " + duration + " nanosecondes");
		}
		
		long endTime = System.nanoTime();

		        //Calculer le temps �coul�
		      long duration = endTime - startTime;
		      
			return new typeretourbfs(duration,nbrn,prof,sol,solcourante);
		
		
	/*	if(sol[0]!=null) {
			System.out.print("la valeurtotale : "+solcourante+"   le sac  :");
			for(int i = 0; i < obj.length; i++) {
			System.out.print("obj  : "+i+"   val  :"+obj[i].valeur);}
			System.out.println("");
			for (int e = 0; e < sol.length; e++) {
				System.out.print("sac: "+e+"mes objets---> ");
				
				for (int f= 0; f < sol[e].tab.length; f++) {	
					if(sol[e].tab[f]==1) {
						
										System.out.print(" oui "+f  );	
				}
						
					
				    
			}
			
			System.out.println("");
			}}
			else 	System.out.print( "pas de sol");
		
		*/
	}
	

}

			
			
			
			
			
			
			
			
			
			
			