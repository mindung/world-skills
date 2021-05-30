package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserDeleteFrame extends baseFrame{
	
	private JButton btnDelete = new JButton("����");
	private JButton btnExit = new JButton("���");
	
	private JTextField tfNum = new JTextField(10);
	
	public UserDeleteFrame() {
		super("ȸ�� ����", 300, 200);
		
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel();
		
		JLabel lbTitle = new JLabel("ȸ������",JLabel.CENTER);
	
		pnlCenter.add(new JLabel("ȸ���ֹι�ȣ:"));
		pnlCenter.add(tfNum);
		
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnExit);
		
		lbTitle.setFont(new Font("����",Font.BOLD,15));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setBackground(Color.DARK_GRAY);
		lbTitle.setOpaque(true);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		add(lbTitle,BorderLayout.NORTH);
		
		btnDelete.addActionListener(e -> delete());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new UserDeleteFrame().setVisible(true);
	}
	
	private void delete() {
		String num = tfNum.getText();
		
		if(num.isEmpty()) {
			return;
		}else {
			try {
				dbManager.executeUpdate("delete from member where mb_num = ?", num);
				System.out.println("���� �Ǿ����ϴ�.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

