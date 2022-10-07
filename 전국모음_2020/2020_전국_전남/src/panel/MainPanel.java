package panel;

import javax.swing.JButton;
import javax.swing.JLabel;

import frame.InsertStaffFrame;
import frame.InsertProjectFrame;
import frame.SelecteProjectFrame;

public class MainPanel extends BasePanel{
	
	public MainPanel() {
		setLayout(null);
	
		JLabel lbTitle = new JLabel("작업을 선택하세요.");
		
		JButton btn1 = new JButton("프로젝트 생성");
		JButton btn2 = new JButton("프로젝트 열기");
		JButton btn3 = new JButton("프로젝트 직원 추가");
		JButton btn4 = new JButton("프로젝트 로그인 화면");
	
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

