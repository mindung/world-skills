package jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Setting {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/?allowLoadLocalInfile=TRUE&serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();
		
		stmt.execute("set global local_infile = ON;");
		stmt.execute("drop database if exists computer_2020");
		stmt.execute("create database computer_2020");
		stmt.execute("use computer_2020");
		
		stmt.execute("create table category(serial int(11) auto_increment primary key not null, name varchar(10))");
		stmt.execute("create table image(serial int(11) auto_increment primary key not null, description varchar(40), image longblob)");
		stmt.execute("create table user(Serial int(11) auto_increment primary key not null, id varchar(20), pw varchar(20), name varchar(20), phone varchar(15), address varchar(50), email varchar(50), favorite int(11), foreign key(favorite) references category(serial))");
		stmt.execute("create table seller(serial int(11) auto_increment primary key not null, id varchar(20), pw varchar(30), name varchar(20), address varchar(50))");
		stmt.execute("create table product(serial int(11) auto_increment primary key not null, name varchar(80), description varchar(500), date date, category int(11), thumb int(11), info int(11), foreign key(category) references category(serial), foreign key(thumb) references image(serial), foreign key(info) references image(serial))");
		stmt.execute("create table orderlist(serial int(11) auto_increment primary key not null, user int(11), seller int(11), product int(11), price int(11), quantity int(11), shipping boolean, foreign key(user) references user(serial), foreign key(seller) references seller(serial), foreign key(product) references product(serial))");
		stmt.execute("create table ad(serial int(11) auto_increment primary key not null, name varchar(50), seller int(11), image int(11), foreign key(seller) references seller(serial), foreign key(image) references image(serial))");
		stmt.execute("create table review(serial int(11) auto_increment primary key not null, user int(11), product int(1), seller int(11), rating int(11), foreign key(user) references user(serial), foreign key(product) references product(serial), foreign key(seller) references seller(serial))");
		stmt.execute("create table stock(serial int(11) auto_increment primary key not null, seller int(11), product int(11), count int(11), price int(11),foreign key(product) references product(serial), foreign key(seller) references seller(serial))");
		
		stmt.execute("load data local infile './지급자료/category.txt' into table category ignore 1 lines");
		stmt.execute("load data local infile './지급자료/image.txt' into table image ignore 1 lines");
		stmt.execute("load data local infile './지급자료/user.txt' into table user ignore 1 lines");
		stmt.execute("load data local infile './지급자료/seller.txt' into table seller ignore 1 lines");
		stmt.execute("load data local infile './지급자료/product.txt' into table product ignore 1 lines");
		stmt.execute("load data local infile './지급자료/orderlist.txt' into table orderlist ignore 1 lines");
		stmt.execute("load data local infile './지급자료/ad.txt' into table ad ignore 1 lines");
		stmt.execute("load data local infile './지급자료/review.txt' into table review ignore 1 lines");
		stmt.execute("load data local infile './지급자료/stock.txt' into table stock ignore 1 lines");
		
		PreparedStatement pst = con.prepareStatement("UPDATE image SET image = ? WHERE serial = ?");
		
		try {
			for (int i = 1; i <= 295; i++) {
				String ext = i == 1 ? "png" : "jpg";
				
				File file = new File(String.format("./지급자료/images/%d.%s", i, ext));
				FileInputStream fis = new FileInputStream(file);
				
				pst.setBinaryStream(1, fis, (int) file.length());
				pst.setObject(2, i);
				
				pst.execute();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		stmt.execute("drop user if exists 'user'");
		stmt.execute("create user 'user' identified by '1234'");
		stmt.execute("grant select, insert, update, delete on computer_2020.* to 'user'");
		stmt.execute("flush privileges");
		
		JOptionPane.showMessageDialog(null, "DB 생성 완료", "Message", JOptionPane.INFORMATION_MESSAGE);
		
	}

}
