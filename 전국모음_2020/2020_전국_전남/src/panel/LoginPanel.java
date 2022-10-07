package panel;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends BasePanel{

	private JTextField tfId = new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	public LoginPanel() {
		
		setLayout(null);

		setSize(400, 300);
		//setPreferredSize(new Dimension(400, 300));
		
		JLabel lbId = new JLabel("ID:");
		JLabel lbPw = new JLabel("PW:");
		
		JButton btnLogin = new JButton("�α���");
		
		add(lbId);
		add(lbPw);
		add(tfId);
		add(tfPw);
		add(btnLogin);
		
		lbId.setBounds(20, 20, 40, 30);
		lbPw.setBounds(20, 70, 40, 30);
		tfId.setBounds(100, 20, 200, 30);
		tfPw.setBounds(100, 70, 200, 30);
		btnLogin.setBounds(20, 130, 290, 30);
		
		btnLogin.addActionListener(e -> login());
	}
	
	private void login() {
		
		String id = tfId.getText();
		String pw = tfPw.getText();
		
		if(id.isEmpty() || pw.isEmpty()) {
			eMessage("������ �����մϴ�.");
		}else {
			if(id.equals("admin") && pw.equals("1234")) {
				IMessage("�����ڷ� �α��� �Ϸ�Ǿ����ϴ�.");
				BasePanel.adminNo = 1;
				frame.changePanel(new MainPanel());
				frame.updateMenu();
			}else {
				eMessage("���̵� ��й�ȣ�� Ʋ���ϴ�.");
			}
		}

	}
}
