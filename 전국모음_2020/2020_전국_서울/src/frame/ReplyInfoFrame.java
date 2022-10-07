package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class ReplyInfoFrame extends BaseFrame{
	
	private JTextArea tfContents = new JTextArea();
	
	private int index;
	
	public ReplyInfoFrame(int index, String submit) {
		super("회신문서", 320, submit.equals("제출") ? 500 : 550);
		
		this.index = index;
		
		JPanel pnlN = new JPanel();
		pnl1 pnl1 = new pnl1();
		pnl2 pnl2 = new pnl2();
		JLabel lbTitle = createLabel("");
		
		JScrollPane jsp = new JScrollPane(tfContents);
		
		
		jsp.setPreferredSize(new Dimension(320, 200));
		try {
			ResultSet rs = dbManager.executeQuery("select * from reply where r_no = ?", index);
			String r_contents = "";
			String contents = "";
					
			if (rs.next()) {
				lbTitle.setText(rs.getString(2));
				r_contents = rs.getString(3);
			}
			
			for (int i = 0; i < r_contents.length(); i++) {
				if ( i != 0 && i % 25 == 0) {
					contents += "\r\n";
				}
				
				contents += r_contents.substring(i, i+1);
			}
			
			tfContents.setText(contents);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		add(jsp, BorderLayout.CENTER);
		add(lbTitle, BorderLayout.NORTH);
		
		if (submit.equals("제출")) {
			add(pnl1, BorderLayout.SOUTH);
		} else {
			add(pnl2, BorderLayout.SOUTH);
		}

	}

	public static void main(String[] args) {
		new ReplyInfoFrame(1, "미제출").setVisible(true);
	}

	public class pnl1 extends JPanel{

		JLabel lbImg = createLbImg(Color.white);
		
		public pnl1() {
			
			setLayout(new BorderLayout());
			
			JPanel pnlC = new  JPanel();
			JPanel pnlS = new JPanel();
			
			pnlC.add(lbImg);
			JButton btnExit = createButton("확인");
			
			pnlS.add(btnExit);
			add(new JLabel("이미 제출하였습니다.", JLabel.CENTER), BorderLayout.NORTH);
			add(pnlC, BorderLayout.CENTER);
			add(pnlS,BorderLayout.SOUTH);
			
			getImgInfo();
			
			btnExit.addActionListener(e -> dispose());
		}
		
		private void getImgInfo() {
			
			try {
				ResultSet rs = dbManager.executeQuery("select * from reply_result where m_no = ? and r_no = ?", m_no, index);
				if(rs.next()) {
					System.out.println(rs.getInt(1));
					lbImg.setIcon(new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(String.format("./2과제 지급자료/싸인/%d.jpg", rs.getInt(1))).getScaledInstance(170, 80, Image.SCALE_SMOOTH)));			 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public class pnl2 extends JPanel{
		
	
	private JRadioButton rb2 = new JRadioButton("동의하지 않습니다.");
	public JRadioButton rb1 = new JRadioButton("동의합니다.");
	private JLabel lbImg = createLbImg(Color.WHITE);
	
		public pnl2() {

			setLayout(new BorderLayout());
			
			JPanel pnlN = new JPanel(new BorderLayout());
			JPanel pnlBtn = new JPanel();
			JPanel pnlC = new  JPanel();
			JPanel pnlS = new JPanel();
			
			JLabel lbInfo = new JLabel("", JLabel.CENTER);
			
			JButton btnInsert = createButton("제출");
			JButton btnExit = createButton("닫기");
			
			pnlBtn.add(rb1);
			pnlBtn.add(rb2);
			
			pnlS.add(btnInsert);
			pnlS.add(btnExit);
			
			pnlN.add(pnlBtn, BorderLayout.SOUTH);
			pnlN.add(lbInfo, BorderLayout.CENTER);
			pnlN.add(new JLabel("|===========================================|"), BorderLayout.NORTH);
			pnlC.add(lbImg);

			ButtonGroup bg = new ButtonGroup();
			
			bg.add(rb1);
			bg.add(rb2);
			
			lbInfo.setPreferredSize(new Dimension(350, 20));


			try {
				ResultSet rs = dbManager.executeQuery("select * from member where m_no = ?", m_no);
				if(rs.next()) {
					lbInfo.setText(String.format("번호: %d / 이름 : %s", rs.getInt(1), rs.getString(4)));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			add(pnlN,BorderLayout.NORTH);
			add(pnlC, BorderLayout.CENTER);
			add(pnlS,BorderLayout.SOUTH);
			
			btnInsert.addActionListener(e -> insert());
			btnExit.addActionListener(e -> dispose());
			
			rb1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					new ReplyResultFrame(index).setVisible(true);
				}
			});
			
			lbImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ReplySignFrame frame = new ReplySignFrame();
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							
						}
					});
					
					frame.setVisible(true);
				}
			});
			
		}
		
		private void insert() {
			int whether = rb1.isSelected() ? 1 : (rb2.isSelected() ? 2 : 0);
			
			if (whether == 0) {
				eMessage("동의 여부를 선택해주세요.");
				return;
			}
	
//			if (lbImg.getIcon().equals("")) {
//				eMessage("");
//			}
			
			try {
				
				dbManager.executeQuery("insert into reply values(0, ?, ?, ?)", m_no, index, whether);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
