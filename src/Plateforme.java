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
       
        Compte source = this.rechercher(de);
        Compte destinataire = this.rechercher(vers);
        
        if(source == null || destinataire == null){
            return false;
        }
         if(de.equals(vers)){
            return false;
        }
        
        if(source.retirer(montant)){
            destinataire.deposer(montant);
            long frais = source.frais(montant);
            this.totalFrais += frais;
            if(cptJournal < 100){
                journal[cptJournal] = String.format("TX%04d %s -> %s %d FCFA (frais %d)",cptJournal+1,de,vers,montant,frais);
                cptJournal += 1;
            }
            return true;
        }else{
            return false;
        }
        
    }
    public String[] journal(){
        String[] copieJournal = new String[cptJournal];
        for(int i = 0; i < cptJournal; i++) {
                copieJournal[i] = journal[i];
        }
        return copieJournal;
    }
    public long getTotalFrais(){
        return totalFrais;
    }
    public int[] compterParType(){
        int[] nombreTypeCompte = new  int[3];
        for(int i = 0 ; i < cptCompte ; i++){
            if (comptes[i].type().equals("STD")) {
                nombreTypeCompte[0] += 1;
            }else if (comptes[i].type().equals("PRM")) {
                nombreTypeCompte[1] += 1;
                
            }else if (comptes[i].type().equals("MAR")){
                nombreTypeCompte[2] += 1;
            }
        }
        return nombreTypeCompte;
    }
    public void trierParSolde(){
        
        for(int i = 1; i < this.cptCompte; i++){
            Compte temp = comptes[i];
            int j = i;
            while(j > 0 && (comptes[j - 1].getSolde() < temp.getSolde() || (comptes[j - 1].getSolde() == temp.getSolde() && comptes[j-1].getNumero().compareTo(temp.getNumero()) > 0))){
                
                     comptes[j] = comptes[j-1];
                     comptes[j-1] = temp;
                     j--;
                }
            }
        }
    }



