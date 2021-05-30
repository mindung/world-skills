package frame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserFrame extends baseFrame{
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"이름","주민번호","전화번호","주소"},0);
	private JTable table = new JTable(dtm);
	
	public UserFrame() {
		super("회원목록", 480, 530);
		
		JPanel pnlCenter = new JPanel();
		JPanel pnlNorth = new JPanel();
		
		JScrollPane jsp = new JScrollPane(table);
		
		JButton btn1 = new JButton("새로고침");
		JButton btn2 = new JButton("등록");
		JButton btn3 = new JButton("조회");
		JButton btn4 = new JButton("수정");
		JButton btn5 = new JButton("삭제");
		JButton btn6 = new JButton("돌아가기");
		
		pnlNorth.add(btn1);
		pnlNorth.add(btn2);
		pnlNorth.add(btn3);
		pnlNorth.add(btn4);
		pnlNorth.add(btn5);
		pnlNorth.add(btn6);
		
		pnlCenter.add(jsp);
		pnlCenter.setBorder(BorderFactory.createTitledBorder("회원목록"));
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlNorth,BorderLayout.NORTH);
		
		getUser();

		btn1.addActionListener(e -> getUser());
		btn2.addActionListener(e -> new UserInsertFrame().setVisible(true));
		btn3.addActionListener(e -> openSearchFrame());
		btn4.addActionListener(e -> new UserUpdateFrame().setVisible(true));
		btn5.addActionListener(e -> new UserDeleteFrame().setVisible(true));
		btn6.addActionListener(e -> openFrame(new MainFrame()));
		
	}
	
	public static void main(String[] args) {
		new UserFrame().setVisible(true);
	}
	
	private void getUser() {
		dtm.setRowCount(0);
		
		ResultSet rs;
		try {
			rs = dbManager.executeQuery("select * from member");
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4)
				});
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void openSearchFrame() {
		
		UserSearchFrame frame = new UserSearchFrame();
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				
				// BaseFrame에서 만든 jumin static 변수가 비어있으면 검색할 필요 없음
				if (Jumin.isEmpty() == false) {
					dtm.setRowCount(0);
					
					try(ResultSet rs = dbManager.executeQuery("select * from member where mb_num = ?", Jumin )) {
						while(rs.next()) {
							dtm.addRow(new Object[] {
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4)
							});
						}				
						// 한번 검색하고 변수를 초기화 시켜야됨
						// 초기화를 안할 경우, 한번 검색하고 새로고침을 한 뒤 폼을 다시 열어 검색을 안하고 취소로 폼을 닫으면 이전에 검색한 값이 남아있음
						Jumin = "";
						
					} catch (SQLException ee) {
						ee.printStackTrace();
					}
				}
			}
		});
		
		frame.setVisible(true);
	}
}
