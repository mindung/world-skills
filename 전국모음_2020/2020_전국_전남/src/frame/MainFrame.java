package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import panel.BasePanel;
import panel.LoginPanel;
import panel.MainPanel;

public class MainFrame extends BaseFrame{
	
	private JMenuBar menubar = new JMenuBar();
	
	private JMenu menu1 = new JMenu("파일");
	private JMenu menu2 = new JMenu("일정관리");
	private JMenu menu3 = new JMenu("보고서");
	private JMenu menu4 = new JMenu("그래프");
	
	private JPanel pnlCenter = new JPanel(new BorderLayout());

	public MainFrame() {
		
		super("PrjectLibre", 900, 600);
	
		JMenuItem item1_1 = new JMenuItem("새로만들기");
		JMenuItem item1_2 = new JMenuItem("인쇄");
		JMenuItem item1_3 = new JMenuItem("닫기");
		
		JMenuItem item2_1 = new JMenuItem("칸트차트");
		JMenuItem item2_2 = new JMenuItem("세부작업내용");
		
		menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./지급자료/파일.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./지급자료/일정관리.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./지급자료/보고서.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./지급자료/그래프.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		
		menu1.add(item1_1);
		menu1.add(item1_2);
		menu1.add(item1_3);
		
		menu2.add(item2_1);
		menu2.add(item2_2);
		
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menu3);
		menubar.add(menu4);
	
		menubar.setPreferredSize(new Dimension(900, 35));
		setJMenuBar(menubar);

		item1_1.addActionListener(e -> changePanel(new MainPanel()));
//		item1_2.addActionListener(e -> changePanel(new MainPanel()));
		item1_3.addActionListener(e -> System.exit(0));
		
		add(pnlCenter,BorderLayout.CENTER);
		
//		ImageIcon im = new ImageIcon(Toolkit.getDefaultToolkit().getImage("./지급자료/icon.png"));
//		setIconImage(im.getImage());
		
		Image im = Toolkit.getDefaultToolkit().getImage("./지급자료/icon.png");
		setIconImage(im);

		changePanel(new LoginPanel());
		updateMenu();
		
	}
	
	public static void main(String[] args) {
		BasePanel.frame.setVisible(true);
	}
	
	public void updateMenu() {
		
		menu1.setEnabled(BasePanel.adminNo == 0 ? false : true);//(BasePanel.admin_no == 0 ? false : true);
		menu2.setEnabled(BasePanel.adminNo == 0 ? false : true);
		menu3.setEnabled(BasePanel.adminNo == 0 ? false : true);
		menu4.setEnabled(BasePanel.adminNo == 0 ? false : true);

	}
	
	public void changePanel(BasePanel panel) {
		pnlCenter.removeAll();
		pnlCenter.add(panel,BorderLayout.CENTER);
		pnlCenter.updateUI();
		
	}

}

