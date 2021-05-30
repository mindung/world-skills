package panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import frame.MainFrame;
import jdbc.DBManager;

public class BasePanel extends JPanel {

	public static final DBManager dbManager = new DBManager();
	public static final MainFrame frame = new MainFrame();
	
	public static int userSerial = 1;
	public static int sellerSerial = 0;
	
	public DefaultTableCellRenderer centerCR = new DefaultTableCellRenderer();
	
	public BasePanel() {
		setBackground(Color.WHITE);
		
		centerCR.setHorizontalAlignment(JLabel.CENTER);
	}
	
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "¸Þ½ÃÁö", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "¸Þ½ÃÁö", JOptionPane.ERROR_MESSAGE);
	}
	
	protected JButton createButton(String text) {
		JButton btn = new JButton(text);
		
		btn.setBackground(Color.WHITE);
		btn.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		return btn;
	}
	
	protected JLabel createLabel(String text) {
		JLabel lb = new JLabel(text);
		
		lb.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		
		return lb;
	}
	
	protected <T extends JComponent> T createComponent(T comp) {
		comp.setBackground(Color.WHITE);
		comp.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		
		return comp;
	}
}
