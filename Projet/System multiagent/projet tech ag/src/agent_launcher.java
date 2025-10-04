import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import jade.wrapper.AgentController;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
public class agent_launcher {
	public static void main(String[] args) {
		try {
			Runtime rt=Runtime.instance();
			ProfileImpl p= new ProfileImpl();
			p.setParameter(Profile.LOCAL_HOST, "localhost");
			p.setParameter(Profile.LOCAL_PORT, "1099");
			p.setParameter(Profile.GUI, "true");
			ContainerController mc=rt.createMainContainer(p);
			AgentController ag1;		
			Object[] arg1={"sac","18800"};
			Object[] arg2={"pc","1800"};
			ag1 = mc.createNewAgent("buyer1","agent_tp1.agent_display", arg1);
			ag1.start();
			AgentController ag2;		
			ag2 = mc.createNewAgent("buyer2","agent_tp1.agent_display", arg2);
			ag2.start();
			AgentController ag3;		
			ag3 = mc.createNewAgent("agent1","agent_tp1.agent1", null);
			ag3.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}

}
