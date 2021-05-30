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
		super("����", 350, 400);
		
		JPanel pnlCenter = new JPanel(new GridLayout(5, 1));
		JLabel lbTitle = new JLabel(String.format("%s ȯ��", p_name),JLabel.CENTER);
		
		JButton btn1 = new JButton("���Ό��");
		JButton btn2 = new JButton("����� ��û");
		JButton btn3 = new JButton("������Ȳ");
		JButton btn4 = new JButton("������� �м�");
		JButton btn5 = new JButton("����");
		
		pnlCenter.add(btn1);
		pnlCenter.add(btn2);
		pnlCenter.add(btn3);
		pnlCenter.add(btn4);
		pnlCenter.add(btn5);
		
		lbTitle.setFont(new Font("����",Font.BOLD,25));
		
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
								// �����¥�� ���������
			if(rs.next()) {
				openFrame(new LeaveFrame(rs.getInt(1))); // hospitalization no�� �Ѱ��ֱ�
			}else {
				openFrame(new sickRoomFrame());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
