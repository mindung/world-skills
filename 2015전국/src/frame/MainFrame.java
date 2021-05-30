package frame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends baseFrame{
	
	public MainFrame() {
		super("도서 관리 프로그램", 1040, 740);
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter = new JPanel();

		JButton btn1 = new JButton("회원 등록/삭제");
		JButton btn2 = new JButton("도서 등록/삭제");
		JButton btn3 = new JButton("도서대여/반납");
		JButton btn4 = new JButton("모든 대여 정보");
		JButton btn5 = new JButton("종료");
		
		JLabel lbImage = new JLabel(new ImageIcon("./지급자료/Welcome.jpg"));
		
		pnlNorth.add(btn1);
		pnlNorth.add(btn2);
		pnlNorth.add(btn3);
		pnlNorth.add(btn4);
		pnlNorth.add(btn5);
		
		pnlCenter.add(lbImage);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		
		btn1.setBackground(Color.WHITE);
		btn2.setBackground(Color.WHITE);
		btn3.setBackground(Color.WHITE);
		btn4.setBackground(Color.WHITE);
		btn5.setBackground(Color.WHITE);
		pnlNorth.setBackground(Color.WHITE);
		
		btn1.addActionListener(e -> openFrame(new UserFrame()));
		btn2.addActionListener(e -> openFrame(new BookFrame()));
		btn3.addActionListener(e -> openFrame(new BookRentFrame()));
		btn4.addActionListener(e -> openFrame(new RentInfoFrame()));
		btn5.addActionListener(e -> openFrame(new LoginFrame()));
		
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}

