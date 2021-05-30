package project;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.cj.protocol.Resultset;

public class LoginFrame extends JFrame{
	
	private JTextField tfid = new JTextField();
	private JPasswordField tfpw = new JPasswordField();

	private DBmanager dBmanager = new DBmanager();
	
	public LoginFrame() {
		
		JLabel ibLabel = new JLabel("STARBOX",JLabel .CENTER);
		JLabel ibid = new JLabel("ID : ");
		JLabel ibpw = new JLabel("PW : ");
		
		JButton btnLogin = new JButton("로그인");
		JButton btnsignup = new JButton("회원가입");
		JButton btnexit = new JButton("종료");
		
		JPanel pnlcenter = new JPanel(null);
		JPanel pnlsouth = new JPanel();
		
		pnlcenter.add(ibid);
		pnlcenter.add(ibpw);
		pnlcenter.add(tfid);
		pnlcenter.add(tfpw);
		pnlcenter.add(btnLogin);
//		꼭 사용해줘야한다. 안 써주면 창 안 떠 ㅎㅎ
		
		ibid.setBounds(20,10,50,25);
		ibpw.setBounds(20,40,50,25);
		tfid.setBounds(80,10,150,25);
		tfpw.setBounds(80,40,150,25);
		btnLogin.setBounds(270,0,70,75);
//		(x,y,가로 넓이(-),세로 넓이(|))
		
		pnlsouth.add(btnsignup);
		pnlsouth.add(btnexit);
		JPanel pn1south = new JPanel();

		//		회원가입 , 종료 창
		
		pn1south.add(btnsignup); //회원가입
		pn1south.add(btnexit); // 종료창
		
//		스타벅스 제목 이름 (서식)
		
		setLayout(new BorderLayout());
		ibLabel.setFont(new Font ("ARIAL BLACK", Font.BOLD,30));
		
		setLayout(new BorderLayout());
	
		add(ibLabel, BorderLayout.NORTH);
		add(pnlcenter,BorderLayout.CENTER); // id,pw,tfid,tfpw 등등 
		add(pn1south,BorderLayout.SOUTH); // 회원가입 종료 창 판넬
		
		setSize(365,200);
		setTitle("로그인");
		setLocationRelativeTo(null);
//		가운데
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		 필요한 창만 띄어주기 위해 나머지 다 닫고 열어준 폼만 닫아줌
		
	// 버튼의 동작을 시켜줌 (E)는 변수 요약한것이당 (버튼,ddActionListener(변수->버튼()); 써주기
		btnLogin.addActionListener(e->login());
		btnsignup.addActionListener(e->signUp());
		btnexit.addActionListener(e->System.exit(0));
	}
		
	private void login() {
		String id = tfid.getText();
		String pw = tfpw.getText();
//  string은  id,pw 변수 선언 위해 적어준 것
		
		if(id.isEmpty() || pw.isEmpty()) {
				
			JOptionPane.showMessageDialog(null, "빈칸이 존재합니다.","메시지",JOptionPane.ERROR_MESSAGE);
//	 메시지 박스 띄우는 방법 거의 비슷 제이옵션버툰,쇼메시지~(null,멘트,이름,모양)
			
		} else if(id.equals("admin") && pw.equals("1234")) { // && ~이고
			JOptionPane.showMessageDialog(null, "회원정보가 틀립니다.다시입력해주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE);
		} else {	
			try {
				ResultSet rs = DBmanager.stmt.executeQuery("select * from user where u_id = '"+ id +"' and u_pw = '"+ pw +"'");
				if(rs.next()) {
//					0이면 밑에 참이면 ~~ 아니면 else ~~
					JOptionPane.showMessageDialog(null,"로그인 성공","메시지",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null,"로그인 실패","메시지",JOptionPane.ERROR_MESSAGE);
				}
						
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void signUp() {
		
	}
	
	public static void main(String[] args) {
			new LoginFrame().setVisible(true);
		
	}
	
}
