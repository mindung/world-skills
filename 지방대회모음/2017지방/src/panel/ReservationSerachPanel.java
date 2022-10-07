package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import frame.BaseFrame;

public class ReservationSerachPanel extends BasePanel {

	private JTextField tfId = new JTextField(8);

	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "성명", "예약일", "차량번호", "좌석번호", "운임금액", "발권상태"},0);
	private JTable table = new JTable(dtm);

	private JScrollPane jsp = new JScrollPane(table);
	private JPanel pnlCenter = new JPanel(null);

	public ReservationSerachPanel() {

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(750, 500));

		JPanel pnl = new JPanel(new BorderLayout());
		JPanel pnlN = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel lbTitle = new JLabel("고객티켓예약조회");

		JButton btnSearch = new JButton("조회");

		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		pnlN.add(new JLabel("고객ID"));
		pnlN.add(tfId);
		pnlN.add(btnSearch);

		pnl.add(lbTitle, BorderLayout.NORTH);
		pnl.add(pnlN, BorderLayout.SOUTH);

		pnlCenter.add(jsp);

		add(pnl, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);

		jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnSearch.addActionListener(e -> search());

		jsp.setVisible(false);
		
		jsp.setBounds(0, 0, 580, 280);
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(BaseFrame.centerRenderer);
		}

		
		table.setRowHeight(25);
	}

	private void updateJsp() {
		pnlCenter.updateUI();
		jsp.setVisible(true);
	}

	private void search() {
		String id = tfId.getText();

		dtm.setRowCount(0);

		try {

			ResultSet rs = dbManager.executeQuery("select * from tbl_ticket where cID =? ", id);
			ResultSet rsUser = dbManager.executeQuery("select * from tbl_customer where cId = ?", id);

			if (id.equals("비회원") == true) {
				System.err.println("실행");
				while (rs.next()) {
					dtm.addRow(new Object[] { 
							"", 
							rs.getString(1),
							rs.getString(2),
							rs.getString(4),
							rs.getString(6),
							rs.getString(7)
					});
				}

				updateJsp();
			}

			if (rsUser.next()) {
				while (rs.next()) {
					dtm.addRow(new Object[] {
							rsUser.getString(3),
							rs.getString(1),
							rs.getString(2),
							rs.getString(4),
							rs.getString(6),
							rs.getString(7),
					});
				}
				
				updateJsp();
				
//			}else {
//				pnlCenter.updateUI();
//				jsp.setVisible(false);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
