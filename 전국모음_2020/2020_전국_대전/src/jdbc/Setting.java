package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {

	public static void main(String[] args) throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=true&serverTimezone=UTC", "root", "1234");
		Statement stmt =con .createStatement();
		
		stmt.execute("set global local_infile = 'on'");
		stmt.execute("drop database if exists wedding");
		stmt.execute("create database wedding default character set utf8");
		stmt.execute("use wedding");
		
		stmt.execute("create table weddinghall(wNo int primary key not null auto_increment,"
				+ "wname varchar(20),"
				+ "wadd varchar(50),"
				+ "wpeople int,"
				+ "wprice int)");

		stmt.execute("create table weddingtype(tyNo int primary key not null auto_increment,"
				+ "tyname varchar(15))");
		
		stmt.execute("create table mealtype(mNo int primary key not null auto_increment,"
				+ "mname varchar(5),"
				+ "mprice int)");

		stmt.execute("create table w_ty(wNo int,"
				+ "tyNo int)");
		
		stmt.execute("create table w_m(wNo int,"
				+ "mNo int)");

		stmt.execute("create table reservation(rNo int primary key not null,"
				+ "wNo int,"
				+ "rpeople int,"
				+ "tyNo int,"
				+ "mNo int,"
				+ "album int,"
				+ "letter int,"
				+ "dress int,"
				+ "rdate Date,"
				+ "pay int,"
				+ "foreign key(wNo) references weddinghall(wNo),"
				+ "foreign key(tyNo) references weddingtype(tyNo),"
				+ "foreign key(mNo) references mealtype(mNo))");

		
		stmt.execute("load data local infile './datafile/weddinghall.txt' into table weddinghall columns terminated by '\t' lines terminated by '\r\n' ignore 1 lines");
		stmt.execute("load data local infile './datafile/weddingtype.txt' into table weddingtype lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile'./datafile/mealtype.txt' into table mealtype lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile './datafile/w_ty.txt' into table w_ty lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile './datafile/w_m.txt' into table w_m lines terminated by '\r\n'ignore 1 lines");
		stmt.execute("load data local infile './datafile/reservation.txt' into table reservation lines terminated by '\r\n'ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on wedding.* to 'user'");
		stmt.execute("flush privileges");
		
		
	}
}
