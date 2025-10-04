package agent_tp4;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class A1topic extends Agent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup(){
		try {
			TopicManagementHelper topicHelper=(TopicManagementHelper)getHelper(TopicManagementHelper.SERVICE_NAME);
			final AID topic=topicHelper.createTopic("JADE");
			topicHelper.register(topic);
			addBehaviour(new CyclicBehaviour(this) {
				
				@Override
				public void action() {
					// TODO Auto-generated method stub
					ACLMessage msg=receive(MessageTemplate.MatchTopic(topic));
					if(msg!=null) {
						System.out.println("Agent "+ getLocalName()+ "Message abour topic "+ topic.getLocalName()+ " recieved content is" + msg.getContent());
						
					}else {
						block();
					}
				}
			});
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
}
