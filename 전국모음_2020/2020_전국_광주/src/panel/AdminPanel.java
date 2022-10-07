package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import frame.MainFrame;

public class AdminPanel extends BasePanel{

	public AdminPanel(MainFrame frame) {
		this.frame = frame;
	
		JPanel pnlFeedback = new JPanel();
		JScrollPane jsp = new JScrollPane(pnlFeedback);
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from tbl_feedback where M_index = ?", BasePanel.m_index);
			
			int cnt = 0;
			while (rs.next()) {
				pnlFeedback.add(new Feedback(rs.getInt(1), rs.getString(3), rs.getInt(4), rs.getString(5)));
				cnt++;
			}
			
			pnlFeedback.setLayout(new GridLayout(cnt, 1));
			pnlFeedback.setPreferredSize(new Dimension(475, cnt * 150));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		add(jsp);
		jsp.setPreferredSize(new Dimension(500,450));
		
	}
	
	public class Feedback extends JPanel{
		public Feedback(int f_index, String f_text, int f_reed, String f_regdate) {
			
			setPreferredSize(new Dimension(475, 150));
			setLayout(new BorderLayout());
			
			JTextArea text = new JTextArea(f_text);
			JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JLabel lbReed = new JLabel(f_reed == 0 ? "안읽음": "읽음");
			
			text.setBorder(BorderFactory.createEtchedBorder());
			
			lbReed.setForeground(f_reed == 0? Color.red : Color.blue);
			
			pnlSouth.add(lbReed);
			pnlSouth.add(new JLabel(String.format("/ %s", f_regdate)));
			
			add(text,BorderLayout.CENTER);
			add(pnlSouth,BorderLayout.SOUTH);
			
			lbReed.addMouseListener(new MouseAdapter() {
 
			public void mouseClicked(MouseEvent e) {
				if(lbReed.getText().equals("안읽음")) {
					lbReed.setText("읽음");
					lbReed.setForeground(Color.blue);
					
					try {
						
						dbManager.executeUpdate("update tbl_feedback set f_read = 1 where f_index = ?", f_index);
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			});
			
		}
	}
}
