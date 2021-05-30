package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserInsertFrame extends baseFrame{

	private JButton btnInsert = new JButton("확인");
	private JButton btnExit = new JButton("취소");
	
	private JTextField tfSearch = new JTextField(7);
	private JTextField tfName = new JTextField(8);
	private JTextField tfJumin = new JTextField(8);
	private JTextField tfTel = new JTextField(8);
	private JTextField tfAddress = new JTextField(8);
	
	public UserInsertFrame() {
		super("회원정보 수정", 300, 200);

		JPanel pnlNorth = new JPanel();
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(new GridLayout(4, 2));
		
		JLabel lbTitle = new JLabel("회원등록",JLabel.CENTER);

		lbTitle.setFont(new Font("굴림",Font.BOLD,15));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setBackground(Color.DARK_GRAY);
		lbTitle.setOpaque(true);
	
		pnlCenter.add(new JLabel("이름"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("주민번호"));
		pnlCenter.add(tfJumin);
		pnlCenter.add(new JLabel("연락처"));
		pnlCenter.add(tfTel);
		pnlCenter.add(new JLabel("주소"));
		pnlCenter.add(tfAddress);
		
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);
		
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(lbTitle,BorderLayout.NORTH);
		
		btnInsert.addActionListener(e -> insert());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new UserInsertFrame().setVisible(true);
	}

	private void insert() {
		String name = tfName.getText();
		String jumin = tfJumin.getText();
		String tel = tfTel.getText();
		String address = tfAddress.getText();
		
		if(name.isEmpty() || jumin.isEmpty() || tel.isEmpty() || address.isEmpty()) {
			eMessage("빈칸을 채워주세요");
		}else {
			try {
				ResultSet rs = dbManager.executeQuery("select * from member where mb_num =?", jumin);
				
				if(rs.next()) {
					iMessage("중복된 아이디가 있습니다.");
				}else {
					dbManager.executeUpdate("insert into member values(?,?,?,?)", name,jumin,tel,address);
					iMessage("처리가 되었습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
}

