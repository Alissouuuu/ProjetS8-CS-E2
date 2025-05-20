package dao;

import model.LogConnexion;
import model.Utilisateur;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import javax.swing.JOptionPane;

public class LogConnexionDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/sportizone";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public List<LogConnexion> findLogsSortedBy(String tri, String nomRecherche, LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<LogConnexion> logs = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT l.id_connexion, l.adresse_ip, l.tentative_connexion_echouee, l.date_connexion, " +
            "u.id_user, u.nom, u.prenom, u.email, u.role, u.fontion " +
            "FROM log_connexion l JOIN user u ON l.id_user = u.id_user "
        );

        boolean hasWhere = false;

        if ("Succès".equals(tri)) {
            sql.append("WHERE l.tentative_connexion_echouee = 0 ");
            hasWhere = true;
        } else if ("Échec".equals(tri)) {
            sql.append("WHERE l.tentative_connexion_echouee = 1 ");
            hasWhere = true;
        }

        if (nomRecherche != null && !nomRecherche.isEmpty()) {
            sql.append(hasWhere ? "AND " : "WHERE ");
            sql.append("u.nom LIKE ? ");
            hasWhere = true;
        }

        if (dateDebut != null && dateFin != null) {
            sql.append(hasWhere ? "AND " : "WHERE ");
            sql.append("l.date_connexion BETWEEN ? AND ? ");
        }

        
        if ("Date (ancien)".equals(tri)) {
            sql.append(" ORDER BY l.date_connexion ASC");
        } else if ("Date (récent)".equals(tri)) {
            sql.append(" ORDER BY l.date_connexion DESC");
        }

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
                    Utilisateur user = new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("role"),
                        rs.getString("fontion")
                    );

                    LogConnexion log = new LogConnexion(
                        rs.getInt("id_connexion"),
                        rs.getString("adresse_ip"),
                        rs.getInt("tentative_connexion_echouee") == 1,
                        rs.getTimestamp("date_connexion").toLocalDateTime(),
                        user
                    );

                    logs.add(log);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public static void enregistrerConnexion(int userId, boolean success, Component component) {
        if (userId <= 0) {
            System.err.println("ID utilisateur invalide, log non enregistré.");
            return;
        }

        String ip = getIpLocale();
        System.out.println("IP utilisée pour log : " + ip);

        String sql = "INSERT INTO log_connexion (adresse_ip, tentative_connexion_echouee, date_connexion, id_user) VALUES (?, ?, NOW(), ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ip);
            stmt.setInt(2, success ? 0 : 1);
            stmt.setInt(3, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(component, "Erreur lors du log de la connexion", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private static String getIpLocale() {
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
