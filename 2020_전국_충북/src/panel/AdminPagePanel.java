package panel;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminPagePanel extends BasePanel {

	public AdminPagePanel() {
		setLayout(null);
		
		JButton btnStock = createButton("재고관리");
		JButton btnShipping = createButton("배송관리");
		JButton btnChart = createButton("통계");
		JButton btnAd = createButton("광고");
		
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
