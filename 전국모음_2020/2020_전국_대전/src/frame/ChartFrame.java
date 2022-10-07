package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ChartFrame extends BaseFrame {
	
	private static final int COUNT = 3;
	
	private JComboBox<String> comType = new JComboBox<>(new String[] {"인기 웨딩 종류","인기 식사 종류"});

	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"이름","주소","홀사용료"},0);
	private JTable table = new JTable(dtm);

	private Color[] colors = {Color.BLACK,Color.BLUE,Color.RED};	
	
	private JLabel[] lbBars = new JLabel[COUNT];
	private JLabel[] lbTitles = new JLabel[COUNT];
	
	private JLabel[] lbSquare = new JLabel[COUNT];
	private JLabel[] lbSquareStr = new JLabel[COUNT];
	
	public ChartFrame() {
		super("인기 차트", 950, 500);
		
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlEast = new JPanel();
		
		JButton btnPrev = new JButton("◀");
		JButton btnNext = new JButton("▶");
		
		JScrollPane jsp = new JScrollPane(table);

		pnlNorth.add(comType);
		
		pnlSouth.add(btnPrev);
		pnlSouth.add(btnNext);
		
		pnlEast.add(jsp);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlSouth, BorderLayout.SOUTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlEast, BorderLayout.EAST);
		
		comType.setPreferredSize(new Dimension(300, 30));
		jsp.setPreferredSize(new Dimension(400, 350));
		
		pnlEast.setBorder(new EmptyBorder(0, 0, 0, 20));
		
		for (int i = 0; i < COUNT; i++) {
			lbBars[i] = new JLabel();
			lbTitles[i] = new JLabel("야외예식");
			lbSquare[i] = new JLabel();
			lbSquareStr[i] = new JLabel("야외예식 : 111개");
			
			lbBars[i].setOpaque(true);
			lbBars[i].setBackground(colors[i]);
			lbSquare[i].setOpaque(true);
			lbSquare[i].setBackground(colors[i]);
			
			pnlCenter.add(lbBars[i]);
			pnlCenter.add(lbTitles[i]);
			pnlCenter.add(lbSquare[i]);
			pnlCenter.add(lbSquareStr[i]);
			
			lbBars[i].setBounds(50 + (i * 100), 30, 50, 300);
			lbTitles[i].setBounds(50 + (i * 100), 330, 50, 30);
			lbSquare[i].setBounds(350, 150 + (i * 30), 15, 15);
			lbSquareStr[i].setBounds(380, 150 + (i * 30), 100, 15);
		}
	}

	public static void main(String[] args) {
		new ChartFrame().setVisible(true);
	}

}
