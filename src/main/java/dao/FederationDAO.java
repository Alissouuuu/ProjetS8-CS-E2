/**
 * @author imane
 * @version 1.1
 * DAO des federations
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Federation;
import util.DBConnexion;

public class FederationDAO {
	public List<Federation> getListFederation() throws SQLException, ClassNotFoundException {
	    List<Federation> federations = new ArrayList<>();
	    
	    
	    String sql = "SELECT * FROM federation ORDER BY lib_federation";

	    try (Connection conn = DBConnexion.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	Federation federation = new Federation();
	        	federation.setCodeFederation(rs.getString("code_federation"));
	        	federation.setLibelleFedeation(rs.getString("lib_federation"));
	        	federations.add(federation);
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }

	    return federations;
	}
}
