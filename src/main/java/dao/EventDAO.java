package dao;

import model.Evenement;
import util.DBConnexion;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

public class EventDAO {
	
    
	
	// créer un nouvel événement
	public boolean createEvent(Evenement event) throws SQLException {
	    String sql = "INSERT INTO evenement (nbr_max, date, heure, lieu, description, nom, id_user, id_club) "
	               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnexion.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, event.getNbrMaxParticipants());
	        pstmt.setDate(2, Date.valueOf(event.getDateEvenement()));
	        pstmt.setTime(3, Time.valueOf(event.getHeureEvenement()));
	        pstmt.setString(4, event.getLieuEvenement());
	        pstmt.setString(5, event.getDescriptionEvenement());
	        pstmt.setString(6, event.getNomEvenement());
	        pstmt.setInt(7, 1); 
	        pstmt.setInt(8, 1); // Remplacez 1 par l'ID du club associé

	        return pstmt.executeUpdate() > 0;
	    }
	}
	public boolean updateEvent(Evenement event, int eventId) throws SQLException {
	    String sql = "UPDATE evenement SET nbr_max=?, date=?, heure=?, lieu=?, description=?, nom=? WHERE id=?";
	    try (Connection conn = DBConnexion.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, event.getNbrMaxParticipants());
	        pstmt.setDate(2, Date.valueOf(event.getDateEvenement()));
	        pstmt.setTime(3, Time.valueOf(event.getHeureEvenement()));
	        pstmt.setString(4, event.getLieuEvenement());
	        pstmt.setString(5, event.getDescriptionEvenement());
	        pstmt.setString(6, event.getNomEvenement());
	        pstmt.setInt(7, eventId);
	        return pstmt.executeUpdate() > 0;
	    }
	}

	public boolean deleteEvent(int id) throws SQLException {
	    String sql = "DELETE FROM evenement WHERE id=?";
	    try (Connection conn = DBConnexion.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, id);
	        return pstmt.executeUpdate() > 0;
	    }
	}

	public List<Evenement> getAllEvents() throws SQLException {
	    List<Evenement> events = new ArrayList<>();
	    String sql = "SELECT * FROM evenement";
	    try (Connection conn = DBConnexion.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        while (rs.next()) {
	            Evenement e = new Evenement();
	            e.setNomEvenement(rs.getString("nom"));
	            e.setDescriptionEvenement(rs.getString("description"));
	            e.setDateEvenement(rs.getDate("date").toLocalDate());
	            e.setHeureEvenement(rs.getTime("heure").toLocalTime());
	            e.setLieuEvenement(rs.getString("lieu"));
	            e.setNbrMaxParticipants(rs.getInt("nbr_max"));
	            events.add(e);
	        }
	    }
	    return events;
	}



}
