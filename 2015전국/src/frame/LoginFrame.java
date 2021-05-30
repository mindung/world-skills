package frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginFrame extends baseFrame{
	
	private JTextField tfId = new JTextField(8);
	private JTextField tfPw = new JTextField(8);
	
	public LoginFrame() {	
		super("������ �α���", 950, 430);
		
		JPanel pnlCenter = new JPanel();
		JPanel pnlSouth = new JPanel();
		
		JButton btnLogin = new JButton("�α���");
		JButton btnExit = new JButton("����");

		JLabel lbImage = new JLabel(new ImageIcon("./�����ڷ�/intro.jpg"));
		
		pnlCenter.add(lbImage);
		
		pnlSouth.add(new JLabel("ID:"));
		pnlSouth.add(tfId);
		pnlSouth.add(new JLabel("PW:"));
		pnlSouth.add(tfPw);
		pnlSouth.add(btnLogin);
		pnlSouth.add(btnExit);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		btnLogin.addActionListener(e -> login());
		btnExit.addActionListener(e -> System.exit(0));
		
	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
	
	private void login() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		
		if(id.equals("admin")&&pw.equals("1234")) {
			openFrame(new MainFrame());
		}else {
			eMessage("�ùٸ��� �ʽ��ϴ�");
		}
	}
}

