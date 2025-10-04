
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
                       	            case "BSO":
                       	            	
                       	            	//childPanel3.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels

                       	            	 bso=d.bso1(sacs,objets,1,2,0);
                       	            	 JTextPane duree = createTextPane("La durée: "+String.valueOf(bso.dure)+"s");
                       	            	 duree.setAlignmentX(Component.CENTER_ALIGNMENT);
                       	            	 childPanel7.add(duree);
                       	            	 childPanel7.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels
                       	 	    	  // frame.pack();
                       	 	    	  //  
                     	            	// frame.pack();
                     	 	    	   // 
                     	 	    	 
                	            	// frame.pack();
                	 	    	   // 
                	 	    	  JTextPane val = createTextPane("Le poid:  "+String.valueOf(bso.meilleur_sol.poid_total)
                	 	    	  +"La valeur:  "+String.valueOf(bso.meilleur_sol.valeur_total));
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
               	 	       
               	 	       
               	 	       
               	 	   import javax.swing.*;
               	 	import javax.swing.text.SimpleAttributeSet;
               	 	import javax.swing.text.StyleConstants;
               	 	import javax.swing.text.StyledDocument;

               	 	import java.awt.*;
               	 	import java.awt.event.ActionEvent;
               	 	import java.awt.event.ActionListener;

               	 	public class Interface {

               	 	    private static JLabel poidsMaxLabel, poidsMinLabel, nbrSacLabel, nbrObjetLabel, valeurMaxLabel, valeurMinLabel;
               	 	    private static JTextField poidsMaxField, poidsMinField, nbrSacField, nbrObjetField, valeurMaxField, valeurMinField;
               	 	    private static JButton valider;

               	 	    public static void main(String[] args) {
               	 	        createAndShowGUI();
               	 	    }

               	 	    private static void createAndShowGUI() {
               	 	        final JFrame frame = new JFrame("Interface Utilisateur");
               	 	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

               	 	        final JPanel panel = new JPanel(new GridLayout(7, 2));
               	 	        panel.setBackground(Color.black);

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

               	 	        valider = new JButton("Valider");
               	 	        valider.setBackground(Color.DARK_GRAY);
               	 	        valider.setForeground(Color.white);
               	 	        valider.addActionListener(new ActionListener() {
               	 	            @Override
               	 	            public void actionPerformed(ActionEvent e) {
               	 	                // Récupérer les valeurs saisies par l'utilisateur et effectuer les actions nécessaires
               	 	                int poidsMax = Integer.parseInt(poidsMaxField.getText());
               	 	                int poidsMin = Integer.parseInt(poidsMinField.getText());
               	 	                int nbrSac = Integer.parseInt(nbrSacField.getText());
               	 	                int nbrObjet = Integer.parseInt(nbrObjetField.getText());
               	 	                int valeurMax = Integer.parseInt(valeurMaxField.getText());
               	 	                int valeurMin = Integer.parseInt(valeurMinField.getText());
               	 	                final String fo=cree_fichier.cree_fichie_objet(poidsMax,poidsMin, valeurMax, valeurMin, nbrObjet);
               	 	          	  final String fs=cree_fichier.cree_fichie_sac(poidsMax,poidsMin, valeurMax, valeurMin, nbrObjet,nbrSac);
               	 	          
               	 	          	 
               	 	          	 
               	 	                // Afficher le message et le menu déroulant pour choisir l'algorithme
               	 	                JTextPane textPane1 = createTextPane("Choisissez un algorithme");
               	 	                panel.removeAll();
               	 	                final JPanel panelgh=new JPanel();
               	 					panelgh.add(textPane1);

               	 	                String[] choices = {"BSO", "GA"};
               	 	                final JComboBox<String> menu = new JComboBox<>(choices);
               	 	                Dimension menuDimension = new Dimension(135, 40); // Définir les dimensions souhaitées
               	 	                menu.setPreferredSize(menuDimension);
               	 	                menu.setAlignmentX(Component.CENTER_ALIGNMENT);
               	 	                menu.setBackground(Color.DARK_GRAY);
               	 	                menu.setForeground(Color.white);
               	 	                panelgh.add(menu);

               	 	                JButton button = new JButton("<html><b>Sélectionner</b></html>");
               	 	                button.setAlignmentX(Component.CENTER_ALIGNMENT);
               	 	                button.setBackground(Color.DARK_GRAY);
               	 	                button.setForeground(Color.white);
               	 	                button.setPreferredSize(menuDimension);
               	 	                panelgh.add(button);

               	 	                button.addActionListener(new ActionListener() {
               	 	                    @Override
               	 	                    public void actionPerformed(ActionEvent e) {
               	 	                        String selectedChoice = (String) menu.getSelectedItem();
               	 	                        System.out.println("Choix sélectionné : " + selectedChoice);
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
               	 	        	            
               	 	                        
               	 	                        
               	 	                        
               	 	                        // Afficher les informations spécifiques à l'algorithme choisi
               	 	                        if (selectedChoice.equals("BSO")) {
               	 	                            // Afficher les informations spécifiques à l'algorithme BSO
               	 	                            // par exemple, les résultats de l'algorithme BSO
               	 	                        	
               	 	                        	objet[] objets = conv.convertire_tab_obj(fo); 
               	 	                         	 Sacàdos[] sacs=conv.convertir_tab_sac(fs,objets.length);
               	 	                         	 typeretourbso bso;
               	 	          	            	  bso b = new bso();
               	 	          	            	  bso=b.bso1(sacs,objets,1,2,0);
               	 	          	            	 JTextPane duree = createTextPane("La durée: "+String.valueOf(bso.dure)+"s");
               	 	          	            	 duree.setAlignmentX(Component.CENTER_ALIGNMENT);
               	 	          	            	 childPanel7.add(duree);
               	 	          	            	 childPanel7.add(Box.createVerticalStrut(10)); // Ajout d'un espace de 10 pixels
               	 	          	 	    	  // frame.pack();
               	 	          	 	    	  //  
               	 	        	            	// frame.pack();
               	 	        	 	    	   // 
               	 	        	 	    	 
               	 	   	            	// frame.pack();
               	 	   	 	    	   // 
               	 	   	 	    	  JTextPane val = createTextPane("Le poid:  "+String.valueOf(bso.meilleur_sol.poid_total)
               	 	   	 	    	  +"La valeur:  "+String.valueOf(bso.meilleur_sol.valeur_total));
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
               	 	  	 	    panelgh.add(childPanel7);
               	 		            panelgh.add(childPanel4);
               	 		            panelgh.add(childPanel5);
               	 		            panelgh.add(childPanel6);
               	 		            
               	 		            panelgh.add(childPanel3);
               	 		           
               	 		            JScrollPane scrollPane = new JScrollPane(childPanel3);
               	 		            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Toujours afficher la barre de défilement verticale

               	 		            frame.add(scrollPane);
               	 	                        } else if (selectedChoice.equals("GA")) {
               	 	                            // Afficher les informations spécifiques à l'algorithme GA
               	 	                            // par exemple, les résultats de l'algorithme GA
               	 	                        }
               	 	                    }
               	 	                });
               	 	            }
               	 	        });
               	 	        panel.add(valider);

               	 	        frame.add(panel);
               	 	        frame.pack();
               	 	        frame.setLocationRelativeTo(null);
               	 	        frame.setVisible(true);
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
               	 	}

