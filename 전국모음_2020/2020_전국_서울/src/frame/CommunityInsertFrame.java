package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CommunityInsertFrame extends BaseFrame {
	
	private JComboBox<String> comGrade = new JComboBox<String>(new String[] {"잡담", "통신문"});
	private JComboBox<String> comTitle = new JComboBox<String>();
	
	private JTextField tfTitle = new JTextField(15);
	private JTextArea tfContents = new JTextArea();
	
	private JRadioButton rbOpen = new JRadioButton("공개");
	private JRadioButton rbNoOpen = new JRadioButton("비공개");
	
	JPanel pnlN = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	public CommunityInsertFrame() {
		super("글쓰기", 300, 400);
		
		JPanel pnlC = new JPanel(null);
		JPanel pnlS = new JPanel(new BorderLayout());
		JPanel pnlSN = new JPanel();
		JPanel pnlSS = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel lbC = new JLabel("내용");
		
		JButton btnInsert = createButton("추가");
		JButton btnExit = createButton("닫기");
		
		pnlN.add(new JLabel("분류"));
		pnlN.add(comGrade);
		pnlN.add(tfTitle);
		pnlN.add(comTitle);
		
		pnlSN.add(rbOpen);
		pnlSN.add(rbNoOpen);
		
		pnlSS.add(btnInsert);
		pnlSS.add(btnExit);
		
		pnlS.add(pnlSN,BorderLayout.NORTH);
		pnlS.add(pnlSS, BorderLayout.SOUTH);
		
		pnlC.add(lbC);
		pnlC.add(tfContents);

		add(pnlN, BorderLayout.NORTH);
		add(pnlC, BorderLayout.CENTER);
		add(pnlS, BorderLayout.SOUTH);

		ButtonGroup g = new ButtonGroup();
		
		g.add(rbOpen);
		g.add(rbNoOpen);
		
		lbC.setBounds(5, 100, 30, 30);
		tfContents.setBounds(35, 10, 240, 235);

		tfContents.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		comGrade.addItemListener(e -> getComInfo());
		btnInsert.addActionListener(e -> Insertcommunity());
		btnExit.addActionListener(e -> dispose());
		
		getComInfo();
	} 
	
	public static void main(String[] args) {
		new CommunityInsertFrame().setVisible(true);
	}

	private void setFrameSize(int w, int h) {
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
	}

	private void Insertcommunity() {
		
		boolean open = rbOpen.isSelected();
		boolean noOpen = rbNoOpen.isSelected();
		String contents = tfContents.getText();

		int anonymous = open == true ? 1 : 2;
		int classification  = comGrade.getSelectedIndex() == 0 ? 2 : 1;
		String title = comGrade.getSelectedIndex() == 0 ? tfTitle.getText() : (String) comTitle.getSelectedItem();
		
		if (comGrade.getSelectedIndex() == 0 && tfTitle.getText().isEmpty()) {
			eMessage("제목을 입력해주세요");
			return;
		}
		
		if( contents.isEmpty()) {
			eMessage("내용을 입력해주세요.");
			return;
		}
		
		if (open == false && noOpen == false) {
			eMessage("공개여부를 선택해주세요.");
			return;
		}
		
		try {
			dbManager.executeUpdate("insert into community value(0, ?, ? , ?, ?, ?)", title, contents, m_no, anonymous, classification);
			iMessage("등록이 완료되었습니다.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getComInfo() {
		int index = comGrade.getSelectedIndex();

		tfTitle.setText("");

		setFrameSize(index == 0 ? 300 : 400, 400);
		tfTitle.setVisible(index == 0 ? true : false);
		comTitle.setVisible(index == 0 ? false : true);

		try {
			ResultSet rs = dbManager.executeQuery("select * from news");

			while (rs.next()) {
				comTitle.addItem(rs.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
