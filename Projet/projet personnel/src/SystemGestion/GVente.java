package SystemGestion;

import SystemComonde.Command;

public interface GVente {
    public void creationCommandEnLine(Client c);
    public void valideCommand(Command com, int choix);
    public void valideCommandEnline();
    public void calcule(Command com);
}
