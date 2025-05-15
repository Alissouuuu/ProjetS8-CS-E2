package fr.esigelec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.esigelec.models.Federation;

public class FederationDAO {
	public static Federation getFederation(int codeFederation) {
		Federation federation = null;
		PreparedStatement stmt = null;
		try {
			stmt = DBDAO.getConn().prepareStatement("SELECT lib_federation FROM federation WHERE code_federation=?");
			stmt.setInt(1, codeFederation);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				federation = new Federation(codeFederation,rs.getString("lib_federation"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return federation;
	}
}
