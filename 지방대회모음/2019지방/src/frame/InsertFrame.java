package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertFrame extends baseFrame {

	private JTextField tfName = new JTextField(20);
	private JTextField tfId = new JTextField(20);
	private JTextField tfPw = new JTextField(20);

	private JComboBox<Integer> ComYear = new JComboBox<Integer>();
	private JComboBox<Integer> ComMonth = new JComboBox<Integer>();
	private JComboBox<Integer> ComDay = new JComboBox<Integer>();

	public InsertFrame() {
		super("회원가입", 350, 240);

		JPanel pnlCenter = new JPanel(new FlowLayout());
		JPanel pnlSouth = new JPanel();

		JLabel lbName = new JLabel("이름", JLabel.RIGHT);
		JLabel lbId = new JLabel("아이디", JLabel.RIGHT);
		JLabel lbPw = new JLabel("비밀번호", JLabel.RIGHT);

		JButton btnInsert = new JButton("가입 완료");
		JButton btnExit = new JButton("취소");

		pnlCenter.add(lbName);
		pnlCenter.add(tfName);
		pnlCenter.add(lbId);
		pnlCenter.add(tfId);
		pnlCenter.add(lbPw);
		pnlCenter.add(tfPw);
		pnlCenter.add(new JLabel("생년월일"));
		pnlCenter.add(ComYear);
		pnlCenter.add(new JLabel("년"));
		pnlCenter.add(ComMonth);
		pnlCenter.add(new JLabel("월"));
		pnlCenter.add(ComDay);
		pnlCenter.add(new JLabel("일"));

		lbId.setPreferredSize(new Dimension(60, 35));
		lbPw.setPreferredSize(new Dimension(60, 35));
		lbName.setPreferredSize(new Dimension(60, 35));

		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);

		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnInsert.addActionListener(e -> Insert());
		btnExit.addActionListener(e -> openFrame(new LoginFrame()));

		LocalDate date = LocalDate.now();

		for (int i = 1900; i <= date.getYear(); i++) {
			ComYear.addItem(i);
		}

		for (int i = 1; i <= 12; i++) {
			ComMonth.addItem(i);
		}

		ComYear.setSelectedIndex(-1);
		ComMonth.setSelectedIndex(-1);

		ComYear.addItemListener(e -> getdays());
		ComMonth.addItemListener(e -> getdays());

	}

	public static void main(String[] args) {
		new InsertFrame().setVisible(true);

	}

	private void Insert() {
		String name = tfName.getText();
		String id = tfId.getText();
		String pw = tfPw.getText();
		
		if(name.isEmpty() || id.isEmpty()|| pw.isEmpty()|| ComYear.getSelectedIndex() == -1 || ComMonth.getSelectedIndex() == -1 || ComDay.getSelectedIndex() ==-1) {
			Emessage("누락된 항목이 있습니다.");
		}else {
			try {
				ResultSet rs = dbmanager.executeQuery("select * from user where u_id = ?", id);
				if(rs.next()) {
					Emessage("누락된 항목이 존재합니다.");
					
					tfId.setText("");
					tfId.requestFocus();
				}else {
					Imessage("가입완료 되었습니다.");
					
					String brith = String.format("%04d-%02d-%02d",ComYear.getSelectedItem(), ComMonth.getSelectedItem(), ComDay.getSelectedItem());
					
				dbmanager.executeUpate("insert into user values(0,?,?,?,?,?,?)", id,pw,name,brith,0,"일반");
					
					openFrame(new LoginFrame());
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	private void getdays() {

		if (ComYear.getSelectedItem() == null || ComMonth.getSelectedItem() == null) {
			return;
		}

		int year = (int) ComYear.getSelectedItem();
		int month = (int) ComMonth.getSelectedItem();

		LocalDate date = LocalDate.of(year, month, 1);

		ComDay.removeAllItems();
		for (int i = 1; i <= date.lengthOfMonth(); i++) {
			ComDay.addItem(i);
		}

		ComDay.setSelectedIndex(-1);

	}

}
