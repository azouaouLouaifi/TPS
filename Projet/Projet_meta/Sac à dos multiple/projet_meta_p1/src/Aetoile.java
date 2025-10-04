
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Aetoile {

	public static typeretourastar aetoile(objet [] obj,Sacàdos [] sacs) {
        long startTime = System.nanoTime();
        ArrayList<ArrayList<objet>> sol = new ArrayList<>();
		int niv_cour=0;
		int prof=0;
		int nbrn=0;
		int solcourante=0;
		//les sacs
		ArrayList<ArrayList<objet>> sac = new ArrayList<>();
		
		for(int i = 0; i < sacs.length; i++) {
		    ArrayList<objet> arr = new ArrayList<>();
		    sac.add(arr);
		}
		//au debut sac vide donc cette boucle pas la peine!
	for(int i=0;i<sacs.length;i++) {
		for(int j =0;j<obj.length;j++) {
			if(sacs[i].tab[j]==1) {
				sac.get(i).add(obj[j]);
			}
		}
		
	}
	// il faut que je compte le poids de chaque array et f de l'element
	int[] poidsCourant = compterpoidscourant(sac);
	System.out.println("avant compterf");

		int f=compterf(sacs,sac,obj,poidsCourant);
		System.out.println("apres compterf");

	//Deque<Pile2> ouvert = new ArrayDeque<>();
	Pile2 element_pile=new Pile2(niv_cour,f,sac,poidsCourant);
	PriorityQueue<Pile2> ouvert = new PriorityQueue<>(Comparator.comparingInt(Pile2 -> Pile2.f));
       
	ouvert.add(element_pile);
	int nbfaux=0;
	int nbfils=0;
	boolean trouve=false;
	while (!ouvert.isEmpty() && trouve==false) 
	{
		element_pile= ouvert.poll();
		//si ce n'est pas un etat finale donc on lui fait ses fils sinn si etat finale ou bien nbfils=nbfaux on evalue;
		
		//je somme les length t3 ss arrays nchof ida = nb objet 
		niv_cour=element_pile.niv;
		if(niv_cour>prof)  prof=niv_cour;

		int nb=0;
				for(int i=0;i<element_pile.sacs.size();i++) {
					nb +=element_pile.sacs.get(i).size();
				}
				System.out.println("nb"+nb);
				//ou nbfaux!=nbfils?
		if(nb!=obj.length) {
			System.out.println("ikram");
			// on va creer les fils 
			// on doit compter nbfils
			//on doit compter les nbfaux 
			nbfils=0;
			nbfaux=0;
		    for(int i=0;i<obj.length;i++) {
		    	
		    		int k=0;
		    		
		    		boolean a=true;
		    		int cpt=0;
		    		while(k<element_pile.sacs.size() && a==true) {
		    			
		    		
		    		if(!element_pile.sacs.get(k).contains(obj[i])) {
		    		cpt++;
		    		
		    		}
		    		
		    		
		    		
		    		k++;
		    		}
		    		
		    		if (cpt==element_pile.sacs.size()) {
		    		
			    	
			    			//ici on va creer les fils mais a chaque creation on evalue le fils 
			    			//si fils valide je fais add ouvert
			    			//si ce n'est pas nn
			    			//faut faire nbfaux
			    			//quand on sort si nbfaux==nbfils donc on a une sol finale 
			    			
			    			
			    			
			    			
			    			for(int j=0;j<element_pile.sacs.size();j++) {
			    				nbfils++;
			    				//je cree le array donc un seule sac je l'evalue par rapport max
			    				//si il depasse poid max jincremente nbfaux 
			    							    				
			    				ArrayList<objet> sac1 = new ArrayList<>(element_pile.sacs.get(j));

			    				sac1.add(obj[i]);
			    				
			    				if(element_pile.poidscour[j]+obj[i].poids >sacs[j].poidsmax) {
			    					nbfaux++;
			    				}else {
			    					int[] poidcour = Arrays.copyOf(element_pile.poidscour, element_pile.poidscour.length);
			    					poidcour[j]+=obj[i].poids;
			    					
			    					ArrayList<ArrayList<objet>> sac2 = new ArrayList<>();

			    					// Parcourir chaque liste interne dans element_pile.sacs
			    					for (ArrayList<objet> listeInterne : element_pile.sacs) {
			    					    // Créer une nouvelle liste pour stocker les éléments copiés
			    					    ArrayList<objet> nouvelleListe = new ArrayList<>(listeInterne);
			    					    // Ajouter la nouvelle liste à sac2
			    					    sac2.add(nouvelleListe);
			    					}

			    					
			    					
			    					//sac2=element_pile.sacs;
			    					// Remplacez l'élément à la position j dans sac2 par les éléments de sac1
			    					sac2.set(j, sac1);
			    					int fnv=compterf(sacs,sac2,obj,poidcour);
			    					//Deque<Pile2> ouvert = new ArrayDeque<>();
			    					//Pile2 element_pile=new Pile2(niv_cour,fnv,sac,poidsCourant);
			    					//je l'ajoute a ouvert 
			    					niv_cour++;
			    					Pile2 element_pile2=new Pile2(niv_cour,fnv,sac2,poidcour);

					    			ouvert.add(element_pile2);
					    			nbrn++;
			    					
			    				}

			    			}
			    			
			    				
			    			}
				    	}
		 	
				    } // donc ici comme ya else on rentre pas !!!!
	
				    	
				    	//evaluation  on evalue li popinah  m ouvert element_pile
				    	if(nbfaux==nbfils || nb==obj.length ) {
				    		trouve =true;
				    		int y=0;
				    		//je compte les valeurs des objets qui sont dans element_pile.sacs
				    		for(int j=0;j<element_pile.sacs.size();j++) {
				    			for( int i=0;i<element_pile.sacs.get(j).size();i++) {
				    			y+=element_pile.sacs.get(j).get(i).valeur;
				    		}
				    		}
				    		
				    		solcourante=y;
				    		sol=element_pile.sacs;
				    	
				    	
				    }
		
	}
	
	long endTime = System.nanoTime();

    //Calculer le temps �coul�
  long duration = endTime - startTime;
	if(sol.size()!=0) {
	System.out.println("");
	System.out.print("la valeurtotale : "+solcourante+"   le sac  :");
	System.out.println("");
	
	for(int i = 0; i < sol.size(); i++) {
	    System.out.println("sac : " + i);
	    
	    // Obtenez la liste à la position i
	    ArrayList<objet> listeObjets = sol.get(i);
	    
	    // Parcourez les éléments de la liste actuelle
	    for (objet objet : listeObjets) {
	        System.out.println(objet.poids);
	        System.out.println(objet.valeur);// Affichez chaque objet de la liste
	    }
	}

		
		/*System.out.println("");
		for(int j=0;j<obj.length;j++) {
		System.out.print("obj  : "+j+"   val  :"+obj[j].valeur);}
		System.out.println("");
		
	}*/
	System.out.println("");
	
	for(int i = 0; i < sacs.length; i++) {
		System.out.print("sac  : "+i+"   poidmax  :"+sacs[i].poidsmax);
		System.out.println("");
		System.out.println("poidcour : "+i+"est : "+element_pile.poidscour[i]);
	}
	}
	else System.out.println("pas sol");
	return new typeretourastar(duration, nbrn, prof, sol, solcourante);
	
	}
	
	 
	
	public static  int compterf(Sacàdos[] sacs, ArrayList<ArrayList<objet>> listObjet,objet [] obj,int[] poids) {
	   
		
		// Calculer g : la valeure totale d'objets dans listObjet
		System.out.println("je suis rentrer ");
	    int g = 0;
	    int h=0;
	    for (ArrayList<objet> sac : listObjet) {
	        		
    		
    			for( int i=0;i<sac.size();i++) {
    			g+=sac.get(i).valeur;
    		}
    		
	        // Ajoute le nombre d'objets dans chaque sac
	    }
		int cpt;
	    for(int i=0;i<obj.length;i++) {
	    	cpt=0;
	    	//si objet existe dans les sacs 
	    	for (ArrayList<objet> sac : listObjet) {
	    	    if (!sac.contains(obj[i])) {
	    	        cpt++;
	    	      
	    	    }
	    	}
	    	if(cpt==sacs.length) {
	    		int k=0;
	    		boolean a=true;
	    		//on compte h 
	    		while(k<sacs.length && a==true) {
	    		if(sacs[k].poidsmax-poids[k]>=obj[i].poids) {
	    			h+=obj[i].valeur;
	    			a=false;
	    		}
	    		else k++;
	    	}
	    	}
	    }
	    
	    // Calculer h : valtotale de lelement -poids restant 
	   /* int h = 0;
	    for (int i = 0; i < sacs.length; i++) {
	        int poidsMaxSac = sacs[i].getPoidsmax(); // Supposons qu'il existe une méthode getPoidsMax() dans la classe Sacàdos
	        int[] poidsCourant = compterpoidscourant(listObjet); // Utilisez votre méthode compterpoidscourant pour obtenir les poids courants

	        // Calculer le poids restant pour chaque sac
	        int poidsRestantSac = poidsMaxSac;
	        for (objet obj : listObjet.get(i)) {
	            poidsRestantSac -= obj.getPoids(); // Soustrait le poids de chaque objet du poids maximum du sac
	        }

	        // Ajouter le poids restant de ce sac au total de h
	        h += poidsRestantSac;
	    }

	    //valeur totale de l'element -poids restant 
	    h=g-h;*/
	    
	    
	    
	    // Calculer f : g + h
	    int f = g + h;
	    System.out.println("je suis sortie");
	    return f;
	}

	public static int[] compterpoidscourant(ArrayList<ArrayList<objet>> listObjet) {
	    int[] poids = new int[listObjet.size()]; // Initialisation du tableau avec la taille de la liste d'objets
	    
	    // Boucle pour calculer les poids actuels des objets dans la pile
	    for (int i = 0; i < listObjet.size(); i++) {
	        ArrayList<objet> sac = listObjet.get(i); // Récupération de l'ArrayList d'objets du sac i
	        int poidsSac = 0;
	        
	        // Calcul du poids total des objets dans le sac
	        for (objet obj : sac) {
	            poidsSac += obj.getPoids(); // Supposons qu'il existe une méthode getPoids() dans la classe Objet
	        }
	        
	        poids[i] = poidsSac; // Enregistrement du poids total dans le tableau
	    }
	    
	    return poids; // Retourne le tableau contenant les poids actuels des objets dans la pile
	}
	

	
	
	
	
	}