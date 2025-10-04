import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.SwingUtilities;

public class forw_chain {
	public static boolean forwchain(rule[] rb,ArrayList<String> fb,ArrayList<String> fp) {
		boolean res;
		
		if(fb.containsAll(fp)) {
			res=true;
		}else {
			res=false;
			boolean applic=true;
			while(rb.length!=0 && res!=true && applic) {
				//choose//
				for(int i=0;i<rb.length;i++){
	                if (fb.containsAll(rb[i].P)) {
	                	//add c in fb
	                    for (String c : rb[i].C) {
	                        if (!fb.contains(c)) {
	                            fb.add(c);
	                           // res = true; 
	                        }
	                    }	   
	                    //delate applayed rule 
	                    rb=res_rules(rb,rb[i]);
	                    // apply a rule so i get out the for 
	                    break;       
	                }
	          
	                if(rb.length==i+1) {
	                	applic=false;
	                }
	            }
	            //test whether fb contain fp 
	            if(fb.containsAll(fp)) {
	            	res=true;
	            }       	
			}
		}	
		return res;
	}
	
	public static rule[] res_rules(rule[] rb, rule r) {
		rule[] nr=new rule[rb.length-1];
		int i=0;
		for (rule rul : rb) {
			if(rul!=r) {
				nr[i]=rul;
				i++;
			}
		}
		return nr;
	}
	//--------------------main function---------------------//
	public static void main(String[]args) {
		rule[] bdr=new rule[9];
		//RULE1//
		ArrayList<String> P=new ArrayList<>(Arrays.asList("A","B"));
		ArrayList<String> C=new ArrayList<>(Arrays.asList("F"));
		bdr[0]=new rule(0,P,C);
		//RULE2//
		P=new ArrayList<>(Arrays.asList("F","H"));
		C=new ArrayList<>(Arrays.asList("I"));
		bdr[1]=new rule(1,P,C);
		//RULE3//
		P=new ArrayList<>(Arrays.asList("D","H","G"));
		C=new ArrayList<>(Arrays.asList("A"));
		bdr[2]=new rule(2,P,C);
		//RULE4//
		P=new ArrayList<>(Arrays.asList("O","G"));
		C=new ArrayList<>(Arrays.asList("H"));
		bdr[3]=new rule(3,P,C);
		//RULE5//
		P=new ArrayList<>(Arrays.asList("E","H"));
		C=new ArrayList<>(Arrays.asList("B"));
		bdr[4]=new rule(4,P,C);
		//RULE6//
		P=new ArrayList<>(Arrays.asList("G","A"));
		C=new ArrayList<>(Arrays.asList("B"));
		bdr[5]=new rule(5,P,C);
		//RULE7//
		P=new ArrayList<>(Arrays.asList("G","H"));
		C=new ArrayList<>(Arrays.asList("P"));
		bdr[6]=new rule(6,P,C);
		//RULE8//
		P=new ArrayList<>(Arrays.asList("G","H"));
		C=new ArrayList<>(Arrays.asList("O"));
		bdr[7]=new rule(7,P,C);
		//RULE9//
		P=new ArrayList<>(Arrays.asList("D","O","G"));
		C=new ArrayList<>(Arrays.asList("J"));
		bdr[8]=new rule(8,P,C);
		
		//fact to prove (GOAL)//
		ArrayList<String> FP=new ArrayList<String>(Arrays.asList("Z"));
		//Fact base
		ArrayList<String> dbf=new ArrayList<String>(Arrays.asList("D","H","G"));
		
		boolean test=forwchain(bdr,dbf,FP);
		//AFFICHER LE RESULTAT//
		System.out.print("the resultat: ");
		System.out.println(test);
		SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            	interface_for_chain myInterface = new interface_for_chain();
                myInterface.setVisible(true);
         
            }
        	
        });
		
	}
}
