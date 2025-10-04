import jade.core.*;
public class agent_display extends Agent {
	float price;
	String name;
	protected void setup() {
		Object[] args=getArguments();
		if(args!=null) {
			name=(String)args[0];
			price=Float.valueOf((String)(args[1])).floatValue();
			
		}
		System.out.println("In Agent "+getLocalName()+" Sell product "+ name+" at price "+price);
		//System.out.println("le nom de l'agent"+getLocalName());
	}

}
