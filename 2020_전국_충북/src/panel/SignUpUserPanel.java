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
		
		JButton btnSignUp = createButton("회원가입");
		JButton btnCancel = createButton("취소");
		
		pnlCenter.add(createLabel("아이디"));
		pnlCenter.add(tfId);
		pnlCenter.add(createLabel("비밀번호"));
		pnlCenter.add(tfPw);
		pnlCenter.add(createLabel("이름"));
		pnlCenter.add(tfName);
		pnlCenter.add(createLabel("이메일"));
		pnlCenter.add(tfEmail);
		pnlCenter.add(createLabel("전화번호"));
		pnlCenter.add(tfPhone);
		pnlCenter.add(createLabel("주소"));
		pnlCenter.add(tfAddress);
		pnlCenter.add(createLabel("관심항목"));
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
			JOptionPane.showMessageDialog(null, "공백이 존재합니다.", "메시지", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			ResultSet rsSeller = dbManager.executeQuery("SELECT * FROM seller WHERE id = ?", id);
			ResultSet rsUser = dbManager.executeQuery("SELECT * FROM user WHERE id = ?", id);
			
			if (rsSeller.next() || rsUser.next()) {
				eMessage("이미 존재하는 아이디입니다.");
			} else if (pw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$]).{6,14}$") == false) {
				eMessage("영문, 숫자, 특수문자 포함 6~14자리로 구성해야합니다.");
			} else {
				iMessage("회원가입 되었습니다.");
				
				dbManager.executeUpdate("INSERT INTO user VALUES (0, ?, ?, ?, ?, ?, ?, ?)", id, pw, name, phone, address, email, favorite);
				frame.changePanel(new LoginPanel());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
