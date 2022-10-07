package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dbManager {

	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/diary1?serverTimezone=UTC", "root", "1234");
//			con = DriverManager.getConnection("jdbc:mysql://localhost/diary?serverTimezone=UTC", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql, Object... objects) throws SQLException {

		PreparedStatement ps = con.prepareStatement(sql);

		for (int i = 0; i < objects.length; i++) {
			ps.setObject(i + 1, objects[i]);
		}

		return ps.executeQuery();

	}
	
	public void executeUpdate(String sql, Object... objects) throws SQLException {

		PreparedStatement ps = con.prepareStatement(sql);

		for (int i = 0; i < objects.length; i++) {
			ps.setObject(i + 1, objects[i]);
		}

		ps.executeUpdate();

	}
}
