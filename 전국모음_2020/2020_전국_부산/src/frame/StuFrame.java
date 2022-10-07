package frame;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StuFrame extends BaseFrame{

	private JTabbedPane tab = new JTabbedPane();
	
	private pnl pnl = new  pnl();
	private pnl1 pnl1 = new pnl1();
	
	public StuFrame() {
		super(String.format("%s %s", S_Code,Info), 500	, 650);
		
		tab.add(pnl, "������û");
		tab.add(pnl1, "������ȸ");
		
		add(tab);
	}

	
	public static void main(String[] args) {
		new StuFrame().setVisible(true);
	}
	
	private class pnl extends JPanel{
		private DefaultTableModel dtm = new DefaultTableModel(new String[] {"","�а�","����","��������"}, 0);
		private JTable table = new JTable(dtm);
		
		public pnl() {
		
			setLayout(new BorderLayout());
			
			JPanel pnlSouth = new JPanel();
			
			JScrollPane jsp = new JScrollPane(table);
			
			JButton btnApply = new JButton("������û");
			JButton btnCancel = new JButton("�������");
			JButton btnExit = new JButton("�����ݱ�");
			
			pnlSouth.add(btnApply);
			pnlSouth.add(btnCancel);
			pnlSouth.add(btnExit);
			
			add(jsp,BorderLayout.CENTER);
			add(pnlSouth,BorderLayout.SOUTH);
			
			btnApply.addActionListener(e -> apply());
			btnCancel.addActionListener(e -> cancel());
			btnExit.addActionListener(e -> openFrame(new LoginStuFrame()));
			
			getInfo();
			
			table.getColumn("").setMinWidth(0);
			table.getColumn("").setMaxWidth(0);
			
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}
		
		private void getInfo() {
			dtm.setRowCount(0);
			
			try {
				ResultSet rs = dbManager.executeQuery("select c_code, d_name, c_title From course inner join department on course.c_dCode = department.d_code");
				while (rs.next()) {
					dtm.addRow(new Object[] {
							rs.getString(1),rs.getString(2),rs.getString(3),
					});
				}
				
				for (int i = 0; i < dtm.getRowCount(); i++) {
					
					rs = dbManager.executeQuery("SELECT * FROM info where i_scode = ? and i_Ccode = ?", S_Code, table.getValueAt(i, 0));
					table.setValueAt(rs.next() ? "O" : "X", i, 3);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		private void apply() {
			int row = table.getSelectedRow();
			
			if (row == -1) {
				eMessage("������ ���� �����ϼ���.");
				return;
			}
			
			if (table.getValueAt(row, 3) == "O") {
				eMessage("�̹� �������� �����Դϴ�.");
			} else {
				
				int yn = JOptionPane.showConfirmDialog(null, table.getValueAt(row, 2) + " ������ ��û�Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION);
				
				if(yn == JOptionPane.YES_OPTION) {
					
					try {
						dbManager.executeUpdate("insert into info values(?, ?, ?, ?)", S_Code,table.getValueAt(row, 0),"","");
						
						getInfo();
						pnl1.getinfo();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		private void cancel() {
			int row = table.getSelectedRow();
			
			if(row == -1) {
				eMessage("������ ���� �����ϼ���.");
				return;
			}

			if(table.getValueAt(row, 3) == "X") {
				eMessage("���� �������� ������ �ƴմϴ�.");
			} else {
				try {
					ResultSet rs = dbManager.executeQuery("SELECT * FROM info WHERE i_sCode = ? and i_cCode = ?", S_Code, table.getValueAt(row, 0));
				
					if (rs.next()) {
						String grade = rs.getString(3);
						
						if (grade.equals("")) {
							
							int yn = JOptionPane.showConfirmDialog(null, " ������ ����Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION);
							if (yn == JOptionPane.YES_OPTION) {
								
								try {
									dbManager.executeUpdate("delete from info where i_cCode = ? and i_sCode = ? ", table.getValueAt(row, 0), S_Code);
									getInfo();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						} else {
							eMessage("������ �ο��� ������ ������Ұ� �Ұ��մϴ�.");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class pnl1 extends JPanel{
		
		private DefaultTableModel dtm = new DefaultTableModel(new String[] {"�����ڵ�","�����","����","Ư�����"}, 0);
		private JTable table = new JTable(dtm);
		
		private JTextField tfInfo = new JTextField(30);
		
		public pnl1() {
		
			setLayout(new BorderLayout());
			
			JPanel pnlCenter = new JPanel(new BorderLayout());
			
			JPanel pnlSouth = new JPanel();
			JPanel pnlS = new JPanel();
			
			JScrollPane jsp = new JScrollPane(table);
			
			JButton btnExit = new JButton("�ݱ�");
			
			pnlSouth.add(btnExit);
			
			pnlSouth.add(new JLabel("Ư�����"));
			pnlSouth.add(tfInfo);
			
			pnlS.add(btnExit);
			
			pnlCenter.add(jsp,BorderLayout.CENTER);
			pnlCenter.add(pnlSouth,BorderLayout.SOUTH);
			
			add(pnlCenter,BorderLayout.CENTER);
			add(pnlS,BorderLayout.SOUTH);
			
			btnExit.addActionListener(e -> openFrame(new MainFrame()));
			
			getinfo();
			
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
			
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					tableClick();
				}
			});
		}
		
		private void getinfo() {
			dtm.setRowCount(0);
			
			try {
				ResultSet rs = dbManager.executeQuery("select c_title, info.* from info inner join course on c_code = i_cCode where i_sCode = ?",S_Code);
				while (rs.next()) {
					dtm.addRow(new Object[] {
						rs.getString(3),
						rs.getString(1),
						rs.getString(4),
						rs.getString(5).equals("") ? "" : "V"
					});
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void tableClick() {
			int row = table.getSelectedRow();
							
				try {
					ResultSet rs = dbManager.executeQuery("select * from info where i_cCode = ?", table.getValueAt(row, 0));
					if(rs.next()) {
						tfInfo.setText(rs.getString(4));
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}

