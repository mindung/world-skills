package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Wedding;

public class PayFrame extends BaseFrame {
	
	private JPanel pnlCenter = new JPanel(new FlowLayout());
	
	public PayFrame(Wedding wedding, int mealPrice, int people, int letter, String date) {
		super("계산서", 300, 350);
		
		JPanel pnlSouth = new  JPanel();
		
		JButton btnPay = new JButton("결제");
		
		JLabel lbTitle = new JLabel("계산서", JLabel.CENTER);
		JLabel lbLetter = new JLabel();
		
		lbTitle.setFont(new Font("굴림", Font.PLAIN, 30));
		
		JPanel pnlWeddingHall = addPanel("웨딩홀명", new JLabel(""));
		
		pnlWeddingHall.setBackground(Color.WHITE);
		
		addPanel("항목", new JLabel("금액"));
		addBlackLine();
		addPanel("홀사용료", new JLabel(String.format("%,d", wedding.price)));
		addPanel("식사비용", new JLabel(String.format("%,d", mealPrice * people)));
		
		int letterPrice = letter == 0 ? 0 : 150000;
		
		if (letter == 0) {
			lbLetter.setForeground(Color.LIGHT_GRAY);
		}
		
		lbLetter.setText(String.format("%,d", letterPrice));
		
		addPanel("청첩장", lbLetter);
		addBlackLine();
		
		int amount = wedding.price + (mealPrice * people) + letterPrice;
		addPanel("총금액", new JLabel(String.format("%,d", amount)));
		
		pnlSouth.add(btnPay);
		
		add(lbTitle, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		btnPay.addActionListener(e -> openFrame(new SignFrame(wedding, people, letter, date), new PayFrame(wedding, mealPrice, people, letter, date)));
	}
	
	public static void main(String[] args) {
		new SearchFrame().setVisible(true);
//		new PayFrame(null, 0, 0, null).setVisible(true);;
	}
	
	private JPanel addPanel(String str1, JLabel lb) {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(new JLabel(str1), BorderLayout.WEST);
		panel.add(lb, BorderLayout.CENTER);
		
		lb.setHorizontalAlignment(JLabel.RIGHT);
		
		panel.setPreferredSize(new Dimension(270, 30));
		
		pnlCenter.add(panel);
		
		return panel;
	}
	
	private void addBlackLine() {
		JPanel panel = new JPanel();
		
		panel.setBackground(Color.BLACK);
		panel.setPreferredSize(new Dimension(270, 3));
		
		pnlCenter.add(panel);
	}
}
