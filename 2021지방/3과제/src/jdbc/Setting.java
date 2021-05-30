package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import frame.SettingFrame;

public class Setting {

	public static void main(String[] args) throws SQLException {
		
		new SettingFrame().setVisible(true);
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=true&serverTimezone=UTC", "root", "1234");
		Statement stmt =con.createStatement();
		
		stmt.execute("set global local_infile = 'on'");
		stmt.execute("drop database if exists 2021瘤规_2");
		stmt.execute("create database 2021瘤规_2 default character set utf8");
		stmt.execute("use 2021瘤规_2");
		
		stmt.execute("create table user(u_no int primary key not null auto_increment,"
				+ "u_id varchar(20),"
				+ "u_pw varchar(20),"
				+ "u_name varchar(20),"
				+ "u_date date,"
				+ "u_phone varchar(50))");
		
		stmt.execute("create table weddinghall(wh_No int primary key not null auto_increment,"
				+ "wh_name varchar(20),"
				+ "wh_add varchar(50),"
				+ "wh_people int,"
				+ "wh_price int)");
		
		stmt.execute("create table weddingtype(wty_No int primary key not null auto_increment,"
				+ "wty_name varchar(15))");
		
		stmt.execute("create table mealtype(m_No int primary key not null auto_increment,"
				+ "m_name varchar(5),"
				+ "m_price int)");
		
		stmt.execute("create table payment(p_no varchar(4) primary key not null,"
				+ "wh_no int,"
				+ "p_people int,"
				+ "wty_no int ,"
				+ "m_no int,"
				+ "i_no int,"
				+ "p_date date,"
				+ "u_no int,"
				+ "foreign key(wh_no) references weddinghall(wh_no),"
				+ "foreign key(wty_no) references weddingtype(wty_no),"
				+ "foreign key(m_no) references mealtype(m_no),"
				+ "foreign key(u_no) references user(u_no))");
		
		stmt.execute("create table invitation(i_no int primary key not null auto_increment,"
				+ "p_no varchar(4),"
				+ "i_from int,"
				+ "i_to int"
				+ ")");
		
		stmt.execute("create table division(wh_No int,"
				+ "wty_no int,"
				+ "m_no int,"
				+ "foreign key(wh_no) references weddinghall(wh_no),"
				+ "foreign key(wty_no) references weddingtype(wty_no),"
				+ "foreign key(m_no) references mealtype(m_no))");

		stmt.execute("load data local infile './datafile/user.txt' into table user columns terminated by '\t' lines terminated by '\r\n' ignore 1 lines");
		stmt.execute("load data local infile './datafile/weddinghall.txt' into table weddinghall lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile'./datafile/weddingtype.txt' into table weddingtype lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile './datafile/mealtype.txt' into table mealtype lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile './datafile/payment.txt' into table payment lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile './datafile/invitation.txt' into table invitation lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile './datafile/division.txt' into table division lines terminated by '\r\n'ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on 2021瘤规_2.* to 'user'");
		stmt.execute("flush privileges");
	}
}
