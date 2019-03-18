package com.model.common;

import com.db_connection.ConnectionConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GlobalDeleteVersion {
	public static boolean deleteVersion(String versionTable, int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			String delete_query = "DELETE FROM " + versionTable + " WHERE id = ?;";
			connection = ConnectionConfiguration.getConnection();
			ps = connection.prepareStatement(delete_query);
			ps.setInt(1, id);
			return ps.execute();
		} catch (SQLException ex) {
			System.out.println("Error on Deleting " + versionTable);
		} finally {
			connection.close();
		}
		return false;
	}
}