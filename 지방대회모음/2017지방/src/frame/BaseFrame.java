package frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.dbManager;

public class BaseFrame extends JFrame{
	
	public static dbManager dbManager = new dbManager();
	
	public static String login = "";
	
	public static String Start = "";
	public static String End = "";
	
	public static String Year = "";
	public static String Month = "";
	public static String Day = "";
	
	public static String date = "";
	
	public static String tfNum = "";
	public static String tfNum2 = "";
	
	public static  DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	
	public BaseFrame(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		centerRenderer.setHorizontalAlignment(centerRenderer.CENTER);
	}
	
	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	
	public void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public  void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.ERROR_MESSAGE);
	}
	
	public void wMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "웹페이지 메시지", JOptionPane.WARNING_MESSAGE);
		
	}
	
}
