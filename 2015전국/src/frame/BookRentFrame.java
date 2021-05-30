package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BookRentFrame extends baseFrame{

	private JTextField tfSearch = new JTextField(7);
	private JTextField tfcode = new JTextField(7);
	private JTextField tfname = new JTextField(8);
	private JTextField tfauthor = new JTextField(8);
	private JTextField tfpublisher = new JTextField(8);
	private JTextField tfprice = new JTextField(8);
	private JTextField tfState = new JTextField(8);
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"도서번호","제목","저자","출판사","가격","대출여부"},0);
	private JTable table = new JTable(dtm);
	
	public BookRentFrame() {
		super("도서대여/반납", 700, 750);
		
		JPanel pnl = new JPanel(new BorderLayout());
		JPanel pnlNorth = new JPanel();
		JPanel pnlEast = new JPanel(new GridLayout(3, 1, 0, 10));
		JPanel pnlWest= new JPanel(new GridLayout(3, 1, 0, 10));
		JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JButton btnSearch = new JButton("검색");
		JButton btnRent = new JButton("대여/반납");
		JButton btnReset = new JButton("초기화");
		JButton btnNew = new JButton("새로고침");
		JButton btnExit = new JButton("돌아가기");
		
		JScrollPane jsp = new JScrollPane(table);
		
		pnlNorth.add(new JLabel("도서명"));
		pnlNorth.add(tfSearch);
		pnlNorth.add(btnSearch);
		
		pnlEast.add(new JLabel("도서번호"));
		pnlEast.add(tfcode);
		pnlEast.add(new JLabel("저  자"));
		pnlEast.add(tfauthor);
		pnlEast.add(new JLabel("가격"));
		pnlEast.add(tfprice);
		
		pnlWest.add(new JLabel("제  목"));
		pnlWest.add(tfname);
		pnlWest.add(new JLabel("출 판 사"));
		pnlWest.add(tfpublisher);
		pnlWest.add(new JLabel("대출여부"));
		pnlWest.add(tfState);
		
		pnlLeft.add(btnRent);
		pnlLeft.add(btnReset);
		pnlLeft.add(btnNew);
		pnlLeft.add(btnExit);
		
		pnl.add(pnlNorth,BorderLayout.NORTH);
		pnl.add(pnlEast,BorderLayout.EAST);
		pnl.add(pnlWest,BorderLayout.WEST);
		pnl.add(pnlLeft,BorderLayout.SOUTH);

		add(jsp,BorderLayout.SOUTH);
		add(pnl,BorderLayout.CENTER);
		
		btnSearch.addActionListener(e -> getInfo());
		btnRent.addActionListener(e -> new RentFrame().setVisible(true));
		btnReset.addActionListener(e -> reset());
		btnNew.addActionListener(e -> getBook());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
		 
		try {
			ResultSet rs = dbManager.executeQuery("select* from lib where lib_state = 'Y'");
				while (rs.next()) {
					dtm.addRow(new Object[] {
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6)
					});
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new BookRentFrame().setVisible(true);
	}
	
	private void getInfo() {
		
		String name = tfSearch.getText();
		
		ResultSet rs;
		try {
			rs = dbManager.executeQuery("select * from lib where lib_name = ?", name);
			
			rent = "";
			state = "";
			
			System.out.println(rent);
			if(rs.next()) {
				tfcode.setText(rs.getString(1));
				tfname.setText(rs.getString(2));
				tfauthor.setText(rs.getString(3));
				tfpublisher.setText(rs.getString(4));
				tfprice.setText(rs.getString(5));
				tfState.setText(rs.getString(6));
				
				rent = tfname.getText();
				state = rs.getString(6);
				
				System.out.println(rent);
				System.out.println(state);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getBook() {
		dtm.setRowCount(0);
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from lib");
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void reset() {
		tfSearch.setText("");
		tfcode.setText("");
		tfname.setText("");
		tfauthor.setText("");
		tfpublisher.setText("");
		tfprice.setText("");
		tfState.setText("");
	}
}
