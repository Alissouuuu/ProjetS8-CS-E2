package dao;

import java.sql.*;
import java.util.*;

import util.*;

public class DepartementDAO {

	public List<String> getListDepartement() throws SQLException, ClassNotFoundException {
	    List<String> departements = new ArrayList<>();
	    //System.out.println(">> Entrée dans getListDepartement()");
	    
	    String sql = "SELECT DISTINCT lib_departement FROM departement ORDER BY lib_departement";

	    try (Connection conn = DBConnexion.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	departements.add(rs.getString("lib_departement"));
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
//	    for (String departement : departements) {
//	    	System.out.println(departement);
//	    }
	    return departements;
	}
}
