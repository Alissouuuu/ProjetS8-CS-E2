package fr.esigelec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.esigelec.models.Commune;
import fr.esigelec.models.Departement;

public class CommuneDAO {
	
	public static Commune getCommune(String codeCommune) {
		Commune commune = null;
		Departement departement = null;
		PreparedStatement stmt = null;
		try {
			stmt = DBDAO.getConn().prepareStatement("SELECT code_postal.code_postal, libelle_ville.lib_commune, commune.longitude, commune.latitude,"
					+ "commune.qpv, commune.code_departement FROM commune INNER JOIN code_postal ON commune.code_commune = code_postal.code_commune "
					+ "INNER JOIN libelle_ville_commune ON commune.code_commune = libelle_ville_commune.code_commune INNER JOIN libelle_ville ON "
					+ "libelle_ville_commune.id_libelle = libelle_ville.id_libelle WHERE commune.code_commune = ?");
			stmt.setString(1, codeCommune);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				departement = DepartementDAO.getDepartement(rs.getString("code_departement"));
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
	
}
