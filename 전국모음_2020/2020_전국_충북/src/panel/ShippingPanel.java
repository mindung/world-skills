package panel;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ShippingPanel extends BasePanel {

	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"�ֹ���ȣ", "�ֹ��� ID", "��ǰ��", "����" }, 0);
	private JTable table = new JTable(dtm);
	
	private int[] columnSize = {90, 100, 400, 90};
	
	public ShippingPanel() {
		setLayout(null);
		
		JLabel lbShipping = new JLabel("��۰���");
		
		JScrollPane jsp = new JScrollPane(table);
		
		JButton btnShipping = createButton("��ۿϷ�");
		
		lbShipping.setFont(new Font("���� ���", Font.BOLD, 20));
		
		add(lbShipping);
		add(jsp);
		add(btnShipping);
		
		lbShipping.setBounds(30, 20, 250, 30);
		jsp.setBounds(30, 60, 830, 430);
		btnShipping.setBounds(780, 500, 80, 35);
		
		btnShipping.addActionListener(e -> shipping());
		
		for (int i = 0; i < columnSize.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnSize[i]);
			table.getColumnModel().getColumn(i).setCellRenderer(centerCR);
		}
		
		getOrderList();
	}
	
	private void getOrderList() {
		dtm.setRowCount(0);
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT orderlist.serial, user.id, product.name, orderlist.quantity FROM orderlist INNER JOIN user ON user.serial = orderlist.user INNER JOIN product ON product.serial = orderlist.product WHERE seller = ? AND shipping = 0", sellerSerial);
		
			while(rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getInt(4)
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void shipping() {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			eMessage("��ۿϷ� ó���� �ֹ��� �������ּ���.");
			return;
		}
		
		try {
			dbManager.executeUpdate("UPDATE orderlist SET shipping = 1 WHERE serial = ?", table.getValueAt(row, 0));
			
			iMessage("��ۿϷ� ó�� �Ͽ����ϴ�.");
			
			getOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
