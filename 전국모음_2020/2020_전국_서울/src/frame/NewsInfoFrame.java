package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NewsInfoFrame extends BaseFrame{
	
	private JTextArea tfContents = new JTextArea();
	
	private  JLabel lbTitle = createLabel("");
	public NewsInfoFrame(int n_no) {

		super("학교소식", 350, 500);

		JPanel pnlS = new JPanel();
		JButton btnExit = createButton("확인");

		pnlS.add(btnExit);
		
		add(lbTitle, BorderLayout.NORTH);
		add(tfContents, BorderLayout.CENTER);
		add(pnlS, BorderLayout.SOUTH);
		
		lbTitle.setFont(new Font("HY견고딕",Font.BOLD,16));
		
		btnExit.setPreferredSize(new Dimension(60, 30));
		tfContents.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		btnExit.addActionListener(e -> dispose());
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from news where n_no = ?", n_no);
			if (rs.next()) {
				lbTitle.setText(rs.getString(2));
				
				String n_content = rs.getString(3);
				String content = "";
				
				for (int i = 0; i < n_content.length(); i++) {
					if (i != 0 && i % 25 == 0) {
						content += "\r\n";
					}
					
					content += n_content.substring(i, i + 1);
				}
				
				tfContents.setText(content);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new NewsInfoFrame(1).setVisible(true);
	}
}
