public class Ussd {

    // C1 - 1 pt
    public static boolean numeroValide(String n) {
        if (n == null) return false;
        if (n.length() != 9) return false;

        // Tous des chiffres ?
        for (int i = 0; i < 9; i++) {
            char c = n.charAt(i);
            if (c < '0' || c > '9') return false;
        }

        // Prefixe : 70, 75, 76, 77, 78
        String prefixe = n.substring(0, 2);
        if (prefixe.equals("70") || prefixe.equals("75") || prefixe.equals("76")
            || prefixe.equals("77") || prefixe.equals("78")) {
            return true;
        }
        return false;
    }

    // C2 - 1 pt
    public static String normaliser(String s) {
        if (s == null) return null;

        StringBuilder nettoye = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // On garde tout sauf espace, point, tiret
            if (c != ' ' && c != '.' && c != '-') {
                nettoye.append(c);
            }
        }
        String res = nettoye.toString();

        // Enlever +221 ou 00221
        if (res.length() >= 4 && res.substring(0, 4).equals("+221")) {
            res = res.substring(4);
        } else if (res.length() >= 5 && res.substring(0, 5).equals("00221")) {
            res = res.substring(5);
        }

        if (numeroValide(res)) {
            return res;
        }
        return null;
    }

    // C3 - 0,5 pt
    public static String masquer(String n) {
        if (n == null) return null;
        if (n.length() != 9) return n; // ou return null selon Verification, garde simple
        return n.substring(0, 2) + "*****" + n.substring(7, 9);
    }

    // C4 - 1 pt : SANS parseLong
    public static long lireMontant(String s) {
        if (s == null) return -1;
        if (s.length() == 0 || s.length() > 9) return -1;

        long resultat = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return -1;
            resultat = resultat * 10 + (c - '0');
        }
        return resultat;
    }

    // C5 - 1 pt
    public static String formaterMontant(long m) {
        if (m == 0) return "0";
        String texte = "" + m; // on transforme en texte sans parse
        StringBuilder res = new StringBuilder();
        int compteur = 0;
        // On part de la droite
        for (int i = texte.length() - 1; i >= 0; i--) {
            res.insert(0, texte.charAt(i));
            compteur++;
            if (compteur == 3 && i != 0) {
                res.insert(0, ' ');
                compteur = 0;
            }
        }
        return res.toString();
    }

    // C6 - 1,5 pt : SANS split
    public static String[] decouper(String code) {
        if (code == null) return null;
        if (code.length() < 3) return null;
        if (code.charAt(0) != '*' || code.charAt(code.length() - 1) != '#') return null;

        // On enleve * au debut et # a la fin
        String interieur = code.substring(1, code.length() - 1);

        // Compter combien de * pour connaitre la taille du tableau
        int nbEtoiles = 0;
        for (int i = 0; i < interieur.length(); i++) {
            if (interieur.charAt(i) == '*') nbEtoiles++;
        }
        String[] champs = new String[nbEtoiles + 1];

        int indexChamp = 0;
        int debut = 0;
        for (int i = 0; i < interieur.length(); i++) {
            if (interieur.charAt(i) == '*') {
                String champ = interieur.substring(debut, i);
                if (champ.length() == 0) return null; // champ vide -> *144**2#
                champs[indexChamp] = champ;
                indexChamp++;
                debut = i + 1;
            }
        }
        // Dernier champ
        String dernier = interieur.substring(debut);
        if (dernier.length() == 0) return null;
        champs[indexChamp] = dernier;

        return champs;
    }

    // I1 - Integration - 1,5 pt - ORDRE A RESPECTER
    public static String executer(Plateforme p, String appelant, String code) {
        String[] champs = decouper(code);
        if (champs == null) return "Code invalide";

        if (!champs[0].equals("144")) return "Service inconnu";

        Compte source = p.rechercher(appelant);
        if (source == null) return "Compte inconnu";

        // Service 1 = Transfert
        if (champs[1].equals("1")) {
            if (champs.length != 4) return "Code invalide";

            String destNormalise = normaliser(champs[2]);
            if (destNormalise == null) return "Numero invalide";

            long montant = lireMontant(champs[3]);
            if (montant == -1 || montant == 0) return "Montant invalide";

            long fraisAvant = p.getTotalFrais();
            boolean ok = p.transferer(appelant, destNormalise, montant);
            if (!ok) return "Transfert refuse";

            long frais = p.getTotalFrais() - fraisAvant;
            return "Transfert de " + formaterMontant(montant) + " FCFA vers "
                + masquer(destNormalise) + " effectue. Frais : "
                + formaterMontant(frais) + " FCFA";
        }

        // Service 2 = Solde
        if (champs[1].equals("2")) {
            if (champs.length != 2) return "Code invalide";
            return "Solde : " + formaterMontant(source.getSolde()) + " FCFA";
        }

        return "Service inconnu";
    }
}
