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
		super("교수로그인", 350, 200);
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();
	
		JButton btnLogin = new JButton("로그인");
		JButton btnExit = new JButton("취소");
		
		pnlCenter.add(addC(new JLabel("교수번호"), 60, 20, 80, 40));
		pnlCenter.add(addC(new JLabel("암   호"), 60, 70, 80, 40));
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
			eMessage("항목이 누락되었습니다.");	
			return;
		}
		
//		eMessage("교수번호 혹은 암호를 확인해주세요.");
//		return;
//		rs = dbManager.executeQuery("select * from pro where p_code = ? and p_pw = ? and p_gr = '정회원'", no,pw);
			 
			try {
				ResultSet rs = dbManager.executeQuery("select * from pro where p_code = ? and p_pw = ? ", no,pw);
			
				if (rs.next()) {
					
					String grade = rs.getString(6);
					
					System.out.println(grade);
					if (grade.equals("정회원")) {
						iMessage("로그인 완료");
						
						p_Code = rs.getString(1);
						Info = rs.getString(2);
						
						openFrame(new ProfessorFrame());
					} else if (grade.equals("비회원")) {
					
						String input = JOptionPane.showInputDialog(null, "처음 접속하셨습니다. 사용할 암호를 입력하세요.", "처음 접속자", JOptionPane.INFORMATION_MESSAGE);
						
						if (input.isEmpty()) {
							eMessage("항목이 누락되었습니다.");
						} else {
							iMessage("입력하신 비밀번호로 승인을 요청합니다.");
							dbManager.executeUpdate("update pro set p_gr = ?, p_pw2 = ?  where p_code = ?", "심사중", input,no);
						}

					} else if (grade.equals("심사중")) {
						eMessage("심사중인 교수님입니다.");
					}
					
				}else {
					eMessage("교수번호 혹은 암호를 확인해주세요.");
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		new LoginProFrame().setVisible(true);
	}
}
