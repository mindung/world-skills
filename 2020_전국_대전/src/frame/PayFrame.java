package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PayFrame extends BaseFrame {

	private JPanel pnlCenter = new JPanel(new GridLayout(3, 10));
	
	private String[] array = new String[11]; // 0 ~ ´Ý±â
	private String[] numbers = new String[30]; // 30°³
	private OrderFrame frame;
	public PayFrame(OrderFrame frame) {
		
		super("Å°ÆÐµå", 400, 200);
		this.frame = frame;
		
		for (int i = 0; i < 10; i++) {
			array[i] = String.valueOf(i);
		}
		
		array[10] = "´Ý±â";
		
		int index = 0;
		
		for (int i = 0; i < 30; i++) {
			// Math.round(Math.random())
			int rnd = (int) (Math.random() * 2); // 0 or 1 °ªÀ» ³ÖÀ»·¡ ¸»·¡
			
			if (index == 11) {
				addLabel(null);
			} else {
				if (rnd == 0) {
					addLabel(array[index++]);
				} else {
					if (30 - i == 11 - index) {
						addLabel(array[index++]);
					} else {
						addLabel(null);
					}
				}
			}				
		}
		
		

//		for (String str : array) { // 0 ~ ´Ý±â
//			int index = getRandomIndex();
//			numbers[index] = str;
//		}
//		
//		for (String string : numbers) {
//			JLabel lb = new JLabel("",JLabel.CENTER);
//			
//			if(string != null) {
//				lb.setText(string);
//				lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//			}
//			
//			pnlCenter.add(lb);
//		}
		
		
		add(pnlCenter,BorderLayout.CENTER);
	}
	
	
	
	private void addLabel(String str) {
		JLabel lb = new JLabel("",JLabel.CENTER);
		
		if(str != null) {
			
			lb.setText(str);
			lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			lb.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(str.equals("´Ý±â")) {
						dispose();
					}else {
						frame.tfNum.setText(frame.tfNum.getText() + str);
					}
				}
			});
		}
		
		pnlCenter.add(lb);
		
	}

	public static void main(String[] args) {
		new PayFrame(null).setVisible(true);
	}

	private int getRandomIndex() {
		int index = (int) (Math.random() * 30); // 0 ~ 29
		
		if(numbers[index] == null) {
			return index;
		}else {
			return getRandomIndex();
		}		
	}
	
	
}
