package frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.dbManager;
import panel.MainPanel;

public class BaseFrame extends JFrame{
	
	public static final dbManager dbManager = new dbManager();
	
	public DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	
	public BaseFrame(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
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
