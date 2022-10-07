package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ReservationFrame extends BaseFrame {

	private JTextField tfNo = new JTextField();
	private JTextField tfName = new JTextField();
	private JTextField tfDate = new JTextField();
	private JTextField tfRecord = new JTextField();

	private JComboBox<String> comSection = new JComboBox<String>(new String[] { "����", "�����ܰ�", "�Ȱ�", "ġ��" });
	private JComboBox<String> comDoctor = new JComboBox<String>();
	private JComboBox<String> comExamination = new JComboBox<String>();

	private JLabel lbImg = new JLabel();

	private String[] Day_Of_Week = { "��", "��", "ȭ", "��", "��", "��", "��", "" };

	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "�����", "�ǻ�", "���ᳯ¥", "�ð�", "�ð���" }, 0);
	private JTable table = new JTable(dtm);

	public ReservationFrame() {
		super("���Ό��", 600, 610);

		selectedDate = "";

		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel pnlNorth = new JPanel(new GridLayout(3, 4, 5, 5));
		JPanel pnlExamination = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JScrollPane jsp = new JScrollPane(table);

		JButton btnReserve = new JButton("����");
		JButton btnExit = new JButton("�ݱ�");

		pnlNorth.add(new JLabel("ȯ�ڹ�ȣ", JLabel.CENTER));
		pnlNorth.add(tfNo);
		pnlNorth.add(new JLabel("ȯ�ڸ�", JLabel.CENTER));
		pnlNorth.add(tfName);
		pnlNorth.add(new JLabel("�����", JLabel.CENTER));
		pnlNorth.add(comSection);
		pnlNorth.add(new JLabel("�ǻ�", JLabel.CENTER));
		pnlNorth.add(comDoctor);
		pnlNorth.add(new JLabel("��¥", JLabel.CENTER));
		pnlNorth.add(tfDate);
		pnlNorth.add(new JLabel("�����̷�", JLabel.CENTER));
		pnlNorth.add(tfRecord);

		pnlExamination.add(new JLabel("�˻�"));
		pnlExamination.add(comExamination);
		pnlExamination.add(lbImg);

		pnlSouth.add(btnReserve);
		pnlSouth.add(btnExit);

		pnlCenter.add(jsp, BorderLayout.CENTER);
		pnlCenter.add(pnlExamination, BorderLayout.SOUTH);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		jsp.setPreferredSize(new Dimension(580, 300));
		lbImg.setPreferredSize(new Dimension(140, 140));

		pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 1, 5, 1));

		lbImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		tfNo.setText(String.valueOf(p_no));
		tfName.setText(p_name);
		
		tfNo.setEnabled(false);
		tfName.setEnabled(false);
		tfRecord.setEnabled(false);
		tfDate.setEnabled(false);

		tfNo.setHorizontalAlignment(SwingConstants.CENTER);
		tfName.setHorizontalAlignment(SwingConstants.CENTER);
		tfRecord.setHorizontalAlignment(SwingConstants.CENTER);
		tfDate.setHorizontalAlignment(SwingConstants.CENTER);

		try {
			ResultSet rs = dbManager.executeQuery("select * from examination");

			while (rs.next()) {
				comExamination.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		btnReserve.addActionListener(e -> reserve());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
		
		comExamination.addItemListener(e -> getExamination());

		comSection.addItemListener(e -> {
			getDoctor();
			getTableList();

		});

		comDoctor.addItemListener(e -> {
			d_name = (String) comDoctor.getSelectedItem();
			getTableList();
		});

		tfDate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				calendarFrame frame = new calendarFrame();

				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						tfDate.setText(selectedDate);
						getTableList();
					}
				});

				frame.setVisible(true);

			}
		});

		getDoctor();

	}

	public static void main(String[] args) {
		new ReservationFrame().setVisible(true);
	}

	private void getDoctor() {

		String section = (String) comSection.getSelectedItem();

		comDoctor.removeAllItems();
		try {
			ResultSet rs = dbManager.executeQuery("select distinct d_name from doctor where d_section = ?", section);

			while (rs.next()) {
				comDoctor.addItem(rs.getString(1));
			}

			rs = dbManager.executeQuery("select * from reservation where r_section = ? and p_no = ?", section, p_no);

			tfRecord.setText(rs.next() ? "����" : "����");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getExamination() {

		String examination = (String) comExamination.getSelectedItem();
		String path = String.format("./Datafiles/�̹���/%s.jpg", examination.trim());
		ImageIcon icon = new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(140, 140, Image.SCALE_SMOOTH));
		lbImg.setIcon(icon);

	}

	private void getTableList() {

		String date = tfDate.getText();

		if (date.isEmpty() || comSection.getSelectedIndex() == -1 || comDoctor.getSelectedIndex() == -1 ) {
			return;
		}

		String section = (String) comSection.getSelectedItem();
		String doctor = (String) comDoctor.getSelectedItem();

		dtm.setRowCount(0);

		int d = LocalDate.parse(date, DateTimeFormatter.ISO_DATE).getDayOfWeek().getValue();
		
		String day = Day_Of_Week[d]; // �ش� ���� ����

		try {
			ResultSet rs = dbManager.executeQuery("select * from doctor where d_name = ? and d_day = ? ", d_name, day);
			while (rs.next()) { //while

				String time = rs.getString(5); // ���� �Ǵ� ������ ���� ��
				
				int start = time.equals("����") ? 9 : 14;
				int end = time.equals("����") ? 12 : 17;

				for (int i = start; i <= end; i++) {

					dtm.addRow(new Object[] { section, doctor, date, String.format("%d:00", i), time });
					// ����
					if (i != end) { // ������ �ð��� �ƴ� ��� 30�� ����
						dtm.addRow(new Object[] { section, doctor, date, String.format("%d:30", i), time });

					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void reserve() {

		if(table.getSelectedRow() == -1) {
			eMessage("������ �ð��� �������ּ���.");
			
		}else {
			
			String section = (String) comSection.getSelectedItem();
			String date = (String) tfDate.getText();
			
			String time = (String) table.getValueAt(table.getSelectedRow(), 3);
			String timeStr = (String) table.getValueAt(table.getSelectedRow(), 4);
			
			int e_no = comExamination.getSelectedIndex() + 1;
			
			String day = Day_Of_Week[LocalDate.parse(date, DateTimeFormatter.ISO_DATE).getDayOfWeek().getValue()];
			
			try {
				ResultSet rs = dbManager.executeQuery("select * from doctor where d_name = ? and d_day = ? and d_time = ?", d_name,day,timeStr);
				
				rs.next();
				int d_no = rs.getInt(1);
				
				System.out.println(d_no);
				
				 rs = dbManager.executeQuery("select * from reservation where p_no = ? and r_section = ? and r_date =?", p_no,section,date);
				
				if(rs.next()) {
					eMessage("������¥�� ���� �� ���Ḧ �� �� �����ϴ�.");
					return;
				}
				
				rs = dbManager.executeQuery("select  * from reservation where p_no =? and r_date = ? and r_time = ? ", p_no,date,time);
				
				if(rs.next()) {
					eMessage("������¥�� ���� �ð� ���Ḧ �� �� �����ϴ�.");
					return;
				}
				
				rs = dbManager.executeQuery("select * from reservation where d_no = ? and r_date = ? and r_time = ?", d_no,date,time);
				
				if(rs.next()) {
					eMessage("�̹� ����Ǿ��ִ� �ð����Դϴ�.");
					return;
				}
					
				iMessage("����Ǿ����ϴ�.");
				
				dbManager.executeUpdate("insert into reservation values(0, ?, ?, ?, ?, ?, ?)", p_no, d_no, section,date,time,e_no);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
