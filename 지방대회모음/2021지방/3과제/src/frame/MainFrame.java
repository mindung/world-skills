package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainFrame extends BaseFrame{

	private JPanel pnlCenter = new JPanel(null);
	private JPanel pnlEast = new JPanel(new GridLayout(10, 1));
	
	public MainFrame() {
		super("����", 1100, 800);
		
		JPanel pnlWest = new JPanel(new GridLayout(5, 1, 10, 5));
		
		JButton btn1 = new JButton("����Ȧ �˻�");
		JButton btn2 = new JButton("����Ȯ��");
		JButton btn3 = new JButton("�α� ����Ȧ");
		JButton btn4 = new JButton("�α׾ƿ�");
		JButton btn5 = new JButton("����");
		
		JScrollPane jsp = new JScrollPane(pnlEast);
		JLabel lbTitle = new JLabel("��ȥ���� �������α׷�",JLabel.CENTER);
		
		lbTitle.setFont(new Font("����",Font.BOLD,30));
		
		pnlWest.add(btn1);
		pnlWest.add(btn2);
		pnlWest.add(btn3);
		pnlWest.add(btn4);
		pnlWest.add(btn5);

		pnlCenter.add(pnlWest);
		pnlCenter.add(jsp);
		
		pnlEast.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(lbTitle,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		
		pnlWest.setBounds( 0, 5, 130, 180);
		jsp.setBounds(135, 10, 930, 700);

		getInfo();
		
		btn1.addActionListener(e -> openFrame(new SearchFrame(), new MainFrame()));
		btn2.addActionListener(e -> openFrame(new ReservationNumFrame(), new MainFrame()));
		btn3.addActionListener(e -> openFrame(new PopularFrame(), new MainFrame()));
		btn4.addActionListener(e -> openFrame(new LoginFrame(), new MainFrame()));
		btn5.addActionListener(e -> System.exit(0));
		
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
		
	}
	
	private void addInfo(int cnt, String name, int price, String addr) {
		
		JPanel pnl = new JPanel(new BorderLayout());
		JPanel panelEast = new JPanel (new GridLayout(4,1,0,10));
		
		JLabel lbImage = new JLabel(getImageIcon(String.format("./datafile/����Ȧ/%s/%s1.jpg", name, name), 300, 200));
		
		panelEast.add(new JLabel(String.format("���� : %d��", cnt),JLabel.LEFT));
		panelEast.add(new JLabel(String.format("�̸� : %s", name),JLabel.LEFT));
		panelEast.add(new JLabel(String.format("���� : %,d��", price),JLabel.LEFT));
		panelEast.add(new JLabel(String.format("�ּ� : %s", addr),JLabel.LEFT));
		
		panelEast.setPreferredSize(new Dimension(600, 180));
		
		pnl.add(lbImage,BorderLayout.WEST);
		pnl.add(panelEast,BorderLayout.EAST);
		
		pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pnlEast.add(pnl);
		
	}

	private void getInfo() {
	
		try {
			ResultSet rs = dbManager.executeQuery("select count(payment.wh_no) as cnt, weddinghall.wh_name, weddinghall.wh_price, weddinghall.wh_add from payment " + 
					"inner join weddinghall on payment.wh_no = weddinghall.wh_no " + 
					"group by payment.wh_no order by cnt desc, weddinghall.wh_no asc limit 10");
			
			while (rs.next()) {
				addInfo(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

