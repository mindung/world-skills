package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame{

	private JPanel pnlCenter = new JPanel(null);
	private JPanel pnlEast = new JPanel(new GridLayout(3, 1));
	
	public MainFrame() {
		super("메인", 1100, 800);
		
		JPanel pnlWest = new JPanel(new GridLayout(5, 1, 10, 5));
		
		JButton btn1 = new JButton("웨딩홀 검색");
		JButton btn2 = new JButton("예약 확인");
		JButton btn3 = new JButton("인기 웨딩홀");
		JButton btn4 = new JButton("관리");
		JButton btn5 = new JButton("종료");
		
		JLabel lbTitle = new JLabel("결혼식장 예약프로그램",JLabel.CENTER);
		
		lbTitle.setFont(new Font("굴림",Font.BOLD,30));
		
		pnlWest.add(btn1);
		pnlWest.add(btn2);
		pnlWest.add(btn3);
		pnlWest.add(btn4);
		pnlWest.add(btn5);

		pnlCenter.add(pnlWest);
		pnlCenter.add(pnlEast);
		
		pnlEast.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(lbTitle,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		
		pnlWest.setBounds( 0, 5, 130, 180);
		pnlEast.setBounds(135, 10, 930, 700);

		getInfo();
		
		btn1.addActionListener(e -> openFrame(new SearchFrame()));
		btn2.addActionListener(e -> reserveCheck());
		btn3.addActionListener(e -> openFrame(new PopularFrame()));
		btn4.addActionListener(e -> openFrame(new AdminFrame()));
		btn5.addActionListener(e -> System.exit(0));
		
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
		
	}
	
	private void reserveCheck() {
	
		String input = JOptionPane.showInputDialog(null, "예약번호를 입력하세요", "입력", JOptionPane.QUESTION_MESSAGE);
		
		if(input == null) {
			return;
		}else {
			try {
				ResultSet rs = dbManager.executeQuery("select *From reservation where rno = ?", input);
				
				if(rs.next()) {
					openFrame(new ReserveCheckFrame(input));
				}else {
					eMessage("예약번호가 일치하지 않습니다.");
				}
				
			 } catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private void addInfo(int wno, int cnt, String name, int price, String addr, int rank) {
		
		JPanel pnl = new JPanel(new BorderLayout());
		JPanel panelEast = new JPanel (new GridLayout(4,1,-200,10));
		
		JLabel lbImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("datafile/호텔이미지/%s/%s1.jpg", name,name)).getScaledInstance(400, 300, Image.SCALE_SMOOTH)));
		
		panelEast.add(new JLabel(String.format("예약 %d위 (%s건)", rank,cnt),JLabel.LEFT));
		panelEast.add(new JLabel(String.format("이름 : %s", name),JLabel.LEFT));
		panelEast.add(new JLabel(String.format("가격 : %s원", price),JLabel.LEFT));
		panelEast.add(new JLabel(String.format("주소 : %s", addr),JLabel.LEFT));
		
		panelEast.setPreferredSize(new Dimension(520, 300));
		
		pnl.add(lbImage,BorderLayout.WEST);
		pnl.add(panelEast,BorderLayout.EAST);
		
		pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pnlEast.add(pnl);
		
	}
//	
	
	private void getInfo() {
	
		try {
			ResultSet rs = dbManager.executeQuery("select reservation.wNo,wname,wadd,wprice,count(reservation.wNo) as cnt from reservation inner join weddinghall on weddinghall.wNo = reservation.wNo group by wno order by cnt  desc limit 3 ");
			
			int i = 1;
			while (rs.next()) {
				addInfo(rs.getInt(1), rs.getInt(5), rs.getString(2), rs.getInt(4), rs.getString(3),i++);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

