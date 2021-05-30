package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class LetterFrame extends BaseFrame {

	private JPopupMenu popup = new JPopupMenu();
	
	private int num = 1;

	private JLabel lbimg = new JLabel();
	private JLabel lbNum;
	
	public LetterFrame(String n, String wname, String wAddr, String wDate) {
		super("ûø��", 350, 500);
		
		if (n.isEmpty()) {
			this.num = 1;
		} else {
			this.num = Integer.valueOf(n);			
		}
		
		lbNum = new JLabel(String.format("%d�� �̹���", this.num),JLabel.CENTER);
		
		JPanel pnlCenter = new JPanel(null);
	 	JPanel pnlCS= new JPanel(new GridLayout(3, 1,0,-30));
		JPanel pnlS = new JPanel();
		
		JMenuItem item1 = new JMenuItem("1��");
		JMenuItem item2 = new JMenuItem("2��");
		JMenuItem item3 = new JMenuItem("3��");
		JMenuItem item4 = new JMenuItem("����");
		
		JLabel lbCenter = new JLabel("<html>"
				+ "<center>���� ��ٸ� �ӿ���<br/></center>"
				+ "<center>���� �λ��, �� ������ �Ǿ�<br/></center>"
				+ "<center>���� ����� ����� �ΰ� �Ǿ����ϴ�.<br/></center>"
				+ "<center>���ż� �ູ�� �ֽø� �����ϰڽ��ϴ�.</center>", JLabel.CENTER);
		
		pnlCS.add(new JLabel(String.format("����Ȧ��:%s", wname)));
		pnlCS.add(new JLabel(String.format("���:%s", wAddr)));
		pnlCS.add(new JLabel(String.format("��¥:%s", wDate)));
		
		pnlCenter.add(lbCenter);
		pnlCenter.add(pnlCS);
		pnlCenter.add(lbimg);
		
		add(lbNum,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
	
		lbCenter.setBounds(20, 60, 300, 150);
		pnlCS.setBounds(60, 230, 230, 150);
		lbimg.setBounds(0, 0, 335, 470);
		
		popup.add(item1);
		popup.add(item2);
		popup.add(item3);
		popup.add(item4);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					 popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		item1.addActionListener(e -> getinfo(1));
		item2.addActionListener(e -> getinfo(2));
		item3.addActionListener(e -> getinfo(3));
		item4.addActionListener(e -> dicision());
		
		getinfo(this.num);
		
		pnlCS.setBackground(Color.WHITE);
		pnlCS.setOpaque(true);
		
		
	}
	
	private void dicision() {
		iMessage(String.format("������ %d������ �����Ǿ����ϴ�.", num));
		
		selectedLetter = num;
		dispose();
	}

	public static void main(String[] args) {
		new LetterFrame("","","","").setVisible(true);
	}
	
	private void getinfo(int n) {
		num = n;
		lbimg.setIcon(getImageIcon(String.format("./datafile/ûø��/ûø��%d.jpg", num), lbimg.getWidth(), lbimg.getHeight()));
		lbNum.setText(String.format("%d�� �̹���", num));
	}
}

