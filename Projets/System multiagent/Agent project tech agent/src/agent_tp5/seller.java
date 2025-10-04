package agent_tp5;

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class seller extends Agent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	product p=new product();
	ACLMessage m;
	protected void setup() {
		p.price=1000;
		p.name="Keyboard";
		m=new ACLMessage(ACLMessage.INFORM);
		m.addReceiver(new AID("Buyer",AID.ISLOCALNAME));
		try {
			m.setContentObject(p);
			m.setLanguage("Javaserialisation");
			send(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
