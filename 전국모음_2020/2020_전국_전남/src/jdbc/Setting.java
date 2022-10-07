package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {
	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=true&serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();
		
		stmt.execute("SET GLOBAL local_infile = 'on'");
		stmt.execute("drop database if exists Projectlibre");
		stmt.execute("create database Projectlibre default character set UTF8 "); //default character set utf8
		stmt.execute("use Projectlibre");
		
		stmt.execute("create table project(p_num int primary key not null auto_increment,"
				+ "p_title varchar(20),"
				+ "p_name varchar(20),"
				+ "p_sdate date,"
				+ "p_edate date,"
				+ "p_note varchar(50))");
		
		stmt.execute("create table member(no int primary key not null auto_increment,"
				+ "p_num int,"
				+ "id varchar(20),"
				+ "pw varchar(20),"
				+ "name varchar(20),"
				+ "note varchar(50),"
				+ "foreign key(p_num) references project(p_num))");
		
		stmt.execute("create table job(j_no int primary key not null auto_increment,"
				+ "p_num int,"
				+ "no int,"
				+ "j_period int,"
				+ "j_percent int,"
				+ "foreign key(p_num) references project(p_num),"
				+ "foreign key(no) references member(no))");
		
		stmt.execute("create table detail(d_no int primary key not null auto_increment,"
				+ "j_no int,"
				+ "d_sdate date,"
				+ "d_edate date,"
				+ "d_note varchar(20),"
				+ "foreign key(j_no) references job(j_no))");
		
		stmt.execute("create table time_sc(t_no int primary key not null auto_increment,"
				+ "d_no int,"
				+ "t_sc varchar(500),"
				+ "foreign key(d_no) references detail(d_no))");
		
		stmt.execute("load data local infile './지급자료/project.txt' into table project ignore 1 lines");
		stmt.execute("load data local infile './지급자료/member.txt' into table member ignore 1 lines");
		stmt.execute("load data local infile './지급자료/job.txt' into table job ignore 1 lines");
		stmt.execute("load data local infile './지급자료/detail.txt' into table detail ignore 1 lines");
		stmt.execute("load data local infile './지급자료/time_sc.txt' into table time_sc ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on Projectlibre.* to 'user'");
		stmt.execute("flush privileges");
		
	}
}
