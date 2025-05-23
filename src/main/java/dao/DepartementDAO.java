package dao;
import java.util.ArrayList;
import java.util.List;

import model.Departement;
import util.DBConnexion;

import java.sql.*;
public class DepartementDAO {
	public List< Departement> getListDepartement() throws SQLException, ClassNotFoundException {
	    List<Departement>  departements = new ArrayList<>();
	    
	    
	    String sql = "SELECT * FROM departement";
	    System.out.println(sql);

	    try (Connection conn = DBConnexion.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	Departement departement = new Departement();
	        	departement.setCodeDepartement(rs.getString("code_departement"));
	        	departement.setLibelleDepartement(rs.getString("lib_departement"));
	        	departements.add(departement);
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }

	    return departements;
	}

}
