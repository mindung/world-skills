package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class ChartFrame extends BaseFrame{

	private static final float BAR_MAX_HEIGHT = 100;
	private static final int START_Y = 300;
	
	private JLabel[] lbBars = new JLabel[3];
	private JLabel[] lbTitle= new JLabel[3];
	
	private String[] str  = {"동의", "비동의", "비제출"};
	private Color[] colors = {Color.red, Color.ORANGE, Color.YELLOW};

	private ArrayList<Integer> list = new ArrayList<Integer>();

 	private JComboBox<Reply> comTitle = new JComboBox<Reply>();
	
	public ChartFrame() {
		super("차트", 350, 500);
		
		setLayout(null);
		addC(comTitle, 0, 0, 340, 30);
		
		comTitle.addItemListener(e -> chartPaint());
		
		for (int i = 0; i < str.length; i++) {
			
			lbTitle[i] = new JLabel();
			lbBars[i] = new JLabel();
			
			lbBars[i].setBackground(colors[i]);
	
			lbBars[i].setOpaque(true);
			lbBars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			add(lbBars[i]);
			add(lbTitle[i]);
		}
		
		getInfo();
		chartPaint();
		
	}
	
	public static void main(String[] args) {
		new ChartFrame().setVisible(true);
	}

	private class Reply{
		
		private int r_no;
		private String r_title;
		
		public Reply(int r_no, String r_title) {
			super();
			this.r_no = r_no;
			this.r_title = r_title;
		}
		
		@Override
		public String toString() {
			return r_title;
		}
	}
	
	private void getInfo() {
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from reply ");
			
			while (rs.next()) {
				comTitle.addItem(new Reply(rs.getInt(1), rs.getString(2)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void chartPaint() {
		Reply reply = (Reply) comTitle.getSelectedItem();
		
		int whether1 = 0;
		int whether2 = 0;
		int memberCnt = 0;
		
		int max = 0;
		
		 // 동의, 비동의, 미제출 
		
		try {
			
			ResultSet rs1 = dbManager.executeQuery("select count(r_no) from reply_result where r_no = ? and re_whether = ?",  reply.r_no, 1);
			ResultSet rs2 = dbManager.executeQuery("select count(r_no) from reply_result where r_no = ? and re_whether = ?", reply.r_no, 2);
			ResultSet rs3= dbManager.executeQuery("select count(m_no) from member");
			
			if (rs1.next()) {
				whether1 = rs1.getInt(1);
			}
			
			if (rs2.next()) {
				whether2 = rs2.getInt(1);
			}
			
			if (rs3.next()) {
				memberCnt = rs3.getInt(1) - (whether1 + whether2);
			}
			
			list.clear();
			
			list.add(whether1);
			list.add(whether2);
			list.add(memberCnt);
			
			for (int i = 0; i < list.size(); i++) {
				
				int cnt = list.get(i); 
				
				max = Collections.max(list);
				
				System.out.println(max);
				System.out.println(list.size());
				
				lbTitle[i].setText(String.format("<html>%d개<br/>%s<html>", cnt, str[i]));
				
//				lbBars[i] = new iLabel();
//				lbBars[i].setBackground(colors[i]);
//				lbBars[i].setOpaque(true);
				
				add(lbBars[i]);
				add(lbTitle[i]);
				
				int height = (int) ((100f / max) * cnt);
				int y = 100 + ( 300 - height);
				
//				int hh = (int) (BAR_MAX_HEIGHT / max) * cnt ;
//				int yy = (int) (START_Y + (BAR_MAX_HEIGHT - hh));
//				
//				System.out.println( hh);
//				System.out.println( yy);
				lbTitle[i].setBounds(70 + (i * 80), 410, 80, 50);
				lbBars[i].setBounds(75 + (i * 80), y, 30, height);

			}

		} catch (SQLException e) {

		}
	}
}

