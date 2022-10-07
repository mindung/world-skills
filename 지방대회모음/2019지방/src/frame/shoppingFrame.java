package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class shoppingFrame extends baseFrame {

	private String[] columns = new String[] { "","","메뉴명", "가격", "수량", "사이즈", "금액","" };
	
	private DefaultTableModel dtm = new DefaultTableModel(columns, 0);
	private JTable table = new JTable(dtm);

	private JButton btnOrder = new JButton("구매");
	private JButton btnDelete = new JButton("삭제");
	private JButton btnExit = new JButton("닫기");

	public shoppingFrame() {
		super("장바구니", 750, 380);

		JPanel pnlSouth = new JPanel();

		JLabel lbTitle = new JLabel(String.format("%S회원님 장바구니", u_name), JLabel.CENTER);
		lbTitle.setFont(new Font("굴림", Font.BOLD, 26));

		JScrollPane jsp = new JScrollPane(table);

		pnlSouth.add(btnOrder);
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnExit);

		add(lbTitle, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnOrder.addActionListener(e -> addOrder());
		btnDelete.addActionListener(e -> delete());
		btnExit.addActionListener(e -> openFrame(new StarboxFrame()));

		btnOrder.setPreferredSize(new Dimension(130, 30));
		btnDelete.setPreferredSize(new Dimension(130, 30));
		btnExit.setPreferredSize(new Dimension(130, 30));
		
		getShopping();

		for (int i = 0; i < table.getColumnCount(); i++) {
			if (columns[i].equals("")) {
				table.getColumnModel().getColumn(i).setMinWidth(0);
				table.getColumnModel().getColumn(i).setMaxWidth(0);
				table.getColumnModel().getColumn(i).setWidth(0);
			
			}
		}
		
		table.getColumn("메뉴명").setPreferredWidth(200);
//		table.getColumnModel().getColumn(2).setPreferredWidth(200);
	}

	public static void main(String[] args) {
		new shoppingFrame().setVisible(true);

	}

	private void delete() {
		if (table.getSelectedRow() == -1) {
			Emessage("삭제할 메뉴를 선택해주세요.");
		}else {
			int no = (int) table.getValueAt(table.getSelectedRow(), 0);
			
			try {
				dbmanager.executeUpate("delete from shopping where s_no =?", no);
				
				getShopping();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	private void getShopping() {
		try {
		
			dtm.setRowCount(0);
			
			ResultSet rs = dbmanager.executeQuery("select s_no, menu.m_no, m_name, m_price,s_count, s_size, s_amount, m_group from shopping inner join menu on menu.m_no = shopping.m_no");

			while(rs.next()) {
				dtm.addRow(new Object[] {
						
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getInt(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getInt(7),
						rs.getString(8),
				});
			}
			
			if(dtm.getRowCount() == 0) {
				btnOrder.setEnabled(false);
				btnDelete.setEnabled(false);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addOrder() {
		
		int sum = 0;
		
		for (int i = 0; i < table.getRowCount(); i++) {
			//table.getValueAt(i, 5); //i는 행
			sum += (int) table.getValueAt(i, 6);
		}
		
		LocalDate date = LocalDate.now();
		
		if(u_point < sum) {
			
			Imessage("구매되었습니다.");
			
			u_point += (int) (sum * 0.05);
			
			try {
				
				for (int i = 0; i < table.getRowCount(); i++) {

					int no = (int) table.getValueAt(i, 1);
					int price = (int) table.getValueAt(i, 3);
					int count = (int) table.getValueAt(i, 4);
					String size = (String) table.getValueAt(i, 5);
					int amount = (int) table.getValueAt(i, 6);
					String group = (String) table.getValueAt(i, 7);
					
					dbmanager.executeUpate("insert into orderlist values(0,?,?,?,?,?,?,?,?)", date,u_no,no,group,size,price,count,amount);					
					
				}
				
				dbmanager.executeUpate("update user set u_point = ? where u_no = ?", u_point, u_no);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			
			int yn = JOptionPane.showConfirmDialog(null, String.format("회원님의 총 포인트: %S\n포인트로 결제하시겠습니까?\n(아니오를 클릭 시 현금결제가 됩니다)", u_point), "결제수단", JOptionPane.YES_NO_OPTION);
			
			if(yn == JOptionPane.YES_OPTION) { 
				
				u_point -= sum; // u_point - amount 
				
				try {
					
					dbmanager.executeUpate("update user set u_point = ? where u_no = ?", u_point, u_no);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				Imessage(String.format("포인트로 결제 완료되었습니다. \n 남은 포인트 : %s", u_point));
			}else if(yn == JOptionPane.NO_OPTION){ // 그냥 창을 닫았을 경우를 제외해준다..

				Imessage("구매되었습니다.");
				
				u_point += (int) (sum * 0.05);
				
				try {
					
					for (int i = 0; i < table.getRowCount(); i++) {

						int no = (int) table.getValueAt(i, 1);
						int price = (int) table.getValueAt(i, 3);
						int count = (int) table.getValueAt(i, 4);
						String size = (String) table.getValueAt(i, 5);
						int amount = (int) table.getValueAt(i, 6);
						String group = (String) table.getValueAt(i, 7);
						
						dbmanager.executeUpate("insert into orderlist values(0,?,?,?,?,?,?,?,?)", date,u_no,no,group,size,price,count,amount);					
						
					}
					
					dbmanager.executeUpate("update user set u_point = ? where u_no = ?", u_point, u_no);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
		}
		
		try {
			
			dbmanager.executeUpate("delete from shopping");
			
			getShopping();
			
			ResultSet rs = dbmanager.executeQuery("select sum(o_amount) from orderlist where u_no = ? ",u_no);
		
			if(rs.next()) {
				
				int amount = rs.getInt(1);
				String grade = "";
				
				if(u_grade.equals("일반") && amount >= 300000) {
					grade = "Bronze";
				}else if (u_grade.equals("Bronze") && amount >= 500000) {
					grade = "Silver";
				}else if (u_grade.equals("Silver") && amount >= 800000) {
					grade = "Gold";
				}else {
					return;
				}
				
				u_grade = grade; // 등급이 업데이트가 안 되어있어서 iMessage가 또 뜬 것. u_grade = grade로 업데이트 시켜준다.
				
				dbmanager.executeUpate("update user set u_grade = ? where u_no = ?", grade, u_no);
				Imessage(String.format("축하합니다.\n 회원님의 등급이 %s로 승급하셨습니다.", grade));
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	
}
