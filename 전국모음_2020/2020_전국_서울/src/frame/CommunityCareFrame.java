package frame;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CommunityCareFrame extends BaseFrame {

	private JComboBox<String> comGrade = new JComboBox<String>(new String[] { "글제목 + 댓글", "글제목", "댓글" });
	private JTextField tfSearch = new JTextField(15);
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "", "제목", "내용", "댓글" }, 0);
	private JTable table = new JTable(dtm);
	
	public CommunityCareFrame() {
		
		super("댓글관리", 630, 350);
		
		JPanel pnlN = new JPanel();
		JPanel pnlS = new JPanel();
	
		JScrollPane jsp = new JScrollPane(table);
		
		JButton btnSearch = createButton("검색");
		JButton btnDelete = createButton("삭제");
		JButton btnExit = createButton("확인");
		
		pnlN.add(comGrade);
		pnlN.add(tfSearch);
		pnlN.add(btnSearch);
		
		pnlS.add(btnDelete);
		pnlS.add(btnExit);
		
		add(pnlN,BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		add(pnlS, BorderLayout.SOUTH);
		
		btnSearch.addActionListener(e -> getinfo());
		btnDelete.addActionListener(e -> delete());
		btnExit.addActionListener(e -> dispose());
		
		getinfo();
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		table.getColumn("").setMinWidth(0);
		table.getColumn("").setMaxWidth(0);
	}
	
	public static void main(String[] args) {
		new CommunityCareFrame().setVisible(true);
	}
	
	private void getinfo() {
		dtm.setRowCount(0);
		
		int index = comGrade.getSelectedIndex();
		
		String contents = tfSearch.getText();
		String Sql = "SELECT community.c_no, c_title, c_content, comment.co_content FROM reminder.community"
				+ " inner join comment on community.c_no = comment.c_no ";
		
		if (index == 0) {
			Sql = Sql + "where c_title like '%" + contents + "%' or co_content like '%" + contents + "%'";
		}else if (index == 1) {
			Sql = Sql + "where c_title like '%" + contents + "%'";
		}else {
			
			Sql = Sql + "where co_content like '%" + contents + "%'";
		}
		
		try {
			ResultSet rs = dbManager.executeQuery(Sql);
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4)
				});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void delete() {
			
		int row = table.getSelectedRow();
		
		if (row == -1) {
			eMessage("항목을 선택해주세요.");
		}else {
			
			try {
				dbManager.executeQuery("SET foreign_key_checks = 0");
				//foreign key 연결 때문에 오류 뜸
				
//				dbManager.executeUpdate("delete from community where c_no = ?", table.getValueAt(row, 0));
//				dbManager.executeQuery("SET foreign_key_checks = 1");
				
				iMessage("삭제 되었습니다.");
				getinfo();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
