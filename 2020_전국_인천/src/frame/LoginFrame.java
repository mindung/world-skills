package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame{
	
	private JTextField tfId = new JTextField();
	private JPasswordField tfPw = new JPasswordField();
	
	public LoginFrame() {
		super("로그인", 430, 220);
	
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel lbTitle = new JLabel("기능마켓",JLabel.CENTER);
		JLabel lbId = new JLabel("아이디",JLabel.RIGHT);
		JLabel lbPw = new JLabel("비밀번호",JLabel.RIGHT);
		JLabel lbInsert = new JLabel("회원가입   ");
		
		JButton btnLogin = new JButton("로그인");
		
		lbTitle.setFont(new Font("HY헤드라인M",Font.BOLD,25));
		
		pnlCenter.add(lbId);
		pnlCenter.add(lbPw);
		pnlCenter.add(tfId);
		pnlCenter.add(tfPw);
		pnlCenter.add(btnLogin);
		
		pnlSouth.add(lbInsert);
		
		lbTitle.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		add(pnlCenter,BorderLayout.CENTER);
		add(lbTitle,BorderLayout.NORTH);
		add(pnlSouth,BorderLayout.SOUTH);
		
		lbId.setBounds(40, 20, 60, 30);
		tfId.setBounds(125, 20, 180, 30);
		lbPw.setBounds(40, 70, 60, 30);
		tfPw.setBounds(125, 70, 180, 30);
		btnLogin.setBounds(320, 20, 85, 80);
		
		btnLogin.addActionListener(e -> login());
		
		lbInsert.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				openFrame(new InsertFrame());
				openFrame(new InsertFrame(), new LoginFrame());
			}
		});
		
	}
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}

	private void login() {
		String id = tfId.getText();
		String pw = tfPw.getText();

		if (id.isEmpty() || pw.isEmpty()) {
			eMessage("빈칸이 있습니다.");

		} else {
			
			if(id.equals("admin") && pw.equals("1234")) {
			
				iMessage("관리자로 로그인 하셨습니다.");
				openFrame(new AdminFrame());
				
			} else {
				
				try {

					ResultSet rs = dbManager.executeQuery("select * from user where u_id = ? and u_pw = ?", id, pw);

					if (rs.next()) {

						u_no = rs.getInt(1);
						u_name = rs.getString(5);
						iMessage(String.format("%s님 환영합니다.", u_name));
						openFrame(new ProductFrame());

					} else {

						eMessage("아이디나 비밀번호가 틀렸습니다.");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
}

