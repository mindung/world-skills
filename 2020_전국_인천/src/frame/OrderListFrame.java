package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class OrderListFrame extends BaseFrame {

	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "상품 번호", "상품명", "상품 가격", "주문 개수", "총 가격" }, 0);

	private JPanel pnlOrder = new JPanel(new FlowLayout());

	public OrderListFrame() {

		super("구매리스트", 820, 500);

		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton btnOk = new JButton("확인");

		JScrollPane jsp = new JScrollPane(pnlOrder, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pnlSouth.add(btnOk);

		add(pnlSouth, BorderLayout.SOUTH);
		add(jsp, BorderLayout.CENTER);

		getOrder();

		btnOk.addActionListener(e -> openFrame(new ProductFrame()));

	}
	
	public static void main(String[] args) {
		new OrderListFrame().setVisible(true);
	}
	
	private void getOrder() {
		String[] str = {"상품 번호", "상품명", "상품 가격", "주문 개수", "총 가격"};
		
		pnlOrder.removeAll();
	
		JPanel pnl = new JPanel(new GridLayout(1, 5));
		
		for (String string : str) {
			JLabel lb = new JLabel(string, JLabel.CENTER);
			lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			pnl.add(lb);
		}
		
		pnl.setPreferredSize(new Dimension(750, 20));
		pnlOrder.add(pnl);
		
		try {

			int cnt = 0;
			ResultSet rs = dbManager.executeQuery("select pu_no, p_name, pu_price,pu_count,pu_total, u_no from purchase inner join product on product.p_no = purchase.p_no where u_no = ?", u_no);

			while (rs.next()) {
				
				addOrder(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				cnt++;
			}
			
			pnlOrder.setLayout(new GridLayout(cnt + 1 , 1));
			pnlOrder.setPreferredSize(new Dimension(750, (45 * cnt) - 25));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addOrder(int no, String name, int price, int count, int amount) {

		JPanel pnl = new JPanel(new GridLayout(1, 5));

		JLabel lbNo = new JLabel(String.valueOf(no),JLabel.CENTER);
		JLabel lbName = new JLabel(name,JLabel.CENTER);
		JLabel lbPrice = new JLabel(String.format("%,d",price), JLabel.CENTER);
		JLabel lbCount = new JLabel(String.valueOf(count),JLabel.CENTER);
		JLabel lbAmount = new JLabel(String.valueOf(amount),JLabel.CENTER);
		
		pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		pnl.setPreferredSize(new Dimension(780, 50));
		pnl.add(lbNo);
		pnl.add(lbName);
		pnl.add(lbPrice);
		pnl.add(lbCount);
		pnl.add(lbAmount);

		pnlOrder.add(pnl);

	}
}