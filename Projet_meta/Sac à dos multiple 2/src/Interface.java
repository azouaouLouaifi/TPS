
import javax.swing.*;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;


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
     private static typeretourbso bso;
     private static bso b= new bso();
     private static GeneticAlgo2 g= new GeneticAlgo2
    		 ();
     private static typeretourga ga;

     private static ArrayList<objet> listeObjets;
    // private static int f=0;
     private static JButton utiliserFichiersBtn, nouvelleInstanceBtn,valider;
     private static JLabel poidsMaxLabel, poidsMinLabel, nbrSacLabel, nbrObjetLabel, valeurMaxLabel, valeurMinLabel;
     private static JTextField poidsMaxField, poidsMinField, nbrSacField, nbrObjetField, valeurMaxField, valeurMinField;
     private static JPanel panel;
     private static JPanel panel2;
     private static JFrame frame;
   //  private static cree_fichier f=new cree_fichier();
     public static void main(String[] args) {
    	   createAndShowGUI();
     }
    	 private static void createAndShowGUI() {
    	  frame = new JFrame("Principal");
  	    frame.setSize(600, 300);
  	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	  final JPanel cards = new JPanel(new CardLayout());
  	    
  	    
  	    
  	    
  	    
  	    
  	    
  	    
  	   // frame.setBackground(Color.BLACK);
  	  panel2 = new JPanel();
      panel2.setBackground( Color.black);
     

      utiliserFichiersBtn = new JButton("Utiliser des fichiers existants");
      nouvelleInstanceBtn = new JButton("Créer une nouvelle instance");
      
      utiliserFichiersBtn.setBackground(Color.DARK_GRAY);
      utiliserFichiersBtn.setForeground(Color.white);
      nouvelleInstanceBtn.setBackground(Color.DARK_GRAY);
      nouvelleInstanceBtn.setForeground(Color.white);
      Dimension menuDimension = new Dimension(200, 30);
      utiliserFichiersBtn.setPreferredSize(menuDimension);
      nouvelleInstanceBtn.setPreferredSize(menuDimension);
      JPanel card1 = new JPanel(); 
      card1.setBackground( Color.black);
      final JPanel panelgh = new JPanel();
      card1.add(utiliserFichiersBtn);
     // JPanel card2 = new JPanel();
      card1.add(nouvelleInstanceBtn);
      cards.add(card1, "card1");
      //   cards.add(card2, "card2");
         CardLayout cl = (CardLayout)(cards.getLayout());
         cl.show(cards, "card1");
         cards.add(panelgh, "card3");
         
         final JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setBackground(Color.black);
            final JButton rerunButton = new JButton("Réexécuter");
            
            rerunButton.setPreferredSize(new Dimension(135, 40));
            rerunButton.setBackground(Color.DARK_GRAY);
            rerunButton.setForeground(Color.white);

            
     
         nouvelleInstanceBtn.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 // Code à exécuter lorsque le bouton est cliqué
            	 CardLayout cl = (CardLayout)(cards.getLayout());
                 cl.show(cards, "card3");
          	    panelgh.setBackground( Color.black);
          	  panel = new JPanel();
              panel.setLayout(new GridLayout(7, 2));
              panel.setBackground( Color.black);

              poidsMaxLabel = createStyledLabel("Poids maximum :");
              poidsMinLabel = createStyledLabel("Poids minimum :");
              nbrSacLabel = createStyledLabel("Nombre de sacs :");
              nbrObjetLabel = createStyledLabel("Nombre d'objets :");
              valeurMaxLabel = createStyledLabel("Valeur maximum :");
              valeurMinLabel = createStyledLabel("Valeur minimum :");
             
              poidsMaxField = createStyledTextField();
              poidsMinField = createStyledTextField();
              nbrSacField = createStyledTextField();
              nbrObjetField = createStyledTextField();
              valeurMaxField = createStyledTextField();
              valeurMinField = createStyledTextField();
             // panel.removeAll(); // Effacer tous les éléments précédents

              // Ajouter les éléments pour saisir les informations
              panel.add(poidsMaxLabel);
              panel.add(poidsMaxField);
              panel.add(poidsMinLabel);
              panel.add(poidsMinField);
              panel.add(nbrSacLabel);
              panel.add(nbrSacField);
              panel.add(nbrObjetLabel);
              panel.add(nbrObjetField);
              panel.add(valeurMaxLabel);
              panel.add(valeurMaxField);
              panel.add(valeurMinLabel);
              panel.add(valeurMinField);
              
             // panel.add(nouvelleInstanceBtn);
              panelgh.add(panel);
              valider = new JButton("valider");
              valider.setBackground(Color.DARK_GRAY);
              valider.setForeground(Color.white);
              panel.add(valider);
              valider.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                	  int poidsMax = Integer.parseInt(poidsMaxField.getText());
                	  int poidsMin = Integer.parseInt(poidsMinField.getText());
                	  int nbrSac = Integer.parseInt(nbrSacField.getText());
                	  int nbrObjet = Integer.parseInt(nbrObjetField.getText());
                	  int valeurMax = Integer.parseInt(valeurMaxField.getText());
                	  int valeurMin = Integer.parseInt(valeurMinField.getText());
                	  final String fo=cree_fichier.cree_fichie_objet(poidsMax,poidsMin, valeurMax, valeurMin, nbrObjet);
                	  final String fs=cree_fichier.cree_fichie_sac(poidsMax,poidsMin, valeurMax, valeurMin, nbrObjet,nbrSac);
                	  JTextPane textPane1 = createTextPane("Choisissez un algorithme");
                   	  
                	  panel.removeAll();
                 	    panelgh.add(textPane1);
                 	    
                 	    
                 	   // 

                 	   String[] choices = {"BSO", "GA"};
                 	    final JComboBox<String> menu = new JComboBox<>(choices);
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
                 	                                     
                                    objets = conv.convertire_tab_obj(fo);
                               
                                    
                             
                        	               JTextPane textPaneChoice = createTextPane("Algorithme  : " + selectedChoice);
                        	             // panelgh.add(textPaneChoice);
                        	                sacs=conv.convertir_tab_sac(fs,objets.length);
                        	    
                        	                
                        	            
                        	            final JPanel childPanel3 = new JPanel();        	        
                        	            childPanel3.setBackground(Color.black);
                        	            final JPanel childPanel8 = new JPanel();        	        
                        	            childPanel8.setBackground(Color.black);
                        	            final JPanel childPanel7 = new JPanel();        	        
                       	            childPanel7.setBackground(Color.black);
                        	            
                       	         final JPanel childPanel4 = new JPanel();
                        	            childPanel4.setLayout(new BoxLayout(childPanel4, BoxLayout.Y_AXIS));
                        	            childPanel4.setBackground(Color.black);
                        	            final    JPanel childPanel5 = new JPanel();
                        	            childPanel5.setLayout(new BoxLayout(childPanel5, BoxLayout.Y_AXIS));
                        	            childPanel5.setBackground(Color.black);
                        	            final         JPanel childPanel6 = new JPanel();
                        	            childPanel6.setLayout(new BoxLayout(childPanel6, BoxLayout.Y_AXIS));
                        	            childPanel6.setBackground(Color.black);
                              	         panelgh.removeAll();

                       	            switch(selectedChoice) {
                       	            case "BSO":
                       	             panel = new JPanel();
                                     panel.setLayout(new GridLayout(4, 2));
                                     panel.setBackground( Color.black);

                                     poidsMaxLabel = createStyledLabel("Max iteration :");
                                     poidsMinLabel = createStyledLabel("Nombre population:");
                                     nbrSacLabel = createStyledLabel("Flip :");
                                     
                                    
                                     poidsMaxField = createStyledTextField();
                                     poidsMinField = createStyledTextField();
                                     nbrSacField = createStyledTextField();
                                     
                                    // panel.removeAll(); // Effacer tous les éléments précédents

                                     // Ajouter les éléments pour saisir les informations
                                     panel.add(poidsMaxLabel);
                                     panel.add(poidsMaxField);
                                     panel.add(poidsMinLabel);
                                     panel.add(poidsMinField);
                                     panel.add(nbrSacLabel);
                                     panel.add(nbrSacField);
                                    
                                     
                                    // panel.add(nouvelleInstanceBtn);
                                     panelgh.add(panel);
                                     valider = new JButton("valider");
                                     valider.setBackground(Color.DARK_GRAY);
                                     valider.setForeground(Color.white);
                                     panel.add(valider);
                                     valider.addActionListener(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {
                                       	  int maxit = Integer.parseInt(poidsMaxField.getText());
                                       	  int nbrpop = Integer.parseInt(poidsMinField.getText());
                                       	  int flip = Integer.parseInt(nbrSacField.getText());
                                       	                                      
                                       	  panel.removeAll();
                       	            	
                       	            	
                       	            	
                       	            	
                       	            	
                       	            	
                       	            	
                       	            	//childPanel3.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels

                       	            	 bso=b.bso1(sacs,objets,maxit,nbrpop,flip);
                       	            	 System.out.println("val"+bso.dure);
                       	              
                        	 	    	 JTextPane nbrn11 = createTextPane("les valeurs de la 1 Sref : poid: "+String.valueOf(bso.solinit.poid_total)
                        	 	    	 +"  valeur:"+String.valueOf(bso.solinit.valeur_total));
                        	 	    	nbrn11.setAlignmentX(Component.CENTER_ALIGNMENT);
                   	 	    	    	
                        	 	    	childPanel5.add(nbrn11);
       	 	          	            	 JTextPane duree = createTextPane("  La durée: "+String.valueOf(bso.dure)+"s");
       	 	          	            	 duree.setAlignmentX(Component.CENTER_ALIGNMENT);
       	 	          	            	 childPanel7.add(duree);
       	 	          	            	 childPanel7.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels
       	 	          	 	    	  // frame.pack();
       	 	          	 	    	  //  
       	 	        	            	// frame.pack();
       	 	        	 	    	   // 
       	 	        	 	    	 
       	 	   	            	// frame.pack();
       	 	   	 	    	   // 
       	 	   	 	    	  JTextPane val = createTextPane(" Les valeurs de la meilleur solution   ");
       	 	      	 	    	val.setAlignmentX(Component.CENTER_ALIGNMENT);
       	 	  	 	    	    	
       	 	      	 	    	childPanel6.add(val);
       	 	      	 	 JTextPane val1 = createTextPane("                 ");
	 	      	 	    	val1.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	  	 	    	    	
	 	      	 	    	childPanel8.add(val1);
       	 	      	 	   JTextPane prof11 = createTextPane("    Le poid: "+String.valueOf(bso.meilleur_sol.poid_total)
    	 	   	 	    	  +"    La valeur:  "+String.valueOf(bso.meilleur_sol.valeur_total));
           	 	    	  prof11.setAlignmentX(Component.CENTER_ALIGNMENT);
         	 	    	    	
           	 	    	childPanel4.add(prof11);
       	 	  	 	    	    //
       	 	    	 	    	// Création de la JTextArea
       	 	  	 	        JTextArea textArea = new JTextArea();
       	 	  	 	        textArea.setEditable(false);
       	 	  	 	        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18)); // Utilise une police monospace pour que les espaces soient alignés
       	 	  	 	        textArea.setMargin(new Insets(10, 10, 10, 10)); // Marge pour un meilleur espacement
       	 	  	 	       textArea.setBackground(Color.GRAY);
       	 	  	 	      textArea.setForeground(Color.WHITE);
       	 	  	 	      
       	 	  	 	        // Construction de la chaîne de caractères pour chaque sac
       	 	  	 	        for (int e1 = 0; e1 < bso.meilleur_sol.sacs.size() ; e1++) {
       	 	  	 	            StringBuilder sacInfo = new StringBuilder();
       	 	  	 	            sacInfo.append("Sac ").append(e1 + 1).append(":").append("\n");
       	 	  	 	            sacInfo.append("Poids max: ").append( bso.meilleur_sol.sacs.get(e1).poidsmax).append(", ");
       	 	  	 	            sacInfo.append("Valeur max: ").append(bso.meilleur_sol.sacs.get(e1).valmin).append("\n");

       	 	  	 	            sacInfo.append("Poids courant: ").append(bso.meilleur_sol.sacs.get(e1).poidcour).append(", ");
       	 	  	 	            sacInfo.append("Valeur courante: ").append(bso.meilleur_sol.sacs.get(e1).valcour).append("\n");
       	 	  	 	            sacInfo.append("Objets:\n");
       	 	  	 	            for (int f = 0; f < bso.meilleur_sol.sacs.get(e1).objets.size(); f++) {
       	 	  	 	              
       	 	  	 	                    sacInfo.append("   Objet ").append(bso.meilleur_sol.sacs.get(e1).objets.get(f).num).append(": ");
       	 	  	 	                    sacInfo.append("Poids=").append(bso.meilleur_sol.sacs.get(e1).objets.get(f).poids).append(", ");
       	 	  	 	                    sacInfo.append("Valeur=").append(bso.meilleur_sol.sacs.get(e1).objets.get(f).valeur).append("\n");

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
               	 	       
               	 	   p.add(rerunButton);
          	 	      rerunButton.addActionListener(new ActionListener() {
                 	        public void actionPerformed(ActionEvent e) { 
                 	        	
                 	        	rerunCode();
                 	        
                 	        
                 	        }
                 	        
          	 	      });
                            }
                              	        
                    });
               	    	  //  
                	 	    	    
                     	            	
                       	                // Ajoutez votre code pour l'algorithme DFS ici
                       	                break;
                       	            case "GA":
                       	            	panel = new JPanel();
                                        panel.setLayout(new GridLayout(3, 2));
                                        panel.setBackground( Color.black);

                                        poidsMaxLabel = createStyledLabel("Max iteration :");
                                        poidsMinLabel = createStyledLabel("Nombre population:");
                                       // nbrSacLabel = createStyledLabel("Flip :");
                                        
                                       
                                        poidsMaxField = createStyledTextField();
                                        poidsMinField = createStyledTextField();
                                     //   nbrSacField = createStyledTextField();
                                        
                                       // panel.removeAll(); // Effacer tous les éléments précédents

                                        // Ajouter les éléments pour saisir les informations
                                        panel.add(poidsMaxLabel);
                                        panel.add(poidsMaxField);
                                        panel.add(poidsMinLabel);
                                        panel.add(poidsMinField);
                                     //   panel.add(nbrSacLabel);
                                     //   panel.add(nbrSacField);
                                       
                                        
                                       // panel.add(nouvelleInstanceBtn);
                                        panelgh.add(panel);
                                        valider = new JButton("valider");
                                        valider.setBackground(Color.DARK_GRAY);
                                        valider.setForeground(Color.white);
                                        panel.add(valider);
                                        valider.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                          	  int maxit = Integer.parseInt(poidsMaxField.getText());
                                          	  int nbrpop = Integer.parseInt(poidsMinField.getText());
                                          	  //int flip = Integer.parseInt(nbrSacField.getText());
                                          	                                      
                                          	  panel.removeAll();
                          	            	
                          	            	
                          	            	
                          	            	
                          	            	
                          	            	
                          	            	
                          	            	//childPanel3.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels

                          	            	 ga=g.genetique_algo(maxit,nbrpop,sacs,objets);
                          	            	 System.out.println("val"+ga.dure);
                          	              
                           	 	    	/* JTextPane nbrn11 = createTextPane("les valeurs de la 1 Sref : poid: "+String.valueOf(bso.solinit.poid_total)
                           	 	    	 +"  valeur:"+String.valueOf(bso.solinit.valeur_total));
                           	 	    	nbrn11.setAlignmentX(Component.CENTER_ALIGNMENT);
                      	 	    	    	
                           	 	    	childPanel5.add(nbrn11);*/
          	 	          	            	 JTextPane duree = createTextPane("  La durée: "+String.valueOf(ga.dure)+"s");
          	 	          	            	 duree.setAlignmentX(Component.CENTER_ALIGNMENT);
          	 	          	            	 childPanel7.add(duree);
          	 	          	            	 childPanel7.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels
          	 	          	 	    	  // frame.pack();
          	 	          	 	    	  //  
          	 	        	            	// frame.pack();
          	 	        	 	    	   // 
          	 	        	 	    	 
          	 	   	            	// frame.pack();
          	 	   	 	    	   // 
          	 	   	 	    	  JTextPane val = createTextPane(" Les valeurs de la meilleur solution   ");
          	 	      	 	    	val.setAlignmentX(Component.CENTER_ALIGNMENT);
          	 	  	 	    	    	
          	 	      	 	    	childPanel6.add(val);
          	 	      	 	 JTextPane val1 = createTextPane("                 ");
   	 	      	 	    	val1.setAlignmentX(Component.CENTER_ALIGNMENT);
   	 	  	 	    	    	
   	 	      	 	    	childPanel8.add(val1);
          	 	      	 	   JTextPane prof11 = createTextPane("    Le poid: "+String.valueOf(ga.meilleur_sol.poid_total)
       	 	   	 	    	  +"    La valeur:  "+String.valueOf(ga.meilleur_sol.valeur_total));
              	 	    	  prof11.setAlignmentX(Component.CENTER_ALIGNMENT);
            	 	    	    	
              	 	    	childPanel4.add(prof11);
          	 	  	 	    	    //
          	 	    	 	    	// Création de la JTextArea
          	 	  	 	        JTextArea textArea = new JTextArea();
          	 	  	 	        textArea.setEditable(false);
          	 	  	 	        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18)); // Utilise une police monospace pour que les espaces soient alignés
          	 	  	 	        textArea.setMargin(new Insets(10, 10, 10, 10)); // Marge pour un meilleur espacement
          	 	  	 	       textArea.setBackground(Color.GRAY);
          	 	  	 	      textArea.setForeground(Color.WHITE);
          	 	  	 	      
          	 	  	 	        // Construction de la chaîne de caractères pour chaque sac
          	 	  	 	        for (int e1 = 0; e1 < ga.meilleur_sol.sacs.size() ; e1++) {
          	 	  	 	            StringBuilder sacInfo = new StringBuilder();
          	 	  	 	            sacInfo.append("Sac ").append(e1 + 1).append(":").append("\n");
          	 	  	 	            sacInfo.append("Poids max: ").append( ga.meilleur_sol.sacs.get(e1).poidsmax).append(", ");
          	 	  	 	            sacInfo.append("Valeur max: ").append(ga.meilleur_sol.sacs.get(e1).valmin).append("\n");

          	 	  	 	            sacInfo.append("Poids courant: ").append(ga.meilleur_sol.sacs.get(e1).poidcour).append(", ");
          	 	  	 	            sacInfo.append("Valeur courante: ").append(ga.meilleur_sol.sacs.get(e1).valcour).append("\n");
          	 	  	 	            sacInfo.append("Objets:\n");
          	 	  	 	            for (int f = 0; f < ga.meilleur_sol.sacs.get(e1).objets.size(); f++) {
          	 	  	 	              
          	 	  	 	                    sacInfo.append("   Objet ").append(ga.meilleur_sol.sacs.get(e1).objets.get(f).num).append(": ");
          	 	  	 	                    sacInfo.append("Poids=").append(ga.meilleur_sol.sacs.get(e1).objets.get(f).poids).append(", ");
          	 	  	 	                    sacInfo.append("Valeur=").append(ga.meilleur_sol.sacs.get(e1).objets.get(f).valeur).append("\n");

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
                  	 	       
                  	 	   p.add(rerunButton);
             	 	      rerunButton.addActionListener(new ActionListener() {
                    	        public void actionPerformed(ActionEvent e) { 
                    	        	
                    	        	rerunCode();
                    	        
                    	        
                    	        }
                    	        
             	 	      });
                               }
                                 	        
                       });
                       	            	break;
                       	            case "A*":
                       	            	
                       	                break;
                       	            default:
                       	                System.out.println("Choix invalide.");
                       	        }
                        	           
                       	            panelgh.add(childPanel5);

                       	            panelgh.add(childPanel7);
                       	            panelgh.add(childPanel6);
                       	            panelgh.add(childPanel4);
                       	         panelgh.add(childPanel8);
                       	            
                       	            
                       	            panelgh.add(childPanel3);
                       	            panelgh.add(p);
                       	            JScrollPane scrollPane = new JScrollPane(childPanel3);
                       	            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Toujours afficher la barre de défilement verticale

                       	            frame.add(scrollPane);
                       	            
                       	            
                       	        }
                       	    });
                 
                  }
              });

             }
         });
         
         
         utiliserFichiersBtn.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 // Code à exécuter lorsque le bouton est cliqué
           	 
          	  
           	  CardLayout cl = (CardLayout)(cards.getLayout());
                 cl.show(cards, "card3");
          	    panelgh.setBackground( Color.black);
          	    

          	    JTextPane textPane1 = createTextPane("Choisissez un algorithme");
          	  
          	   
          	    panelgh.add(textPane1);
          	    
          	    
          	   // 

          	   String[] choices = {"BSO", "GA"};
          	    final JComboBox<String> menu = new JComboBox<>(choices);
          	   final Dimension menuDimension = new Dimension(135, 40); // Définir les dimensions souhaitées
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
          	            
          
          	           
         	    	    JTextPane textPane2 = createTextPane("Choisissez un fichier d'objets");
         	    	    textPane2.setAlignmentX(Component.CENTER_ALIGNMENT);
         	    	    childPanel.add(textPane2);
         	
         	    	   // 

         	    JButton fileButton = new JButton("Choisir un fichier");
         	    fileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
         	    fileButton.setPreferredSize(menuDimension);
         	    fileButton.setBackground(Color.DARK_GRAY);

         	    

         	    childPanel.add(fileButton);
         	   
         	  fileButton.setForeground(Color.white);
         	  panelgh.add(childPanel, BorderLayout.CENTER);
         	   
             
                 fileButton.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                         JFileChooser fileChooser = new JFileChooser();
                         int returnValue = fileChooser.showOpenDialog(null);
                         if (returnValue == JFileChooser.APPROVE_OPTION) {
                             selectedFile = fileChooser.getSelectedFile();
                             chemin_objet = selectedFile.getAbsolutePath();
                             System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());

                             objets = conv.convertire_tab_obj(chemin_objet);
                        
                             
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

                 	             
                 	               JTextPane textPaneChoice = createTextPane("Algorithme  : " + selectedChoice);
                 	             // panelgh.add(textPaneChoice);
                 	                sacs=conv.convertir_tab_sac(chemin_sac,objets.length);
                 	       
                 	                
                 	            } else {
                 	                System.out.println("Aucun fichier sélectionné");
                 	            }
                 	           final JPanel childPanel3 = new JPanel();        	        
               	            childPanel3.setBackground(Color.black);
               	            final JPanel childPanel8 = new JPanel();        	        
               	            childPanel8.setBackground(Color.black);
               	            final JPanel childPanel7 = new JPanel();        	        
              	            childPanel7.setBackground(Color.black);
               	            
              	         final JPanel childPanel4 = new JPanel();
               	            childPanel4.setLayout(new BoxLayout(childPanel4, BoxLayout.Y_AXIS));
               	            childPanel4.setBackground(Color.black);
               	            final    JPanel childPanel5 = new JPanel();
               	            childPanel5.setLayout(new BoxLayout(childPanel5, BoxLayout.Y_AXIS));
               	            childPanel5.setBackground(Color.black);
               	            final         JPanel childPanel6 = new JPanel();
               	            childPanel6.setLayout(new BoxLayout(childPanel6, BoxLayout.Y_AXIS));
               	            childPanel6.setBackground(Color.black);
               	         panelgh.removeAll();
                	            switch(selectedChoice) {
                	            case "BSO":
                	            	 panel = new JPanel();
                                     panel.setLayout(new GridLayout(4, 2));
                                     panel.setBackground( Color.black);

                                     poidsMaxLabel = createStyledLabel("Max iteration :");
                                     poidsMinLabel = createStyledLabel("Nombre population:");
                                     nbrSacLabel = createStyledLabel("Flip :");
                                     
                                    
                                     poidsMaxField = createStyledTextField();
                                     poidsMinField = createStyledTextField();
                                     nbrSacField = createStyledTextField();
                                     
                                    // panel.removeAll(); // Effacer tous les éléments précédents

                                     // Ajouter les éléments pour saisir les informations
                                     panel.add(poidsMaxLabel);
                                     panel.add(poidsMaxField);
                                     panel.add(poidsMinLabel);
                                     panel.add(poidsMinField);
                                     panel.add(nbrSacLabel);
                                     panel.add(nbrSacField);
                                    
                                     
                                    // panel.add(nouvelleInstanceBtn);
                                     panelgh.add(panel);
                                     valider = new JButton("valider");
                                     valider.setBackground(Color.DARK_GRAY);
                                     valider.setForeground(Color.white);
                                     panel.add(valider);
                                     valider.addActionListener(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {
                                       	  int maxit = Integer.parseInt(poidsMaxField.getText());
                                       	  int nbrpop = Integer.parseInt(poidsMinField.getText());
                                       	  int flip = Integer.parseInt(nbrSacField.getText());
                                       	                                      
                                       	  panel.removeAll();
                       	            	
                       	            	
                       	            	
                       	            	
                       	            	
                       	            	
                       	            	
                       	            	//childPanel3.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels

                       	            	 bso=b.bso1(sacs,objets,maxit,nbrpop,flip);
                       	            	 System.out.println("val"+bso.dure);
                       	              
                        	 	    	 JTextPane nbrn11 = createTextPane("les valeurs de la 1 Sref : poid: "+String.valueOf(bso.solinit.poid_total)
                        	 	    	 +"  valeur:"+String.valueOf(bso.solinit.valeur_total));
                        	 	    	nbrn11.setAlignmentX(Component.CENTER_ALIGNMENT);
                   	 	    	    	
                        	 	    	childPanel5.add(nbrn11);
       	 	          	            	 JTextPane duree = createTextPane("  La durée: "+String.valueOf(bso.dure)+"s");
       	 	          	            	 duree.setAlignmentX(Component.CENTER_ALIGNMENT);
       	 	          	            	 childPanel7.add(duree);
       	 	          	            	 childPanel7.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels
       	 	          	 	    	  // frame.pack();
       	 	          	 	    	  //  
       	 	        	            	// frame.pack();
       	 	        	 	    	   // 
       	 	        	 	    	 
       	 	   	            	// frame.pack();
       	 	   	 	    	   // 
       	 	   	 	    	  JTextPane val = createTextPane(" Les valeurs de la meilleur solution   ");
       	 	      	 	    	val.setAlignmentX(Component.CENTER_ALIGNMENT);
       	 	  	 	    	    	
       	 	      	 	    	childPanel6.add(val);
       	 	      	 	 JTextPane val1 = createTextPane("                 ");
	 	      	 	    	val1.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	  	 	    	    	
	 	      	 	    	childPanel8.add(val1);
       	 	      	 	   JTextPane prof11 = createTextPane("    Le poid: "+String.valueOf(bso.meilleur_sol.poid_total)
    	 	   	 	    	  +"    La valeur:  "+String.valueOf(bso.meilleur_sol.valeur_total));
           	 	    	  prof11.setAlignmentX(Component.CENTER_ALIGNMENT);
         	 	    	    	
           	 	    	childPanel4.add(prof11);
       	 	  	 	    	    //
       	 	    	 	    	// Création de la JTextArea
       	 	  	 	        JTextArea textArea = new JTextArea();
       	 	  	 	        textArea.setEditable(false);
       	 	  	 	        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18)); // Utilise une police monospace pour que les espaces soient alignés
       	 	  	 	        textArea.setMargin(new Insets(10, 10, 10, 10)); // Marge pour un meilleur espacement
       	 	  	 	       textArea.setBackground(Color.GRAY);
       	 	  	 	      textArea.setForeground(Color.WHITE);
       	 	  	 	      
       	 	  	 	        // Construction de la chaîne de caractères pour chaque sac
       	 	  	 	        for (int e1 = 0; e1 < bso.meilleur_sol.sacs.size() ; e1++) {
       	 	  	 	            StringBuilder sacInfo = new StringBuilder();
       	 	  	 	            sacInfo.append("Sac ").append(e1 + 1).append(":").append("\n");
       	 	  	 	            sacInfo.append("Poids max: ").append( bso.meilleur_sol.sacs.get(e1).poidsmax).append(", ");
       	 	  	 	            sacInfo.append("Valeur max: ").append(bso.meilleur_sol.sacs.get(e1).valmin).append("\n");

       	 	  	 	            sacInfo.append("Poids courant: ").append(bso.meilleur_sol.sacs.get(e1).poidcour).append(", ");
       	 	  	 	            sacInfo.append("Valeur courante: ").append(bso.meilleur_sol.sacs.get(e1).valcour).append("\n");
       	 	  	 	            sacInfo.append("Objets:\n");
       	 	  	 	            for (int f = 0; f < bso.meilleur_sol.sacs.get(e1).objets.size(); f++) {
       	 	  	 	              
       	 	  	 	                    sacInfo.append("   Objet ").append(bso.meilleur_sol.sacs.get(e1).objets.get(f).num).append(": ");
       	 	  	 	                    sacInfo.append("Poids=").append(bso.meilleur_sol.sacs.get(e1).objets.get(f).poids).append(", ");
       	 	  	 	                    sacInfo.append("Valeur=").append(bso.meilleur_sol.sacs.get(e1).objets.get(f).valeur).append("\n");

       	 	  	 	            }
       	 	  	 	            sacInfo.append("\n");
       	 	  	 	            textArea.append(sacInfo.toString());
       	 	  	 	        }
       	 	  	 	        textArea.add(childPanel6);
       	 	  	 	        
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
               	 	       
               	 	   p.add(rerunButton);
          	 	      rerunButton.addActionListener(new ActionListener() {
                 	        public void actionPerformed(ActionEvent e) { 
                 	        	
                 	        	rerunCode();
                 	        
                 	        
                 	        }
                 	        
          	 	      });
                            }
                              	        
                    });
              	            	
                	                // Ajoutez votre code pour l'algorithme DFS ici
                	                break;
                	            case "GA":
                	            	panel = new JPanel();
                                    panel.setLayout(new GridLayout(3, 2));
                                    panel.setBackground( Color.black);

                                    poidsMaxLabel = createStyledLabel("Max iteration :");
                                    poidsMinLabel = createStyledLabel("Nombre population:");
                                   // nbrSacLabel = createStyledLabel("Flip :");
                                    
                                   
                                    poidsMaxField = createStyledTextField();
                                    poidsMinField = createStyledTextField();
                                 //   nbrSacField = createStyledTextField();
                                    
                                   // panel.removeAll(); // Effacer tous les éléments précédents

                                    // Ajouter les éléments pour saisir les informations
                                    panel.add(poidsMaxLabel);
                                    panel.add(poidsMaxField);
                                    panel.add(poidsMinLabel);
                                    panel.add(poidsMinField);
                                 //   panel.add(nbrSacLabel);
                                 //   panel.add(nbrSacField);
                                   
                                    
                                   // panel.add(nouvelleInstanceBtn);
                                    panelgh.add(panel);
                                    valider = new JButton("valider");
                                    valider.setBackground(Color.DARK_GRAY);
                                    valider.setForeground(Color.white);
                                    panel.add(valider);
                                    valider.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                      	  int maxit = Integer.parseInt(poidsMaxField.getText());
                                      	  int nbrpop = Integer.parseInt(poidsMinField.getText());
                                      	  //int flip = Integer.parseInt(nbrSacField.getText());
                                      	                                      
                                      	  panel.removeAll();
                      	            	
                      	            	
                      	            	
                      	            	
                      	            	
                      	            	
                      	            	
                      	            	//childPanel3.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels

                      	            	 ga=g.genetique_algo(maxit,nbrpop,sacs,objets);
                      	            	 System.out.println("val"+ga.dure);
                      	              
                       	 	    	/* JTextPane nbrn11 = createTextPane("les valeurs de la 1 Sref : poid: "+String.valueOf(bso.solinit.poid_total)
                       	 	    	 +"  valeur:"+String.valueOf(bso.solinit.valeur_total));
                       	 	    	nbrn11.setAlignmentX(Component.CENTER_ALIGNMENT);
                  	 	    	    	
                       	 	    	childPanel5.add(nbrn11);*/
      	 	          	            	 JTextPane duree = createTextPane("  La durée: "+String.valueOf(ga.dure)+"s");
      	 	          	            	 duree.setAlignmentX(Component.CENTER_ALIGNMENT);
      	 	          	            	 childPanel7.add(duree);
      	 	          	            	 childPanel7.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels
      	 	          	 	    	  // frame.pack();
      	 	          	 	    	  //  
      	 	        	            	// frame.pack();
      	 	        	 	    	   // 
      	 	        	 	    	 
      	 	   	            	// frame.pack();
      	 	   	 	    	   // 
      	 	   	 	    	  JTextPane val = createTextPane(" Les valeurs de la meilleur solution   ");
      	 	      	 	    	val.setAlignmentX(Component.CENTER_ALIGNMENT);
      	 	  	 	    	    	
      	 	      	 	    	childPanel6.add(val);
      	 	      	 	 JTextPane val1 = createTextPane("                 ");
	 	      	 	    	val1.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	  	 	    	    	
	 	      	 	    	childPanel8.add(val1);
      	 	      	 	   JTextPane prof11 = createTextPane("    Le poid: "+String.valueOf(ga.meilleur_sol.poid_total)
   	 	   	 	    	  +"    La valeur:  "+String.valueOf(ga.meilleur_sol.valeur_total));
          	 	    	  prof11.setAlignmentX(Component.CENTER_ALIGNMENT);
        	 	    	    	
          	 	    	childPanel4.add(prof11);
      	 	  	 	    	    //
      	 	    	 	    	// Création de la JTextArea
      	 	  	 	        JTextArea textArea = new JTextArea();
      	 	  	 	        textArea.setEditable(false);
      	 	  	 	        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18)); // Utilise une police monospace pour que les espaces soient alignés
      	 	  	 	        textArea.setMargin(new Insets(10, 10, 10, 10)); // Marge pour un meilleur espacement
      	 	  	 	       textArea.setBackground(Color.GRAY);
      	 	  	 	      textArea.setForeground(Color.WHITE);
      	 	  	 	      
      	 	  	 	        // Construction de la chaîne de caractères pour chaque sac
      	 	  	 	        for (int e1 = 0; e1 < ga.meilleur_sol.sacs.size() ; e1++) {
      	 	  	 	            StringBuilder sacInfo = new StringBuilder();
      	 	  	 	            sacInfo.append("Sac ").append(e1 + 1).append(":").append("\n");
      	 	  	 	            sacInfo.append("Poids max: ").append( ga.meilleur_sol.sacs.get(e1).poidsmax).append(", ");
      	 	  	 	            sacInfo.append("Valeur max: ").append(ga.meilleur_sol.sacs.get(e1).valmin).append("\n");

      	 	  	 	            sacInfo.append("Poids courant: ").append(ga.meilleur_sol.sacs.get(e1).poidcour).append(", ");
      	 	  	 	            sacInfo.append("Valeur courante: ").append(ga.meilleur_sol.sacs.get(e1).valcour).append("\n");
      	 	  	 	            sacInfo.append("Objets:\n");
      	 	  	 	            for (int f = 0; f < ga.meilleur_sol.sacs.get(e1).objets.size(); f++) {
      	 	  	 	              
      	 	  	 	                    sacInfo.append("   Objet ").append(ga.meilleur_sol.sacs.get(e1).objets.get(f).num).append(": ");
      	 	  	 	                    sacInfo.append("Poids=").append(ga.meilleur_sol.sacs.get(e1).objets.get(f).poids).append(", ");
      	 	  	 	                    sacInfo.append("Valeur=").append(ga.meilleur_sol.sacs.get(e1).objets.get(f).valeur).append("\n");

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
              	 	       
              	 	   p.add(rerunButton);
         	 	      rerunButton.addActionListener(new ActionListener() {
                	        public void actionPerformed(ActionEvent e) { 
                	        	
                	        	rerunCode();
                	        
                	        
                	        }
                	        
         	 	      });
                           }
                             	        
                   });
                	            	              break;
                	            case "A*":
                	            	
                	                break;
                	            default:
                	                System.out.println("Choix invalide.");
                	        }
                	            panelgh.add(childPanel5);

                   	            panelgh.add(childPanel7);
                   	            panelgh.add(childPanel6);
                   	            panelgh.add(childPanel4);
                   	         panelgh.add(childPanel8);
                   	            
                   	            
                   	            panelgh.add(childPanel3);
                	           panelgh.add(p);


                	            JScrollPane scrollPane = new JScrollPane(childPanel3);
                	            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Toujours afficher la barre de défilement verticale

                	            frame.add(scrollPane);
                	            
                	            
                	        }
                	    });             	  
                        
                    }
                });
         	        }
         	    });
             }
         });
         
      
      
      
      
      
      frame.add(cards);
      
      	setBoldFont(frame);

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

 	
       
     private static JLabel createStyledLabel(String text) {
         JLabel label = new JLabel(text);
         label.setBackground(Color.DARK_GRAY);
         label.setForeground(Color.WHITE);
         label.setFont(new Font("Arial", Font.PLAIN, 17));
         return label;
     }
     private static JTextField createStyledTextField() {
         JTextField textField = new JTextField();
         textField.setBackground(Color.DARK_GRAY);
         textField.setForeground(Color.WHITE);
         textField.setFont(new Font("Arial", Font.PLAIN, 17));
         return textField;
     }

     private static void setBoldFont(Component component) {
         if (component instanceof Container) {
             for (Component child : ((Container) component).getComponents()) {
                 setBoldFont(child); // Appel récursif pour les composants enfants
             }
         }
         if (component instanceof JComponent) {
             Font font = component.getFont();
             if (font != null) {
                 ((JComponent) component).setFont(font.deriveFont(Font.BOLD));
             }
         }
     }
     private static void rerunCode() {
         // Fermer le frame actuel
         frame.dispose();
         
         // Réexécuter le code
         createAndShowGUI();
     }
    	    
     
   }