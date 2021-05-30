package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.BaseFrame;
import panel.BasePanel;

public class pressureFrame extends BaseFrame{
	
	private JTextField tf1 = new JTextField();
	private JTextField tf2 = new JTextField();
	
	public pressureFrame() {
		super("혈압", 300, 160);
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();
		
		JButton btnExit = new JButton("취소");
		JButton btnOk = new JButton("확인");
		
		JLabel lb1 = new JLabel("수축기 입력");
		JLabel lb2 = new JLabel("이완기 입력");
		
		pnlCenter.add(tf1);
		pnlCenter.add(tf2);
		pnlCenter.add(lb1);
		pnlCenter.add(lb2);
		
		pnlSouth.add(btnExit);
		pnlSouth.add(btnOk);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		lb1.setBounds(45, 0, 100, 30);
		lb2.setBounds(170, 0, 100, 30);
		tf1.setBounds(30, 25, 110, 50);
		tf2.setBounds(150, 25, 110, 50);

		btnExit.setPreferredSize(new Dimension(130, 35));
		btnOk.setPreferredSize(new Dimension(130, 35));
		
		btnExit.addActionListener(e -> dispose());
		btnOk.addActionListener(e -> ok());
	}
	
	public static void main(String[] args) {
		new pressureFrame().setVisible(true);
	}
	
	private void ok() {
		String tf11 = tf1.getText();
		String tf22 = tf2.getText();
		String info = String.format("%s/%s", tf11,tf22);
		
		System.out.println(info);
		if(tf11.isEmpty() || tf22.isEmpty()) {
			eMessage("빈칸이 존재합니다.");
		}else {
			if(tf11.matches("^[1-9]+$") && tf22.matches("^[1-9]+$")) {
			
				
				try {
					if(BasePanel.d_index == 0) {
						
						dbManager.executeUpdate("insert into tbl_diary(M_index, D_date, D_pressure) values(?,?,?)", BasePanel.m_index,BasePanel.d_date,info);
					}else {
						dbManager.executeUpdate("update tbl_diary set D_pressure = ? where D_index = ?", info,BasePanel.d_index);
					}
					
					iMessage("입력이 완료되었습니다.");
					
					dispose();
					BasePanel.mainPanel.getdiary();
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
				}
			}else {
				eMessage("둘다 숫자로 입력해주세요.");
			}
		}
		

	}
}

