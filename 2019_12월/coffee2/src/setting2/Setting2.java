package setting2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//import sun.font.CreatedFontTracker;


import java.sql.DriverManager;

public class Setting2 {
	public static void main(String[] args) throws SQLException {
		

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=true&serverTimezone=UTC","root","1234");
		
		Statement stmt = con.createStatement();
		
		stmt.execute("drop Database if exists coffee2");
		stmt.execute("create Database coffee2 ");
		stmt.execute("use coffee2");
		
		stmt.execute("create table menu (m_no int primart key not null auto_increment, m_group varchar(10), m_name varchar(30), ,_m_price int ) ");
		stmt.execute("create table user ( i_no int primart key not null auto_increment, u_id varchar(20), u_pw varchar(4), u_name varchar(5), u_bd varchar(14), i_point int, u_grade varchar(10)");
		stmt.execute("create table orderlist ( o_no int partmart key not null auto_increment, o_date bate, foreign key(u_no) references user(u_no ), foreign key(m_no) references menu(m_no), o_group carchar(10), o_size varcher(10), o_price int, o_count int, o_amount)");
		stmt.execute("create table shopping ( s_no int primart key not null auto_increment, foreign key(m_no) references menu(m_no) , s_price int, s_count int, s_size varchar(1), s_amount)");
		
		
		
	}

}
