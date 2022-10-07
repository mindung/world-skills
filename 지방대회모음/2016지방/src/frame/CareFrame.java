package frame;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CareFrame extends BaseFrame {

	private JTextField tfCode = new JTextField();
	private JTextField tfBirth = new JTextField();
	private JTextField tfTel = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfMonthAmount = new JTextField();
	
	private JComboBox<String >comName = new JComboBox<>();
	private JComboBox<String >comContract = new JComboBox<>();
	private JComboBox<String> comAdminName = new JComboBox<>();
	
	private DefaultTableModel dtm = new DefaultTableModel(new String [] {"customerCode","contractName","regPrcie","regDate","monthPrice","adminName"},0);
	private JTable table = new JTable(dtm);
	
	public CareFrame() {
		
		super("������",800, 750);
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlNEast = new JPanel(new GridLayout(3, 2));
		JPanel pnlNWest = new JPanel(new GridLayout(4, 2));
		JPanel pnlNCenter = new JPanel();
		
		JLabel lbTitle = new JLabel("< �� ���� �����Ȳ >",JLabel.CENTER);
		
		JScrollPane jsp = new JScrollPane(table);
		
		JButton btnInsert = new JButton("����");
		JButton btnDelete = new JButton("����");
		JButton btnSaveAs = new JButton("���Ϸ�����");
		JButton btnExit = new JButton("�ݱ�");
		
		pnlNWest.add(new JLabel("���ڵ�:"));
		pnlNWest.add(tfCode);
		pnlNWest.add(new JLabel("�� �� ��:"));
		pnlNWest.add(comName);
		pnlNWest.add(new JLabel("�������:"));
		pnlNWest.add(tfBirth);
		pnlNWest.add(new JLabel("����ó:"));
		pnlNWest.add(tfTel);
		
		pnlNEast.add(new JLabel("�������:"));
		pnlNEast.add(comContract);
		pnlNEast.add(new JLabel("���Աݾ�:"));
		pnlNEast.add(tfPrice);
		pnlNEast.add(new JLabel("�������:"));
		pnlNEast.add(tfMonthAmount);

		pnlNCenter.add(new JLabel("�����:"));
		pnlNCenter.add(comAdminName);
		pnlNCenter.add(btnInsert);
		pnlNCenter.add(btnDelete);
		pnlNCenter.add(btnSaveAs);
		pnlNCenter.add(btnExit);
		
		pnlCenter.add(pnlNEast);
		pnlCenter.add(pnlNWest);
		pnlCenter.add(pnlNCenter);
		pnlCenter.add(lbTitle);
		pnlCenter.add(jsp);
		
		add(pnlCenter,BorderLayout.CENTER);

		pnlNWest.setBounds(100, 20, 300, 100);
		pnlNEast.setBounds(450, 20, 300, 100);
		pnlNCenter.setBounds(0, 130, 800, 35);
		lbTitle.setBounds(300, 190, 200, 30);
		jsp.setBounds(0,220, 800, 400);
		
		btnInsert.addActionListener(e -> insert());
		btnDelete.addActionListener(e -> delete());
		btnSaveAs.addActionListener(e -> saveAS());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			 table.getColumnModel().getColumn(i).setCellRenderer(CenterRenderer);
		}
		
		contractInfo();
		getNameInfo();
		getAdminNameInfo();
		getinfo();
		getTableList();
		
		comName.addItemListener(e -> {
			getinfo();
			getTableList();
			
		});
		
	}
	
	private void insert() {
		
		String code = tfCode.getText();
		String contract = (String) comContract.getSelectedItem();
		String regPrice = tfPrice.getText();
		String monthPrice = tfMonthAmount.getText();
		String regDate = LocalDate.now().toString();
		String adminName = (String) comAdminName.getSelectedItem();
		
		if(code.isEmpty() || contract.isEmpty() || monthPrice.isEmpty() || regDate.isEmpty() || regPrice.isEmpty() || adminName.isEmpty()) {
			eMessage("�޽���", "��ĭ�� ��� ä���ּ���");
		}else {
			try {
				dbManager.executeUpdate("insert into contract values(?,?,?,?,?,?)",code,contract,regPrice,regDate,monthPrice,adminName);
				getTableList();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	public static void main(String[] args) {
		new CareFrame().setVisible(true);
	}
	
	private void getNameInfo() { // �� ���� ���� ������ ���� �ڵ�

		try {
			ResultSet  rs = dbManager.executeQuery("select * from customer ");
			while (rs.next()) {
				comName.addItem(rs.getString(2));
			}
				comName.setSelectedIndex(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void contractInfo() { // �����ǰ���� ���� ������ ���� �ڵ�
		
		try {
			ResultSet rs = dbManager.executeQuery("select distinct contractName from contract ");
			while (rs.next()) {
				comContract.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getAdminNameInfo() { // ����� ���� ������ ���� �ڵ�
		
		try {
			ResultSet  rs = dbManager.executeQuery("select * from contract");
			while (rs.next()) {
				comAdminName.addItem(rs.getString(6));
			}
				comAdminName.setSelectedIndex(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getinfo() { // ���� ������ ���� �ڵ�
		
		String name = (String) comName.getSelectedItem();
		
		tfCode.setText("");
		tfBirth.setText("");
		Tel = "";
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from customer where name = ?", name);
			if(rs.next()) {
				code = rs.getString(1);
				tfCode.setText(rs.getString(1));
				tfBirth.setText(rs.getString(3));
				tfTel.setText(rs.getString(4));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getTableList() { // ���̺� ���� ������ ���� �ڵ�
		
		dtm.setRowCount(0);
		try {
			ResultSet rs = dbManager.executeQuery("select* from contract where customerCode = ? order by regDate desc",code);
			
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
	
	private void delete() {
		
		int row = table.getSelectedRow();
		System.out.println(row);
		
		if(row == -1) {
			eMessage("�޽���", "������ ����� �������ּ���.");
		}else {

			String code = (String) table.getValueAt(row, 0);
			String contract = (String) table.getValueAt(row, 1);
			
			int cnt = JOptionPane.showConfirmDialog(null, code + "(" + contract + ")�� �����Ͻðڽ��ϱ�?", "������� ����", JOptionPane.OK_CANCEL_OPTION);

			if(cnt == JOptionPane.OK_OPTION) {
				
				try {
					dbManager.executeUpdate("delete from contract where customerCode = ? and contractName = ?", code,contract);
					getTableList();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void saveAS() {		
		
		FileDialog dialog = new FileDialog(this, "�ؽ�Ʈ ���Ϸ� ����", FileDialog.SAVE);
		
		dialog.setFile("��������Ȳ.txt"); // ���� �̸� ����
		dialog.setVisible(true);

		if(dialog.getFile() == null) {
			return; // ������ ������ ���� ��� ����			
		}
		
		try {
			FileWriter fw = new FileWriter(dialog.getDirectory() + dialog.getFile());
											// ���� ��� + ���� ��
			// \n �ٹٲ�  	\t ��	
			fw.write(String.format("���� : %s(%s)\n\n", comName.getSelectedItem(),tfCode.getText()));
			fw.write(String.format("����ڸ� : %s\n\n", comAdminName.getSelectedItem()));
			fw.write("�����ǰ\t���Աݾ�\t������\t�������\n");
			for (int i = 0; i < table.getRowCount(); i++) {
				fw.write(String.format("%s\t%s\t%s\t%s\t\n", table.getValueAt(i, 0),table.getValueAt(i, 1),table.getValueAt(i, 2),table.getValueAt(i, 3)));
			}
			
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
}
