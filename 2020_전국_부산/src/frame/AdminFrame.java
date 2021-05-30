package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class AdminFrame extends BaseFrame{

	private JTabbedPane tab = new JTabbedPane();
	
	public AdminFrame() {
		super("�������۾�", 500, 630);
		
		pnl pnl = new pnl();
		pnl1 pnl1 = new pnl1();
		pnl2 pnl2 = new pnl2();
		
		tab.add("��������", pnl);
		tab.add("��������", pnl1);
		tab.add("�л�����", pnl2);
		
//		tab.add("��������", component);
//		tab.add("�л�����", component);
		
		add(tab,BorderLayout.CENTER);
		
	}
	
	private class pnl extends JPanel{
		
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"������ȣ","�̸�","�а�",}, 0);
	private JTable table = new JTable(dtm);
		
		public pnl() {
			
			JPanel pnlCenter = new JPanel(new BorderLayout());
			JPanel pnlSouth = new JPanel();
			
			JScrollPane jsp = new JScrollPane(table);
			
			JButton btnAllCancel = new JButton("��ν���");
			JButton btnCancel = new JButton("��������");
			JButton btnExit = new JButton("�ݱ�");
			
			pnlCenter.add(new JLabel("���� ��û ����",JLabel.LEFT),BorderLayout.NORTH);
			pnlCenter.add(jsp,BorderLayout.CENTER);
			
			pnlSouth.add(btnAllCancel);
			pnlSouth.add(btnCancel);
			pnlSouth.add(btnExit);
			
			btnAllCancel.setPreferredSize(new Dimension(145, 25));
			btnCancel.setPreferredSize(new Dimension(145, 25));
			btnExit.setPreferredSize(new Dimension(145, 25));
			
			add(pnlCenter,BorderLayout.CENTER);
			add(pnlSouth,BorderLayout.SOUTH);
			
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
			
			getinfo();
			
			btnAllCancel.addActionListener(e -> allCancel());
			btnCancel.addActionListener(e -> cancel());
			btnExit.addActionListener(e -> openFrame(new MainFrame()));
			
		}
		
		private void getinfo() {
			dtm.setRowCount(0);
			try {
				ResultSet rs = dbManager.executeQuery("SELECT * FROM pro inner join department on d_code = p_dno where p_gr = '�ɻ���'");
				while (rs.next()) {
					dtm.addRow(new Object[] {
							rs.getString(1),
							rs.getString(2),
							rs.getString(9)		
					});
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void allCancel() {
			
			for (int i = 0; i < table.getRowCount(); i++) {
				updateGrade((String) table.getValueAt(i, 0));
			}
			
			getinfo();
			
		}
		
		private void cancel() {
			int row = table.getSelectedRow();
			
			if(row == -1) {
				eMessage("���� �׸��� �����ϼ���");
			} else {
				updateGrade((String) table.getValueAt(row, 0));
				getinfo();
			}
		}
		
		private void updateGrade(String p_code) {
			try {
				ResultSet rs = dbManager.executeQuery("select * from pro where p_code = ?", p_code);
				
				if (rs.next()) {
					String pw = rs.getString(8);
					
					dbManager.executeUpdate("update pro set p_gr = ?, p_pw = ? where p_code = ?","��ȸ��", pw, p_code);						
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	private class pnl1 extends JPanel{
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"������ȣ","�̸�","�а�",}, 0);
	private JTable table = new JTable(dtm);
	
	private JTextField tfCode = new JTextField();
	private JComboBox<String> comD = new JComboBox<>();
	private JComboBox<String> comSt = new JComboBox<>(new String[] {"����","����","����"});
		
		public pnl1() {
			
			JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JPanel pnlCenter = new JPanel(new BorderLayout());
			
			JPanel pnlSN = new JPanel();
			JPanel pnlSS = new JPanel();
			JPanel pnlSouth = new JPanel(new BorderLayout());
			
			JScrollPane jsp = new JScrollPane(table);
			
			JButton btnSave = new JButton("����");
			JButton btnExit = new JButton("�ݱ�");
			
			pnlNorth.add(new JLabel("�а�",JLabel.LEFT));
			pnlNorth.add(comD);
			
			pnlSN.add(new JLabel("������ȣ"));
			pnlSN.add(tfCode);
			pnlSN.add(new JLabel("�ٹ�����"));
			pnlSN.add(comSt);
			
			pnlSS.add(btnSave);
			pnlSS.add(btnExit);

			pnlCenter.add(pnlNorth,BorderLayout.NORTH);
			pnlCenter.add(jsp,BorderLayout.CENTER);
		
			pnlSouth.add(pnlSN,BorderLayout.NORTH);
			pnlSouth.add(pnlSS,BorderLayout.SOUTH);
			
			add(pnlCenter,BorderLayout.CENTER);
			add(pnlSouth,BorderLayout.SOUTH);
			
			tfCode.setPreferredSize(new Dimension(160, 30));
			comSt.setPreferredSize(new Dimension(160, 30));
			
			btnSave.setPreferredSize(new Dimension(180, 30));
			btnExit.setPreferredSize(new Dimension(180, 30));
			
			btnSave.addActionListener(e -> DataSave());
			btnExit.addActionListener(e -> openFrame(new MainFrame()));
			try {
				ResultSet rs = dbManager.executeQuery("select* from department");
				while (rs.next()) {
					comD.addItem(rs.getString(2));
				} 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					getTableInfo();
				}
			});
			
			tfCode.setEnabled(false);
			comD.addItemListener(e -> getComInfo());
			
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
			
			getComInfo();
			
		}
		
		private void getComInfo() {
	
		dtm.setRowCount(0);
		
		String name = (String) comD.getSelectedItem();
		
			try {
				ResultSet rs = dbManager.executeQuery("select pro.* from department inner join pro on p_dNo = d_code where d_name = ?", name);
				while (rs.next()) {
					dtm.addRow(new Object[] {
						rs.getString(1),
						rs.getString(2),
						rs.getString(5)
					});

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			tfCode.setText("");
			comSt.setSelectedIndex(0);
			
		}
		
		private void getTableInfo() {
			int row = table.getSelectedRow();
			
			tfCode.setText((String) table.getValueAt(row, 0));
			comSt.setSelectedItem(table.getValueAt(row, 2));

		}
		
		private void DataSave() {
		
			int row = table.getSelectedRow();
		
			String st = (String) comSt.getSelectedItem();
			
			if(row == -1) {
				return;
			}
			
			try {
				dbManager.executeUpdate("update pro set p_st = ? where p_code = ?", st,table.getValueAt(row, 0));
				getComInfo();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private class pnl2 extends JPanel{
		
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"�й�","�̸�","��ȭ��ȣ"}, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	
	private JTable table = new JTable(dtm);
	
	private JComboBox<String> comD = new JComboBox<>();
	private JComboBox<String> comD1 = new JComboBox<>();
	private JComboBox<String> comG = new JComboBox<>(new String[] {"��", "��"});
	
	private JTextField tfCode = new JTextField();
	private JTextField tfPw = new JTextField();
	private JTextField tfName = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JTextField tfTel = new JTextField();
		
		public pnl2() {
			setLayout(null);
			
			JLabel lb = new JLabel("�а�");
			
			JLabel lb1 = new JLabel("�й�");
			JLabel lb2 = new JLabel("��ȣ");
			JLabel lb3 = new JLabel("�̸�");
			JLabel lb4 = new JLabel("����");
			JLabel lb5 = new JLabel("��ȭ��ȣ");
			JLabel lb6 = new JLabel("�ּ�");
			JLabel lb7 = new JLabel("�а�");
			
			JScrollPane jsp = new JScrollPane(table);
			
			JButton btnDelete = new JButton("����");
			JButton btnExit = new JButton("�ݱ�");
			JButton btnInsert = new JButton("���");
			
			add(lb);
			add(lb1);
			add(lb2);
			add(lb3);
			add(lb4);
			add(lb5);
			add(lb6);
			add(lb7);
			
			add(comD);
			add(comD1);
			add(comG);
			
			add(tfAddress);
			add(tfCode);
			add(tfName);
			add(tfPw);
			add(tfTel);
			
			add(btnDelete);
			add(btnExit);
			add(btnInsert);
			
			add(jsp);
			
			lb.setBounds(15, 40, 40, 30);
			comD.setBounds(48, 40, 170, 30);
			
			btnDelete.setBounds(270, 40, 60, 30);
			btnExit.setBounds(335, 40, 60, 30);
			
			jsp.setBounds(10, 75, 460, 250);
			
			lb1.setBounds(15, 335, 30, 30);
			tfCode.setBounds(45, 335, 130, 25);
			
			lb2.setBounds(225, 335, 30, 30); //365 380 
			tfPw.setBounds(255, 335, 130, 25);
			
			lb3.setBounds(15, 380, 30, 30);
			tfName.setBounds(45, 380, 130, 25);
			
			lb4.setBounds(225, 380, 30, 30);
			comG.setBounds(255, 380, 130, 25);
			
			lb5.setBounds(15, 435, 60, 30);
			tfTel.setBounds(68, 435, 140, 25);
			
			lb6.setBounds(15, 475, 60, 30);
			tfAddress.setBounds(68, 475, 140, 25);
			
			lb7.setBounds(15, 515, 60, 30);
			comD1.setBounds(68, 515, 140, 25);
			
			btnInsert.setBounds(300, 515, 60, 25);
			
			btnInsert.addActionListener(e -> insert());
			btnExit.addActionListener(e -> openFrame(new MainFrame()));
			btnDelete.addActionListener(e -> delete());
			
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
			
			String name = (String) comD.getSelectedItem();
			
			comD.addItemListener(e -> getComInfo());
			comD1.addItemListener(e -> maxCode());
			
			try {
				ResultSet rs = dbManager.executeQuery("select* from department");
				
				while (rs.next()) {
					comD.addItem(rs.getString(2));
					comD1.addItem(rs.getString(2));
				} 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {
						try {
							boolean print = table.print();
						
							if (!print) {
								return;
							}

						} catch (PrinterException e1) {
//							JOptionPane.showMessageDialog(null, e1.getMessage());
							e1.printStackTrace();
						}
						
					}
				}
			});

			getComInfo();
		}
		
		private void getComInfo() {
			
			dtm.setRowCount(0);

			String name = (String) comD.getSelectedItem();
			
			try {
			
				ResultSet rs = dbManager.executeQuery("select s_code, s_name, s_tel  from student inner join department on d_code = s_dcode where d_name = ?", name);
				
				while (rs.next()) {
					dtm.addRow(new Object[] {
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
					});
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void maxCode() {
			
			String name = (String) comD1.getSelectedItem();
			try {
				ResultSet rs = dbManager.executeQuery("select max(s_code) from student inner join department on d_code = s_dcode where d_name = ?", name);
				
				if (rs.next()) {
					tfCode.setText(String.valueOf(rs.getInt(1) + 1));					
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} 
		
		private void delete() {
			int row = table.getSelectedRow();
			
			if(row == -1) {
				eMessage("���� �׸��� �����ϼ���.");
				return;
			}
			
			try {
				
				dbManager.executeUpdate("delete from info where i_sCode = ?", table.getValueAt(row, 0));
				dbManager.executeUpdate("delete from student where s_Code = ?", table.getValueAt(row, 0));
				
				getComInfo();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void insert() {
			
			String code = tfCode.getText();
			String pw = tfPw.getText();
			String name = tfName.getText();
			String gen = (String) comG.getSelectedItem();
			String tel = tfTel.getText();
			String addr = tfAddress.getText();
			String de = (String) "C0" + comD1.getSelectedIndex();
			
			System.out.println(de);
			
			if(code.isEmpty() || pw.isEmpty() || name.isEmpty() || gen.isEmpty() || tel.isEmpty() || addr.isEmpty() || comD1.getSelectedIndex() == -1) {
				eMessage("��ĭ�� �����մϴ�.");
				return;
			}
			
			try {
				
				dbManager.executeUpdate("insert into student values(?, ?, ?, ?, ?, ?, ?)", code, pw, name, gen, tel, addr, de);
				getComInfo();
				
				tfCode.setText("");
				tfPw.setText("");
				tfName.setText("");
				tfTel.setText("");
				tfAddress.setText("");
				comG.setSelectedIndex(0);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
}

