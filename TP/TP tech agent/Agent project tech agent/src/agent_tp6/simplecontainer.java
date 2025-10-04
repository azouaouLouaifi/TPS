package agent_tp6;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.StaleProxyException;
public class simplecontainer {
	public static void main(String[] args) {
	
			Runtime rt=Runtime.instance();
			ProfileImpl p= new ProfileImpl(false);
			p.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer container=rt.createAgentContainer(p); 
			try {
				container.start();
			} catch (ControllerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*AgentController ag2=container.createNewAgent("agent2","agent_tp3.a_sender", null);
			ag2.start();	*/	
		
	}
}
