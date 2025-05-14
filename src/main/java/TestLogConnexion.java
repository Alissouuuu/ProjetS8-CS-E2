package test; // ou le package que tu veux

import dao.LogConnexionDAO;

public class TestLogConnexion {
    public static void main(String[] args) {
        int userId = 1; // ⚠️ Mets ici l’ID d’un utilisateur réel existant dans ta table user

        // Enregistrer une connexion réussie
        LogConnexionDAO.enregistrerConnexion(userId, true, null);

        // Enregistrer une tentative échouée
        LogConnexionDAO.enregistrerConnexion(userId, false, null);

        System.out.println("✔︎ Logs insérés dans la base !");
    }
}

