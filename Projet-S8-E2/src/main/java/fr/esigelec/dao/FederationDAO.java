package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	
	public ArrayList<Federation> getFederations(){
		ArrayList<Federation> federations = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT code_federation,lib_federation FROM federation");
			rs = stmt.executeQuery();
			while(rs.next())
				federations.add(new Federation(rs.getInt("code_federation"),rs.getString("lib_federation")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return federations;
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
