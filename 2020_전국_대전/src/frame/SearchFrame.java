package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Wedding;


public class SearchFrame extends BaseFrame	{

	private JComboBox<String> comCity = new JComboBox<String>(new String[] {"전체","구로구","노원구","송파구","강남구","중구","마포구","서초구","영등포구","종로구","용산구"});
	private JComboBox<String> comWedding = new JComboBox<String>(new String[] {"전체"});
	private JComboBox<String> comMeal = new JComboBox<String>(new String[] {"전체"});
	
	private JTextField tfPeople = new JTextField();
	private JTextField tfPeople1 = new JTextField();
	
	private JTextField tfPrice = new JTextField();
	private JTextField tfPrice1 = new JTextField();
	
	private JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	private JLabel lbCnt = new JLabel("건이 검색되었습니다. ");
	
	ArrayList<Wedding> list_c = new ArrayList<Wedding>();
	
	public SearchFrame() {
		super("검색", 1100, 500);
	
		setLayout(null);
		
		JButton btnReset = new JButton("초기화");
		JButton btnSearch = new JButton("검색");
		JButton btnExit = new JButton("닫기");
		
		JScrollPane jsp = new JScrollPane(pnl);
		
		addC(new JLabel("지역 : "), 40, 70, 50, 30);
		addC(new JLabel("예식형태 : "), 20, 110, 130, 30);
		addC(new JLabel("식사종류 : "), 20, 150, 130, 30);
		addC(new JLabel("수용인원 : "), 20, 190, 130, 30);
		addC(new JLabel("홀사용료 : "), 20, 230, 130, 30);
		
		addC(comCity, 90, 70, 130, 25);
		addC(comWedding, 90, 110, 130, 25);
		addC(comMeal, 90, 150, 130, 25);
		
		addC(tfPeople, 90, 190, 60, 25);
		addC(new JLabel("~"), 152, 190, 10, 25);
		addC(tfPeople1, 165, 190, 60, 25);
		
		addC(tfPrice, 90, 230, 60, 25);
		addC(new JLabel("~"), 152, 230, 10, 25);
		addC(tfPrice1, 165, 230, 60, 25);
		
		addC(lbCnt, 100, 270, 200, 30);
	
		addC(btnReset, 10, 330, 80, 30);
		addC(btnSearch, 95, 330, 80, 30);
		addC(btnExit, 180, 330, 80, 30);
		
		addC(jsp, 265, 10, 810, 440);
		
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from weddingType");
			while (rs.next()) {
				comWedding.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from mealType");
			while (rs.next()) {
				comMeal.addItem(rs.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		btnReset.addActionListener(e -> reset());
		btnSearch.addActionListener(e -> getWedding());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
		
		getWedding();
		
	}

	public static void main(String[] args) {
		new SearchFrame().setVisible(true);
	}
	
	private void addWedding(int wNo, String name,String address, String weddingType, String mealType, int people, int price, int cnt) {
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel pnlCenter = new JPanel(new GridLayout(6, 1));
		
		JLabel lbImg = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("datafile/호텔이미지/%s/%s1.jpg", name,name)).getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		
		pnlCenter.add(new JLabel(String.format("이름 :%s", name)));
		pnlCenter.add(new JLabel(String.format("주소 :%s", address)));
		pnlCenter.add(new JLabel(String.format("예식형태 :%s", weddingType)));
		pnlCenter.add(new JLabel(String.format("식사종류 :%s", mealType)));
		pnlCenter.add(new JLabel(String.format("수용인원 :%d", people)));
		pnlCenter.add(new JLabel(String.format("홀사용료 :%,d원", price)));
		
		panel.add(lbImg,BorderLayout.NORTH);
		panel.add(pnlCenter,BorderLayout.CENTER);
		
		lbImg.setPreferredSize(new Dimension(110, 120));
		panel.setPreferredSize(new Dimension(250, 300));
		pnl.add(panel);

		lbImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openFrame(new ReservationFrame(list_c, cnt));
				W_no = wNo;
				
			}
		});
	}
	
	private void getWedding() {
		
		String sql = "select h.wNo, wname, wadd, group_concat(distinct(wty.tyname)) as tyname, group_concat(distinct(mty.mname)) as mname, wpeople, wprice from wedding.weddinghall as h"
				+ " inner join w_m on w_m.wno = h.wNo"
				+ " inner join  mealtype as mty on w_m.mNo = mty.mNo"
				+ " inner join w_ty on w_ty.wno = h.wNo"
				+ " inner join  weddingtype as wty on w_ty.tyNo = wty.tyNo "
				+ "group by h.wNo";

		ArrayList<String> list = new ArrayList<String>();
		
		if(comCity.getSelectedIndex() != 0) {
			list.add("wadd like '%" + comCity.getSelectedItem() + "%'");
		}
		
		if(comWedding.getSelectedIndex() != 0) {
			list.add("tyname like '%" + comWedding.getSelectedItem() + "%'");
		}
		
		if(comMeal.getSelectedIndex() != 0 ) {
			list.add("mname like '%" + comMeal.getSelectedItem() + "%'");
		}
	
		if(!tfPeople.getText().isEmpty() && !tfPeople1.getText().isEmpty()) {
			
			if(tfPeople.getText().matches("^[0-9]+$") == false ||  tfPeople1.getText().matches("^[0-9]+$") == false ) {
				eMessage("수용인원과 홀사용료는 숫자만 입력 가능합니다");
				return;
			}else if(Integer.valueOf(tfPeople.getText()) > Integer.valueOf(tfPeople1.getText())) {
				eMessage("숫자를 올바르게 입력해주세요.");
				return;
			}else {
				list.add("wpeople >= " + tfPeople.getText() + " and wpeople <= " + tfPeople1.getText());
			}
			
		}
		
		if(!tfPrice.getText().isEmpty() && !tfPrice1.getText().isEmpty()) {
			
			if(tfPrice.getText().matches("^[0-9]+$") == false ||  tfPrice1.getText().matches("^[0-9]+$") == false ) {
				eMessage("수용인원과 홀사용료는 숫자만 입력 가능합니다");
				return;
				
			}else if(Integer.valueOf(tfPrice.getText()) > Integer.valueOf(tfPrice1.getText())) {
				eMessage("숫자를 올바르게 입력해주세요.");
				return;
			}else {
				list.add("wprice >= " + tfPrice.getText() + " and wprice <= " + tfPrice1.getText());
			}
		}

		if(list.size() != 0) {
			sql += " having " + String.join(" and ", list);
		}
		
		System.out.println(String.join(" and ", list));
		
		try {
			ResultSet rs = dbManager.executeQuery(sql);
		
			int cnt = 0;
			
			pnl.removeAll();
			list_c.clear();
			
			while (rs.next()) {
				addWedding(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), cnt);
				list_c.add(new Wedding(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
				cnt++;
			}

			lbCnt.setText(String.format("%d건이 검색되었습니다.", cnt));
			pnl.setPreferredSize(new Dimension(250, (int) (Math.ceil(cnt / 3f) * 300)));
			pnl.repaint();
			pnl.revalidate();
			
		} catch (SQLException e) { 
	
			e.printStackTrace();
		}
	}
	
	private void reset() {
		
		comCity.setSelectedIndex(0);
		comWedding.setSelectedIndex(0);
		comMeal.setSelectedIndex(0);
		tfPeople.setText("");
		tfPeople1.setText("");
		tfPrice.setText("");
		tfPrice1.setText("");
		
		getWedding();
		
	}
}


