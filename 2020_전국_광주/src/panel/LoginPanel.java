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
		
		JPanel pnlCenter = new JPanel(new GridLayout(2, 2, -200, 20)); // - ���� ������ ���� ���̷���
		JPanel pnlSouth = new JPanel();
		
		JButton btnLogin = new JButton("�α���");
		
		pnlCenter.add(new JLabel("���̵�"));
		pnlCenter.add(tfId);
		pnlCenter.add(new JLabel("��й�ȣ"));
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
			eMessage("��ĭ�� �����մϴ�.");
			return;
		}
		
		if (id.equals("admin") && pw.equals("1234")) {
			frame.dispose();
			new AdminFrame().setVisible(true);
		} else {
			if (id.length() < 4 || id.length() > 12) {
				eMessage("���̵�� 4~12���� ���̷� �Է��ϼ���.");
				return;
			}
			
			if (pw.length() < 6) {
				eMessage("��й�ȣ�� 6���� �̻����� �Է����ּ���.");
				return;
			}
			
			try {
				ResultSet rs = dbManager.executeQuery("SELECT * FROM tbl_member WHERE M_id = ? AND M_passwd = ?", id, pw);
				
				if (rs.next()) {
					iMessage(String.format("%s�� ȯ���մϴ�.", rs.getString(4)));
					BasePanel.m_index= rs.getInt(1);
					frame.home();
				} else {
					eMessage("���̵� Ȥ�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}

