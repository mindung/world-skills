package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InsertMenuFrame extends BaseFrame {

	private JTextField tfName = new JTextField();

	private JComboBox<String> comGroup = new JComboBox<String>(new String[] { "한식", "중식", "일식", "양식" });
	private JComboBox<Integer> comPrice = new JComboBox<Integer>();
	private JComboBox<Integer> comCount = new JComboBox<Integer>();

	public InsertMenuFrame() {
		super("신규 메뉴 등록", 400, 280);

		JPanel pnlCenter = new JPanel(new GridLayout(4, 2));
		JPanel pnlSouth = new JPanel();

		JButton btnInsert = new JButton("등록");
		JButton btnExit = new JButton("닫기");

		pnlCenter.add(new JLabel("종류"));
		pnlCenter.add(comGroup);
		pnlCenter.add(new JLabel("메뉴명"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("가격"));
		pnlCenter.add(comPrice);
		pnlCenter.add(new JLabel("조리가능수량"));
		pnlCenter.add(comCount);

		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);

		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		for (int i = 0; i <= 50; i++) {
			comCount.addItem(i);
		}

		for (int i = 1000; i <= 12000; i += 500) {
			comPrice.addItem(i);
			System.err.println(i);
		}

		btnExit.addActionListener(e -> openFrame(new AdminFrame()));
		btnInsert.addActionListener(e -> insert());

	}

	public static void main(String[] args) {
		new InsertMenuFrame().setVisible(true);
	}

	private void insert() {
		String name = tfName.getText();
		int group = comGroup.getSelectedIndex() + 1;
		int price = (int) comPrice.getSelectedItem();
		int count = (int) comCount.getSelectedItem();

		if (name.isEmpty()) {
			eMessage("메뉴명을 입력해주세요.");

		} else {
			try {
				dbManager.executeUpdate("insert into meal values(0,?,?,?,?,0) ", group, name, price, count);
				iMessage("메뉴가 등록되었습니다.");
				openFrame(new AdminFrame());

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
