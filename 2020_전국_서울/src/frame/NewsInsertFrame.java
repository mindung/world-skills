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
		super("�߰�", 330, 430);
		
		JPanel pnlN = new JPanel();
		JPanel pnlC = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlS= new JPanel();
		
		JScrollPane jsp = new JScrollPane(tfContents);
		
		JButton btnInsert = createButton("�߰�");
		JButton btnExit = createButton("�ݱ�");
		
		pnlN.add(new JLabel("����"));
		pnlN.add(tfTitle);
		
		pnlC.add(new JLabel("����"));
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
			eMessage("������ �Է����ּ���");
			return;
		}
		
		if(contents.isEmpty()) {
			eMessage("������ �Է����ּ���.");
			return;
		}
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from news where n_title = ?", title);
			if(rs.next()) {
				eMessage("�ߺ��� ������ ��Ź��� �����մϴ�.");
			}else {
				dbManager.executeUpdate("insert into news values(0, ?, ?, ?)", title, contents, LocalDate.now().toString());
				iMessage("�߰��Ǿ����ϴ�.");
//				dispose();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
}
