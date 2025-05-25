package dao;

import model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Utilisateur;

public class DAOUtilisateur {

    private static final String URL = "jdbc:mysql://localhost:3306/club_sport";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static String convertirIntEnRole(int code) {
        switch (code) {
            case 1:
                return "administrateur";
            case 2:
                return "élus";
            case 3:
                return "acteur du monde sportif";
            default:
                return "inconnu";
        }
    }

    public static int convertirRoleEnInt(String role) {
        if (role == null) return 0;
        switch (role.toLowerCase()) {
            case "administrateur": return 1;
            case "élus": return 2;
            case "acteur du monde sportif": return 3;
            default: return 0;
        }
    }

    public static Utilisateur connexion(String email, String password) {
        Utilisateur user = null;
        String sql = "SELECT * FROM user WHERE email = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, email);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String hashedPassword = rs.getString("mdp");


                        boolean match = BCrypt.checkpw(password, hashedPassword);

                        if (match) {

                            int id = rs.getInt("id_user");
                            String nom = rs.getString("nom");
                            String prenom = rs.getString("prenom");
                            int role = rs.getInt("role");
                            String fonction = rs.getString("fonction");

                            user = new Utilisateur(id, nom, prenom, email, role, fonction);
                        }
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<Utilisateur> findUser(String nom, String roleStr, String fonction) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM user WHERE 1=1");

        if (nom != null && !nom.trim().isEmpty()) {
            sql.append(" AND nom LIKE ?");
        }
        if (roleStr != null && !roleStr.trim().isEmpty()) {
            sql.append(" AND role = ?");
        }
        if (fonction != null && !fonction.trim().isEmpty()) {
            sql.append(" AND fonction LIKE ?");
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (nom != null && !nom.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + nom + "%");
            }
            if (roleStr != null && !roleStr.trim().isEmpty()) {
                int roleCode = convertirRoleEnInt(roleStr);
                stmt.setInt(paramIndex++, roleCode);
            }
            if (fonction != null && !fonction.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + fonction + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Utilisateur u = new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("role"),
                        rs.getString("fonction")
                    );
                    utilisateurs.add(u);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

    public List<Utilisateur> findInscriptionsAValider(String nom, String fonction) {
        List<Utilisateur> resultats = new ArrayList<>();

        String sql = "SELECT * FROM user WHERE statut_demande = 200 AND nom LIKE ? AND fonction LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nom + "%");
            stmt.setString(2, "%" + fonction + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id_user"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getInt("role"));
                u.setFonction(rs.getString("fonction"));
                u.setPieceJustificative(rs.getBytes("piece_justificative"));
                resultats.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }

    public boolean validerInscription(int idUser) {
        String sql = "UPDATE user SET statut_demande = 100 WHERE id_user = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> findAllRoleLabels() {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT DISTINCT role FROM user";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int roleCode = rs.getInt("role");
                String label = convertirIntEnRole(roleCode);
                if (!roles.contains(label)) {
                    roles.add(label);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }



    public boolean updateUser(Utilisateur utilisateur) {

        String sql = "UPDATE user SET nom = ?, prenom = ?, email = ?, role = ?, fonction = ? WHERE id_user = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getEmail());
            stmt.setInt(4, utilisateur.getRole());
            stmt.setString(5, utilisateur.getFonction());
            stmt.setInt(6, utilisateur.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // succès = au moins 1 ligne modifiée

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // échec
        }
    }


    public int addUser(String nom, String prenom, String email, String hashedPassword, int role, String fonction) {

        String sql = "INSERT INTO user (nom, prenom, email, mdp, role, fonction, statut_demande) VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (emailExiste(email)) {
            System.out.println("Email déjà utilisé.");
            return -1;
        }


        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setString(4, hashedPassword);
            stmt.setInt(5, role);
            stmt.setString(6, fonction);
            stmt.setInt(7, 100);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // ID généré
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public static int getUserIdByEmail(String email) {
        String sql = "SELECT id_user FROM user WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_user");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public byte[] getPieceJustificative(int userId) {
        String sql = "SELECT piece_justificative FROM user WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBytes("piece_justificative");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean verifierMotDePasse(String email, String motDePasse) {
        String sql = "SELECT mdp FROM user WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("mdp");
                    return BCrypt.checkpw(motDePasse, hash);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean mettreAJourMotDePasse(int idUser, String nouveauHash) {
        String sql = "UPDATE user SET mdp = ? WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nouveauHash);
            stmt.setInt(2, idUser);
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("role"),
                        rs.getString("fonction")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur findById(int id) {
        String sql = "SELECT * FROM user WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("role"),
                        rs.getString("fonction")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    
}
