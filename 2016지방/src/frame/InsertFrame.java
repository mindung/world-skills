package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertFrame extends BaseFrame {

	private JTextField tfCode = new JTextField();
	private JTextField tfName = new JTextField();
	private JTextField tfBirth = new JTextField();
	private JTextField tfTel = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JTextField tfCompany = new JTextField();

	public InsertFrame() {
		super("고객 등록", 500, 320);

		JPanel pnlCenter = new JPanel(new GridLayout(6, 2));
		JPanel pnlSouth = new JPanel();

		JButton btnInsert = new JButton("추가");
		JButton btnExit = new JButton("닫기");

		pnlCenter.add(new JLabel("고객코드:"));
		pnlCenter.add(tfCode);
		pnlCenter.add(new JLabel("*고 객 명:"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("*생년월일(YYYY-MM-DD):"));
		pnlCenter.add(tfBirth);
		pnlCenter.add(new JLabel("*연 락 처:"));
		pnlCenter.add(tfTel);
		pnlCenter.add(new JLabel("주  소:"));
		pnlCenter.add(tfAddress);
		pnlCenter.add(new JLabel("회  사:"));
		pnlCenter.add(tfCompany);

		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);

		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		tfCode.setEnabled(false);

		btnInsert.addActionListener(e -> insert());
		btnExit.addActionListener(e -> dispose());

		tfBirth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && tfBirth.getText().length() >= 8) {

					createCode();
				}
			}
		});
	}

	public static void main(String[] args) {
		new InsertFrame().setVisible(true);
	}

	private void insert() {
		
		String code = tfCode.getText();
		String name = tfName.getText();
		String birth = tfBirth.getText();
		String tel = tfTel.getText();
		String address = tfAddress.getText();
		String company = tfCompany.getText();

		if (name.isEmpty() || birth.isEmpty() || tel.isEmpty()) {

			eMessage("고객등록 에러", "필수항목(*)을 모두 입력하세요");
		} else {
			try {
				dbManager.executeUpdate("insert into customer values(?,?,?,?,?,?)", code, name, birth, tel, address,company);
				iMessage("메시지", "고객추가가 완료되었습니다");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createCode() {

		String birth = tfBirth.getText();
		String date = LocalDate.now().toString().substring(0, 2);

		String Year = birth.substring(0, 4);
		String Month = birth.substring(4, 6); // index 마지막 값 + 1
		String Day = birth.substring(6, 8);

		int sum = Integer.valueOf(Year) + Integer.valueOf(Month) + Integer.valueOf(Day);

		String code = "S" + date + sum;

		tfCode.setText(code);

	}
}
