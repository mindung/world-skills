package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import frame.MemberFrame.Feedback;

public class MemberFrame1 extends BaseFrame{

	private DefaultTableModel dtmDiary = new DefaultTableModel(new String[] {"",""}, 0);
	private JTable tableDiary = new JTable(dtmDiary);
	
	private DefaultTableModel dtmFeedback = new DefaultTableModel(new String[] {"날짜","피드백"}, 0);
	private JTable tableFeedback = new JTable(dtmFeedback);
	
	private JScrollPane jsp = new JScrollPane(tableFeedback);
	
	private JLabel lbDate = new JLabel("",JLabel.CENTER);
	private JPanel pnlPage = new JPanel();
	
	private JTextArea tfFeedback = new JTextArea();
	
	private int index;
	private int pageIndex = 1;
	
	private LocalDate date = LocalDate.now();
	
	public class Feedback {
		private String date;
		private String feedback;
		
		public Feedback(String date, String feedback) {
			this.date = date;
			this.feedback = feedback;
		}
	}
	
	public MemberFrame1(int index) {
		super("메인", 700, 400);
		this.index = index;
		
		JPanel pnlNorth = new JPanel(new BorderLayout());
		
		JPanel pnlCenter = new JPanel(new FlowLayout());
		JPanel pnlCNorth = new JPanel();
		
		JPanel pnlSouth = new JPanel(new BorderLayout());
		JPanel pnlSCenter = new JPanel(new BorderLayout());
		JPanel pnlSEast = new JPanel(new BorderLayout());
		
		JLabel lbStrInfo = new JLabel("회원정보",JLabel.CENTER);
		JLabel lbInfo = new JLabel("",JLabel.CENTER);
		JLabel lbFeedback = new JLabel("지난 알림",JLabel.CENTER);
		
		JButton btnPrevDate = new JButton("이전 날짜");
		JButton btnNextDate = new JButton("다음 날짜");
		JButton btnExit = new JButton("이전");
		JButton btnFeedback = new JButton("알림내용 전송");
		
		pnlNorth.add(lbStrInfo,BorderLayout.WEST);
		pnlNorth.add(lbInfo,BorderLayout.CENTER);
		pnlNorth.add(btnExit,BorderLayout.EAST);
		
		pnlCNorth.add(btnPrevDate);
		pnlCNorth.add(lbDate);
		pnlCNorth.add(btnNextDate);
	
		pnlCenter.add(pnlCNorth);//,BorderLayout.NORTH
		pnlCenter.add(tableDiary);//,BorderLayout.CENTER
		
		pnlSCenter.add(lbFeedback,BorderLayout.NORTH);
		pnlSCenter.add(jsp,BorderLayout.CENTER);
		pnlSCenter.add(pnlPage,BorderLayout.SOUTH);
		
		pnlSEast.add(tfFeedback,BorderLayout.CENTER);
		pnlSEast.add(btnFeedback,BorderLayout.SOUTH);
		
		pnlSouth.add(pnlSEast,BorderLayout.EAST);
		pnlSouth.add(pnlSCenter,BorderLayout.CENTER);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
	
		lbStrInfo.setPreferredSize(new Dimension(130, 30));
		lbInfo.setPreferredSize(new Dimension(500, 30));		

		lbStrInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lbDate.setPreferredSize(new Dimension(300, 30));
		lbDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pnlSCenter.setPreferredSize(new Dimension(500, 120));
		pnlSEast.setPreferredSize(new Dimension(300, 120));
		
		tfFeedback.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		tableFeedback.getColumnModel().getColumn(1).setPreferredWidth(250);

		tableDiary.setPreferredScrollableViewportSize(new Dimension(400,300));
		
		pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlSEast.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		
		dtmDiary.addRow(new String[] { "몸무게 측정", "" });
		dtmDiary.addRow(new String[] { "발목 부종", "" });
		dtmDiary.addRow(new String[] { "혈압 측정", "" });
		dtmDiary.addRow(new String[] { "처반 약물 복용", "" });
		dtmDiary.addRow(new String[] { "규칙적 운동", "" });
		dtmDiary.addRow(new String[] { "저염식이", "" });
		dtmDiary.addRow(new String[] { "기타 증상", "" });

		try {
			ResultSet rs = dbManager.executeQuery("select * from tbl_member where M_index = ?", index);
			
			if(rs.next()) {
				lbInfo.setText(String.format("%s / %s / %s", rs.getString(2),rs.getString(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MemberFrame1(1).setVisible(true);
	}
}


