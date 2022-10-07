package frame;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jdbc.DbManager;

public class BaseFrame extends JFrame {
    
	public static DbManager dbManager = new DbManager();
	public static String selecteDate = "";
	public static int selectedLetter = 0;
	public static int W_no = 0;
	
	public static int u_no = 1;
	
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
	protected void openFrame(JFrame frame, JFrame frame1) {
		dispose();
		frame.setVisible(true);
		
		if (frame1 == new LoginFrame()) {
			System.exit(0);
		}
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame1.setVisible(true);
			}
		});
	}
    
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "정보", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "경고", JOptionPane.ERROR_MESSAGE);
	}
	
	protected <T extends JComponent> T addC(T com, int w, int h ) {
		com.setPreferredSize(new Dimension(w, h));
		return com;
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
