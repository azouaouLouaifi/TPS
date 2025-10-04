import java.util.ArrayList;
public class rule {
	boolean state;
	ArrayList<String> P;
	ArrayList<String> C;
	int name;
	
	public rule(int i,ArrayList<String> a1,ArrayList<String> a2) {
		name=i;
		state=true;
		P=a1;
		C=a2;
	}
	
}
