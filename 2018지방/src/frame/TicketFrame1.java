package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class TicketFrame1 extends BaseFrame implements Runnable{
	
	private JLabel lb = new JLabel();

	private JButton btn1= new JButton();
	private JButton btn2= new JButton();
	private JButton btn3= new JButton();
	private JButton btn4= new JButton();
	
	private JPanel pnlCenter = new JPanel(new GridLayout(2, 2));
	
	private ImageIcon img1;
	private ImageIcon img2;
	private ImageIcon img3;
	private ImageIcon img4;
	
	private JTabbedPane jtp = new JTabbedPane();
	private JPanel pnlSouth = new JPanel();
	
	public TicketFrame1() {
		
		super("식권 발매 프로그램", 400,550);
		
		JLabel lbTitle = new JLabel("식권 발매 프로그램",JLabel.CENTER);
		lbTitle.setFont(new Font("굴림체",Font.BOLD,22));

		img1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage("./DataFiles/menu_1.png").getScaledInstance(170, 210,Image.SCALE_SMOOTH));
		img2 = new ImageIcon(Toolkit.getDefaultToolkit().getImage("./DataFiles/menu_2.png").getScaledInstance(170, 210,Image.SCALE_SMOOTH));
		img3 = new ImageIcon(Toolkit.getDefaultToolkit().getImage("./DataFiles/menu_3.png").getScaledInstance(170, 210,Image.SCALE_SMOOTH));
		img4 = new ImageIcon(Toolkit.getDefaultToolkit().getImage("./DataFiles/menu_4.png").getScaledInstance(170, 210,Image.SCALE_SMOOTH));
		
		
		for (int i = 1; i <= 4; i++) {
//			String.format("img%s",i);
//			JLabel lb[] = (JLabel) new JLabel("img[i]") 
		}
		
		btn1 = new JButton(img1);
		btn2 = new JButton(img2);
		btn3 = new JButton(img3);
		btn4 = new JButton(img4);
		
		pnlCenter.add(btn1);
		pnlCenter.add(btn2);
		pnlCenter.add(btn3);
		pnlCenter.add(btn4);
		
		btn1.setToolTipText("한식");
		btn2.setToolTipText("중식");
		btn3.setToolTipText("일식");
		btn4.setToolTipText("양식");
		
		jtp.add("메뉴",pnlCenter);
		
		pnlSouth.add(lb);
		
		jtp.setPreferredSize(new Dimension(400, 500));
		
		add(lbTitle,BorderLayout.NORTH);
		add(jtp,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		pnlSouth.setBackground(Color.BLACK);
		
		lb.setForeground(Color.WHITE);
		lb.setFont(new Font("굴림",Font.BOLD,15));
		
		setAction();
		
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				lb.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("현재시간 : yyyy년 MM월 dd일 hh시 mm분 ss초")));
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void setAction() {
		btn1.addActionListener(e -> {
			new PayFrame(1,"한식").setVisible(true);
		});

		btn2.addActionListener(e -> {
			new PayFrame(2,"중식").setVisible(true);
		});
		
		btn3.addActionListener(e -> {
			new PayFrame(3,"일식").setVisible(true);
		});
		
		btn4.addActionListener(e -> {
			new PayFrame(4,"양식").setVisible(true);
		});
	}
	
	public static void main(String[] args) {
		new TicketFrame1().setVisible(true);
	}
}
