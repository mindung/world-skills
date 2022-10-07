package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CommentFrame extends BaseFrame{
	
	private int c_no;
	private String c_title;
	
	private JTextField tfComment = new JTextField(20);
	
	private JPanel pnlComment = new JPanel();
	
	public CommentFrame(int c_no, String c_title) {
		super(String.format("%s 글의 댓글", c_title), 450, 300);
		
		this.c_no = c_no;
		this.c_title = c_title;
		
		JScrollPane jsp = new JScrollPane(pnlComment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel pnlSN = new JPanel();
		JPanel pnlSS = new JPanel();
		JPanel pnlS = new JPanel(new BorderLayout());
		
		JButton btnEnter = createButton("입력");
		JButton btnExit  = createButton("닫기");
		
		pnlSN.add(tfComment);
		pnlSN.add(btnEnter);
	
		pnlSS.add(btnExit);
		
		pnlS.add(pnlSN,BorderLayout.NORTH);
		pnlS.add(pnlSS, BorderLayout.SOUTH);
//		
		add(jsp, BorderLayout.CENTER);
		add(pnlS, BorderLayout.SOUTH);
		
		getInfo();
		
		btnEnter.addActionListener(e -> commentInsert());
		btnExit.addActionListener(e -> dispose());
		jsp.setPreferredSize(new Dimension(440, 300));
	}


	public static void main(String[] args) {
		new CommentFrame(1,"다중이용시설 관련 안내").setVisible(true);
	}
	
	private void commentInsert() {
		String comment = tfComment.getText();
		
		if (comment.isEmpty()) {
			eMessage("댓글을 입력해주세요.");
		}else {
			
			try {
				dbManager.executeUpdate("insert into comment value(0, ?, ? ,?)", comment, c_no, m_no);
				tfComment.setText("");
				getInfo();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	
	private void getInfo() {
		
		pnlComment.removeAll();
		
		int cnt = 0;
		try {
			ResultSet rs = dbManager.executeQuery("SELECT co_content, m_name FROM comment inner join member on member.m_no = comment.m_no where c_no = ?", c_no);
			
			while (rs.next()) {
				
				addComment(rs.getString(1), rs.getString(2));
				cnt++;
			}
			
			pnlComment.setPreferredSize(new Dimension(410,cnt * 100));
//			pnlComment.setLayout(new GridLayout(cnt  , 1));
			
			pnlComment.updateUI();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addComment(String m_name,String c_contents) {

		JPanel pnl = new JPanel(new GridLayout(2, 1));

		JLabel lbPeo = new JLabel(m_name, JLabel.LEFT);
		JLabel lbContents = new JLabel(c_contents, JLabel.LEFT);

		pnl.add(lbContents);
		pnl.add(lbPeo);
		
		pnl.setBackground(Color.WHITE);
		pnl.setOpaque(true);

		pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnl.setPreferredSize(new Dimension(410, 70));

		pnlComment.add(pnl);
		
		
	}
}

