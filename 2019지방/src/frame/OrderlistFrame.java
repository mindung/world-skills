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
		super("���ų���", 730, 380);

		JPanel pnlSouth = new JPanel();

		JButton btnExit = new JButton("�ݱ�");

		JLabel lbTitle = new JLabel(String.format("%Sȸ���� ���ų���", u_name), JLabel.CENTER);
		lbTitle.setFont(new Font("����", Font.BOLD, 26));

		JScrollPane jsp = new JScrollPane(table);

		dtm.setColumnIdentifiers(new String[] { "��������", "�޴���", "����", "������", "����", "�ѱݾ�" });

		pnlSouth.add(new JLabel("�� ���� �ݾ�"));
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
		
		table.getColumnModel().getColumn(1).setPreferredWidth(200); // columnModel ũ�� �ø��� ��
	}

	public static void main(String[] args) {
		new OrderlistFrame().setVisible(true);

	}
	
	private void getOrderList() {

		try {
		
			ResultSet rs = dbmanager.executeQuery("select d_date, m_name , o_price , o_size, o_count, o_amount from orderlist inner join menu on menu.m_no = orderlist.m_no where u_no = ?", u_no);
		
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(1), // ��¥ string
						rs.getString(2),
						String.format("%,d",rs.getInt(3)),
						rs.getString(4),
						rs.getInt(5),
						String.format("%,d",rs.getInt(6))
				});
			}
			
			rs = dbmanager.executeQuery("select sum(o_amount) from orderlist where u_no = ?;", u_no);
			
			if(rs.next()) {
				tfAmount.setText(String.format("%,d",rs.getInt(1))); //%,d 3�ڸ� �޸�
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
 