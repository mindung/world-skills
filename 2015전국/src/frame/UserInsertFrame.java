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

public class UserInsertFrame extends baseFrame{

	private JButton btnInsert = new JButton("Ȯ��");
	private JButton btnExit = new JButton("���");
	
	private JTextField tfSearch = new JTextField(7);
	private JTextField tfName = new JTextField(8);
	private JTextField tfJumin = new JTextField(8);
	private JTextField tfTel = new JTextField(8);
	private JTextField tfAddress = new JTextField(8);
	
	public UserInsertFrame() {
		super("ȸ������ ����", 300, 200);

		JPanel pnlNorth = new JPanel();
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(new GridLayout(4, 2));
		
		JLabel lbTitle = new JLabel("ȸ�����",JLabel.CENTER);

		lbTitle.setFont(new Font("����",Font.BOLD,15));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setBackground(Color.DARK_GRAY);
		lbTitle.setOpaque(true);
	
		pnlCenter.add(new JLabel("�̸�"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("�ֹι�ȣ"));
		pnlCenter.add(tfJumin);
		pnlCenter.add(new JLabel("����ó"));
		pnlCenter.add(tfTel);
		pnlCenter.add(new JLabel("�ּ�"));
		pnlCenter.add(tfAddress);
		
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);
		
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(lbTitle,BorderLayout.NORTH);
		
		btnInsert.addActionListener(e -> insert());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new UserInsertFrame().setVisible(true);
	}

	private void insert() {
		String name = tfName.getText();
		String jumin = tfJumin.getText();
		String tel = tfTel.getText();
		String address = tfAddress.getText();
		
		if(name.isEmpty() || jumin.isEmpty() || tel.isEmpty() || address.isEmpty()) {
			eMessage("��ĭ�� ä���ּ���");
		}else {
			try {
				ResultSet rs = dbManager.executeQuery("select * from member where mb_num =?", jumin);
				
				if(rs.next()) {
					iMessage("�ߺ��� ���̵� �ֽ��ϴ�.");
				}else {
					dbManager.executeUpdate("insert into member values(?,?,?,?)", name,jumin,tel,address);
					iMessage("ó���� �Ǿ����ϴ�.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
}

