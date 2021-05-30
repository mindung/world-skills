package frame;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import jdbc.DbManager;

public class BaseFrame extends JFrame{

	public static DbManager dbManager = new DbManager();
	
	public static String Info = "";
	public static String p_Code = "T011";
	public static String S_Code = "170001";
	
	public static DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	public BaseFrame(String title, int w, int h) {
		setTitle(title);
		setSize(w, h);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);	
		centerRenderer.setHorizontalAlignment(centerRenderer.CENTER);
	}
	
	protected void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	
	protected void iMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "확인", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void eMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "오류", JOptionPane.WARNING_MESSAGE);
	}

	public JComponent addC(JComponent com, int x, int y, int w, int h ) {
		com.setBounds(x, y, w, h);
		add(com);
		return com;
	}
	
	public  static void fileCopy(String input, String outPut) {
		try {

			FileInputStream in = new FileInputStream(input);
			FileOutputStream out= new FileOutputStream(outPut);

			int data = 0;
			
				try {
					while ((data=in.read())!= -1) {
						out.write(data);
					}
				
					in.close();
					out.close();
			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
	
	protected JButton createButton(String text) {
		JButton btn = new JButton(text);
		btn.setBackground(Color.WHITE);
		return btn;
	}
}
