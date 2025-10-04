package agent_tp2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class a_sender extends Agent{
	protected void setup() {
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		msg.setContent("Hello");
		msg.addReceiver(new AID("receiver",AID.ISLOCALNAME));
		send(msg);
		/*addBehaviour(new CyclicBehaviour(this) {
			
			@Override
			public void action() {*/
				// TODO Auto-generated method stub
				ACLMessage msg1=new ACLMessage(ACLMessage.INFORM);
				msg1=receive();
				if(msg1!=null) {
					System.out.println("Iam "+ getLocalName() +" I received a reply " + msg.getContent() +" From a reply "+ msg.getSender().getName());
				}//block();
				
				
			//}
		//});
	}
}
