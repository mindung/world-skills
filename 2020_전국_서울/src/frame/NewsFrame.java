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
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class NewsFrame extends BaseFrame {

	private JLabel lbDate = createLb("날짜");

	private String sql;
	public String Title = ""; 
	private int index = 0;
	
	private JPanel pnlNews = new JPanel();
	public NewsFrame() {
		super("학교 소식", 400, 460);

		JPanel pnlFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlCenter = new JPanel();
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JScrollPane jsp = new JScrollPane(pnlNews);

		JLabel lbTitle = createLabel("학교소식");

		JLabel lbCnt = createLb("순번");
		JLabel lbName = createLb("제목");

		JButton btnExit = createButton("닫기");

		pnlFlow.add(lbCnt);
		pnlFlow.add(lbName);
		pnlFlow.add(lbDate);

		pnlSouth.add(btnExit);

		pnlFlow.setPreferredSize(new Dimension(380, 20));

		lbCnt.setPreferredSize(new Dimension(70, 20));
		lbName.setPreferredSize(new Dimension(200, 20));
		lbDate.setPreferredSize(new Dimension(70, 20));

		jsp.setPreferredSize(new Dimension(380, 310));
		btnExit.setPreferredSize(new Dimension(60, 30));

		pnlCenter.add(pnlFlow);
		pnlCenter.add(jsp);

		add(lbTitle, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnExit.addActionListener(e -> dispose());

		lbDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				lbDate.setForeground(lbDate.getBackground() == Color.white ? Color.white: Color.BLACK);
				lbDate.setBackground(lbDate.getForeground() == Color.white ? Color.black : Color.white );
				
				lbDate.setOpaque(true);
				getNews();
			}
		});
		
		lbName.addMouseListener(new MouseAdapter() {
			SearchTitleFrame frame = new SearchTitleFrame();

			public void mouseClicked(MouseEvent e) {

				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						Title = frame.tfTitle.getText();
						getNews();
						pnlNews.updateUI();
					}
				});

				frame.setVisible(true);
			}
		});

		getNews();
	}

	public static void main(String[] args) {
		new NewsFrame().setVisible(true);
	}

	private void getNews() {

		index = 0 ; // index  초기화
		
		sql = "SELECT * FROM reminder.news" + " left join notice on notice.n_no = news.n_no  where n_title like '%" + Title+ "%' "
				+ "order by nt_no desc, n_date";

		if (lbDate.getBackground() == Color.BLACK) {
			sql = sql + " desc";
		}
		
		pnlNews.removeAll();

		try {

			ResultSet rs = dbManager.executeQuery(sql);

			int cnt = 0;
			while (rs.next()) {

				addNews(rs.getInt(1), rs.getInt(5), rs.getString(2), rs.getString(3), rs.getString(4));
				cnt++;

			}
			
			pnlNews.setLayout(new GridLayout(cnt, 1));
			pnlNews.setPreferredSize(new Dimension(350, cnt * 75));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addNews(int n_no, int nt_no, String n_title, String n_content, String date) {
		
		String content = "<html>";
		
		for (int i = 0; i < n_content.length(); i++) {
			if (i != 0 && i % 25 == 0) {
				content += "<br/>";
			}
			
			content += n_content.substring(i, i + 1);
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
	
		JLabel lbTitle = new JLabel(n_title, JLabel.CENTER);
		JLabel lbContent = new JLabel(n_content, JLabel.CENTER);
		JLabel lbNo = new JLabel(String.valueOf(nt_no), JLabel.CENTER);
		JLabel lbDate = new JLabel("<html><center>등록일<br/>"+ date);
		
		pnlWest.add(lbNo);
		pnlCenter.add(lbTitle, BorderLayout.NORTH);
		pnlCenter.add(lbContent, BorderLayout.SOUTH);
		pnlEast.add(lbDate);
		
		pnl.setBackground(Color.WHITE);
		pnl.setOpaque(true);
		
		lbContent.setToolTipText(content);
		
		lbNo.setText(nt_no == 0 ? String.valueOf(index += 1) : "공지");
		
		lbNo.setBackground(nt_no == 0 ? Color.green : Color.BLACK);
		lbNo.setForeground(nt_no == 0 ? Color.BLACK : Color.WHITE);
		lbNo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbNo.setFont(new Font("HY견고딕", Font.BOLD, 15));
		lbNo.setOpaque(true);

		pnlNews.add(pnl);

		lbNo.setPreferredSize(new Dimension(45, 25));
		lbContent.setPreferredSize(new Dimension(80, 20));
		pnl.setPreferredSize(new Dimension(350, 70));

		pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		Color color = new Color(135, 206, 250);
		
		pnl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new NewsInfoFrame(n_no).setVisible(true);
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
