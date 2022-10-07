package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame implements Runnable {
	
	private JLabel lbTitle;
	public static Color[] color = {Color.red,Color.orange,Color.yellow,Color.green,Color.blue,Color.MAGENTA,Color.pink};
	
	public MainFrame() {
		super("����", 1100, 600);
		setLayout(null);
		JPanel pnlEast = new JPanel(new GridLayout(4, 1,0,20));
		
		JButton btnPro = new JButton("���� �α���");
		JButton btnStu = new JButton("�л� �α���");
		JButton btnAdmin = new JButton("������ �α���");
		JButton btnExit = new JButton("�����ϱ�");
		
		lbTitle = new JLabel("<html>"+"�л����" + "<br/>" +"���α׷�");
		lbTitle.setFont(new Font("���� ���",Font.BOLD,40));
		
		JLabel lbImg = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./�����ڷ�/main.jpg").getScaledInstance(800, 600, Image.SCALE_SMOOTH)));
		
		pnlEast.add(btnPro);
		pnlEast.add(btnStu);
		pnlEast.add(btnAdmin);
		pnlEast.add(btnExit);
		
		addC(lbTitle, 5, 0, 400, 100);
		addC(lbImg, 0, 0, 800, 600);
		addC(pnlEast, 850, 60, 200, 450);

		btnPro.addActionListener(e -> openFrame(new LoginProFrame()));
		btnStu.addActionListener(e -> openFrame(new LoginStuFrame()));
		btnAdmin.addActionListener(e -> openFrame(new LoginAdminFrame()));
		btnExit.addActionListener(e -> System.exit(0));
		
		Thread t = new Thread(this);
		t.start();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				eMessage("���� ���� X��ư���� ����Ͻ� �� �����ϴ�.");
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			}
		});
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
		
	}

	@Override
	public void run() {

		while (true) {
			try {
				for (int i = 0; i < color.length; i++) {
					lbTitle.setForeground(color[i]);
					Thread.sleep(2000);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
