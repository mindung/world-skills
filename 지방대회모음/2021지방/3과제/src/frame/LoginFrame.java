package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame{

	private JTextField tfId = new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	public LoginFrame() {
		super("�α���", 320, 180);
		
		JPanel pnlGrid = new JPanel(new GridLayout(2, 2, -100, 10));
		JPanel pnlCenter = new JPanel(null);
		
		JLabel lbTitle = new JLabel("WEDDING", JLabel.CENTER);
		JButton btnLogin = new JButton("�α���");
		
		pnlGrid.add(new JLabel("ID : ", JLabel.LEFT));
		pnlGrid.add(tfId);
		pnlGrid.add(new JLabel("PW : ", JLabel.LEFT));
		pnlGrid.add(tfPw);
		
		pnlCenter.add(pnlGrid);
		pnlCenter.add(btnLogin);
		
		add(lbTitle, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		
		lbTitle.setFont(new Font("���� ���", Font.BOLD, 35));
		
		pnlGrid.setBounds(20, 10, 190, 60);
		btnLogin.setBounds(220, 10, 72, 60);
		
		btnLogin.addActionListener(e -> login());
	}
	
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
	
	private void login() {
		String id = tfId.getText();
		String pw = tfPw.getText();
		
		if (id.isEmpty() || pw.isEmpty()) {
			eMessage("��ĭ�� �����մϴ�.");
		} else {
			try {
				ResultSet rs = dbManager.executeQuery("select * from user where u_id = ? and u_pw = ?", id,pw);
				
				if (rs.next()) {
					u_no = rs.getInt(1);
					
					iMessage(String.format("%s�� ȯ���մϴ�.", rs.getString(4)));
					
					rs = dbManager.executeQuery("select payment.p_date, user.u_name from invitation "
							+ "inner join payment on payment.p_no = invitation.p_no "
							+ "inner join user on user.u_no = payment.u_no "
							+ " where i_to = ?", u_no);

					if (rs.next()) {
						LocalDate fromDate = LocalDate.now();
						LocalDate toDate = LocalDate.parse(rs.getString(1));
						
						iMessage(String.format("%s���� ��ȥ���� D-%d�� ���ҽ��ϴ�.", rs.getString(2), ChronoUnit.DAYS.between(fromDate, toDate)));
					}
					openFrame(new MainFrame(), new LoginFrame());
				} else {
					eMessage("ID �Ǵ� PW�� ��ġ���� �ʽ��ϴ�.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

