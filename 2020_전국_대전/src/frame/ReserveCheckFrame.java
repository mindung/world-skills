package frame;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.MealType;
import model.Wedding;
import model.WeddingType;

public class ReserveCheckFrame extends BaseFrame {

	private JComboBox<WeddingType>comType = new JComboBox<>();
	private JComboBox<MealType> comMeal = new JComboBox<>();
	
	private JCheckBox cbAlbum = new JCheckBox("앨범제작");
	private JCheckBox cbLetter = new JCheckBox("청첩장");
	private JCheckBox cbDress = new JCheckBox("드레스");
	
	private JTextField tfName = new JTextField();
	private JTextField tfAddr = new JTextField();
	private JTextField tfPeople = new JTextField();
	private JTextField tfHallPrice = new JTextField();
	private JTextField tfPrice = new JTextField();
	
	private JTextField tfPeopleStr = new JTextField(10);
	
	private String input;
	private int wNo;
	private String Wname;
	private String WAddr;
	private String WDate;
	
	private String WType;
	private String Meal;
	private String WPeople;
	
	private boolean Album;
	private boolean Dress;
	private boolean Letter;
	
	private JButton btn1 = new JButton("청첩장 선택");
	private JButton btn2 = new JButton("예약 변경");
	private JButton btn3 = new JButton("예약 취소");
	private JButton btn4 = new JButton("결제하기");
	private JButton btn5 = new JButton("닫기");

	public ReserveCheckFrame(String input) {
		super("예약 확인", 600, 475);
		
		this.input = input;
		
		setLayout(null);
	
		JPanel pnlCenter = new JPanel(new GridLayout(7, 2,-200,10));
		JPanel pnlEast = new JPanel(new GridLayout(5, 1));
		JPanel pnlSouth = new JPanel();
		
		
		pnlCenter.add(new JLabel("웨딩홀명"));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("주소"));
		pnlCenter.add(tfAddr);
		pnlCenter.add(new JLabel("수용인원"));
		pnlCenter.add(tfPeople);
		pnlCenter.add(new JLabel("홀사용료"));
		pnlCenter.add(tfHallPrice);
		pnlCenter.add(new JLabel("예식형태"));
		pnlCenter.add(comType);
		pnlCenter.add(new JLabel("식사종류"));
		pnlCenter.add(comMeal);
		pnlCenter.add(new JLabel("식사비용"));
		pnlCenter.add(tfPrice);
	
		pnlEast.add(btn1);
		pnlEast.add(btn2);
		pnlEast.add(btn3);
		pnlEast.add(btn4);
		pnlEast.add(btn5);
		
		pnlSouth.add(new JLabel("인원수"));
		pnlSouth.add(tfPeopleStr);
		pnlSouth.add(cbAlbum);
		pnlSouth.add(cbLetter);
		pnlSouth.add(cbDress);
		
		addC(pnlCenter, 5, 10, 450, 350);
		addC(pnlSouth, 2, 370, 450, 30);
		addC(pnlEast, 460, 10, 100, 400);
		
		tfName.setEnabled(false);
		tfAddr.setEnabled(false);
		tfPeople.setEnabled(false);
		tfHallPrice.setEnabled(false);
		tfPrice.setEnabled(false);

		btn1.addActionListener(e -> new LetterFrame(input,Wname,WAddr,WDate).setVisible(true));
		btn2.addActionListener(e -> reserveUpdate());
		btn3.addActionListener(e -> cancel());
		btn4.addActionListener(e -> order());
		btn5.addActionListener(e -> openFrame(new MainFrame()));
		
		getinfo();
		getComInfo();
		
	}
	
	public static void main(String[] args) {
		new ReserveCheckFrame("10890").setVisible(true);
		
	}
	
	private void reserveUpdate() {
	
		String people = tfPeopleStr.getText();
		WeddingType wedding = (WeddingType) comType.getSelectedItem();
		MealType meal = (MealType) comMeal.getSelectedItem();
		
		boolean letter = cbLetter.isSelected();
		boolean album = cbAlbum.isSelected();
		boolean dress = cbDress.isSelected();
		
		if(people.isEmpty() || people.matches("^[0-9]+$")==false || Integer.valueOf(tfPeople.getText()) < Integer.valueOf(people)) {
			eMessage("인원수를 바르게 입력해주세요.");
		}else if(people.equals(WPeople) && wedding.getName().equals(WType) && meal.getName().equals(Meal) && letter == Letter && Album == album && dress == Dress) {
			eMessage("변경 내용이 없습니다.");
		}else {
			
			try {
				System.out.println(wedding.getNo());
				System.out.println(meal.getNo());
				dbManager.executeUpdate("update reservation set rpeople = ?, tyno = ?, mno = ?, album = ?, dress = ?, letter = ? where rno = ?", people, wedding.getNo(), meal.getNo(), album, dress, letter, input);
				iMessage("변경되었습니다.");//, album = ?,letter =?, dress = ?, letter = ?,letter
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void cancel() {
		try {
			iMessage("취소되었습니다.");
			dbManager.executeUpdate("delete from reservation where rno = ?", input);
			openFrame(new MainFrame());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void order() {
		
		String people = tfPeopleStr.getText();
		String hallPrice = tfHallPrice.getText();
		String mealPrice = tfPrice.getText();
		
		int album = cbAlbum.isSelected() ? 1: 0;
		int letter= cbLetter.isSelected() ? 1: 0;
		int dress = cbDress.isSelected() ? 1: 0;
	
		openFrame(new OrderFrame(input,Wname,hallPrice,mealPrice,people,album,letter,dress));
		
	}
	
	private void getinfo() {
		
		try {
			
			ResultSet rs = dbManager.executeQuery("select reservation.rno, wname, wadd, wpeople, wprice, group_concat(distinct(mName)), group_concat(distinct(tyName)), mprice, rpeople, album, letter, dress, pay, reservation.wNo, rDate From reservation  \r\n"
					+ "inner join weddinghall on weddinghall.wNo = reservation.wNo \r\n"
					+ "inner join w_ty on w_ty.tyNo = reservation.tyNo \r\n"
					+ "inner join weddingtype on weddingtype.tyNo = w_ty.tyNo\r\n"
					+ "inner join w_m on w_m.mNo = reservation.mNo\r\n"
					+ "inner join mealtype on mealtype.mNo = w_m.mNo\r\n"
					+ "where rno = ?", input);
			
			if(rs.next()) {
				
				Wname = rs.getString(2);
				WAddr = rs.getString(3);
				wNo = rs.getInt(14);
				WDate = rs.getString(15);
				
				WType = rs.getString(7);
				Meal = rs.getString(6);
				WPeople = rs.getString(9);
				
				tfName.setText(rs.getString(2));
				tfAddr.setText(rs.getString(3));
				tfPeople.setText(rs.getString(4));
				tfHallPrice.setText(rs.getString(5));
				
				System.out.println(WType);
				System.out.println(Meal);
				
				
				tfPrice.setText(rs.getString(8));
				tfPeopleStr.setText(WPeople);
				
				cbAlbum.setSelected(rs.getInt(10) != 0 );
				cbLetter.setSelected(rs.getInt(11)!= 0 );
				cbDress.setSelected(rs.getInt(12) != 0 );
				
				if(rs.getInt(13) == 0) {
					setComponentEnable(true);
				}else {
					setComponentEnable(false);
				}
				
				if(rs.getInt(11) != 0) {
					btn1.setText("청첩장 수정");
				}
				
			}else {
				return;
			}
			
			Album = cbAlbum.isSelected();
			Letter = cbLetter.isSelected();
			Dress = cbDress.isSelected();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getComInfo() {
		
		comType.removeAllItems();
		comMeal.removeAllItems();
		
		try {
			
			ResultSet rs = dbManager.executeQuery("select * from weddingtype inner join w_ty on weddingtype.tyNo = w_ty.tyNo where w_ty.wNo = ?", wNo);
			while (rs.next()) {
				comType.addItem(new WeddingType(rs.getInt(1), rs.getString(2)));
			}
			
			rs = dbManager.executeQuery("select * from mealtype inner join w_m on mealtype.mNo = w_m.mNo where w_m.wNo = ?", wNo);
			
			while (rs.next()) {
				comMeal.addItem(new MealType(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}
			
			comType.setSelectedItem(new WeddingType(0, WType));
			comMeal.setSelectedItem(new MealType(0, Meal, 0));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setComponentEnable(boolean b) {
		comType.setEnabled(b);
		comMeal.setEnabled(b);
		tfPeopleStr.setEnabled(b);
		cbAlbum.setEnabled(b);
		cbLetter.setEnabled(b);
		cbDress.setEnabled(b);
		
		btn2.setEnabled(b);
		btn3.setEnabled(b);
		btn4.setEnabled(b);
		
	}
}



