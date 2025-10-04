

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.io.File;
import java.text.NumberFormat.Style;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface {
    private static File selectedFile;
    protected static  String chemin_objet;
    protected static String chemin_sac;
    private static File selectedFile2;
     protected static String selectedChoice;
    
     private static objet[] objets;
     private static conv conv=new conv();
     private static Sacàdos[] sacs;
     private static typeretourbfs dfs;
     private static dfs d = new dfs();
     private static typeretourbfs bfs;
     private static bfs b = new bfs();;
     private static typeretourastar a;
     private static Aetoile aa = new Aetoile();;
     private static ArrayList<objet> listeObjets;
    // private static int f=0;
     public static void main(String[] args) {
    	 JFrame frame = new JFrame("Principal");
  	    frame.setSize(600, 300);
  	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	   // frame.setBackground(Color.BLACK);

  	    
  	    
  	    
  	    
  	    
  	   JPanel panelgh = new JPanel();
  	   JPanel panelghb = new JPanel();
  	   JPanel paneldh = new JPanel();
  	   JPanel paneldb = new JPanel();
  	   // panelgh.setLayout(new BoxLayout(panelgh, BoxLayout.Y_AXIS));
  	   // panelgh.setLayout(new BoxLayout(panelgh, BoxLayout.Y_AXIS));

  	    panelgh.setBackground( Color.black);
  	    

  	    JTextPane textPane1 = createTextPane("Choisissez un algorithme");
  	  
  	   
  	    panelgh.add(textPane1);
  	    
  	    
  	   // 

  	   String[] choices = {"DFS", "BFS", "A*"};
  	    JComboBox<String> menu = new JComboBox<>(choices);
  	   Dimension menuDimension = new Dimension(135, 40); // Définir les dimensions souhaitées
  	  menu.setPreferredSize(menuDimension);
  	    menu.setAlignmentX(Component.CENTER_ALIGNMENT);
  	    menu.setBackground(Color.DARK_GRAY);
  	    menu.setForeground(Color.white);
  	   
  	    panelgh.add(menu);

  	    //

  	   JButton button = new JButton("<html><b>Sélectionner</b></html>");
  	    button.setAlignmentX(Component.CENTER_ALIGNMENT);
  	    button.setBackground(Color.DARK_GRAY);
  	    button.setForeground(Color.white);
  	 	  button.setPreferredSize(menuDimension);


  	    panelgh.add(button);
  	    button.addActionListener(new ActionListener() {
  	        @Override
  	        public void actionPerformed(ActionEvent e) {
  	            selectedChoice = (String) menu.getSelectedItem();
  	            System.out.println("Choix sélectionné : " + selectedChoice);

  	           JPanel childPanel = new JPanel();
  	          childPanel.setLayout(new FlowLayout());
  	          childPanel.setBackground(Color.black);
  	            
  	            // Ajoutez le texte avec le choix sélectionné
  	/*  ajouter apres en bas           JTextPane textPaneChoice = createTextPane("Algorithme choisi : " + selectedChoice);
  	            panelgh.add(textPaneChoice);
  	            panelgh.revalidate();
  	            panelgh.repaint();
  	           */
  	           
 	    	    JTextPane textPane2 = createTextPane("Choisissez un fichier d'objets");
 	    	    textPane2.setAlignmentX(Component.CENTER_ALIGNMENT);
 	    	    childPanel.add(textPane2);
 	
 	    	   // 

 	    JButton fileButton = new JButton("Choisir un fichier");
 	    fileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
 	    fileButton.setPreferredSize(menuDimension);
 	    fileButton.setBackground(Color.DARK_GRAY);

 	    

 	    childPanel.add(fileButton);
 	   //fileButton.setBackground(Color.black);
 	  fileButton.setForeground(Color.white);
 	  panelgh.add(childPanel, BorderLayout.CENTER);
 	   // 

 	/*   model = new DefaultTableModel();
         model.addColumn("Valeur");
         model.addColumn("Poids");

         // Création du tableau
         JTable table = new JTable(model);
         JScrollPane scrollPane = new JScrollPane(table);
       
         model2 = new DefaultTableModel();
         model2.addColumn("Poids max");

         // Création du tableau
         JTable table2 = new JTable(model2);
         JScrollPane scrollPane2 = new JScrollPane(table2);*/
     
         fileButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 JFileChooser fileChooser = new JFileChooser();
                 int returnValue = fileChooser.showOpenDialog(null);
                 if (returnValue == JFileChooser.APPROVE_OPTION) {
                     selectedFile = fileChooser.getSelectedFile();
                     chemin_objet = selectedFile.getAbsolutePath();
                     System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());

                     // Ajoutez le texte avec le chemin du fichier sélectionné
            /*         JTextPane textPaneFile = createTextPane("Fichier d'objets sélectionné : " + selectedFile.getAbsolutePath());
                     panelgh.add(textPaneFile);
                     panelgh.revalidate();
                     panelgh.repaint();*/
                    // panelgh.add(scrollPane);
                     // Lecture des objets à partir du fichier et ajout dans le tableau
                     
                     objets = conv.convertire_tab_obj(chemin_objet);
                
                     /*     if (objets != null) {
                         for (objet objet : objets) {
                             model.addRow(new Object[]{objet.getValeur(), objet.getPoids()});
                         }
                     }*/
                 } else {
                     System.out.println("Aucun fichier sélectionné");
                 }
                 JPanel childPanel2 = new JPanel();
    	          childPanel2.setLayout(new FlowLayout());
    	          childPanel2.setBackground(Color.black);
                 JTextPane textPane3 = createTextPane("Choisissez un fichier de sacs");
         	    textPane3.setAlignmentX(Component.CENTER_ALIGNMENT);
         	    childPanel2.add(textPane3);
         	    //frame.pack();
         	   // 

         	    JButton fileButton2 = new JButton("Choisir un fichier");
         	    fileButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
         	   fileButton2.setBackground(Color.DARK_GRAY);
         	  fileButton2.setForeground(Color.white);
         	  fileButton2.setPreferredSize(menuDimension);
         	  childPanel2.add(fileButton2);
         	  panelgh.add(childPanel2);
         	  
         	    //

         	    fileButton2.addActionListener(new ActionListener() {
         	        public void actionPerformed(ActionEvent e) {
         	            JFileChooser fileChooser = new JFileChooser();
         	            int returnValue = fileChooser.showOpenDialog(null);
         	            if (returnValue == JFileChooser.APPROVE_OPTION) {
         	                selectedFile2 = fileChooser.getSelectedFile();
         	                chemin_sac = selectedFile2.getAbsolutePath();
         	                System.out.println("Fichier sélectionné : " + selectedFile2.getAbsolutePath());

         	                // Ajoutez le texte avec le chemin du fichier sélectionné
         	           //     JTextPane textPaneFile = createTextPane("Fichier de sacs sélectionné : " + selectedFile2.getAbsolutePath());
         	           //     panelgh.add(textPaneFile);
         	            //    panelgh.revalidate();
         	         //       panelgh.repaint();
                             //panelgh.add(scrollPane2);
         	               JTextPane textPaneChoice = createTextPane("Algorithme  : " + selectedChoice);
         	             // panelgh.add(textPaneChoice);
         	                sacs=conv.convertir_tab_sac(chemin_sac,objets.length);
         	       /*         if (sacs != null) {
                                 for (Sacàdos sac : sacs) {
                                     model2.addRow(new Object[]{sac.getPoidsmax()});
                                 }
                             }
                         */
         	                
         	            } else {
         	                System.out.println("Aucun fichier sélectionné");
         	            }
         	            JPanel childPanel3 = new JPanel();        	        
         	            childPanel3.setBackground(Color.black);
         	           JPanel childPanel7 = new JPanel();        	        
        	            childPanel7.setBackground(Color.black);
         	            
         	            JPanel childPanel4 = new JPanel();
         	            childPanel4.setLayout(new BoxLayout(childPanel4, BoxLayout.Y_AXIS));
         	            childPanel4.setBackground(Color.black);
         	            JPanel childPanel5 = new JPanel();
         	            childPanel5.setLayout(new BoxLayout(childPanel5, BoxLayout.Y_AXIS));
         	            childPanel5.setBackground(Color.black);
         	            JPanel childPanel6 = new JPanel();
         	            childPanel6.setLayout(new BoxLayout(childPanel6, BoxLayout.Y_AXIS));
         	            childPanel6.setBackground(Color.black);
         	            
        	            switch(selectedChoice) {
        	            case "DFS":
        	            	
        	            	//childPanel3.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels

        	            	 dfs=d.rechercheDFS2(objets,sacs);
        	            	 JTextPane duree = createTextPane("La durée: "+String.valueOf(dfs.duree)+"ns");
        	            	 duree.setAlignmentX(Component.CENTER_ALIGNMENT);
        	            	 childPanel7.add(duree);
        	            	 childPanel7.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels
        	 	    	  // frame.pack();
        	 	    	  //  
        	 	    	   JTextPane prof = createTextPane("La profodeur:  "+String.valueOf(dfs.prof));
        	 	    	  prof.setAlignmentX(Component.CENTER_ALIGNMENT);
      	 	    	    	
        	 	    	 childPanel4.add(prof);
        	 	    	childPanel3.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels
      	            	// frame.pack();
      	 	    	   // 
      	 	    	 JTextPane nbrn = createTextPane("nombre de noeud:  "+String.valueOf(dfs.nbrn));
      	 	    	nbrn.setAlignmentX(Component.CENTER_ALIGNMENT);
 	 	    	    	
      	 	    	childPanel5.add(nbrn);
 	            	// frame.pack();
 	 	    	   // 
 	 	    	  JTextPane val = createTextPane("La valeur:  "+String.valueOf(dfs.valtot));
    	 	    	val.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	    	    	
    	 	    	childPanel6.add(val);
	 	
	 	    	    //
  	 	    	// Création de la JTextArea
	 	        JTextArea textArea = new JTextArea();
	 	        textArea.setEditable(false);
	 	        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18)); // Utilise une police monospace pour que les espaces soient alignés
	 	        textArea.setMargin(new Insets(10, 10, 10, 10)); // Marge pour un meilleur espacement
	 	       textArea.setBackground(Color.GRAY);
	 	      textArea.setForeground(Color.WHITE);
	 	      
	 	        // Construction de la chaîne de caractères pour chaque sac
	 	        for (int e1 = 0; e1 < dfs.sol.length; e1++) {
	 	            StringBuilder sacInfo = new StringBuilder();
	 	            sacInfo.append("Sac ").append(e1 + 1).append(":").append("\n");
	 	            sacInfo.append("Poids max: ").append(dfs.sol[e1].poidsmax).append(", ");
	 	            sacInfo.append("Poids courant: ").append(dfs.sol[e1].poidcour).append(", ");
	 	            sacInfo.append("Valeur courante: ").append(dfs.sol[e1].valcour).append("\n");
	 	            sacInfo.append("Objets:\n");
	 	            for (int f = 0; f < dfs.sol[e1].tab.length; f++) {
	 	                if (dfs.sol[e1].tab[f] == 1) {
	 	                    sacInfo.append("   Objet ").append(f + 1).append(": ");
	 	                    sacInfo.append("Poids=").append(objets[f].getPoids()).append(", ");
	 	                    sacInfo.append("Valeur=").append(objets[f].getValeur()).append("\n");
	 	                }
	 	            }
	 	            sacInfo.append("\n");
	 	            textArea.append(sacInfo.toString());
	 	        }
	 	       JTextPane textPane = new JTextPane();

	            StyledDocument doc = textPane.getStyledDocument();
	    	    // Création d'un style pour modifier la couleur du texte
	    	    javax.swing.text.Style style = textPane.addStyle("CustomStyle", null);
	    	    StyleConstants.setForeground(style, Color.black); // Couleur du texte blanche
	    	    // Application du style au texte
	    	    doc.setCharacterAttributes(0, doc.getLength(), style, false);
	            textPane.setText("                                                                                                      ");
	            textPane.setBackground(Color.black);
	            childPanel3.add(textPane);
	 	       childPanel3.add(textArea);
	 	       
	    	  //  
 	 	    	    
      	            	
        	                // Ajoutez votre code pour l'algorithme DFS ici
        	                break;
        	            case "BFS":
        	            	 bfs=b.recherchebFS(objets,sacs);
        	            	 JTextPane duree1 = createTextPane("La durée: "+String.valueOf(bfs.duree)+"ns");
        	            	// duree1.setAlignmentX(Component.CENTER_ALIGNMENT);
        	            	 childPanel7.add(duree1);
        	 	
        	 	    	    
        	 	    	   JTextPane prof1 = createTextPane("La profodeur:  "+String.valueOf(bfs.prof));
        	 	    	  prof1.setAlignmentX(Component.CENTER_ALIGNMENT);
      	 	    	    	
        	 	    	 childPanel4.add(prof1);
      	 	
      	            	//childPanel.add(Box.createVerticalGlue());
      	 	    	 JTextPane nbrn1 = createTextPane("nombre de noeud:  "+String.valueOf(bfs.nbrn));
      	 	    	nbrn1.setAlignmentX(Component.CENTER_ALIGNMENT);
 	 	    	    	
      	 	    	childPanel5.add(nbrn1);
 	 	
      	 	    	//childPanel.add(Box.createVerticalGlue());
 	 	    	  JTextPane val1 = createTextPane("La valeur:  "+String.valueOf(bfs.valtot));
    	 	    	val1.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	    	    	
    	 	    	childPanel6.add(val1);
	 	
    	 	    //	childPanel.add(Box.createVerticalGlue());
  	 	    	// Création de la JTextArea
	 	        JTextArea textArea1 = new JTextArea();
	 	        textArea1.setEditable(false);
	 	        textArea1.setFont(new Font("Monospaced", Font.PLAIN, 18)); // Utilise une police monospace pour que les espaces soient alignés
	 	        textArea1.setMargin(new Insets(10, 10, 10, 10)); // Marge pour un meilleur espacement
	 	       textArea1.setBackground(Color.GRAY);
		 	      textArea1.setForeground(Color.WHITE);
	 	        // Construction de la chaîne de caractères pour chaque sac
	 	        for (int e1 = 0; e1 < bfs.sol.length; e1++) {
	 	            StringBuilder sacInfo = new StringBuilder();
	 	            sacInfo.append("Sac ").append(e1 + 1).append(":").append("\n");
	 	            sacInfo.append("Poids max: ").append(bfs.sol[e1].poidsmax).append(", ");
	 	            sacInfo.append("Poids courant: ").append(bfs.sol[e1].poidcour).append(", ");
	 	            sacInfo.append("Valeur courante: ").append(bfs.sol[e1].valcour).append("\n");
	 	            sacInfo.append("Objets:\n");
	 	            for (int f = 0; f < bfs.sol[e1].tab.length; f++) {
	 	                if (bfs.sol[e1].tab[f] == 1) {
	 	                    sacInfo.append("   Objet ").append(f + 1).append(": ");
	 	                    sacInfo.append("Poids=").append(objets[f].getPoids()).append(", ");
	 	                    sacInfo.append("Valeur=").append(objets[f].getValeur()).append("\n");
	 	                }
	 	            }
	 	            sacInfo.append("\n");
	 	            textArea1.append(sacInfo.toString());
	 	        }
	 	        
	 	       JTextPane textPane1 = new JTextPane();

	            StyledDocument doc1 = textPane1.getStyledDocument();
	    	    // Création d'un style pour modifier la couleur du texte
	    	    javax.swing.text.Style style1 = textPane1.addStyle("CustomStyle", null);
	    	    StyleConstants.setForeground(style1, Color.black); // Couleur du texte blanche
	    	    // Application du style au texte
	    	    doc1.setCharacterAttributes(0, doc1.getLength(), style1, false);
	            textPane1.setText("                                                                                                      ");
	            textPane1.setBackground(Color.black);
	            childPanel3.add(textPane1);
	 	       childPanel3.add(textArea1);
        	                break;
        	            case "A*":
        	            	a=aa.aetoile(objets,sacs);
       	            	 JTextPane duree11 = createTextPane("La durée: "+String.valueOf(a.duree)+"ns");
       	            	 duree11.setAlignmentX(Component.CENTER_ALIGNMENT);
       	            	 childPanel7.add(duree11);
       	 	
       	 	    	    
       	 	    	   JTextPane prof11 = createTextPane("La profodeur:  "+String.valueOf(a.prof));
       	 	    	  prof11.setAlignmentX(Component.CENTER_ALIGNMENT);
     	 	    	    	
       	 	    	childPanel4.add(prof11);
     	 	
     	 	    	    
     	 	    	 JTextPane nbrn11 = createTextPane("nombre de noeud:  "+String.valueOf(a.nbrn));
     	 	    	nbrn11.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	    	    	
     	 	    	childPanel5.add(nbrn11);
	 	
	 	    	    
	 	    	  JTextPane val11 = createTextPane("La valeur:  "+String.valueOf(a.valtot));
   	 	    	val11.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	    	    	
   	 	    	childPanel6.add(val11);
	 	
	 	    	    
 	 	    	// Création de la JTextArea
	 	        JTextArea textArea11 = new JTextArea();
	 	        textArea11.setEditable(false);
	 	        textArea11.setFont(new Font("Monospaced", Font.PLAIN, 18)); // Utilise une police monospace pour que les espaces soient alignés
	 	        textArea11.setMargin(new Insets(10, 10, 10, 10)); // Marge pour un meilleur espacement
		 	     textArea11.setBackground(Color.GRAY);
		 	     textArea11.setForeground(Color.WHITE);
	 	        // Construction de la chaîne de caractères pour chaque sac
	 	        for (int e1 = 0; e1 < a.sol.size(); e1++) {
	 	            StringBuilder sacInfo = new StringBuilder();
	 	            sacInfo.append("Sac ").append(e1 + 1).append(":").append("\n");
	 	            sacInfo.append("Poids max: ").append(sacs[e1].poidsmax).append(", ");
	 	            sacInfo.append("Poids courant: ").append(sacs[e1].poidcour).append(", ");
	 	            sacInfo.append("Valeur courante: ").append(sacs[e1].valcour).append("\n");
	 	            sacInfo.append("Objets:\n");
	 	         listeObjets = a.sol.get(e1);
	 		 //   f=0;
	 		    // Parcourez les éléments de la liste actuelle
	 		    for (objet objet : listeObjets){
	 	                    sacInfo.append("   Objet : ");
	 	                    sacInfo.append("Poids=").append(objet.getPoids()).append(", ");
	 	                    sacInfo.append("Valeur=").append(objet.getValeur()).append("\n");
	 //	                    f++;
	 	                }
	 	            sacInfo.append("\n");
	 	            textArea11.append(sacInfo.toString());
	 	        }
        	            
	 	            
	 	       JTextPane textPane11 = new JTextPane();

	            StyledDocument doc11 = textPane11.getStyledDocument();
	    	    // Création d'un style pour modifier la couleur du texte
	    	    javax.swing.text.Style style11 = textPane11.addStyle("CustomStyle", null);
	    	    StyleConstants.setForeground(style11, Color.black); // Couleur du texte blanche
	    	    // Application du style au texte
	    	    doc11.setCharacterAttributes(0, doc11.getLength(), style11, false);
	            textPane11.setText("                                                                                                      ");
	            textPane11.setBackground(Color.black);
	            childPanel3.add(textPane11);
	 	        
	 	       childPanel3.add(textArea11);
	    	    
        	                break;
        	            default:
        	                System.out.println("Choix invalide.");
        	        }
        	            panelgh.add(childPanel7);
        	            panelgh.add(childPanel4);
        	            panelgh.add(childPanel5);
        	            panelgh.add(childPanel6);
        	            
        	            panelgh.add(childPanel3);

        	            JScrollPane scrollPane = new JScrollPane(childPanel3);
        	            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Toujours afficher la barre de défilement verticale

        	            frame.add(scrollPane);
        	            
        	            
        	        }
        	    });

        	    

                
            }
        });
 	        }
 	    });

 	 

      

 	    

 	   
 	    frame.add(panelgh); 	
 	    
 	    frame.setLocationRelativeTo(null); 
 	    frame.setVisible(true);
  }
  // Méthode pour créer un JTextPane avec un texte et des attributs prédéfinis
     private static JTextPane createTextPane(String text) {
    	    JTextPane textPane = new JTextPane();
    	    SimpleAttributeSet attr = new SimpleAttributeSet();
    	    StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
    	    StyleConstants.setFontSize(attr, 20);
    	    StyleConstants.setBold(attr, true); // Rendre le texte en gras
    	    textPane.setParagraphAttributes(attr, true);
    	    textPane.setText(text);
    	    textPane.setEditable(false);
    	    textPane.setBackground(Color.black);

    	    // Obtention du document du JTextPane
    	    StyledDocument doc = textPane.getStyledDocument();
    	    // Création d'un style pour modifier la couleur du texte
    	    javax.swing.text.Style style = textPane.addStyle("CustomStyle", null);
    	    StyleConstants.setForeground(style, Color.white); // Couleur du texte blanche
    	    // Application du style au texte
    	    doc.setCharacterAttributes(0, doc.getLength(), style, false);

    	    return textPane;
    	}

 	
     private static JSeparator createSeparator() {
         JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
         separator.setPreferredSize(new Dimension(10, 10)); // Définir une taille préférée pour l'espacement vertical
         return separator;
     }
 	    
  

    	    
     
   }