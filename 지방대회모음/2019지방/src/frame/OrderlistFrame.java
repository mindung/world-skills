package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class OrderlistFrame extends baseFrame {

	private DefaultTableModel dtm = new DefaultTableModel();
	private JTable table = new JTable(dtm);
	private JTextField tfAmount = new JTextField(15);

	public OrderlistFrame() {
		super("구매내역", 730, 380);

		JPanel pnlSouth = new JPanel();

		JButton btnExit = new JButton("닫기");

		JLabel lbTitle = new JLabel(String.format("%S회원님 구매내역", u_name), JLabel.CENTER);
		lbTitle.setFont(new Font("굴림", Font.BOLD, 26));

		JScrollPane jsp = new JScrollPane(table);

		dtm.setColumnIdentifiers(new String[] { "구매일자", "메뉴명", "가격", "사이즈", "수량", "총금액" });

		pnlSouth.add(new JLabel("총 결제 금액"));
		pnlSouth.add(tfAmount);
		pnlSouth.add(btnExit);
		
		tfAmount.setEditable(false);
		
		add(jsp, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		add(lbTitle, BorderLayout.NORTH);
		
		btnExit.addActionListener(e -> openFrame(new StarboxFrame()));

		getOrderList();
		
		tfAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		
		for (int i = 0; i < dtm.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		table.getColumnModel().getColumn(1).setPreferredWidth(200); // columnModel 크기 늘리는 것
	}

	public static void main(String[] args) {
		new OrderlistFrame().setVisible(true);

	}
	
	private void getOrderList() {

		try {
		
			ResultSet rs = dbmanager.executeQuery("select d_date, m_name , o_price , o_size, o_count, o_amount from orderlist inner join menu on menu.m_no = orderlist.m_no where u_no = ?", u_no);
		
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(1), // 날짜 string
						rs.getString(2),
						String.format("%,d",rs.getInt(3)),
						rs.getString(4),
						rs.getInt(5),
						String.format("%,d",rs.getInt(6))
				});
			}
			
			rs = dbmanager.executeQuery("select sum(o_amount) from orderlist where u_no = ?;", u_no);
			
			if(rs.next()) {
				tfAmount.setText(String.format("%,d",rs.getInt(1))); //%,d 3자리 콤마
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
 