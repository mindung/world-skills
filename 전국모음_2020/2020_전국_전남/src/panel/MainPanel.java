package panel;

import javax.swing.JButton;
import javax.swing.JLabel;

import frame.InsertStaffFrame;
import frame.InsertProjectFrame;
import frame.SelecteProjectFrame;

public class MainPanel extends BasePanel{
	
	public MainPanel() {
		setLayout(null);
	
		JLabel lbTitle = new JLabel("�۾��� �����ϼ���.");
		
		JButton btn1 = new JButton("������Ʈ ����");
		JButton btn2 = new JButton("������Ʈ ����");
		JButton btn3 = new JButton("������Ʈ ���� �߰�");
		JButton btn4 = new JButton("������Ʈ �α��� ȭ��");
	
		add(lbTitle);
		add(btn1);
		add(btn2);
		add(btn3);
		add(btn4);
		
		lbTitle.setBounds(30, 10, 200, 30);
		btn1.setBounds(30, 50, 250, 30);
		btn2.setBounds(30, 90, 250, 30);
		btn3.setBounds(30, 130, 250, 30);
		btn4.setBounds(30, 170, 250, 30);
		
		btn1.addActionListener(e ->  new InsertProjectFrame().setVisible(true));
		btn2.addActionListener(e -> {
			new SelecteProjectFrame().setVisible(true);
			
		});
		
		btn3.addActionListener(e -> new InsertStaffFrame().setVisible(true));
		btn4.addActionListener(e -> {
			
			adminNo = 0;
			frame.changePanel(new LoginPanel());
			frame.updateMenu();
		
		});
		
	}

}

