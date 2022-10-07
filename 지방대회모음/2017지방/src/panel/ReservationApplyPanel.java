package panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import frame.BaseFrame;

public class ReservationApplyPanel extends BasePanel{
	
	private JComboBox<String> com1 = new JComboBox<>();
	private JComboBox<String> com2 = new JComboBox<>();
	private JComboBox<String> com3 = new JComboBox<>();
	private JComboBox<String> com4 = new JComboBox<>();
	private JComboBox<String> com5 = new JComboBox<>();
	
	private JButton btnSearch = new JButton("�¼� ��ȸ");
	private JButton btnMain= new JButton("��������");
	
	private LocalDate date = LocalDate.now();
	
	public ReservationApplyPanel() {
		
		JPanel pnl = new JPanel(new BorderLayout());
		JPanel pnlC = new JPanel();
		
		JLabel lbTitle = new JLabel("����������ȸ �� �����ϱ�");
		
		lbTitle.setFont(new Font("���� ���",Font.BOLD,15));
		
		pnlC.add(new JLabel("������ȣ :"));
		pnlC.add(com1);
		pnlC.add(com2);
		pnlC.add(com3);
		pnlC.add(com4);
		pnlC.add(com5);
		pnlC.add(btnSearch);
		pnlC.add(btnMain);
	
		pnl.add(lbTitle,BorderLayout.NORTH);
		pnl.add(pnlC,BorderLayout.CENTER);

		add(pnl,BorderLayout.CENTER);
	
		getNumberInfo();
		getMonth();
		getDays();
		
		com3.addItem(date.format(DateTimeFormatter.ofPattern("yyyy")));
		com4.addItemListener(e -> getDays());
		
		btnSearch.addActionListener(e -> search());
		btnMain.addActionListener(e -> mainFrame.changePanel(new MainPanel()));
		
		for (int i = 1 ; i <=2; i++) {
			com2.addItem(String.format("%sȣ��", i));
		}
		
		
	}
	
	private void search() {
		if(com4.getSelectedIndex()==0 || com5.getSelectedIndex()==0) {
			iMessage("�� �Ǵ� ���� �����Ͽ� �ֽʽÿ�.");
		}else {
			BaseFrame.Year = String.format("%s", com3.getSelectedItem());
			BaseFrame.Month = String.format("%S", com4.getSelectedItem());
			BaseFrame.Day = String.format("%s", com5.getSelectedItem());
			
			LocalDate Datee = LocalDate.of(Integer.valueOf(BaseFrame.Year), Integer.valueOf(BaseFrame.Month), Integer.valueOf(BaseFrame.Day));
			
			BaseFrame.tfNum =  (String) com1.getSelectedItem();
			BaseFrame.tfNum2 = (String) com2.getSelectedItem();
			BaseFrame.date = String.valueOf(Datee);  

			wMessage("������ ���۵˴ϴ�.");
			
			mainFrame.changePanel(new LookUpPanel());
			
		}
	}

	private void getMonth() {
		
		int month = Integer.valueOf(date.format(DateTimeFormatter.ofPattern("MM")));
			
		com4.addItem("==�� ����==");
		
		System.out.println(String.valueOf(month));
		for (int i = month; i < month + 3; i++) {
		
			if(i > 12) {
				return; 
			}
			
			com4.addItem(String.format("%s" , String.valueOf(i))); // ��	
		}
		
	}
	private void getNumberInfo() {
		
		com1.removeAllItems();
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from tbl_bus");
		
			while (rs.next()) {
				com1.addItem(rs.getString(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getDays() {
		
		com5.removeAllItems();
		com5.addItem("==�� ����==");

		if(com4.getSelectedIndex() == 0 ) {
			return;
		}
	
		int month = (int) Integer.valueOf((String) com4.getSelectedItem());
		
		LocalDate Day = (LocalDate) LocalDate.of(date.getYear(), month, 1);
		
		for (int i = 1; i <= Day.lengthOfMonth(); i++) {
			
			com5.addItem(String.valueOf(i));
		}	
	}
}
