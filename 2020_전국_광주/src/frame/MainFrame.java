package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import panel.AdminPanel;
import panel.BasePanel;
import panel.CalendarPanel;
import panel.ChartPanel;
import panel.LoginPanel;
import panel.MainPanel;
import panel.SignUpPanel;
import frame.BaseFrame;

public class MainFrame extends BaseFrame{
	
	private JButton btnHome = new JButton("홈");
	private JButton btnAdmin = new JButton("관리자 알림");
	private JButton btnCalener = new JButton("달력");
	private JButton btnSignUp = new JButton("회원가입");
	private JButton btnLogin = new JButton("로그인");
	private JButton btnLogout = new JButton("로그아웃");
	
	private JPanel pnlWest = new JPanel(new GridLayout(5, 1));
	private JPanel pnlCenter = new JPanel(new BorderLayout());
	
	public MainFrame() {
		
		super("메인", 800, 500);
		
		pnlWest.add(btnHome);
		pnlWest.add(btnAdmin);
		pnlWest.add(btnCalener);
		pnlWest.add(btnSignUp);
		pnlWest.add(btnLogin);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlWest,BorderLayout.WEST);
		
		pnlWest.setPreferredSize(new Dimension(150, 400));

		btnHome.addActionListener(e -> home());
		btnAdmin.addActionListener(e -> admin());
		btnCalener.addActionListener(e -> calender());
		btnSignUp.addActionListener(e -> changePage(new SignUpPanel()));
		btnLogin.addActionListener(e -> changePage(new LoginPanel()));
		btnLogout.addActionListener(e -> logout());
		
		home();	
	}
	
	public static void main(String[] args) {
		BasePanel.frame.setVisible(true);
		//BasePanel.mainPanel.getdiary();
	}
	
	public void home() {
		
		pnlWest.remove(btnSignUp);
		pnlWest.remove(btnLogin);
		pnlWest.remove(btnLogout);
		
		if(BasePanel.m_index == 0) {
			pnlWest.add(btnSignUp);
			pnlWest.add(btnLogin);	
		}else {
			pnlWest.add(btnLogout);
		}
	
		pnlWest.setLayout(new GridLayout(pnlWest.getComponentCount(), 1));
		pnlWest.updateUI();

		//BasePanel.mainPanel = new MainPanel();
		changePage(BasePanel.mainPanel);
	}

	private void admin() {
		if(BasePanel.m_index == 0) {
			eMessage("로그인을 해주세요");
		}else {
			changePage(new AdminPanel(this));
		}
		
	}
	
	private void calender() {
		if(BasePanel.m_index == 0) {
			eMessage("로그인을 해주세요.");
		}else {
			changePage(new ChartPanel());
		}
	}

	private void logout() {
		BasePanel.m_index = 0;
		home();
	}
	
	public void changePage(BasePanel pnl) {
		pnlCenter.removeAll();
		pnlCenter.add(pnl,BorderLayout.CENTER);
		pnlCenter.updateUI();
	}
}
