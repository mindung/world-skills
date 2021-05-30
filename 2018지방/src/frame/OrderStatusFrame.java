package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OrderStatusFrame extends BaseFrame{
	
	private JLabel lbSum = new JLabel("",JLabel.RIGHT);
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"종류","주문수량"}, 0);
	private JTable table = new JTable(dtm);
	
	public OrderStatusFrame() {
		super("메뉴별 주문현황", 350, 200);
		
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnExit = new JButton("닫기");
		
		JScrollPane jsp = new JScrollPane(table);
		
		pnlNorth.add(btnExit);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);
		add(lbSum,BorderLayout.SOUTH);
		
		getTableList();
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		table.setAutoCreateRowSorter(true);
		
		btnExit.addActionListener(e -> openFrame(new AdminFrame()));
	}
	
	public static void main(String[] args) {
		new OrderStatusFrame().setVisible(true);
	}
	
	private void getTableList() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT orderlist.cuisineNo, cuisineName, count(*) as cnt FROM meal.orderlist inner join cuisine on cuisine.cuisineNo = orderlist.cuisineNo group by cuisineName order by cuisineNo");
			
			int cnt = 0;
			
			while (rs.next()) {
				cnt += rs.getInt(3);
				
				dtm.addRow(new Object[] {
						rs.getString(2),rs.getInt(3)
				});
			}	
			
			lbSum.setText(String.format("합계 : %d개", cnt));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
