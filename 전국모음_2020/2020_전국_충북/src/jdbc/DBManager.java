package jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	
	static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/computer_2020?allowLoadLocalInfile=TRUE&serverTimezone=UTC", "root", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String sql, Object...objects) throws SQLException {
		PreparedStatement pst = con.prepareStatement(sql);
		
		for (int i = 0; i < objects.length; i++) {
			pst.setObject(i+1, objects[i]);
		}
		
		return pst.executeQuery();
	}
	
	public int executeUpdate(String sql, Object...objects) throws SQLException {
		PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		for (int i = 0; i < objects.length; i++) {
			pst.setObject(i+1, objects[i]);
		}
		
		pst.executeUpdate();
		
		ResultSet rs = pst.getGeneratedKeys();
		
		if (rs.next()) {
			return rs.getInt(1);
		}
		
		return 0;
	}
	
	public int executeUpdate(String sql, String path) throws SQLException, FileNotFoundException {
		PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		
		pst.setBinaryStream(1, fis, (int) file.length());
		
		pst.executeUpdate();
		
		ResultSet rs = pst.getGeneratedKeys();
		
		if (rs.next()) {
			return rs.getInt(1);
		}
		
		return 0;
	}
}
