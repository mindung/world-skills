package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserSearchFrame extends baseFrame{
	
	private JButton btnSearch = new JButton("�˻�");
	private JButton btnExit = new JButton("���");
	
	private JTextField tfNum = new JTextField(10);
	
	public UserSearchFrame() {
		super("ȸ���˻�", 300, 200);
		
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel();
		
		JLabel lbTitle = new JLabel("ȸ���˻�",JLabel.CENTER);

		lbTitle.setFont(new Font("����",Font.BOLD,15));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setBackground(Color.DARK_GRAY);
		lbTitle.setOpaque(true);
	
		pnlCenter.add(new JLabel("ȸ���ֹι�ȣ:"));
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
			eMessage("��ĭ�� �����մϴ�.");
		}else {
			Jumin = num;
			iMessage("�˻��� �Ϸ�Ǿ����ϴ�.");
			dispose();
		}
			
		
	}
}

