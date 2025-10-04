import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Auction {
    public static void main(String[] args) {
        // Initialize the JADE environment
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createMainContainer(p);

        try {
            // Nombre d'acheteurs
            int nombreAcheteurs = 10;

            // Create and start the seller agent
            Object[] sellerArgs = new Object[]{nombreAcheteurs};
            AgentController sellerAgent = cc.createNewAgent("vendeur", SellerAgent.class.getName(), sellerArgs);
            sellerAgent.start();

            // Create and start the buyer agents
            for (int i = 1; i <= nombreAcheteurs; i++) {
                AgentController buyerAgent = cc.createNewAgent("acheteur" + i, BuyerAgent.class.getName(), null);
                buyerAgent.start();
            }
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
