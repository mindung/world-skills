package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateFrame extends BaseFrame {

	private JTextField tfCode = new JTextField();
	private JTextField tfName = new JTextField();
	private JTextField tfBirth = new JTextField();
	private JTextField tfTel = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JTextField tfCompany = new JTextField();

	public UpdateFrame(String name) {
		super("고객 수정", 500, 320);

		JPanel pnlCenter = new JPanel(new GridLayout(6, 2));
		JPanel pnlSouth = new JPanel();

		JButton btnUpdate = new JButton("수정");
		JButton btnExit = new JButton("닫기");

		pnlCenter.add(new JLabel("고객코드:"));
		pnlCenter.add(tfCode);
		pnlCenter.add(new JLabel("*고 객 명:"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("*생년월일(YYYY-MM-DD):"));
		pnlCenter.add(tfBirth);
		pnlCenter.add(new JLabel("*연 락 처:"));
		pnlCenter.add(tfTel);
		pnlCenter.add(new JLabel("주  소:"));
		pnlCenter.add(tfAddress);
		pnlCenter.add(new JLabel("회  사:"));
		pnlCenter.add(tfCompany);

		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnExit);

		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		tfCode.setEnabled(false);

		btnUpdate.addActionListener(e -> update());
		btnExit.addActionListener(e -> openFrame(new SelectFrame()));

		tfName.setText(name);
		
		getinfo();
		
		tfCode.setEnabled(false);
		tfName.setEnabled(false);
	}

	public static void main(String[] args) {
		new UpdateFrame("").setVisible(true);
	}

	private void getinfo() {
		String name = tfName.getText();
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from customer where name = ?", name);
			
			if(rs.next()) {
			
				tfCode.setText(rs.getString(1));
				tfBirth.setText(rs.getString(3));
				tfTel.setText(rs.getString(4));
				tfAddress.setText(rs.getString(5));
				tfCompany.setText(rs.getString(6));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update() {
		String name = tfName.getText();
		String birth = tfBirth.getText();
		String tel = tfTel.getText();
		String address = tfAddress.getText();
		String company = tfCompany.getText();

		if(birth.isEmpty() || tel.isEmpty() || address.isEmpty() || company.isEmpty()) {
			eMessage("메시지", "빈칸을 채워주세요.");
		}else {
			try {
				dbManager.executeUpdate("update customer set birth = ? , tel = ?, address =? , company  = ? where name = ?", birth,tel,address,company,name);
				iMessage("메시지", "고객수정이 완료되었습니다.");
				
				dispose();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
