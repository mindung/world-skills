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
		super("�α���", 430, 220);
	
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel lbTitle = new JLabel("��ɸ���",JLabel.CENTER);
		JLabel lbId = new JLabel("���̵�",JLabel.RIGHT);
		JLabel lbPw = new JLabel("��й�ȣ",JLabel.RIGHT);
		JLabel lbInsert = new JLabel("ȸ������   ");
		
		JButton btnLogin = new JButton("�α���");
		
		lbTitle.setFont(new Font("HY������M",Font.BOLD,25));
		
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
			eMessage("��ĭ�� �ֽ��ϴ�.");

		} else {
			
			if(id.equals("admin") && pw.equals("1234")) {
			
				iMessage("�����ڷ� �α��� �ϼ̽��ϴ�.");
				openFrame(new AdminFrame());
				
			} else {
				
				try {

					ResultSet rs = dbManager.executeQuery("select * from user where u_id = ? and u_pw = ?", id, pw);

					if (rs.next()) {

						u_no = rs.getInt(1);
						u_name = rs.getString(5);
						iMessage(String.format("%s�� ȯ���մϴ�.", u_name));
						openFrame(new ProductFrame());

					} else {

						eMessage("���̵� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
}

