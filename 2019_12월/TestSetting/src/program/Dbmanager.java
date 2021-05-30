package program;

public class Dbmanager {
//	drop database if exists `user`
//	CREATE `user`
//	USE `user`
//	insert into `user`(`no`int(), `name`varchar(10) not null,`id` varchar(10) not null, `passwd` varcher(10) not null)
//	
//}

	private Connection cn;
	ptivate statement st;
	
	public Dbmanager() {
		try {
			class.forName("com.mysql.jdbc.briver.");
			Cn = DriverManager.getConnection("jdbc:musql://localhost/?serverTimezone=UTC","root","1234");
			st = cn.createstatement();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}