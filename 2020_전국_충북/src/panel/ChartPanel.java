package panel;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ChartPanel extends BasePanel {

	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"Á¦Ç°¸í", "ÆÇ¸Å·®"}, 0);
	private JTable table = new JTable(dtm);
	
	private int[] columnSize = {200, 100};
	private Color[] colors = {Color.RED, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GRAY};
	private JLabel[] lbTitles = new JLabel[5];
	private JLabel[] lbBars = new JLabel[5];
	
	public ChartPanel() {
		setLayout(null);
		
		JLabel lbChart = new JLabel("ÆÇ¸Å È½¼ö");
		
		JScrollPane jsp = new JScrollPane(table);
		JScrollPane jsp2 = new JScrollPane();
		
		lbChart.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		
		add(lbChart);
		add(jsp);
		
		lbChart.setBounds(30, 20, 300, 30);
		jsp.setBounds(30, 60, 300, 480);
		
//		add(jsp2);
//		jsp2.setBounds(360, 60, 500, 480);
		
		for (int i = 0; i < columnSize.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnSize[i]);
		}
		
		getOrderList();
	}
	
	private void getOrderList() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT product.name, SUM(orderlist.quantity) AS cnt FROM orderlist INNER JOIN product ON product.serial = orderlist.product WHERE seller = ? GROUP BY product.name ORDER BY cnt DESC, product.name", sellerSerial);
			
			int i = 0;
			int max = 0;
			
			while(rs.next()) {
				String product = rs.getString(1);
				int cnt = rs.getInt(2);
				
				dtm.addRow(new Object[] {product, cnt});
				
				if (cnt > max) {
					max = cnt;
				}
				
				if (i < 5) {
					lbTitles[i] = createLabel(product);
					lbBars[i] = new JLabel();
					lbBars[i].setBackground(colors[i]);
					lbBars[i].setOpaque(true);
					lbBars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					
					add(lbTitles[i]);
					add(lbBars[i]);
					
					int height = (int) ((450f / max) * cnt);
					int y = 60 + (450 - height);
					
					lbTitles[i].setBounds(360 + (i * 100), 510, 100, 30);
					lbBars[i].setBounds(385 + (i * 100), y, 50, height);
				}
				
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
