package utils;

import model.Utilisateur;

public class Session {
    private static Utilisateur utilisateurConnecte;

    public static void setUtilisateur(Utilisateur u) {
        utilisateurConnecte = u;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    public static void clear() {
        utilisateurConnecte = null;
    }
}
