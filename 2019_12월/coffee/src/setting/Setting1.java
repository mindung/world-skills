package setting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting1 {
	public static void main(String[] args) throws SQLException {
		
		//		mysql 연결 시켜주기 위함 
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost?allowLoadLocalInfile=true&serverTimezone=UTC","root","1234"); 
//		 대문자로 써줘야함LoadLacalInfile,Time
		
		Statement stmt = con.createStatement();
//		statement stmt execute = sql 구문 실행
		
//		load date in file 구문을 쓰기 위한 
		stmt.execute("set GLOBAL local_infile = 'on' ");
		//		모든 문자열 utf-8
		stmt.execute("drop database if exists coffee");
		//		coffee 데이터 베이스 만들기
		stmt.execute("create database coffee default character set utf8");
		//		coffee 사용
		stmt.execute("use coffee");
		//		테이블 만들기
		stmt.execute("create table menu (m_no int primary key not null auto_increment, m_group varchar(10), m_name varchar(30), m_price int ) ");
		stmt.execute("create table user ( u_no int primary key not null auto_increment, u_id varchar(20),u_pw varchar(4),u_name varchar(5),u_bd varchar(4),u_point int, u_grade varchar(10))");
		stmt.execute("create table orderlist ( o_no int primary key not null auto_increment, o_date date, u_no int , m_no int , o_group varchar(10),o_size varchar(1),o_price int , o_count int,o_amount int,foreign key(u_no) references user(u_no),foreign key(m_no) references menu(m_no))");
		stmt.execute("create table shopping ( s_no int primary key not null auto_increment, m_no int, s_price int,s_count int,s_size varchar(1),s_amount int, foreign key(m_no) references menu(m_no))");

	//	테이블 파일 불러오기 (지급자료 붙혀넣기 후 파일 불러오기 위함	)
		
		stmt.execute("load data local infile './Datafiles/menu.txt' into table menu ignore 1 lines"  );
		stmt.execute("load data local infile './Datafiles/orderlist.txt' into table orderlist ignore 1 lines"  );
		stmt.execute("load data local infile './Datafiles/user.txt' into table user ignore 1 lines" );
//		./은 현재 ? ignore 은 첫줄 무시(1)
		
//		권한 설정
		stmt.execute("drop user if exists 'user'");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select,insert,delete, update on coffee.* to 'user'");

		//		시스템에 적용
		stmt.execute("Flush privileges");
	}

}
