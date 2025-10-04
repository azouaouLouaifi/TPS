package SystemGestion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import SystemCarte.Carte;
import SystemComonde.Command;

public class Client extends Magasin {
    protected static ArrayList<Command> listComm= new ArrayList<Command>();

    public Client (){}

    public void ControlAnnuleeCommande() {
        if(! listComm.isEmpty()){
            LocalDateTime Date = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateActuel = Date.format(myFormatObj);
            String tab[] = dateActuel.split("/");
            int  jourAcuel =Integer.parseInt(tab[1]) ;
            for(Command c : listComm){
                String dateDemmande  = c.getDateDemende();
                String tab2[] = dateDemmande.split("/");
                int jourDemmande  =Integer.parseInt(tab2[1]);
                if((jourDemmande+2-jourAcuel) == 0 || (jourDemmande+2-jourAcuel) <= jourDemmande){
                    c.romoveProduits();
                    listCommandValid.remove(c);
                    listComm.remove(c);
                }
            }
        }
       
    }

    public void ajoutComm(Command c){
        listComm.add(c);
    }


   
    public void valideCommand(Command com, int choix) {
        // TODO Auto-generated method stub

    }

    public void retourCommande() {
        // TODO Auto-generated method stub

    }

   
    public void modifPrix() {
        // TODO Auto-generated method stub

    }

 
    public Carte newCarte() {
        // TODO Auto-generated method stub
        return null;
    }

   
    public void calcule(Command com) {
        // TODO Auto-generated method stub

    }

    public void creationCommandEnLine(Client c) {
        super.creationCommandEnLine(c);
        
    }

    public void valideCommandEnline() {
        // TODO Auto-generated method stub
        
    }

   

    
}
