package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {

	public static void main(String[] args) throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=true&serverTimezone=UTC","root","1234");
		Statement stmt = con.createStatement();
		
		stmt.execute("SET GLOBAL local_infile = 'on'");
		stmt.execute("drop database if exists Coffee2019");
		stmt.execute("create database Coffee2019 default character set utf8");
//		stmt.execute("create database hospital default character set utf8");
		stmt.execute("use Coffee2019");
		
		stmt.execute("create table menu(m_no int primary key not null auto_increment,"
				+ "m_group varchar(10),"
				+ "m_name varchar(30),"
				+ "m_price int)");
		
		stmt.execute("create table user(u_no int primary key not null auto_increment,"
				+ "u_id varchar(20),"
				+ "u_pw varchar(4),"
				+ "u_name varchar(5),"
				+ "u_bd varchar(14),"
				+ "u_point int,"
				+ "u_grade varchar(10))");
		stmt.execute("create table orderlist(o_no int primary key not null auto_increment,"
				+ "d_date date,"
				+ "u_no int,"
				+ "m_no int,"
				+ "o_group varchar(10),"
				+ "o_size varchar(1),"
				+ "o_price int,"
				+ "o_count int,"
				+ "o_amount int,"
				+ "foreign key(u_no) references user(u_no),"
				+ "foreign key(m_no) references menu(m_no))");
		
		stmt.execute("create table shopping(s_no int primary key not null auto_increment,"
				+ "m_no int,"
				+ "s_price int,"
				+ "s_count int,"
				+ "s_size varchar(1),"
				+ "s_amount int,"
				+ "foreign key(m_no) references menu(m_no))");
		
		stmt.execute("load data local infile './DataFiles/menu.txt' into table menu ignore 1 lines");
		stmt.execute("load data local infile './DataFiles/user.txt' into table user ignore 1 lines");
		stmt.execute("load data local infile './DataFiles/orderlist.txt' into table orderlist ignore 1 lines");
		
		stmt.execute("drop user if exists user ");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on Coffee2019.* to 'user'");
		stmt.execute("flush privileges");
		
	}

}

