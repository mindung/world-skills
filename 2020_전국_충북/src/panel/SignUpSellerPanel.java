package panel;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignUpSellerPanel extends BasePanel {

	private JTextField tfId = createComponent(new JTextField());
	private JTextField tfPw = createComponent(new JTextField());
	private JTextField tfName = createComponent(new JTextField());
	private JTextField tfAddress = createComponent(new JTextField());
	
	public SignUpSellerPanel() {
		setLayout(null);
		
		JPanel pnlCenter = new JPanel(new GridLayout(4, 2, -180, 10));
		JPanel pnlSouth = new JPanel(new GridLayout(1, 2, 10, 0));
		
		JButton btnSignUp = createButton("ȸ������");
		JButton btnCancel = createButton("���");
		
		pnlCenter.add(createLabel("���̵�"));
		pnlCenter.add(tfId);
		pnlCenter.add(createLabel("��й�ȣ"));
		pnlCenter.add(tfPw);
		pnlCenter.add(createLabel("��ȣ��"));
		pnlCenter.add(tfName);
		pnlCenter.add(createLabel("�ּ�"));
		pnlCenter.add(tfAddress);
		
		pnlSouth.add(btnSignUp);
		pnlSouth.add(btnCancel);
		
		add(pnlCenter);
		add(pnlSouth);
		
		pnlCenter.setBackground(null);
		pnlSouth.setBackground(null);
		
		pnlCenter.setBounds(300, 150, 300, 160);
		pnlSouth.setBounds(300, 320, 300, 30);
		
		btnSignUp.addActionListener(e -> signUp());
		btnCancel.addActionListener(e -> frame.changePanel(new SignUpPanel()));
	}
	
	private void signUp() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		String name = tfName.getText();
		String address = tfAddress.getText();
		
		if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || address.isEmpty()) {
			JOptionPane.showMessageDialog(null, "������ �����մϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			ResultSet rsSeller = dbManager.executeQuery("SELECT * FROM seller WHERE id = ?", id);
			ResultSet rsUser = dbManager.executeQuery("SELECT * FROM user WHERE id = ?", id);
			
			if (rsSeller.next() || rsUser.next()) {
				eMessage("�̹� �����ϴ� ���̵��Դϴ�.");
			} else if (pw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$]).{6,14}$") == false) {
				eMessage("����, ����, Ư������ ���� 6~14�ڸ��� �����ؾ��մϴ�.");
			} else {
				iMessage("ȸ������ �Ǿ����ϴ�.");
				
				dbManager.executeUpdate("INSERT INTO seller VALUES (0, ?, ?, ?, ?)", id, pw, name, address);
				frame.changePanel(new LoginPanel());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
