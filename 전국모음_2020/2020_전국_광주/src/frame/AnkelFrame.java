package frame;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

import frame.BaseFrame;
import panel.BasePanel;
import panel.ButtonPanel;

public class AnkelFrame extends BaseFrame {

	private ButtonPanel Buttons = new ButtonPanel();

	public AnkelFrame() {
		super("�߸� ����", 300, 150);

		JPanel pnlSouth = new JPanel();

		JButton btnExit = new JButton("���");
		JButton btnOk = new JButton("Ȯ��");

		pnlSouth.add(btnExit);
		pnlSouth.add(btnOk);

		add(pnlSouth, BorderLayout.SOUTH);
		add(Buttons, BorderLayout.CENTER);

		btnExit.addActionListener(e -> dispose());
		btnOk.addActionListener(e -> ok());
	}

	public static void main(String[] args) {
		new AnkelFrame().setVisible(true);
	}

	private void ok() {
		String values = Buttons.getvalue();

		if (values == null) {
			eMessage("���θ� �������ּ���.");
		} else {
			try {
				if (BasePanel.d_index == 0) {

					dbManager.executeUpdate("insert into tbl_diary (M_index, D_date, D_ankle) values(?,?,?)", BasePanel.m_index,
							BasePanel.d_date, values);

				} else {
					dbManager.executeUpdate("Update tbl_diary set D_ankle = ? where D_index = ?", values, BasePanel.d_index);

				}

				iMessage("�Է��� �Ϸ�Ǿ����ϴ�.");
				BasePanel.mainPanel.getdiary();
				dispose();
				BasePanel.mainPanel.updateUI();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}
}
