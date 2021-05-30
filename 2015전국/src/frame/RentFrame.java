package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class RentFrame extends baseFrame {

	private JTextField tfJumin = new JTextField(8);
	private JTextField tfCode = new JTextField(8);
	private JTextField tfName = new JTextField(8);
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"대여번호","회원이름","전화번호","도서이름","도서번호","날짜"},0);
	private JTable table = new JTable(dtm);
	
	public RentFrame() {
		super("대여/반납", 420, 540);
		
		getinfo();
		getTableInfo();
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter = new JPanel();
		JButton btnRent = new JButton("대여");
		JButton btnReturn = new JButton("반납");
		JButton btnExit = new JButton("취소");
	
		 JScrollPane jsp = new JScrollPane(table);
		 
		 pnlNorth.add(new JLabel("회원주민번호"));
		 pnlNorth.add(tfJumin);
		 pnlNorth.add(btnRent);
		 pnlNorth.add(btnReturn);
		 pnlNorth.add(btnExit);
		 
		 pnlCenter.add(new JLabel("도서번호"));
		 pnlCenter.add(tfCode);
		 pnlCenter.add(new JLabel("도서제목"));
		 pnlCenter.add(tfName);
		 
		 add(pnlNorth,BorderLayout.NORTH);
		 add(pnlCenter,BorderLayout.CENTER);
		 add(jsp,BorderLayout.SOUTH);
		 
		 btnReturn.addActionListener(e ->bookReturn());
		 btnRent.addActionListener(e -> rent());
		 btnExit.addActionListener(e -> dispose());
		 
	}
	
	public static void main(String[] args) {
		new RentFrame().setVisible(true);
	}
	
	private void getinfo() {
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from lib where lib_name = ?", rent);
			if(rs.next()) {
				tfCode.setText(rs.getString(1));
				tfName.setText(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getTableInfo() {
		try {
			dtm.setRowCount(0);
			ResultSet rs = dbManager.executeQuery("select* from rent where lib_name = ? ", tfName.getText());
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(5),
						rs.getString(4),
						rs.getString(6),
				});
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void rent() {
		String Jumin = tfJumin.getText();
		String code = tfCode.getText();
		String name = tfName.getText();
		String date = LocalDate.now().toString();

		try {
			ResultSet rs = dbManager.executeQuery("select* from member where mb_num = ?", Jumin);
			
			if(rs.next()) {
				System.err.println(state);
				
				if(state.equals("Y")) {
					
					String mbName = rs.getString(1);
					String tel = rs.getString(3);
					
					dbManager.executeUpdate("insert into rent values(0,?,?,?,?,?)",mbName,tel,code,name,date);
					dbManager.executeUpdate("update lib set lib_state = 'N' where lib_code = ?", code);
					getTableInfo();
					state = "N";
					
				}else {
					eMessage("대여중인 도서입니다.");
				}
			}else {
				iMessage("해당하는 주민번호가 없습니다.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void bookReturn() {
		String jumin = tfJumin.getText();
		
		try {
			ResultSet rs = dbManager.executeQuery("select* from member where mb_num = ?", jumin);
				if(rs.next()) {
					String mem_name = rs.getString(1);
					
					ResultSet rs1 = dbManager.executeQuery("select * from rent where mem_name =? ",mem_name);
					
					if(rs1.next()) {
						dbManager.executeUpdate("delete from rent where mem_name = ?", mem_name);
						dbManager.executeUpdate("update lib set lib_state = 'Y' where lib_code = ?", tfCode.getText());
						getTableInfo();
						state = "Y";
					}else {
						eMessage("대여중인 책이 없습ㄴ디ㅏ.");					
					}
				}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
