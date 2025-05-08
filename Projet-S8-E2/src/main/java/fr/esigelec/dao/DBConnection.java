package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe DBConnection.
 */

public class DBConnection {

    private static final String url = "jdbc:mysql://localhost:3306/sportdb";
    private static final String user = "root";
    private static final String password = "";
    public static Connection getConnection() {
        System.out.println("Tentative de connexion à la base de données...");
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion à la base réussie !");
            return conn;
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trouvé : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}