package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import panel.AdminPagePanel;
import panel.BasePanel;
import panel.LoginPanel;
import panel.MainPanel;
import panel.MyPagePanel;

public class MainFrame extends BaseFrame {

	private JLabel lbIcon = new JLabel();
	
	private JLabel lbLogin = new JLabel("로그인/회원가입", JLabel.CENTER);
	private JLabel lbLogout = new JLabel("로그아웃", JLabel.CENTER);
	private JLabel lbMyPage = new JLabel("마이페이지", JLabel.CENTER);
	
	private JPanel pnlCenter = new JPanel(new BorderLayout());
	
	public MainFrame() {
		super("메인", 900, 650);
		
//		new ImageIcon(Toolkit.getDefaultToolkit().getImage("./지급자료/images/1.png").getScaledInstance(150, 40, Image.SCALE_SMOOTH))
		
		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNCenter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel pnlNSouth = new JPanel();
		
		pnlNCenter.add(lbLogin);
		pnlNCenter.add(lbLogout);
		pnlNCenter.add(lbMyPage);
		
		pnlNorth.setBackground(Color.WHITE);
		pnlNCenter.setBackground(Color.WHITE);
		pnlNSouth.setBackground(Color.BLACK);
		
		pnlNorth.add(lbIcon, BorderLayout.WEST);
		pnlNorth.add(pnlNCenter, BorderLayout.CENTER);
		pnlNorth.add(pnlNSouth, BorderLayout.SOUTH);
		
		lbLogin.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lbLogout.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lbMyPage.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		
		lbLogin.setPreferredSize(new Dimension(110, 40));
		lbLogout.setPreferredSize(new Dimension(80, 40));
		lbMyPage.setPreferredSize(new Dimension(80, 40));
		
		pnlNSouth.setPreferredSize(new Dimension(900, 1));
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		
		lbIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changePanel(new MainPanel());
			}
		});
		
		lbLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changePanel(new LoginPanel());
			}
		});
		
		lbLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BasePanel.sellerSerial = 0;
				BasePanel.userSerial = 0;
				
				updateSession();
			}
		});
		
		lbMyPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (BasePanel.sellerSerial != 0) {
					changePanel(new AdminPagePanel());
				} else if (BasePanel.userSerial != 0) {
					changePanel(new MyPagePanel());
				} else {
					eMessage("로그인 후 이용하실 수 있습니다.");
				}
			}
		});
		
		getImage();
		updateSession();
		changePanel(new MainPanel());
	}
	
	private void getImage() {
		try {
			ResultSet rs = BasePanel.dbManager.executeQuery("SELECT * FROM image WHERE serial = 1");

			if (rs.next()) {
				Blob blob = rs.getBlob(3);
				Image img = Toolkit.getDefaultToolkit().createImage(blob.getBytes(1, (int) blob.length())).getScaledInstance(150, 40, Image.SCALE_SMOOTH);
				lbIcon.setIcon(new ImageIcon(img));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		BasePanel.frame.setVisible(true);
	}
	
	public void changePanel(BasePanel panel) {
		pnlCenter.removeAll();
		pnlCenter.add(panel, BorderLayout.CENTER);
		pnlCenter.updateUI();
	}
	
	public void updateSession() {
		lbLogin.setVisible(BasePanel.sellerSerial == 0 && BasePanel.userSerial == 0);
		lbLogout.setVisible(BasePanel.sellerSerial != 0 || BasePanel.userSerial != 0);
		lbMyPage.setText(BasePanel.sellerSerial != 0 ? "관리페이지" : "마이페이지");
	}

}
