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
	
	private JButton btnDelete = new JButton("삭제");
	private JButton btnExit = new JButton("취소");
	
	private JTextField tfNum = new JTextField(10);
	
	public UserDeleteFrame() {
		super("회원 삭제", 300, 200);
		
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel();
		
		JLabel lbTitle = new JLabel("회원삭제",JLabel.CENTER);
	
		pnlCenter.add(new JLabel("회원주민번호:"));
		pnlCenter.add(tfNum);
		
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnExit);
		
		lbTitle.setFont(new Font("굴림",Font.BOLD,15));
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
				System.out.println("삭제 되었습니다.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

