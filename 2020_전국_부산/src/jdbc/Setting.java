package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {

	public static void main(String[] args) throws SQLException {
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=true&serverTimezone=UTC","root","1234");
	Statement stmt = con.createStatement();
	
	stmt.execute("set global local_infile ='on'");
	stmt.execute("drop database if exists HrdDB");
	stmt.execute("create database HrdDB character set utf8");
	stmt.execute("use HrdDB");
	
	stmt.execute("create table course(c_code varchar(4) primary key,"
			+ "c_title varchar(10),"
			+ "c_pCode varchar(4),"
			+ "c_dCode varchar(3))");

	stmt.execute("create table Pro(p_code varchar(4) primary key,"
			+ "p_name varchar(4),"
			+ "p_tel varchar(13),"
			+ "p_pw varchar(10),"
			+ "p_st varchar(2),"
			+ "p_gr varchar(3),"
			+ "p_dNo varchar(4),"
			+ "p_pw2 varchar(10))");
	
	stmt.execute("create table Info(i_sCode varchar(6), "
			+ "i_cCode varchar(4) ,"
			+ "i_gr varchar(1),"
			+ "i_rmk varchar(40),"
			+ "primary key(i_sCode, i_cCode))");
	
	stmt.execute("create table Department(d_code varchar(4) primary key,"
			+ "d_name varchar(10),"
			+ "d_pNo varchar(4))");
	
	stmt.execute("create table student(s_code varchar(6) primary key,"
			+ "s_pw varchar(10),"
			+ "s_name varchar(4),"
			+ "s_gen varchar(1),"
			+ "s_tel varchar(13),"
			+ "s_addr varchar(20),"
			+ "s_dCode varchar(3))");
	
	stmt.execute("load data local infile './지급자료/course.txt' into table course ignore 1 lines");
	stmt.execute("load data local infile './지급자료/pro.txt' into table pro ignore 1 lines");
	stmt.execute("load data local infile './지급자료/info.txt' into table info ignore 1 lines");
	stmt.execute("load data local infile './지급자료/department.txt' into table department ignore 1 lines");
	stmt.execute("load data local infile './지급자료/student.txt' into table student ignore 1 lines");
	
	stmt.execute("drop user if exists user");
	stmt.execute("create user 'user' identified by '1234'");
	stmt.execute("grant select, insert, update, delete on HrdDB .* to 'user'");
	stmt.execute("flush privileges");
					//  privileges
	}
}
