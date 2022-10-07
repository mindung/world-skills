package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarFrame extends BaseFrame{

	private JComboBox<Integer> comMonth = new JComboBox<>() ; 
	private JPanel pnlDays = new JPanel(new GridLayout(6, 7));
	
	private String[] Days = {"일","월","화","수","목","금","토","일"};
	
	private LocalDate now = LocalDate.now();
	
	public CalendarFrame() {
		super("달력", 300, 370);
	
		JPanel pnlNorth = new JPanel();
		JPanel pnlCalender = new JPanel(new BorderLayout());
		JPanel pnlDayOfWeek = new JPanel(new GridLayout(1, 7));
		JPanel pnlSouth = new JPanel();
		
		pnlNorth.add(new JLabel("2020 년"));
		pnlNorth.add(comMonth);
		pnlNorth.add(new JLabel("월"));
		
		pnlCalender.add(pnlDayOfWeek,BorderLayout.NORTH);
		pnlCalender.add(pnlDays,BorderLayout.CENTER);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCalender,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		pnlCalender.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		for (int i = 1; i <= 12; i++) {
			comMonth.addItem(i);
		}
		
		for (int i = 0; i < Days.length-1; i++) {
			JLabel lb = new JLabel(Days[i],JLabel.CENTER);
			lb.setForeground(i == 0 ? Color.red : (i == 6 ? Color.BLUE : Color.BLACK));
			pnlDayOfWeek.add(lb);
			
		}
		
		comMonth.setSelectedItem(9);
		comMonth.addItemListener(e -> getDays());
		getDays();
	}
	
	public static void main(String[] args) {
		new CalendarFrame().setVisible(true);
	}
	
	private void getDays() {
		
		pnlDays.removeAll();

		int month = (int) comMonth.getSelectedItem();
		
		LocalDate firstDay = LocalDate.of(now.getYear(), month, 1);
		
		
		for (int i = 0; i < firstDay.getDayOfWeek().getValue(); i++) {
			pnlDays.add(new JLabel());
		}
		
		for (int i = 1; i <= firstDay.lengthOfMonth(); i++) {
			LocalDate day = LocalDate.of(firstDay.getYear(), month,i );
			System.out.println(LocalDate.of(firstDay.getYear(), month,i ));
			pnlDays.add(createButton(day));
		}
		
		while (pnlDays.getComponentCount() < 42) {
			pnlDays.add(new JLabel());
		}
		
		pnlDays.validate();
	}
	
	private JButton createButton(LocalDate date) {
	
		JButton btn = new JButton(String.valueOf(date.getDayOfMonth()));
		
		btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		if(date.compareTo(now) < 0 || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) { // 입력한 날짜(1~마지막날짜)와 현재 날짜를 비교해 0보다 작으면 버튼 비활성화
			btn.setEnabled(false);
		}
		
		btn.addActionListener(e -> {
			if (isStart) { //  true
				SDate = date;
			} else {
				FDate = date;
			}
			
			dispose();
		});
		
		return btn;
	}
}

