/**
 * @author imane
 * @version 1.1
 * DAO des events
 */
package dao;

import model.Evenement;
import util.DBConnexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

	// créer un nouvel événement
	public boolean createEvent(Evenement event) throws SQLException {
		String sql = "INSERT INTO evenement (nbr_max, date, heure, lieu, description, nom, id_user, id_club) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

	public void updateEvenement(Evenement e) throws SQLException {
	    String sql = "UPDATE evenement SET nom = ?, date = ?, heure = ?, lieu= ?, nbr_max = ?, description = ? WHERE id_evenement = ?";
	    try (Connection conn = DBConnexion.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, e.getNomEvenement());
	        stmt.setDate(2, Date.valueOf(e.getDateEvenement()));
	        stmt.setTime(3, Time.valueOf(e.getHeureEvenement()));
	        stmt.setString(4, e.getLieuEvenement());
	        stmt.setInt(5, e.getNbrMaxParticipants());
	        stmt.setString(6, e.getDescriptionEvenement());
	        stmt.setInt(7, e.getIdEvenement());

	        stmt.executeUpdate();
	    }
	}


	public void supprimerEvenement(int id) throws SQLException {
		String sql = "DELETE FROM evenement WHERE id_evenement = ?";
		try (Connection conn = DBConnexion.getConnection();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
			System.out.println("Nombre de lignes supprimées : " + rowsAffected);

			stmt.executeUpdate();
			
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
	public Evenement getEvenementById(int id) throws SQLException {
	    String sql = "SELECT * FROM evenement WHERE id_evenement = ?";
	    try (Connection conn = DBConnexion.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                Evenement e = new Evenement();
	                e.setIdEvenement(id);
	                e.setNomEvenement(rs.getString("nom"));
	                e.setDescriptionEvenement(rs.getString("description"));
	                e.setDateEvenement(rs.getDate("date").toLocalDate());
	                e.setHeureEvenement(rs.getTime("heure").toLocalTime());
	                e.setLieuEvenement(rs.getString("lieu"));
	                e.setNbrMaxParticipants(rs.getInt("nbr_max"));
	                
	                
	                return e;
	            }
	        }
	    }
	    return null; 
	}


	// Événements passés
	public List<Evenement> getEvenementsPasses() throws SQLException {
		List<Evenement> evenements = new ArrayList<>();
		String sql = "SELECT * FROM evenement WHERE date < ?";

		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setDate(1, Date.valueOf(LocalDate.now()));

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					evenements.add(mapResultSetToEvenement(rs));
				}
			}
		}
		return evenements;
	}

	public List<Evenement> getEvenementsAVenir() throws SQLException {
		List<Evenement> evenements = new ArrayList<>();
		String sql = "SELECT * FROM evenement WHERE date >= ? ORDER BY date ASC";

		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setDate(1, Date.valueOf(LocalDate.now()));

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					evenements.add(mapResultSetToEvenement(rs));
				}
			}
		}
		return evenements;
	}

	private Evenement mapResultSetToEvenement(ResultSet rs) throws SQLException {
		Evenement e = new Evenement();
		e.setNomEvenement(rs.getString("nom"));
		e.setIdEvenement(rs.getInt("id_evenement"));
		e.setDateEvenement(rs.getDate("date").toLocalDate());
		e.setHeureEvenement(rs.getTime("heure").toLocalTime());
		e.setLieuEvenement(rs.getString("lieu"));
		e.setDescriptionEvenement(rs.getString("description"));
		e.setNbrMaxParticipants(rs.getInt("nbr_Max"));
		e.setIdUser(rs.getInt("id_user"));
		e.setIdClub(rs.getInt("id_club"));
		return e;
	}

}
