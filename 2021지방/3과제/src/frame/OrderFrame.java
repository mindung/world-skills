package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OrderFrame extends BaseFrame {

	private String input;
	private String wName;
	private String hallPrice;
	private String mealPrice;
	private String people;
	
	public JTextField tfNum = new JTextField(28);
	
	public OrderFrame(String input, String wname, String hallPrice, String mealPrice, String people, int album, int letter, int dress) {
		super("결제하기", 350, 600);
	
		this.input = input;
		this.wName = wname;
		this.hallPrice = hallPrice;
		this.mealPrice = mealPrice;
		this.people = people;
		this.wName = wname;this.wName = wname;
	
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlCCenter = new JPanel(new GridLayout(8, 1,0,0));
		JPanel pnlCSouth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JLabel lbTitle = new JLabel("결제 서비스",JLabel.CENTER);
		
		JButton btnOrder = new JButton("결제");
		JButton btnExit = new JButton("취소");
		
		lbTitle.setFont(new Font("굴림",Font.BOLD,30));
		lbTitle.setBackground(Color.red);
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setOpaque(true);

		lbTitle.setPreferredSize(new Dimension(350, 55));
		pnlCCenter.setPreferredSize(new Dimension(320, 30));
		pnlCSouth.setPreferredSize(new Dimension(320, 50));
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(9,9, 9, 9));
		
		lbTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
//		pnlCCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		int Amount = (Integer.valueOf(mealPrice) * Integer.valueOf(people)) + (100000 * album) + (150000 * letter) + (200000 * dress) ;
		
		
		pnlCCenter.add(new JLabel(String.format("웨딩홀명  	 %s", wname)));
		pnlCCenter.add(new JLabel(String.format("홀사용료   	%s", hallPrice)));
		pnlCCenter.add(new JLabel(String.format("식사비용   	%s", mealPrice)));
		pnlCCenter.add(new JLabel(String.format("인원수   	%s", people)));
		pnlCCenter.add(new JLabel(String.format("앨범제작   	%s", album == 0 ? "신청안함" : "신청")));
		pnlCCenter.add(new JLabel(String.format("청첩장   	%s", letter == 0 ? "신청안함" : "신청")));
		pnlCCenter.add(new JLabel(String.format("드레스   	%s", dress == 0 ? "신청안함" : "신청")));
		pnlCCenter.add(new JLabel(String.format("총금액   	%d원", Amount)));
		
		for (int i = 0; i < pnlCCenter.getComponentCount(); i++) {
			JLabel lb = (JLabel) pnlCCenter.getComponent(i);
			
			lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			if (i % 2 == 0) {
				lb.setOpaque(true);
				lb.setBackground(Color.WHITE);
			}
		}
		
		pnlCSouth.add(new JLabel("예약번호를 입력해주세요."));
		pnlCSouth.add(tfNum);
		
		pnlCenter.add(pnlCCenter,BorderLayout.CENTER);
		pnlCenter.add(pnlCSouth,BorderLayout.SOUTH);
		
		pnlSouth.add(btnOrder);
		pnlSouth.add(btnExit);
		
		add(lbTitle,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		btnOrder.addActionListener(e -> order());
//		btnExit.addActionListener(e -> openFrame(new ReserveCheckFrame(input)));
		
		tfNum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showPayFrame();
			};
		});
		
	}
	
	public static void main(String[] args) {
		new OrderFrame("","","","","",0,0,0).setVisible(true);
	}
	

	private void order() {
		String num = tfNum.getText();
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from reservation where rno = ?",num);
			
			if(rs.next()) {
				iMessage("결제가 완료되었습니다.");
				dbManager.executeUpdate("update reservation set pay = 1 where rno =?", num);
				openFrame(new MainFrame());
			}else {
				eMessage("예약정보가 일치하지 않습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void showPayFrame() {
		tfNum.setText("");
//		new PayFrame(this).setVisible(true);
	}
}

