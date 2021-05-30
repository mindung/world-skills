package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookSearchFrame extends baseFrame{
	
	private JButton btnSearch = new JButton("검색");
	private JButton btnExit = new JButton("취소");
	
	private JTextField tfname = new JTextField(10);
	
	public BookSearchFrame() {
		super("도서검색", 300, 200);
		
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel();
		
		JLabel lbTitle = new JLabel("도서검색",JLabel.CENTER);

		lbTitle.setFont(new Font("굴림",Font.BOLD,15));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setBackground(Color.DARK_GRAY);
		lbTitle.setOpaque(true);
	
		pnlCenter.add(new JLabel("도서제목:"));
		pnlCenter.add(tfname);
		
		pnlSouth.add(btnSearch);
		pnlSouth.add(btnExit);
		
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		add(lbTitle,BorderLayout.NORTH);
		
		btnSearch.addActionListener(e -> search());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new BookSearchFrame().setVisible(true);
	}
	
	private void search() {
		String name = tfname.getText();
		
		if(name.isEmpty()) {
			name = "";
		}else {
		 baseFrame.name = name ;
			dispose();
		}
	}
}

