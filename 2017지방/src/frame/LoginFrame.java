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
	
	
	private JRadioButton rbUser = new JRadioButton("ȸ�� �α���",true);
	private JRadioButton rbNoUser = new JRadioButton("��ȸ�� �α���");
	
	private ButtonGroup group = new ButtonGroup();
	
	private JTextField tfUser = new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	private JButton btnOk = new JButton("Ȯ��");
	private JButton btnExit = new JButton("���");
	
	public LoginFrame() {
		super("ȸ�� �α���", 350, 180);
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter = new JPanel(null);
		
		JLabel lbLogin = new JLabel("�α���");
		JLabel lbUser = new JLabel("ȸ�� ��ȣ:");
		JLabel lbPw = new JLabel("��� ��ȣ :");
		
		lbLogin.setFont(new Font("���� ���",Font.BOLD,20));
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
		
		tfUser.setText("��ȸ��");
		
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
				iMessage("�α��� �Ϸ�");
				login = rs.getString(1);
				openFrame(new MainFrame());
				
			}else if(id.equals("��ȸ��")==true) {
				iMessage("��ȸ������ �α����մϴ�.");
				login = "��ȸ��";
				openFrame(new MainFrame());
			}else {
				iMessage("���� ȸ�� �Դϴ�. �ٽ� Ȯ���Ͽ� �ֽʽÿ�.");		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
