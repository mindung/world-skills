package panel;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignUpUserPanel extends BasePanel {

	private JTextField tfId = createComponent(new JTextField());
	private JTextField tfPw = createComponent(new JTextField());
	private JTextField tfName = createComponent(new JTextField());
	private JTextField tfEmail = createComponent(new JTextField());
	private JTextField tfPhone = createComponent(new JTextField());
	private JTextField tfAddress = createComponent(new JTextField());
	private JComboBox<String> comFavorite = createComponent(new JComboBox<>());
	
	public SignUpUserPanel() {
		setLayout(null);
		
		JPanel pnlCenter = new JPanel(new GridLayout(7, 2, -180, 10));
		JPanel pnlSouth = new JPanel(new GridLayout(1, 2, 10, 0));
		
		JButton btnSignUp = createButton("ȸ������");
		JButton btnCancel = createButton("���");
		
		pnlCenter.add(createLabel("���̵�"));
		pnlCenter.add(tfId);
		pnlCenter.add(createLabel("��й�ȣ"));
		pnlCenter.add(tfPw);
		pnlCenter.add(createLabel("�̸�"));
		pnlCenter.add(tfName);
		pnlCenter.add(createLabel("�̸���"));
		pnlCenter.add(tfEmail);
		pnlCenter.add(createLabel("��ȭ��ȣ"));
		pnlCenter.add(tfPhone);
		pnlCenter.add(createLabel("�ּ�"));
		pnlCenter.add(tfAddress);
		pnlCenter.add(createLabel("�����׸�"));
		pnlCenter.add(comFavorite);
		
		pnlSouth.add(btnSignUp);
		pnlSouth.add(btnCancel);
		
		add(pnlCenter);
		add(pnlSouth);
		
		pnlCenter.setBackground(null);
		pnlSouth.setBackground(null);
		
		pnlCenter.setBounds(300, 100, 300, 280);
		pnlSouth.setBounds(300, 390, 300, 30);
		
		btnSignUp.addActionListener(e -> signUp());
		btnCancel.addActionListener(e -> frame.changePanel(new SignUpPanel()));
		
		addCategory();
	}
	
	private void addCategory() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM category");
			
			while(rs.next()) {
				comFavorite.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void signUp() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		String name = tfName.getText();
		String email = tfEmail.getText();
		String phone = tfPhone.getText();
		String address = tfAddress.getText();
		int favorite = comFavorite.getSelectedIndex() + 1;
		
		if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
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
				
				dbManager.executeUpdate("INSERT INTO user VALUES (0, ?, ?, ?, ?, ?, ?, ?)", id, pw, name, phone, address, email, favorite);
				frame.changePanel(new LoginPanel());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
