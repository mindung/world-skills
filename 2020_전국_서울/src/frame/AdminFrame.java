package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminFrame extends BaseFrame {

	public AdminFrame() {
		super("관리자", 330, 350);
		
		JPanel pnlCenter = new JPanel(new GridLayout(5, 1, 10, 10));

		JButton btn1 = createButton("소식문서 관리");
		JButton btn2 = createButton("회신문서관리");
		JButton btn3 = createButton("대나무숲댓글관리");
		JButton btn4 = createButton("차트");
		JButton btn5 = createButton("로그아웃");

		pnlCenter.add(btn1);
		pnlCenter.add(btn2);
		pnlCenter.add(btn3);
		pnlCenter.add(btn4);
		pnlCenter.add(btn5);

		add(pnlCenter, BorderLayout.CENTER);

		pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		btn1.addActionListener(e -> new NewsCareFrame().setVisible(true));
		btn2.addActionListener(e -> new ReplyCareFrame().setVisible(true));
		btn3.addActionListener(e -> new CommunityCareFrame().setVisible(true));
		btn4.addActionListener(e -> new ChartFrame().setVisible(true));
		btn5.addActionListener(e -> dispose());

	}
	
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
}
