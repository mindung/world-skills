package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LetterFrame extends BaseFrame {

	private int num = 1;
	private String  rno;
	private JLabel lbimg = new JLabel();
	private JLabel lbN = new JLabel(String.format("%d�� �̹���", num),JLabel.CENTER);
	
	public LetterFrame(String input, String wname, String wAddr, String wDate) {
		super("ûø�� ������ ����", 350, 550);
		this.rno = input;
		
		JPanel pnlCenter = new JPanel(null);
	 	JPanel pnlCS= new JPanel(new GridLayout(3, 1,0,-15));
		JPanel pnlS = new JPanel();
		
		JLabel lbCenter = new JLabel("<html>"
				+ "<center>���� ��ٸ� �ӿ���<br/></center>"
				+ "<center>���� �λ��, �� ������ �Ǿ�<br/></center>"
				+ "<center>���� ����� ����� �ΰ� �Ǿ����ϴ�.<br/></center>"
				+ "<center>���ż� �ູ�� �ֽø� �����ϰڽ��ϴ�.</center>", JLabel.CENTER);
		
		JButton btnPrev = new JButton("��");
		JButton btnDecision = new JButton("����");
		JButton btnNext = new JButton("��");
		
		pnlS.add(btnPrev);
		pnlS.add(btnDecision);
		pnlS.add(btnNext);
		
		pnlCS.add(new JLabel(String.format("����Ȧ��:%s", wname)));
		pnlCS.add(new JLabel(String.format("���:%s", wAddr)));
		pnlCS.add(new JLabel(String.format("��¥:%s", wDate)));
		
		pnlCenter.add(lbCenter);
		pnlCenter.add(pnlCS);
		pnlCenter.add(lbimg);
		
		add(pnlS,BorderLayout.SOUTH);
		add(lbN,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		
		btnNext.addActionListener(e -> nextBtn());
		btnPrev.addActionListener(e -> prevBtn());
		btnDecision.addActionListener(e -> dicision());
	
		lbCenter.setBounds(30, 50, 300, 150);
		pnlCS.setBounds(60, 230, 230, 150);
		lbimg.setBounds(0, 0, 335, 470);
		
		getinfo();
		
		pnlCS.setBackground(null);
		
	}
	
	private void dicision() {
		try {
			iMessage(String.format("������ %d������ �����Ǿ����ϴ�.", num));
			
			if(Integer.valueOf(rno) != 0) {				
				dbManager.executeUpdate("update wedding.reservation set letter = ? where rno = ?", num,rno);
			}
			
			selectedLetter = num;
			dispose();
			
		} catch (SQLException e) {	
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LetterFrame("","","","").setVisible(true);
	}
	
	private void prevBtn() {
		num = num == 1 ? 3 : num - 1;
		getinfo();
	}
	
	private void nextBtn() {
		num = num == 3 ? 1 : num + 1;
		getinfo();
	}
	
	private void getinfo() {
		lbimg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("./datafile/ûø��/ûø��%d.jpg", num)).getScaledInstance(lbimg.getWidth(), lbimg.getHeight(), Image.SCALE_SMOOTH)));
		lbN.setText(String.format("%d�� �̹���", num));
	}
}
