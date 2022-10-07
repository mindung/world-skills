package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import jdbc.dbManager;

public class LoginFrame extends BaseFrame{
	
	
	private JRadioButton rbUser = new JRadioButton("회원 로그인",true);
	private JRadioButton rbNoUser = new JRadioButton("비회원 로그인");
	
	private ButtonGroup group = new ButtonGroup();
	
	private JTextField tfUser = new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	private JButton btnOk = new JButton("확인");
	private JButton btnExit = new JButton("취소");
	
	public LoginFrame() {
		super("회원 로그인", 350, 180);
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter = new JPanel(null);
		
		JLabel lbLogin = new JLabel("로그인");
		JLabel lbUser = new JLabel("회원 번호:");
		JLabel lbPw = new JLabel("비밀 번호 :");
		
		lbLogin.setFont(new Font("맑은 고딕",Font.BOLD,20));
		group.add(rbUser);
		group.add(rbNoUser);
		
		pnlNorth.add(rbUser);
		pnlNorth.add(rbNoUser);
		
		pnlCenter.add(lbLogin);
		pnlCenter.add(lbUser);
		pnlCenter.add(lbPw);
		pnlCenter.add(tfUser);
		pnlCenter.add(tfPw);
		pnlCenter.add(btnOk);
		pnlCenter.add(btnExit);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlNorth,BorderLayout.NORTH);
		
		lbLogin.setBounds(140, 0, 100, 30);
		lbUser.setBounds(20, 30, 60, 25);
		lbPw.setBounds(20, 70, 60, 25);
		tfUser.setBounds(100, 30, 150, 25);
		tfPw.setBounds(100, 70, 150, 25);
		
		btnOk.setBounds(260, 30, 70, 30);
		btnExit.setBounds(260, 60, 70, 30);
		
		btnOk.addActionListener(e -> ok());
		btnExit.addActionListener(e -> System.exit(0));
		
		rbNoUser.addActionListener(e -> noUser());
		rbUser.addActionListener(e -> User());
		
	
	}
	
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
	
	private void noUser() {
		
		tfUser.setText("비회원");
		
		tfUser.setEnabled(false);
		tfPw.setEnabled(false);
		tfPw.setText("123");
		

	}

	private void User() {
		
		tfUser.setText("");
		
		tfUser.setEnabled(true);
		tfPw.setEnabled(true);
		tfPw.setText("");

	}
	
	private void ok() {
		
		String id = tfUser.getText();
		String pw = tfPw.getText();
		
		
		try {
			ResultSet rs = dbManager.executeQuery("Select * from tbl_customer where cID = ? and cPw = ?" ,id,pw);
			
			if(rs.next()) {
				iMessage("로그인 완료");
				login = rs.getString(1);
				openFrame(new MainFrame());
				
			}else if(id.equals("비회원")==true) {
				iMessage("비회원으로 로그인합니다.");
				login = "비회원";
				openFrame(new MainFrame());
			}else {
				iMessage("없는 회원 입니다. 다시 확인하여 주십시오.");		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
