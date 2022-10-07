package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarFrame extends BaseFrame{

	private String[] Days = {"일","월","화","수","목","금","토","일"};
	
	private JPanel pnlDays = new JPanel(new GridLayout(6, 7));
	private JLabel lbDate = new JLabel();
	
	private JButton btnPrev = new JButton("◀");
	private JButton btnNext = new JButton("▶");
	
	private LocalDate date = LocalDate.now();
	private LocalDate now = LocalDate.now();
	
	private int wNo;
	
	public CalendarFrame(int wNo) {
		super("기간선택", 400, 500);
		
		this.wNo = wNo;
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlWeek = new JPanel(new GridLayout(1, 7));
		JPanel pnlCalendar = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel();
		
		pnlNorth.add(btnPrev);
		pnlNorth.add(lbDate);
		pnlNorth.add(btnNext);
		
		pnlCalendar.add(pnlWeek,BorderLayout.NORTH);
		pnlCalendar.add(pnlDays,BorderLayout.CENTER);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCalendar,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
	
		for (int i = 0; i < Days.length -1; i++) {
			JLabel lb = new JLabel(Days[i],JLabel.CENTER);
			
			lb.setForeground(i == 0 ? Color.red : (i == 6 ? Color.blue : Color.BLACK));
			pnlWeek.add(lb);
		}
		
		btnPrev.addActionListener(e -> {
			int n = now.getMonthValue();
			int d = date.getMonthValue();
			
			System.out.println(d);
			
			if(n == d){
				btnPrev.setEnabled(false);
			 }else{
				 btnPrev.setEnabled(true);
				 date = date.plusMonths(-1);
				 getDays();
			 }
		});
		
		btnNext.addActionListener(e -> {
			date = date.plusMonths(1);
			getDays();
		});	
		
		getDays();
	
	}
	
	public static void main(String[] args) {
		new CalendarFrame(1).setVisible(true);
	}
	
	private void getDays() {
	
		pnlDays.removeAll();
		
		LocalDate fristDay = LocalDate.of(date.getYear(), date.getMonth(), 1);
		
		for (int i = 0; i < fristDay.getDayOfWeek().getValue(); i++) {
			pnlDays.add(new JLabel());
		}
		
		for (int i = 1; i <= date.lengthOfMonth(); i++) {
			LocalDate day = LocalDate.of(date.getYear(), date.getMonth(), i);
			pnlDays.add(createButton(day));
		}
		
		while (pnlDays.getComponentCount() < 42) {
			pnlDays.add(new JLabel());
		}
		
		btnPrev.setEnabled(now.getMonthValue() != date.getMonthValue());
		
		lbDate.setText(date.format(DateTimeFormatter.ofPattern("YYYY년 MM월")));
		pnlDays.validate();
	}
	
	private JButton createButton(LocalDate date){
		
		JButton btn = new JButton(String.valueOf(date.getDayOfMonth()));
		Color color = date.getDayOfWeek() == DayOfWeek.SUNDAY ? Color.red : (date.getDayOfWeek() == DayOfWeek.SATURDAY ? Color.blue : Color.BLACK);
		
		btn.setForeground(color);
		btn.setBorder(BorderFactory.createLineBorder(color.BLACK));
		
		if(date.compareTo(now) <= 0){
			btn.setEnabled(false);
		}
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from reservation where wNo = ? and rdate = ?", wNo, date.toString());
			
			if (rs.next()) {
				btn.setEnabled(false);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		btn.addActionListener(e -> {
			selecteDate = date.toString();
			dispose();
		});
		
		return btn;
	
	}
}
