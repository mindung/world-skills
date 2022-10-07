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

public class calendarFrame extends BaseFrame {

	private String[] Days = {"일","월","화","수","목","금","토","일"};
	
	private JPanel pnlDays = new JPanel(new GridLayout(6 , 7));
	private JLabel lbDate = new JLabel("",JLabel.CENTER);
	
	private LocalDate date = LocalDate.now();
	private LocalDate now = LocalDate.now();
	
	
	public calendarFrame() {
		super("기간선택", 400, 500);
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlWeekDay = new JPanel(new GridLayout(1, 7));
		JPanel pnlCalendar = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel();
		
		JButton btnPrev = new JButton("◀");
		JButton btnNext = new JButton("▶");
		
		pnlNorth.add(btnPrev);
		pnlNorth.add(lbDate);
		pnlNorth.add(btnNext);
		
		pnlCalendar.add(pnlWeekDay,BorderLayout.NORTH);
		pnlCalendar.add(pnlDays,BorderLayout.CENTER);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCalendar,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		btnPrev.addActionListener(e -> getPrve());
		btnNext.addActionListener(e -> getNext());
		
		
		for (int i = 0; i < Days.length -1 ; i++) {
			JLabel lb = new JLabel(Days[i],JLabel.CENTER);
			
			lb.setForeground(i == 0 ? Color.red : (i == 6 ? Color.BLUE : Color.BLACK));
			pnlWeekDay.add(lb);
		}
		
		getDays();
	}
	
	public static void main(String[] args) {
		new calendarFrame().setVisible(true);
	}

	private void getPrve() {
		date = date.minusMonths(1);
		getDays();
	}
	
	private void getNext() {
		date = date.plusMonths(1);
		getDays();
	}
	
	private void getDays() {
		pnlDays.removeAll();		
	
		lbDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy년 MM 월")));

		LocalDate firstDay = LocalDate.of(date.getYear(), date.getMonth(), 1);
		
		for (int i = 0; i < firstDay.getDayOfWeek().getValue(); i++) {
			pnlDays.add(new JLabel());
		}
		
		for (int i = 1; i <= date.lengthOfMonth(); i++) { // date에 해당하는 일을 다 구해야함
			LocalDate day = LocalDate.of(date.getYear(), date.getMonth(), i); // 년,월 일(변수) 지정
			pnlDays.add(createButton(day));
		}

		while (pnlDays.getComponentCount() < 42) {
			pnlDays.add(new JLabel());
		}
	}
	
	private JButton createButton(LocalDate date) {
		
		JButton btn = new JButton(String.valueOf(date.getDayOfMonth()));
		
		btn.setForeground(date.getDayOfWeek() == DayOfWeek.SUNDAY ? Color.red : ( date.getDayOfWeek() == DayOfWeek.SATURDAY ? Color.BLUE : Color.BLACK));
		btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		if(date.compareTo(now) < 0) {
			btn.setEnabled(false);
		}
		
		btn.addActionListener(e -> {
			String day = Days[date.getDayOfWeek().getValue()]; // 해당 주의 요일

			try {
				ResultSet rs = dbManager.executeQuery("select * from doctor where d_name = ? and d_day = ?", d_name,day);
				
				if(rs.next()) {
					eMessage("해당날짜에 해당 교수님 진료는 없습니다.");
				}else {
					selectedDate = date.toString();
					dispose();
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		return btn;
	}
}

