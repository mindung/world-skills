package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class MenuCareFrame extends BaseFrame{

	private JComboBox<String> comGroup = new JComboBox<>(new String[] {"한식","중식","일식","양식"});
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"", "", "mealName","price","maxCount","todayMeal"}, 0){
		public java.lang.Class<?> getColumnClass(int column) {
			if (column == 0) {
				return Boolean.class;
			} else {
				return super.getColumnClass(column);
			}
		};
	};
	private JTable table = new JTable(dtm) ;
	
	private JButton btnAllSelected = new JButton("모두 선택");
	private JButton btnSearch = new JButton("검색");
	private JButton btnUpdate = new JButton("수정");
	private JButton btnDlete = new JButton("삭제");
	private JButton btnTodayMeal = new JButton("오늘의 메뉴선정");
	private JButton btnExit = new JButton("닫기");
	
	public MenuCareFrame() {
		super("메뉴 관리", 650, 600);
		
		JPanel pnlNorth = new JPanel();
		
		JScrollPane jsp = new JScrollPane(table);
		
		pnlNorth.add(btnAllSelected);
		pnlNorth.add(new JLabel("종류:"));
		pnlNorth.add(comGroup);
		pnlNorth.add(btnSearch);
		pnlNorth.add(btnUpdate);
		pnlNorth.add(btnDlete);
		pnlNorth.add(btnTodayMeal);
		pnlNorth.add(btnExit);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);
		
		addTable(1);
		
		btnAllSelected.addActionListener(e -> selectAllCheckBox());
		btnUpdate.addActionListener(e -> updateTable());
		btnSearch.addActionListener(e -> addTable(comGroup.getSelectedIndex() +1));
		btnDlete.addActionListener(e -> deleteTable());
		btnTodayMeal.addActionListener(e -> todayMeal());
		btnExit.addActionListener(e -> openFrame(new AdminFrame()));
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setWidth(0);
		
		table.addMouseListener(new MouseAdapter() {
	
			@Override
			public void mouseClicked(MouseEvent e) {
//				table.getSelectedColumn() ==0
				if(table.getSelectedColumn() == 0) {
					btnAllSelected.setEnabled(true);
					
					if(getSelectedCheckBoxCount() == table.getRowCount()) { // cnt 개수와 테이블 행의 개수가 같으면 전체선택
						btnAllSelected.setEnabled(false);
					}
				}
			}
		});
	}
	
	private void deleteTable() {
		int cnt = getSelectedCheckBoxCount();
		
		if(cnt == 0) {
			eMessage("삭제할 메뉴를 선택해주세요.");
			
		}else {
			try {
				
				for (int i = 0; i < table.getRowCount(); i++) { // 모든 테이블의 행을 돌면서, 해당되는 체크박스가 체크되면 삭제..??
					if((boolean) table.getValueAt(i, 0) == true) { // 체크박스 체크 됬으면
						dbManager.executeUpdate("delete from meal where mealNo = ?", table.getValueAt(i, 1));						
					}
				}				
				
				addTable(comGroup.getSelectedIndex()+1);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void updateTable() {
	
		int cnt = getSelectedCheckBoxCount();
		
		if(cnt == 0) {
			eMessage("수정할 메뉴를 선택해주세요");
			
		}else if(cnt != 1) {
			eMessage("하나씩 수정가능합니다.");
		}else {
			
			openFrame(new UpdateMenuFrame((String) comGroup.getSelectedItem(),(int) table.getValueAt(getSelectedCheckboxRow(), 1)));
		}
		
	}
	
	private void todayMeal() {
		
		int cnt = getSelectedCheckBoxCount();
		
		if(cnt > 25) {
			eMessage("25개를 초과할 수 없습니다.");
		}else {
			for (int i = 0; i < table.getRowCount(); i++) {
				if((boolean) table.getValueAt(i, 0)) {
					
					try {
						dbManager.executeUpdate("update meal set todayMeal = 1 where mealno = ? ", table.getValueAt(i, 1));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			addTable(comGroup.getSelectedIndex() +1);
		}

	}
	public static void main(String[] args) {
		new MenuCareFrame().setVisible(true);
	}

	private void selectAllCheckBox() {
		
		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(true, i, 0);
			btnAllSelected.setEnabled(false);
		}
	}
	
	private int getSelectedCheckBoxCount() {
		
		int cnt = 0;
		
		for (int i = 0; i < table.getRowCount(); i++) {
			
			if((boolean) table.getValueAt(i, 0)== true) {
				cnt++;
			}		
		}
		
		return cnt;
		
	}
	
	private int getSelectedCheckboxRow() {
		
		for (int i = 0; i < table.getRowCount(); i++) {
			
			if((boolean) table.getValueAt(i, 0)) {
				return i;
			}		
		}
		
		return -1; // -1은 임의로 -1 
		
	}
	
	private void addTable(int no) {
		
		int cuisineNo  = comGroup.getSelectedIndex()+1;
		
		dtm.setRowCount(0);
		
		try {
			
			ResultSet rs = dbManager.executeQuery("select mealNo, mealName, price, maxcount, todaymeal from meal inner join cuisine on cuisine.cuisineNo = meal.cuisineNo where meal.cuisineNo = ?", cuisineNo);
			
			while(rs.next()) {
				dtm.addRow(new Object[] {
					Boolean.FALSE, rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5) == 1 ? "Y" : "N"
				});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
