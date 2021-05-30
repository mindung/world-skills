package frame;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginProFrame extends BaseFrame {
	
	private JTextField tfNo = new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	public LoginProFrame() {
		super("�����α���", 350, 200);
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();
	
		JButton btnLogin = new JButton("�α���");
		JButton btnExit = new JButton("���");
		
		pnlCenter.add(addC(new JLabel("������ȣ"), 60, 20, 80, 40));
		pnlCenter.add(addC(new JLabel("��   ȣ"), 60, 70, 80, 40));
		pnlCenter.add(addC(tfNo, 130, 25, 180, 30));
		pnlCenter.add(addC(tfPw, 130, 75, 180, 30));
	
		pnlSouth.add(btnLogin);
		pnlSouth.add(btnExit);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
	
		btnLogin.addActionListener(e -> login());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
		
	}
	
	private void login() {
		String no = tfNo.getText();
		String pw = tfPw.getText();
		
		if(no.isEmpty() || pw.isEmpty()) {
			eMessage("�׸��� �����Ǿ����ϴ�.");	
			return;
		}
		
//		eMessage("������ȣ Ȥ�� ��ȣ�� Ȯ�����ּ���.");
//		return;
//		rs = dbManager.executeQuery("select * from pro where p_code = ? and p_pw = ? and p_gr = '��ȸ��'", no,pw);
			 
			try {
				ResultSet rs = dbManager.executeQuery("select * from pro where p_code = ? and p_pw = ? ", no,pw);
			
				if (rs.next()) {
					
					String grade = rs.getString(6);
					
					System.out.println(grade);
					if (grade.equals("��ȸ��")) {
						iMessage("�α��� �Ϸ�");
						
						p_Code = rs.getString(1);
						Info = rs.getString(2);
						
						openFrame(new ProfessorFrame());
					} else if (grade.equals("��ȸ��")) {
					
						String input = JOptionPane.showInputDialog(null, "ó�� �����ϼ̽��ϴ�. ����� ��ȣ�� �Է��ϼ���.", "ó�� ������", JOptionPane.INFORMATION_MESSAGE);
						
						if (input.isEmpty()) {
							eMessage("�׸��� �����Ǿ����ϴ�.");
						} else {
							iMessage("�Է��Ͻ� ��й�ȣ�� ������ ��û�մϴ�.");
							dbManager.executeUpdate("update pro set p_gr = ?, p_pw2 = ?  where p_code = ?", "�ɻ���", input,no);
						}

					} else if (grade.equals("�ɻ���")) {
						eMessage("�ɻ����� �������Դϴ�.");
					}
					
				}else {
					eMessage("������ȣ Ȥ�� ��ȣ�� Ȯ�����ּ���.");
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		new LoginProFrame().setVisible(true);
	}
}
