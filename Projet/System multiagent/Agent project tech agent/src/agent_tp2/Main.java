package agent_tp2;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] jadeArg = new String[2];
		StringBuffer SbAgent = new StringBuffer();
		
		SbAgent.append("receiver:agent_tp2.a_receive;");
		SbAgent.append("sender:agent_tp2.a_sender;");
		jadeArg[0]="-gui";
		jadeArg[1]=SbAgent.toString();
		jade.Boot.main(jadeArg);
	}

}
