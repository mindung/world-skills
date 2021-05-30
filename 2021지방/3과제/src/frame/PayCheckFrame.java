package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PayCheckFrame extends BaseFrame {

	private JTextField tfName = new JTextField();
	private JTextField tfDate = new JTextField();
	private JTextField tfAddr = new JTextField();
	private JTextField tfWedding = new JTextField();
	private JTextField tfMeal = new JTextField();
	private JTextField tfMealPrice = new JTextField();
	private JTextField tfPeople = new JTextField();

	private JTextField tfHallPrice = new JTextField();
	private JTextField tfMealAmount = new JTextField();
	private JTextField tfLetterAmount = new JTextField();
	private JTextField tfAllAmount = new JTextField();
	
	private JCheckBox cb1 = new JCheckBox("1");
	private JCheckBox cb2 = new JCheckBox("2");
	private JCheckBox cb3 = new JCheckBox("3");
	private JPanel pnlCheck = new JPanel(new GridLayout(1, 3));
	
	private int p_no;
	
	public PayCheckFrame(int p_no) {
		super("결제확인", 400, 550);
	
		this.p_no = p_no;
		
		JPanel pnlCenter =new JPanel(new GridLayout(12, 2, -200, 10));
		
		JLabel lbTitle = new JLabel("결제확인" ,JLabel.CENTER);
		
		pnlCenter.add(new JLabel("웨딩홀명", JLabel.LEFT));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("날짜", JLabel.LEFT));
		pnlCenter.add(tfDate);
		pnlCenter.add(new JLabel("주소", JLabel.LEFT));
		pnlCenter.add(tfAddr);
		pnlCenter.add(new JLabel("예식형태", JLabel.LEFT));
		pnlCenter.add(tfWedding);
		pnlCenter.add(new JLabel("식사종류", JLabel.LEFT));
		pnlCenter.add(tfMeal);
		pnlCenter.add(new JLabel("식사비용", JLabel.LEFT));
		pnlCenter.add(tfMealPrice);
		pnlCenter.add(new JLabel("인원수", JLabel.LEFT));
		pnlCenter.add(tfPeople);
		pnlCenter.add(new JLabel("청첩장", JLabel.LEFT));
		pnlCenter.add(pnlCheck);
		pnlCenter.add(new JLabel("홀사용료", JLabel.LEFT));
		pnlCenter.add(tfHallPrice);
		pnlCenter.add(new JLabel("총식사비용", JLabel.LEFT));
		pnlCenter.add(tfMealAmount);
		pnlCenter.add(new JLabel("청첩장금액", JLabel.LEFT));
		pnlCenter.add(tfLetterAmount);
		pnlCenter.add(new JLabel("총금액", JLabel.LEFT));
		pnlCenter.add(tfAllAmount);
		
		pnlCheck.add(cb1);
		pnlCheck.add(cb2);
		pnlCheck.add(cb3);
		
		add(lbTitle, BorderLayout.NORTH);
		add(pnlCenter , BorderLayout.CENTER);
		
		lbTitle.setFont(new Font("굴림", Font.BOLD, 30));
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
//		for (Component com : pnlCenter.getComponents()) {
//			JTextField tf = (JTextField) com;
//
//			if (tf.getn)
//		}

		for (Component com : pnlCheck.getComponents()) {
			JCheckBox cb = (JCheckBox) com;
			cb.setEnabled(false);
		}
		
		tfAddr.setEnabled(false);
		tfAllAmount.setEnabled(false);
		tfDate.setEnabled(false);
		tfHallPrice.setEnabled(false);
		tfLetterAmount.setEnabled(false);
		tfMeal.setEnabled(false);
		tfMealAmount.setEnabled(false);
		tfMealPrice.setEnabled(false);
		tfName.setEnabled(false);
		tfPeople.setEnabled(false);
		tfWedding.setEnabled(false);
		
		
//		for (int i = 9; i <= 11; i++) {
//			
//			JPanel pnl = (JPanel) pnlCenter.getComponent(i);
//			
//			pnl.getComponent(i).setBackground(Color.gray);
//			pnl.setOpaque(true);
//		}
		
		getInfo();
	}
	
	public static void main(String[] args) {
		new PayCheckFrame(0502).setVisible(true);
	}
	
	private void getInfo() {
		String sql = "SELECT wh_name, p_date, wh_add, wty_name, mt.m_name, mt.m_price, p.p_people, p.i_no, wh_price FROM payment as p "
				+ "inner join weddinghall as wh on wh.wh_no = p.wh_no "
				+ "inner join weddingtype as wt on wt.wty_no = p.wty_no "
				+ "inner join mealtype as mt on mt.m_No = p.m_no "
				+ "where p_no = ?";
		
		int hallPrice = 0;
		int MealPrice = 0;
		int LetterPrice = 0;
		int letter = 0;
		
		try {
			ResultSet rs = dbManager.executeQuery(sql, p_no);
			
			if (rs.next()) {
				
				hallPrice = rs.getInt(9);
				MealPrice = rs.getInt(6);
				LetterPrice = 0 ;
				letter = rs.getInt(8);
				
				for (Component com : pnlCheck.getComponents()) {
					JCheckBox cb = (JCheckBox) com;
					
					if (letter == Integer.valueOf(cb.getText())) {
						cb.setSelected(true);
						LetterPrice = Integer.valueOf(cb.getText()) == 0 ? 0 : 150000;
					}
				}
				
				tfName.setText(rs.getString(1));
				tfDate.setText(rs.getString(2));
				tfAddr.setText(rs.getString(3));
				tfWedding.setText(rs.getString(4));
				tfMeal.setText(rs.getString(5));
				tfMealPrice.setText(String.format("%,d", MealPrice));
				tfPeople.setText(rs.getString(7));
				tfHallPrice.setText(String.format("%,d", hallPrice));
				tfMealAmount.setText(String.format("%,d", rs.getInt(6) * rs.getInt(7)));
				tfLetterAmount.setText(String.format("%,d", LetterPrice));
				tfAllAmount.setText(String.format("%,d", hallPrice + MealPrice  * rs.getInt(7) + LetterPrice));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
