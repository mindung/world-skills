package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import panel.BasePanel;
import panel.LookUpPanel;
import panel.ReservationApplyPanel;
import panel.ReservationSerachPanel;
import panel.TicketPanel;
import panel.searchPanel;

public class MainFrame extends BaseFrame{
	
	private JButton btn1 = new JButton("배차차량조회");
	private JButton btn2 = new JButton("예약신청");
	private JButton btn3 = new JButton("차량좌석조회");
	private JButton btn4 = new JButton("승차권발매");
	private JButton btn5 = new JButton("예약조회");
	
	private JPanel pnlCenter = new JPanel();
	public MainFrame() {
		
		super("고속버스예매 프로그램", 800, 550);
		
		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNorthN = new JPanel();
		JPanel pnlNorthC = new JPanel();
		
		JLabel lbTitle = new JLabel("고속버스예매 프로그램",JLabel.CENTER);
		
		lbTitle.setFont(new Font("맑은 고딕",Font.BOLD,22));
		
		pnlNorthC.setPreferredSize(new Dimension(800, 10));
		pnlNorthC.setBackground(Color.BLACK);
		
		pnlNorthN.add(btn1);
		pnlNorthN.add(btn2);
		pnlNorthN.add(btn3);
		pnlNorthN.add(btn4);
		pnlNorthN.add(btn5);
		
		pnlNorth.add(pnlNorthC,BorderLayout.SOUTH);
		pnlNorth.add(lbTitle,BorderLayout.NORTH);
		pnlNorth.add(pnlNorthN,BorderLayout.CENTER);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		
		btn1.addActionListener(e -> changePanel(new searchPanel()));
		btn2.addActionListener(e -> changePanel(new ReservationApplyPanel()));
		btn3.addActionListener(e -> changePanel(new LookUpPanel()));
		btn4.addActionListener(e -> changePanel(new TicketPanel()));
		btn5.addActionListener(e -> changePanel(new ReservationSerachPanel()));
		
	}
	public static void main(String[] args) {
		BasePanel.mainFrame.setVisible(true);
	}

	public void changePanel(BasePanel pnl) {
		pnlCenter.removeAll();
		pnlCenter.add(pnl,BorderLayout.CENTER);//,BorderLayout.CENTER;
		pnlCenter.updateUI();
	}
}


