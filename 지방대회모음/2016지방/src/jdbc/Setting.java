package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {
	
	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=True&serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();
		
		stmt.execute("set global local_infile = 'on'");
		stmt.execute("drop database if exists Company_101");
		stmt.execute("create database Company_101 default character set utf8");
		stmt.execute("use Company_101");
		
		stmt.execute("create table admin(name varchar(20) not null,"
				+ "passwd varchar(20) primary key not null ,"
				+ "position varchar(20),"
				+ "jumin char(14),"
				+ "inputDate date)");
		
		stmt.execute("create table customer(code char(7) not null ,"
				+ "name varchar(20) primary key not null,"
				+ "birth date,"
				+ "tel varchar(20),"
				+ "address varchar(100),"
				+ "company varchar(20))");
		
		stmt.execute("create table contract(customerCode char(20) not null,"
				+ "contractName varchar(20) not null,"
				+ "regPrice int ,"
				+ "regDate date,"
				+ "monthPrice int,"
				+ "adminName varchar(20) not null)");

		stmt.execute("load data local infile './力2苞力/admin.txt' into table admin ignore 1 lines");
		stmt.execute("load data local infile './力2苞力/customer.txt' into table customer ignore 1 lines");
		stmt.execute("load data local infile './力2苞力/contract.txt' into table contract ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on Company_101.* to 'user'");
		stmt.execute("flush privileges");
	
	}
}
