package agent_tp2;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
public class a_receive extends Agent{
	protected void setup(){
		addBehaviour(new CyclicBehaviour(this){

			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage msg=receive();
				if(msg!=null) {
				System.out.println("Iam "+ getLocalName() +" I received a message" + msg.getContent() +" Frpm the agent  "+ msg.getSender().getName());
				ACLMessage reply=msg.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent("Thank you");
				send(reply);
				
				}block();
			}
	});
}
}
