import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;




public class GeneticAlgo {

	
	
	 public sol generation_sol2(Sacàdos[] sacs, objet[] objets)  {
			
			Random random = new Random();
			ArrayList<Sac> sac1=new ArrayList<>();
			ArrayList<objet> objets_rest = new ArrayList<>();

			boolean con=true;
			while(con) {
			// liste objet
				
			ArrayList<objet> objet=new ArrayList<>();
			for(int i=0;i<objets.length;i++) {
				objet.add(objets[i]);
			}
			// liste sac
			ArrayList<Sac> sac=new ArrayList<>();			
			for(int i=0;i<sacs.length;i++) {
				ArrayList<objet> obje=new ArrayList<>();
				sac.add(new Sac(sacs[i].poidsmax,sacs[i].valmin,sacs[i].poidcour,sacs[i].valcour,obje));
			}
			
			//ArrayList<sol>  sols= new ArrayList<>();
			//liste index
			ArrayList<Integer> index=new ArrayList<>();
			for(int i=0;i<sacs.length;i++) {
				index.add(i);
			}
			
			ArrayList<Integer> index2=new ArrayList<>();
			for(int i=0;i<sacs.length;i++) {
				index2.add(i);
			}
			
			// objet restant
			ArrayList<objet> objet_res=new ArrayList<>();
			boolean arr=false;
				while(!objet.isEmpty() && !arr)
				{
					int k =  random.nextInt(objet.size());
					if(k == -1) 
								k = 0;
					

					boolean ajouter=false;
					while(!index.isEmpty() && !ajouter) {
						int k1 =  random.nextInt(index.size());
						if(k1 == -1) 
							k1 = 0;
						

						if(objet.get(k).poids+sac.get(k1).poidcour<=sac.get(index.get(k1)).poidsmax) {
							ajouter=true;
							sac.get(index.get(k1)).poidcour=objet.get(k).poids+sac.get(index.get(k1)).poidcour;
							sac.get(index.get(k1)).valcour=objet.get(k).valeur+sac.get(index.get(k1)).valcour;
							sac.get(index.get(k1)).objets.add(objet.get(k));
							objet.remove(k);
							
							if(sac.get(index.get(k1)).valcour>=sac.get(index.get(k1)).valmin)
								{
								
								index2.remove(k1);
								
								
								}
					
						}
						else {
							index.remove(k1);
						}
	
						
						}
					if(!ajouter) {
					objet ob=objet.remove(k);
					objet_res.add(ob);
					}
					index.clear();
					index.addAll(index2);
					
					if(index2.isEmpty()) arr=true;
					
				}
				
				
				// si la objet est vide verifer si val est verifier 
				
					int i=0;	
					boolean ver=true;			
					while(i<sac.size() && ver) {
						if(sac.get(i).valcour<sac.get(i).valmin) {
						ver=false;
						}
						i++;
				
					}
					if (ver) {
						con=false;
						sac1.addAll(sac);
						objets_rest.addAll(objet_res);
						objets_rest.addAll(objet);
				
				}
				
				
				
			
		   }
			
			
			// calculer le poit et val totale de la solution
			int poid=0;
					 int val=0;
			 for(int i1=0;i1<sac1.size();i1++) {
					 poid=sac1.get(i1).poidcour+poid;
					 val=val+sac1.get(i1).valcour;
					
				}
			 

			/*   for(int i1=0;i1<sac1.size();i1++) {
					System.out.print("sac"+i1);
					 System.out.println("poid :"+sac1.get(i1).poidcour+"  val "+sac1.get(i1).valcour);
					 for(int i11=0;i11<sac1.get(i1).objets.size();i11++) {
					
						 System.out.print("num   "+sac1.get(i1).objets.get(i11).num+"   poid :"+sac1.get(i1).objets.get(i11).poids+"  val "+sac1.get(i1).objets.get(i11).valeur);
						 
					 }
					System.out.println("");
				}*/
			
				
			return new sol(0, poid, sac1, val,objets_rest);
	 }
	 public void fitness (sol [] pop) {
			for (int i=0;i<pop.length;i++) {
				int s=calcul(pop[i]);
				pop[i].fitness=s;
			}
			//verifierfitness(pop);
		}

		public int calcul(sol soll) {
			int v=0;
			int k;
			for(k=0;k<soll.sacs.size()-1;k++) {
				v=v+soll.sacs.get(k).valcour;
				}
			
			

			return v;
		}
		
		
		
		
		
	/*quand je vais trier une population pour prendre meilleure/ 
	 * je prends par rapport a v :
	 * apres le dernier que je vais pas prendre et dernier que je vais prendre
	 * 
	 *  comparer les deux par rapport v si = alors comparer poids
	 *   apres inverser les deux si le cas  
	 */
 
	 public sol[] verifierfitness(sol [] pop, int l) {
           if(pop[l].fitness==pop[l+1].fitness) {
        	   if(pop[l].poid_total>pop[l+1].poid_total) {
        		   sol temp =pop[l];
        		   pop[l]=pop[l+1];
        		   pop[l+1]=temp;
        	   }
           }
           return pop ;

		 
	 }
	 void entasser(sol [] arr, int n, int i) {
		    int largest = i; 
		    int left = 2 * i + 1; 
		    int right = 2 * i + 2; 

		    if (left < n && arr[left].fitness > arr[largest].fitness)
		        largest = left;

		    if (right < n && arr[right].fitness > arr[largest].fitness)
		        largest = right;

		    if (largest != i) {
		        sol temp = arr[i];
		        arr[i] = arr[largest];
		        arr[largest] = temp;

		        entasser(arr, n, largest);
		    }
		}

		public  void trie(sol [] arr) {
		    int n = arr.length;

		    // Construit un tas max
		    for (int i = n / 2 - 1; i >= 0; i--)
		    	entasser(arr, n, i);

		    // Extrait les éléments un par un du tas
		    for (int i = n - 1; i > 0; i--) {
		        // Échange la racine actuelle avec le dernier élément non trié
		        sol temp = arr[0];
		        arr[0] = arr[i];
		        arr[i] = temp;
		        entasser(arr, i, 0);
		    }
		}


		public int compterpoidsc(ArrayList <objet> s) {
			
        		int totalWeight=0;
	    		
				for (objet obj : s) {
		            totalWeight += obj.poids;
		        }
				
			return totalWeight;
		}
		public int comptervalc(ArrayList <objet> s) {
			
    		int totalWeight=0;
    		
			for (objet obj : s) {
	            totalWeight += obj.valeur;
	        }
			
		return totalWeight;
	}
		
	 
	 
		
				
		 public void  ajouter_obj(Sac sac,objet o) {
			 sac.objets.add(o);
			 sac.poidcour= sac.poidcour+o.poids;
			sac.valcour= sac.valcour+o.valeur;
			 
		 }
		 public void  sup_obj(Sac sac,objet o) {
			 sac.objets.remove(o);
			 sac.poidcour= sac.poidcour-o.poids;
			sac.valcour= sac.valcour-o.valeur;
			 
		 }
		 
		 
		 public void muter(sol sol,int p,int k ) {
			Random random = new Random();

			 int p1=random.nextInt(sol.sacs.get(p).objets.size())-1;
			 
			int p2=random.nextInt(sol.sacs.get(k).objets.size())-1;
			if(p1==-1) {
				p1=0;
			}
			if(p2==-1) {
				p2=0;
			}
			 
			 objet ob1=sol.sacs.get(p).objets.get(p1);
			 objet ob2=sol.sacs.get(k).objets.get(p2);
			 
			 
			
			 
			 // supp ob de sac1
			 sol.sacs.get(p).objets.remove(p1);
			 sol.sacs.get(p).poidcour= sol.sacs.get(p).poidcour-ob1.poids;
			 sol.sacs.get(p).valcour= sol.sacs.get(p).valcour-ob1.valeur;
			 
			 // supp ob de sac2

			 sol.sacs.get(k).objets.remove(p2);
			 sol.sacs.get(k).poidcour= sol.sacs.get(k).poidcour-ob2.poids;
			 sol.sacs.get(k).valcour= sol.sacs.get(k).valcour-ob2.valeur;
			 
			 // ajouter ob2 dans sac 
			 if(ob2.poids+sol.sacs.get(p).poidcour<=sol.sacs.get(p).poidsmax 
					 && ob2.valeur+sol.sacs.get(p).valcour>=sol.sacs.get(p).valmin) {
				 
				 ajouter_obj(sol.sacs.get(p), ob2);
				 // ajouter ob1 dans sac2 
				 if(ob1.poids+sol.sacs.get(k).poidcour<=sol.sacs.get(k).poidsmax 
						 && ob1.valeur+sol.sacs.get(k).valcour>=sol.sacs.get(k).valmin) {
				
					 ajouter_obj(sol.sacs.get(k), ob1);
	 
				 }
				 // obj1 ne rentre pas dans le sac2
				 else {
					 

					// recuperer la liste 
					 ArrayList<objet> t = new ArrayList<>();
					 for(int i=0;i<sol.objets_restant.size();i++) {
						 t.add(sol.objets_restant.get(i));
						}
					 boolean ajouter1=false;
					 // recherche aleatoire d'un objet a ajouter dans sac2 si on le trouve en ajoute
					 while(!t.isEmpty() && !ajouter1) {
						 int r=random.nextInt(t.size());
					
						 if(t.get(r).poids+sol.sacs.get(k).poidcour<=sol.sacs.get(k).poidsmax 
								 && t.get(r).valeur+sol.sacs.get(k).valcour>=sol.sacs.get(k).valmin) {
							 
							 ajouter_obj(sol.sacs.get(k), t.get(r));
							 ajouter1=true;
							 sol.objets_restant.remove(t.get(r));
							 
						 }
						 else t.remove(r);
						
					 	}
					 // si on la trouver
					 if(ajouter1) {
						 sol.objets_restant.add(ob1);
					 }
					 else {
						 sup_obj(sol.sacs.get(p), ob2);
						 ajouter_obj(sol.sacs.get(k), ob2);
						 
						 // rechercher aleatoirement un objet pour sac1
						 
						 
						// recuperer la liste 
						 ArrayList<objet> t1 = new ArrayList<>();
						 for(int i=0;i<sol.objets_restant.size();i++) {
							 t1.add(sol.objets_restant.get(i));
							}
						 boolean ajouter11=false;
						 while(!t1.isEmpty() && !ajouter11) {
							 int r=random.nextInt(t1.size());
						
							 if(t1.get(r).poids+sol.sacs.get(p).poidcour<=sol.sacs.get(p).poidsmax 
									 && t1.get(r).valeur+sol.sacs.get(p).valcour>=sol.sacs.get(p).valmin) {
								 
								 ajouter_obj(sol.sacs.get(p), t1.get(r));
								 ajouter11=true;
								 sol.objets_restant.remove(t1.get(r));
								 
							 }
							 else t1.remove(r);
							
						 	}
						 
							 if(ajouter11) {
								 sol.objets_restant.add(ob1);
							 }
							 else {
								 ajouter_obj(sol.sacs.get(p), ob1);
							 }

					 	} 
				 	}
				 
			 }
			 
			 
			 // ob 2 ne rentre pas dans le sac 1
			 
			 else {
				 // rechercher aleatoirement un objet
				 
				 ArrayList<objet> t1 = new ArrayList<>();
				 for(int i=0;i<sol.objets_restant.size();i++) {
					 t1.add(sol.objets_restant.get(i));
					}
				 boolean ajouter11=false;
				 while(!t1.isEmpty() && !ajouter11) {
					 int r=random.nextInt(t1.size());
				
					 if(t1.get(r).poids+sol.sacs.get(p).poidcour<=sol.sacs.get(p).poidsmax 
							 && t1.get(r).valeur+sol.sacs.get(p).valcour>=sol.sacs.get(p).valmin) {
						 
						 ajouter_obj(sol.sacs.get(p), t1.get(r));
						 ajouter11=true;
						 sol.objets_restant.remove(t1.get(r));
						 
					 }
					 else t1.remove(r);
					
				 	}
				 if(!ajouter11) {
					 ajouter_obj(sol.sacs.get(p), ob1);
					 
					 // recherche aleatoire pour sac2
					// recuperer la liste 
					 ArrayList<objet> t = new ArrayList<>();
					 for(int i=0;i<sol.objets_restant.size();i++) {
						 t.add(sol.objets_restant.get(i));
						}
					 boolean ajouter1=false;
					 // recherche aleatoire d'un objet a ajouter dans sac2 si on le trouve en ajoute
					 while(!t.isEmpty() && !ajouter1) {
						 int r=random.nextInt(t.size());
					
						 if(t.get(r).poids+sol.sacs.get(k).poidcour<=sol.sacs.get(k).poidsmax 
								 && t.get(r).valeur+sol.sacs.get(k).valcour>=sol.sacs.get(k).valmin) {
							 
							 ajouter_obj(sol.sacs.get(k), t.get(r));
							 ajouter1=true;
							 sol.objets_restant.remove(t.get(r));
							 
						 }
						 else t.remove(r);
						
					 	}
					 // si on la trouver
					 if(ajouter1) {
						 sol.objets_restant.add(ob2);
					 }
					 else {
						 ajouter_obj(sol.sacs.get(k), ob2);

					 }

				 }
				 // on a ajouter un objet dans sac1 donc ob1 libre 
				 else {
					 
					 if(ob1.poids+sol.sacs.get(k).poidcour<=sol.sacs.get(k).poidsmax 
							 && ob1.valeur+sol.sacs.get(k).valcour>=sol.sacs.get(k).valmin) {
					
						 ajouter_obj(sol.sacs.get(k), ob1);
						 sol.objets_restant.add(ob2);
		 
					 }
					 // obj1 ne rentre pas dans le sac2
					 else {
						 
						 sol.objets_restant.add(ob1);
						// recuperer la liste 
						 ArrayList<objet> t = new ArrayList<>();
						 for(int i=0;i<sol.objets_restant.size();i++) {
							 t.add(sol.objets_restant.get(i));
							}
						 boolean ajouter1=false;
						 // recherche aleatoire d'un objet a ajouter dans sac2 si on le trouve en ajoute
						 while(!t.isEmpty() && !ajouter1) {
							 int r=random.nextInt(t.size());
						
							 if(t.get(r).poids+sol.sacs.get(k).poidcour<=sol.sacs.get(k).poidsmax 
									 && t.get(r).valeur+sol.sacs.get(k).valcour>=sol.sacs.get(k).valmin) {
								 
								 ajouter_obj(sol.sacs.get(k), t.get(r));
								 ajouter1=true;
								 sol.objets_restant.remove(t.get(r));
								 
							 }
							 else t.remove(r);
							
						 	}
						 // si on la trouver
						 if(ajouter1) {
							 sol.objets_restant.add(ob2);
						 }
						 else 
							 ajouter_obj(sol.sacs.get(k), ob2);

					 
				 }
				 
			 }
			 }
		 }
			
		 
		 
		 public sol[] mutation(sol[] croisement,float rate) {
			 int nbrmutation=(int)(rate*croisement.length);
			 
			 sol [] mutations=new sol[nbrmutation];
			 int nbr=0;
			 while(nbr<nbrmutation) {
					
				 
				 	ArrayList<Sac> sacs = new ArrayList<>();
					for(int i=0;i<croisement[nbr].sacs.size();i++) {
						ArrayList<objet> objets= new ArrayList<>();
						for(int i1=0;i1<croisement[nbr].sacs.get(i).objets.size();i1++) {
							 objets.add(croisement[nbr].sacs.get(i).objets.get(i1));						
						}
						sacs.add(new Sac(croisement[nbr].sacs.get(i).poidsmax , croisement[nbr].sacs.get(i).valmin, croisement[nbr].sacs.get(i).poidcour, croisement[nbr].sacs.get(i).valcour, objets));
					}
					
					sol nv =  new sol(croisement[nbr].fitness, croisement[nbr].poid_total, sacs, croisement[nbr].valeur_total, croisement[nbr].objets_restant);
					
					Random random = new Random();
					int p2;
					int p1;
					do {
					  p1=random.nextInt(nv.sacs.size())-1;
					 
					 p2=random.nextInt(nv.sacs.size())-1;
					 if(p1==-1) 
							p1=0;
						
						if(p2==-1) 
							p2=0;
					} while(p1==p2);
						
				      
						muter(nv, p1, p2);
					
						mutations[nbr]=nv;
						nbr++;
			 }
			 
				
			 
			return mutations;
			 
			 
		 }
	 /* 1tous ok =>ok
	    2si un sac nest pas ok si tous existe => alors on regarde sacs restants et on va placer 
	    et voir si ca mene a sol valide sinn ca mene pas on change de sac .
	    3si pas tous existe on garde qui nexiste pas on retire de objets restant et on continu 
	    a remplire et on va mener a sol valide si ca mene pas on change de sac si tous les deux 
	    sacs existe on regard sacs restant de chque sac si ca mene sol valide ok sinn on change les 2 sacs 
	     les deux n'ont pas dobjets restant beh on va les sacs quon a faire croisement on va 
	    ex : sacs1 sac 1 1568   sacs2 sac 1369 le 16 cest ok ; pour 85 et 93 ; sacs1 on regarde ou 93 existe on met 85 a sa place et on prends 93 on le mets dans le sac croise  meme pour lautre 
	     
	     */
	     
	     
	    // pour le croisement on choisie aleatoirement 2 sacs pour faire crois� apres 
	    //on va echanger ses sacs quand on echange tous les objets 
	    //on va chercher dans les objets restant on va essayer de placer 
	    // sinn tous les deux sacs 
	    //si tous existe on va choisir autre sac 
	    //si une sol tous existe on va choisir les sacs restants et on va faire par ...
	    //rapport poids et valeur sinn ca mene pas a une bonne solution on choisir un autre sac 
	    //si pas tous existe on garde les objets qui n'existe pas et on cherche objets restant 
	    //si ca mene a sol pas valide on change de sacs 
	    // si mene sol valide ok 
	   
	     //  on trie  pour avoir taille pop initiale 
		//on trie pour faire croisement aussi 
		// quand yaura de selectionner pop 
	
		
		
		
		// bon jsp si je vais l'utiliser plus tard 
	 boolean verifier(ArrayList<Sac> s) {
		//v+poids shah return true sinn return false apr cond taht si 
			//faux je vois si le poids et le probleme sinn v 
	
		 boolean v=true;
		 int poids=0;
		 int val=0;
		 int cpt =0;
		 for(Sac sac : s) {
			for(objet o:sac.objets) {
				poids+=o.poids;
				val+=o.valeur;
			}
			if(poids >sac.poidsmax || val <sac.valmin) {
				v=false;
				
			}
		 }
		 
	
		
		 return v;
	 }
		
	public void ajouter_fils(sol[] croisement,int y ,sol sol1,sol sol2,
			ArrayList<Sac> parent1,ArrayList<Sac> parent2)	
	 
	{
	 sol1.sacs.addAll(parent1);
     sol2.sacs.addAll(parent2);
     int f=calcul(sol1);
     sol1.valeur_total=f;
     f=calcul(sol2);
     sol2.valeur_total=f;
     croisement[y] = sol1;
     croisement[y+1]= sol2;
     y=y+2;
	}
		
	 public sol[] croisement (sol[] pop,float r) {
		 trie(pop);
		 
		 int nbparent=(int) ((int)(pop.length)*r);
		 if(nbparent==pop.length) {
			 if (nbparent % 2 != 0) {
				 nbparent--;
			 }
		 }
			if(nbparent!=pop.length) {
			if (nbparent % 2 != 0) {
				nbparent++;
			}}
			pop =verifierfitness(pop,nbparent);
			
			sol [] croisement2 =new sol[nbparent];//pour inserer pop qu'on va lui appliquer croisement au debut
			sol [] croisement =new sol[nbparent];//pour resultat retourn�
				
																					
			for(int i=0;i<nbparent;i++) {
				croisement2[i]=pop[i];
			}
			
			int k=0;
			// pour calculer nbr de fils croisser ajouter
			int p=0;
			 Random rand = new Random();
			while(k<croisement2.length  ) {
				float m=rand.nextFloat();
				if(m<r) {
				
				// creation d'un fils croiser 
				ArrayList<Sac> parent1 = new ArrayList<>();
				ArrayList<Sac> parent2 = new ArrayList<>();
				ArrayList<objet> objrest1 = new ArrayList<>();
				ArrayList<objet> objrest2 = new ArrayList<>();
				objrest1.addAll(croisement2[k].objets_restant);
				objrest2.addAll(croisement2[k+1].objets_restant);
				for (Sac value : croisement2[k].sacs) {
				    parent1.add(value);
				}
				for (Sac value : croisement2[k+1].sacs) {
				    parent2.add(value);
				}
				int position1 = rand.nextInt(parent1.size());
				 int position2;
				 do {
				     position2 = rand.nextInt(parent2.size());
				 } while (position2 == position1);
				//pour sauvegarder les obj qui ne sont pas dans parent2 qui sont position1 parent1 
				 //obj qui ne sont pas dans parent1 mais qui sont dans position 2 parent2
				 ArrayList <objet> pos =new ArrayList<>();//parent1 pos1 qui ne sont pas parent2
				 ArrayList <objet> pos1 =new ArrayList<>();//parent2 posi2 qui ne sont pas parent1
				 //pour sauvegarder les position1 et position2 deja choisie 
				 ArrayList <Integer> apos1 =new ArrayList<>();
				 ArrayList <Integer> apos2 =new ArrayList<>();
				 //les temp pour sauvegarder parent1 et parent2 en cas de modif 
				 ArrayList<Sac> temp =new ArrayList<>();							 							
					for (Sac s : parent1) {		    					    			
 					    temp.add(s);
 					}
					 ArrayList<Sac> temp2 =new ArrayList<>();							 							
						for (Sac s : parent2) {		    					    			
	    					    temp2.add(s);
	    					}	
				 
				 apos1.add(position1);
				 apos2.add(position2);
				 boolean l=true;
				 int y=0;
				 sol sol1 =new sol();
                 sol sol2 =new sol();
				 while(l==true) {
					// cpt :si tous les objets existe ou non
					 int cpt1;
					 int cpt;
					 ArrayList <objet> rv =new ArrayList<>();
					 //voir si obj de parent1 position 1 existe dans parent2
					 for(int i=0;i<parent1.get(position1).objets.size();i++) {
					 int j=0;
					 cpt=0;
					boolean trouve=false;
					 while(j<parent2.size() && trouve==false) {
						 if(j!=position2) {
							 if(!parent2.get(j).objets.contains(parent1.get(position1).objets.get(i))) {
								 cpt++;			 
							 }
							 else {
								 trouve=true;
							 }
							 
						 }
						 j++;
						 
					 }
					 if (cpt==parent2.size()-1) {
						 pos.add(parent1.get(position1).objets.get(i));	
					 }
				
				 }
					 //ici voir si obj parent2 pos 2 existe parent1
				 for(int i=0;i<parent2.get(position2).objets.size();i++) {
					 int j=0;
					 cpt1=0;
					boolean trouve=false;
					 while(j<parent1.size() && trouve==false) {
						 if(j!=position1) {
							 if(!parent1.get(j).objets.contains(parent2.get(position2).objets.get(i))) {
								 cpt1++;			 
							 }else {
								 trouve=true;
							 }
						 }
						 j++;
						 
					 } 
					 if (cpt1==parent2.size()-1) {
						 pos1.add(parent2.get(position2).objets.get(i))	;	
					 }
				 }
				
		//ici si tous objets existe dans les deux sacs donc on change de pos 
		 if(pos1.size()==0 && pos.size()==0) {
			 		int positi1=-1;
			 		int positi2=-1;
						 do {
						  positi1 = rand.nextInt(parent1.size());}
						 while(apos1.contains(positi1));
						 do {
						     positi2 = rand.nextInt(parent2.size());
						 } while ((positi2 == positi1) && (apos2.contains(positi2)));
						 
						 //pour refaire iteration choisir autre pos de sacs 
						 if(positi1!=-1 && positi2!=-1) {
							 l=true;
							 apos2.add(positi2);
							 apos1.add(positi1);
							 }else {
								// ici c bn on va laisser tomber les parents
								 l=false;
								 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);
						
							 }
					 }
					 else {
						 //ici si les objets des pos1 et pos 2 
						 //nexiste dans aucun ni parent1 pour pos 2 ni parent2 pour pos1
						if(pos.size() == parent1.get(position1).objets.size() && 
								          pos1.size()==parent2.get(position2).objets.size()) {
							
							sol1.objets_restant.addAll(parent1.get(position1).objets);
							sol2.objets_restant.addAll(parent2.get(position2).objets);
							 
							parent1.set(position1, parent2.get(position2));
							parent2.set(position2, temp.get(position1));
							
							// ** !!!! **ici on a pas verifier le v et le poids !!!!
							
//********************************************************************************************************
							if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour &&
									parent1.get(position1).valmin<=parent1.get(position1).valcour ) {
									if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour &&
									parent2.get(position2).valmin<=parent2.get(position2).valcour){
							
							// ici on ne soucie pas de duplication mais par la validit�
	                       
	                        //if( nchofo ida sal valide n9arno poids et ncompariw val )
						
							
								l=false;
								 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

			                     
							 
							} else {
								if(parent2.get(position2).poidsmax<parent2.get(position2).poidcour) {
								if(parent2.get(position2).valmin<=parent2.get(position2).valcour) {
							//j'essaye d'enlever sans affecter v sinn j'enleve et 
							//je rajoute de objets restant si ca marche pas c bn 
							
							int po=parent2.get(position2).poidcour-parent2.get(position2).poidsmax;
							int v=parent2.get(position2).valcour-parent2.get(position2).valmin;
							//
							
					for(objet o : parent2.get(position2).objets) {
					  if(o.valeur<=v && o.poids>=po ) {
						  //ici c ok on a regler le poids et val non affecte 
						  //on va l'enlever 
						  parent2.get(position2).objets.remove(o);
						  // imp : ajouter au objets restants
						  objrest2.add(o);
						 parent2.get(position2).poidcour=compterpoidsc(parent2.get(position2).objets);
						 parent2.get(position2).poidcour=comptervalc(parent2.get(position2).objets);
						  l=false;

						  sol2.objets_restant.addAll(objrest2);
						  ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);
						  
		                     break;
							  }else {
				                  if(o.poids<po) {
					                   if(o.valeur<=v) {
										rv.add(o);    //ici jai sauvegarder on sait jms je dois rendre ces obj 
										parent2.get(position2).objets.remove(o);
										parent2.get(position2).poidcour=compterpoidsc(parent2.get(position2).objets);
									    parent2.get(position2).poidcour=comptervalc(parent2.get(position2).objets);
									    po=po-o.poids;	
									    v=v-o.valeur;
										  // then nchdo pos dyalo nahoh on cherche whdkher qui va nous regler le poids
										  
									  }
										  
								  }
							  }
					}
			
							
							
							//on fait combinaison d'objets 
							//ici si le poids s'est pas regl� donc on prends poids ki affecte le min possible le v 
							if(parent2.get(position2).poidsmax<parent2.get(position2).poidcour) {
								parent2.get(position2).objets.addAll(rv);
								int valmin=Integer.MAX_VALUE;
								// je prends objet qui regle le poids et qui affecte v tres peu 
								for(objet o : parent2.get(position2).objets) {
									if(o.poids>=po ) {
										//on compare  avec precedente .
										if(valmin>o.valeur) {
										rv.removeAll(rv);
										//rv.remove(0);
									    valmin=o.valeur;
									    rv.add(o);//contient juste qui a min v et qui regle le poids 
										}
									}
								}
								 parent2.get(position2).objets.remove(rv.get(0));

							}
							if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour) {
								
                                  objrest2.addAll(rv);
								// ici on regard pour le v si cest regle ok sinn 
								if(parent2.get(position2).valcour>=parent2.get(position2).valmin) {
									 l=false;
									 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

								}
								else {
										
												
												
					//on voit objets restant on essaye de regler v sans ffceter poids 
					//cest pour regler le v 
	              for(objet o: objrest2) {
	                if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
	                if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
        			  parent2.get(position2).objets.add(o);
        			  objrest2.remove(o);
        			  l=false;
						 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

        			  break;
                		  }
                	  }
                  }
	              }
					//if v trigla ok sinn c bn laisse tomber 
					if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
						 l=false;
						 ajouter_fils(croisement,y,sol1,sol2,parent1,temp2);
					
					}
					
                      // ce comm faut lire !
                      //ajouter ca dans objets restant then chercher obj restants qui n'affecte
                      //pas le poids et regle v ajouter le sinn c bn pas de croisement 
                      
				}
							
				
			//celui la aussi jsp wchfih 
				//test si parent1 trigla si oui ok sinn on remets les rv et on retire now 
				//le poids qui va regler mais avec val minimaliste apr on 
				//ajoute dans objets restant et on va picker obj qui regle le v et n'affecte pas le poids 
				
				
				
				
			}else {
			//poids +v nest pas ok parent2
			//on regle le poids apr si v est regler ok sinn on essaye de lui ajouter des trucs 
			//restant si ok ok sinn on laisse tomber
			int valmin=Integer.MAX_VALUE;
			// je prends objet qui regle le poids et qui affecte v tres peu 
			for(objet o : parent2.get(position2).objets) {
				if(o.poids>=(parent2.get(position2).poidcour-parent2.get(position2).poidsmax) ) {
					//on compare  avec precedente .
					if(valmin>o.valeur) {
					rv.removeAll(rv);
					//rv.remove(0);
				    valmin=o.valeur;
				    rv.add(o);//contient juste qui a min v et qui regle le poids 
					}
				}
			}
			 parent2.get(position2).objets.remove(rv.get(0));
				if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour) {
					
                    objrest2.addAll(rv);
					// ici on regard pour le v si cest regle ok sinn 
					if(parent2.get(position2).valcour>=parent2.get(position2).valmin) {
						 l=false;
						 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

					}
					else {
					
					
					
					//on voit objets 
					//restant on essaye de regler v sans ffceter poids 
					//cest pour regler le v 
                    for(objet o: objrest2) {
                  	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
                  		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
                  			  parent2.get(position2).objets.add(o);
                  			  objrest2.remove(o);
                  			  l=false;
 							 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

                  			  break;
                  		  }
                  	  }
                    }}
					//if v trigla ok sinn c bn laisse tomber 
					if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
						 l=false;
						 ajouter_fils(croisement,y,sol1,sol2,parent1,temp2);
					
					}
					
				
					
				}
				
				}
			
			}else {
		        // si poids parent 2 ok mais v non donc on essaye de rajouter des obj de obj 
				//restant si regle ok sans affecter poids sinn on laisse tomber 
			
				 for(objet o: objrest2) {
                 	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
                 		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
                 			  parent2.get(position2).objets.add(o);
                 			  objrest2.remove(o);
                 			  l=false;
 							 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

                 			  break;
                 		  }
                 	  }
                   }
					if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
						 l=false;
						 ajouter_fils(croisement,y,sol1,sol2,parent1,temp2);

					     
					}
				
				
			}									
			}
		} else {
			//parent1 prblm donc si parent2 pas de prblm 1 chose apr je fais si les deux ont 
			
				if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour &&
						parent2.get(position2).valmin<=parent2.get(position2).valcour) {										
				//  si il a prblm avc poids sinn avec v 
					
					if(parent1.get(position1).poidsmax<parent1.get(position1).poidcour) {
						if(parent1.get(position1).valmin<=parent1.get(position1).valcour) {
							//j'essaye d'enlever sans affecter v sinn j'enleve et 
							//je rajoute de objets restant si ca marche pas c bn 
							
							int po=parent1.get(position1).poidcour-parent1.get(position1).poidsmax;
							int v=parent1.get(position1).valcour-parent1.get(position1).valmin;
							//
							
							for(objet o : parent1.get(position1).objets) {
							  if(o.valeur<=v && o.poids>=po ) {
								  //ici c ok on a regler le poids et val non affecte 
								  //on va l'enlever 
								  parent1.get(position1).objets.remove(o);
								  // imp : ajouter au objets restants
								  objrest1.add(o);
								 parent1.get(position1).poidcour=compterpoidsc(parent1.get(position1).objets);
								 parent1.get(position1).poidcour=comptervalc(parent1.get(position1).objets);
								  l=false;
								  sol1.objets_restant.addAll(objrest1);
									 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

				                     break;
							  }else {
								  if(o.poids<po) {
									  if(o.valeur<=v) {
										  rv.add(o);    //ici jai sauvegarder on sait jms je dois rendre ces obj 
										  parent1.get(position1).objets.remove(o);
										  parent1.get(position1).poidcour=compterpoidsc(parent1.get(position1).objets);
									      parent1.get(position1).poidcour=comptervalc(parent1.get(position1).objets);
									po=po-o.poids;	
									v=v-o.valeur;
										  // then nchdo pos dyalo nahoh on cherche whdkher qui va nous regler le poids
										  
									  }
										  
								  }
							  }
							}
							
							
							
							//on fait combinaison d'objets 
							//ici si le poids s'est pas regl� donc on prends poids ki affecte le min possible le v 
							if(parent1.get(position1).poidsmax<parent1.get(position1).poidcour) {
								parent1.get(position1).objets.addAll(rv);
								int valmin=Integer.MAX_VALUE;
								// je prends objet qui regle le poids et qui affecte v tres peu 
								for(objet o : parent1.get(position1).objets) {
									if(o.poids>=po ) {
										//on compare  avec precedente .
										if(valmin>o.valeur) {
										rv.removeAll(rv);
										//rv.remove(0);
									    valmin=o.valeur;
									    rv.add(o);//contient juste qui a min v et qui regle le poids 
										}
									}
								}
								 parent1.get(position1).objets.remove(rv.get(0));
	
							}
							if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour) {
								
	                              objrest1.addAll(rv);
								// ici on regard pour le v si cest regle ok sinn 
								if(parent1.get(position1).valcour>=parent1.get(position1).valmin) {
									 l=false;
									 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

								}
								else {
								
								
								
								//on voit objets 
								//restant on essaye de regler v sans ffceter poids 
								//cest pour regler le v 
	                              for(objet o: objrest1) {
	                            	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
	                            		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
	                            			  parent1.get(position1).objets.add(o);
	                            			  objrest1.remove(o);
	                            			  l=false;
	             							 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

	                            			  break;
	                            		  }
	                            	  }
	                              }}
								//if v trigla ok sinn c bn laisse tomber 
								if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
									 l=false;
									 ajouter_fils(croisement,y,sol1,sol2,temp,parent2);								    
								}
								
	                              // ce comm faut lire !
	                              //ajouter ca dans objets restant then chercher obj restants qui n'affecte
	                              //pas le poids et regle v ajouter le sinn c bn pas de croisement 
	                              
							}
							
							
						//celui la aussi jsp wchfih 
							//test si parent2 trigla si oui ok sinn on remets les rv et on retire now 
							//le poids qui va regler mais avec val minimaliste apr on 
							//ajoute dans objets restant et on va picker obj qui regle le v et n'affecte pas le poids 
						
						}else {
						//else si v de parent1  n'est pas ok ? 
						//poids +v nest pas ok 
						//on regle le poids apr si v est regler ok sinn on essaye de lui ajouter des trucs 
						//restant si ok ok sinn on laisse tomber
						int valmin=Integer.MAX_VALUE;
						// je prends objet qui regle le poids et qui affecte v tres peu 
						for(objet o : parent1.get(position1).objets) {
							if(o.poids>=(parent1.get(position1).poidcour-parent1.get(position1).poidsmax) ) {
								//on compare  avec precedente .
								if(valmin>o.valeur) {
								rv.removeAll(rv);
								//rv.remove(0);
							    valmin=o.valeur;
							    rv.add(o);//contient juste qui a min v et qui regle le poids 
								}
							}
						}
						 parent1.get(position1).objets.remove(rv.get(0));
							if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour) {
								
	                            objrest1.addAll(rv);
								// ici on regard pour le v si cest regle ok sinn 
								if(parent1.get(position1).valcour>=parent1.get(position1).valmin) {
									 l=false;
									 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

								}
								else {
								
								
								
								//on voit objets 
								//restant on essaye de regler v sans ffceter poids 
								//cest pour regler le v 
	                            for(objet o: objrest1) {
	                          	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
	                          		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
	                          			  parent1.get(position1).objets.add(o);
	                          			  objrest1.remove(o);
	                          			  l=false;
	         							 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

	                          			  break;
	                          		  }
	                          	  }
	                            }}
								//if v trigla ok sinn c bn laisse tomber 
								if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
									 l=false;
									 ajouter_fils(croisement,y,sol1,sol2,temp,parent2);

								     
								}
								
							
								
							}
							
							}
						
						}else {
					        // si poids parent 2 ok mais v non donc on essaye de rajouter des obj de obj 
							//restant si regle ok sans affecter poids sinn on laisse tomber 
							 for(objet o: objrest1) {
	                         	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
	                         		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
	                         			  parent1.get(position1).objets.add(o);
	                         			  objrest1.remove(o);
	                         			  l=false;
	         							 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

	                         			  break;
	                         		  }
	                         	  }
	                           }
								if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
									 l=false;
									 ajouter_fils(croisement,y,sol1,sol2,temp,parent2);

								}
							
														
													}																		
											}else {
												//parent 1 et 2 les deux ont des prblm de poids ou val
												if(parent2.get(position2).poidsmax<parent2.get(position2).poidcour) {
													if(parent2.get(position2).valmin<=parent2.get(position2).valcour) {
												//j'essaye d'enlever sans affecter v sinn j'enleve et 
												//je rajoute de objets restant si ca marche pas c bn 
												
												int po=parent2.get(position2).poidcour-parent2.get(position2).poidsmax;
												int v=parent2.get(position2).valcour-parent2.get(position2).valmin;
												//
												
										for(objet o : parent2.get(position2).objets) {
										  if(o.valeur<=v && o.poids>=po ) {
											  //ici c ok on a regler le poids et val non affecte 
											  //on va l'enlever 
											  parent2.get(position2).objets.remove(o);
											  // imp : ajouter au objets restants
											  objrest2.add(o);
											 parent2.get(position2).poidcour=compterpoidsc(parent2.get(position2).objets);
											 parent2.get(position2).poidcour=comptervalc(parent2.get(position2).objets);
											  l=false;
											  sol2.objets_restant.addAll(objrest2);
												 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

							                     break;
												  }else {
									                  if(o.poids<po) {
										                   if(o.valeur<=v) {
															rv.add(o);    //ici jai sauvegarder on sait jms je dois rendre ces obj 
															parent2.get(position2).objets.remove(o);
															parent2.get(position2).poidcour=compterpoidsc(parent2.get(position2).objets);
														    parent2.get(position2).poidcour=comptervalc(parent2.get(position2).objets);
														    po=po-o.poids;	
														    v=v-o.valeur;
															  // then nchdo pos dyalo nahoh on cherche whdkher qui va nous regler le poids
															  
														  }
															  
													  }
												  }
										}
								
												
												
												//on fait combinaison d'objets 
												//ici si le poids s'est pas regl� donc on prends poids ki affecte le min possible le v 
												if(parent2.get(position2).poidsmax<parent2.get(position2).poidcour) {
													parent2.get(position2).objets.addAll(rv);
													int valmin=Integer.MAX_VALUE;
													// je prends objet qui regle le poids et qui affecte v tres peu 
													for(objet o : parent2.get(position2).objets) {
														if(o.poids>=po ) {
															//on compare  avec precedente .
															if(valmin>o.valeur) {
															rv.removeAll(rv);
															//rv.remove(0);
														    valmin=o.valeur;
														    rv.add(o);//contient juste qui a min v et qui regle le poids 
															}
														}
													}
													 parent2.get(position2).objets.remove(rv.get(0));
	
												}
												if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour) {
													
					                                  objrest2.addAll(rv);
													// ici on regard pour le v si cest regle ok sinn 
													if(parent2.get(position2).valcour>=parent2.get(position2).valmin) {
														 l=false;
														 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

													}
													else {
															
																	
																	
										//on voit objets restant on essaye de regler v sans ffceter poids 
										//cest pour regler le v 
						              for(objet o: objrest2) {
						                if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
						                if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
					        			  parent2.get(position2).objets.add(o);
					        			  objrest2.remove(o);
					        			  l=false;
											 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

					        			  break;
					                		  }
					                	  }
					                  }
						              }
										//if v trigla ok sinn c bn laisse tomber 
										if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
											 l=false;
											 ajouter_fils(croisement,y,sol1,sol2,parent1,temp2);

										   
										}
										
									
										
										
										
										
										
					                      // ce comm faut lire !
					                      //ajouter ca dans objets restant then chercher obj restants qui n'affecte
					                      //pas le poids et regle v ajouter le sinn c bn pas de croisement 
					                      
									}
												
									
								//celui la aussi jsp wchfih 
									//test si parent1 trigla si oui ok sinn on remets les rv et on retire now 
									//le poids qui va regler mais avec val minimaliste apr on 
									//ajoute dans objets restant et on va picker obj qui regle le v et n'affecte pas le poids 
									
									
									
									
								}else {
								//poids +v nest pas ok parent2
								//on regle le poids apr si v est regler ok sinn on essaye de lui ajouter des trucs 
								//restant si ok ok sinn on laisse tomber
								int valmin=Integer.MAX_VALUE;
								// je prends objet qui regle le poids et qui affecte v tres peu 
								for(objet o : parent2.get(position2).objets) {
									if(o.poids>=(parent2.get(position2).poidcour-parent2.get(position2).poidsmax) ) {
										//on compare  avec precedente .
										if(valmin>o.valeur) {
										rv.removeAll(rv);
										//rv.remove(0);
									    valmin=o.valeur;
									    rv.add(o);//contient juste qui a min v et qui regle le poids 
										}
									}
								}
								 parent2.get(position2).objets.remove(rv.get(0));
									if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour) {
										
					                    objrest2.addAll(rv);
										// ici on regard pour le v si cest regle ok sinn 
										if(parent2.get(position2).valcour>=parent2.get(position2).valmin) {
											 l=false;
											 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

										    
										}
										else {
										
										
										
										//on voit objets 
										//restant on essaye de regler v sans ffceter poids 
										//cest pour regler le v 
					                    for(objet o: objrest2) {
					                  	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
					                  		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
					                  			  parent2.get(position2).objets.add(o);
					                  			  objrest2.remove(o);
					                  			  l=false;
					 							 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

					                  			  break;
					                  		  }
					                  	  }
					                    }}
										//if v trigla ok sinn c bn laisse tomber 
										if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
											 l=false;
											 ajouter_fils(croisement,y,sol1,sol2,parent1,temp2);

										     
										}
										
									
										
									}
									
									}
								
								}else {
							        // si poids parent 2 ok mais v non donc on essaye de rajouter des obj de obj 
									//restant si regle ok sans affecter poids sinn on laisse tomber 
								
									 for(objet o: objrest2) {
					                 	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
					                 		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
					                 			  parent2.get(position2).objets.add(o);
					                 			  objrest2.remove(o);
					                 			  l=false;
					 							 ajouter_fils(croisement,y,sol1,sol2,parent1,parent2);

												  
					                 			  break;
					                 		  }
					                 	  }
					                   }
										if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
											 l=false;
											 ajouter_fils(croisement,y,sol1,sol2,parent1,temp2);

										    
										}
									
									
								}
												if(parent1.get(position1).poidsmax<parent1.get(position1).poidcour) {
													if(parent1.get(position1).valmin<=parent1.get(position1).valcour) {
														//j'essaye d'enlever sans affecter v sinn j'enleve et 
														//je rajoute de objets restant si ca marche pas c bn 
														
														int po=parent1.get(position1).poidcour-parent1.get(position1).poidsmax;
														int v=parent1.get(position1).valcour-parent1.get(position1).valmin;
														//
														
														for(objet o : parent1.get(position1).objets) {
														  if(o.valeur<=v && o.poids>=po ) {
															  //ici c ok on a regler le poids et val non affecte 
															  //on va l'enlever 
															  parent1.get(position1).objets.remove(o);
															  // imp : ajouter au objets restants
															  objrest1.add(o);
															 parent1.get(position1).poidcour=compterpoidsc(parent1.get(position1).objets);
															 parent1.get(position1).poidcour=comptervalc(parent1.get(position1).objets);
															  l=false;
															  sol1.objets_restant.addAll(objrest1);
															  sol2.sacs.addAll(parent2);
											                     sol1.sacs.addAll(parent1);
											                     int f=calcul(sol1);
											                     sol2.valeur_total=f;
											                     f=calcul(sol2);
											                     sol1.valeur_total=f;
											                     croisement[y] = sol2;
											                     croisement[y+1]= sol1;
											                     y=y+2;
											                     break;
														  }else {
															  if(o.poids<po) {
																  if(o.valeur<=v) {
																	  rv.add(o);    //ici jai sauvegarder on sait jms je dois rendre ces obj 
																	  parent1.get(position1).objets.remove(o);
																	  parent1.get(position1).poidcour=compterpoidsc(parent1.get(position1).objets);
																      parent1.get(position1).poidcour=comptervalc(parent1.get(position1).objets);
																po=po-o.poids;	
																v=v-o.valeur;
																	  // then nchdo pos dyalo nahoh on cherche whdkher qui va nous regler le poids
																	  
																  }
																	  
															  }
														  }
														}
														
														
														
														//on fait combinaison d'objets 
														//ici si le poids s'est pas regl� donc on prends poids ki affecte le min possible le v 
														if(parent1.get(position1).poidsmax<parent1.get(position1).poidcour) {
															parent1.get(position1).objets.addAll(rv);
															int valmin=Integer.MAX_VALUE;
															// je prends objet qui regle le poids et qui affecte v tres peu 
															for(objet o : parent1.get(position1).objets) {
																if(o.poids>=po ) {
																	//on compare  avec precedente .
																	if(valmin>o.valeur) {
																	rv.removeAll(rv);
																	//rv.remove(0);
																    valmin=o.valeur;
																    rv.add(o);//contient juste qui a min v et qui regle le poids 
																	}
																}
															}
															 parent1.get(position1).objets.remove(rv.get(0));
	
														}
														if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour) {
															
								                              objrest1.addAll(rv);
															// ici on regard pour le v si cest regle ok sinn 
															if(parent1.get(position1).valcour>=parent1.get(position1).valmin) {
																 l=false;
															     sol2.sacs.addAll(parent2);
											                     sol1.sacs.addAll(parent1);
											                     int f=calcul(sol1);
											                     sol2.valeur_total=f;
											                     f=calcul(sol2);
											                     sol1.valeur_total=f;
											                     croisement[y] = sol2;
											                     croisement[y+1]= sol1;
											                     y=y+2;
															}
															else {
															
															
															
															//on voit objets 
															//restant on essaye de regler v sans ffceter poids 
															//cest pour regler le v 
								                              for(objet o: objrest1) {
								                            	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
								                            		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
								                            			  parent1.get(position1).objets.add(o);
								                            			  objrest1.remove(o);
								                            			  l=false;
																		  sol2.sacs.addAll(parent2);
														                     sol1.sacs.addAll(parent1);
														                     int f=calcul(sol2);
														                     sol2.valeur_total=f;
														                     f=calcul(sol1);
														                     sol1.valeur_total=f;
														                     croisement[y] = sol2;
														                     croisement[y+1]= sol1;
														                     y=y+2;
								                            			  break;
								                            		  }
								                            	  }
								                              }}
															//if v trigla ok sinn c bn laisse tomber 
															if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
																 l=false;
															     sol2.sacs.addAll(parent2);
											                     sol1.sacs.addAll(temp);
											                     int f=calcul(sol2);
											                     sol2.valeur_total=f;
											                     f=calcul(sol1);
											                     sol1.valeur_total=f;
											                     croisement[y] = sol2;
											                     croisement[y+1]= sol1;
											                     y=y+2;
															}
															
								                              // ce comm faut lire !
								                              //ajouter ca dans objets restant then chercher obj restants qui n'affecte
								                              //pas le poids et regle v ajouter le sinn c bn pas de croisement 
								                              
														}
														
														
													//celui la aussi jsp wchfih 
														//test si parent2 trigla si oui ok sinn on remets les rv et on retire now 
														//le poids qui va regler mais avec val minimaliste apr on 
														//ajoute dans objets restant et on va picker obj qui regle le v et n'affecte pas le poids 
													
													}else {
													//else si v de parent1  n'est pas ok ? 
													//poids +v nest pas ok 
													//on regle le poids apr si v est regler ok sinn on essaye de lui ajouter des trucs 
													//restant si ok ok sinn on laisse tomber
													int valmin=Integer.MAX_VALUE;
													// je prends objet qui regle le poids et qui affecte v tres peu 
													for(objet o : parent1.get(position1).objets) {
														if(o.poids>=(parent1.get(position1).poidcour-parent1.get(position1).poidsmax) ) {
															//on compare  avec precedente .
															if(valmin>o.valeur) {
															rv.removeAll(rv);
															//rv.remove(0);
														    valmin=o.valeur;
														    rv.add(o);//contient juste qui a min v et qui regle le poids 
															}
														}
													}
													 parent1.get(position1).objets.remove(rv.get(0));
														if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour) {
															
								                            objrest1.addAll(rv);
															// ici on regard pour le v si cest regle ok sinn 
															if(parent1.get(position1).valcour>=parent1.get(position1).valmin) {
																 l=false;
															     sol2.sacs.addAll(parent2);
											                     sol1.sacs.addAll(parent1);
											                     int f=calcul(sol2);
											                     sol2.valeur_total=f;
											                     f=calcul(sol1);
											                     sol1.valeur_total=f;
											                     croisement[y] = sol2;
											                     croisement[y+1]= sol1;
											                     y=y+2;
															}
															else {
															
															
															
															//on voit objets 
															//restant on essaye de regler v sans ffceter poids 
															//cest pour regler le v 
								                            for(objet o: objrest1) {
								                          	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
								                          		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
								                          			  parent1.get(position1).objets.add(o);
								                          			  objrest1.remove(o);
								                          			  l=false;
																	  sol2.sacs.addAll(parent2);
													                     sol1.sacs.addAll(parent1);
													                     int f=calcul(sol2);
													                     sol2.valeur_total=f;
													                     f=calcul(sol1);
													                     sol1.valeur_total=f;
													                     croisement[y] = sol2;
													                     croisement[y+1]= sol1;
													                     y=y+2;
								                          			  break;
								                          		  }
								                          	  }
								                            }}
															//if v trigla ok sinn c bn laisse tomber 
															if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
																 l=false;
															     sol2.sacs.addAll(parent2);
											                     sol1.sacs.addAll(temp);
											                     int f=calcul(sol2);
											                     sol2.valeur_total=f;
											                     f=calcul(sol1);
											                     sol1.valeur_total=f;
											                     croisement[y] = sol2;
											                     croisement[y+1]= sol1;
											                     y=y+2;
															}
															
														
															
														}
														
														}
													
													}else {
												        // si poids parent 1 ok mais v non donc on essaye de rajouter des obj de obj 
														//restant si regle ok sans affecter poids sinn on laisse tomber 
														 for(objet o: objrest1) {
								                         	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
								                         		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
								                         			  parent1.get(position1).objets.add(o);
								                         			  objrest1.remove(o);
								                         			  l=false;
																	  sol2.sacs.addAll(parent2);
													                     sol1.sacs.addAll(parent1);
													                     int f=calcul(sol2);
													                     sol2.valeur_total=f;
													                     f=calcul(sol1);
													                     sol1.valeur_total=f;
													                     croisement[y] = sol2;
													                     croisement[y+1]= sol1;
													                     y=y+2;
								                         			  break;
								                         		  }
								                         	  }
								                           }
															if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
																 l=false;
															     sol2.sacs.addAll(parent2);
											                     sol1.sacs.addAll(temp);
											                     int f=calcul(sol2);
											                     sol2.valeur_total=f;
											                     f=calcul(sol1);
											                     sol1.valeur_total=f;
											                     croisement[y] = sol2;
											                     croisement[y+1]= sol1;
											                     y=y+2;
															}
														
																					
																				}	
											}
										}
	//********************************************************************************************************							
						
						 }else {
						 // si par ex pas tous les obj existe ou si par ex sac de sol 1 rentre dans sac sol2 sans probleme 
			if(pos.size()<parent1.get(position1).objets.size() && pos1.size()<parent2.get(position2).objets.size()) {
						 //on permute peut etre que les objets qui ne snt pas dans les sols
						
						//ne touche pas pos pos1
						
						// obj parent1 qui ne sont pas pos1 seront stock� objets restant 
						   for (objet obj : parent1.get(position1).objets) {
					    
					            if (!pos1.contains(obj)) {
					     
					                sol1.objets_restant.add(obj);
					            }
					        }
						   for (objet obj : parent2.get(position2).objets) {
							    
					            if (!pos.contains(obj)) {
					     
					                sol2.objets_restant.add(obj);
					            }
					        }
			
						parent1.get(position1).setObjets(pos1);
						parent2.get(position2).setObjets(pos);
						parent1.get(position1).poidcour=compterpoidsc(pos1);
						parent2.get(position2).valcour=compterpoidsc(pos);
						parent1.get(position1).poidcour=comptervalc(pos1);
						parent2.get(position2).valcour=comptervalc(pos);
						
						if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour &&
								parent1.get(position1).valmin<=parent1.get(position1).valcour ) {
								if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour &&
								parent2.get(position2).valmin<=parent2.get(position2).valcour){
						
						// ici on ne soucie pas de duplication mais par la validit�
                       
                        //if( nchofo ida sal valide n9arno poids et ncompariw val )
					
						
							l=false;
		                     sol1.sacs.addAll(parent1);
		                     sol2.sacs.addAll(parent2);
		                     int f=calcul(sol1);
		                     sol1.valeur_total=f;
		                     f=calcul(sol2);
		                     sol2.valeur_total=f;
		                     croisement[y] = sol1;
		                     croisement[y+1]= sol2;
		                     y=y+2;
						 
								}
			else {
				if(parent2.get(position2).poidsmax<parent2.get(position2).poidcour) {
				if(parent2.get(position2).valmin<=parent2.get(position2).valcour) {
					//j'essaye d'enlever sans affecter v sinn j'enleve et 
					//je rajoute de objets restant si ca marche pas c bn 
					
					int po=parent2.get(position2).poidcour-parent2.get(position2).poidsmax;
					int v=parent2.get(position2).valcour-parent2.get(position2).valmin;
					//
					
					for(objet o : parent2.get(position2).objets) {
					  if(o.valeur<=v && o.poids>=po ) {
						  //ici c ok on a regler le poids et val non affecte 
						  //on va l'enlever 
						  parent2.get(position2).objets.remove(o);
						  // imp : ajouter au objets restants
						  objrest2.add(o);
						 parent2.get(position2).poidcour=compterpoidsc(parent2.get(position2).objets);
						 parent2.get(position2).poidcour=comptervalc(parent2.get(position2).objets);
						  l=false;
						  sol2.objets_restant.addAll(objrest2);
						  sol1.sacs.addAll(parent1);
		                     sol2.sacs.addAll(parent2);
		                     int f=calcul(sol1);
		                     sol1.valeur_total=f;
		                     f=calcul(sol2);
		                     sol2.valeur_total=f;
		                     croisement[y] = sol1;
		                     croisement[y+1]= sol2;
		                     y=y+2;
		                     break;
					  }else {
						  if(o.poids<po) {
							  if(o.valeur<=v) {
								  rv.add(o);    //ici jai sauvegarder on sait jms je dois rendre ces obj 
								  parent2.get(position2).objets.remove(o);
								  parent2.get(position2).poidcour=compterpoidsc(parent2.get(position2).objets);
							      parent2.get(position2).poidcour=comptervalc(parent2.get(position2).objets);
							po=po-o.poids;	
							v=v-o.valeur;
								  // then nchdo pos dyalo nahoh on cherche whdkher qui va nous regler le poids
								  
							  }
								  
						  }
					  }
					}
					
					
			
			//on fait combinaison d'objets 
			//ici si le poids s'est pas regl� donc on prends poids ki affecte le min possible le v 
			if(parent2.get(position2).poidsmax<parent2.get(position2).poidcour) {
				parent2.get(position2).objets.addAll(rv);
				int valmin=Integer.MAX_VALUE;
				// je prends objet qui regle le poids et qui affecte v tres peu 
				for(objet o : parent2.get(position2).objets) {
					if(o.poids>=po ) {
						//on compare  avec precedente .
						if(valmin>o.valeur) {
						rv.removeAll(rv);
						//rv.remove(0);
					    valmin=o.valeur;
					    rv.add(o);//contient juste qui a min v et qui regle le poids 
						}
					}
				}
				 parent2.get(position2).objets.remove(rv.get(0));

			}
			if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour) {
				
                  objrest2.addAll(rv);
				// ici on regard pour le v si cest regle ok sinn 
				if(parent2.get(position2).valcour>=parent2.get(position2).valmin) {
					 l=false;
				     sol1.sacs.addAll(parent1);
                     sol2.sacs.addAll(parent2);
                     int f=calcul(sol1);
                     sol1.valeur_total=f;
                     f=calcul(sol2);
                     sol2.valeur_total=f;
                     croisement[y] = sol1;
                     croisement[y+1]= sol2;
                     y=y+2;
				}
				else {
				
				
				
				//on voit objets restant on essaye de regler v sans ffceter poids 
				//cest pour regler le v 
                  for(objet o: objrest2) {
                	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
                		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
                			  parent2.get(position2).objets.add(o);
                			  objrest2.remove(o);
                			  l=false;
							  sol1.sacs.addAll(parent1);
			                     sol2.sacs.addAll(parent2);
			                     int f=calcul(sol1);
			                     sol1.valeur_total=f;
			                     f=calcul(sol2);
			                     sol2.valeur_total=f;
			                     croisement[y] = sol1;
			                     croisement[y+1]= sol2;
			                     y=y+2;
                			  break;
                		  }
                	  }
                  }}
				//if v trigla ok sinn c bn laisse tomber 
				if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
					 l=false;
				     sol1.sacs.addAll(parent1);
                     sol2.sacs.addAll(temp2);
                     int f=calcul(sol1);
                     sol1.valeur_total=f;
                     f=calcul(sol2);
                     sol2.valeur_total=f;
                     croisement[y] = sol1;
                     croisement[y+1]= sol2;
                     y=y+2;
				}
				
			
				
				
				
				
				
                  // ce comm faut lire !
                  //ajouter ca dans objets restant then chercher obj restants qui n'affecte
                  //pas le poids et regle v ajouter le sinn c bn pas de croisement 
                  
			}
			
		//celui la aussi jsp wchfih 
			//test si parent1 trigla si oui ok sinn on remets les rv et on retire now 
			//le poids qui va regler mais avec val minimaliste apr on 
			//ajoute dans objets restant et on va picker obj qui regle le v et n'affecte pas le poids 
			
			
			
			
		}else {
		//else si v de parent2  n'est pas ok ? 
		//poids +v nest pas ok 
		//on regle le poids apr si v est regler ok sinn on essaye de lui ajouter des trucs 
		//restant si ok ok sinn on laisse tomber
		int valmin=Integer.MAX_VALUE;
		// je prends objet qui regle le poids et qui affecte v tres peu 
		for(objet o : parent2.get(position2).objets) {
			if(o.poids>=(parent2.get(position2).poidcour-parent2.get(position2).poidsmax) ) {
				//on compare  avec precedente .
				if(valmin>o.valeur) {
				rv.removeAll(rv);
				//rv.remove(0);
			    valmin=o.valeur;
			    rv.add(o);//contient juste qui a min v et qui regle le poids 
				}
			}
		}
		 parent2.get(position2).objets.remove(rv.get(0));
			if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour) {
				
                objrest2.addAll(rv);
				// ici on regard pour le v si cest regle ok sinn 
				if(parent2.get(position2).valcour>=parent2.get(position2).valmin) {
					 l=false;
				     sol1.sacs.addAll(parent1);
                     sol2.sacs.addAll(parent2);
                     int f=calcul(sol1);
                     sol1.valeur_total=f;
                     f=calcul(sol2);
                     sol2.valeur_total=f;
                     croisement[y] = sol1;
                     croisement[y+1]= sol2;
                     y=y+2;
				}
				else {
				
				
				
				//on voit objets 
				//restant on essaye de regler v sans ffceter poids 
				//cest pour regler le v 
                for(objet o: objrest2) {
              	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
              		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
              			  parent2.get(position2).objets.add(o);
              			  objrest2.remove(o);
              			  l=false;
						  sol1.sacs.addAll(parent1);
		                     sol2.sacs.addAll(parent2);
		                     int f=calcul(sol1);
		                     sol1.valeur_total=f;
		                     f=calcul(sol2);
		                     sol2.valeur_total=f;
		                     croisement[y] = sol1;
		                     croisement[y+1]= sol2;
		                     y=y+2;
              			  break;
              		  }
              	  }
                }}
				//if v trigla ok sinn c bn laisse tomber 
				if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
					 l=false;
				     sol1.sacs.addAll(parent1);
                     sol2.sacs.addAll(temp2);
                     int f=calcul(sol1);
                     sol1.valeur_total=f;
                     f=calcul(sol2);
                     sol2.valeur_total=f;
                     croisement[y] = sol1;
                     croisement[y+1]= sol2;
                     y=y+2;
				}
				
			
				
			}
			
			}
		
		}else {
	        // si poids parent 2 ok mais v non donc on essaye de rajouter des obj de obj 
			//restant si regle ok sans affecter poids sinn on laisse tomber 
			 for(objet o: objrest2) {
             	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
             		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
             			  parent2.get(position2).objets.add(o);
             			  objrest2.remove(o);
             			  l=false;
						  sol1.sacs.addAll(parent1);
		                     sol2.sacs.addAll(parent2);
		                     int f=calcul(sol1);
		                     sol1.valeur_total=f;
		                     f=calcul(sol2);
		                     sol2.valeur_total=f;
		                     croisement[y] = sol1;
		                     croisement[y+1]= sol2;
		                     y=y+2;
             			  break;
             		  }
             	  }
               }
				if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
					 l=false;
				     sol1.sacs.addAll(parent1);
                     sol2.sacs.addAll(temp2);
                     int f=calcul(sol1);
                     sol1.valeur_total=f;
                     f=calcul(sol2);
                     sol2.valeur_total=f;
                     croisement[y] = sol1;
                     croisement[y+1]= sol2;
                     y=y+2;
				}
			
			
		}									
		}
	} else {
		//parent1 prblm donc si parent2 pas de prblm 1 chose apr je fais si les deux ont prblm 
		if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour &&
				parent2.get(position2).valmin<=parent2.get(position2).valcour) {										
		//  si il a prblm avc poids sinn avec v 
			
			if(parent1.get(position1).poidsmax<parent1.get(position1).poidcour) {
				if(parent1.get(position1).valmin<=parent1.get(position1).valcour) {
					//j'essaye d'enlever sans affecter v sinn j'enleve et 
					//je rajoute de objets restant si ca marche pas c bn 
					
					int po=parent1.get(position1).poidcour-parent1.get(position1).poidsmax;
					int v=parent1.get(position1).valcour-parent1.get(position1).valmin;
					//
					
					for(objet o : parent1.get(position1).objets) {
					  if(o.valeur<=v && o.poids>=po ) {
						  //ici c ok on a regler le poids et val non affecte 
						  //on va l'enlever 
						  parent1.get(position1).objets.remove(o);
						  // imp : ajouter au objets restants
						  objrest1.add(o);
						 parent1.get(position1).poidcour=compterpoidsc(parent1.get(position1).objets);
						 parent1.get(position1).poidcour=comptervalc(parent1.get(position1).objets);
						  l=false;
						  sol1.objets_restant.addAll(objrest1);
						  sol2.sacs.addAll(parent2);
		                     sol1.sacs.addAll(parent1);
		                     int f=calcul(sol1);
		                     sol2.valeur_total=f;
		                     f=calcul(sol2);
		                     sol1.valeur_total=f;
		                     croisement[y] = sol2;
		                     croisement[y+1]= sol1;
		                     y=y+2;
		                     break;
					  }else {
						  if(o.poids<po) {
							  if(o.valeur<=v) {
								  rv.add(o);    //ici jai sauvegarder on sait jms je dois rendre ces obj 
								  parent1.get(position1).objets.remove(o);
								  parent1.get(position1).poidcour=compterpoidsc(parent1.get(position1).objets);
							      parent1.get(position1).poidcour=comptervalc(parent1.get(position1).objets);
							po=po-o.poids;	
							v=v-o.valeur;
								  // then nchdo pos dyalo nahoh on cherche whdkher qui va nous regler le poids
								  
							  }
								  
						  }
					  }
					}
					
					
					
					//on fait combinaison d'objets 
					//ici si le poids s'est pas regl� donc on prends poids ki affecte le min possible le v 
					if(parent1.get(position1).poidsmax<parent1.get(position1).poidcour) {
						parent1.get(position1).objets.addAll(rv);
						int valmin=Integer.MAX_VALUE;
						// je prends objet qui regle le poids et qui affecte v tres peu 
						for(objet o : parent1.get(position1).objets) {
							if(o.poids>=po ) {
								//on compare  avec precedente .
								if(valmin>o.valeur) {
								rv.removeAll(rv);
								//rv.remove(0);
							    valmin=o.valeur;
							    rv.add(o);//contient juste qui a min v et qui regle le poids 
								}
							}
						}
						 parent1.get(position1).objets.remove(rv.get(0));

					}
					if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour) {
						
                          objrest1.addAll(rv);
						// ici on regard pour le v si cest regle ok sinn 
						if(parent1.get(position1).valcour>=parent1.get(position1).valmin) {
							 l=false;
						     sol2.sacs.addAll(parent2);
		                     sol1.sacs.addAll(parent1);
		                     int f=calcul(sol1);
		                     sol2.valeur_total=f;
		                     f=calcul(sol2);
		                     sol1.valeur_total=f;
		                     croisement[y] = sol2;
		                     croisement[y+1]= sol1;
		                     y=y+2;
						}
						else {
						
						
						
						//on voit objets 
						//restant on essaye de regler v sans ffceter poids 
						//cest pour regler le v 
                          for(objet o: objrest1) {
                        	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
                        		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
                        			  parent1.get(position1).objets.add(o);
                        			  objrest1.remove(o);
                        			  l=false;
									  sol2.sacs.addAll(parent2);
					                     sol1.sacs.addAll(parent1);
					                     int f=calcul(sol2);
					                     sol2.valeur_total=f;
					                     f=calcul(sol1);
					                     sol1.valeur_total=f;
					                     croisement[y] = sol2;
					                     croisement[y+1]= sol1;
					                     y=y+2;
                        			  break;
                        		  }
                        	  }
                          }}
						//if v trigla ok sinn c bn laisse tomber 
						if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
							 l=false;
						     sol2.sacs.addAll(parent2);
		                     sol1.sacs.addAll(temp);
		                     int f=calcul(sol2);
		                     sol2.valeur_total=f;
		                     f=calcul(sol1);
		                     sol1.valeur_total=f;
		                     croisement[y] = sol2;
		                     croisement[y+1]= sol1;
		                     y=y+2;
						}
						
                          // ce comm faut lire !
                          //ajouter ca dans objets restant then chercher obj restants qui n'affecte
                          //pas le poids et regle v ajouter le sinn c bn pas de croisement 
                          
					}
					
				//celui la aussi jsp wchfih 
					//test si parent2 trigla si oui ok sinn on remets les rv et on retire now 
					//le poids qui va regler mais avec val minimaliste apr on 
					//ajoute dans objets restant et on va picker obj qui regle le v et n'affecte pas le poids 
				
				}else {
				//else si v de parent1  n'est pas ok ? 
				//poids +v nest pas ok 
				//on regle le poids apr si v est regler ok sinn on essaye de lui ajouter des trucs 
				//restant si ok ok sinn on laisse tomber
				int valmin=Integer.MAX_VALUE;
				// je prends objet qui regle le poids et qui affecte v tres peu 
				for(objet o : parent1.get(position1).objets) {
					if(o.poids>=(parent1.get(position1).poidcour-parent1.get(position1).poidsmax) ) {
						//on compare  avec precedente .
						if(valmin>o.valeur) {
						rv.removeAll(rv);
						//rv.remove(0);
					    valmin=o.valeur;
					    rv.add(o);//contient juste qui a min v et qui regle le poids 
						}
					}
				}
				 parent1.get(position1).objets.remove(rv.get(0));
					if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour) {
						
                        objrest1.addAll(rv);
						// ici on regard pour le v si cest regle ok sinn 
						if(parent1.get(position1).valcour>=parent1.get(position1).valmin) {
							 l=false;
						     sol2.sacs.addAll(parent2);
		                     sol1.sacs.addAll(parent1);
		                     int f=calcul(sol2);
		                     sol2.valeur_total=f;
		                     f=calcul(sol1);
		                     sol1.valeur_total=f;
		                     croisement[y] = sol2;
		                     croisement[y+1]= sol1;
		                     y=y+2;
						}
						else {
						
						
						
						//on voit objets 
						//restant on essaye de regler v sans ffceter poids 
						//cest pour regler le v 
                        for(objet o: objrest1) {
                      	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
                      		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
                      			  parent1.get(position1).objets.add(o);
                      			  objrest1.remove(o);
                      			  l=false;
								  sol2.sacs.addAll(parent2);
				                     sol1.sacs.addAll(parent1);
				                     int f=calcul(sol2);
				                     sol2.valeur_total=f;
				                     f=calcul(sol1);
				                     sol1.valeur_total=f;
				                     croisement[y] = sol2;
				                     croisement[y+1]= sol1;
				                     y=y+2;
                      			  break;
                      		  }
                      	  }
                        }}
						//if v trigla ok sinn c bn laisse tomber 
						if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
							 l=false;
						     sol2.sacs.addAll(parent2);
		                     sol1.sacs.addAll(temp);
		                     int f=calcul(sol2);
		                     sol2.valeur_total=f;
		                     f=calcul(sol1);
		                     sol1.valeur_total=f;
		                     croisement[y] = sol2;
		                     croisement[y+1]= sol1;
		                     y=y+2;
						}
						
					
						
					}
					
					}
				
				}else {
			        // si poids parent 2 ok mais v non donc on essaye de rajouter des obj de obj 
					//restant si regle ok sans affecter poids sinn on laisse tomber 
					 for(objet o: objrest1) {
                     	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
                     		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
                     			  parent1.get(position1).objets.add(o);
                     			  objrest1.remove(o);
                     			  l=false;
								  sol2.sacs.addAll(parent2);
				                     sol1.sacs.addAll(parent1);
				                     int f=calcul(sol2);
				                     sol2.valeur_total=f;
				                     f=calcul(sol1);
				                     sol1.valeur_total=f;
				                     croisement[y] = sol2;
				                     croisement[y+1]= sol1;
				                     y=y+2;
                     			  break;
                     		  }
                     	  }
                       }
						if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
							 l=false;
						     sol2.sacs.addAll(parent2);
		                     sol1.sacs.addAll(temp);
		                     int f=calcul(sol2);
		                     sol2.valeur_total=f;
		                     f=calcul(sol1);
		                     sol1.valeur_total=f;
		                     croisement[y] = sol2;
		                     croisement[y+1]= sol1;
		                     y=y+2;
						}
												
												
											}																		
									}else {
										//parent 1 et 2 les deux ont des prblm de poids ou val
										if(parent2.get(position2).poidsmax<parent2.get(position2).poidcour) {
											if(parent2.get(position2).valmin<=parent2.get(position2).valcour) {
										//j'essaye d'enlever sans affecter v sinn j'enleve et 
										//je rajoute de objets restant si ca marche pas c bn 
										
										int po=parent2.get(position2).poidcour-parent2.get(position2).poidsmax;
										int v=parent2.get(position2).valcour-parent2.get(position2).valmin;
										//
										
								for(objet o : parent2.get(position2).objets) {
								  if(o.valeur<=v && o.poids>=po ) {
									  //ici c ok on a regler le poids et val non affecte 
									  //on va l'enlever 
									  parent2.get(position2).objets.remove(o);
									  // imp : ajouter au objets restants
									  objrest2.add(o);
									 parent2.get(position2).poidcour=compterpoidsc(parent2.get(position2).objets);
									 parent2.get(position2).poidcour=comptervalc(parent2.get(position2).objets);
									  l=false;
									  sol2.objets_restant.addAll(objrest2);
									  sol1.sacs.addAll(parent1);
					                     sol2.sacs.addAll(parent2);
					                     int f=calcul(sol1);
					                     sol1.valeur_total=f;
					                     f=calcul(sol2);
					                     sol2.valeur_total=f;
					                     croisement[y] = sol1;
					                     croisement[y+1]= sol2;
					                     y=y+2;
					                     break;
										  }else {
							                  if(o.poids<po) {
								                   if(o.valeur<=v) {
													rv.add(o);    //ici jai sauvegarder on sait jms je dois rendre ces obj 
													parent2.get(position2).objets.remove(o);
													parent2.get(position2).poidcour=compterpoidsc(parent2.get(position2).objets);
												    parent2.get(position2).poidcour=comptervalc(parent2.get(position2).objets);
												    po=po-o.poids;	
												    v=v-o.valeur;
													  // then nchdo pos dyalo nahoh on cherche whdkher qui va nous regler le poids
													  
												  }
													  
											  }
										  }
								}
						
										
										
										//on fait combinaison d'objets 
										//ici si le poids s'est pas regl� donc on prends poids ki affecte le min possible le v 
										if(parent2.get(position2).poidsmax<parent2.get(position2).poidcour) {
											parent2.get(position2).objets.addAll(rv);
											int valmin=Integer.MAX_VALUE;
											// je prends objet qui regle le poids et qui affecte v tres peu 
											for(objet o : parent2.get(position2).objets) {
												if(o.poids>=po ) {
													//on compare  avec precedente .
													if(valmin>o.valeur) {
													rv.removeAll(rv);
													//rv.remove(0);
												    valmin=o.valeur;
												    rv.add(o);//contient juste qui a min v et qui regle le poids 
													}
												}
											}
											 parent2.get(position2).objets.remove(rv.get(0));

										}
										if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour) {
											
			                                  objrest2.addAll(rv);
											// ici on regard pour le v si cest regle ok sinn 
											if(parent2.get(position2).valcour>=parent2.get(position2).valmin) {
												 l=false;
											     sol1.sacs.addAll(parent1);
							                     sol2.sacs.addAll(parent2);
							                     int f=calcul(sol1);
							                     sol1.valeur_total=f;
							                     f=calcul(sol2);
							                     sol2.valeur_total=f;
							                     croisement[y] = sol1;
							                     croisement[y+1]= sol2;
							                     y=y+2;
											}
											else {
													
															
															
								//on voit objets restant on essaye de regler v sans ffceter poids 
								//cest pour regler le v 
				              for(objet o: objrest2) {
				                if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
				                if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
			        			  parent2.get(position2).objets.add(o);
			        			  objrest2.remove(o);
			        			  l=false;
								  sol1.sacs.addAll(parent1);
				                     sol2.sacs.addAll(parent2);
				                     int f=calcul(sol1);
				                     sol1.valeur_total=f;
				                     f=calcul(sol2);
				                     sol2.valeur_total=f;
				                     croisement[y] = sol1;
				                     croisement[y+1]= sol2;
				                     y=y+2;
			        			  break;
			                		  }
			                	  }
			                  }
				              }
								//if v trigla ok sinn c bn laisse tomber 
								if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
									 l=false;
								     sol1.sacs.addAll(parent1);
				                     sol2.sacs.addAll(temp2);
				                     int f=calcul(sol1);
				                     sol1.valeur_total=f;
				                     f=calcul(sol2);
				                     sol2.valeur_total=f;
				                     croisement[y] = sol1;
				                     croisement[y+1]= sol2;
				                     y=y+2;
								}
								
							
								
								
								
								
								
			                      // ce comm faut lire !
			                      //ajouter ca dans objets restant then chercher obj restants qui n'affecte
			                      //pas le poids et regle v ajouter le sinn c bn pas de croisement 
			                      
							}
										
							
						//celui la aussi jsp wchfih 
							//test si parent1 trigla si oui ok sinn on remets les rv et on retire now 
							//le poids qui va regler mais avec val minimaliste apr on 
							//ajoute dans objets restant et on va picker obj qui regle le v et n'affecte pas le poids 
							
							
							
							
						}else {
						//poids +v nest pas ok parent2
						//on regle le poids apr si v est regler ok sinn on essaye de lui ajouter des trucs 
						//restant si ok ok sinn on laisse tomber
						int valmin=Integer.MAX_VALUE;
						// je prends objet qui regle le poids et qui affecte v tres peu 
						for(objet o : parent2.get(position2).objets) {
							if(o.poids>=(parent2.get(position2).poidcour-parent2.get(position2).poidsmax) ) {
								//on compare  avec precedente .
								if(valmin>o.valeur) {
								rv.removeAll(rv);
								//rv.remove(0);
							    valmin=o.valeur;
							    rv.add(o);//contient juste qui a min v et qui regle le poids 
								}
							}
						}
						 parent2.get(position2).objets.remove(rv.get(0));
							if(parent2.get(position2).poidsmax>=parent2.get(position2).poidcour) {
								
			                    objrest2.addAll(rv);
								// ici on regard pour le v si cest regle ok sinn 
								if(parent2.get(position2).valcour>=parent2.get(position2).valmin) {
									 l=false;
								     sol1.sacs.addAll(parent1);
				                     sol2.sacs.addAll(parent2);
				                     int f=calcul(sol1);
				                     sol1.valeur_total=f;
				                     f=calcul(sol2);
				                     sol2.valeur_total=f;
				                     croisement[y] = sol1;
				                     croisement[y+1]= sol2;
				                     y=y+2;
								}
								else {
								
								
								
								//on voit objets 
								//restant on essaye de regler v sans ffceter poids 
								//cest pour regler le v 
			                    for(objet o: objrest2) {
			                  	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
			                  		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
			                  			  parent2.get(position2).objets.add(o);
			                  			  objrest2.remove(o);
			                  			  l=false;
										  sol1.sacs.addAll(parent1);
						                     sol2.sacs.addAll(parent2);
						                     int f=calcul(sol1);
						                     sol1.valeur_total=f;
						                     f=calcul(sol2);
						                     sol2.valeur_total=f;
						                     croisement[y] = sol1;
						                     croisement[y+1]= sol2;
						                     y=y+2;
			                  			  break;
			                  		  }
			                  	  }
			                    }}
								//if v trigla ok sinn c bn laisse tomber 
								if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
									 l=false;
								     sol1.sacs.addAll(parent1);
				                     sol2.sacs.addAll(temp2);
				                     int f=calcul(sol1);
				                     sol1.valeur_total=f;
				                     f=calcul(sol2);
				                     sol2.valeur_total=f;
				                     croisement[y] = sol1;
				                     croisement[y+1]= sol2;
				                     y=y+2;
								}
								
							
								
							}
							
							}
						
						}else {
					        // si poids parent 2 ok mais v non donc on essaye de rajouter des obj de obj 
							//restant si regle ok sans affecter poids sinn on laisse tomber 
						
							 for(objet o: objrest2) {
			                 	  if(o.poids<(parent2.get(position2).poidsmax-parent2.get(position2).poidcour)) {
			                 		  if(o.valeur >= (parent2.get(position2).valmin-parent2.get(position2).valcour)) {
			                 			  parent2.get(position2).objets.add(o);
			                 			  objrest2.remove(o);
			                 			  l=false;
										  sol1.sacs.addAll(parent1);
						                     sol2.sacs.addAll(parent2);
						                     int f=calcul(sol1);
						                     sol1.valeur_total=f;
						                     f=calcul(sol2);
						                     sol2.valeur_total=f;
						                     croisement[y] = sol1;
						                     croisement[y+1]= sol2;
						                     y=y+2;
			                 			  break;
			                 		  }
			                 	  }
			                   }
								if(parent2.get(position2).valcour<parent2.get(position2).valmin) {
									 l=false;
								     sol1.sacs.addAll(parent1);
				                     sol2.sacs.addAll(temp2);
				                     int f=calcul(sol1);
				                     sol1.valeur_total=f;
				                     f=calcul(sol2);
				                     sol2.valeur_total=f;
				                     croisement[y] = sol1;
				                     croisement[y+1]= sol2;
				                     y=y+2;
								}
							
							
						}
										if(parent1.get(position1).poidsmax<parent1.get(position1).poidcour) {
											if(parent1.get(position1).valmin<=parent1.get(position1).valcour) {
												//j'essaye d'enlever sans affecter v sinn j'enleve et 
												//je rajoute de objets restant si ca marche pas c bn 
												
												int po=parent1.get(position1).poidcour-parent1.get(position1).poidsmax;
												int v=parent1.get(position1).valcour-parent1.get(position1).valmin;
												//
												
												for(objet o : parent1.get(position1).objets) {
												  if(o.valeur<=v && o.poids>=po ) {
													  //ici c ok on a regler le poids et val non affecte 
													  //on va l'enlever 
													  parent1.get(position1).objets.remove(o);
													  // imp : ajouter au objets restants
													  objrest1.add(o);
													 parent1.get(position1).poidcour=compterpoidsc(parent1.get(position1).objets);
													 parent1.get(position1).poidcour=comptervalc(parent1.get(position1).objets);
													  l=false;
													  sol1.objets_restant.addAll(objrest1);
													  sol2.sacs.addAll(parent2);
									                     sol1.sacs.addAll(parent1);
									                     int f=calcul(sol1);
									                     sol2.valeur_total=f;
									                     f=calcul(sol2);
									                     sol1.valeur_total=f;
									                     croisement[y] = sol2;
									                     croisement[y+1]= sol1;
									                     y=y+2;
									                     break;
												  }else {
													  if(o.poids<po) {
														  if(o.valeur<=v) {
															  rv.add(o);    //ici jai sauvegarder on sait jms je dois rendre ces obj 
															  parent1.get(position1).objets.remove(o);
															  parent1.get(position1).poidcour=compterpoidsc(parent1.get(position1).objets);
														      parent1.get(position1).poidcour=comptervalc(parent1.get(position1).objets);
														po=po-o.poids;	
														v=v-o.valeur;
															  // then nchdo pos dyalo nahoh on cherche whdkher qui va nous regler le poids
															  
														  }
															  
													  }
												  }
												}
												
												
												
												//on fait combinaison d'objets 
												//ici si le poids s'est pas regl� donc on prends poids ki affecte le min possible le v 
												if(parent1.get(position1).poidsmax<parent1.get(position1).poidcour) {
													parent1.get(position1).objets.addAll(rv);
													int valmin=Integer.MAX_VALUE;
													// je prends objet qui regle le poids et qui affecte v tres peu 
													for(objet o : parent1.get(position1).objets) {
														if(o.poids>=po ) {
															//on compare  avec precedente .
															if(valmin>o.valeur) {
															rv.removeAll(rv);
															//rv.remove(0);
														    valmin=o.valeur;
														    rv.add(o);//contient juste qui a min v et qui regle le poids 
															}
														}
													}
													 parent1.get(position1).objets.remove(rv.get(0));

												}
												if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour) {
													
						                              objrest1.addAll(rv);
													// ici on regard pour le v si cest regle ok sinn 
													if(parent1.get(position1).valcour>=parent1.get(position1).valmin) {
														 l=false;
													     sol2.sacs.addAll(parent2);
									                     sol1.sacs.addAll(parent1);
									                     int f=calcul(sol1);
									                     sol2.valeur_total=f;
									                     f=calcul(sol2);
									                     sol1.valeur_total=f;
									                     croisement[y] = sol2;
									                     croisement[y+1]= sol1;
									                     y=y+2;
													}
													else {
													
													
													
													//on voit objets 
													//restant on essaye de regler v sans ffceter poids 
													//cest pour regler le v 
						                              for(objet o: objrest1) {
						                            	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
						                            		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
						                            			  parent1.get(position1).objets.add(o);
						                            			  objrest1.remove(o);
						                            			  l=false;
																  sol2.sacs.addAll(parent2);
												                     sol1.sacs.addAll(parent1);
												                     int f=calcul(sol2);
												                     sol2.valeur_total=f;
												                     f=calcul(sol1);
												                     sol1.valeur_total=f;
												                     croisement[y] = sol2;
												                     croisement[y+1]= sol1;
												                     y=y+2;
						                            			  break;
						                            		  }
						                            	  }
						                              }}
													//if v trigla ok sinn c bn laisse tomber 
													if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
														 l=false;
													     sol2.sacs.addAll(parent2);
									                     sol1.sacs.addAll(temp);
									                     int f=calcul(sol2);
									                     sol2.valeur_total=f;
									                     f=calcul(sol1);
									                     sol1.valeur_total=f;
									                     croisement[y] = sol2;
									                     croisement[y+1]= sol1;
									                     y=y+2;
													}
													
						                              // ce comm faut lire !
						                              //ajouter ca dans objets restant then chercher obj restants qui n'affecte
						                              //pas le poids et regle v ajouter le sinn c bn pas de croisement 
						                              
												}
												
												
											//celui la aussi jsp wchfih 
												//test si parent2 trigla si oui ok sinn on remets les rv et on retire now 
												//le poids qui va regler mais avec val minimaliste apr on 
												//ajoute dans objets restant et on va picker obj qui regle le v et n'affecte pas le poids 
											
											}else {
											//else si v de parent1  n'est pas ok ? 
											//poids +v nest pas ok 
											//on regle le poids apr si v est regler ok sinn on essaye de lui ajouter des trucs 
											//restant si ok ok sinn on laisse tomber
											int valmin=Integer.MAX_VALUE;
											// je prends objet qui regle le poids et qui affecte v tres peu 
											for(objet o : parent1.get(position1).objets) {
												if(o.poids>=(parent1.get(position1).poidcour-parent1.get(position1).poidsmax) ) {
													//on compare  avec precedente .
													if(valmin>o.valeur) {
													rv.removeAll(rv);
													//rv.remove(0);
												    valmin=o.valeur;
												    rv.add(o);//contient juste qui a min v et qui regle le poids 
													}
												}
											}
											 parent1.get(position1).objets.remove(rv.get(0));
												if(parent1.get(position1).poidsmax>=parent1.get(position1).poidcour) {
													
						                            objrest1.addAll(rv);
													// ici on regard pour le v si cest regle ok sinn 
													if(parent1.get(position1).valcour>=parent1.get(position1).valmin) {
														 l=false;
													     sol2.sacs.addAll(parent2);
									                     sol1.sacs.addAll(parent1);
									                     int f=calcul(sol2);
									                     sol2.valeur_total=f;
									                     f=calcul(sol1);
									                     sol1.valeur_total=f;
									                     croisement[y] = sol2;
									                     croisement[y+1]= sol1;
									                     y=y+2;
													}
													else {
													
													
													
													//on voit objets 
													//restant on essaye de regler v sans ffceter poids 
													//cest pour regler le v 
						                            for(objet o: objrest1) {
						                          	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
						                          		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
						                          			  parent1.get(position1).objets.add(o);
						                          			  objrest1.remove(o);
						                          			  l=false;
															  sol2.sacs.addAll(parent2);
											                     sol1.sacs.addAll(parent1);
											                     int f=calcul(sol2);
											                     sol2.valeur_total=f;
											                     f=calcul(sol1);
											                     sol1.valeur_total=f;
											                     croisement[y] = sol2;
											                     croisement[y+1]= sol1;
											                     y=y+2;
						                          			  break;
						                          		  }
						                          	  }
						                            }}
													//if v trigla ok sinn c bn laisse tomber 
													if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
														 l=false;
													     sol2.sacs.addAll(parent2);
									                     sol1.sacs.addAll(temp);
									                     int f=calcul(sol2);
									                     sol2.valeur_total=f;
									                     f=calcul(sol1);
									                     sol1.valeur_total=f;
									                     croisement[y] = sol2;
									                     croisement[y+1]= sol1;
									                     y=y+2;
													}
													
												
													
												}
												
												}
											
											}else {
										        // si poids parent 1 ok mais v non donc on essaye de rajouter des obj de obj 
												//restant si regle ok sans affecter poids sinn on laisse tomber 
												 for(objet o: objrest1) {
						                         	  if(o.poids<(parent1.get(position1).poidsmax-parent1.get(position1).poidcour)) {
						                         		  if(o.valeur >= (parent1.get(position1).valmin-parent1.get(position1).valcour)) {
						                         			  parent1.get(position1).objets.add(o);
						                         			  objrest1.remove(o);
						                         			  l=false;
															  sol2.sacs.addAll(parent2);
											                     sol1.sacs.addAll(parent1);
											                     int f=calcul(sol2);
											                     sol2.valeur_total=f;
											                     f=calcul(sol1);
											                     sol1.valeur_total=f;
											                     croisement[y] = sol2;
											                     croisement[y+1]= sol1;
											                     y=y+2;
						                         			  break;
						                         		  }
						                         	  }
						                           }
													if(parent1.get(position1).valcour<parent1.get(position1).valmin) {
														 l=false;
													     sol2.sacs.addAll(parent2);
									                     sol1.sacs.addAll(temp);
									                     int f=calcul(sol2);
									                     sol2.valeur_total=f;
									                     f=calcul(sol1);
									                     sol1.valeur_total=f;
									                     croisement[y] = sol2;
									                     croisement[y+1]= sol1;
									                     y=y+2;
													}
												
																			
																		}	
									}
								}
						
						}
							

					 }
					 }	 
					 }
				}
			
			}
		
	 }
	 
	// public sol[] croisement (sol[] pop,float r) {return null;}
	 public sol[] remplacer(sol[] pop,sol[] pop1,sol[] pop2) {
		 return null;
	 }
	 public typeretourga genetique_algo(int maxgen,int nbr_pop,Sacàdos[] sacs, objet[] objets) {
		


		long startTime = System.nanoTime();

		 sol [] pop =new sol [nbr_pop];
		for(int i =0;i<nbr_pop;i++) {
			pop[i]=generation_sol2(sacs, objets);
			
		}
		fitness(pop);
		for(int i=0;i<maxgen;i++) {
			    float ratec = 0.7f;
			   
			    sol [] enfantC=croisement(pop,ratec);
			    sol [] enfantC1=enfantC;
			    float ratem=1;
				sol [] enfantM= mutation (enfantC1,ratem);

				fitness(enfantM);

				pop=remplacer(pop,enfantC,enfantM);	
				

			
		}
		long endTime = System.nanoTime();

	    //Calculer le temps ecoule
	  long duration = endTime - startTime;
	double tempsEcouleEnSecondes = duration / 1_000_000_000.0; 
		  
		System.out.println("temps en s:"+tempsEcouleEnSecondes);
		
		System.out.println("affichage best sol");
		System.out.print("best sol:  ");
		for(int i=0;i<pop.length;i++) {
			
		}
		
		 return new typeretourga( pop[0], tempsEcouleEnSecondes);

	 }
	
	
	
}
