package agent_tp5;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class buyer extends Agent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {
		addBehaviour(new CyclicBehaviour(this) {
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage m;
				product p;
				m=receive();
				if(m!=null) {
					try {
						p=(product)m.getContentObject();
						System.out.println("I am "+getLocalName()+ " i received the product "+ p.name+ " with the price "+ p.price);
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
	}
}
