package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {

	private JTextField tfId=  new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	public LoginFrame() {
		super("�α���", 320, 180);
	
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(null);
		
		JButton btnSignUp = new JButton("ȸ������");
		JButton btnLogin = new JButton("�α���");
		JButton btnExit = new JButton("����");
		
		JLabel lbTitle = new JLabel("��������ý���",JLabel.CENTER);
		JLabel lbId = new JLabel("ID : ");
		JLabel lbPw = new JLabel("PW : ");
		
		lbTitle.setFont(new Font("����",Font.BOLD,20));
		
		pnlCenter.add(tfId);
		pnlCenter.add(tfPw);
		pnlCenter.add(lbId);
		pnlCenter.add(lbPw);
		pnlCenter.add(btnLogin);
		
		pnlSouth.add(btnSignUp);
		pnlSouth.add(btnExit);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		add(lbTitle,BorderLayout.NORTH);
		
		lbId.setBounds(30, 10, 30, 30);
		lbPw.setBounds(30, 50, 30, 30);
		tfId.setBounds(65, 10, 140, 25);
		tfPw.setBounds(65, 50, 140, 25);
		btnLogin.setBounds(210, 8, 75, 70);
		
		btnLogin.addActionListener(e -> login()); 
		btnSignUp.addActionListener(e -> openFrame(new SignUpFrame()));
		btnExit.addActionListener(e -> System.exit(0));
		
	}
	
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
	
	private void login() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		
		if(id.isEmpty() || pw.isEmpty()) {
			eMessage("��ĭ�� �����մϴ�.");
		}else {
			try {
				ResultSet rs = dbManager.executeQuery("select * from patient where p_id = ? and p_pw = ?", id,pw);

				if(rs.next()) {
					
					p_no = rs.getInt(1);
					p_name = rs.getString(2);
					
				
					openFrame(new MainFrame());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
