package dao;

import model.Utilisateur;
//import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class DAOUtilisateur {

    private static final String URL = "jdbc:mysql://localhost:3306/SportiZone";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Utilisateur connexion(String email, String password) {
        Utilisateur user = null;

        String sql = "SELECT * FROM user WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("mdp");

                    if (password.equals(hashedPassword)) {
                        int id = rs.getInt("id_user");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");
                        int role = rs.getInt("role");

                        user = new Utilisateur(id, nom, prenom, email, role);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
