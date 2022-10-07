package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {

	private JTextField tfId = new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	public LoginFrame() {
		super("로그인", 350, 160);
		
		JPanel pnlCenter = new JPanel(new GridLayout(2, 2, -150, 10));
		JPanel pnlSouth = new JPanel();
		
		JButton btnLogin = createButton("로그인");
		
		pnlCenter.add(new JLabel("ID :"));
		pnlCenter.add(tfId);
		pnlCenter.add(new JLabel("PW :"));
		pnlCenter.add(tfPw);

		pnlSouth.add(btnLogin);

		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnLogin.setPreferredSize(new Dimension(310, 35));

		pnlCenter.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		btnLogin.addActionListener(e -> login());

	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}

	private void login() {
		String id = tfId.getText();
		String pw = tfPw.getText();

		if (id.isEmpty() | pw.isEmpty()) {
			eMessage("빈칸이 존재합니다.");
			return;
		}

		if (id.equals("admin") && pw.equals("1234")) {
			dispose();
			frame.dispose();
			new AdminFrame().setVisible(true);
			return;
		}

		try {
			ResultSet rs = dbManager.executeQuery("select * from member where m_id = ? and m_pw = ?", id, pw);
			if (rs.next()) {
				m_no = rs.getInt(1);
				System.out.println(m_no);
				dispose();
				frame.LogInfo();
//				frame.validate();

			} else {
				eMessage("아이디 혹은 비밀번호가 일치하지 않습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
