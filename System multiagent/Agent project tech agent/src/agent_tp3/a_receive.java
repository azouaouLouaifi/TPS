package agent_tp3;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
public class a_receive extends Agent{
	protected void setup(){
		addBehaviour(new CyclicBehaviour(this){
			@Override
			public void action() {
				// TODO Auto-generated method stub
				MessageTemplate mt= MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
				ACLMessage msg= receive(mt);
				if(msg!=null) {
				System.out.println("Iam "+ getLocalName() +" I received a message " + msg.getContent() +" Frpm the agent  "+ msg.getSender().getName());
				ACLMessage reply=msg.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent("Thank you");
				send(reply);
				
				}block();
			}
	});
}
}
