package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminFrame extends BaseFrame{
	
	public AdminFrame() { 
		super("包府磊", 310, 220);
		
		JPanel pnlCenter = new JPanel(new GridLayout(2, 1));
		
		JButton btnProduct = new JButton("惑前包府");
		JButton btnUser = new JButton("蜡历包府");
		
		pnlCenter.add(btnProduct);
		pnlCenter.add(btnUser);
		
		add(pnlCenter,BorderLayout.CENTER);
		
		btnUser.addActionListener(e -> openFrame(new UserCareFrame()));
		btnProduct.addActionListener(e -> openFrame(new ProductCareFrame()));
	}
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);

	}

}

