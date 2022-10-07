package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import panel.BasePanel;

public class MemberFrame extends BaseFrame {

	private DefaultTableModel dtmDiary = new DefaultTableModel(new String[] { "", "" }, 0);
	private JTable tableDiary = new JTable(dtmDiary);

	private DefaultTableModel dtmFeedback = new DefaultTableModel(new String[] { "��¥", "�ǵ��" }, 0);
	private JTable tableFeedback = new JTable(dtmFeedback);

	private JScrollPane jsp = new JScrollPane(tableFeedback);

	private JLabel lbDate = new JLabel("", JLabel.CENTER);
	private JPanel pnlPage = new JPanel();

	private JTextArea tfFeedback = new JTextArea();

	private int index;
	private int pageIndex = 1;

	private LocalDate date = LocalDate.now();

	// Ŭ���� ������ֱ�
	public class Feedback {

		private String date;
		private String feedback;

		public Feedback(String date, String feedback) {
			this.date = date;
			this.feedback = feedback;

		}
	}

	private ArrayList<Feedback> feedbacks = new ArrayList<>();

	public MemberFrame(int index) {
		super("����", 700, 400);

		this.index = index;

		JLabel lbStrInfo = new JLabel("ȸ�� ����", JLabel.CENTER);
		JLabel lbInfo = new JLabel("", JLabel.CENTER);

		JPanel pnlNorth = new JPanel(new BorderLayout()); // ����
		JPanel pnlNCenter = new JPanel();
		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel(new BorderLayout()); // ����
		JPanel pnlScenter = new JPanel(new BorderLayout());
		JPanel pnlSEast = new JPanel(new BorderLayout());

		JButton btnExit = new JButton("����");
		JButton btnPrevDate = new JButton("���� ��¥");
		JButton btnNextDate = new JButton("���� ��¥");
		JButton btnFeedback = new JButton("�˸� ���� ����");

		pnlNorth.add(lbStrInfo, BorderLayout.WEST);
		pnlNorth.add(lbInfo, BorderLayout.CENTER);
		pnlNorth.add(btnExit, BorderLayout.EAST);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		pnlNCenter.add(btnPrevDate);
		pnlNCenter.add(lbDate);
		pnlNCenter.add(btnNextDate);

		pnlScenter.add(new JLabel("���� �˸�", JLabel.CENTER), BorderLayout.NORTH);
		pnlScenter.add(jsp, BorderLayout.CENTER);
		pnlScenter.add(pnlPage, BorderLayout.SOUTH);

		pnlSEast.add(tfFeedback, BorderLayout.CENTER);
		pnlSEast.add(btnFeedback, BorderLayout.SOUTH);

		pnlCenter.add(pnlNCenter, BorderLayout.NORTH);
		pnlCenter.add(tableDiary, BorderLayout.CENTER);

		pnlSouth.add(pnlScenter, BorderLayout.CENTER);
		pnlSouth.add(pnlSEast, BorderLayout.EAST);

		pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlSEast.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		tfFeedback.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbStrInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		lbDate.setPreferredSize(new Dimension(300, 30));
		pnlScenter.setPreferredSize(new Dimension(500, 120));
		pnlSEast.setPreferredSize(new Dimension(300, 120));
		lbStrInfo.setPreferredSize(new Dimension(130, 30));
		lbInfo.setPreferredSize(new Dimension(500, 30));

		pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		tableDiary.setPreferredSize(new Dimension(400, 300));
		btnExit.setPreferredSize(new Dimension(80, 30));
		pnlScenter.setPreferredSize(new Dimension(500, 120));
		pnlSEast.setPreferredSize(new Dimension(300, 120));

		tableFeedback.getColumnModel().getColumn(1).setPreferredWidth(250);

		try {
			ResultSet rs = dbManager.executeQuery("select * from tbl_member where M_index = ?", index);
			if (rs.next()) {
				lbInfo.setText(String.format("%s / %s / %s", rs.getString(2), rs.getString(4), rs.getString(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		btnPrevDate.addActionListener(e -> getPrev());
		btnNextDate.addActionListener(e -> getNext());
		btnExit.addActionListener(e -> openFrame(new AdminFrame()));
		btnFeedback.addActionListener(e -> sendFeedback());

		dtmDiary.addRow(new String[] { "������ ����", "" });
		dtmDiary.addRow(new String[] { "�߸� ����", "" });
		dtmDiary.addRow(new String[] { "���� ����", "" });
		dtmDiary.addRow(new String[] { "ó�� �๰ ����", "" });
		dtmDiary.addRow(new String[] { "��Ģ�� �", "" });
		dtmDiary.addRow(new String[] { "��������", "" });
		dtmDiary.addRow(new String[] { "��Ÿ ����", "" });

		getDiary();
		getFeedbackList();
		getFeedbackData();

	}

	public static void main(String[] args) {
		new MemberFrame(1).setVisible(true);
	}

	private void getPrev() {
		date = date.minusDays(1);
		getDiary();
	}

	private void getNext() {
		date = date.plusDays(1);
		getDiary();

	}

	private void getDiary() {

		lbDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy�� MM�� dd��")));

		try {
			ResultSet rs = dbManager.executeQuery("select * from tbl_diary where M_index = ? and D_date = ?", BasePanel.m_index,
					date.toString());

			if (rs.next()) {
				tableDiary.setValueAt(rs.getString(4), 0, 1);
				tableDiary.setValueAt(rs.getString(5), 1, 1);
				tableDiary.setValueAt(rs.getString(6) == null ? null : String.format("����(����/�̿�): %d/ %d", rs.getInt(6)),2, 1);
				tableDiary.setValueAt(rs.getString(7) == null ? null: String.format("���� : %s  ���� : %s", rs.getString(7), rs.getString(8)), 3, 1);
				tableDiary.setValueAt(rs.getString(9), 4, 1);
				tableDiary.setValueAt(rs.getString(12), 5, 1);
				tableDiary.setValueAt(rs.getString(13), 6, 1);

			} else {
				for (int i = 0; i < tableDiary.getRowCount(); i++) {
					tableDiary.setValueAt(null, i, 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void sendFeedback() {
		String feedback = tfFeedback.getText();
		
		if(feedback.isEmpty()) {
			eMessage("������ �Է����ּ���.");
			return;
		}
		
		try {
			dbManager.executeUpdate("insert into tbl_feedback values(0,?,?,0,?)", index,feedback,LocalDate.now().toString());
			
			iMessage("�ǵ���� �Ϸ�Ǿ����ϴ�.");
			
			getFeedbackList();
			getFeedbackData();
			pnlPage.updateUI();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void getFeedbackList() {
		try {			
			ResultSet rs = dbManager.executeQuery("select * from tbl_feedback where M_index = ? order by F_regdate DESC", index);
			
			feedbacks.clear();
			pnlPage.removeAll();
			
			while (rs.next()) {
				feedbacks.add(new Feedback(rs.getString(5), rs.getString(3)));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int pagecnt = (int) Math.ceil(feedbacks.size() /3f);
		
			for (int i = 0; i < pagecnt; i++) {
				int page = i + 1;
				
				JLabel lb = new JLabel(String.format("[%s]", page));
				
				lb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						pageIndex  = page; // ó���� �����س� pageIndex
						
						for (Component com : pnlPage.getComponents()) {
							com.setForeground(Color.BLACK);
						}
						
						lb.setForeground(Color.RED);
						
						getFeedbackData();
					}
				});
				
				if(page == pageIndex) {
					lb.setForeground(Color.RED); // ������ = �������ε��� ���� ��� ������
				}
				
				pnlPage.add(lb);
			}
	}
	
	private void getFeedbackData() {
	
		int start = (pageIndex - 1) * 3; // 1�ΰ��� start 0
		
		dtmFeedback.setRowCount(0);
		
		for (int i = start; i <= start + 2; i++) { // 0 <= 0+2 3�� ����
				if(i < feedbacks.size()) { // i���� feedbacks ������ �� ũ��
											// feedbacks�� �ش��ϴ� �迭 ������ feedback�� �־���
					Feedback feedback = feedbacks.get(i); // ������ ������ ���ִ� �ڵ� 
					dtmFeedback.addRow(new String[] {feedback.date,feedback.feedback});
					
				}else {
					dtmFeedback.addRow(new String[] {"",""});
				}
			
		}

	}
	
	
}
