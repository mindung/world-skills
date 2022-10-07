package frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.dbManager;

public class BaseFrame extends JFrame{

	public static dbManager dbManager = new dbManager();
	
	public static Integer p_no = 1;
	public static String p_name = "";
	public static String d_name = "";
	public static String selectedDate = "";
	public static String selectedBed= "";
	
	
	protected DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	
	public BaseFrame(String title, int w, int h) {
		setTitle(title);
		setSize(w, h);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		centerRenderer.setHorizontalAlignment(centerRenderer.CENTER);
		
	}
	
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.ERROR_MESSAGE);
	}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
}
