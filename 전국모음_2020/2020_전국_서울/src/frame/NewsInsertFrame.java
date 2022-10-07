package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NewsInsertFrame extends BaseFrame{

	private JTextField tfTitle = new JTextField(23);
	private JTextArea tfContents = new JTextArea();
	
	public NewsInsertFrame() {
		super("추가", 330, 430);
		
		JPanel pnlN = new JPanel();
		JPanel pnlC = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlS= new JPanel();
		
		JScrollPane jsp = new JScrollPane(tfContents);
		
		JButton btnInsert = createButton("추가");
		JButton btnExit = createButton("닫기");
		
		pnlN.add(new JLabel("제목"));
		pnlN.add(tfTitle);
		
		pnlC.add(new JLabel("내용"));
		pnlC.add(jsp);
		
		pnlS.add(btnInsert);
		pnlS.add(btnExit);
		
		jsp.setPreferredSize(new Dimension(255, 300));
		
		btnInsert.setPreferredSize(new Dimension(60, 30));
		btnExit.setPreferredSize(new Dimension(60, 30));
		
		add(pnlN,BorderLayout.NORTH);
		add(pnlC,BorderLayout.CENTER);
		add(pnlS,BorderLayout.SOUTH);
		
		btnInsert.addActionListener(e -> insert());
		btnExit.addActionListener(e -> dispose());
	}
	
	public static void main(String[] args) {
		new NewsInsertFrame().setVisible(true);
	}
	
	private void insert() {
		String title = tfTitle.getText();
		String contents = tfContents.getText();
		
		if (title.isEmpty()) {
			eMessage("제목을 입력해주세요");
			return;
		}
		
		if(contents.isEmpty()) {
			eMessage("내용을 입력해주세요.");
			return;
		}
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from news where n_title = ?", title);
			if(rs.next()) {
				eMessage("중복된 제목의 통신문이 존재합니다.");
			}else {
				dbManager.executeUpdate("insert into news values(0, ?, ?, ?)", title, contents, LocalDate.now().toString());
				iMessage("추가되었습니다.");
//				dispose();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
}
