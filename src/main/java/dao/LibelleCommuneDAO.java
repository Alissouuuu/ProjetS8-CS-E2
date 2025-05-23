package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.LibelleCommune;
import util.DBConnexion;

public class LibelleCommuneDAO {
	public List<LibelleCommune> getLibellesByCommuneId(int communeId) {
        List<LibelleCommune> libelles = new ArrayList<>();

        String sql = """
            SELECT l.id, l.libelle
            FROM libelle_ville l
            JOIN libelle_ville_commune clc ON l.id = clc.libelle_id
            WHERE clc.commune_id = ?
        """;

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, communeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LibelleCommune libelle = new LibelleCommune();
                
                libelle.setIdLibelle(rs.getInt("id"));
                libelle.setLibelleCommune(rs.getString("libelle"));
                libelles.add(libelle);
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return libelles;
    }
}
