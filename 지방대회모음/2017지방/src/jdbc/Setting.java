package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=True&serverTimezone=UTC", "root", "1234");
		Statement stmt =con.createStatement();
		
		stmt.execute("set global local_infile='on'");
		stmt.execute("drop database if exists sw3_101");
		stmt.execute("create database sw3_101 default character set utf8");
		stmt.execute("use sw3_101");
		
		stmt.execute("create table TBL_Customer(cID varchar(6) primary key not null,"
				+ "cPW varchar(4),"
				+ "cName varchar(10),"
				+ "cHP varchar(13))");
		
		stmt.execute("create table TBL_Bus(bNumber varchar(4) primary key not null,"
				+ "bDeparture varchar(5),"
				+ "dArrival varchar(5),"
				+ "dTime time,"
				+ "bElapse  varchar(10),"
				+ "bCount varchar(1),"
				+ "bPrice int(6))");
		
		stmt.execute("create table TBL_Ticket(bDate date,"
				+ "bNumber varchar(4),"
				+ "bNumber2 varchar(5),"
				+ "bSeat int(2),"
				+ "cID varchar(6),"
				+ "cPrice int(6),"
				+ "bState varchar(1))");
		
		stmt.execute("load data local infile './2과제/TBL_CUSTOMER.txt' into table TBL_Customer  ignore 1 lines");
		stmt.execute("load data local infile './2과제/TBL_BUS.txt' into table TBL_Bus ignore 1 lines");
		stmt.execute("load data local infile './2과제/TBL_TICKET.txt'into table TBL_Ticket ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on sw3_101.*to 'user'");
		stmt.execute("flush privileges");
		
	}
}

