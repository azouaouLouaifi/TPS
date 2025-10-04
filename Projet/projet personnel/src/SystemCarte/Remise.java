package SystemCarte;

import SystemComonde.Command;

public interface Remise {
    final double cat1 =  0.05 ;
    final double cat2 =  0.1 ;
    final double cat3 =  0.15 ;

            
    public void ajoutePrixProduitCarte (Command com);
    public void calcule (Command  com);
    public void valideCumul();
    public void nonValideCumul();
    public void affichage();
}
    
