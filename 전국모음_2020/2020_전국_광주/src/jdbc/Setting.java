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
		stmt.execute("drop database if exists diary1");
		stmt.execute("create database diary1 default character set utf8");
		stmt.execute("use diary1");
		
		stmt.execute("create table tbl_member(M_index int primary key not null auto_increment,"
				+ "M_id varchar(50) not null,"
				+ "M_passwd varchar(255) not null,"
				+ "M_name varchar(10) not null,"
				+ "M_gender varchar(10),"
				+ "M_regdate varchar(20),"
				+ "M_height int )");
		stmt.execute("create table tbl_diary(D_index int primary key not null auto_increment,"
				+ "M_index int,"
				+ "D_date varchar(10),"
				+ "D_weight varchar(20),"
				+ "D_ankle varchar(20),"
				+ "D_pressure varchar(20),"
				+ "D_drug_am varchar(20),"
				+ "D_drug_pm varchar(20),"
				+ "D_ex_do varchar(20),"
				+ "D_ex_time varchar(20),"
				+ "D_ex_type varchar(20),"
				+ "D_hypochlorization varchar(20),"
				+ "D_etc varchar(20))");
		stmt.execute("create table tbl_feedback(F_index int primary key not null auto_increment,"
				+ "M_index int not null,"
				+ "F_text Text,"
				+ "F_read varchar(10),"
				+ "F_regdate varchar(20) not null)");
	
		stmt.execute("load data local infile './지급자료/tbl_member.txt' into table tbl_member ignore 1 lines");
		stmt.execute("load data local infile './지급자료/tbl_diary.txt' into table tbl_diary ignore 1 lines");
		stmt.execute("load data local infile './지급자료/tbl_feedback.txt' into table tbl_feedback ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("Grant select, insert, delete , update on diary1.* to 'user'");
		stmt.execute("flush privileges");
		
		System.out.println("세팅 완료");
	}
}
