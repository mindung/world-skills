package panel;

import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends BasePanel {

	private JTextField tfId = createComponent(new JTextField());
	private JPasswordField tfPw = createComponent(new JPasswordField());
	
	public LoginPanel() { 
		
		setLayout(null);
		
		JLabel lbId = new JLabel("ID : ", JLabel.RIGHT);
		JLabel lbPw = new JLabel("PW : ", JLabel.RIGHT);
		
		JButton btnLogin = createButton("�α���");
		JButton btnSignUp = createButton("ȸ������");
		
		add(lbId);
		add(lbPw);
		add(tfId);
		add(tfPw);
		add(btnLogin);
		add(btnSignUp);
		
		lbId.setBounds(300, 210, 30, 35);
		lbPw.setBounds(300, 255, 30, 35);
		tfId.setBounds(340, 210, 200, 35);
		tfPw.setBounds(340, 255, 200, 35);
		btnLogin.setBounds(550, 210, 50, 80);
		btnSignUp.setBounds(300, 300, 300, 30);
		
		btnLogin.setMargin(new Insets(0, 0, 0, 0));
		
		btnLogin.addActionListener(e -> login());
		btnSignUp.addActionListener(e -> signUp());
	}
	
	private void login() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		
		if (id.isEmpty() || pw.isEmpty()) {
			eMessage("ID/PW�� ��� �Է����ּ���.");
			return;
		}
		
		try {
			ResultSet rsSeller = dbManager.executeQuery("SELECT * FROM seller WHERE id = ? AND pw = ?", id, pw);
			ResultSet rsUser = dbManager.executeQuery("SELECT * FROM user WHERE id = ? AND pw = ?", id, pw);
			
			if (rsSeller.next()) {
				iMessage(String.format("%s�� ȯ���մϴ�.", rsSeller.getString(4)));
				
				sellerSerial = rsSeller.getInt(1);
				frame.changePanel(new MainPanel());
				frame.updateSession();
			} else if (rsUser.next()) {
				iMessage(String.format("%s�� ȯ���մϴ�.", rsUser.getString(4)));
				
				userSerial = rsUser.getInt(1);
				frame.changePanel(new MainPanel());
				frame.updateSession();
			} else {
				eMessage("���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void signUp() {
		frame.changePanel(new SignUpPanel());
	}
	
}
