package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JobInfoFrame extends BaseFrame {

	private JTabbedPane tap = new JTabbedPane();

	private int p_num;

	private class Member {
		private int no;
		private String name;

		public Member(int no, String name) {
			this.no = no;
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public JobInfoFrame(int p_num) {
		super("작업 정보", 600, 350);

		this.p_num = p_num;

		System.out.println(p_num);
		JPanel pnlSouth = new JPanel();

		JButton btnExit = new JButton("닫기");

		panel pnl = new panel();
		panel1 pnl1 = new panel1();

		tap.addTab("일반", pnl);
		tap.addTab("노트", pnl1);

		pnlSouth.add(btnExit);

		pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		add(tap, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnExit.addActionListener(e -> dispose());

	}

	public static void main(String[] args) {
		new JobInfoFrame(0).setVisible(true);

	}

	private class panel extends JPanel {

		private JTextField tfPeriod = new JTextField();
		private JTextField tfTask = new JTextField();
		private JTextField tfStart = new JTextField();
		private JTextField tfEnd = new JTextField();

		private JComboBox<Member> comName = new JComboBox<>();
		private JComboBox<String> comPercent = new JComboBox<>();

		public panel() {

			setLayout(null);

			JPanel pnlCenter = new JPanel(new GridLayout(4, 2, -300, 10));

			JLabel lbStart = new JLabel("시작 : ");
			JLabel lbEnd = new JLabel("종료 : ");

			pnlCenter.add(new JLabel("이름 : "));
			pnlCenter.add(comName);
			pnlCenter.add(new JLabel("기간 : "));
			pnlCenter.add(tfPeriod);
			pnlCenter.add(new JLabel("완료율 : "));
			pnlCenter.add(comPercent);
			pnlCenter.add(new JLabel("업무 : "));
			pnlCenter.add(tfTask);

			add(pnlCenter);
			add(lbStart);
			add(lbEnd);
			add(tfStart);
			add(tfEnd);

			pnlCenter.setBounds(5, 5, 500, 150);
			lbStart.setBounds(5, 165, 50, 30);
			tfStart.setBounds(105, 165, 165, 30);

			lbEnd.setBounds(285, 165, 50, 30);
			tfEnd.setBounds(340, 165, 165, 30);

			try {
				ResultSet rs = dbManager.executeQuery("select * from member where p_num = ?", p_num);
				while (rs.next()) {
					comName.addItem(new Member(rs.getInt(1), rs.getString(5)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			for (int i = 0; i <= 100; i++) {
				comPercent.addItem(String.format("%d%%", i));
			}

			tfPeriod.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					try {
						int period = Integer.valueOf(tfPeriod.getText());

						tfTask.setText(String.format("%d hours", period * 8));
					} catch (NumberFormatException ee) {

					}
				}
			});

			tfStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					isStart = true;

					CalendarFrame calendar = new CalendarFrame();

					calendar.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							tfStart.setText(SDate.format(DateTimeFormatter.ofPattern("yyyy. MM. dd 오전 09:00")));

							int cnt = 1; // 시작날짜 하루 세야되기 때문에 1부터 시작
							int period = Integer.valueOf(tfPeriod.getText());
							LocalDate fDate = SDate;

							while (true) {
								fDate = fDate.plusDays(1);

								if (fDate.getDayOfWeek() != DayOfWeek.SATURDAY
										&& fDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
									cnt++;
								}

								if (cnt == period) {
									break;
								}
							}

							tfEnd.setText(fDate.format(DateTimeFormatter.ofPattern("yyyy. MM. dd 오후 18:00")));
						}
					});

					calendar.setVisible(true);
				}
			});
		}
	}

	private class panel1 extends JPanel {

		private JTextField tfName = new JTextField();
		private JTextArea tfNote = new JTextArea();

		public panel1() {

			setLayout(null);

			JLabel lbName = new JLabel("이름:");
			JLabel lbNote = new JLabel("노트:");

			add(lbNote);
			add(lbName);
			add(tfNote);
			add(tfName);

			lbName.setBounds(5, 5, 50, 30);
			tfName.setBounds(80, 5, 450, 30);
			lbNote.setBounds(5, 45, 50, 30);
			tfNote.setBounds(5, 85, 530, 125);

		}
	}

}
