package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class AdminFrame extends BaseFrame{
	
	private JTextField tfId = new JTextField(8);
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"","아이디","이름","나이","성별","기능"}, 0);
	private JTable table = new JTable(dtm);
	
	public AdminFrame() {
		super("메인", 700, 400);
		
		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter= new JPanel();
		
		JButton btnSearch = new JButton("검색");
		JButton btnLogOut = new JButton("로그아웃");
		
		JScrollPane jsp = new JScrollPane(table);
		
		pnlNorth.add(new JLabel("        아이디"));
		pnlNorth.add(tfId);
		pnlNorth.add(btnSearch);
		pnlNorth.add(new JLabel("                           "));
		pnlNorth.add(btnLogOut);
		
		pnlCenter.add(jsp);
		
		jsp.setPreferredSize(new Dimension(670, 300));
		pnlCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlNorth,BorderLayout.NORTH);
		
		btnLogOut.addActionListener(e -> openFrame(new MainFrame()));
		btnSearch.addActionListener(e -> search());
		search();
		
		table.getColumn("").setPreferredWidth(0);
		table.getColumn("").setMinWidth(0);
		table.getColumn("").setMaxWidth(0);
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(table.getSelectedColumn() == 5) {
					
					int index = (int) table.getValueAt(table.getSelectedRow(), 0); // 숨겨논 index
					
//					openFrame(new MemberFrame(index)); // 상속!!!
					openFrame(new MemberFrame(index));
							
				}
			};
		});
	}
	
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
	
	private void search() {
		String id = tfId.getText();
		
		try {
		dtm.setRowCount(0);
		
			ResultSet rs = dbManager.executeQuery("select * from tbl_member where M_id like '%" + id +"%'");
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(4),
						rs.getString(5),
						rs.getString(7),
						"      보기   "
						
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		ResultSet rs = dbManager.executeQuery("select *  from tbl_member where M_id like '%" + id + "%'");

	}
}
