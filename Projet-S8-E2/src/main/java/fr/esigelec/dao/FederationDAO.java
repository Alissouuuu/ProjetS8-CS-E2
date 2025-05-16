package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import fr.esigelec.models.Federation;

public class FederationDAO {
	private DataSource dataSource;
	
	public FederationDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Federation getFederation(int codeFederation) {
		Federation federation = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT lib_federation FROM federation WHERE code_federation=?");
			stmt.setInt(1, codeFederation);
			rs = stmt.executeQuery();
			if(rs.next())
				federation = new Federation(codeFederation,rs.getString("lib_federation"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return federation;
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
