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
	Connection conn = null;
	
	public DBDAO() {}
	
	
	public boolean Connexion() {
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
	
	public Region getRegion(String codeRegion) {
		Region region = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT lib_region FROM region WHERE code_region=?");
			stmt.setString(1, codeRegion);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				region = new Region(codeRegion,rs.getString("lib_region"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(stmt !=null)
					stmt.close();
			}
			catch(SQLException ignore) {
				System.err.println("Erreur lors de la fermeture de requête");
			}
		}
		return region;
	}
	
	public Departement getDepartement(String codeDepartement) {
		Region region = null;
		Departement departement = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT lib_departement,code_region FROM departement WHERE code_departement=?");
			stmt.setString(1, codeDepartement);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				region = getRegion(rs.getString("code_region"));
				departement = new Departement(codeDepartement,rs.getString("lib_departement"),region);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(stmt !=null)
					stmt.close();
			}
			catch(SQLException ignore) {
				System.out.println("Erreur lors de la fermeture de requête");
			}
		}
		return departement;
	}
	
	public Commune getCommune(String codeCommune) {
		Commune commune = null;
		Departement departement = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT code_postal.code_postal, libelle_ville.lib_commune, commune.longitude, commune.latitude,"
					+ "commune.qpv, commune.code_departement FROM commune INNER JOIN code_postal ON commune.code_commune = code_postal.code_commune "
					+ "INNER JOIN libelle_ville_commune ON commune.code_commune = libelle_ville_commune.code_commune INNER JOIN libelle_ville ON "
					+ "libelle_ville_commune.id_libelle = libelle_ville.id_libelle WHERE commune.code_commune = ?");
			stmt.setString(1, codeCommune);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				departement = getDepartement(rs.getString("code_departement"));
				String nom = rs.getString("lib_commune");
				String codePostal = rs.getString("code_postal");
				String qpv = rs.getString("qpv");
				double longitude = rs.getDouble("longitude");
				double latitude = rs.getDouble("latitude");
				
				commune = new Commune(codeCommune,codePostal,nom,qpv,longitude,latitude,departement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(stmt !=null)
					stmt.close();
			}
			catch(SQLException ignore) {
				System.out.println("Erreur lors de la fermeture de requête");
			}
		}
		return commune;
	}
	
	public Federation getFederation(int codeFederation) {
		Federation federation = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT lib_federation FROM federation WHERE code_federation=?");
			stmt.setInt(1, codeFederation);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				federation = new Federation(codeFederation,rs.getString("lib_federation"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return federation;
	}
	
	public ArrayList<Club> getClubs(int page,String requeteMilieu){
		//SELECT id_club,lib_club,total,total_femme,total_homme,club.code_commune,club.code_federation FROM club INNER JOIN code_postal ON club.code_commune=code_postal.code_commune INNER JOIN federation ON (club.code_federation=federation.code_federation) 
		
		String requeteDebut = "SELECT id_club,lib_club,total,total_femme,total_homme,club.code_commune,club.code_federation FROM club NATURAL JOIN code_postal NATURAL JOIN federation ";
		String requeteFin = " LIMIT 25 OFFSET ?";
		if(requeteMilieu == null)
			requeteMilieu = "";
		String requete = requeteDebut+requeteMilieu+requeteFin;
		int offset = (page-1)*25;
		ArrayList<Club> clubs = new ArrayList<>();
		int id,totalLicences,totalLicencesFemme,totalLicencesHomme;
		String nom;
		Commune commune = null;
		Federation federation = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(requete);
			stmt.setInt(1, offset);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id_club");
				nom = rs.getString("lib_club");
				totalLicences = rs.getInt("total");
				totalLicencesFemme = rs.getInt("total_femme");
				totalLicencesHomme = rs.getInt("total_homme");
				commune = getCommune(rs.getString("code_commune"));
				federation = getFederation(rs.getInt("code_federation"));
				clubs.add(new Club(id,federation,commune,nom,totalLicences,totalLicencesFemme,totalLicencesHomme));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(stmt !=null)
					stmt.close();
			}
			catch(SQLException ignore) {
				System.out.println("Erreur lors de la fermeture de requête");
			}
		}
		return clubs;
	}
	
	
	
	public boolean Deconnexion() {
		try {
			if(conn!=null)
				conn.close();
			return true;
		}
		catch(SQLException ignore) {
			System.err.println("Impossible de fermer la connexion !");
			return false;
		}
	}
}
