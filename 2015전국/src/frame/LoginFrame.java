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
		super("관리자 로그인", 950, 430);
		
		JPanel pnlCenter = new JPanel();
		JPanel pnlSouth = new JPanel();
		
		JButton btnLogin = new JButton("로그인");
		JButton btnExit = new JButton("종료");

		JLabel lbImage = new JLabel(new ImageIcon("./지급자료/intro.jpg"));
		
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
			eMessage("올바르지 않습니다");
		}
	}
}

