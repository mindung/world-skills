
package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginAdminFrame extends BaseFrame {

	private JPanel pnlCenter = new JPanel(new GridLayout(4, 3));

	private JTextField tfPw = new JTextField();
	
	private ArrayList<Integer> numbers = new ArrayList<>(); // 0-9
	
	public LoginAdminFrame() {
		super("관리자확인", 300, 450);
		
		this.setBackground(Color.WHITE);
		
		JPanel pnlNorth = new JPanel(new BorderLayout());
		
		tfPw.setBorder(BorderFactory.createLineBorder(Color.blue));
		tfPw.setBackground(Color.WHITE);
		
		JLabel lbTitle = new JLabel("관리자 비밀번호 입력",JLabel.CENTER);
		lbTitle.setFont(new Font("굴림",Font.BOLD,25));

		pnlNorth.add(lbTitle,BorderLayout.NORTH);
		pnlNorth.add(tfPw,BorderLayout.CENTER);
		
		tfPw.setPreferredSize(new Dimension(0, 35));
		tfPw.setHorizontalAlignment(JLabel.CENTER);
		
		pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		
		for (int i = 0; i < 10; i++) {
			numbers.add(i);
		}
		
		for (int i = 0; i < 12; i++) {
			if (i == 9) {
				addLabel("ENTER");
			} else if (i == 11) {
				addLabel("←");
			} else {
				addLabel(null);				
			}
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				openFrame(new MainFrame());
			}
		});
	}
	
	public static void main(String[] args) {
		new LoginAdminFrame().setVisible(true);
	}

	private void addLabel(String str) {
	
		JLabel lb = new JLabel("",JLabel.CENTER);
		
		if (str == null) {
			lb.setText(String.valueOf(getRandomIndex()));			
		} else {
			lb.setText(str);
		}
		
		lb.setBackground(Color.WHITE);
		
		lb.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (str.equals("←")) {
//					"12345".substring(0, 4) => 1234
					
					if (!tfPw.getText().isEmpty()) {
						tfPw.setText(tfPw.getText().substring(0, tfPw.getText().length() - 1));							
					}
					
				} else if (str.equals("ENTER")) {
					
					if (tfPw.getText().equals("12345")) {
						iMessage("비밀번호가 확인되었습니다.");
						openFrame(new AdminFrame());
					} else {
						eMessage("관리자 비밀번호 오류");
						tfPw.setText("");
						openFrame(new MainFrame());
					}
					
				}else {
					tfPw.setText(tfPw.getText() + str);
				}
			}
		});
		
		pnlCenter.add(lb);
	}
	
	private int getRandomIndex() {
		int index = (int) (Math.random() * numbers.size());

		int n = numbers.get(index);
		numbers.remove(index);
		return n;
	}
}
