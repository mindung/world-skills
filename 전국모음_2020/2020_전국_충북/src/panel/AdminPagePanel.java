package panel;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminPagePanel extends BasePanel {

	public AdminPagePanel() {
		setLayout(null);
		
		JButton btnStock = createButton("������");
		JButton btnShipping = createButton("��۰���");
		JButton btnChart = createButton("���");
		JButton btnAd = createButton("����");
		
		JPanel pnlCenter = new JPanel(new GridLayout(2, 2));
		
		pnlCenter.add(btnStock);
		pnlCenter.add(btnShipping);
		pnlCenter.add(btnChart);
		pnlCenter.add(btnAd);
		
		add(pnlCenter);
		
		pnlCenter.setBounds(250, 130, 400, 300);
		
		btnStock.addActionListener(e -> frame.changePanel(new StockPanel()));
		btnShipping.addActionListener(e -> frame.changePanel(new ShippingPanel()));
		btnChart.addActionListener(e -> frame.changePanel(new ChartPanel()));
		btnAd.addActionListener(e -> frame.changePanel(new AdPanel()));
	}
	
}
