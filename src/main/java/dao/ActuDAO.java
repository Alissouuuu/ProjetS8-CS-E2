package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import model.ActualiteClub;
import util.DBConnexion;

public class ActuDAO {

    public boolean ajouterActualite(ActualiteClub actualite) {
        String sql = "INSERT INTO actualite_club (titre_actu, titre_conseil, description_actu,description_conseil, titre_competition, date_competition, score_competition,lieu_competition,id_user,id_club) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, actualite.getTitreActu());
            stmt.setString(2, actualite.getDescriptionActu());
            stmt.setString(3, null);
            stmt.setString(4, null);
            stmt.setString(5, null);
            stmt.setString(6, null);
            stmt.setInt(7, 0);
            if(actualite.getDateCompetition()!=null) {
                stmt.setDate(8, java.sql.Date.valueOf(actualite.getDateCompetition()));

            }else
                stmt.setString(8, "null");


            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
