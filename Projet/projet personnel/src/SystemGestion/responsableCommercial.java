package SystemGestion;

public class responsableCommercial extends Serv {
    static private String motePasseResponsable = "responsable";

    public responsableCommercial (){}

    public void modifMotsPasseRessponsable() {
        System.out.println("entre le mote passe  de le responsable ");
        String mots1 = sc.next();
        if (mots1.equals(motePasseResponsable)) {
            System.out.println("entre nouveau le mote passe  de le responsable ");
            String mots2 = sc.next();
            setMotePasseResponsable(mots2);
            System.out.println("le mote passe est bien modifier : " + motePasseResponsable);
        }
    }

    
    public void setMotePasseResponsable(String motePasseResponsable) {
        responsableCommercial.motePasseResponsable = motePasseResponsable;
    }

    public String getMotePasseResponsable() {
        return motePasseResponsable;
    }

}
