package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class chartFrame extends baseFrame{

	private static final int BAR_WIDTH = 300;
	private static final int BAR_HEIGHT = 30;
	private static final int START_X = 50;
	private static final int START_Y = 50;
	
	private static final Color[] COLORS = {Color.RED, Color.ORANGE,Color.YELLOW, Color.GREEN, Color.BLUE};
	
	private JComboBox<String> comGroup = new JComboBox<String>(new String[] {"음료","푸드","상품"});
	

	public chartFrame() { 
		
		super("인기상품 Top5", 400, 450);
		
		JPanel pnlNorth = new JPanel();
		Chart chart = new Chart();
		
		JLabel lbTitle = new JLabel("인기상품 Top5");
		lbTitle.setFont(new Font("굴림",Font.BOLD,17));
		
		pnlNorth.add(comGroup);
		pnlNorth.add(lbTitle);
		
		pnlNorth.setBackground(Color.LIGHT_GRAY);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(chart,BorderLayout.CENTER);
		
		comGroup.addItemListener(e ->{
			chart.setGroup((String) comGroup.getSelectedItem());
			chart.repaint();
		});
	}
	
	public static void main(String[] args) {
		new chartFrame().setVisible(true);

	}

	private class Chart extends JPanel{
		private String group = "음료";
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);

			int i = 0; // i는 데이터 값
			int max = 0;
			
			g.fillRect(48, 20, 2, 340);
			
			try {
				ResultSet rs = dbmanager.executeQuery("select m_name, count(*) as cnt from orderlist inner join menu on menu.m_no = orderlist.m_no where o_group = ? group by m_name order by cnt desc limit 5", group);
					
				while (rs.next()) {

					String name = rs.getString(1);
					int cnt = rs.getInt(2);
					
					if(cnt > max) {
						max = cnt; // 최대값 구하는 방법....
					}
					
					g.setColor(Color.BLACK); //글자 검은색
					g.drawString(String.format("%s-%d개", name,cnt), START_X + 5, START_Y + 45 + (i * 60));
					g.fillRect(START_X -2, START_Y-2 + (i * 60), (int) ((double) (BAR_WIDTH / max) * cnt) +4, BAR_HEIGHT+4);
					
					g.setColor(COLORS[i]); // bar 색 지정
					g.fillRect(START_X, START_Y + (i * 60), (int) ((double) (BAR_WIDTH / max) * cnt), BAR_HEIGHT);
					i++;
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void setGroup(String group) {
			this.group = group;
		}
	}
	
}
