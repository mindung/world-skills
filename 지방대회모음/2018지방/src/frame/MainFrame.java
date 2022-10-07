package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame{
	public MainFrame() {
		super("����", 330, 250);
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter = new JPanel(new GridLayout(4, 1));
		JPanel pnlSouth = new JPanel();
		
		JButton btnInsert = new JButton("������");
		JButton btnUser = new JButton("�����");
		JButton btnAdmin = new JButton("������");
		JButton btnExit = new JButton("����");
		
		pnlCenter.add(btnInsert);
		pnlCenter.add(btnUser);
		pnlCenter.add(btnAdmin);
		pnlCenter.add(btnExit);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		btnInsert.addActionListener(e -> openFrame(new InsertFrame()));
		btnUser.addActionListener(e -> openFrame(new TicketFrame1()));
		btnAdmin.addActionListener(e -> openFrame(new AdminFrame()));
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}



