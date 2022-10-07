package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CommunityFrame extends BaseFrame {
	
	private JPanel pnlCommunity = new JPanel();
	private JComboBox<String> comGrade = new JComboBox<String>(new String[] { "전체", "통신문", "잡담" });

	public CommunityFrame() {
		
		super("대나무숲", 390, 460);
//		
		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNS = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel();
		
		JLabel lbTitle = createLabel("대나무숲");
		
		JScrollPane jsp = new JScrollPane(pnlCommunity, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JButton btnExit = createButton("닫기");
		JButton btnWrite = createButton("글쓰기");
		
		pnlNS.add(comGrade);
		
		pnlSouth.add(btnWrite);
		pnlSouth.add(btnExit);
		
		pnlNorth.add(lbTitle, BorderLayout.NORTH);
		pnlNorth.add(pnlNS,BorderLayout.SOUTH);
		pnlCenter.add(jsp);

		add(pnlSouth, BorderLayout.SOUTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlNorth, BorderLayout.NORTH);
		
		jsp.setPreferredSize(new Dimension(370, 310));
		btnWrite.setPreferredSize(new Dimension(70, 30));
		btnExit.setPreferredSize(new Dimension(60, 30));
		
		btnExit.addActionListener(e -> dispose());
		
		getCommunity();
		
		comGrade.addItemListener(e -> getCommunity());
		
		btnWrite.addActionListener(e -> {
			CommunityInsertFrame frame = new CommunityInsertFrame();
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						getCommunity();
					}
				});
				frame.setVisible(true);
		});
	}
	
	public static void main(String[] args) {
		new CommunityFrame().setVisible(true);
	}
	
	private void getCommunity() {
	
		pnlCommunity.removeAll();

		int grade = comGrade.getSelectedIndex();
		String g = "";

		g = grade == 1 ? "1" : (grade == 2 ? "2" : "");

		String sql = "SELECT c.*,member.m_name,count(co_content) as cnt FROM reminder.community as c "
				+ "left join member on c.m_no = member.m_no " + "left join comment on comment.c_no = c.c_no "
				+ "where classification like '%" + g + "%'  group by c.c_no ";

		int cnt = 0;

		try {

			ResultSet rs = dbManager.executeQuery(sql);

			while (rs.next()) {
				addCommunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(5), rs.getString(7), rs.getInt(8));
				cnt++;
			}

			pnlCommunity.setLayout(new GridLayout(cnt, 1));
 			pnlCommunity.setPreferredSize(new Dimension(350, cnt * 75));
			pnlCommunity.updateUI();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private void addCommunity(int c_no,String c_title, String c_contents, int anonymous,String m_name, int cnt) {

		JPanel pnl = new JPanel(new BorderLayout());

		m_name = anonymous == 1 ? m_name : "익명";
		
		JLabel lbPeo = new JLabel(m_name, JLabel.CENTER);
		JLabel lbTitle = new JLabel("    " + c_title, JLabel.LEFT);
		JLabel lbcomment = new JLabel(String.format("댓글수 : %d개", cnt), JLabel.LEFT);

		pnl.add(lbPeo, BorderLayout.WEST);
		pnl.add(lbTitle, BorderLayout.CENTER);
		pnl.add(lbcomment, BorderLayout.EAST);
		
		pnl.setBackground(Color.WHITE);
		pnl.setOpaque(true);

		pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		pnlCommunity.add(pnl);
		
		lbTitle.setToolTipText("<html>" + c_title + "<br/>"+ c_contents);
		
		lbcomment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CommentFrame frame = new CommentFrame(c_no, c_title);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						getCommunity();
					}
				});
				
				frame.setVisible(true);
			}
	
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
		});

	}
}
