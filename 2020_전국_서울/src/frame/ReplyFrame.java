package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ReplyFrame extends BaseFrame {

	
	private JPanel pnlReply = new JPanel();

	private String sql;
	
	private String grade;
	
	private JComboBox<String> comGrade = new JComboBox<String>(new String[] { "전체", "제출", "미제출" });
	
	public ReplyFrame() {
		super("회신문서",  400, 460);
		
		JPanel pnlFlow = new JPanel();
		JPanel pnlCenter = new JPanel();
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JScrollPane jsp = new JScrollPane(pnlReply);

		JLabel lbTitle = createLabel("학교소식");

		JLabel lbCnt = createLb("순번");
		JLabel lbName = createLb("제목");

		JButton btnExit = createButton("닫기");

		pnlFlow.add(lbCnt);
		pnlFlow.add(lbName);
		pnlFlow.add(comGrade);

		pnlSouth.add(btnExit);

		pnlFlow.setPreferredSize(new Dimension(380, 20));

		lbCnt.setPreferredSize(new Dimension(70, 25));
		lbName.setPreferredSize(new Dimension(210, 25));
		comGrade.setPreferredSize(new Dimension(70, 25));

		jsp.setPreferredSize(new Dimension(380, 310));
		btnExit.setPreferredSize(new Dimension(60, 30));

		pnlCenter.add(pnlFlow);
		pnlCenter.add(jsp);

		add(lbTitle, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnExit.addActionListener(e -> dispose());
		comGrade.addItemListener(e -> getReply());
		
		getReply();
	}
	
	public static void main(String[] args) {
		new ReplyFrame().setVisible(true);
	}

	private void getReply() {
		
		System.err.println("실행ㅂ중");
		int grade = comGrade.getSelectedIndex();

		pnlReply.removeAll();

		try {

			ResultSet rs = dbManager.executeQuery("select * from reply "); 
			//where r_sday <= curdate() and r_eday >= curdate()

			int cnt = 0;
			
			while (rs.next()) {
				int r_no = rs.getInt(1);
				
				ResultSet rs2 = dbManager.executeQuery("select * from reply_result where m_no = ? and r_no = ?", m_no, r_no);
				
				if (grade == 0) {
					addReply(cnt, rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs2.next() ? "제출" : "미제출");
					
				} else if (grade == 1) {
					if (rs2.next()) {
						addReply(cnt, rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), "제출");		
					}
				} else if (grade == 2) {
					if (rs2.next() == false) {
						addReply(cnt, rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), "미제출");		
					}
				}
				
				System.out.println(grade);
				cnt++;
			}
			
			pnlReply.setLayout(new GridLayout(cnt, 1));
			pnlReply.setPreferredSize(new Dimension(350, cnt * 75));
			pnlReply.updateUI();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addReply(int index, String r_title,String r_content, String r_sday, String r_eday, String submit) {

		String content = "<html>";
		
		Color color = new Color(135, 206, 250);
		
		for (int i = 0; i < r_content.length(); i++) {
			if (i != 0 && i % 25 == 0) {
				content += "<br/>";
			}
			
			content += r_content.substring(i, i + 1);
		}
		
		content += "</html>";
		
		JPanel pnl = new JPanel(new BorderLayout());
		
		JPanel pnlWest = new JPanel();
		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlEast = new JPanel();
		
		pnlWest.setOpaque(true);
		pnlCenter.setOpaque(true);
		pnlEast.setOpaque(true);
		
		pnlWest.setBackground(null);
		pnlCenter.setBackground(null);
		pnlEast.setBackground(null);
		
		pnlWest.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		pnlEast.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
		
		pnl.add(pnlWest, BorderLayout.WEST);
		pnl.add(pnlCenter, BorderLayout.CENTER);
		pnl.add(pnlEast, BorderLayout.EAST);
	
		JLabel lbTitle = new JLabel(r_title, JLabel.CENTER);
		JLabel lbDate = new JLabel(String.format("%s ~ %s", r_sday, r_eday), JLabel.CENTER);
		JLabel lbNo = new JLabel(String.valueOf(index + 1), JLabel.CENTER);
		JLabel lbSubmit = new JLabel(submit);
		
		pnlWest.add(lbNo);
		pnlCenter.add(lbTitle, BorderLayout.NORTH);
		pnlCenter.add(lbDate, BorderLayout.SOUTH);
		pnlEast.add(lbSubmit);
		
		pnl.setBackground(Color.WHITE);
		pnl.setOpaque(true);
		
		lbDate.setToolTipText(content);
		
		lbNo.setBackground(color);
		
		lbNo.setForeground(submit.equals("제출") ? Color.BLACK : Color.RED);
		lbSubmit.setForeground(submit.equals("제출") ? Color.BLACK : Color.RED);
		lbNo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbNo.setFont(new Font("HY견고딕", Font.BOLD, 15));
		lbNo.setOpaque(true);

		pnlReply.add(pnl);

		lbNo.setPreferredSize(new Dimension(45, 25));
		lbDate.setPreferredSize(new Dimension(80, 20));
		pnl.setPreferredSize(new Dimension(350, 70));

		pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		lbDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				pnl.setBackground(color);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				pnl.setBackground(Color.white);
			}
		});
		
		pnl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ReplyInfoFrame(index+1, submit).setVisible(true);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				pnl.setBackground(color);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				pnl.setBackground(Color.white);
			}
		});

	}
}
