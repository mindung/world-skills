package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FriendFrame extends BaseFrame {

	private JTextField tfName = new JTextField(15);
	private JCheckBox cbAllSelected = new JCheckBox("전체 선택");
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"","","이름", "생년월일", "전화번호"}, 0);
	private JTable table = new JTable(dtm);
	
	private int pNo;

	public FriendFrame(int pNo) {
		super("친구목록", 600, 450);
		
		this.pNo = pNo;

		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNEast = new JPanel();
		JPanel pnlNWest = new JPanel();
		
		JScrollPane jsp = new JScrollPane(table);
		
		JButton btnSearch = new JButton("검색");
		JButton btnReset = new JButton("초기화");
		JButton btnSend = new JButton("보내기");

		pnlNWest.add(new JLabel("이름"));
		pnlNWest.add(tfName);
		pnlNWest.add(btnSearch);
		
		pnlNEast.add(cbAllSelected);
		pnlNEast.add(btnReset);
		
		pnlNorth.add(pnlNEast, BorderLayout.EAST);
		pnlNorth.add(pnlNWest, BorderLayout.WEST);
	
		pnlSouth.add(btnSend);
		
		add(pnlNorth , BorderLayout.NORTH);
		add(jsp);
		add(pnlSouth ,BorderLayout.SOUTH);
		
		getFriends();
		
		btnSearch.addActionListener(e -> getFriends());
		btnReset.addActionListener(e -> reset());
		btnSend.addActionListener(e -> send());

//		table.getColumnModel().getColumn(0).setMaxWidth(0);
//		table.getColumnModel().getColumn(0).setMinWidth(0);
//		table.getColumnModel().getColumn(0).setPreferredWidth(0);
//
//		table.getColumnModel().getColumn(1).setMaxWidth(30);
//		table.getColumnModel().getColumn(1).setMinWidth(30);
//		table.getColumnModel().getColumn(1).setPreferredWidth(30);

		JCheckBox box = new JCheckBox();
		box.setHorizontalAlignment(JLabel.CENTER);

		table.getColumnModel().getColumn(1).setCellRenderer(dcr);
		table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(box));

		table.setAutoCreateRowSorter(true);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cbAllSelected.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				
				for (int i = 0; i < table.getRowCount(); i++) {
					table.setValueAt(cbAllSelected.isSelected(), i, 1);
				}
				 
			};
		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (table.getSelectedColumn() == 1) {
					cbAllSelected.setSelected(getCheckedCount() == table.getRowCount());
				}
			};
		});
	}
	
	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	public static void main(String[] args) {
		new FriendFrame(1425).setVisible(true);
	}
	
	private int getCheckedCount() {
		int cnt = 0;

		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) table.getValueAt(i, 1)) {
				cnt++;
			}
		}
		
		return cnt;
	}
	private void getFriends() {
		String name = tfName.getText();
		
		dtm.setRowCount(0);
		cbAllSelected.setSelected(false);

		try {
			ResultSet rs = dbManager.executeQuery("select * from user where u_no <> ? and u_name like '%" + name + "%'", u_no);
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
					rs.getInt(1),
					false,
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
				});
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void reset() {
		tfName.setText("");
		getFriends();
	}
	
	private void send() {
		if (getCheckedCount() == 0) {
			eMessage("청첩장을 보낼 친구를 선택해주세요.");
		} else {
			for (int i = 0; i < table.getRowCount(); i++) {
				if ((boolean) table.getValueAt(i, 1)) {
					try {
						dbManager.executeUpdate("INSERT INTO invitation VALUES (0, ?, ?, ?)", pNo, u_no, (int) table.getValueAt(i, 0));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			iMessage("청첩장을 보냈습니다.");
			openFrame(new MainFrame());
		}
	}
}
