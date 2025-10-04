	import jade.core.AID;
	import jade.core.Agent;
	import jade.core.behaviours.TickerBehaviour;
	import jade.core.behaviours.CyclicBehaviour;
	import jade.lang.acl.ACLMessage;
	import jade.lang.acl.MessageTemplate;
	
	import java.util.ArrayList;
	import java.util.List;
	
	public class SellerAgent extends Agent {
	    private String produit = "Ordinateur portable";
	    private int prixInitial = 800;
	    private int prixDeReserve = 1500;  // Connu seulement du vendeur
	    private int prixActuel;
	    private AID meilleurOffreur;
	    private List<AID> acheteurs;
	    private long finEnchere;
	    int nbr=0;
	
	    @Override
	    protected void setup() {
	        System.out.println("Agent vendeur " + getAID().getName() + " est prêt et je mets en vente le produit :"+produit );
	        acheteurs = new ArrayList<>();
	        prixActuel = prixInitial;
	        finEnchere = System.currentTimeMillis() + 15000;  // Limite de temps de l'enchère : 30 secondes
	        Object[] args = getArguments();
	       
	            int nombreAcheteurs = (int) args[0];
	            // Utiliser le nombre d'acheteurs comme nécessaire
	            this.nbr=nombreAcheteurs;
	            for (int o=1;o<= nombreAcheteurs;o++) {
	                acheteurs.add(new AID("acheteur"+o, AID.ISLOCALNAME));
	
	            
	        } 
	        // Trouver tous les agents acheteurs
	        
	       
	
	
	        addBehaviour(new ComportementEnchere(this, 2000));  // Répéter toutes les 2 secondes
	        addBehaviour(new RecevoirPropositionsComportement());
	    }
	
	    private class ComportementEnchere extends TickerBehaviour {
	        public ComportementEnchere(Agent a, long period) {
	            super(a, period);
	        }
	
	        @Override
	        protected void onTick() {
	            if (System.currentTimeMillis() > finEnchere) {
	            	try {
	                    Thread.sleep(3000); 
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                finEnchere();
	                stop();
	            } else {
	                // Informer les acheteurs de la meilleure offre
	                ACLMessage informer = new ACLMessage(ACLMessage.INFORM);
	                informer.setContent("Meilleure offre:" + prixActuel);
	                for (AID acheteur : acheteurs) {
	                    informer.addReceiver(acheteur);
	                }
	                myAgent.send(informer);
	            }
	        }
	
	        private void finEnchere() {
	            if (meilleurOffreur != null && prixActuel >= prixDeReserve) {
	                ACLMessage accepter = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
	                accepter.addReceiver(meilleurOffreur);
	                accepter.setContent("Produit vendu au prix de: " + prixActuel);
	                myAgent.send(accepter);
	                System.out.println("Produit vendu à " + meilleurOffreur.getLocalName() + " pour " + prixActuel);
	            } else {
	                System.out.println("L'enchère s'est terminée sans atteindre le prix de réserve.");
	            }
	        }
	    }
	
	    private class RecevoirPropositionsComportement extends CyclicBehaviour {
	        private int nombreAcheteurs = 0; 

	
	        @Override
	        public void action() {
	            ACLMessage reponse = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
	            if (reponse != null) {
	                int offrePrix = Integer.parseInt(reponse.getContent());
	                if (offrePrix > prixActuel) {
	                    prixActuel = offrePrix;
	                    meilleurOffreur = reponse.getSender();
	                   // System.out.println("Nouvelle meilleure offre: " + prixActuel + " par " + meilleurOffreur.getLocalName());
	                }
	
	                nombreAcheteurs++;
	
	                // Vérifier si tous les acheteurs ont envoyé leurs offres
	                if (nombreAcheteurs == nbr) {
	                    // Afficher la meilleure offre
	                    System.out.println("Meilleure offre: " + prixActuel+ " par " + meilleurOffreur.getLocalName());
	                    nombreAcheteurs = 0;
	                    System.out.println("-------------------------");
	                }
	              /*  try {
	                    Thread.sleep(2000); // Attendre 2 secondes
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                System.out.println("Meilleure offre:2 " + prixActuel+ " par " + meilleurOffreur.getLocalName());
	                System.out.println("-------------------------");
	*/
	            } else {
	                block();
	            }
	        }
	    }
	
	}
