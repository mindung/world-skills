package frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame {

	public MainFrame() {
		super("������ ����ȭ��", 600,460);
		
		JPanel pnlNorth = new JPanel();
		
		JButton btnInsert = new JButton("�� ���");
		JButton btnSelect = new JButton("�� ��ȸ");
		JButton btnCare = new JButton("�� ����");
		JButton btnExit = new JButton("��  ��");
		
		JLabel lbImage = new JLabel(new ImageIcon("./��2����/img.jpg"));
		
		pnlNorth.add(btnInsert);
		pnlNorth.add(btnSelect);
		pnlNorth.add(btnCare);
		pnlNorth.add(btnExit);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(lbImage,BorderLayout.CENTER);
		
		btnInsert.addActionListener(e -> new InsertFrame().setVisible(true));
		btnSelect.addActionListener(e -> openFrame(new SelectFrame()));
		btnCare.addActionListener(e -> openFrame(new CareFrame()));
		btnExit.addActionListener(e -> openFrame(new LoginFrame()));
		
		
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}

