package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends baseFrame{
	
	private JTextField tfID = new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	public LoginFrame() {
		super("로그인", 330, 190);
	
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();
		
		JButton btnInsert = new JButton("회원가입");
		JButton btnExit = new JButton("종료");
		JButton btnLoin = new JButton("로그인");
		
		JLabel lbTitle = new JLabel("STARBOX",JLabel.CENTER);
		JLabel lbId = new JLabel("ID:");
		JLabel lbPW = new JLabel("PW:");
		
		lbTitle.setFont(new Font("Arial Black",Font.BOLD,25));
		
		pnlCenter.add(lbId);
		pnlCenter.add(lbPW);
		pnlCenter.add(tfID);
		pnlCenter.add(tfPw);
		pnlCenter.add(btnLoin);
		
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);
		
		add(lbTitle,BorderLayout.NORTH);
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlCenter,BorderLayout.CENTER);
		
		lbId.setBounds(35, 8, 40, 40);
		tfID.setBounds(60, 14, 170, 20);
		lbPW.setBounds(28, 35, 40, 40);
		tfPw.setBounds(60, 42, 170, 20);
		btnLoin.setBounds(235, 10, 70, 65);
		
		btnLoin.addActionListener(e -> login());
		btnInsert.addActionListener(e -> openFrame(new InsertFrame()));
		btnExit.addActionListener(e -> System.exit(0));
		
	}
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);

	}
	
	private void login() {
		String id = tfID.getText();
		String pw = tfPw.getText();
		
		if(id.isEmpty() || pw.isEmpty()) {
			Emessage("빈칸이 존재합니다.");
		}else {
			try {
				
				ResultSet rs = dbmanager.executeQuery("select * from user where u_id = ? and u_pw = ?", id,pw);
				if(rs.next()){
					
					u_no = rs.getInt(1);
					u_name = rs.getString(4);
					u_point = rs.getInt(6);
					u_grade= rs.getString(7);
					
					openFrame(new StarboxFrame());
				}else if ( id.equals("admin") && pw.equals("1234")) {
					openFrame(new adminFrame());
				}else {
					Emessage("회원정보가 틀립니다.다시입력해주세요.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

