package panel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import frame.MainFrame;
import jdbc.dbManager;

public class BasePanel extends JPanel{
	
	public static MainPanel mainPanel = new MainPanel();
	public static MainFrame frame = new MainFrame();

	public dbManager dbManager = new dbManager();
	
	public static int m_index = 0;
	public static int d_index = 0;
	public static String d_date = "null";
	

	//public static MainPanel mainPanel = null;
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.ERROR_MESSAGE);
	}
	
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
}
