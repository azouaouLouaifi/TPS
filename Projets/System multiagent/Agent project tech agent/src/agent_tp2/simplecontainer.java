package agent_tp2;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.StaleProxyException;
public class simplecontainer {
	public static void main(String[] args) {
		try {
			Runtime rt=Runtime.instance();
			ProfileImpl p= new ProfileImpl(false);
			p.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer container=rt.createAgentContainer(p); 
			container.start();
		}catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
