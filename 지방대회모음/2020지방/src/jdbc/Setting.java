package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {
	
	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=True&serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();
		
		stmt.execute("set global local_infile='on'");
		stmt.execute("drop database if exists hospital");
		stmt.execute("create database hospital default character set utf8");
		stmt.execute("use hospital");
		
		stmt.execute("create table patient(p_no int primary key not null auto_increment,"
				+ "p_name varchar(10),"
				+ "p_id varchar(15),"
				+ "p_pw varchar(10),"
				+ "p_bd date)");

		stmt.execute("create table doctor(d_no int primary key not null auto_increment,"
				+ "d_section varchar(10),"
				+ "d_name varchar(15),"
				+ "d_day varchar(1),"
				+ "d_time varchar(2))");
		
		stmt.execute("create table examination(e_no int primary key not null auto_increment,"
				+ "e_name varchar(10))");
		
		stmt.execute("create table reservation(r_no int primary key not null auto_increment,"
				+ "p_no int,"
				+ "d_no int,"
				+ "r_section varchar(10),"
				+ "r_date varchar(14),"
				+ "r_time varchar(10),"
				+ "e_no int ,"
				+ "foreign key(p_no) references patient(p_no),"
				+ "foreign key(d_no) references doctor(d_no),"
				+ "foreign key(e_no) references examination(e_no))");
		
		stmt.execute("create table sickroom(s_no int primary key not null auto_increment,"
				+ "s_people int,"
				+ "s_room int,"
				+ "s_roomno varchar(20))");
		
		stmt.execute("create table hospitalization(h_no int primary key not null auto_increment,"
				+ "p_no int,"
				+ "s_no int,"
				+ "h_bedno int,"
				+ "h_sday varchar(14),"
				+ "h_fday varchar(14),"
				+ "h_meal int,"
				+ "h_amount int,"
				+ "foreign key(p_no) references patient(p_no),"
				+ "foreign key(s_no) references sickroom(s_no))");
		
		stmt.execute("load data local infile './Datafiles/patient.txt' into table patient ignore 1 lines ");
		stmt.execute("load data local infile './Datafiles/doctor.txt' into table doctor ignore 1 lines ");
		stmt.execute("load data local infile './Datafiles/examination.txt' into table examination ignore 1 lines ");
		stmt.execute("load data local infile './Datafiles/reservation.txt' into table reservation ignore 1 lines ");
		stmt.execute("load data local infile './Datafiles/sickroom.txt' into table sickroom ignore 1 lines ");
		stmt.execute("load data local infile './Datafiles/hospitalization.txt' into table hospitalization ignore 1 lines ");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on hospital.* to 'user'");
		stmt.execute("flush privileges");
		
	}
}
