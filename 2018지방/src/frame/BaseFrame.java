package frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.dbManager;

public class BaseFrame extends JFrame{
	
	public static dbManager dbManager = new dbManager();
	
	public static int maxNo = 0;
	
	public DefaultTableCellRenderer centerRenderer = new  DefaultTableCellRenderer();
			
	public BaseFrame(String title, int width, int height) {
			setTitle(title);
			setSize(width, height);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			centerRenderer.setHorizontalAlignment(centerRenderer.CENTER);
			
		}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
}
