package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminFrame extends BaseFrame{
	
	public AdminFrame() { 
		super("������", 310, 220);
		
		JPanel pnlCenter = new JPanel(new GridLayout(2, 1));
		
		JButton btnProduct = new JButton("��ǰ����");
		JButton btnUser = new JButton("��������");
		
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

