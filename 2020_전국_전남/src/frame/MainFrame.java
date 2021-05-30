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
	
	private JMenu menu1 = new JMenu("����");
	private JMenu menu2 = new JMenu("��������");
	private JMenu menu3 = new JMenu("����");
	private JMenu menu4 = new JMenu("�׷���");
	
	private JPanel pnlCenter = new JPanel(new BorderLayout());

	public MainFrame() {
		
		super("PrjectLibre", 900, 600);
	
		JMenuItem item1_1 = new JMenuItem("���θ����");
		JMenuItem item1_2 = new JMenuItem("�μ�");
		JMenuItem item1_3 = new JMenuItem("�ݱ�");
		
		JMenuItem item2_1 = new JMenuItem("ĭƮ��Ʈ");
		JMenuItem item2_2 = new JMenuItem("�����۾�����");
		
		menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./�����ڷ�/����.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./�����ڷ�/��������.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./�����ڷ�/����.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./�����ڷ�/�׷���.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		
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
		
//		ImageIcon im = new ImageIcon(Toolkit.getDefaultToolkit().getImage("./�����ڷ�/icon.png"));
//		setIconImage(im.getImage());
		
		Image im = Toolkit.getDefaultToolkit().getImage("./�����ڷ�/icon.png");
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

