package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import frame.BaseFrame;
import panel.BasePanel;

public class TicketPanel extends BasePanel {

	private JTextField tfSdate = new JTextField(7);
	private JTextField tfNum = new JTextField(7);
	private JTextField tfNum2 = new JTextField(7);
	private JTextField tfId = new JTextField(7);

	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "�����", "����", "������" }, 0);
	private JTable table = new JTable(dtm);

	private JScrollPane jsp = new JScrollPane(table);

	private JPanel pnlCenter = new JPanel();

	public TicketPanel() {

		setLayout(new BorderLayout());

		JPanel pnlNorth = new JPanel();

		JButton btnSearch = new JButton("��ȸ");

		pnlNorth.add(new JLabel("�������"));
		pnlNorth.add(tfSdate);
		pnlNorth.add(new JLabel("������ȣ"));
		pnlNorth.add(tfNum);
		pnlNorth.add(new JLabel("������ȣ"));
		pnlNorth.add(tfNum2);
		pnlNorth.add(new JLabel("ȸ��ID"));
		pnlNorth.add(tfId);
		pnlNorth.add(btnSearch);

		pnlCenter.add(jsp);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);

		jsp.setPreferredSize(new Dimension(600, 235));
		pnlNorth.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

		btnSearch.addActionListener(e -> search());

		for (int i = 1; i <= 7; i++) {
			dtm.addRow(new Object[] { "", "", "" });

		}

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(BaseFrame.centerRenderer);
		}

		table.setRowHeight(30);
		jsp.setVisible(false);
	}

	private void updateJsp() {

		pnlCenter.updateUI();
		jsp.setVisible(true);

	}

	private void search() {

		String sDate = tfSdate.getText();
		String num = tfNum.getText();
		String num2 = tfNum2.getText();
		String id = tfId.getText();

		if (sDate.isEmpty() || num.isEmpty() || num2.isEmpty() || id.isEmpty()) {
			wMessage("�߱��� �������� �����͸� �Է����ֽñ� �ٶ��ϴ�.");
			return;
		}

		try {

			ResultSet rs = dbManager.executeQuery(
					"select * from tbl_ticket where bDate = ? and bNumber = ? and bNumber2 = ? and cID = ?", sDate, num,
					num2, id);

			if (rs.next()) {

				wMessage("�������� ���������� �߱ǵǾ����ϴ�.");
				updateJsp();

				System.err.println("����");

				int price = rs.getInt(6) / 10;

//				dtm.addRow(new Object[] {
//						"","","","","",""
//				});

				table.setValueAt(rs.getString(3).toString().equals("1ȣ��") ? "����" : "�λ�", 0, 0);
				table.setValueAt(rs.getString(3).toString().equals("1ȣ��") ? "�λ�" : "����", 0, 2);

				table.setValueAt("������", 2, 0);
				table.setValueAt("���ο��", 2, 1);
				table.setValueAt("������", 2, 2);

				table.setValueAt(rs.getInt(6), 3, 0);
				table.setValueAt(rs.getString(5).toString().equals("��ȸ��") ? 0 : price, 3, 1);
				table.setValueAt(rs.getInt(6) - (int) table.getValueAt(3, 1), 3, 2);

				table.setValueAt("�߱ǳ���", 5, 0);
				table.setValueAt("�������", 5, 2);

				table.setValueAt(LocalDate.now().toString(), 6, 0);
				table.setValueAt(sDate, 6, 2);

			} else {

				wMessage("��ȸ�� ������ �������� �ʽ��ϴ�.");
				pnlCenter.updateUI();
				jsp.setVisible(false);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
