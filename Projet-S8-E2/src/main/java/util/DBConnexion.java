package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;

public class DBConnexion {
	private static Connection connection;

	// j'ai définis un singleton pour garantir une seule instance dans toute
	// l'application
	// aussi pour soulager le serveur / evitons sa surcharge
	private DBConnexion() {
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			try (InputStream input = DBConnexion.class.getClassLoader()
					.getResourceAsStream("database.properties")) {

				/*Properties props = new Properties();
				props.load(input);

				String driver = props.getProperty("db.driver");
				String url = props.getProperty("db.url");
				String user = props.getProperty("db.user");
				String password = props.getProperty("db.password");
				*/
				
				String url = "jdbc:mysql://localhost:3306/club_sport?useSSL=false&serverTimezone=UTC";
				String user = "root";
				String password = "";

				String driver="com.mysql.cj.jdbc.Driver";
				try {
				    Class.forName("com.mysql.cj.jdbc.Driver");
//				    System.out.println("Driver JDBC chargé avec succes.");
				} catch (ClassNotFoundException e) {
				    System.out.println(" Le driver JDBC est introuvable : " + e.getMessage());
				}
//				System.out.println("Driver lu : " + driver);
//			    System.out.println("URL : " + url);
//			 
//			    System.out.println("user : " + user);
				Class.forName(driver);
				connection = DriverManager.getConnection(url, user, password);
				System.out.println("Connexion à la BDD établie !");

			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Echec de connexion à la bdd.");
			}
			
		}
		return connection;
	}
	

}
