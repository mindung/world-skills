package frame;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PaymentFrame extends BaseFrame {

	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "종류", "메뉴명", "사원명", "결제수량", "총결제금액", "결제일" },
			0);
	private JTable table = new JTable(dtm);

	private JTextField tfMenu = new JTextField(8);

	public PaymentFrame() {
		super("결제조회", 550, 600);

		JPanel pnlNorth = new JPanel();

		JButton btnSearch = new JButton("조회");
		JButton btnShowAll = new JButton("모두보기");
		JButton btnPrint = new JButton("인쇄");
		JButton btnExit = new JButton("닫기");

		JScrollPane jsp = new JScrollPane(table);

		pnlNorth.add(new JLabel("메뉴명:"));
		pnlNorth.add(tfMenu);
		pnlNorth.add(btnSearch);
		pnlNorth.add(btnShowAll);
		pnlNorth.add(btnPrint);
		pnlNorth.add(btnExit);

		add(pnlNorth, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);

		search();
		btnSearch.addActionListener(e -> search());
		btnShowAll.addActionListener(e -> {
			tfMenu.setText("");
			search();
		});
		
		btnExit.addActionListener(e -> openFrame(new AdminFrame()));

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	public static void main(String[] args) {
		new PaymentFrame().setVisible(true);

	}

	private void search() {


		dtm.setRowCount(0);
		try {
			String menu = tfMenu.getText();

			ResultSet rs = dbManager.executeQuery(
					"SELECT  cuisineName, mealName,memberName,orderlistCount,amount,date_format(orderDate, '%Y-%m-%d') FROM meal.orderlist "
							+ "inner join member on member.memberNo = orderlist.memberNo inner join cuisine on cuisine.cuisineNo = orderlist.cuisineNo "
							+ "inner join meal on meal.mealNo = orderlist.mealNo where mealName like '%" + menu + "%'");

			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getInt(4),
						rs.getInt(5),
						rs.getString(6),

				});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
