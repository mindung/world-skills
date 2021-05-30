package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserUpdateFrame extends baseFrame{
	
	private JButton btnSearch = new JButton("�˻�");
	private JButton btnUpdate = new JButton("����");
	private JButton btnExit = new JButton("���ư���");
	
	private JTextField tfSearch = new JTextField(7);
	private JTextField tfName = new JTextField(8);
	private JTextField tfJumin = new JTextField(8);
	private JTextField tfTel = new JTextField(8);
	private JTextField tfAddress = new JTextField(8);
	
	public UserUpdateFrame() {
		super("ȸ������ ����", 300, 200);

		JPanel pnlNorth = new JPanel();
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(new GridLayout(4, 2));
		
		pnlNorth.add(new JLabel("ȸ���ֹι�ȣ:"));
		pnlNorth.add(tfSearch);
		pnlNorth.add(btnSearch);
		
		pnlCenter.add(new JLabel("�̸�"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("�ֹι�ȣ"));
		pnlCenter.add(tfJumin);
		pnlCenter.add(new JLabel("����ó"));
		pnlCenter.add(tfTel);
		pnlCenter.add(new JLabel("�ּ�"));
		pnlCenter.add(tfAddress);
		
		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnExit);
		
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlNorth,BorderLayout.NORTH);
		
		btnSearch.addActionListener(e -> getInfo());
		btnUpdate.addActionListener(e -> update());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new UserUpdateFrame().setVisible(true);
	}

	private void getInfo() {
		
		String info = tfSearch.getText();
		
		ResultSet rs;
		try {
			rs = dbManager.executeQuery("select * from member where mb_num = ?", info);
		
			if(rs.next()) {
				tfName.setText(rs.getString(1));
				tfJumin.setText(rs.getString(2));
				tfTel.setText(rs.getString(3));
				tfAddress.setText(rs.getString(4));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update() {
		String search = tfSearch.getText();
		String name = tfName.getText();
		String num = tfJumin.getText();
		String tel = tfTel.getText();
		String address = tfAddress.getText();
		
		if(name.isEmpty() || num.isEmpty()|| tel.isEmpty()||address.isEmpty()) {
			eMessage("��ĭ�� �����մϴ�.");
		}else {
			
			try {
				
				dbManager.executeUpdate("update member set mb_name = ? , mb_num = ? , mb_phone = ? , mb_addr = ? where mb_num =? ", name,num,tel,address,search);
				
				iMessage("������ �Ϸ�Ǿ����ϴ�.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	
	
}

