package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarFrame extends BaseFrame{
	
	private JComboBox<Integer> comMonth = new JComboBox<Integer>();
	
	private String[] Days = new String[] {"일", "월","화","수","목","금","토","일"};
	
	private JPanel pnlDays = new JPanel(new GridLayout(6, 7));
	
	private LocalDate date = LocalDate.now();
	private LocalDate now = LocalDate.now();
	
	private String str;
	
	public CalendarFrame(String str) {
		super("달력", 350, 400);
		this.str = str;

		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNN = new JPanel();
		JPanel pnlWeek = new JPanel(new GridLayout(1, 7));
		
		JLabel lbDate = new JLabel(String.valueOf(date.getYear()) + "년");
		
		for (int i = 0; i < Days.length-1; i++) {
			JLabel lb = new JLabel(Days[i],JLabel.CENTER);
			pnlWeek.add(lb);
		}
		
		pnlNN.add(lbDate);
		pnlNN.add(comMonth);
		pnlNN.add(new JLabel("월"));
		
		pnlNorth.add(pnlNN, BorderLayout.NORTH);
		pnlNorth.add(pnlWeek, BorderLayout.CENTER);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlDays, BorderLayout.CENTER);
		
		for (int i = 1; i <= 12; i++) {
			comMonth.addItem(i);
		}
		
		comMonth.setSelectedItem(date.getMonth().getValue());
		//String.valueOf(date.getMonth())
		
		comMonth.addItemListener(e -> getdays());
		getdays();

		System.out.println(str);
	}
	
	private void getdays() {
		
		pnlDays.removeAll();
		
		int month = (int) comMonth.getSelectedItem();
		
		date = LocalDate.of(date.getYear(), month, 1);
		
		for (int i = 0; i < date.getDayOfWeek().getValue(); i++) {
			pnlDays.add(new JLabel());
		}
		
		for (int i = 1; i <= date.lengthOfMonth(); i++) {
			LocalDate day = LocalDate.of(date.getYear(),month, i);
			pnlDays.add(createButton(day));
		}
		
		while (pnlDays.getComponentCount() < 42) {
			pnlDays.add(new JLabel());
		}
		
		pnlDays.updateUI();

	}
	
	public static void main(String[] args) {
		new ReplyInsertFrame().setVisible(true);
	}
	
	private JButton createButton(LocalDate day) {
		JButton btn = new JButton(String.valueOf(day.getDayOfMonth()));
		btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		if (day.compareTo(now) < 0) {
			btn.setEnabled(false);
		}
		
		if(str.equals("end")) {
			System.out.println("AA" + SelectedStart);
			LocalDate s = LocalDate.parse(SelectedStart);
			if (day.compareTo(s) < 0) {
				System.out.println(day.compareTo(s) < 0);
				btn.setEnabled(false);
			}			
		}
		
		btn.addActionListener(e -> {

			if (str.equals("start")) {
				SelectedStart = day.toString();
				System.out.println(SelectedStart);
//				Start = day;

//				while (day.compareTo(now) > 0) {
//					btn.setEnabled(false);
//				}

			} else {
				SelectedEnd = day.toString();
			}

			dispose();
		});

		return btn;

	}
}
