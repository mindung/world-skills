package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class UserCareFrame extends BaseFrame {
	
	private DefaultTableModel dtm = new DefaultTableModel();
	
	private JPanel pnlUser = new JPanel();
	
	private JButton btnOk = new JButton("확인");
	private JButton btnUpdate = new JButton("수정");
	
	public UserCareFrame() {
		super("유저관리", 900, 600);
		
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
		JScrollPane jsp = new JScrollPane(pnlUser,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		pnlSouth.add(btnOk);
		pnlSouth.add(btnUpdate);
	
		add(pnlSouth,BorderLayout.SOUTH);
		add(jsp,BorderLayout.CENTER);
		
		btnOk.addActionListener(e -> userCancel());
		btnUpdate.addActionListener(e-> userUpdate());
		
		getuser();
		
	}
	
	public static void main(String[] args) {
		new UserCareFrame().setVisible(true);

	}

	private class UserPnl extends JPanel{
		
		private boolean isSelected = false;
		private JTextField tfName;   
		private JTextField tfId;
		private JTextField tfPw;
		private JTextField tfBrith;
		private JTextField tfTel;
		private JTextField tfAddress;
				
		public UserPnl(int no, String name, String id, String pw, String birth, String tel, String address) {
			
			tfName = new JTextField(name);
			tfId = new JTextField(id);
			tfPw = new JTextField(pw);
			tfBrith = new JTextField(birth);
			tfTel = new JTextField(tel);
			tfAddress = new JTextField(address);
	
			this.setLayout(new FlowLayout());
			
			add(tfName);
			add(tfId);
			add(tfPw);
			add(tfBrith);
			add(tfTel);
			add(tfAddress);
			
			for (Component com: this.getComponents()) {
				JTextField tf = (JTextField) com;
				tf.setHorizontalAlignment(SwingConstants.CENTER);
				tf.setPreferredSize(new Dimension(130, 25));
				tf.setEnabled(false);
			}
			
			
			setPreferredSize(new Dimension(880, 35));
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						setBackground(Color.PINK);
						
						isSelected = true;
						tfPw.setEnabled(true);
						tfBrith.setEnabled(true);
						tfTel.setEnabled(true);
						tfAddress.setEnabled(true);
						
					}
				}
			});
		}
		
		private void Update() {
			
			try {
				dbManager.executeUpdate("update user set  u_pw = ?, u_age = ? , u_phone = ?, u_address = ? where u_id = ?", tfPw.getText(), tfBrith.getText(), tfTel.getText(), tfAddress.getText(), tfId.getText());
				
				iMessage("업데이트 되었습니다.");
				
				setBackground(null);
				
				isSelected = false;
				
				tfPw.setEnabled(false);
				tfBrith.setEnabled(false);
				tfTel.setEnabled(false);
				tfAddress.setEnabled(false);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void cancel() {
			
			System.out.println("실행");
			

			openFrame(new AdminFrame());
		}
	
	}
	
	private void userUpdate() {
		
		for (Component com : pnlUser.getComponents()) {
			
			if (com instanceof UserPnl) {
				UserPnl pnl = (UserPnl) com;
				
				if (pnl.isSelected) {
					pnl.Update();
				} else {
					
				}
			}
		}
	}
	
	private void userCancel() {
		int cnt = 0;
		
		for (Component comp : pnlUser.getComponents()) {
			
			if (comp instanceof UserPnl) {
				UserPnl pnl = (UserPnl) comp;
				
				System.out.println(pnl.isSelected);

				if (pnl.isSelected) {
					
					int yn = JOptionPane.showConfirmDialog(null, "아직 수정되지 않았습니다\n 정말로 창을 닫으시겠습니까?", "Warning", JOptionPane.YES_NO_OPTION);

					if(yn == JOptionPane.YES_OPTION) {
						openFrame(new AdminFrame());
					}
					
					cnt++;
				} 
			}
		}
		
		if (cnt == 0) {
			openFrame(new AdminFrame());
		}
	}
	
	private void getuser() {
		
		pnlUser.removeAll();
		String[] str = {"이름", "아이디","비밀번호","생년월일","전화번호","주소"};
		JPanel pnl = new JPanel(new GridLayout(1, 6));
		
		for (String string : str) {
			JLabel lb = new JLabel(string, JLabel.CENTER);
			lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			pnl.add(lb);
		}
		
		pnl.setPreferredSize(new Dimension(750, 10));
		pnlUser.add(pnl);
		
		try {
		
			int cnt = 0;
			
			ResultSet rs = dbManager.executeQuery("select u_no,u_name,u_id,u_pw,u_age,u_phone,u_address from user");
		
			while (rs.next()) {
				pnlUser.add(new UserPnl(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
				cnt++;
			
			}
			
			pnlUser.setLayout(new GridLayout(cnt + 1 , 1));
			pnlUser.setPreferredSize(new Dimension(750, (40 * cnt) -20 ));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

