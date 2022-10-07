package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class adminFrame extends baseFrame{
	
	public adminFrame() {
	
		super("������ �޴�", 280, 200);
		
		JPanel pnlCenter = new JPanel(new GridLayout(3, 1));
		JPanel pnlSouth = new JPanel();
		JPanel pnlNorth = new JPanel();
		
		JButton btn1 = new JButton("�޴� ���");
		JButton btn2 = new JButton("�޴� ����");
		JButton btn3 = new JButton("�α׾ƿ�");
		
		pnlCenter.add(btn1);
		pnlCenter.add(btn2);
		pnlCenter.add(btn3);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlNorth,BorderLayout.NORTH);
		
		btn1.addActionListener(e -> openFrame(new munuInsertFrame()));
		btn2.addActionListener(e ->openFrame(new munuUpdateFrame()));
		btn3.addActionListener(e -> openFrame(new LoginFrame()));
	}
	public static void main(String[] args) {
		new adminFrame().setVisible(true);

	}

}
