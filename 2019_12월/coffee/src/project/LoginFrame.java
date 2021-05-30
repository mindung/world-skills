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
		
		JButton btnLogin = new JButton("�α���");
		JButton btnsignup = new JButton("ȸ������");
		JButton btnexit = new JButton("����");
		
		JPanel pnlcenter = new JPanel(null);
		JPanel pnlsouth = new JPanel();
		
		pnlcenter.add(ibid);
		pnlcenter.add(ibpw);
		pnlcenter.add(tfid);
		pnlcenter.add(tfpw);
		pnlcenter.add(btnLogin);
//		�� ���������Ѵ�. �� ���ָ� â �� �� ����
		
		ibid.setBounds(20,10,50,25);
		ibpw.setBounds(20,40,50,25);
		tfid.setBounds(80,10,150,25);
		tfpw.setBounds(80,40,150,25);
		btnLogin.setBounds(270,0,70,75);
//		(x,y,���� ����(-),���� ����(|))
		
		pnlsouth.add(btnsignup);
		pnlsouth.add(btnexit);
		JPanel pn1south = new JPanel();

		//		ȸ������ , ���� â
		
		pn1south.add(btnsignup); //ȸ������
		pn1south.add(btnexit); // ����â
		
//		��Ÿ���� ���� �̸� (����)
		
		setLayout(new BorderLayout());
		ibLabel.setFont(new Font ("ARIAL BLACK", Font.BOLD,30));
		
		setLayout(new BorderLayout());
	
		add(ibLabel, BorderLayout.NORTH);
		add(pnlcenter,BorderLayout.CENTER); // id,pw,tfid,tfpw ��� 
		add(pn1south,BorderLayout.SOUTH); // ȸ������ ���� â �ǳ�
		
		setSize(365,200);
		setTitle("�α���");
		setLocationRelativeTo(null);
//		���
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		 �ʿ��� â�� ����ֱ� ���� ������ �� �ݰ� ������ ���� �ݾ���
		
	// ��ư�� ������ ������ (E)�� ���� ����Ѱ��̴� (��ư,ddActionListener(����->��ư()); ���ֱ�
		btnLogin.addActionListener(e->login());
		btnsignup.addActionListener(e->signUp());
		btnexit.addActionListener(e->System.exit(0));
	}
		
	private void login() {
		String id = tfid.getText();
		String pw = tfpw.getText();
//  string��  id,pw ���� ���� ���� ������ ��
		
		if(id.isEmpty() || pw.isEmpty()) {
				
			JOptionPane.showMessageDialog(null, "��ĭ�� �����մϴ�.","�޽���",JOptionPane.ERROR_MESSAGE);
//	 �޽��� �ڽ� ���� ��� ���� ��� ���̿ɼǹ���,��޽���~(null,��Ʈ,�̸�,���)
			
		} else if(id.equals("admin") && pw.equals("1234")) { // && ~�̰�
			JOptionPane.showMessageDialog(null, "ȸ�������� Ʋ���ϴ�.�ٽ��Է����ּ���.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
		} else {	
			try {
				ResultSet rs = DBmanager.stmt.executeQuery("select * from user where u_id = '"+ id +"' and u_pw = '"+ pw +"'");
				if(rs.next()) {
//					0�̸� �ؿ� ���̸� ~~ �ƴϸ� else ~~
					JOptionPane.showMessageDialog(null,"�α��� ����","�޽���",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null,"�α��� ����","�޽���",JOptionPane.ERROR_MESSAGE);
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
