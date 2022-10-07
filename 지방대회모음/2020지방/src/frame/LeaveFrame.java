package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LeaveFrame extends BaseFrame {

	private int h_no;
	private int amount;
	private int day;

	private JTextField tfNo = new JTextField(15);
	private JTextField tfName = new JTextField();
	private JTextField tfRoom = new JTextField();
	private JTextField tfSdate = new JTextField();
	private JTextField tfFdate = new JTextField();
	private JTextField tfMeal = new JTextField();
	private JTextField tfAmount = new JTextField();

	public LeaveFrame(int h_no) {
		super("���", 400, 420);

		this.h_no = h_no;
				
		JPanel pnlcenter = new JPanel(new GridLayout(7, 2, -60, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JLabel lbTitle = new JLabel("��꼭", JLabel.CENTER);

		JButton btnLeave = new JButton("���");
		JButton btnExit = new JButton("�ݱ�");

		lbTitle.setFont(new Font("��꼭", Font.BOLD, 25));

		pnlcenter.add(new JLabel("ȯ�ڹ�ȣ", JLabel.CENTER));
		pnlcenter.add(tfNo);
		pnlcenter.add(new JLabel("ȯ�ڸ�", JLabel.CENTER));
		pnlcenter.add(tfName);
		pnlcenter.add(new JLabel("ȣ��", JLabel.CENTER));
		pnlcenter.add(tfRoom);
		pnlcenter.add(new JLabel("�Կ���¥", JLabel.CENTER));
		pnlcenter.add(tfSdate);
		pnlcenter.add(new JLabel("�����¥", JLabel.CENTER));
		pnlcenter.add(tfFdate);
		pnlcenter.add(new JLabel("�Ļ� Ƚ��", JLabel.CENTER));
		pnlcenter.add(tfMeal);
		pnlcenter.add(new JLabel("�ѱݾ�", JLabel.CENTER));
		pnlcenter.add(tfAmount);

		pnlSouth.add(btnLeave);
		pnlSouth.add(btnExit);

		add(pnlcenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		add(lbTitle, BorderLayout.NORTH);

		btnLeave.addActionListener(e -> leave());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));

		getInfo();

		LocalDate sDay = LocalDate.parse(tfSdate.getText(), DateTimeFormatter.ISO_DATE);
		day = sDay.until(LocalDate.now()).getDays();

		System.out.println(day);

		tfMeal.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
				ResultSet rs = dbManager.executeQuery("select * from sickroom where s_room = ?", tfRoom.getText());

				rs.next();

				int meal = Integer.parseInt(tfMeal.getText());

				int people = rs.getInt(2);

				int price = (7 - people) * 50000;

				amount = price * day + (meal * 5000);

				tfAmount.setText(String.valueOf(amount));
				
				}catch(NumberFormatException e1) {
					tfAmount.setText("");
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			};
		});
	}

	public static void main(String[] args) {
		new LeaveFrame(1).setVisible(true);
	}

	private void getInfo() {

		try {
			ResultSet rs = dbManager.executeQuery(
					"select h_no, patient.p_no, p_name, s_room,h_sday from hospitalization inner join patient on patient.p_no = hospitalization.p_no inner join sickroom on sickroom.s_no = hospitalization.s_no where h_no = ?",
					h_no);
			if (rs.next()) {
				tfNo.setText(String.valueOf(rs.getInt(2)));
				tfName.setText(rs.getString(3));
				tfRoom.setText(String.valueOf(rs.getInt(4)));
				tfSdate.setText(rs.getString(5));
				tfFdate.setText(LocalDate.now().toString());
			}

			tfNo.setEnabled(false);
			tfName.setEnabled(false);
			tfRoom.setEnabled(false);
			tfSdate.setEnabled(false);
			tfFdate.setEnabled(false);
			tfAmount.setEnabled(false);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void leave() {

		String fdate = tfFdate.getText();
		String meal = tfMeal.getText();

		if (meal.isEmpty()) {
			eMessage("�Ļ� Ƚ���� �Է����ּ���.");
		} else {
			if (meal.matches("^[0-9]+$") == false) {
				eMessage("���ڸ� �Է����ּ���.");
				tfMeal.setText("");
				tfMeal.requestFocus();
			} else {
				try {
					ResultSet rs = dbManager.executeQuery("select * from hospitalization where h_no=?", h_no);
					if (rs.next()) {
						if (Integer.valueOf(meal) > day * 3) {
	
							System.out.println(Integer.valueOf(meal) > day * 3);
							eMessage(String.format("%d���� �ִ��Դϴ�.", day * 3));

							tfMeal.setText("");
							tfMeal.requestFocus();
						} else {
							
							iMessage("����� �Ϸ�Ǿ����ϴ�.");
							dbManager.executeUpdate("update hospitalization set h_fday = ?, h_meal = ?, h_amount = ? where h_no = ? ", fdate,meal,amount,h_no);							
//							
							openFrame(new MainFrame());
						}
					}
				} catch (SQLException e) {			
					e.printStackTrace();
				}
			}
		}
	}
}


