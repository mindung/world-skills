package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Wedding;


public class SearchFrame extends BaseFrame	{

	private String[] cities = new String[] { "노원구", "송파구", "강남구", "중구", "마포구", "서초구", "영등포구", "종로구" };
	
	private JPanel pnlCityCheckbox = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
	private JPanel pnlWeddingCheckbox = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
	private JPanel pnlMealCheckbox = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));

	private JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel pnlWeddingHall = new JPanel(new FlowLayout(FlowLayout.LEFT));

	private JTextField tfPeople = new JTextField(10);
	private JTextField tfPeople1 = new JTextField(10);
	
	private JTextField tfPrice = new JTextField(10);
	private JTextField tfPrice1 = new JTextField(10);
	
	private ArrayList<Wedding> weddings = new ArrayList<>();

	public SearchFrame() {
		super("검색", 810, 600);
		
		JScrollPane jsp = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		add(jsp, BorderLayout.CENTER);

		JPanel pnlCity = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlWedding = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlMeal = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		pnlCity.add(addC(new JLabel("지역"), 60, 50));
		pnlWedding.add(addC(new JLabel("예식형태"), 60, 50));
		pnlMeal.add(addC(new JLabel("식사종류"), 60, 30));

		pnlCity.add(addC(pnlCityCheckbox, 550, 50));
		pnlWedding.add(addC(pnlWeddingCheckbox, 550, 50));
		pnlMeal.add(addC(pnlMealCheckbox, 550, 30));

		JPanel pnlSearch = new JPanel(new BorderLayout());
		JPanel pnlTextField = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		pnlTextField.add(new JLabel("수용인원"));
		pnlTextField.add(tfPeople);
		pnlTextField.add(new JLabel("~"));
		pnlTextField.add(tfPeople1);

		pnlTextField.add(new JLabel("홀사용료"));
		pnlTextField.add(tfPrice);
		pnlTextField.add(new JLabel("~"));
		pnlTextField.add(tfPrice1);

		JButton btnSearch = new JButton("검색");
		JButton btnReset = new JButton("초기화");

		pnlButton.add(btnSearch);
		pnlButton.add(btnReset);

		pnlSearch.add(pnlTextField, BorderLayout.WEST);
		pnlSearch.add(pnlButton, BorderLayout.CENTER);

		pnlCenter.setPreferredSize(new Dimension(770, 50));

		pnlCenter.add(pnlCity);
		pnlCenter.add(pnlWedding);
		pnlCenter.add(pnlMeal);
		pnlCenter.add(pnlSearch);
		pnlCenter.add(pnlWeddingHall);
		
		pnlCity.setPreferredSize(new Dimension(770, 60));
		pnlWedding.setPreferredSize(new Dimension(770, 60));
		pnlMeal.setPreferredSize(new Dimension(770, 40));
		pnlSearch.setPreferredSize(new Dimension(770, 75));
		pnlTextField.setPreferredSize(new Dimension(300, 70));

		pnlSearch.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		pnlButton.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

		btnSearch.addActionListener(e -> getWedding());
		btnReset.addActionListener(e -> reset());

		initCheckBoxes();
		getWedding();
	}

	private void initCheckBoxes() {
		for (String city : cities) {
			JCheckBox checkBox = addC(new JCheckBox(city), 90, 20);
			pnlCityCheckbox.add(checkBox);
		}

		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM weddingtype");

			while(rs.next()) {
				JCheckBox checkBox = addC(new JCheckBox(rs.getString(2)), 90, 20);
				pnlWeddingCheckbox.add(checkBox);
			}

			rs = dbManager.executeQuery("SELECT * FROM mealtype");

			while(rs.next()) {
				JCheckBox checkBox = addC(new JCheckBox(rs.getString(2)), 90, 20);
				pnlMealCheckbox.add(checkBox);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new SearchFrame().setVisible(true);
	}
	
	private void reset() {
		
		for (Component com : pnlCityCheckbox.getComponents()) {
			JCheckBox cb = (JCheckBox) com;
			cb.setSelected(false);
		}
		
		for (Component com : pnlMealCheckbox.getComponents()) {
			JCheckBox cb = (JCheckBox) com;
			cb.setSelected(false);
		}
		
		for (Component com : pnlWeddingCheckbox.getComponents()) {
			JCheckBox cb = (JCheckBox) com;
			cb.setSelected(false);
		}
		
		tfPeople.setText("");
		tfPeople1.setText("");
		tfPrice.setText("");
		tfPrice1.setText("");
		
		getWedding();
		
	}

	private void getWedding() {
		
		String sql = "select wh.wh_no, wh_name, wh_add, wty_name, m_name, wh_people, wh_price from weddinghall as wh "
				+ "inner join division as d on d.wh_no = wh.wh_no "
				+ "inner join weddingtype as wt on wt.wty_no = d.wty_no "
				+ "inner join mealtype as mt on mt.m_no = d.m_no";

		ArrayList<String> sqlList = new ArrayList<>();
		ArrayList<String> checkboxList = new ArrayList<String>();

		for (Component com : pnlCityCheckbox.getComponents()) {
			JCheckBox cb = (JCheckBox) com;
			
			if (cb.isSelected()) {
				checkboxList.add("wh_add like '%" + cb.getText() + "%'");
			}
		}
		
		if (checkboxList.size() != 0) {
			sqlList.add("(" + String.join(" or ", checkboxList) + ")");
			checkboxList.clear();
		}
		
		for (Component com : pnlWeddingCheckbox.getComponents()) {
			JCheckBox cb = (JCheckBox) com;
			
			if (cb.isSelected()) {
				checkboxList.add("wty_name like '%" + cb.getText() + "%'");
			}
		}

		if (checkboxList.size() != 0) {
			sqlList.add("(" + String.join(" or ", checkboxList) + ")");
			checkboxList.clear();
		}
		
		for (Component com : pnlMealCheckbox.getComponents()) {
			JCheckBox cb = (JCheckBox) com;
			
			if (cb.isSelected()) {
				checkboxList.add("m_name like '%" + cb.getText() + "%'");
			}
		}

		if (checkboxList.size() != 0) {
			sqlList.add("(" + String.join(" or ", checkboxList) + ")");
			checkboxList.clear();
		}
		
		if ((!tfPeople.getText().isEmpty() && tfPeople.getText().matches("^[0-9]+$") == false) || 
				(!tfPeople1.getText().isEmpty() && tfPeople1.getText().matches("^[0-9]+$") == false)) {
			eMessage("수용인원과 홀사용료는 숫자만 입력 가능합니다.");
			return;
		} else if (!tfPeople.getText().isEmpty() && !tfPeople1.getText().isEmpty() &&
				Integer.valueOf(tfPeople.getText()) > Integer.valueOf(tfPeople1.getText())) {
			eMessage("숫자를 올바르게 입력해주세요.");
			return;
		} else {
			if (!tfPeople.getText().isEmpty()) {
				sqlList.add("wh_people >= " + tfPeople.getText());
			}
			if (!tfPeople1.getText().isEmpty()) {
				sqlList.add("wh_people <= " + tfPeople1.getText());
			}
		}
		
		if ((!tfPrice.getText().isEmpty() && tfPrice.getText().matches("^[0-9]+$") == false) || (!tfPrice1.getText().isEmpty() && tfPrice1.getText().matches("^[0-9]+$") == false)) {
			eMessage("수용인원과 홀사용료는 숫자만 입력 가능합니다.");
			return;
		} else if (!tfPrice.getText().isEmpty() && !tfPrice1.getText().isEmpty() && Integer.valueOf(tfPrice.getText()) > Integer.valueOf(tfPrice1.getText())) {
			eMessage("숫자를 올바르게 입력해주세요.");
			return;
		} else {
			if (!tfPrice.getText().isEmpty()) {
				sqlList.add("wh_price >= " + tfPrice.getText());
			}
			if (!tfPrice1.getText().isEmpty()) {
				sqlList.add("wh_price <= " + tfPrice1.getText());
			}
		}

		if (sqlList.size() != 0) {
			sql += " where " + String.join(" and ", sqlList);
		}
		
		try {
			ResultSet rs = dbManager.executeQuery(sql);
			
			int cnt = 0;
			
			pnlWeddingHall.removeAll();
			weddings.clear();

			while (rs.next()) {
				Wedding wedding = new Wedding(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
				
				addWedding(wedding, cnt);
				weddings.add(wedding);
				cnt++;
			}
			
			pnlWeddingHall.setPreferredSize(new Dimension(780, (int) (cnt  * 500)));
			pnlCenter.setPreferredSize(new Dimension(800, 500 + (cnt * 100)));

			pnlWeddingHall.updateUI();
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}

	private void addWedding(Wedding wedding, int cnt) {		

		JPanel panel = new JPanel(new BorderLayout());
		JPanel pnlBorder = new JPanel();
		JPanel pnlCenter = new JPanel(new GridLayout(4, 1));
		
		JButton btnSelected = new JButton("선택");
		JLabel lbImg = new JLabel(getImageIcon(String.format("./datafile/웨딩홀/%s/%s1.jpg", wedding.name, wedding.name), 180, 100));
		
		pnlCenter.add(new JLabel(wedding.name));
		pnlCenter.add(new JLabel(String.format("주소 : %s/", wedding.address), JLabel.LEFT));
		pnlCenter.add(new JLabel(String.format("예식형태 : %s / 식사종류 : %s/", wedding.w_ty, wedding.m_ty), JLabel.LEFT));
		pnlCenter.add(new JLabel(String.format("수용인원 : %d / 홀사용료 : %,d원", wedding.people, wedding.price), JLabel.LEFT));

		panel.add(lbImg, BorderLayout.WEST);
		panel.add(pnlCenter, BorderLayout.CENTER);
		panel.add(btnSelected, BorderLayout.EAST);
		
		pnlBorder.add(panel, BorderLayout.CENTER);

		pnlWeddingHall.add(pnlBorder);

		panel.setPreferredSize(new Dimension(750, 100));
		btnSelected.setPreferredSize(new Dimension(60, 100));
		pnlCenter.setPreferredSize(new Dimension(400, 100));

		pnlBorder.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		btnSelected.addActionListener(e -> {
			openFrame(new WeddinghallFrame(weddings, cnt), new SearchFrame());
		});
	}
}


