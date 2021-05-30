package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InsertFrame extends BaseFrame {

	private boolean check;
	private String checkId;

	private JTextField tfName = new JTextField(20);
	private JTextField tfId = new JTextField(20);
	private JPasswordField tfPw = new JPasswordField(20);
	private JPasswordField tfPw1 = new JPasswordField(20);
	private JTextField tfPhone = new JTextField(20);
	private JTextField tfBirth = new JTextField(20);
	private JTextField tfAddress = new JTextField(20);

	public InsertFrame() {

		super("회원가입", 445, 500);

		JPanel pnlCenter = new JPanel(null);
		JPanel pnlGrid = new JPanel(new GridLayout(7, 2 , -140 , 30));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JLabel lbName = new JLabel("이름");
		JLabel lbId = new JLabel("아이디");
		JLabel lbPw = new JLabel("비밀번호");
		JLabel lbPw1 = new JLabel("비밀번호 체크", JLabel.LEFT);
		JLabel lbPhone = new JLabel("전화번호");
		JLabel lbBirth = new JLabel("생년월일");
		JLabel lbAddress = new JLabel("주소");

		JButton btncheck = new JButton("중복확인");
		JButton btnInsert = new JButton("회원가입");
		JButton btnExit = new JButton("취소");

		pnlGrid.add(lbName);
		pnlGrid.add(tfName);
		pnlGrid.add(lbId);
		pnlGrid.add(tfId);
		pnlGrid.add(lbPw);
		pnlGrid.add(tfPw);
		pnlGrid.add(lbPw1);
		pnlGrid.add(tfPw1);
		pnlGrid.add(lbPhone);
		pnlGrid.add(tfPhone);
		pnlGrid.add(lbBirth);
		pnlGrid.add(tfBirth);
		pnlGrid.add(lbAddress);
		pnlGrid.add(tfAddress);

		pnlCenter.add(pnlGrid);
		pnlCenter.add(btncheck);

		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);

		pnlSouth.setBorder(BorderFactory.createEmptyBorder(7, 7, 15, 0));
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		pnlGrid.setBounds(5, 10, 320, 380);
		btncheck.setBounds(330, 70, 95, 30);

		btncheck.addActionListener(e -> check());
		btnExit.addActionListener(e -> openFrame(new LoginFrame()));
		btnInsert.addActionListener(e -> insert());

		tfId.addFocusListener(new FocusAdapter() {
	
			@Override
			public void focusLost(FocusEvent e) {

				if (check == true &&  !tfId.getText().equals(checkId)) {
					check = false;
				}
				
			};
		});
	}

	public static void main(String[] args) {
		new InsertFrame().setVisible(true);
	}

	private void insert() {

		String name = tfName.getText();
		String id = tfId.getText();
		String pw = tfPw.getText();
		String pw1 = tfPw1.getText();
		String phone = tfPhone.getText();
		String birth = tfBirth.getText();
		String address = tfAddress.getText();

		if (name.isEmpty() || id.isEmpty() || pw.isEmpty() || pw1.isEmpty() || phone.isEmpty() || birth.isEmpty() || address.isEmpty()) {
			eMessage("빈칸이 있습니다.");
			return;
		} 
		
		if (pw.equals(pw1) == false) {
			eMessage("비밀번호를 확인해주세요.");
			return;
		}
		
		if (check == false) {
			eMessage("중복체크를 해주세요.");
			return;
		}

			try {

				dbManager.executeUpdate("insert into user values(0, ?, ?, ?, ?, ?, ?)", id, pw, address, name, phone, birth);
				iMessage("회원가입이 완료되었습니다.");
				openFrame(new LoginFrame());

			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private void check() {
		String id = tfId.getText();

		if (id.isEmpty()) {
			eMessage("빈칸이 있습니다.");
			return;
		} 
			
			try {
				ResultSet rs = dbManager.executeQuery("select * from user where u_id =?", id);

				if (rs.next()) {
					
					eMessage("아이디가 중복되었습니다.");
					check = false;

				} else {
					
					iMessage("사용 가능한 아이디입니다.");
					check = true;
					checkId = tfId.getText();

				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}
}
