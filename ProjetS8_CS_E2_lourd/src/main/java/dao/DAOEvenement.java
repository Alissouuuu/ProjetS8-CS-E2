package dao;

import model.Evenement;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DAOEvenement {

    private static final String URL = "jdbc:mysql://localhost:3306/club_sport";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn;

    public DAOEvenement() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Evenement> getAllEvenementsAvecNoms() {
        List<Evenement> evenements = new ArrayList<>();
        String sql = """
        	    SELECT e.id_evenement, e.type, e.nbr_max, e.date, e.heure, e.lieu, e.description,
				u.nom AS nom_user, u.prenom AS prenom_user, u.email,
				c.lib_club AS nom_club
				FROM evenement e
				JOIN user u ON e.id_user = u.id_user
				LEFT JOIN club c ON e.id_club = c.id_club
				ORDER BY e.date, e.heure

        	""";



        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

        	while (rs.next()) {
        	    Evenement evt = new Evenement(
        	        rs.getInt("id_evenement"),
        	        rs.getString("type"),
        	        rs.getDate("date").toLocalDate(),
        	        rs.getTime("heure").toLocalTime(),
        	        rs.getString("lieu"),
        	        rs.getInt("nbr_max"),
        	        rs.getString("description"),
        	        rs.getString("prenom_user") + " " + rs.getString("nom_user"),
        	        rs.getString("nom_club"),
        	        rs.getString("email")
        	    );
        	    evenements.add(evt);
        	}


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evenements;
    }
    
    
    
    public List<Evenement> findByDateAndHeure(LocalDate date, LocalTime heureMin) {
        List<Evenement> evenements = new ArrayList<>();
        String sql = """
        	    SELECT e.type, e.nbr_max, e.date, e.heure, e.lieu,
        	           u.nom AS nom_user, u.prenom AS prenom_user, u.email,
        	           c.lib_club AS nom_club
        	    FROM evenement e
        	    JOIN user u ON e.id_user = u.id_user
        	    LEFT JOIN club c ON e.id_club = c.id_club
        	    WHERE e.date = ? AND e.heure >= ?
        	    ORDER BY e.date, e.heure
        	""";


        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setTime(2, java.sql.Time.valueOf(heureMin));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	Evenement evt = new Evenement(
            		    rs.getString("type"),
            		    rs.getDate("date").toLocalDate(),
            		    rs.getTime("heure").toLocalTime(),
            		    rs.getString("lieu"),
            		    rs.getInt("nbr_max"),
            		    rs.getString("prenom_user") + " " + rs.getString("nom_user"),
            		    rs.getString("nom_club"),
            		    rs.getString("email")
            		);

                evenements.add(evt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evenements;
    }

    
    

    public boolean ajouterEvenement(Evenement evt) {
        String sql = """
            INSERT INTO evenement (type, nbr_max, date, heure, lieu, description, id_user, id_club)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evt.getType());
            stmt.setInt(2, evt.getNbrMax());
            stmt.setDate(3, Date.valueOf(evt.getDate()));
            stmt.setTime(4, Time.valueOf(evt.getHeure()));
            stmt.setString(5, evt.getLieu());
            stmt.setString(6, evt.getDescription());
            stmt.setInt(7, evt.getIdUser());

            if (evt.getIdClub() != null) {
                stmt.setInt(8, evt.getIdClub());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
