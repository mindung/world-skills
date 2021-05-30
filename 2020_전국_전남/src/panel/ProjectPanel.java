package panel;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import frame.JobInfoFrame;

public class ProjectPanel extends BasePanel {

	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "No", "이름", "기간", "시작", "끝" }, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	private JTable table = new JTable(dtm);
	private int[] columnSizes = { 50, 100, 50, 100, 100 };

	public ProjectPanel() {

		setLayout(null);

		JScrollPane jsp = new JScrollPane(table);

		add(jsp);
		jsp.setBounds(0, 0, 400, 600);

		getProject();

		for (int i = 0; i < table.getRowCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnSizes[i]);
		}

		for (int i = 1; i < 28; i++) {
			dtm.addRow(new Object[] { "", "", "", "", "" });
		}

		JTableHeader header = table.getTableHeader();

		header.setPreferredSize(new Dimension(0, 40));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedColumn() == 1 && e.getClickCount() == 2) {
					new JobInfoFrame(project_no).setVisible(true);
				}
			}
		});
	}

	private void getProject() {

		dtm.setRowCount(0);

		try {

			ResultSet rs = dbManager.executeQuery(
					"SELECT * FROM projectlibre.member left join project on project.p_num = member.p_num where project.p_num = ?",
					project_no);

			int i = 1;

			while (rs.next()) {

				dtm.addRow(new Object[] { i, rs.getString(5), rs.getString(9), rs.getString(10), rs.getString(11) });
				i++;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
