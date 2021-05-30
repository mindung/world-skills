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
		
		super("보험계약",800, 750);
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlNEast = new JPanel(new GridLayout(3, 2));
		JPanel pnlNWest = new JPanel(new GridLayout(4, 2));
		JPanel pnlNCenter = new JPanel();
		
		JLabel lbTitle = new JLabel("< 고객 보험 계약현황 >",JLabel.CENTER);
		
		JScrollPane jsp = new JScrollPane(table);
		
		JButton btnInsert = new JButton("가입");
		JButton btnDelete = new JButton("삭제");
		JButton btnSaveAs = new JButton("파일로저장");
		JButton btnExit = new JButton("닫기");
		
		pnlNWest.add(new JLabel("고객코드:"));
		pnlNWest.add(tfCode);
		pnlNWest.add(new JLabel("고 객 명:"));
		pnlNWest.add(comName);
		pnlNWest.add(new JLabel("생년월일:"));
		pnlNWest.add(tfBirth);
		pnlNWest.add(new JLabel("연락처:"));
		pnlNWest.add(tfTel);
		
		pnlNEast.add(new JLabel("보험상종:"));
		pnlNEast.add(comContract);
		pnlNEast.add(new JLabel("가입금액:"));
		pnlNEast.add(tfPrice);
		pnlNEast.add(new JLabel("월보험료:"));
		pnlNEast.add(tfMonthAmount);

		pnlNCenter.add(new JLabel("담당자:"));
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
			eMessage("메시지", "빈칸을 모두 채워주세요");
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
	
	private void getNameInfo() { // 고객 선택 정보 가지고 오는 코드

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
	private void contractInfo() { // 보험상품선택 정보 가지고 오는 코드
		
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
	private void getAdminNameInfo() { // 담당자 정보 가지고 오는 코드
		
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
	
	private void getinfo() { // 정보 가지고 오는 코드
		
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
	
	private void getTableList() { // 테이블 정보 가지고 오는 코드
		
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
			eMessage("메시지", "삭제할 계약을 선택해주세요.");
		}else {

			String code = (String) table.getValueAt(row, 0);
			String contract = (String) table.getValueAt(row, 1);
			
			int cnt = JOptionPane.showConfirmDialog(null, code + "(" + contract + ")을 삭제하시겠습니까?", "계약정보 삭제", JOptionPane.OK_CANCEL_OPTION);

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
		
		FileDialog dialog = new FileDialog(this, "텍스트 파일로 저장", FileDialog.SAVE);
		
		dialog.setFile("보험계약현황.txt"); // 파일 이름 지정
		dialog.setVisible(true);

		if(dialog.getFile() == null) {
			return; // 선택한 파일이 없을 경우 리턴			
		}
		
		try {
			FileWriter fw = new FileWriter(dialog.getDirectory() + dialog.getFile());
											// 폴더 경로 + 파일 명
			// \n 줄바꿈  	\t 탭	
			fw.write(String.format("고객명 : %s(%s)\n\n", comName.getSelectedItem(),tfCode.getText()));
			fw.write(String.format("담당자명 : %s\n\n", comAdminName.getSelectedItem()));
			fw.write("보험상품\t가입금액\t가입일\t월보험료\n");
			for (int i = 0; i < table.getRowCount(); i++) {
				fw.write(String.format("%s\t%s\t%s\t%s\t\n", table.getValueAt(i, 0),table.getValueAt(i, 1),table.getValueAt(i, 2),table.getValueAt(i, 3)));
			}
			
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
}
