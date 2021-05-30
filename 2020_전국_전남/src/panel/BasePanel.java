package panel;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import frame.MainFrame;
import jdbc.dbManager;

public class BasePanel extends JPanel{
	
	public static final dbManager dbManager = new dbManager();
	public static final MainFrame frame = new MainFrame();

	public static int adminNo = 0;
	
	public static int project_no = 0;

	public BasePanel() {

		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}
	
	protected void IMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.ERROR_MESSAGE);
	}

}
