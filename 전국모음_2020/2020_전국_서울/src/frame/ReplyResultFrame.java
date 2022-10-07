
package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class ReplyResultFrame extends BaseFrame{
	private JTextArea tfTerms = new  JTextArea();
	
	public ReplyResultFrame(int index) {
		super("동의약관", 400, 200);
		
		JPanel pnlS = new JPanel();
		
		JLabel lbTitle = new JLabel("이용약관 동의",JLabel.CENTER);
		JButton btnOk = createButton("동의합니다.");
		
		lbTitle.setFont(new Font("", Font.BOLD, 15));
		
		btnOk.setPreferredSize(new Dimension(80, 30));
		JScrollPane jsp = new JScrollPane(tfTerms); 
		pnlS.add(btnOk);
		
		add(lbTitle, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		add(pnlS,BorderLayout.SOUTH);
		
		String r_terms = "";
		String terms = "";
		
		btnOk.setEnabled(false);
		
		btnOk.addActionListener(e -> {
			iMessage("동의하였습니다.");
			dispose();	
		});
		
		jsp.getVerticalScrollBar().setAlignmentY(0);
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from reply where r_no = ?", index);
			if(rs.next()) {
				r_terms = rs.getString(6);
			}
			
			for (int i = 0; i < r_terms.length(); i++) {
				
				if (i != 0 && i % 25 == 0) {
					terms += "\r\n";
				}
				
				terms += r_terms.substring(i, i + 1);
				
				tfTerms.setText(terms);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		jsp.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDragged(MouseEvent e) {
//				System.out.println("드래그");
//				if (jsp.getVerticalScrollBar().getValue() >= jsp.getVerticalScrollBar().getHeight() - 10) {
//					btnOk.setEnabled(true);
//				}
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				System.out.println("클릭");
//			}
//		});
		
		jsp.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (jsp.getVerticalScrollBar().getValue() >= jsp.getVerticalScrollBar().getHeight()) {
					btnOk.setEnabled(true);
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new ReplyResultFrame(2).setVisible(true);
	}

}

