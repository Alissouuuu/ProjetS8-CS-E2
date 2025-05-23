package dao;

import model.LogAdmin;
import model.Utilisateur;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import javax.swing.JOptionPane;

public class LogAdminDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/club_sport";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    // Enregistrer une action admin
    public static void enregistrerLog(LogAdmin log, Component component) {
        if (log.getId_admin() == null || log.getId_admin().getId() <= 0) {
            System.err.println("ID administrateur invalide, log non enregistré.");
            return;
        }

        String sql = "INSERT INTO log_admin (id_admin, type_action, type_entite, id_entite, " +
                     "ancienne_valeur, nouvelle_valeur, adresse_IP, dateHeureAction, reussite) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getId_admin().getId());
            stmt.setString(2, log.getType_action());
            stmt.setString(3, log.getType_entite());
            stmt.setInt(4, log.getId_entite());
            stmt.setString(5, log.getAncienne_valeur());
            stmt.setString(6, log.getNouvelle_valeur());
            stmt.setString(7, log.getAdresse_IP());
            stmt.setBoolean(8, log.isReussite());

            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(component, "Erreur lors de l'enregistrement du log admin", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Lire tous les logs
    public List<LogAdmin> findAllLogs() {
        List<LogAdmin> logs = new ArrayList<>();
        String sql = "SELECT la.*, u.id_user, u.nom, u.prenom, u.email, u.role, u.fonction " +
                     "FROM log_admin la JOIN user u ON la.id_admin = u.id_user " +
                     "ORDER BY la.dateHeureAction DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                logs.add(construireLog(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    // Lire les logs d’un administrateur spécifique
    public List<LogAdmin> findByAdmin(int idAdmin) {
        List<LogAdmin> logs = new ArrayList<>();
        String sql = "SELECT la.*, u.id_user, u.nom, u.prenom, u.email, u.role, u.fonction " +
                     "FROM log_admin la JOIN user u ON la.id_admin = u.id_user " +
                     "WHERE la.id_admin = ? ORDER BY la.dateHeureAction DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAdmin);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(construireLog(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    // Lire les logs entre deux dates
    public List<LogAdmin> findBetweenDates(LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<LogAdmin> logs = new ArrayList<>();
        String sql = "SELECT la.*, u.id_user, u.nom, u.prenom, u.email, u.role, u.fonction " +
                     "FROM log_admin la JOIN user u ON la.id_admin = u.id_user " +
                     "WHERE la.dateHeureAction BETWEEN ? AND ? " +
                     "ORDER BY la.dateHeureAction DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(dateDebut));
            stmt.setTimestamp(2, Timestamp.valueOf(dateFin));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(construireLog(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    // Filtres dynamiques comme dans LogConnexionDAO
    public List<LogAdmin> findLogsSortedBy(String statut, String nomRecherche, LocalDateTime dateDebut, LocalDateTime dateFin, boolean ordreCroissant) {
        List<LogAdmin> logs = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT la.*, u.id_user, u.nom, u.prenom, u.email, u.role, u.fonction " +
            "FROM log_admin la JOIN user u ON la.id_admin = u.id_user "
        );

        boolean hasWhere = false;

        if ("Succès".equalsIgnoreCase(statut)) {
            sql.append("WHERE la.reussite = true ");
            hasWhere = true;
        } else if ("Échec".equalsIgnoreCase(statut)) {
            sql.append("WHERE la.reussite = false ");
            hasWhere = true;
        }

        if (nomRecherche != null && !nomRecherche.isEmpty()) {
            sql.append(hasWhere ? "AND " : "WHERE ");
            sql.append("u.nom LIKE ? ");
            hasWhere = true;
        }

        if (dateDebut != null && dateFin != null) {
            sql.append(hasWhere ? "AND " : "WHERE ");
            sql.append("la.dateHeureAction BETWEEN ? AND ? ");
        }

        sql.append("ORDER BY la.dateHeureAction ").append(ordreCroissant ? "ASC" : "DESC");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (nomRecherche != null && !nomRecherche.isEmpty()) {
                stmt.setString(paramIndex++, "%" + nomRecherche + "%");
            }
            if (dateDebut != null && dateFin != null) {
                stmt.setTimestamp(paramIndex++, Timestamp.valueOf(dateDebut));
                stmt.setTimestamp(paramIndex++, Timestamp.valueOf(dateFin));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(construireLog(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    // Construction d’un objet LogAdmin à partir du ResultSet
    private LogAdmin construireLog(ResultSet rs) throws SQLException {
        Utilisateur admin = new Utilisateur(
            rs.getInt("id_user"),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("email"),
            rs.getInt("role"),
            rs.getString("fonction")
        );

        return new LogAdmin(
            rs.getInt("id_log"),
            admin,
            rs.getString("type_action"),
            rs.getString("type_entite"),
            rs.getInt("id_entite"),
            rs.getString("ancienne_valeur"),
            rs.getString("nouvelle_valeur"),
            rs.getString("adresse_IP"),
            rs.getTimestamp("dateHeureAction").toLocalDateTime(),
            rs.getBoolean("reussite")
        );
    }

    // IP locale
    public static String getIpLocale() {
        try {
            for (java.net.NetworkInterface ni : java.util.Collections.list(java.net.NetworkInterface.getNetworkInterfaces())) {
                for (java.net.InetAddress address : java.util.Collections.list(ni.getInetAddresses())) {
                    if (!address.isLoopbackAddress() && address instanceof java.net.Inet4Address) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
}
