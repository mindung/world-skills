package frame;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jdbc.dbManager;
import model.Wedding;

public class BaseFrame extends JFrame{
	
	public static dbManager dbManager = new dbManager();
	public static String selecteDate = "";
	public static int selectedLetter = 0;
	public static int W_no = 0;
	
	
	public BaseFrame(String title, int w, int h) {
		setTitle(title);
		setSize(w, h);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "확인", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "오류", JOptionPane.ERROR_MESSAGE);
	}
	
	protected JComponent addC(JComponent com, int x, int y, int w, int h ) {
		com.setBounds(x, y, w, h);
		add(com);
		return com;
		
	}

	protected ImageIcon getImageIcon(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path));
	}
	
	protected ImageIcon getImageIcon(String path, int width, int height) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}
}
