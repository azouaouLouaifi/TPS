
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class cree_fichier {
	
	public static String cree_fichie_objet(int poidmax,int poidmin,int valeurmax,int valeurmin,int nbroject) {
	objet[] objets = new objet[nbroject];
	
	
	Random random = new Random();
	 String filePath = System.getProperty("user.home") + "/Documents/fichier objets2/objets"+nbroject+"_"+poidmax+"_"+valeurmax+".txt";
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i1 = 0; i1 < nbroject; i1++) {
            	int poid=random.nextInt(poidmax - poidmin + 1) + poidmin;
				int valeur=random.nextInt(valeurmax - valeurmin + 1) + valeurmin;
                String line = "objet :" + i1 + " poid " + poid + " val " + valeur;
                //System.out.println(line);
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Les informations des objets ont été écrites dans le fichier objets.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
	}
	
	
	
	public static String cree_fichie_sac(int poidmax,int poidmin,int valeurmax,int valeurmin,int nbroject,int nbrsac) {

	Sacàdos [] sacs=new Sacàdos[nbrsac];
	Random random = new Random();
	
	int [] tab_init_0= new int[nbroject];
	for(int i1=0;i1<nbroject;i1++) {
				
		tab_init_0[i1]=0;

	}
	 String filePath = System.getProperty("user.home") + "/Documents/fichier sacados2/sacsados"+nbrsac+"_"+poidmax+"_"+valeurmax+".txt";
    File file2 = new File(filePath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2))) {
        for (int i1 = 0; i1 < nbrsac; i1++) {
        	int poid=random.nextInt(poidmax+5 - poidmin+5 + 1) + poidmin+5;
			int valeur=random.nextInt(valeurmax - valeurmin + 1) + valeurmin;
            String line = "sac :" + i1 + " poid " + poid + " val " + valeur;

            //String line = "sac :" + i1 + " poids " + poid;
            //System.out.println(line);
            writer.write(line);
            writer.newLine();
        }
        System.out.println("Les informations des sacs à dos ont été écrites dans le fichier sacsados.txt dans le répertoire Documents.");
    } catch (IOException e) {
        e.printStackTrace();
    }
    return filePath;
	}
}
