package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends BasePanel {

	private JPanel pnlCWest = new JPanel(new GridLayout(7, 1, 0, 5));
	private JPanel pnlCCenter = new JPanel(null);
	private JPanel pnlSCenter = new JPanel(new GridLayout(1, 5, 5, 5));
	
	private ArrayList<ImageIcon> ads = new ArrayList<ImageIcon>();
	
	private int index = 0;
	private JLabel lbAd[] = new JLabel[3];
	
	public MainPanel() {
		setLayout(new BorderLayout());

		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel(new BorderLayout());
		
		pnlCWest.setBackground(Color.BLUE);
		pnlCCenter.setBackground(Color.LIGHT_GRAY);
		
		pnlCenter.add(pnlCWest, BorderLayout.WEST);
		pnlCenter.add(pnlCCenter, BorderLayout.CENTER);
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		JLabel lb = createLabel("추천 상품");
		
		lb.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
		pnlSouth.add(lb, BorderLayout.NORTH);
		pnlSouth.add(pnlSCenter, BorderLayout.CENTER);
		
		pnlSCenter.setBackground(null);
		pnlSouth.setBackground(Color.WHITE);
		
		pnlCWest.setPreferredSize(new Dimension(205, 400));
		pnlCWest.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
		
		pnlSouth.setBorder(null);
		pnlSouth.setPreferredSize(new Dimension(900, 200));
		
		getCategory();
		getTop5();
		getAd();
		
		updateAd();
	}
	
	private void getCategory() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM category");
			
			while(rs.next()) {
				String name = rs.getString(2);
				JButton btn = createButton(name);
				
				btn.addActionListener(e -> {
					frame.changePanel(new SearchPage(name));
				});
				
				pnlCWest.add(btn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getTop5() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT product.name, sum(orderlist.quantity) AS qt, image.image FROM orderlist INNER JOIN product ON orderlist.product = product.serial INNER JOIN image ON product.thumb = image.serial GROUP BY product.name ORDER BY qt DESC LIMIT 5");
			
			while(rs.next()) {
				Blob blob = rs.getBlob(3);
				
				JPanel panel = new JPanel();
				JLabel lbImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(blob.getBytes(1, (int) blob.length())).getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
				
				panel.add(lbImage);
				panel.add(createLabel(rs.getString(1)));
				
				lbImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				panel.setBackground(null);
				
				pnlSCenter.add(panel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void getAd() {
		
		for (int i = 0; i < lbAd.length; i++) {
			lbAd[i] = new JLabel();
			lbAd[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			pnlCCenter.add(lbAd[i]);
			
			lbAd[i].setBounds(20 + (310 * i) + (20 * i), 25, 310, 310);
		}
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT ad.serial, image.image FROM ad INNER JOIN image ON image.serial = ad.image");
			
			while(rs.next()) {
				Blob blob = rs.getBlob(2);
				ads.add(new ImageIcon(Toolkit.getDefaultToolkit().createImage(blob.getBytes(1, (int) blob.length())).getScaledInstance(310, 310, Image.SCALE_SMOOTH)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateAd() {
		for (int i = index; i < index + 3; i++) {
			lbAd[i].setIcon(ads.get(i));
		}
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				while(lbAd[0].getX() > -310) {
					for (int i = 0; i < lbAd.length; i++) {
						lbAd[i].setBounds(lbAd[i].getX() - 1, lbAd[i].getY(), 310, 310);
					}
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}	
				
				index++;
				
				if (index == ads.size()) {
					index = 0;
				}
				
				for (int i = 0; i < 3; i++) {
					int n = index + i;
					
					if (n >= ads.size()) {
						n = n - ads.size();
					}
					
					lbAd[i].setIcon(ads.get(n));
				}
				
				for (int i = 0; i < lbAd.length; i++) {
					lbAd[i].setBounds(20 + (310 * i) + (20 * i), 25, 310, 310);
				}
			}
		};
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 3000, 3000);
	}
}
