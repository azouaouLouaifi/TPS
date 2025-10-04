import java.util.ArrayList;
import java.util.Arrays;
public class back_chain {
	//fonction backward chaining//
	public static boolean backchain(rule[] bdr,ArrayList<String> bdf,ArrayList<String> fp) {
		boolean res;
		if(fp.isEmpty()) {
			res=true;
		}else {
			if(TakeGoal(bdr,bdf,fp.get(0))) {
				bdf.add(fp.get(0));
				res=backchain(bdr,bdf,rest(fp));
			}else {
				res=false;
			}
		}
			return res;
	}
	//fonction task goal*/
	public static boolean TakeGoal(rule[] bdr,ArrayList<String> bdf,String goal) {
		boolean res;
		if(bdf.contains(goal)) {
			res=true;
		}else {
			res=false;
			//this part represente the choose
			ArrayList<rule> RA=new ArrayList<rule>();   //rules array  liste de conflit//
			for(int i=0;i<bdr.length;i++) {
				if(bdr[i].C.contains(goal)) {
					RA.add(bdr[i]);
				}	
			}
			
			while((!RA.isEmpty()) && (res!=true)) {
				rule r=RA.get(0);
				RA.remove(0);
				res=backchain(bdr,bdf,r.P);
			}
		}
		return res;
	}
	//fonction rest
	public static ArrayList<String> rest(ArrayList<String> a) {
		ArrayList<String> r=new ArrayList<String>();
		for(int i=1;i<a.size();i++) {
			r.add(a.get(i));
			
		}
		return r;
	}
	
	
	
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
		ArrayList<String> FP=new ArrayList<String>(Arrays.asList("X"));
		//Fact base
		ArrayList<String> dbf=new ArrayList<String>(Arrays.asList("D","O","G"));
		
		//first call backchain//
		//fp :fait to prove
		//dbf: fait base
		//bdr: rule base
		boolean test= backchain(bdr,dbf,FP);
		//AFFICHER LE RESULTAT//
		System.out.println("the result is: "+ test);
	}
	
}
