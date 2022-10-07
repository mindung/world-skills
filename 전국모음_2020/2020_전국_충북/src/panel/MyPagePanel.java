package panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MyPagePanel extends BasePanel {

	private JTextField tfId = createComponent(new JTextField());
	private JTextField tfPw = createComponent(new JTextField());
	private JTextField tfName = createComponent(new JTextField());
	private JTextField tfEmail = createComponent(new JTextField());
	private JTextField tfPhone = createComponent(new JTextField());
	private JTextField tfAddress = createComponent(new JTextField());
	private JComboBox<String> comFavorite = createComponent(new JComboBox<>());
	private JComboBox<Integer> comReview = new JComboBox<>();
	
	private JPopupMenu popup = new JPopupMenu();
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"���Ź�ȣ", "�Ǹ���", "��ǰ��", "��ۿ���" }, 0);
	private JTable table = new JTable(dtm);
	
	private int[] columnSize = {60, 100, 200, 70};
	
	public MyPagePanel() {
		setLayout(null);
	
		JPanel pnlCenter = new JPanel(new GridLayout(7, 2, -140, 10));
		
		JLabel lbUpdate = new JLabel("ȸ������ ����");
		JLabel lbOrderList = new JLabel("�ֹ�����");
		
		JButton btnUpdate = createButton("��������");
		
		JScrollPane jsp = new JScrollPane(table);
		
		JMenuItem item1 = new JMenuItem("��ǰ������ �̵�");
		JMenuItem item2 = new JMenuItem("��ǰ ���� �ֱ�");
		JMenuItem item3 = new JMenuItem("�ֹ� ���");
		
		pnlCenter.add(createLabel("���̵�"));
		pnlCenter.add(tfId);
		pnlCenter.add(createLabel("��й�ȣ"));
		pnlCenter.add(tfPw);
		pnlCenter.add(createLabel("�̸�"));
		pnlCenter.add(tfName);
		pnlCenter.add(createLabel("�̸���"));
		pnlCenter.add(tfEmail);
		pnlCenter.add(createLabel("��ȭ��ȣ"));
		pnlCenter.add(tfPhone);
		pnlCenter.add(createLabel("�ּ�"));
		pnlCenter.add(tfAddress);
		pnlCenter.add(createLabel("�����׸�"));
		pnlCenter.add(comFavorite);
		
		add(lbUpdate);
		add(lbOrderList);
		add(pnlCenter);
		add(btnUpdate);
		add(jsp);
		
		lbUpdate.setBounds(30, 20, 250, 30);
		pnlCenter.setBounds(30, 60, 250, 300);
		btnUpdate.setBounds(30, 370, 250, 30);
		
		lbOrderList.setBounds(300, 20, 500, 30);
		jsp.setBounds(300, 60, 550, 470);
		
		lbUpdate.setFont(new Font("���� ���", Font.BOLD, 20));
		lbOrderList.setFont(new Font("���� ���", Font.BOLD, 20));
		
		pnlCenter.setBackground(null);
		
		for (int i = 0; i < columnSize.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnSize[i]);
			table.getColumnModel().getColumn(i).setCellRenderer(centerCR);
		}
		
		for (int i = 1; i <= 5; i++) {
			comReview.addItem(i);
		}
		
		tfId.setEnabled(false);
		
		popup.add(item1);
		popup.add(item2);
		popup.add(item3);
		
//		table.setComponentPopupMenu(popup);
	
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) { // getButton() = 3 �̸� ��Ŭ��
					int row = table.rowAtPoint(e.getPoint()); // ������ �� ��ȣ?  Ŭ���� �߻��� ���� ���õǾ����� Ȯ���մϴ�.
					
					table.setRowSelectionInterval(row, row);   
					popup.show(table, e.getX(), e.getY());
				}
			}
		});
		
		btnUpdate.addActionListener(e -> updateInfo());
		item2.addActionListener(e -> review());
		item3.addActionListener(e -> cancelOrder());
		
		addCategory();
		getInfo();
		getOrderList();
	}
	
	private void addCategory() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM category");
			
			while(rs.next()) {
				comFavorite.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getInfo() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM user WHERE serial = ?", userSerial);
			
			if (rs.next()) {
				tfId.setText(rs.getString(2));
				tfName.setText(rs.getString(4));
				tfEmail.setText(rs.getString(7));
				tfPhone.setText(rs.getString(5));
				tfAddress.setText(rs.getString(6));
				comFavorite.setSelectedIndex(rs.getInt(8) - 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getOrderList() {
		dtm.setRowCount(0);
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT orderlist.serial, seller.name, product.name, orderlist.shipping FROM orderlist INNER JOIN seller ON seller.serial = orderlist.seller INNER JOIN product ON product.serial = orderlist.product WHERE user = ?", userSerial);
		
			while(rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getInt(4) == 0 ? "�����" : "��ۿϷ�"
				});
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateInfo() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		String name = tfName.getText();
		String email = tfEmail.getText();
		String phone = tfPhone.getText();
		String address = tfAddress.getText();
		int favorite = comFavorite.getSelectedIndex() + 1;
		
		if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
			JOptionPane.showMessageDialog(null, "������ �����մϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			if (pw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$]).{6,14}$") == false) {
				eMessage("����, ����, Ư������ ���� 6~14�ڸ��� �����ؾ��մϴ�.");
			} else {
				dbManager.executeUpdate("UPDATE user SET pw = ?, name = ?, phone = ?, address = ?, email = ?, favorite = ? WHERE id = ?", pw, name, phone, address, email, favorite, id);
				iMessage("������ �Ϸ�Ǿ����ϴ�.");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void review() {
		int row = table.getSelectedRow();
		
		if (table.getValueAt(row, 3).equals("�����")) {
			eMessage("����� �Ϸ�Ǿ� ������ �ű� �� �ֽ��ϴ�.");
			return;
		}
		
		JOptionPane.showMessageDialog(null, comReview, "���� �ֱ�", JOptionPane.QUESTION_MESSAGE);
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM orderlist WHERE serial = ?", table.getValueAt(row, 0));
			
			if (rs.next()) {
				dbManager.executeUpdate("INSERT INTO review VALUES (0, ?, ?, ?, ?)", rs.getInt(2), rs.getInt(4), rs.getInt(3), comReview.getSelectedIndex() + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void cancelOrder() {
		int row = table.getSelectedRow();
		
		if (table.getValueAt(row, 3).equals("��ۿϷ�")) {
			eMessage("����� �Ϸ�� �ֹ��� ��Ұ� �Ұ��մϴ�.");
			return;
		}
		
		try {
			dbManager.executeUpdate("DELETE FROM orderlist WHERE serial = ?", table.getValueAt(row, 0));
			
			iMessage("�ֹ��� ��ҵǾ����ϴ�.");
			
			getOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
