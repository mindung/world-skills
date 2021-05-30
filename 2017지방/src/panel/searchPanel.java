package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import frame.BaseFrame;
import panel.BasePanel;

public class searchPanel extends BasePanel {

	private DefaultTableModel dtm = new DefaultTableModel(
			new String[] { "차량번호", "출발지", "도착지", "첫차시간", "소요시간", "운임횟수", "운임금액" }, 0);
	private JTable table = new JTable(dtm);

	public searchPanel() {

		JScrollPane jsp = new JScrollPane(table);
		jsp.setPreferredSize(new Dimension(700, 380));

		add(jsp, BorderLayout.CENTER);

		getinfo();

		table.setRowHeight(23);

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(BaseFrame.centerRenderer);

		}
	}

	private void getinfo() {
		try {
			ResultSet rs = dbManager.executeQuery("select * from TBL_Bus");
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
