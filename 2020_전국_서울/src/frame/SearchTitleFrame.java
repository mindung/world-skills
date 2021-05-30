package frame;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchTitleFrame extends BaseFrame{

	public JTextField tfTitle = new JTextField(15);
	
	private NewsFrame frame;
	
	public SearchTitleFrame() {
		super("검색", 300, 80);
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton btnSearch = createButton("검색"); 
		
		add(new JLabel("제목"));
		add(tfTitle);
		add(btnSearch);
		
		tfTitle.setText("");
		
		btnSearch.setPreferredSize(new Dimension(60, 30));
		btnSearch.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new SearchTitleFrame().setVisible(true);
	}
}
