package frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminFrame extends BaseFrame {
	
	public AdminFrame() {
		super("관리", 590, 470);
		
		JPanel pnlNorth = new JPanel();
		
		JButton btn1 = new JButton("메뉴등록");
		JButton btn2 = new JButton("메뉴관리");
		JButton btn3 = new JButton("결제조회");
		JButton btn4 = new JButton("메뉴별 주문현황");
		JButton btn5 = new JButton("종료");
		
		JLabel lbImage = new JLabel(new ImageIcon("./DataFiles/main.jpg"));
		
		pnlNorth.add(btn1);
		pnlNorth.add(btn2);
		pnlNorth.add(btn3);
		pnlNorth.add(btn4);
		pnlNorth.add(btn5);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(lbImage,BorderLayout.CENTER);
		
		btn1.addActionListener(e -> openFrame(new InsertMenuFrame()));
		btn2.addActionListener(e -> openFrame(new MenuCareFrame()));
		btn3.addActionListener(e -> openFrame(new PaymentFrame()));
		btn4.addActionListener(e -> openFrame(new OrderStatusFrame()));
		btn5.addActionListener(e -> openFrame(new MainFrame()));
		
		
	}
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);

	}

}
