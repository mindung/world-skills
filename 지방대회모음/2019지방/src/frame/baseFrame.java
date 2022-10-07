package frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.dbManager;

public class baseFrame extends JFrame{

	public static dbManager dbmanager = new dbManager();
	
	public static String u_name = "";
	public static String u_grade = "";
	public static Integer u_point = 0;
	public static Integer u_no = 1;
	
	protected DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	
	public baseFrame(String title, int width, int height) {
	 	setTitle(title);
	 	setSize(width, height);
	 	setLocationRelativeTo(null);
	 	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	 	centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	
	protected void Emessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.ERROR_MESSAGE);
	}
	
	protected void Imessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {

	}

}
