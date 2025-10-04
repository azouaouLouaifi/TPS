import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Random;

public class BuyerAgent extends Agent {
    private int budget; 

    @Override
    protected void setup() {
       // System.out.println("Agent acheteur " + getAID().getName() + " est prêt.");

        budget = (int) (1000 + Math.random() * 2000);
        System.out.println("Agent acheteur " + getAID().getName() + " est mon budget:  "+budget);
        addBehaviour(new RecevoirOffresComportement());
    }

    private class RecevoirOffresComportement extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage informer = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            if (informer != null) {
                String contenu = informer.getContent();
                String[] parties = contenu.split(":");
                int meilleureOffre = Integer.parseInt(parties[1]);
                Random random = new Random();

                int nombreAleatoire = random.nextInt(1000);
                int nouvelleOffre = meilleureOffre + (int) (Math.random() * nombreAleatoire);

                // Vérifier si la nouvelle offre dépasse le budget
                if (nouvelleOffre <= budget) {
                    // Faire une proposition avec la nouvelle offre
                    ACLMessage proposer = new ACLMessage(ACLMessage.PROPOSE);
                    proposer.addReceiver(informer.getSender());
                    proposer.setContent(String.valueOf(nouvelleOffre));
                    myAgent.send(proposer);
                    System.out.println("Acheteur " + getLocalName() + " propose " + nouvelleOffre);
                } else {
                    // Afficher un message si le budget est dépassé
                	 ACLMessage proposer = new ACLMessage(ACLMessage.PROPOSE);
                     proposer.addReceiver(informer.getSender());
                     proposer.setContent(String.valueOf(0));
                     myAgent.send(proposer);
                    System.out.println("Acheteur " + getLocalName() + " ne peut plus enchérir, budget dépassé.");
                	
                }
            } else {
                block();
            }
        }
    }
}
