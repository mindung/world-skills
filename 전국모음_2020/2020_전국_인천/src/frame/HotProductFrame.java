package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.ResultSet;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HotProductFrame extends BaseFrame{
	
	private static final Color[] COLORS = {Color.BLUE, Color.RED, Color.CYAN,Color.YELLOW,Color.GREEN};
	
	private JComboBox<String> comGroup = new JComboBox<String>(new String[] {"����","����","ä��","�ػ깰","������ǰ","����ǰ","��Ȱ��ǰ","�ֹ��ǰ"});
	
	private static final int BAR_WIDTH = 40;
	private static final int BAR_HEIGHT = 440 ;
	private static final int START_X = 30;
	private static final int START_Y = 70;
//	
	
	public HotProductFrame() {
	
		super("�α� ��ǰ", 450, 650);
	
		JPanel pnlNorth = new JPanel();
		Chart chart = new Chart();
		
		JLabel lbBest = new JLabel("Best ��ǰ",JLabel.CENTER);

		lbBest.setFont(new Font("���� ���",Font.BOLD,22));
		
		pnlNorth.add(lbBest);
		pnlNorth.add(comGroup);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(chart,BorderLayout.CENTER);
		
		comGroup.addItemListener(e -> { // ��Ʈ �� ���� �� setgroup�� com�� ���� getselectedItem
		
			chart.setGroup((String) comGroup.getSelectedItem()); // group ->  comGroup ���� ������ �� 
			chart.repaint();
		
		});
	}
	
	public static void main(String[] args) {
		new HotProductFrame().setVisible(true);
	}

	private class Chart extends JPanel{
		private String group = "����";
		
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);

			int i = 0; // i ������ ��
			int max = 0;
		
			try {
				ResultSet rs = dbManager.executeQuery("select c_name,p_name, sum(pu_count) as cnt from purchase "
						+ "inner join product on product.p_no = purchase.p_no "
						+ "inner join category on product.c_no = category.c_no "
						+ "where c_name = ? group by p_name order by cnt desc limit 5",group);
					
				while (rs.next()) {
					
					String name = rs.getString(2);
					int cnt = rs.getInt(3);
					
					if(cnt > max) {
						max = cnt;	// �ִ� �� ���ϴ� ���
					}
					
					g.setColor(Color.BLACK);
					g.drawString(name, START_X + (i * 75), START_Y + 480 );					
					g.drawString(String.format("%d��", cnt), START_X  + (i * 80), START_Y -10 );
					// ����
					g.fillRect(START_X + (i * 80) -2, START_Y + (BAR_HEIGHT - (int) ((double) (BAR_HEIGHT / max) * cnt))-2, BAR_WIDTH+4, (int) ((double) (BAR_HEIGHT / max) * cnt)+2);
					// �׵θ�
					
					g.setColor(COLORS[i]);
					g.fillRect(START_X + (i * 80), START_Y + (BAR_HEIGHT - (int) ((double) (BAR_HEIGHT / max) * cnt)), BAR_WIDTH, (int) ((double) (BAR_HEIGHT / max) * cnt));
					i++; // x ��ǥ  + ( i * 80) , ���� Y��ǥ + ( ���� �� ���� - �ش��ϴ� ����� ����) , ����, ����  
					
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		private void setGroup(String group) {
			this.group = group;
		}
	}
	
	
}
