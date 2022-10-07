package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.dbManager;

public class LoginFrame extends BaseFrame {

	private JTextField tfName = new JTextField();
	private JTextField tfPw = new JTextField();

	public LoginFrame() {
		super("�α���", 320, 190);

		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();

		JLabel lbTitle = new JLabel("������ �α���", JLabel.CENTER);
		JLabel lbName = new JLabel("�̸�");
		JLabel lbPw = new JLabel("��й�ȣ");

		JButton btnOk = new JButton("Ȯ��");
		JButton btnExit = new JButton("����");

		lbTitle.setFont(new Font("����", Font.BOLD, 22));

		pnlNorth.add(lbTitle);

		pnlCenter.add(lbName);
		pnlCenter.add(lbPw);
		pnlCenter.add(tfName);
		pnlCenter.add(tfPw);

		pnlSouth.add(btnOk);
		pnlSouth.add(btnExit);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		lbName.setBounds(30, 20, 30, 30);
		lbPw.setBounds(30, 45, 50, 30);
		tfName.setBounds(100, 20, 150, 20);
		tfPw.setBounds(100, 49, 150, 20);
		
		btnExit.addActionListener(e -> System.exit(0));
		btnOk.addActionListener(e -> login());
	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);

	}
	
	private void login() {
		String name = tfName.getText();
		String pw = tfPw.getText();
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from admin where name = ? and passwd = ?", name,pw);
			
			if(rs.next()) {
				
				openFrame(new MainFrame());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
