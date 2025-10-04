package agent_tp4;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.TickerBehaviour;
public class A2topic extends Agent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//sendre
	protected void setup() {
	
			TopicManagementHelper topicHelper;
			try {
				topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);
			
			final AID Topic=topicHelper.createTopic("JADE");
			addBehaviour(new TickerBehaviour(this,10000) {
				@Override
				protected void onTick() {
					// TODO Auto-generated method stub
					System.out.println("Agent "+getLocalName()+ "Sending message about topic "+ Topic.getLocalName());
					ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
					msg.addReceiver(Topic);
					msg.setContent(String.valueOf(getTickCount()));
					send(msg);
					
				}
			});
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		
	}
}
