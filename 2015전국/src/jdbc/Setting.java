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
		stmt.execute("drop database if exists World101");
		stmt.execute("create database World101 default character set utf8");
		stmt.execute("use World101");
		
		stmt.execute("create table admin(id varchar(10), pw varchar(10))");
		stmt.execute("create table lib(lib_code varchar(4) primary key not null,lib_name varchar(50), lib_author varchar(50),lib_publisher varchar(50), lib_price int, lib_state varchar(1))");
		stmt.execute("create table member(mb_name varchar(20),mb_num varchar(6)primary key not null,mb_phone varchar(20),mb_addr varchar(20))");
		stmt.execute("create table rent(rent_no int auto_increment primary key, mem_name varchar(20),mem_phone varchar(20),lib_code varchar(4),lib_name varchar(20),rent_date date)");
		
		stmt.execute("load data local infile './지급자료/booklist.txt' into table lib");
		stmt.execute("load data local infile './지급자료/member.txt' into table member fields terminated by ','");
		stmt.execute("update lib set lib_code = 'A001' where lib_name = '세상에서가장멋진내친구똥퍼'");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on World101.* to 'user'");
		stmt.execute("flush privileges");
		
	}
}
