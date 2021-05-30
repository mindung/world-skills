package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookInsertFrame extends baseFrame{

	private JButton btnInsert = new JButton("확인");
	private JButton btnExit = new JButton("취소");
	
	private JTextField tfcode = new JTextField(7);
	private JTextField tfname = new JTextField(8);
	private JTextField tfauthor = new JTextField(8);
	private JTextField tfpublisher = new JTextField(8);
	private JTextField tfprice = new JTextField(8);
	private JTextField tfState = new JTextField(8);
	
	public BookInsertFrame() {
		super("도서등록", 300, 250);

		JPanel pnlNorth = new JPanel();
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2));
		
		JLabel lbTitle = new JLabel("회원등록",JLabel.CENTER);

		lbTitle.setFont(new Font("굴림",Font.BOLD,15));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setBackground(Color.DARK_GRAY);
		lbTitle.setOpaque(true);
	
		pnlCenter.add(new JLabel("도서번호"));
		pnlCenter.add(tfcode);
		pnlCenter.add(new JLabel("제목"));
		pnlCenter.add(tfname);
		pnlCenter.add(new JLabel("저자"));
		pnlCenter.add(tfauthor);
		pnlCenter.add(new JLabel("출판사"));
		pnlCenter.add(tfpublisher);
		pnlCenter.add(new JLabel("가격"));
		pnlCenter.add(tfprice);
		pnlCenter.add(new JLabel("대여정보"));
		pnlCenter.add(tfState);
		
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);
		
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(lbTitle,BorderLayout.NORTH);
		
		btnInsert.addActionListener(e -> insert());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new BookInsertFrame().setVisible(true);
	}

	private void insert() {
		String code = tfcode.getText();
		String name = tfname.getText();
		String author = tfauthor.getText();
		String publisher = tfpublisher.getText();
		String price  = tfprice.getText();
		String state = tfState.getText();
		
		if(code.isEmpty()||name.isEmpty() || author.isEmpty() || publisher.isEmpty() || price.isEmpty()||state.isEmpty()) {
			eMessage("빈칸을 채워주세요");
		}else {
			try {
				ResultSet rs = dbManager.executeQuery("select * from lib where lib_code =?", code);
				
				if(rs.next()) {
					iMessage("중복된 도서번호가 있습니다.");
				}else {
					
					if(state.equals("Y") || state.equals("N")) {
						dbManager.executeUpdate("insert into lib values(?,?,?,?,?,?)", code,name,author,publisher,price,state);
						iMessage("처리가 되었습니다.");						
					}else {
						iMessage("Y나N만 입력해주세요.");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
}

