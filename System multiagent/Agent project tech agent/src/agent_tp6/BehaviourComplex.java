package agent_tp6;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;

public class BehaviourComplex extends Agent{
	protected void setup() {
		ParallelBehaviour parab=new ParallelBehaviour();
		parab.addSubBehaviour(langhelloBehaviour(" السلام "));
		parab.addSubBehaviour(langhelloBehaviour(" Bonjour "));
		parab.addSubBehaviour(langhelloBehaviour("buenos dias"));
		parab.addSubBehaviour(langhelloBehaviour(" Maraba "));
		System.out.println();
		addBehaviour(parab);
	}
	private Behaviour langhelloBehaviour(String msg) {
		Behaviour b= new Behaviour() {
			int i=0;
			@Override
			public void action() {
				// TODO Auto-generated method stub
				System.out.println("affichage du msg"+msg);
			
			//	System.out.println("%s->%s(%d/3)\n",new Object[] {getLocalName(),msg,(i+1)});
				i++;	
			}
			
			@Override
			public boolean done() {
				// TODO Auto-generated method stub
				return i>=3;
			}
			
		};
		return b;
	}
}
