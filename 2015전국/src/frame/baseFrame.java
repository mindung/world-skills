package frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jdbc.dbManager;

public class baseFrame extends JFrame{
	
	public static dbManager dbManager = new dbManager();
	public static String Jumin = "" ;
	public static String name = "" ;
	public static String rent = "";	 
	public static String state = "";
	public baseFrame(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.ERROR_MESSAGE);
	}
	
}
