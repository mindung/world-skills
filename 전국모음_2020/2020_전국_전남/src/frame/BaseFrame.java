package frame;

import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jdbc.dbManager;

public class BaseFrame extends JFrame{
	
	public static dbManager dbManager = new dbManager();
	
	public static boolean isStart;
	public static LocalDate SDate;
	public static LocalDate FDate;

	public BaseFrame(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	
	protected void IMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.ERROR_MESSAGE);
	}
	
}
