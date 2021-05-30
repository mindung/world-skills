package frame;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class LoginStuFrame extends BaseFrame{

	private JTextField tfCode = new JTextField();
	private JPasswordField tfPW = new JPasswordField();

	private Icon information = UIManager.getIcon("OptionPane.informationIcon");
	private Icon warning = UIManager.getIcon("OptionPane.warningIcon");
	
	private JLabel lb = new JLabel();
	private JLabel lb1 = new JLabel();
	
	public LoginStuFrame() {
		super("학생로그인", 350, 200);
		
		setLayout(null);
		
		JPanel pnlCenter = new JPanel(new GridLayout(2, 2,-100,30));
	
		pnlCenter.add(new JLabel("학생번호"));
		pnlCenter.add(tfCode);

		pnlCenter.add(new JLabel("암호"));
		pnlCenter.add(tfPW);
		
		addC(pnlCenter, 30, 30, 220, 80);
		addC(lb, 260, 30, 30, 35);
		addC(lb1, 260, 85, 30, 35);
		
		lb.setIcon(warning);
		lb1.setIcon(warning);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				openFrame(new MainFrame());
			}
		});
		
		tfCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				login();
			}
		});
		
		tfPW.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				login();
			}
		});
	}

	public static void main(String[] args) {
		new LoginStuFrame().setVisible(true);
	}
	
	private void login() {
		String code = tfCode.getText();
		String pw = tfPW.getText();
		try {
			ResultSet rs = dbManager.executeQuery("select * from student where s_code = ?", code);
			
			lb.setIcon(rs.next() ? information : warning);
			
			rs = dbManager.executeQuery("select * from student where s_code = ? and s_pw = ?", code,pw);
			
			if(rs.next()) {
				lb1.setIcon(information);
				S_Code = rs.getString(1);
				Info = rs.getString(3);
				openFrame(new StuFrame());
			}else {
				lb1.setIcon(warning);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
}

