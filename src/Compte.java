public abstract class Compte {

    private static int nombreCrees = 0;

    private String numero;
    private String titulaire;
    private long solde=0;

    public Compte (String numero, String titulaire){
        this.numero=numero;
        this.titulaire=titulaire;
        nombreCrees++;
    }

    public String getNumero () {
        return this.numero;
    }

    public String getTitulaire (){
        return this.titulaire;
    }

    public long getSolde (){
        return this.solde;
    }

    public static int getNombreCrees() {
        return nombreCrees;
    }

    public abstract long frais(long montant);
    public abstract long plafond();
    public abstract String type();

    public boolean deposer(long montant){
        if (montant <= 0) return false; 
        this.solde += montant; 
        return true;
        
    }
    public boolean retirer(long montant) {
        if (montant <= 0 || montant > plafond() ) return false;
        long total = montant + frais( montant) ;
        if (total >  this.solde) return false;
        this.solde -= total ;
        return true;
    }

    @Override
    public String toString() {
        return "[" + type() + "] " + numero + " " + titulaire + " " + solde + " FCFA";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compte)) return false;
        Compte autre = (Compte) o;
        return this.numero.equals(autre.numero);
    }

    @Override
    public int hashCode() {
        return numero.hashCode();
    }
        
}