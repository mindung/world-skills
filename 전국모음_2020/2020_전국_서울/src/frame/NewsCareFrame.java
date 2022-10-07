package frame;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class NewsCareFrame extends BaseFrame{
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"","소식항목"}, 0);
	private JTable table = new JTable(dtm);
	
	private DefaultTableModel dtm1 = new DefaultTableModel(new String[] {"","공지항목"}, 0);
	private JTable table1 = new JTable(dtm1);
	
	public NewsCareFrame() {
		super("소식문서관리", 540, 250);
		
		setLayout(null);
		
		JPanel pnl = new JPanel(new GridLayout(1, 4, 5, 0));
		JPanel pnlG = new JPanel(new GridLayout(2, 1, 0, 3));
		
		JButton btnTableInsert = createButton("추가>>");
		JButton btnTableDelete = createButton("삭제");
		JButton btnUpdate = createButton("수정");
		JButton btnInsert = createButton("추가");
		JButton btnDelete = createButton("삭제");
		JButton btnExit = createButton("닫기");
		
		JScrollPane jsp = new JScrollPane(table);
		JScrollPane jsp1 = new JScrollPane(table1);
		
		pnl.add(btnDelete);
		pnl.add(btnInsert);
		pnl.add(btnUpdate);
		pnl.add(btnExit);
		
		pnlG.add(btnTableInsert);
		pnlG.add(btnTableDelete);
		
		addC(jsp, 3, 5, 220, 150);
		addC(pnlG, 225, 60, 70, 80);
		addC(jsp1, 300, 5, 220, 150);
		addC(pnl, 130, 165, 280, 30);
		
		btnTableInsert.addActionListener(e ->{
			tableInsert();
			
			NewsInsertFrame frame = new NewsInsertFrame();
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					getTableInfo();
				}
			});
		});
		
		btnTableDelete.addActionListener(e -> tableDelete());
		btnInsert.addActionListener(e -> new NewsInsertFrame().setVisible(true));
		btnExit.addActionListener(e -> dispose());
		btnUpdate.addActionListener(e -> {

			NewsUpdateFrame frame = new NewsUpdateFrame(0);
			
			int row = table.getSelectedRow();
			int row1 = table1.getSelectedRow();
			
			if(row == -1 && row1 == -1) {
				return;
			}
				
			if (row != -1) {
				frame = new NewsUpdateFrame((int) table.getValueAt(row, 0));

			} else {
				frame = new NewsUpdateFrame((int) table1.getValueAt(row1, 0));
			}
			
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					getTableInfo();
				}
			});

			frame.setVisible(true);

		});

		btnDelete.addActionListener(e -> delete());

		getTableInfo();

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		for (int i = 0; i < table1.getColumnCount(); i++) {
			table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		table.getColumn("").setMinWidth(0);
		table.getColumn("").setMaxWidth(0);

		table1.getColumn("").setMinWidth(0);
		table1.getColumn("").setMaxWidth(0);

//		NewsCareFrame frame = new NewsCareFrame();
		
	}
	
	public static void main(String[] args) {
		new NewsCareFrame().setVisible(true);
	}
	
	private void tableInsert() {
		int row = table.getSelectedRow();

		if (row == -1) {
			eMessage("항목을 선택해주세요.");
		} else {
			try {

				dbManager.executeUpdate("insert into notice values(0,?) ", table.getValueAt(row, 0));
				getTableInfo();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void delete() {
		int row = table.getSelectedRow();

		if (row == -1) {
			eMessage("항목을 선택해주세요.");
		} else {

			try {

				dbManager.executeUpdate("delete from news where n_no =?", table.getValueAt(row, 0));
				getTableInfo();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void tableDelete() {
		int row = table1.getSelectedRow();

		if (row == -1) {
			eMessage("항목을 선택해주세요.");
		} else {
			try {
				dbManager.executeUpdate("delete from notice where nt_no =?", table1.getValueAt(row, 0));
				getTableInfo();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			table1.setValueAt(table.getValueAt(row, 1), tableRow, 1);
		}

	}
	
	public void getTableInfo() {
		
		dtm.setRowCount(0);
		dtm1.setRowCount(0);
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM reminder.news left join notice on notice.n_no = news.n_no where nt_no is null");
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2)
				});
			}
			
			rs = dbManager.executeQuery("SELECT * FROM reminder.notice inner join news on notice.n_no = news.n_no");
			while (rs.next()) {
				dtm1.addRow(new Object[] {
						rs.getInt(1), rs.getString(4)
				});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
