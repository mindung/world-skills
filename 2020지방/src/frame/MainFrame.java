package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame {
	public MainFrame() {
		super("메인", 350, 400);
		
		JPanel pnlCenter = new JPanel(new GridLayout(5, 1));
		JLabel lbTitle = new JLabel(String.format("%s 환자", p_name),JLabel.CENTER);
		
		JButton btn1 = new JButton("진료예약");
		JButton btn2 = new JButton("입퇴원 신청");
		JButton btn3 = new JButton("예약현황");
		JButton btn4 = new JButton("진료과별 분석");
		JButton btn5 = new JButton("종료");
		
		pnlCenter.add(btn1);
		pnlCenter.add(btn2);
		pnlCenter.add(btn3);
		pnlCenter.add(btn4);
		pnlCenter.add(btn5);
		
		lbTitle.setFont(new Font("굴림",Font.BOLD,25));
		
		add(pnlCenter,BorderLayout.CENTER);
		add(lbTitle,BorderLayout.NORTH);
		
		btn1.addActionListener(e -> openFrame(new ReservationFrame()));
		btn2.addActionListener(e -> hospitalization());
		btn3.addActionListener(e -> openFrame(new ReservationListFrame()));
		btn4.addActionListener(e -> openFrame(new ChartFrame()));
		btn5.addActionListener(e -> openFrame(new LoginFrame()));
		
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

	private void hospitalization() {
	
		try {
			ResultSet rs = dbManager.executeQuery("select * from hospitalization where p_no = ? and h_fday = ''", p_no);
								// 퇴원날짜가 비어있으면
			if(rs.next()) {
				openFrame(new LeaveFrame(rs.getInt(1))); // hospitalization no값 넘겨주기
			}else {
				openFrame(new sickRoomFrame());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
