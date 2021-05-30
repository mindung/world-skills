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

	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "출발지", "→→→", "도착지" }, 0);
	private JTable table = new JTable(dtm);

	private JScrollPane jsp = new JScrollPane(table);

	private JPanel pnlCenter = new JPanel();

	public TicketPanel() {

		setLayout(new BorderLayout());

		JPanel pnlNorth = new JPanel();

		JButton btnSearch = new JButton("조회");

		pnlNorth.add(new JLabel("출발일자"));
		pnlNorth.add(tfSdate);
		pnlNorth.add(new JLabel("차량번호"));
		pnlNorth.add(tfNum);
		pnlNorth.add(new JLabel("버스번호"));
		pnlNorth.add(tfNum2);
		pnlNorth.add(new JLabel("회원ID"));
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
			wMessage("발권할 승차권의 데이터를 입력해주시기 바랍니다.");
			return;
		}

		try {

			ResultSet rs = dbManager.executeQuery(
					"select * from tbl_ticket where bDate = ? and bNumber = ? and bNumber2 = ? and cID = ?", sDate, num,
					num2, id);

			if (rs.next()) {

				wMessage("승차권이 정상적으로 발권되었습니다.");
				updateJsp();

				System.err.println("실행");

				int price = rs.getInt(6) / 10;

//				dtm.addRow(new Object[] {
//						"","","","","",""
//				});

				table.setValueAt(rs.getString(3).toString().equals("1호차") ? "서울" : "부산", 0, 0);
				table.setValueAt(rs.getString(3).toString().equals("1호차") ? "부산" : "서울", 0, 2);

				table.setValueAt("운행요금", 2, 0);
				table.setValueAt("할인요금", 2, 1);
				table.setValueAt("영수액", 2, 2);

				table.setValueAt(rs.getInt(6), 3, 0);
				table.setValueAt(rs.getString(5).toString().equals("비회원") ? 0 : price, 3, 1);
				table.setValueAt(rs.getInt(6) - (int) table.getValueAt(3, 1), 3, 2);

				table.setValueAt("발권날자", 5, 0);
				table.setValueAt("출발일자", 5, 2);

				table.setValueAt(LocalDate.now().toString(), 6, 0);
				table.setValueAt(sDate, 6, 2);

			} else {

				wMessage("조회한 정보가 존재하지 않습니다.");
				pnlCenter.updateUI();
				jsp.setVisible(false);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
