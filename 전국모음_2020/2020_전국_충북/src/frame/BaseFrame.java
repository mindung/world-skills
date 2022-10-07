package frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BaseFrame extends JFrame {
	
	public BaseFrame(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "메시지", JOptionPane.ERROR_MESSAGE);
	}
}
