package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonPanel extends BasePanel{
	
	private String value = null;
	
	public ButtonPanel() {
		
		Button btnYes = new Button("예");
		Button btnNo = new Button("아니오");
		
		add(btnYes);
		add(btnNo);
		
		btnYes.setPreferredSize(new Dimension(120,60));
		btnNo.setPreferredSize(new Dimension(120,60));
		
		btnYes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnNo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		btnYes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				value = "예";
				
				btnYes.setBackground(Color.RED);
				btnNo.setBackground(null);
			}
		});
		
		btnNo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				value = "아니오";
				
				btnNo.setBackground(Color.RED);
				btnYes.setBackground(null);
			}
		});
		
	}
		private class Button extends JPanel{
			public Button(String text) {
				setLayout(new BorderLayout());
				setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				JLabel lb = new JLabel(text,JLabel.CENTER);
				add(lb,BorderLayout.CENTER);
				
			}
			
		}
		
		public String getvalue() {
			return value; // 입력된 값을 다른 클래스에서도 사용해야함.
			
		}
}
