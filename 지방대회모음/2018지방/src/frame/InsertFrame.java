package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InsertFrame extends BaseFrame {

	private JTextField tfNo = new JTextField();
	private JTextField tfName = new JTextField();
	private JPasswordField tfPasswd = new JPasswordField();
	private JPasswordField tfPassWd1 = new JPasswordField();

	public InsertFrame() {
		super("사원등록", 400, 280);
		
		JPanel pnlCenter = new JPanel(new GridLayout(4, 2));
		JPanel pnlSouth = new JPanel();
		
		JButton btnInsert = new JButton("등록");
		JButton btnExit = new JButton("닫기");
		
		pnlCenter.add(new JLabel("사원번호:"));
		pnlCenter.add(tfNo);
		pnlCenter.add(new JLabel("사 원 명:"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("패스워드:"));
		pnlCenter.add(tfPasswd);
		pnlCenter.add(new JLabel("패스워드재입력:"));
		pnlCenter.add(tfPassWd1);
		
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
	
		btnInsert.addActionListener(e -> insert());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
		
		getinfo();
		
		tfNo.setText(String.valueOf(maxNo));
		tfNo.setEnabled(false);
	}
	

	public static void main(String[] args) {
		new InsertFrame().setVisible(true);

	}

	private void getinfo() {
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT max(memberNo) FROM member ");
			if(rs.next()) {
				maxNo = rs.getInt(1)+1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void insert() {
		String no = tfNo.getText();
		String name = tfName.getText();
		String pw = tfPasswd.getText();
		String pw1 = tfPassWd1.getText();
		
		if(no.isEmpty() || name.isEmpty() || pw.isEmpty()||pw1.isEmpty()) {
			eMessage("항목 누락");
		}else if(pw.equals(pw1)==false){
			eMessage("패스워드 확인 요망");
		}else {
			try {
				dbManager.executeUpdate("insert into member values(?,?,?)", no,name,pw);
				iMessage("사원이 등록되었습니다.");
				openFrame(new MainFrame());
			} catch (SQLException e) {
				e.printStackTrace();

			}		
		}
	}
	
	
}

