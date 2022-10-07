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
		stmt.execute("drop database if exists market1");
		stmt.execute("create database market1 default character set utf8");
		stmt.execute("use market1");
		
		stmt.execute("create table category(c_name varchar(10), "
				+ "c_no int primary key)");//primary key  
//		+ "foreign key(c_no) references product(c_no)
		
		 
		stmt.execute("create table product(p_no int primary key not null auto_increment,"
				+ "c_no int,"
				+ "p_name varchar(20),"
				+ "p_price int,"
				+ "p_stock int,"
				+ "p_explanation varchar(150),"
				+ "foreign key(c_no) references category(c_no))");

		stmt.execute("create table user(u_no int primary key not null auto_increment,"
				+ "u_id varchar(20),"
				+ "u_pw varchar(20),"
				+ "u_address varchar(50),"
				+ "u_name varchar(15),"
				+ "u_phone varchar(20),"
				+ "u_age varchar(20))");
		
		stmt.execute("create table purchase(pu_no int primary key not null auto_increment,"
				+ "p_no int,"
				+ "pu_price int,"
				+ "pu_count int,"
				+ "pu_total int,"
				+ "u_no int,"
				+ "foreign key(p_no) references product(p_no),"
				+ "foreign key(u_no) references user(u_no))");
		
		stmt.execute("load data local infile './지급자료/category.txt' into table category ignore 1 lines");
		stmt.execute("load data local infile './지급자료/product.txt' into table product ignore 1 lines");
		stmt.execute("load data local infile './지급자료/user.txt' into table user ignore 1 lines");
		stmt.execute("load data local infile './지급자료/purchase.txt' into table purchase ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on market1.* to 'user'");
		stmt.execute("flush privileges");

	
	}

}

