package frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.DbManager;

public class BaseFrame extends JFrame{
	
	public static String u_name = "";
	public static int u_no = 1;
	public static String p_group ="";
	
	public DbManager dbManager = new DbManager();
	
	public DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	
	public BaseFrame(String title, int width, int height) {
		
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		
	}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
		
	}
	
	protected void openFrame(JFrame frame, JFrame frame1) {
		dispose();
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame1.setVisible(true);
			}
		});
	}
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
