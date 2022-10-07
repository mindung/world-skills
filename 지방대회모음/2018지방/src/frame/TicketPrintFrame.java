package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Menu;

public class TicketPrintFrame extends BaseFrame {
	
	private JPanel pnl = new JPanel();
	private ArrayList<Menu> list;
	
	private int cuisineNo;
	private int memberNum;
	
	private String num;
	
	public TicketPrintFrame(ArrayList<Menu> list, int cuisineNo ,int memberNum) {
		super("½Ä±Ç", 350, 440);
		
		this.cuisineNo = cuisineNo;
		this.memberNum = memberNum;
		this.list = list;
		
		num = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddhhmmss")) + "-" +  memberNum + "-"+ cuisineNo;
		
		JScrollPane jsp = new JScrollPane(pnl);
		
		add(jsp, BorderLayout.CENTER);
	
		getinfo();
	}
	
	public static void main(String[] args) {
		ArrayList<Menu> list = new ArrayList<Menu>();
		
		list.add(new Menu("¶±ººÀÌ", 2, 1));
		list.add(new Menu("±è¹ä", 1, 2));
		
		new TicketPrintFrame(list, 1, 1001).setVisible(true);
	}

	private void getinfo() {
		int cnt = 0;
		
		Color colors[] = {Color.CYAN, Color.PINK};
		
		for (int i = 0; i < list.size(); i++) {
			Menu menu = list.get(i);
			
			for (int j = 0; j < menu.getCnt(); j++) {
				addTick(menu, j+1, colors[i % 2]);
				cnt++;
			}
		}
		
		pnl.setBackground(Color.RED);
		pnl.setLayout(new GridLayout(cnt, 1));
		pnl.setPreferredSize(new Dimension(300, 200 * cnt));
	}
	
	private void addTick(Menu menu, int cnt, Color color) {
		
		JPanel jp = new JPanel(new BorderLayout());
		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel(new BorderLayout());
		
		pnlCenter.setBackground(null);
		pnlSouth.setBackground(null);
		
		JLabel lbTitle = new JLabel("½Ä±Ç",JLabel.CENTER);
		JLabel lbPrice = new JLabel("4,500¿ø",JLabel.CENTER);
		
		JLabel lbName = new JLabel(String.format("¸Þ´º : %s", menu.getName()));
		JLabel lbNo = new JLabel(String.format("%s/%s", cnt,menu.getCnt()));
		
		JLabel lbNum = new JLabel(num,JLabel.LEFT);
		
		pnlCenter.add(lbTitle,BorderLayout.NORTH);
		pnlCenter.add(lbPrice,BorderLayout.CENTER);
		
		pnlSouth.add(lbName,BorderLayout.WEST);
		pnlSouth.add(lbNo,BorderLayout.EAST);
		
		jp.add(lbNum,BorderLayout.NORTH);
		jp.add(pnlSouth,BorderLayout.SOUTH);
		jp.add(pnlCenter,BorderLayout.CENTER);
		
		lbTitle.setFont(new Font("±¼¸²",Font.BOLD,25));
		lbPrice.setFont(new Font("±¼¸²",Font.BOLD,25));
		
		jp.setBackground(color);
		jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		jp.setSize(new Dimension(300, 200));
		
		pnl.add(jp);
		
	}
}
