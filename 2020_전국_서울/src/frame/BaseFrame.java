package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.DbManager;

public class BaseFrame extends JFrame {
	
	
	public static DbManager dbManager = new DbManager();
	public static DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

	public static String SelectedStart = "";
//	public static LocalDate Start = LocalDate.now();
	public static String SelectedEnd = "";
			
	public static int m_no = 2;

	public static MainFrame frame = new MainFrame();

		public BaseFrame(String title, int w, int h) {
			setTitle(title);
			setSize(w, h);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			centerRenderer.setHorizontalAlignment(centerRenderer.CENTER);
			this.setBackground(Color.WHITE);
			
		}
	
	
	protected JButton createButton(String str) {
		JButton btn = new JButton(str);
		Color color = new Color(80, 208, 250);
		btn.setBackground(color);
		btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btn.setPreferredSize(new Dimension(60, 30));
		return btn;

	}
	
	

	protected JPanel panel(JPanel pnl) {
		JPanel panel = pnl;
		pnl.setBackground(Color.WHITE);
		pnl.setOpaque(true);
		return panel;
	}
	
	protected JLabel createLabel(String str) {
		JLabel lb = new JLabel(str, JLabel.CENTER);
		lb.setFont(new Font("HY견고딕", Font.BOLD, 25));
		lb.setBackground(Color.WHITE);
		lb.setOpaque(true);
		return lb;
	}
	
	protected JLabel createLbImg(Color color) {
		JLabel lbImg = new JLabel();
		lbImg.setPreferredSize(new Dimension(140, 80));
		lbImg.setBorder(BorderFactory.createLineBorder(Color.black));
		lbImg.setBackground(color);
		lbImg.setOpaque(true);
		return lbImg;
	}
	
	protected JLabel createLb(String str) {
		JLabel lb = new JLabel(str, JLabel.CENTER);
		lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lb.setBackground(Color.WHITE);
		lb.setOpaque(true);
		return lb;
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
	
	protected JComponent addC(JComponent com, int x, int y, int w, int h) {
		com.setBounds(x, y, w, h);
		add(com);
		return com;
		
	}

//	public class panel extends JPanel{
//		public panel() {
//			setBackground(Color.WHITE);
//		}
//	}
	
}
