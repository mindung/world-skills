package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import frame.BaseFrame;
import frame.text1;

public class Setting {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=True&serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();
		
		stmt.execute("set global local_infile ='on'");
		stmt.execute("drop database if exists meal");
		stmt.execute("create database meal default character set utf8 ");
		stmt.execute("use meal");
		
		stmt.execute("create table member(memberNo int primary key not null auto_increment , "
				+ "memberName varchar(20),"
				+ "passwd varchar(4))");
		
		stmt.execute("create table cuisine(cuisineNo int primary key not null auto_increment, "
				+ "cuisineName varchar(10))");
		
		stmt.execute("create table meal(mealNo int primary key not null auto_increment, "
				+ "cuisineNo int,"
				+ "mealName varchar(20),"
				+ "price int,"
				+ "maxCount int,"
				+ "todayMeal tinyint(1))");
		
		stmt.execute("create table orderlist(orderNo int primary key not null auto_increment, " 
				+ "cuisineNo int,"
				+ "mealNo int,"
				+ "memberNo int,"
				+ "orderlistCount int,"
				+ "amount int,"
				+ "orderDate datetime)");
		
		stmt.execute("load data local infile './DataFiles/member.txt' into table member ignore 1 lines");
		stmt.execute("load data local infile './DataFiles/cuisine.txt' into table cuisine ignore 1 lines");
		stmt.execute("load data local infile './DataFiles/meal.txt' into table meal ignore 1 lines");
		stmt.execute("load data local infile './DataFiles/orderlist.txt' into table orderlist ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete,update on meal.*to 'user'");
		stmt.execute("flush privileges");
		
		new text1().setVisible(true);
		
		System.err.println("bhj");
	}
	

}
