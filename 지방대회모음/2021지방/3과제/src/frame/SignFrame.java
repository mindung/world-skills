package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Wedding;

public class SignFrame extends BaseFrame {

	private BufferedImage image = new BufferedImage(200, 170, BufferedImage.TYPE_INT_RGB);

	private boolean mouse = false;

	private Wedding wedding;
	private int people;
	private int letter;
	private String date;
	
	public SignFrame(Wedding wedding, int people, int letter, String date) {
		super("서명", 202, 250);
		
		this.wedding = wedding;
		this.people = people;
		this.letter = letter;
		this.date = date;
		
		setLayout(null);

		Graphics2D graphics = image.createGraphics();
		graphics.setPaint(Color.WHITE);
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

		JButton btnOk = new JButton("확인");
		JLabel lb = new JLabel(new ImageIcon(image));
		Brush brush = new Brush();

		lb.setBounds(0, 0, 200, 170);
		brush.setBounds(0, 0, 200, 170);
		btnOk.setBounds(65, 175, 60, 30);
		add(lb);
		add(brush);
		add(btnOk);

		btnOk.addActionListener(e -> weddingPay());
		
		lb.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				mouse = true;
				
				brush.x = e.getX();
				brush.y = e.getY();
				brush.repaint();
				brush.printAll(image.getGraphics());
			};
		});
	}

	public static void main(String[] args) {
		new SearchFrame().setVisible(true);
	}
	
	private void weddingPay() {
		
		if (mouse == false) {
			eMessage("서명을 하지 않았습니다.");
			return;
		}
		
		int pNo = randomIndex();
		
		iMessage("결제가 완료되었습니다.\r\n 결제번호: " + pNo);
		
		try {
			int mealType = 0;
			int weddingType = 0;
			
			ResultSet rs = dbManager.executeQuery("SELECT * FROM mealtype WHERE m_name = ?", wedding.m_ty);
			
			if (rs.next()) {
				mealType = rs.getInt(1);
			}
			
			rs = dbManager.executeQuery("SELECT * FROM weddingtype WHERE wty_name = ?", wedding.w_ty);
			
			if (rs.next()) {
				weddingType = rs.getInt(1);
			}
			
			dbManager.executeUpdate("insert into payment values (?, ?, ?, ?, ?, ?, ?, ?)", pNo, wedding.wNo, people, weddingType, mealType, letter, date, u_no);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int yn = JOptionPane.showConfirmDialog(null, "청접장을 보내시겠습니까?", "정보", JOptionPane.YES_NO_OPTION);
		
		if (yn == JOptionPane.OK_OPTION) {
			openFrame(new FriendFrame(pNo));
		} else {
			openFrame(new MainFrame());
		}
	}
	
	private int randomIndex() {
		int random = (int) (Math.random() * 9999) + 1000;
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM payment WHERE p_no = ?", random);
			
			if (rs.next()) {
				return randomIndex();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return random;
	}

	class Brush extends JLabel {
		private int x;
		private int y;
		
		@Override
		public void paint(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillOval(x - 10, y - 10, 10, 10);
		}
	}
}
