public class Plateforme {
    private Compte[] comptes;
    private int cptCompte;
    private String[] journal;
    private int cptJournal;
    private long totalFrais;
    public Plateforme(){
        comptes = new Compte[Parametres.CAPACITE];
        cptCompte  = 0;
        journal = new String[100];
        cptJournal = 0;
        totalFrais = 0;
    }
    // Methode ajouter 
    public boolean ajouter(Compte c){
        if(c == null){
            return  false;
        }
        if(rechercher(c.getNumero()) != null){
            return  false;
        }
        if(cptCompte  >= Parametres.CAPACITE){
            return false;
        }
        this.comptes[cptCompte] = c;
        cptCompte++;
        return true;
        
        
    }
    public int getNombre(){
        return  this.cptCompte;
    }
    public Compte rechercher(String numero){
        for (int i = 0; i < this.cptCompte; i++){
            if(this.comptes[i].getNumero().equals(numero) ){
                return  this.comptes[i];
               
            }
        }
        return null;
    }
    public boolean transferer(String de,String vers,long montant ){
        if(montant <= 0){
            return false;
        }
        if(de == vers){
            return false;
        }
        if(de.retire)
    }


}
