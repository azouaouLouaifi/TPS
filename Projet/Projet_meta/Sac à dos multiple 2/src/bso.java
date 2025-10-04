import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class bso {


	
	
	public class ListeTabou {
	    private List<sol> elementsInterdits;

	    public ListeTabou() {
	        this.elementsInterdits = new ArrayList<>();
	    }
	    public sol get(int i) {
	    	return this.elementsInterdits.get(i);
	    }
	    // Méthode pour ajouter un élément à la liste tabou
	    public void ajouterElementInterdit(sol element) {
	        this.elementsInterdits.add(element);
	    }
	    public int size() {
	    	return this.elementsInterdits.size();
	    }
	    // Méthode pour vérifier si un élément est dans la liste tabou
	    public boolean estInterdit(sol element) {
	    	boolean trouver=false;
	    	int i = 0;
	    	
	    	while(i<this.elementsInterdits.size() && !trouver) {
	    		if (elementsInterdits.get(i).poid_total== element.poid_total) {
	    			if (elementsInterdits.get(i).valeur_total == element.valeur_total) {
	    				int t=0;
	    				int k=0;
	    				while(k<elementsInterdits.get(i).sacs.size()) {
	    					int r=0;
	    					int m=0;
	    					while(r<elementsInterdits.get(i).sacs.get(k).objets.size()
	    							&& r<element.sacs.get(k).objets.size()) {
	    						if(element.sacs.get(k).objets.get(r).num == 
	    								elementsInterdits.get(i).sacs.get(k).objets.get(r).num) {
	    							m++;
	    						}
	    					r++;
	    					}
	    					if(m==elementsInterdits.get(i).sacs.get(k).objets.size()) t++;
	    					k++;
	    				}
	    				if (t==elementsInterdits.get(i).sacs.size())
	    					trouver=true;
	    				
	    			}
	    		}
	    		i++;
	    		}
	    	if (trouver)
	    		return true;
	    	else return false;
	    }
	    
	    
	    
	    
	    
	    
	}
	
	
	
	

	
	
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
			
			// maximiser les valeur 
			for(int i=0;i<sac1.size();i++) {
				 for(int j=0;j<objets_rest.size();j++) {
					 if(objets_rest.get(j).poids+sac1.get(i).poidcour<=sac1.get(i).poidsmax) {						 
						 ajouter_obj(sac1.get(i), objets_rest.get(j));					
						 objets_rest.remove(j);
				 }
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
	 
	 
	 public void mutation(sol sol,int p,int k ) {
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
		 
		 
		 
		 
		 
		 
		 
	  
		 
		 
		
	
	
	/* public sol[] generation_solution2(sol Sref,int k,int flip) {
		 sol [] solution=new sol[k];
		 int nbr=0;
		 int p=0;
		 int h=1;
		 
		 while(nbr<k) {
				
			 
			 	ArrayList<Sac> sacs = new ArrayList<>();
				
				
				for(int i=0;i<Sref.sacs.size();i++) {
					ArrayList<objet> objets= new ArrayList<>();
					for(int i1=0;i1<Sref.sacs.get(i).objets.size();i1++) {
						 objets.add(Sref.sacs.get(i).objets.get(i1));						
					}
					sacs.add(new Sac(Sref.sacs.get(i).poidsmax , Sref.sacs.get(i).valmin, Sref.sacs.get(i).poidcour, Sref.sacs.get(i).valcour, objets));
				}
				
				sol nv =  new sol(Sref.fitness, Sref.poid_total, sacs, Sref.valeur_total, Sref.objets_restant);
				
					
					
				
			
				while(flip+h<nv.sacs.size()) {
					mutation(nv,p,h+flip);
					// h=p
					p=flip+h+1;
					h=h+3;
				}

				
					int poit1=0;
					int val1 =0;
					for(int i1=0;i1<nv.sacs.size();i1++) {
						
						 poit1=nv.sacs.get(i1).poidcour+poit1;
						 val1=val1+nv.sacs.get(i1).valcour;
						
					}
					
					nv.poid_total=poit1;
					nv.valeur_total=val1;
					// System.out.println("");
						//System.out.println("poid t  "+poit1+ "   val"+val1);
				solution[nbr]=nv;
				nbr++;
				p=nbr;
				h=nbr+1;
		 }
		 
		 
		return solution;
		 
		 
	 }*/
	 public sol[] generation_solution(sol Sref,int k,int flip) {
		 sol [] solution=new sol[k];
		 int nbr=0;
		 int p=0;
		 int h=1;
		 
		 while(nbr<k) {
				
			 
			 	ArrayList<Sac> sacs = new ArrayList<>();
				
				
				for(int i=0;i<Sref.sacs.size();i++) {
					ArrayList<objet> objets= new ArrayList<>();
					for(int i1=0;i1<Sref.sacs.get(i).objets.size();i1++) {
						 objets.add(Sref.sacs.get(i).objets.get(i1));						
					}
					sacs.add(new Sac(Sref.sacs.get(i).poidsmax , Sref.sacs.get(i).valmin, Sref.sacs.get(i).poidcour, Sref.sacs.get(i).valcour, objets));
				}
				
				sol nv =  new sol(Sref.fitness, Sref.poid_total, sacs, Sref.valeur_total, Sref.objets_restant);
				
					
					
				
			
				while(flip+h<nv.sacs.size()) {
					mutation(nv,p,h+flip);
					// h=p
					p=flip+h+1;
					h=h+3;
				}

				// effectuer la recherche locale
				
					int poit1=0;
					int val1 =0;
					for(int i1=0;i1<nv.sacs.size();i1++) {
						
						 poit1=nv.sacs.get(i1).poidcour+poit1;
						 val1=val1+nv.sacs.get(i1).valcour;
						
					}
					sol meil=nv;
					for(int k1=0;k1<(int)nv.sacs.size()/2;k1++) {
						
					 	ArrayList<Sac> sacs1 = new ArrayList<>();
						for(int i=0;i<meil.sacs.size();i++) {
							ArrayList<objet> objets= new ArrayList<>();
							for(int i1=0;i1<meil.sacs.get(i).objets.size();i1++) {
								 objets.add(meil.sacs.get(i).objets.get(i1));						
							}
							sacs1.add(new Sac(meil.sacs.get(i).poidsmax , meil.sacs.get(i).valmin, meil.sacs.get(i).poidcour, meil.sacs.get(i).valcour, objets));
						}
						
						sol nv1 =  new sol(meil.fitness, meil.poid_total, sacs1, meil.valeur_total, meil.objets_restant);
						Random random = new Random();
						int p2;
						int p1;
						do {
						  p1=random.nextInt(nv1.sacs.size())-1;
						 
						 p2=random.nextInt(nv1.sacs.size())-1;
						 if(p1==-1) 
								p1=0;
							
							if(p2==-1) 
								p2=0;
						} while(p1==p2);
						
						

						System.out.println(p1+"  "+p2);
						
						mutation(nv1,p1,p2);
						// compter les val de nouvel mutation
						int poit11=0;
						int val11 =0;
						for(int i1=0;i1<nv1.sacs.size();i1++) {
							
							 poit11=nv1.sacs.get(i1).poidcour+poit11;
							 val11=val11+nv1.sacs.get(i1).valcour;
							
						}
						nv1.poid_total=poit1;
						nv1.valeur_total=val1;
						// mettre a jour meilleur
						if (val11>meil.valeur_total) meil =nv1;
						else {
							if (poit11<meil.poid_total) {
								 meil =nv1;
							}
						}
						
						
					}
					
					
					// System.out.println("");
						//System.out.println("poid t  "+poit1+ "   val"+val1);
				solution[nbr]=meil;
				nbr++;
				p=nbr;
				h=nbr+1;
		 }
		 
		 
		return solution;
		 
		 
	 }
	 
	 public sol choisir_meilleur(sol [] sols) {
		
		int val=sols[0].valeur_total;
		int poid=sols[0].valeur_total;
		int pos=0;
		for(int i=0;i<sols.length;i++) {
			if(sols[i].valeur_total>val) {
				val=sols[i].valeur_total;
				 poid=sols[i].valeur_total;
				 pos=i;
				 
			}
			else {
				if(sols[i].valeur_total==val) {
					if(sols[i].poid_total<poid) {
						val=sols[i].valeur_total;
						 poid=sols[i].valeur_total;
						 pos=i;
					}
				}
			}
		}
		 
		 
		 return sols[pos];
		 
		 
	 }
	 
	 
	 
	 
	 typeretourbso bso1(Sacàdos[] sacs, objet[] objets,int maxit,int k,int flip) {
		 long startTime = System.nanoTime();
		 ListeTabou liste=new ListeTabou();
		 System.out.println("avant ");
		 sol sref=generation_sol2(sacs, objets);
		 System.out.println("apres ");

		 sol solinit=sref;
		 int maxchance=10;
		 int i=0;
		 liste.ajouterElementInterdit(sref);
		 while(i<maxit ) {
			 
			 sol [] solution=new sol[k];
			 
			System.out.println("generation:"+i);
			 
			 solution=generation_solution(sref, k, flip);
			 sol best=new sol();
			 best=choisir_meilleur(solution);
			// System.out.println("meilleur sol  "+best.poid_total+"    "+best.valeur_total);
			 
			 
			 if(best.valeur_total>sref.valeur_total || best.poid_total<sref.poid_total ) {
				 sref=best;
				 maxchance=10; 
			 }
			 else {
				 maxchance--;
				 if(maxchance>0) {
					 sref=best;
				 }
				 else {
					 int pos=0;
					 int meilleur=0;
					 for(int j=0;j<solution.length;i++) {
						 int k1=0;
						 int m=0;
							while(k1<solution[j].sacs.size()) {
		    					int r=0;
		    					
		    					while(r<solution[j].sacs.get(k1).objets.size()
		    							&& r<sref.sacs.get(k1).objets.size()) {
		    						if(solution[j].sacs.get(k1).objets.get(r).num != 
		    								sref.sacs.get(k1).objets.get(r).num) {
		    							m++;
		    						}
		    					r++;
		    					}
		    		
		    					k1++;
		    				}
							if (m>meilleur) {
								pos=j;
								meilleur=m;
							}
					 }
					 sref=solution[pos];
				 }
			 }
			 if(!liste.estInterdit(sref)) {
				 liste.ajouterElementInterdit(sref);
			 }
			 i++;
			 
		 }
		 
		 
		 int val=liste.get(0).valeur_total  ;
			int poid=liste.get(0).valeur_total;
			int pos=0;
			for(int i1=0;i1<liste.size();i1++) {
				if(liste.get(i1).valeur_total>val) {
					val=liste.get(i1).valeur_total;
					 poid=liste.get(i1).valeur_total;
					 pos=i1;
					 
				}
				else {
					if(liste.get(i1).valeur_total==val) {
						if(liste.get(i1).poid_total<poid) {
							val=liste.get(i1).valeur_total;
							 poid=liste.get(i1).valeur_total;
							 pos=i1;
						}
					}
				}
			}
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			
			double tempsEcouleEnSecondes = duration / 1_000_000_000.0;
			System.out.println("le temps:"+tempsEcouleEnSecondes);
			System.out.println("init sol   :  p:"+solinit.poid_total+" v:"+solinit.valeur_total);

			System.out.println("meilleur sol   :  p:"+liste.get(pos).poid_total+" v:"+liste.get(pos).valeur_total);
		 return new typeretourbso(solinit, liste.get(pos), tempsEcouleEnSecondes);
		 
	 }
	 
	 
	 
	 
	 
	 
	/* typeretourbso bso2(Sacàdos[] sacs, objet[] objets,int maxit,int k,int flip,sol sref) {
		 long startTime = System.nanoTime();
		 ListeTabou liste=new ListeTabou();
		 System.out.println("avant ");
		 //sol sref=generation_sol2(sacs, objets);
		 System.out.println("apres ");

		 sol solinit=sref;
		 int maxchance=10;
		 int i=0;
		 liste.ajouterElementInterdit(sref);
		 while(i<maxit ) {
			 
			 sol [] solution=new sol[k];
			 
			System.out.println("generation:"+i);
			 
			 solution=generation_solution2(sref, k, flip);
			 sol best=new sol();
			 best=choisir_meilleur(solution);
			// System.out.println("meilleur sol  "+best.poid_total+"    "+best.valeur_total);
			 
			 
			 if(best.valeur_total>sref.valeur_total || best.poid_total<sref.poid_total ) {
				 sref=best;
				 maxchance=10; 
			 }
			 else {
				 maxchance--;
				 if(maxchance>0) {
					 sref=best;
				 }
				 else {
					 int pos=0;
					 int meilleur=0;
					 for(int j=0;j<solution.length;i++) {
						 int k1=0;
						 int m=0;
							while(k1<solution[j].sacs.size()) {
		    					int r=0;
		    					
		    					while(r<solution[j].sacs.get(k1).objets.size()
		    							&& r<sref.sacs.get(k1).objets.size()) {
		    						if(solution[j].sacs.get(k1).objets.get(r).num != 
		    								sref.sacs.get(k1).objets.get(r).num) {
		    							m++;
		    						}
		    					r++;
		    					}
		    		
		    					k1++;
		    				}
							if (m>meilleur) {
								pos=j;
								meilleur=m;
							}
					 }
					 sref=solution[pos];
				 }
			 }
			 if(!liste.estInterdit(sref)) {
				 liste.ajouterElementInterdit(sref);
			 }
			 i++;
			 
		 }
		 
		 
		 int val=liste.get(0).valeur_total  ;
			int poid=liste.get(0).valeur_total;
			int pos=0;
			for(int i1=0;i1<liste.size();i1++) {
				if(liste.get(i1).valeur_total>val) {
					val=liste.get(i1).valeur_total;
					 poid=liste.get(i1).valeur_total;
					 pos=i1;
					 
				}
				else {
					if(liste.get(i1).valeur_total==val) {
						if(liste.get(i1).poid_total<poid) {
							val=liste.get(i1).valeur_total;
							 poid=liste.get(i1).valeur_total;
							 pos=i1;
						}
					}
				}
			}
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			
			double tempsEcouleEnSecondes = duration / 1_000_000_000.0;
			System.out.println("le temps:"+tempsEcouleEnSecondes);
			System.out.println("init sol   :  p:"+solinit.poid_total+" v:"+solinit.valeur_total);

			System.out.println("meilleur sol   :  p:"+liste.get(pos).poid_total+" v:"+liste.get(pos).valeur_total);
		 return new typeretourbso(solinit, liste.get(pos), tempsEcouleEnSecondes);
		 
	 }*/
			
}
