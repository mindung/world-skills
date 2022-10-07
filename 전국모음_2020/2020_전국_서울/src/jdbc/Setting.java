package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {

	public static void main(String[] args) throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=true&serverTimezone=UTC", "root", "1234");
		Statement stmt =con.createStatement();
		
		stmt.execute("set global local_infile ='on'");
		stmt.execute("drop database if exists reminder");
		stmt.execute("create database reminder default character set utf8");
		stmt.execute("use reminder");
		
		stmt.execute("create table member(m_no int primary key not null auto_increment,"
				+ "m_id varchar(20),"
				+ "m_pw varchar(20),"
				+ "m_name varchar(10),"
				+ "m_phone varchar(10))");

		stmt.execute("create table news(n_no int primary key not null auto_increment,"
				+ "n_title varchar(20),"
				+ "n_content varchar(1100),"
				+ "n_date varchar(20))");

		stmt.execute("create table notice(nt_no int primary key not null auto_increment,"
				+ "n_no int,"
				+ "foreign key(n_no) references news(n_no))");
		
		stmt.execute("create table reply(r_no int primary key not null auto_increment,"
				+ "r_title varchar(20),"
				+ "r_content varchar(1100),"
				+ "r_sday varchar(20),"
				+ "r_eday varchar(20),"
				+ "r_terms varchar(400))");
		
		stmt.execute("create table reply_result(re_no int primary key not null auto_increment,"
				+ "m_no int,"
				+ "r_no int,"
				+ "re_whether int,"
				+ "foreign key(m_no) references member(m_no),"
				+ "foreign key(r_no) references reply(r_no))");
		
		stmt.execute("create table community(c_no int primary key not null auto_increment,"
				+ "c_title varchar(20),"
				+ "c_content varchar(200),"
				+ "m_no int,"
				+ "anonymous int,"
				+ "classification int,"
				+ "foreign key(m_no) references member(m_no))");
		
		stmt.execute("create table comment(co_no int primary key not null auto_increment,"
				+ "co_content varchar(200),"
				+ "c_no int,"
				+ "m_no int,"
				+ "foreign key(c_no) references community(c_no),"
				+ "foreign key(m_no) references member(m_no))");
		
		stmt.execute("load data local infile './2과제 지급자료/member.txt' into table member ignore 1 lines");
		stmt.execute("load data local infile './2과제 지급자료/news.txt' into table news ignore 1 lines");
		stmt.execute("load data local infile './2과제 지급자료/notice.txt' into table notice ignore 1 lines");
		stmt.execute("load data local infile './2과제 지급자료/reply.txt' into table reply ignore 1 lines");
		stmt.execute("load data local infile './2과제 지급자료/reply_result.txt' into table reply_result ignore 1 lines");
		stmt.execute("load data local infile './2과제 지급자료/community.txt' into table community ignore 1 lines");
		stmt.execute("load data local infile './2과제 지급자료/comment.txt' into table comment ignore 1 lines");
		
		stmt.execute("drop user if exists user");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, delete, update on reminder.* to 'user'");
		stmt.execute("flush privileges");
						 //privileges
		
	}
}
