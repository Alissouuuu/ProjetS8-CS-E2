package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.DBConnexion;

/**
 * Classe DAO pour gérer les opérations CRUD sur les utilisateurs
 * 
 * @author imane
 * @version 1.0
 */
public class UserDAO {
	/**
     * Ajoute un nouvel utilisateur dans la base de données
     * 
     * @param user l'utilisateur à ajouter
     * @return l'ID de l'utilisateur créé, -1 en cas d'erreur
     */
    public boolean create(User user) throws SQLException{
        String sql = "INSERT INTO user (nom, prenom, email, role, fonction, statut_demande, mdp, piece_justificative) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getNom());
            pstmt.setString(2, user.getPrenom());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getRole());
            pstmt.setString(5, user.getFonction());
            pstmt.setInt(6, user.getStatutDemande());
            pstmt.setString(7, user.getMdp());
            pstmt.setBytes(8, user.getJustificatifDonnees());
        
            
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;  
        }
       
    }
    /**
     * verifier si une adresse mail est déja enregistrée dans la bdd
     */
    public boolean emailEstUtilise(String email) {
    	String sqlCount = "SELECT COUNT(*) FROM user WHERE email = ?";
    	try (Connection conn = DBConnexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sqlCount)) {
               
               pstmt.setString(1, email);
            
               
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                	   //verifier si la valeur retournée n'est pas nulle
                	   if (rs.next()) {
                           int count = rs.getInt(1);
                           return count > 0;
                       }
                   }
               }
           } catch (SQLException e) {
               System.err.println("Erreur lors de l'authentification: " + e.getMessage());
           }
    	return false; // au cas où l'mail n'est pas trouvé
    }
    /**
     * Verifie les identifiants de connexion d'un utilisateur
     * 
     * @param email l'email de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return l'utilisateur si les identifiants sont corrects, null sinon
     */
    
   /* public User authenticate(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND mdp = ?";
        
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification: " + e.getMessage());
        }
        
        return null;
    }*/
    public User findByEmail(String email) {
        User user = null;
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE email = ?")) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));
                user.setRole(rs.getInt("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // À remplacer par du logging en prod
        }
        return user;
    }
    /**
     * Convertit un ResultSet en objet User
     * 
     * @param rs le ResultSet à convertir
     * @return l'objet User correspondant
     * @throws SQLException si une erreur survient lors de la récupération des données
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setIdUser(rs.getInt("id_user"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getInt("role"));
        user.setFonction(rs.getString("fonction"));
        user.setStatutDemande(rs.getInt("statut_demande"));
        user.setMdp(rs.getString("mdp"));
        user.setNomFichier(rs.getString("nom_fichier"));
        user.setTypeFichier(rs.getString("type_fichier"));
        user.setTailleFichier(rs.getLong("taille_fichier"));
        user.setJustificatifDonnees(rs.getBytes("justificatif_donnees"));
        return user;
    }
}
