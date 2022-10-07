package frame;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.dbManager;

public class InsertProjectFrame extends BaseFrame{

	private JTextField tfName = new JTextField();
	private JTextField tfCare = new JTextField();
	private JTextField tfSDate = new JTextField();
	private JTextField tfFDate = new JTextField();
	private JTextField tfNote = new JTextField();
	
	public InsertProjectFrame() {
		super("»õ ÇÁ·ÎÁ§Æ®", 650, 370);
		
		setLayout(new BorderLayout());
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();
		
		JLabel lbName = new JLabel("ÇÁ·ÎÁ§Æ® ÀÌ¸§ :");
		JLabel lbCare = new JLabel("°ü¸®ÀÚ :");
		JLabel lbSDay = new JLabel("½ÃÀÛÀÏ :");
		JLabel lbFDay= new JLabel("Á¾·áÀÏ : ");
		JLabel lbNote = new JLabel("³ëÆ® :");
		
		JButton btnOk = new JButton("È®ÀÎ");
		JButton btnExit = new JButton("Ãë¼Ò");
		
		pnlCenter.add(lbName);
		pnlCenter.add(lbCare);
		pnlCenter.add(lbSDay);
		pnlCenter.add(lbFDay);
		pnlCenter.add(lbNote);
		pnlCenter.add(tfName);
		pnlCenter.add(tfCare);
		pnlCenter.add(tfSDate);
		pnlCenter.add(tfFDate);
		pnlCenter.add(tfNote);
		
		pnlSouth.add(btnOk);
		pnlSouth.add(btnExit);		
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		lbName.setBounds(30, 10, 100, 30);
		lbCare.setBounds(30, 60, 50, 30);
		lbSDay.setBounds(30, 110, 50, 30);
		lbFDay.setBounds(340, 110, 50, 30);
		lbNote .setBounds(30, 160, 50, 30);
		
		tfName.setBounds(150, 10, 450, 30);
		tfCare.setBounds(150, 60, 450, 30);
		tfSDate.setBounds(150, 110, 160, 30);
		tfFDate.setBounds(440, 110,160, 30);
		tfNote.setBounds(30, 190, 570, 80);

		btnOk.addActionListener(e -> insertProject());
		btnExit.addActionListener(e -> dispose());
		
		tfCare.setText("°ü¸®ÀÚ");
		tfCare.setEnabled(false);
		
		tfSDate.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				isStart = true;
				
				CalendarFrame frame = new CalendarFrame();
				
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						
						tfSDate.setText(SDate.format(DateTimeFormatter.ofPattern("yyyy. MM. dd")));
					};
				});
				frame.setVisible(true);
			};
		
		});

		tfFDate.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				isStart = false;
				
				CalendarFrame frame = new CalendarFrame();
				
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						tfFDate.setText(FDate.format(DateTimeFormatter.ofPattern("yyyy. MM. dd")));
					};
				});
				frame.setVisible(true);
			};
		});
		
	}
	
	public static void main(String[] args) {
		new InsertProjectFrame().setVisible(true);
	}
	
	private void insertProject() {	
		String name = tfName.getText();
		String care = tfCare.getText();
		String sDate = tfSDate.getText();
		String fDate = tfFDate.getText();
		String note = tfNote.getText();
		
		
		if(name.isEmpty() || care.isEmpty() || sDate.isEmpty() || fDate.isEmpty()) {
			eMessage("°ø¹éÀÌ Á¸ÀçÇÕ´Ï´Ù.");
		}else {
			
//			if(name.matches(".*[*¤¡-¤¾°¡-ÆR])+.*")) {
				if(name.matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*")) {
				//.*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*
				try {		
				IMessage("ÇÁ·ÎÁ§Æ®°¡ »ý¼ºµÇ¾ú½À´Ï´Ù.");
					dbManager.executeUpdate("insert into project values(0,?,?,?,?,?)", name,care,BaseFrame.SDate,BaseFrame.FDate,note);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				eMessage("ÇÁ·ÎÁ§Æ® ÀÌ¸§Àº ÇÑ±Û·Î ÀÔ·ÂÇØÁÖ¼¼¿ä.");
			}
		}

	}
}

