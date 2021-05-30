package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.nio.file.attribute.DosFileAttributes;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BedFrame extends BaseFrame {
	
	
	public BedFrame(int s_no) {
		super("침대", 500, 300);
	
		JPanel pnlCenter = new JPanel();
		JLabel lbTitle = new JLabel("",JLabel.CENTER);
		
		add(lbTitle,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		
		lbTitle.setFont(new Font("굴림",Font.BOLD,25));
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from sickroom where s_no = ?", s_no);
			
			rs.next();
			
			lbTitle.setText(String.format("%d호", rs.getInt(3)));
			
			String[] roomNo = rs.getString(4).split(",");
			
			for (int i = 0; i < roomNo.length; i++) {

				String no = roomNo[i]; // 해당 번호

				JPanel pnl = new JPanel(new BorderLayout());
				JButton btn = new JButton();
				JLabel lb = new JLabel(no,JLabel.CENTER);
				
				pnl.add(lb,BorderLayout.NORTH);
				pnl.add(btn,BorderLayout.CENTER);
				
				rs = dbManager.executeQuery("select * from hospitalization where s_no = ? and h_bedno = ? and h_fday = ''", s_no,no);
				
				if(rs.next()) {
					btn.setEnabled(false);			
				}
				
				btn.setName(String.valueOf(no));
				btn.addActionListener(e -> {
					JButton jbtn = (JButton) e.getSource();
					
					selectedBed = btn.getName();
					dispose();
					
				});
				
				pnlCenter.add(pnl);
				pnlCenter.setLayout(new GridLayout(1, roomNo.length));
				
			}
			
			setSize(roomNo.length* 100, 300);
			setLocationRelativeTo(null);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void main(String[] args) {
		new BedFrame(1).setVisible(true);
	}

}
