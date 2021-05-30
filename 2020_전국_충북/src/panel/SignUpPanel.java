package panel;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SignUpPanel extends BasePanel {

	public SignUpPanel() {
		setLayout(null);
		
		JButton btnSeller = createButton("사업자 회원가입");
		JButton btnUser = createButton("일반 회원가입");
		
		JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 10, 0));
		
		pnlCenter.add(btnSeller);
		pnlCenter.add(btnUser);
		
		pnlCenter.setBackground(null);
		
		add(pnlCenter);
		
		pnlCenter.setBounds(300, 200, 300, 130);
		
		btnSeller.addActionListener(e -> frame.changePanel(new SignUpSellerPanel()));
		btnUser.addActionListener(e -> frame.changePanel(new SignUpUserPanel()));
	}
	
}
