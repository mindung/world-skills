package panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import frame.AdminFrame;
import frame.MainFrame;

public class LoginPanel extends BasePanel{
	
	private JTextField tfId = new JTextField(10);
	private JPasswordField tfPw = new JPasswordField(10);
	
	public LoginPanel() {
		
		JPanel pnlCenter = new JPanel(new GridLayout(2, 2, -200, 20)); // - 넣은 이유는 간격 줄이려고
		JPanel pnlSouth = new JPanel();
		
		JButton btnLogin = new JButton("로그인");
		
		pnlCenter.add(new JLabel("아이디"));
		pnlCenter.add(tfId);
		pnlCenter.add(new JLabel("비밀번호"));
		pnlCenter.add(tfPw);
		
		pnlSouth.add(btnLogin);
		
		add(pnlCenter);
		add(pnlSouth);
		
		pnlCenter.setPreferredSize(new Dimension(330, 130));
		pnlSouth.setPreferredSize(new Dimension(330, 50));
		
		btnLogin.addActionListener(e -> login());
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private void login() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		
		if (id.isEmpty() || pw.isEmpty()) {
			eMessage("빈칸이 존재합니다.");
			return;
		}
		
		if (id.equals("admin") && pw.equals("1234")) {
			frame.dispose();
			new AdminFrame().setVisible(true);
		} else {
			if (id.length() < 4 || id.length() > 12) {
				eMessage("아이디는 4~12글자 사이로 입력하세요.");
				return;
			}
			
			if (pw.length() < 6) {
				eMessage("비밀번호는 6글자 이상으로 입력해주세요.");
				return;
			}
			
			try {
				ResultSet rs = dbManager.executeQuery("SELECT * FROM tbl_member WHERE M_id = ? AND M_passwd = ?", id, pw);
				
				if (rs.next()) {
					iMessage(String.format("%s님 환영합니다.", rs.getString(4)));
					BasePanel.m_index= rs.getInt(1);
					frame.home();
				} else {
					eMessage("아이디 혹은 비밀번호가 일치하지 않습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}

