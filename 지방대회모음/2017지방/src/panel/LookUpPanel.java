 package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import frame.BaseFrame;

public class LookUpPanel extends BasePanel{
	
	private JTextField tfNumber = new JTextField(8);
	private JTextField tfNumber2 = new JTextField(8);
	private JTextField tfSdate = new JTextField(8);
	private JTextField tfSdateS = new JTextField(8);
	private JTextField tfNumberS = new JTextField(8);
	private JTextField tfSeat = new JTextField(8);
	private JTextField tfId = new JTextField(8);
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"���� â��","����","�������","����","���","����â��","����","�������","����"}, 0);
	private JTable table = new JTable(dtm);
	
	private ArrayList<String> enteredSeat = new ArrayList<String>();
	private ArrayList<String> duplicatedSeat = new ArrayList<String>();
	private ArrayList<String> reservedSeat = new ArrayList<String>();
	private ArrayList<String> reservedCheckSeat = new ArrayList<String>();
	
	private JScrollPane jsp = new JScrollPane(table);
	private JPanel pnlcenter = new JPanel();
	
	public LookUpPanel() {
	
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(700, 400));
	
		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNSouth =new JPanel(new FlowLayout());
		JPanel pnlSouth = new JPanel(new FlowLayout());
		
		JButton btnSearch = new JButton("�¼� ��ȸ");
		JButton btnReservation = new JButton("����");
		
		JLabel lbTitle = new JLabel("�¼�ǥ ����",JLabel.LEFT);
		
		lbTitle.setFont(new Font("���� ���",Font.BOLD,19));
		
		pnlNSouth.add(new JLabel("���� ��ȣ"));
		pnlNSouth.add(tfNumber);
		pnlNSouth.add(new JLabel("�������"));
		pnlNSouth.add(tfSdate);
		pnlNSouth.add(new JLabel("ȣ�� ��ȣ"));
		pnlNSouth.add(tfNumber2);
		pnlNSouth.add(btnSearch);
		
		pnlSouth.add(new JLabel("�������"));
		pnlSouth.add(tfSdateS);
		pnlSouth.add(new JLabel("������ȣ"));
		pnlSouth.add(tfNumberS);
		pnlSouth.add(new JLabel("�¼���ȣ"));
		pnlSouth.add(tfSeat);
		pnlSouth.add(new JLabel("ȸ��ID"));
		pnlSouth.add(tfId);
		pnlSouth.add(btnReservation);
		
		pnlNorth.add(lbTitle,BorderLayout.NORTH);
		pnlNorth.add(pnlNSouth,BorderLayout.CENTER);
		
		pnlcenter.add(jsp);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlcenter,BorderLayout.CENTER);

		tfNumber.setText(BaseFrame.tfNum);
		tfSdate.setText(BaseFrame.date);
		tfNumber2.setText(BaseFrame.tfNum2);
		
		jsp.setPreferredSize(new Dimension(550, 200));

		btnReservation.addActionListener(e -> reserve());
		
		tfId.setText(BaseFrame.login);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(25);
		table.getColumnModel().getColumn(3).setPreferredWidth(25);
		table.getColumnModel().getColumn(4).setPreferredWidth(25);
		table.getColumnModel().getColumn(6).setPreferredWidth(25);
		table.getColumnModel().getColumn(8).setPreferredWidth(25);
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(BaseFrame.centerRenderer);
		}
		
		for (int i = 0; i < 5; i++) {
			int n = i * 4 + 1; // �ʱⰪ�� 0 , 1, 2 ... ��
			
			dtm.addRow(new Object[] {
				n, "", n + 1, "", "---", n + 2, "", n + 3, ""
			});
		}
		
		jsp.setVisible(false);
		btnSearch.addActionListener(e -> SearchSeat());
		
	}

	private void reserve() {
		
			String seat = tfSeat.getText();
		
			if (seat.isEmpty()) {
				iMessage("�¼���ȣ�� �Է����ּ���.");
				return;
			}
		
			enteredSeat.clear();
			duplicatedSeat.clear();
			reservedCheckSeat.clear();
			
			StringTokenizer st = new StringTokenizer(tfSeat.getText(),", ");
//			
//			duplicatedSeat �ߺ� �¼�
//			enteredSeat �Է� �¼�
//			reservedCheckSeat ���� Ȯ�� �¼�
			
			while(st.hasMoreTokens()) {
				String s = st.nextToken();
				
				if(enteredSeat.contains(s) && duplicatedSeat.contains(s) == false) { // �ߺ��� �¼��� ���� �迭�� ���� ���
					duplicatedSeat.add(s);											// �ߺ� �¼��� �ɰ� ���� ����
				}else if(reservedSeat.contains(s)){ 								/////// ����� �¼��� ���� �ش�Ǵ� ���� �ִٸ�
					reservedCheckSeat.add(s);										// ����� �¼� üũ �迭�� ���� �ִ´�.
				}else {																// ���� ������ �� �ƴѰ�� 
					enteredSeat.add(s);												// �Է��¼�
				}
			}
			 
			if(duplicatedSeat.isEmpty() == false) { // �ߺ��¼� ���� ����ִٸ�
				eMessage(String.format("�¼���ȣ %s��(��) �ߺ��ԷµǾ����ϴ�.", String.join(", ", duplicatedSeat)));
				return;
			}
			
			if(reservedCheckSeat.isEmpty() == false) {	// ����Ȯ�ΰ��� ��� ���� �ʴٸ� 
				eMessage(String.format("�¼���ȣ %s�� �̹� ����Ǿ� �ִ� �¼��Դϴ�.", String.join(", ", duplicatedSeat)));
				return;
			}
			
			iMessage(String.join(", ", enteredSeat));
			
	}
	
	private void SearchSeat() {
		
		String tfNum = tfNumber.getText();
		String Sdate = tfSdate.getText();
		String tfNum2 = tfNumber2.getText();
		
		if(tfNum.isEmpty() || Sdate.isEmpty() || tfNum2.isEmpty()) {
			iMessage("�ùٸ��� �����¼������Դϴ�.");
			return;
		}
		
		reservedCheckSeat.clear(); // ����Ȯ�� �¼� ���� �ʱ�ȭ 
		
		try {
			
			ResultSet rs = dbManager.executeQuery("select * from tbl_ticket where bNumber = ? and bDate = ? and bNumber2 = ?",tfNum,Sdate,tfNum2);
		
			while(rs.next()) { // �ش� ������ �����ϸ�
				
				String seat = rs.getString(4); 
				
				for (int i = 0; i < dtm.getRowCount(); i++) { //  �� ���� ��
					for (int j = 0; j < dtm.getColumnCount(); j++) { // �� ���� ��
						if (table.getValueAt(i, j).toString().equals(seat)) { // ���̺� �ش��ϴ� ���,���� ���� seat ���� ���ٸ�
							table.setValueAt("O", i, j+1);					// table "O" �־��� �ش� ���, �� + 1 �ٷ� ��ĭ�� ���� �ֿ����ش�.
							reservedSeat.add(seat);							// �����ߴ��¼��� �迭 ���� �� �� ���� �־���
						}
					}
				}
			} 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pnlcenter.updateUI();
		jsp.setVisible(true);
		
		tfSdateS.setEnabled(false);
		tfNumberS.setEnabled(false);
		
		tfSdateS.setText(Sdate);
		tfNumberS.setText(tfNum);
		
		
	}
}

