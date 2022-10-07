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
	
	private JButton btn1 = new JButton("����������ȸ");
	private JButton btn2 = new JButton("�����û");
	private JButton btn3 = new JButton("�����¼���ȸ");
	private JButton btn4 = new JButton("�����ǹ߸�");
	private JButton btn5 = new JButton("������ȸ");
	
	private JPanel pnlCenter = new JPanel();
	public MainFrame() {
		
		super("��ӹ������� ���α׷�", 800, 550);
		
		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNorthN = new JPanel();
		JPanel pnlNorthC = new JPanel();
		
		JLabel lbTitle = new JLabel("��ӹ������� ���α׷�",JLabel.CENTER);
		
		lbTitle.setFont(new Font("���� ���",Font.BOLD,22));
		
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


