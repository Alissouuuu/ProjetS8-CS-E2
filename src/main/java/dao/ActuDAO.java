package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ActualiteClub;
import util.DBConnexion;

public class ActuDAO {

	public boolean ajouterActualite(ActualiteClub actualite) {
		String sql = "INSERT INTO actualite_club (titre_actu, description_actu,id_user,id_club) VALUES (?, ?, ?, ?)";

		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, actualite.getTitreActu());
			stmt.setString(2, actualite.getDescriptionActu());
			stmt.setInt(3, actualite.getIdUser());
			stmt.setInt(4, 1);
			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<ActualiteClub> listerActualites() throws SQLException {
		List<ActualiteClub> liste = new ArrayList<>();
		String sql = "SELECT * FROM actualite_club";

		try (Connection conn = DBConnexion.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				ActualiteClub a = new ActualiteClub();
				a.setIdActu(rs.getInt("id_actualite"));
				a.setTitreActu(rs.getString("titre_actu"));
				a.setDescriptionActu(rs.getString("description_actu"));

				liste.add(a);
			}
		}
		return liste;
	}
	public void supprimerActu(int id) throws SQLException {
		String sql = "DELETE FROM actualite_club WHERE id_actualite = ?";
		try (Connection conn = DBConnexion.getConnection();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
			System.out.println("Nombre de lignes supprimées : " + rowsAffected);
			
		}
	}

}
