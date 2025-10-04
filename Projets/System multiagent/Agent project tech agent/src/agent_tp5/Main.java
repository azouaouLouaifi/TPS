package agent_tp5;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			// TODO Auto-generated method stub
			String [] jadeArg = new String[2];
			StringBuffer SbAgent = new StringBuffer();
			SbAgent.append("s:agent_tp5.seller;");
			SbAgent.append("Buyer:agent_tp5.buyer;");
			jadeArg[0]="-gui";
			jadeArg[1]=SbAgent.toString();
			jade.Boot.main(jadeArg);
		}
	

}
