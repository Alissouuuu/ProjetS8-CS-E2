package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBConnexion;

import java.sql.*;
public class CommuneDAO {
	public List<String> getListCommune() throws SQLException, ClassNotFoundException {
	    List<String> villes = new ArrayList<>();
	    
	    String sql = "SELECT DISTINCT lib_commune FROM libelle_ville ORDER BY lib_commune";

	    try (Connection conn = DBConnexion.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            villes.add(rs.getString("lib_commune"));
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }

	    return villes;
	}

}
