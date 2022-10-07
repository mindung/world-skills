package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ChartFrame extends BaseFrame{

	private static final int BAR_WIDTH = 50;
	private static final int BAR_MAX_HEIGHT = 250;
	private static final int BAR_INTERVAL= 100;
	private static final int START_X = 118;
	private static final int START_Y = 40;
	
	private Color[] colors = {Color.red,Color.orange,Color.yellow};
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"진료과","진료건수"}, 0);
	private JTable table = new JTable(dtm);
	
	private JLabel lbTitle = new JLabel("",JLabel.CENTER);

	public ChartFrame() {
		super("통계", 800, 400);
		
		JScrollPane jsp = new JScrollPane(table);
		JPanel pnlCenter = new JPanel();
		
		Chart chart = new Chart();
		
		pnlCenter.add(chart,BorderLayout.CENTER);
		pnlCenter.add(lbTitle,BorderLayout.NORTH);
		
		add(jsp,BorderLayout.WEST);
		add(pnlCenter,BorderLayout.CENTER);
		
		jsp.setPreferredSize(new Dimension(200, 400));
	
		lbTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		
		try {
			ResultSet rs = dbManager.executeQuery("select r_section,count(*) from reservation group by r_section");
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(1),rs.getInt(2)
				});
			}
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
	
		table.getSelectionModel().addListSelectionListener(e ->{
			String section = (String) table.getValueAt(table.getSelectedRow(), 0);
			
			lbTitle.setText(section);

			chart.setSection(section);
			
			
		});
		
		table.setRowSelectionInterval(0, 0);
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
	}
	
	public static void main(String[] args) {
		new ChartFrame().setVisible(true);
	}
	
	private class Chart extends JPanel{
		private String section = "";
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			g.setFont(new Font("맑은 고딕",Font.BOLD,16));
			
			try {
				ResultSet rs = dbManager.executeQuery("select doctor.d_name, count(*) as cnt from reservation inner join doctor on reservation.d_no = doctor.d_no where r_section = ? group by doctor.d_name order by cnt desc", section);

				int i = 0;
				int max = 0;
				
					while (rs.next()) {
					
						String name = rs.getString(1);
						int cnt = rs.getInt(2);
						
				
						if(cnt > max) {
							max = cnt;
						}
						
						int height = (int)((double)BAR_MAX_HEIGHT / max * cnt);
						
						int x = START_X + (i  * (BAR_WIDTH + BAR_INTERVAL));
						int y = START_Y + (BAR_MAX_HEIGHT - height);
						
						g.setColor(Color.BLACK);
						g.drawString(String.valueOf(cnt), x + 15, y -10); // 진료건수 그리기
						g.drawString(name, x, 310); // 의사 이름 그리기
						
						g.fillRect(x -2, y -2, BAR_WIDTH + 4, height + 4); // 검은색 테두리 그리기
						g.setColor(colors[i]);
						g.fillRect(x, y, BAR_WIDTH, height);
						
						i++;
						
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void setSection(String section) {
			this.section = section;
			repaint();
		}
	}
}
