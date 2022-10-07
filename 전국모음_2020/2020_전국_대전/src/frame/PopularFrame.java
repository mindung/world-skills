package frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PopularFrame extends BaseFrame{
	  
	private JComboBox<String> comPop = new JComboBox<String>(new String[] {"인기 웨딩 종류","인기 식사 종류"});
	
	private Color[] colors = {Color.BLACK,Color.BLUE,Color.RED};	
	
	private JLabel[] lbBars = new JLabel[3];
	private JLabel[] lbTitles = new JLabel[3];
	
	private JLabel[] lbSquare = new JLabel[3];
	private JLabel[] lbSquareStr = new JLabel[3];
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"이름","주소","홀사용료"},0);
	private JTable table = new JTable(dtm);
	
	public PopularFrame () {
		super("인기 차트", 600, 500);
		
		setLayout(null);
		
		JScrollPane jsp = new JScrollPane(table);
		JScrollPane jsp2 = new JScrollPane();
		
		JButton btnPrev = new JButton("◀");
		JButton btnNext = new JButton("▶");
		
		addC(comPop, 5, 5, 250, 25);
		addC(btnPrev, 200, 430, 50, 30);
		addC(btnNext, 255, 430, 50, 30);
		
		comPop.setSelectedIndex(0);
		getInfo();
		comPop.addActionListener(e -> {
//			PopularFrame frame = new PopularFrame();
//			frame.removeAll();
			getInfo();
		});
		
	}
	
	public static void main(String[] args) {
		new PopularFrame().setVisible(true);
	}
	
	private void getInfo() {
		int i = 0;
		int max = 0;
		
		String sql = comPop.getSelectedIndex() == 0 ? "SELECT weddingtype.tyno,tyname,count(tyname) as cnt FROM wedding.reservation inner join weddingtype on reservation.tyNo = weddingtype.tyNo group by tyname order by cnt desc" : "SELECT reservation.mno, mname, count(mname) as cnt FROM wedding.reservation inner join mealtype on reservation.mNo = mealtype.mNo group by mname order by cnt desc";
			
			try {
				
				setFrameSize(600, 500);

				ResultSet rs = dbManager.executeQuery(sql);
				
				while (rs.next()) {
					int cnt = rs.getInt(3);
					String name = rs.getString(2);
					if(cnt > max) {
						max = cnt;
					}
					
					if(i < 3) {
						
						lbTitles[i] = new JLabel(rs.getString(2));
						
						lbBars[i] = new JLabel();
						lbBars[i].setBackground(colors[i]);
						lbBars[i].setOpaque(true);
						
						lbSquare[i] = new JLabel();
						lbSquare[i].setBackground(colors[i]);
						lbSquare[i].setOpaque(true);
						
						lbSquareStr[i] = new JLabel(String.format("%s : %d개", name,cnt));
						
						lbBars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
						lbSquare[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
						
						add(lbTitles[i]);
						add(lbBars[i]);
						add(lbSquare[i]);
						add(lbSquareStr[i]);
						
						int height = (int) ((250f/max) * cnt);
						int y = 100 + (250 - height);
						
						lbTitles[i].setBounds(50 + (i*100), 350, 70, 30);
						lbBars[i].setBounds(55 + (i*100), y, 50, height);
						lbSquare[i].setBounds(350, 200+(i*30), 10, 10);
						lbSquareStr[i].setBounds(390, 200+(i*30), 150, 10);
						
						lbSquare[i].addMouseListener(new MouseAdapter() {
							
							public void mouseClicked(MouseEvent e) {
								lbBars[1].setBackground(Color.pink);
								iMessage("눌렀땅");
								setFrameSize(1000, 500);
							}
						});
					}
					i++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	private void setFrameSize(int width, int height) {
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
	}
}



