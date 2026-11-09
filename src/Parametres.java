/**
 * Paramètres de l'équipe. Remplissez GROUPE et MEMBRES au checkpoint 0,
 * puis ne modifiez plus rien : les autres valeurs sont calculées à partir de GROUPE.
 */
public final class Parametres {
    public static final int GROUPE = 2;                                   // numéro donné par l'enseignant
    public static final String[] MEMBRES = {
        "A : Aida 1015748 (1015748)",
        "B : Mairam Thierno Ndiath(8651143)",
        "C : Saoudatou Gano (8771213)"
    };

    public static final int TAUX_FRAIS = 10 + GROUPE % 6;                 // pour mille (10 = 1 %)
    public static final long PLAFOND_RETRAIT = 150_000 + 10_000 * (GROUPE % 8);   // FCFA
    public static final long SEUIL_PREMIUM = 50_000;                      // FCFA
    public static final int CAPACITE = 20;                                // comptes

    private Parametres() { }
}
