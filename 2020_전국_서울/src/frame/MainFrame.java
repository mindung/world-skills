package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame implements Runnable{

	private JLabel lbInfo = new JLabel(String.valueOf(m_no));
	private JLabel lbImage = new JLabel("");
	private JButton btnLogin = createButton("로그인");
	
	public MainFrame() {
		super("MAIN", 450, 470);
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter = new JPanel(new FlowLayout());
		JPanel pnlCNorth = new JPanel(new BorderLayout());
		JPanel pnlCSouth = new JPanel(new GridLayout(1, 4, 6, 0));

		JLabel lbTitle = createLabel("학교 알리미");

		JLabel lbImg = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./2과제 지급자료/image/학교.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

		JButton btnNews = createButton("소식");
		JButton btnReply = createButton("회신");
		JButton btnCommunity = createButton("대나무숲");
		JButton btnExit = createButton("종료");

		pnlNorth.add(lbImg);
		pnlNorth.add(lbTitle);

		pnlCNorth.add(btnLogin, BorderLayout.EAST);
		pnlCNorth.add(lbInfo, BorderLayout.WEST);

		pnlCSouth.add(btnNews);
		pnlCSouth.add(btnReply);
		pnlCSouth.add(btnCommunity);
		pnlCSouth.add(btnExit);

		pnlCenter.add(pnlCNorth, BorderLayout.NORTH);
		pnlCenter.add(pnlCSouth, BorderLayout.CENTER);
		pnlCenter.add(lbImage, BorderLayout.CENTER);

		pnlCNorth.setPreferredSize(new Dimension(430, 25));
		btnLogin.setPreferredSize(new Dimension(55, 25));
		pnlCSouth.setPreferredSize(new Dimension(430, 30));
		lbImage.setPreferredSize(new Dimension(430, 300));

		lbImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);

		btnLogin.addActionListener(e -> {

			if(btnLogin.getText().equals("로그아웃")) {
				m_no = 0;
				LogInfo();
				return;
			}
			
			LoginFrame frame = new LoginFrame();
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					LogInfo();
				}
			});
			
			frame.setVisible(true);
		});
		
		btnNews.addActionListener(e -> openFrame(new NewsFrame()));
		btnReply.addActionListener(e -> openFrame(new ReplyFrame()));
		btnCommunity.addActionListener(e -> openFrame(new CommunityFrame()));
		btnExit.addActionListener(e -> System.exit(0));
		
		LogInfo();
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
	
	protected void openFrame(JFrame frame) {
		
		if (m_no == 0) {
			eMessage("로그인을 해주세요.");
		}else {
			frame.setVisible(true);
		}
	}
	
	public void LogInfo() {

		String name = "";

		try {
			ResultSet rs = dbManager.executeQuery("select * from member where m_no =?", m_no);
			if (rs.next()) {
				name = rs.getString(4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		lbInfo.setText(m_no == 0 ? "로그인을 해주세요." : String.format("%s님 환영합니다.", name));
		btnLogin.setText(m_no == 0 ? "로그인" : "로그아웃");

		lbInfo.updateUI();
		btnLogin.updateUI();

	}

	@Override
	public void run() {

		while (true) {

			for (int i = 1; i <= 3; i++) {

				try {
					lbImage.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("./2과제 지급자료/image/main/%d.jpg", i)).getScaledInstance(430, 300, Image.SCALE_SMOOTH)));
//					System.out.println(i);
					Thread.sleep(3000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}



