package panel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import frame.MainFrame;
import jdbc.dbManager;

public class BasePanel extends JPanel{
	
	public static dbManager dbManager = new dbManager();
	
	public static final MainFrame mainFrame = new MainFrame();
	public static MainPanel mainPanel = new MainPanel();
	
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
