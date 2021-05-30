package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserSearchFrame extends baseFrame{
	
	private JButton btnSearch = new JButton("검색");
	private JButton btnExit = new JButton("취소");
	
	private JTextField tfNum = new JTextField(10);
	
	public UserSearchFrame() {
		super("회원검색", 300, 200);
		
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel();
		
		JLabel lbTitle = new JLabel("회원검색",JLabel.CENTER);

		lbTitle.setFont(new Font("굴림",Font.BOLD,15));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setBackground(Color.DARK_GRAY);
		lbTitle.setOpaque(true);
	
		pnlCenter.add(new JLabel("회원주민번호:"));
		pnlCenter.add(tfNum);
		
		pnlSouth.add(btnSearch);
		pnlSouth.add(btnExit);
		
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		add(lbTitle,BorderLayout.NORTH);
		
		btnSearch.addActionListener(e -> search());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new UserSearchFrame().setVisible(true);
	}
	
	private void search() {
		
		String num = tfNum.getText();
		
		if(num.isEmpty()) {
			eMessage("빈칸이 존재합니다.");
		}else {
			Jumin = num;
			iMessage("검색이 완료되었습니다.");
			dispose();
		}
			
		
	}
}

