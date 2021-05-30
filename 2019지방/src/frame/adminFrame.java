package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class adminFrame extends baseFrame{
	
	public adminFrame() {
	
		super("관리자 메뉴", 280, 200);
		
		JPanel pnlCenter = new JPanel(new GridLayout(3, 1));
		JPanel pnlSouth = new JPanel();
		JPanel pnlNorth = new JPanel();
		
		JButton btn1 = new JButton("메뉴 등록");
		JButton btn2 = new JButton("메뉴 관리");
		JButton btn3 = new JButton("로그아웃");
		
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
