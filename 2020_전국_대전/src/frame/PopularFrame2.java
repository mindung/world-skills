package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PopularFrame2 extends BaseFrame{
	
	private JComboBox<String> comPop = new JComboBox<String>(new String[] {"인기 웨딩 종류","인기 식사 종류"});
	
	private Color[] colors = {Color.BLACK,Color.BLUE,null,Color.RED,null};	
	private JLabel[] lbBars = new JLabel[3];
	private JLabel[] lbTitles = new JLabel[3];
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"이름","주소","홀사용료"},0);
	private JTable table = new JTable(dtm);

	
	public PopularFrame2 () {
		super("인기 차트", 600, 500);
	
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(null);
		
		JScrollPane jsp = new JScrollPane(table);
		JScrollPane jsp2 = new JScrollPane();
		
		JButton btnPrev = new JButton("◀");
		JButton btnNext = new JButton("▶");
		
		pnlSouth.add(btnPrev);
		pnlSouth.add(btnNext);
		pnlSouth.add(comPop);
		
//		pnlCenter.add()
		add(pnlSouth,BorderLayout.SOUTH);
		
		comPop.setSelectedIndex(0);
		getInfo();
		comPop.addActionListener(e -> getInfo());
		
		
	}
	
	public static void main(String[] args) {
		new PopularFrame2().setVisible(true);
	}
	
	private void getInfo() {

		int i = 0;
		int max = 0;
		
		if(comPop.getSelectedIndex() == 0) {
			
			setFrameSize(600, 500);
			
			try {
				ResultSet rs = dbManager.executeQuery("SELECT weddingtype.tyno,tyname,count(tyname) as cnt FROM wedding.reservation inner join weddingtype on reservation.tyNo = weddingtype.tyNo group by tyname order by cnt desc limit 3");

				while (rs.next()) {
					int cnt = rs.getInt(3);
					
					if(cnt > max) {
						max = cnt;
					}
					
					if(i < 3) {
						lbTitles[i] = new JLabel(rs.getString(2));
						lbBars[i] = new JLabel();
						lbBars[i].setBackground(colors[i]);
						lbBars[i].setOpaque(true);
						lbBars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
						
						add(lbTitles[i]);
						add(lbBars[i]);
						
						int height = (int) ((250f/max) * cnt);
						int y = 100 + (250 - height);
						
						lbTitles[i].setBounds(50 + (i*100), 440, 50, 30);
						lbBars[i].setBounds(55 + (i*100), y, 50, height);
						
					}
					i++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else {
			
			setFrameSize(1000, 500);
			
			try {
				ResultSet rs = dbManager.executeQuery("SELECT reservation.mno, mname, count(mname) as cnt FROM wedding.reservation inner join mealtype on reservation.mNo = mealtype.mNo group by mname order by cnt desc limit 4");
					
				i = 0;
				max = 0;
			
				while (rs.next()) {
					int cnt = rs.getInt(3);
				
					System.out.println(i + max);
					if(cnt > max) {
						max = cnt;
					}
					
					if(i < 3) {
						lbTitles[i] = new JLabel(rs.getString(2));
						lbBars[i] = new JLabel();
						lbBars[i].setBackground(colors[i]);
						lbBars[i].setOpaque(true);
						lbBars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
						
						add(lbTitles[i]);
						add(lbBars[i]);
						
						int height = (int) ((250f/max) * cnt);
						int y = 100 + (250 - height);
						
						lbTitles[i].setBounds(60 + (i*100), 440, 50, 30);
						lbBars[i].setBounds(55 + (i*100), y, 50, height);
						
					}
					i++;
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setFrameSize(int width, int height) {
		//this.setLayout(new BorderLayout());
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
	}
}



