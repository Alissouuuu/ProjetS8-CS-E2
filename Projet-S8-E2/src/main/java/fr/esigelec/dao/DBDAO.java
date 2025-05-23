package fr.esigelec.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import fr.esigelec.models.*;

public class DBDAO {
	
	final static String nomTableCommune = "commune";
	final static String nomTableDepartement = "departement";
	final static String nomTableRegion = "region";
	final static String nomTableFederation = "federation";
	private static Connection conn = null;
	
	public DBDAO() {}
	
	
	public static boolean connexion() {
		Properties pr = new Properties();
		try {
			pr.load(DBDAO.class.getClassLoader().getResourceAsStream("db.properties"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		String host = pr.getProperty("hote");
        String nomBase = pr.getProperty("nomBase");
        String login = pr.getProperty("login");
        String motDePasse = pr.getProperty("motDePasse");
                
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e2) {
        	System.err.println("Pilote MySQL non trouvé : con.mysql.cj.jdbc.Driver");
        	System.exit(-1);
        }
        
        try {
        	conn = DriverManager.getConnection("jdbc:mysql://"+host+"/"+nomBase+"?characterEncoding=UTF-8",login,motDePasse);
        	return true;
        }
        catch(SQLException e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	
	


	public static Connection getConn() {
		return conn;
	}
	
	
}
