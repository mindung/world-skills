package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UpdateMenuFrame extends BaseFrame {

	private JTextField tfName = new JTextField();

	private JComboBox<String> comGroup = new JComboBox<String>(new String[] { "한식", "중식", "일식", "양식" });
	private JComboBox<Integer> comPrice = new JComboBox<Integer>();
	private JComboBox<Integer> comCount = new JComboBox<Integer>();

	private String group;
	private int mealNo;
	
	public UpdateMenuFrame(String group,int no) {
		super("메뉴수정", 400, 280);
		
		this.mealNo = no;
		this.group = group;
		
		
		JPanel pnlCenter = new JPanel(new GridLayout(4, 2));
		JPanel pnlSouth = new JPanel();

		JButton btnUpdate = new JButton("수정");
		JButton btnExit = new JButton("닫기");

		pnlCenter.add(new JLabel("종류"));
		pnlCenter.add(comGroup);
		pnlCenter.add(new JLabel("메뉴명"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("가격"));
		pnlCenter.add(comPrice);
		pnlCenter.add(new JLabel("조리가능수량"));
		pnlCenter.add(comCount);

		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnExit);

		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnExit.addActionListener(e -> openFrame(new MenuCareFrame()));
		btnUpdate.addActionListener(e -> insert());
		
		
		for (int i = 0; i <= 50; i++) {
			comCount.addItem(i);
		}

		for (int i = 1000; i <= 12000; i += 500) {
			comPrice.addItem(i);
		//	System.err.println(i);
		}

		
		getinfo();

	}

	public static void main(String[] args) {
		new UpdateMenuFrame("",0).setVisible(true);
	}

	private void insert() {
		
		String name = tfName.getText();
		int group = comGroup.getSelectedIndex() + 1;
		int price = (int) comPrice.getSelectedItem();
		int count = (int) comCount.getSelectedItem();

		if (name.isEmpty()) {
			eMessage("메뉴명을 입력해주세요.");

		} else {
			try {
				dbManager.executeUpdate("Update meal set cuisineNo = ? , mealName = ?, price = ?, maxCount = ?  where mealNo = ?", group, name, price, count,mealNo);
				iMessage("메뉴가 수정되었습니다.");
				openFrame(new MenuCareFrame());

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void getinfo() {
		try {
			ResultSet rs = dbManager.executeQuery("select * from meal where mealno = ?", mealNo);
			
			if(rs.next()) {
				comGroup.setSelectedItem(group);
				tfName.setText(rs.getString(3));
				comPrice.setSelectedItem(rs.getInt(4));
				comCount.setSelectedItem(rs.getInt(5));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
}
