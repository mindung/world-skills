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

	private DefaultTableModel dtmFeedback = new DefaultTableModel(new String[] { "날짜", "피드백" }, 0);
	private JTable tableFeedback = new JTable(dtmFeedback);

	private JScrollPane jsp = new JScrollPane(tableFeedback);

	private JLabel lbDate = new JLabel("", JLabel.CENTER);
	private JPanel pnlPage = new JPanel();

	private JTextArea tfFeedback = new JTextArea();

	private int index;
	private int pageIndex = 1;

	private LocalDate date = LocalDate.now();

	// 클래스 만들어주기
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
		super("메인", 700, 400);

		this.index = index;

		JLabel lbStrInfo = new JLabel("회원 정보", JLabel.CENTER);
		JLabel lbInfo = new JLabel("", JLabel.CENTER);

		JPanel pnlNorth = new JPanel(new BorderLayout()); // 방향
		JPanel pnlNCenter = new JPanel();
		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel(new BorderLayout()); // 방향
		JPanel pnlScenter = new JPanel(new BorderLayout());
		JPanel pnlSEast = new JPanel(new BorderLayout());

		JButton btnExit = new JButton("이전");
		JButton btnPrevDate = new JButton("이전 날짜");
		JButton btnNextDate = new JButton("다음 날짜");
		JButton btnFeedback = new JButton("알림 내용 전송");

		pnlNorth.add(lbStrInfo, BorderLayout.WEST);
		pnlNorth.add(lbInfo, BorderLayout.CENTER);
		pnlNorth.add(btnExit, BorderLayout.EAST);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		pnlNCenter.add(btnPrevDate);
		pnlNCenter.add(lbDate);
		pnlNCenter.add(btnNextDate);

		pnlScenter.add(new JLabel("지난 알림", JLabel.CENTER), BorderLayout.NORTH);
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

		dtmDiary.addRow(new String[] { "몸무게 측정", "" });
		dtmDiary.addRow(new String[] { "발목 부종", "" });
		dtmDiary.addRow(new String[] { "혈압 측정", "" });
		dtmDiary.addRow(new String[] { "처반 약물 복용", "" });
		dtmDiary.addRow(new String[] { "규칙적 운동", "" });
		dtmDiary.addRow(new String[] { "저염식이", "" });
		dtmDiary.addRow(new String[] { "기타 증상", "" });

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

		lbDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));

		try {
			ResultSet rs = dbManager.executeQuery("select * from tbl_diary where M_index = ? and D_date = ?", BasePanel.m_index,
					date.toString());

			if (rs.next()) {
				tableDiary.setValueAt(rs.getString(4), 0, 1);
				tableDiary.setValueAt(rs.getString(5), 1, 1);
				tableDiary.setValueAt(rs.getString(6) == null ? null : String.format("혈압(수축/이완): %d/ %d", rs.getInt(6)),2, 1);
				tableDiary.setValueAt(rs.getString(7) == null ? null: String.format("오전 : %s  오후 : %s", rs.getString(7), rs.getString(8)), 3, 1);
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
			eMessage("내용을 입력해주세요.");
			return;
		}
		
		try {
			dbManager.executeUpdate("insert into tbl_feedback values(0,?,?,0,?)", index,feedback,LocalDate.now().toString());
			
			iMessage("피드백이 완료되었습니다.");
			
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
						pageIndex  = page; // 처음에 선언해논 pageIndex
						
						for (Component com : pnlPage.getComponents()) {
							com.setForeground(Color.BLACK);
						}
						
						lb.setForeground(Color.RED);
						
						getFeedbackData();
					}
				});
				
				if(page == pageIndex) {
					lb.setForeground(Color.RED); // 페이지 = 페이지인덱스 같은 경우 빨강색
				}
				
				pnlPage.add(lb);
			}
	}
	
	private void getFeedbackData() {
	
		int start = (pageIndex - 1) * 3; // 1인경우는 start 0
		
		dtmFeedback.setRowCount(0);
		
		for (int i = start; i <= start + 2; i++) { // 0 <= 0+2 3번 돌겠
				if(i < feedbacks.size()) { // i보다 feedbacks 변수가 더 크면
											// feedbacks에 해당하는 배열 정보를 feedback에 넣어줌
					Feedback feedback = feedbacks.get(i); // 데이터 가지고 와주는 코드 
					dtmFeedback.addRow(new String[] {feedback.date,feedback.feedback});
					
				}else {
					dtmFeedback.addRow(new String[] {"",""});
				}
			
		}

	}
	
	
}
