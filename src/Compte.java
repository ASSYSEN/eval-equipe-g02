public abstract class Compte {
    private String numero;
    private String titulaire;
    private long solde;

    public Compte (String numero, String titulaire){
        this.numero=numero;
        this.titulaire=titulaire;
        this.solde=0;

        public String getNumero () {
            return this.numero;
        }

        public String getTitulaire (){
            return this.titulaire;
        }

        public long getSolde (){
            return this.solde;
        }

        public abstract  boolean deposer(long montant);
        public abstract  boolean retirer(long montant);
    }
}