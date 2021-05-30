package frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.dbManager;

public class BaseFrame extends JFrame{
	
	 public dbManager dbManager = new dbManager();
	 
	 public static String code = "";
	 public static String Tel = "";
	 
	 public DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
	 
	public BaseFrame(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		CenterRenderer.setHorizontalAlignment(CenterRenderer.CENTER);
	}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	
	protected void iMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}


	protected void eMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	
}
