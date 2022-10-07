package frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ReplyInsertFrame extends BaseFrame {

	private JTextField tfTitle = new JTextField();

	private JTextField tfStart = new JTextField();
	private JTextField tfEnd = new JTextField();
	
	private JTextArea tfContents = new JTextArea();
	private JTextArea tfterms = new JTextArea();
	
	public ReplyInsertFrame() {
		super("추가", 300, 500);
	
		SelectedEnd = "";
		SelectedStart = "";

		setLayout(null);
		
		JButton btnInsert = createButton("추가");
		JButton btnExit = createButton("닫기");
		
		tfContents.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		tfterms.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		addC(new JLabel("제목"), 2, 5, 60, 30);
		addC(tfTitle, 45, 10, 150, 25);
		
		addC(new JLabel("내용"), 2, 130, 60, 30);
		addC(tfContents, 45, 45, 230, 200);
		
		addC(new JLabel("약관"), 2, 300, 60, 30);
		addC(tfterms, 45, 255, 230, 100);
	
		addC(new JLabel("기한"), 2, 370, 60, 30);
		addC(tfStart, 45, 370, 90, 25);
		addC(new JLabel("-"), 140, 365, 20, 30);
		addC(tfEnd, 150, 370, 90, 25);
		
		addC(btnInsert, 70, 410, 70, 30);
		addC(btnExit, 145, 410, 70, 30);
		
		
		tfStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CalendarFrame frame = new CalendarFrame("start");
				
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						tfStart.setText(SelectedStart);
					}
				});
				frame.setVisible(true);
			}
		});
		
		tfEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CalendarFrame frame = new CalendarFrame("end");
				
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						tfEnd.setText(SelectedEnd);
					}
				});
				
				if(tfStart.getText().isEmpty()) {
					return;
				}
				frame.setVisible(true);
			}
		});
		
		btnExit.addActionListener(e -> dispose());
		btnInsert.addActionListener(e -> insert());
		
	}
	
	public static void main(String[] args) {
		new ReplyInsertFrame().setVisible(true);
	}
	
	private void insert() {
		String title = tfTitle.getText();
		String contents = tfContents.getText();
		String terms = tfterms.getText();
		String start = tfStart.getText();
		String end = tfEnd.getText();
		
		if(title.isEmpty()) {
			eMessage("제목을 입력해주세요.");
			return;
		}
		
		if(contents.isEmpty()) {
			eMessage("내용을 입력해주세요.");
			return;
		}
		
		if(start.isEmpty() || end.isEmpty()) {
			eMessage("기한을 입력해주세요..");
			return;
		}
		
		if(terms.isEmpty()) {
			eMessage("약관을 입력해주세요..");
			return;
		}
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM reminder.reply where r_title = ?", title);
			if (rs.next()) {
				eMessage("중복된 통신문이 존재합니다.");
			}else {
				dbManager.executeUpdate("insert into reply values(0, ?, ?, ?, ?, ?)", title, contents, start, end, terms);
				iMessage("등록되었습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
