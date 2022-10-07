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
		JOptionPane.showMessageDialog(null, message, "�޽���", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public  void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "�޽���", JOptionPane.ERROR_MESSAGE);
	}
	
	public void wMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "�������� �޽���", JOptionPane.WARNING_MESSAGE);
		
	}
	
}
