

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class main {

	public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
		//System.out.println("nbr d'ojet");
		//int nbroject=sc.nextInt();
	for(int i=1;i<15;i++) {
		int nbroject=i;
		objet[] objets = new objet[nbroject];
		
		//System.out.println("nbr de sac");
		int nbrsac=i;
		Sacàdos [] sacs=new Sacàdos[nbrsac];
		Random random = new Random();
		 String filePath = System.getProperty("user.home") + "/Documents/fichier objets/objets2"+i+".txt";
	        File file = new File(filePath);

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	            for (int i1 = 0; i1 < nbroject; i1++) {
	            	int poid=random.nextInt(15)+3;
					int valeur=random.nextInt(14)+2;
	                String line = "objet :" + i1 + " poid " + poid + " val " + valeur;
	                //System.out.println(line);
	                writer.write(line);
	                writer.newLine();
	            }
	            System.out.println("Les informations des objets ont été écrites dans le fichier objets.txt.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		int [] tab_init_0= new int[nbroject];
		for(int i1=0;i1<nbroject;i1++) {
					
			tab_init_0[i1]=0;

		}
		String filePath2 = System.getProperty("user.home") + "/Documents/fichier sacados/sacsados2"+i+".txt";
        File file2 = new File(filePath2);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2))) {
            for (int i1 = 0; i1 < nbrsac; i1++) {
            	int poid=random.nextInt(30)+10;
                String line = "sac :" + i1 + " poids " + poid;
                //System.out.println(line);
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Les informations des sacs à dos ont été écrites dans le fichier sacsados.txt dans le répertoire Documents.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}
		dfs instance = new dfs();
		bfs instance1 = new bfs();
		
		//dfs.rechercheDFS2(objets,sacs);
		Aetoile a=new Aetoile();
		//a.aetoile(objets, sacs);
		 
		/*	String directoryPath = System.getProperty("user.home") + "/Documents/fichier objets";
	        String filePath = directoryPath + "/objets2.txt";

	        public objet[] convertire_tab_obj(String filePath) {
	        // Créer une ArrayList pour stocker les objets lus à partir du fichier
	        ArrayList<objet> objets = new ArrayList<>();

	        try {
	            File file = new File(filePath);
	            Scanner scanner = new Scanner(file);

	            // Lire chaque ligne du fichier et créer un objet pour chaque ligne
	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                // Supposons que le format de chaque ligne soit "objet :<index> poid <poid> val <valeur>"
	                String[] parts = line.split(" ");
	                int poid = Integer.parseInt(parts[3]);
	                int valeur = Integer.parseInt(parts[5]);
	                objet objet = new objet(valeur, poid);
	                objets.add(objet);
	            }

	            scanner.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }

	        // Convertir l'ArrayList en un tableau si nécessaire
	        return objets.toArray(new objet[0]);

	        // Affichage du tableau d'objets
	        System.out.println("Contenu du tableau d'objets :");
	        for (int i=0;i<objetsArray.length;i++) {
	            System.out.println("Poids : " + objetsArray[i].poids + ", Valeur : " + objetsArray[i].valeur);
	        }
	        
	        }
	       
	        public Sacàdos[] convertir_tab_sac(String filePath1 ,int nbobj) {
	        
	       // String directoryPath1 = System.getProperty("user.home") + "/Documents/fichier sacados";
	       // String filePath1 = directoryPath1 + "/sacsados.txt";
	        int [] tab_init_0= new int[nbobj];
			for(int i=0;i<nbobj;i++) {
						
				tab_init_0[i]=0;

			}
	        // Créer une ArrayList pour stocker les sacs lus à partir du fichier
	        ArrayList<Sacàdos> sacsList = new ArrayList<>();

	      /*  try {
	            File directory = new File(directoryPath1);
	            if (!directory.exists()) {
	                directory.mkdirs(); // Créer le répertoire s'il n'existe pas déjà
	            }
	            */

	 /*           File file = new File(filePath1);
	            if (!file.exists()) {
	                // Traiter le cas où le fichier n'existe pas
	                System.out.println("Le fichier " + filePath1 + " n'existe pas.");
	                return;
	            }

	            Scanner scanner = new Scanner(file);

	            // Lire chaque ligne du fichier et créer un objet Sacàdos pour chaque ligne
	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                // Supposons que le format de chaque ligne soit "sac :<index> poids <poids>"
	                String[] parts = line.split(" ");
	                int poids = Integer.parseInt(parts[3]);
	                Sacàdos sac = new Sacàdos(poids, 0, 0, tab_init_0); // Assurez-vous d'avoir tab_init_0 initialisé
	                sacsList.add(sac);
	            }

	            scanner.close();
	      //  } catch (FileNotFoundException e) {
	        //    e.printStackTrace();
	        //}

	        // Convertir l'ArrayList en un tableau si nécessaire
	            return sacsList.toArray(new Sacàdos[0]);	   
	            /*for (int i=0;i<sacs.length;i++) {
	            System.out.println("Poids : " + sacs[i].poidsmax);
	        }
	        
	        }*/
	}}




