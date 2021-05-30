package frame;

import java.awt.BorderLayout;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SelectFrame extends BaseFrame {
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"code", "name","birth","tel","address","company"},0);
	private JTable table = new JTable(dtm);
	
	private JTextField tfName = new JTextField(13);
	
	public SelectFrame() {
		super("�� ��ȸ", 800, 800);
		
		JPanel pnlNorth = new JPanel();
		
		JButton btnSearch = new JButton("��ȸ");
		JButton btnShowAll = new JButton("��ü����");
		JButton btnUpdate = new JButton("����");
		JButton btnDelete = new JButton("����");
		JButton btnExit = new JButton("�ݱ�");
		
		JScrollPane jsp = new JScrollPane(table);
		
		pnlNorth.add(new JLabel("����"));
		pnlNorth.add(tfName);
		pnlNorth.add(btnSearch);
		pnlNorth.add(btnShowAll);
		pnlNorth.add(btnUpdate);
		pnlNorth.add(btnDelete);
		pnlNorth.add(btnExit);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);
		
		getInfo();
		
		btnShowAll.addActionListener(e -> showAll());
		btnSearch.addActionListener(e -> getInfo());
		btnUpdate.addActionListener(e -> update());
		btnDelete.addActionListener(e -> delete());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
	}
	
	public static void main(String[] args) {
		new SelectFrame().setVisible(true);
	}
	
	private void showAll() {
		
		dtm.setRowCount(0);
		try {
		
			ResultSet rs = dbManager.executeQuery("select * from customer ");
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						
				});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void getInfo() {
		
		String name = tfName.getText();
			
		dtm.setRowCount(0);
		try {
		
			ResultSet rs = dbManager.executeQuery("select * from customer where name like '%" + name + "%'");
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						
				});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void update() {

		int row = table.getSelectedRow();
		
		if(row == -1) {
			eMessage("�޽���", "������ ���� �������ּ���");
		}else {
			String name = (String) table.getValueAt(table.getSelectedRow(), 1);
			new UpdateFrame(name).setVisible(true);
			
			System.out.println(name);
			
		}
		
	}
	
	private void delete() {
		
//		dtm.setRowCount(0);
		
		int row = table.getSelectedRow();
		
		if(row == -1) {
			eMessage("�޽���", "������ ���� �������ּ���");
		}else {
			
			String code = (String) table.getValueAt(row, 0);
			String name = (String) table.getValueAt(row, 1);
			
			int ok = JOptionPane.showConfirmDialog(null, name + " ���� ���� �����Ͻðڽ��ϱ�?", "������ ����", JOptionPane.OK_CANCEL_OPTION);
			
			if (ok == JOptionPane.OK_OPTION) {
				
				try {
					dbManager.executeUpdate("delete from customer where code =?", code);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
