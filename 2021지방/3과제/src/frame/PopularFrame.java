package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PopularFrame extends BaseFrame{
	  
	private JComboBox<String> comPop = new JComboBox<String>(new String[] {"인기 웨딩 종류","인기 식사 종류"});
	
	private static final int BAR_MAX_HEIGHT = 230;
	private static final int START_Y = 50;
	
	private Color[] colors = { Color.BLACK, Color.BLUE, Color.RED };

	private JLabel[] lbBars = new JLabel[3];
	private JLabel[] lbTitles = new JLabel[3];
	
	private JLabel[] lbSquare = new JLabel[3];
	private JLabel[] lbSquareStr = new JLabel[3];
	
	private ArrayList<Info> list = new ArrayList<>();
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] { "이름", "주소", "홀사용료" }, 0);
	private JTable table = new JTable(dtm);
	
	private int index = 0;
	
	private JButton btnPrev = new JButton("◀");
	private JButton btnNext = new JButton("▶");
	
	private JPanel pnlC = new JPanel(null);

	public PopularFrame () {
		super("인기 차트", 400, 400);
		
		JPanel pnlS = new JPanel();
		
		JScrollPane jsp = new JScrollPane(table);
		
		pnlS.add(btnPrev);
		pnlS.add(btnNext);
		
		pnlC.add(comPop);
		pnlC.add(jsp);
		
		add(pnlC , BorderLayout.CENTER);
		add(pnlS, BorderLayout.SOUTH);
		
		
		for (int i = 0; i < lbBars.length; i++) {
			lbTitles[i] = new JLabel("");
			lbSquare[i] = new JLabel();
			lbSquareStr[i] = new JLabel();
			
			lbBars[i] = new JLabel();
			
			lbBars[i].setBackground(colors[i]);
			lbBars[i].setOpaque(true);
			lbBars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			lbSquare[i].setBackground(colors[i]);
			lbSquare[i].setOpaque(true);
			lbSquare[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			pnlC.add(lbTitles[i]);
			pnlC.add(lbBars[i]);
			pnlC.add(lbSquare[i]);
			pnlC.add(lbSquareStr[i]);
			
		}
		
		
		pnlC.add(jsp);
		comPop.setBounds(5, 5, 250, 30);
		jsp.setBounds(400, 40, 350, 250);

		comPop.setSelectedIndex(0);
		
		getInfo();
		IdxManager();
		animation();
		
		comPop.addItemListener(e -> {
			setFrameSize(400, 400);
			getInfo();
			chartPaint();
			animation();
		});
		
		btnNext.addActionListener(e -> nextIdx());
		btnPrev.addActionListener(e -> prevIdx());
		
		
	}

	public static void main(String[] args) {
		new PopularFrame().setVisible(true);
	}
	
	public class Info {

		private int no;
		private String name;
		private int cnt;
		private int y;
		private int height;
		
		public Info(int no, String name, int cnt) {
			super();
			this.no = no;
			this.name = name;
			this.cnt = cnt;
		}
		
	}
	
	private void animation() {
		for (int i = index; i <= index + 2; i++) {
			int num = i;
			Info info = list.get(i);
			
			Thread thread = new Thread() {
				public void run() {
					float h = 0;
					int cnt = 0;
					
					while(h < info.height) {
						h = info.height / 100f * cnt;
						if (h > info.height) {
							h = info.height;
						}
						
						lbBars[num].setBounds(20 + (num * 80), info.y, 35, (int) h);

						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						cnt += 2;
					}
				};
			};
			thread.start();
		}
	}

	private void getInfo() {

		list.clear();
		index = 0;
		
		System.out.println("실행");
		String sql = comPop.getSelectedIndex() == 0 ? "SELECT p.wty_no, wty_name, count(wty_name) as cnt  FROM 2021지방_2.payment as p"
				+ " inner join weddingtype as wt on wt.wty_no = p.wty_no "
				+ "group by wty_name order by cnt desc"
				
				: "SELECT p.m_No, m_name, count(m_name) as cnt  FROM 2021지방_2.payment as p"
						+ " inner join mealtype as mt on mt.m_No = p.m_no "
						+ "group by m_name order by cnt desc";
		
		try {
			ResultSet rs = dbManager.executeQuery(sql);
			
			int i = 0;
			while (rs.next()) {
				list.add(new Info(rs.getInt(1), rs.getString(2), rs.getInt(3)));
				System.out.println(list.get(i).name);
				i++;
			}
			
			System.out.println(list.size());
			
			btnPrev.setEnabled(index == 0 ? false : true);
			btnNext.setEnabled(list.size() == 3 ? false : true);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void prevIdx() {
		index = index - 1;
		IdxManager();
	}
	
	private void nextIdx() {
		index = index + 1;
		IdxManager();
	}
	
	private void IdxManager() {
		
		btnNext.setEnabled(index !=  list.size() - 3 ? true : false);
		btnPrev.setEnabled(index != 0 ? true : false);
		
		chartPaint();
		setFrameSize(400, 400);
	}
	
	private void chartPaint() {
		
		int max = 0;
		int cnt = 0;
		
		System.out.println(index);
		for (int i = index; i <= index + 2; i++) {
			
			Info info = list.get(i);
			
			if (info.cnt > max) {
				max = info.cnt;
			}
			
		}
		
		for (int i = index; i <= index + 2; i++) {
			
			Info info = list.get(i);
			
			lbTitles[cnt].setText(String.valueOf(info.name));
			
			lbBars[cnt].setBackground(colors[cnt]);
			lbBars[cnt].setOpaque(true);
			lbBars[cnt].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			lbBars[cnt].setName(String.valueOf(info.no));
			
			lbSquare[cnt].setBackground(colors[cnt]);
			lbSquare[cnt].setOpaque(true);
			lbSquare[cnt].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			lbSquareStr[cnt].setText(String.format("%s : %d개", info.name, info.cnt));
			
			int height = (int) (BAR_MAX_HEIGHT / max) * info.cnt ;
			int y = (int) (START_Y + (BAR_MAX_HEIGHT - height));
			
			info.y = y;
			info.height = height;
			
			lbTitles[cnt].setBounds(20 + (cnt * 80), 280, 90, 30);
			lbBars[cnt].setBounds(20 + (cnt * 80), y, 35, height);
			lbSquare[cnt].setBounds(250, 150 + (cnt * 30), 10, 10);
			lbSquareStr[cnt].setBounds(280, 150 + (cnt * 30), 150, 10);
			
			int num = cnt;
			
			lbSquare[cnt].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					setFrameSize(800, 400);
					lbBars[num].setBackground(Color.MAGENTA);
					getTableInfo(Integer.valueOf(lbBars[num].getName()));
				}
			});
			
			cnt++;
			
		}	
	}
	
	
	private void getTableInfo(int no) {
	
		dtm.setRowCount(0);
		String sql = comPop.getSelectedIndex() == 0 ? "select wh.* from weddinghall as wh"
				+ " inner join division as d on d.wh_No= wh.wh_No "
				+ "inner join weddingtype as wt on wt.wty_no = d.wty_no "
				+ "where d.wty_no = ?" : 
					
					"select wh.* from weddinghall as wh"
					+ " inner join division as d on d.wh_No = wh.wh_No"
					+ " inner join weddingtype as wt on wt.wty_no"
					+ " = d.wty_no where d.m_no = ?";
		
		
		try {
			ResultSet rs = dbManager.executeQuery(sql, no);
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(2),
						rs.getString(3),
						String.format("%,d원", rs.getInt(5))
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
	
	private void setFrameSize(int width, int height) {
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		
		for (int j = 0; j < lbBars.length; j++) {
			lbBars[j].setBackground(colors[j]);
		}
	}

}







