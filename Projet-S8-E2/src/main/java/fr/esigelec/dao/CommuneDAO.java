package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import fr.esigelec.models.Commune;
import fr.esigelec.models.Departement;

public class CommuneDAO {
	private DataSource dataSource;
	
	public CommuneDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Commune getCommune(String codeCommune) {
		Commune commune = null;
		Departement departement = null;
		DepartementDAO departementDAO = new DepartementDAO(dataSource);
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT code_postal.code_postal, libelle_ville.lib_commune, commune.longitude, commune.latitude,"
					+ "commune.qpv, commune.code_departement FROM commune INNER JOIN code_postal ON commune.code_commune = code_postal.code_commune "
					+ "INNER JOIN libelle_ville_commune ON commune.code_commune = libelle_ville_commune.code_commune INNER JOIN libelle_ville ON "
					+ "libelle_ville_commune.id_libelle = libelle_ville.id_libelle WHERE commune.code_commune = ?");
			stmt.setString(1, codeCommune);
			rs = stmt.executeQuery();
			if(rs.next())
				departement = departementDAO.getDepartement(rs.getString("code_departement"));
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
			close(conn,stmt,rs);
		}
		return commune;
	}
	
	public ArrayList<Commune> getCommunes() {
		ArrayList<Commune> communes = new ArrayList<>();
		Departement departement = null;
		DepartementDAO departementDAO = new DepartementDAO(dataSource);
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT commune.code_commune,code_postal.code_postal, libelle_ville.lib_commune, commune.longitude, commune.latitude,"
					+ "commune.qpv, commune.code_departement FROM commune INNER JOIN code_postal ON commune.code_commune = code_postal.code_commune "
					+ "INNER JOIN libelle_ville_commune ON commune.code_commune = libelle_ville_commune.code_commune INNER JOIN libelle_ville ON "
					+ "libelle_ville_commune.id_libelle = libelle_ville.id_libelle");
			rs = stmt.executeQuery();
			while(rs.next()) {
				departement = departementDAO.getDepartement(rs.getString("code_departement"));
				String nom = rs.getString("lib_commune");
				String codePostal = rs.getString("code_postal");
				String qpv = rs.getString("qpv");
				double longitude = rs.getDouble("longitude");
				double latitude = rs.getDouble("latitude");
				
				communes.add(new Commune(rs.getString("code_commune"),codePostal,nom,qpv,longitude,latitude,departement));
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return communes;
	}
	
	private void close(Connection con,Statement stmt, ResultSet rs) {
		try {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(con != null)
				con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
