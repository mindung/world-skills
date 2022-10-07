package panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import frame.MainFrame;

public class SignUpPanel extends BasePanel{
	
	private JTextField tfId = new JTextField(10);
	private JPasswordField tfPw = new JPasswordField(10);
	private JTextField tfName = new JTextField(10);
	private JTextField tfHeight = new JTextField(10);
	
	private ButtonGroup bg = new ButtonGroup();
	
	private JRadioButton rbMale = new JRadioButton("��");
	private JRadioButton rbFeMale = new JRadioButton("��");
	
	
	public SignUpPanel() {
	
		JPanel pnlCenter =new JPanel(new GridLayout(4, 2, -200, 20));
		JPanel pnlSouth = new JPanel();
		JPanel pnlbtn = new JPanel();
		
		JButton btnInsert = new JButton("ȸ������ �Ϸ�");
		
		pnlCenter.add(new JLabel("���̵�"));
		pnlCenter.add(tfId);
		pnlCenter.add(new JLabel("��й�ȣ"));
		pnlCenter.add(tfPw);
		pnlCenter.add(new JLabel("�̸�"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("Ű"));
		pnlCenter.add(tfHeight);
		
		pnlSouth.add(rbMale);
		pnlSouth.add(rbFeMale);
		
		pnlbtn.add(btnInsert);
		
		bg.add(rbMale);
		bg.add(rbFeMale);
		
		add(pnlCenter);
		add(pnlSouth);
		add(pnlbtn);
		
		pnlCenter.setPreferredSize(new Dimension(330, 250));
		pnlSouth.setPreferredSize(new Dimension(330, 80));
		pnlbtn.setPreferredSize(new Dimension(330, 50));
		
		btnInsert.addActionListener(e -> insert());
	}
	
	private void insert() {
		
		String id = tfId.getText();
		String pw = tfPw.getText();
		String name = tfName.getText();
		String StrHeight = tfHeight.getText();
		
		if(id.isEmpty() || pw.isEmpty() || name.isEmpty() || StrHeight.isEmpty()) {
			eMessage("��ĭ�� �����մϴ�.");
			return;
		}
		
		if(id.length() < 4 || id.length() > 12) {
			eMessage("4~12���� ���̷� �Է����ּ���.");
			return;
		}
		
		if(pw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()~]).{6,}$")== false) {
			eMessage("��й�ȣ ������ ��ġ���� �ʽ��ϴ�.");
			return;
		}
		
		try {
			int height = Integer.valueOf(StrHeight);
			
			String gender = rbMale.isSelected() ? "��" : "��";
		
			dbManager.executeUpdate("insert into tbl_member values(0,?,?,?,?,?,?)", id,pw,name,gender,LocalDate.now().toString(),height);
			
			iMessage("ȸ�������� �Ϸ�Ǿ����ϴ�.");
			
			frame.home();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

