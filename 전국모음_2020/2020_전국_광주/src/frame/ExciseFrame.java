package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.BaseFrame;
import panel.BasePanel;

public class ExciseFrame extends BaseFrame {

	private JTextField tfTime = new JTextField();
	private JComboBox<String> comGroup = new JComboBox<>(new String[] { "걷기", "수영", "요가", "자전거", "근력", "기타" });

	private String values = null;

	public ExciseFrame() {
		super("운동", 370, 240);

		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();

		JPanel pnlYes = new JPanel();
		JPanel pnlNo = new JPanel();

		JButton btnExit = new JButton("취소");
		JButton btnOk = new JButton("확인");

		JLabel lbYesNo = new JLabel("여부");
		JLabel lbTime = new JLabel("시간");
		JLabel lbMin = new JLabel("분");
		JLabel lbGroup = new JLabel("종류");

		pnlCenter.add(lbYesNo);
		pnlCenter.add(lbTime);
		pnlCenter.add(lbMin);
		pnlCenter.add(lbGroup);
		pnlCenter.add(tfTime);
		pnlCenter.add(comGroup);
		pnlCenter.add(pnlYes);
		pnlCenter.add(pnlNo);

		pnlYes.add(new JLabel("예"), JLabel.CENTER);
		pnlNo.add(new JLabel("아니오"), JLabel.CENTER);

		pnlYes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlNo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		btnExit.addActionListener(e -> dispose());
		btnOk.addActionListener(e -> ok());

		pnlSouth.add(btnExit);
		pnlSouth.add(btnOk);

		btnOk.setPreferredSize(new Dimension(120, 30));
		btnExit.setPreferredSize(new Dimension(120, 30));

		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		lbYesNo.setBounds(40, 10, 30, 30);
		pnlYes.setBounds(90, 10, 120, 30);
		pnlNo.setBounds(220, 10, 120, 30);

		lbTime.setBounds(40, 60, 30, 30);
		tfTime.setBounds(90, 60, 120, 30);
		lbMin.setBounds(220, 60, 30, 30);
		lbGroup.setBounds(40, 110, 30, 30);
		comGroup.setBounds(90, 110, 90, 30);

		btnExit.addActionListener(e -> dispose());
		btnOk.addActionListener(e -> ok());

		pnlYes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				values = "예";
				pnlYes.setBackground(Color.red);
				pnlNo.setBackground(null);
				comGroup.setEnabled(true);
				tfTime.setEnabled(true);
			};
		});

		pnlNo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				values = "아니오";
				pnlNo.setBackground(Color.red);
				pnlYes.setBackground(null);
				comGroup.setEnabled(false);
				tfTime.setEnabled(false);
				comGroup.setSelectedIndex(-1);
				tfTime.setText("");
			};
		});
	}

	public static void main(String[] args) {
		new ExciseFrame().setVisible(true);

	}

	private void ok() {

		if (values == null) {
			eMessage("여부를 선택해주세요.");
		} else {
			try {

				if (values == "예") {
					String time = tfTime.getText();

					if (time.isEmpty()) {
						eMessage("시간을 입력해주세요.");
						System.out.println("시간 입력해조");
						return;
						
					} else if (comGroup.getSelectedIndex() == -1) {
						eMessage("운동종류를 선택해주세요.");
						return;
					}
				}

				if (BasePanel.d_index == 0) {

					dbManager.executeUpdate(
							"insert into tbl_diary(M_index, D_date , D_ex_do, D_ex_time,D_ex_type) values(?,?,?,?,?)",BasePanel.m_index, BasePanel.d_date, values, tfTime.getText(), comGroup.getSelectedItem());
					
//					dbManager.executeUpdate("insert into tbl_diary ( M_index, D_date, D_ex_do, D_ex_time, D_ex_type)values(?,?,?,?,?)",M_index,date,result,tfTime.getText(),comGroup.getSelectedItem());
				} else {
					dbManager.executeUpdate("update tbl_diary set D_ex_do = ?, D_ex_time = ?, D_ex_type = ? where M_index = ? ", values,tfTime.getText(), comGroup.getSelectedItem(), BasePanel.m_index);
//					dbManager.executeUpdate("update tbl_diary set D_ex_do = ?, D_ex_time = ?, D_ex_type = ? where M_index = ? and D_date = ?", result,tfTime.getText(),comGroup.getSelectedItem().toString(),M_index, d_date);
				}

				iMessage("입력이 완료되었습니다.");

				dispose();
				BasePanel.mainPanel.getdiary();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
