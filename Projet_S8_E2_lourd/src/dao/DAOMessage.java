package dao;

import model.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAOMessage {
    private static final String URL = "jdbc:mysql://localhost:3306/club_sport";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public boolean enregistrerMessage(Message message) {
        String sql = "INSERT INTO messages (sujet, contenu, destinataire, date_envoi) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, message.getSujet());
            stmt.setString(2, message.getContenu());
            stmt.setString(3, message.getDestinataire());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getDateEnvoi()));
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages ORDER BY date_envoi DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	messages.add(new Message(
            		    rs.getInt("id"),
            		    rs.getString("sujet"),
            		    rs.getString("contenu"),
            		    rs.getString("destinataire"),
            		    rs.getString("expediteur"),
            		    rs.getTimestamp("date_envoi").toLocalDateTime()
            		));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
    
    public boolean envoyerMessage(Message message) {
        String sql = "INSERT INTO messages (sujet, contenu, destinataire, expediteur, date_envoi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, message.getSujet());
            stmt.setString(2, message.getContenu());
            stmt.setString(3, message.getDestinataire());
            stmt.setString(4, message.getExpediteur());
            stmt.setTimestamp(5, Timestamp.valueOf(message.getDateEnvoi()));
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
