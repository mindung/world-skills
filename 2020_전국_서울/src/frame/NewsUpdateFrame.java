package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NewsUpdateFrame extends BaseFrame{

	private JTextField tfTitle = new JTextField(23);
	private JTextArea tfContents = new JTextArea();
	
	private int n_no;
	public NewsUpdateFrame(int n_no) {
		super("수정", 330, 430);
		
		this.n_no = n_no;
		
		JPanel pnlN = new JPanel();
		JPanel pnlC = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlS= new JPanel();
		
		JScrollPane jsp = new JScrollPane(tfContents);
		
		JButton btnUpdate = createButton("수정");
		JButton btnExit = createButton("닫기");
		
		pnlN.add(new JLabel("제목"));
		pnlN.add(tfTitle);
		
		pnlC.add(new JLabel("내용"));
		pnlC.add(jsp);
		
		pnlS.add(btnUpdate);
		pnlS.add(btnExit);
		
		jsp.setPreferredSize(new Dimension(255, 300));
		
		btnUpdate.setPreferredSize(new Dimension(60, 30));
		btnExit.setPreferredSize(new Dimension(60, 30));
		
		add(pnlN,BorderLayout.NORTH);
		add(pnlC,BorderLayout.CENTER);
		add(pnlS,BorderLayout.SOUTH);
		
		btnUpdate.addActionListener(e -> updateInfo());
		btnExit.addActionListener(e -> dispose());
		
		getinfo();
	
		tfTitle.setEnabled(false);
		tfContents.setLineWrap(true);
	}
	
	public static void main(String[] args) {
		new NewsUpdateFrame(1).setVisible(true);
	}
	
	private void updateInfo() {
		
		try {
			dbManager.executeUpdate("update news set n_content = ?, n_date =? where n_no = ?", tfContents.getText(), LocalDate.now().toString(),n_no);
			iMessage("수정되었습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getinfo() {
		try {
			ResultSet rs = dbManager.executeQuery("select * from news where n_no =?", n_no);
			if (rs.next()) {
				tfTitle.setText(rs.getString(2));
				tfContents.setText(rs.getString(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
