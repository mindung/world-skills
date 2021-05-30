package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBmanager {
 

	private static Connection con; 
	public static Statement stmt;
	//public static 으로 써줘야 LoginFrame 에서 사용 가능
	static {
	
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost/coffee?serverTimezone=UTC","root","1234");
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executeQeuer(String sql) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(sql);
	}
}