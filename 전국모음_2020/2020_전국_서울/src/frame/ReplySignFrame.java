package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReplySignFrame extends BaseFrame {
	
	private JLabel lbSign = createLbImg(Color.BLACK);
			
	public ReplySignFrame() {
		super("", 200, 150);
		
		JPanel pnlS = new JPanel();
		JPanel pnlC = new JPanel();
		
		JButton btnOk = createButton("확인");
		
		pnlC.add(lbSign);
		pnlS.add(btnOk);
		
		add(new JLabel(), BorderLayout.NORTH);
		add(pnlC, BorderLayout.CENTER);
		add(pnlS,BorderLayout.SOUTH);

		lbSign.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.err.println("사인");
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				System.err.println("사인");
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				System.err.println("사인을 합시당");
			}
		});
		
		lbSign.setIcon(new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(String.format("./2과제 지급자료/싸인/%d.jpg", 1))));
		btnOk.addActionListener(e -> {
			
			File file = new File(lbSign.getText());
			System.out.println(lbSign.getText());
		});
	}
	
	public static void main(String[] args) {
		new ReplySignFrame().setVisible(true);
	}
	
}
