package frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.MealType;
import model.Wedding;
import model.WeddingType;

public class ReservationFrame extends BaseFrame {

	private JComboBox<WeddingType> comType = new JComboBox<WeddingType>();
	private JComboBox<MealType> comMeal = new JComboBox<MealType>();

	private JTextField tfName = new JTextField();
	private JTextField tfAddr = new JTextField();
	private JTextField tfPeople = new JTextField();
	private JTextField tfHallPrice = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfPeopleStr = new JTextField();
	private JTextField tfDate = new JTextField();
	private JTextField tfLetter = new JTextField(3);

	private JLabel lbImg = new JLabel();
	private JPanel pnlImg= new JPanel();

	private ArrayList<Wedding> list;
	private int index;
	
	private JPanel pnl = new JPanel();
	
	public ReservationFrame(ArrayList<Wedding> list, int index) {
		super("예약", 590, 788);
		setLayout(null);
	
		this.list = list;
		this.index = index;
		
		selecteDate = "";
		selectedLetter = 0;
				
		JPanel pnlCenter = new JPanel(new GridLayout(9, 2, -300, 10));

		JButton btnPrev = new JButton("◀");
		JButton btnNext = new JButton("▶");
		JButton btnLetter = new JButton("청첩장 선택");
		JButton btnReserve = new JButton("예약하기");

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
		pnlCenter.add(new JLabel("인원수"));
		pnlCenter.add(tfPeopleStr);
		pnlCenter.add(new JLabel("날짜"));
		pnlCenter.add(tfDate);
		
		addC(lbImg, 60, 10, 460, 170);
		addC(pnlImg, 60, 180, 460, 60);
		
		addC(pnlCenter, 60, 250, 460, 400);
		addC(btnLetter, 65, 670, 100, 30);
		addC(tfLetter, 170, 670, 30, 25);
		addC(btnPrev, 0, 300, 55, 100);
		addC(btnNext, 520, 300, 55, 100);
		addC(btnReserve, 0, 720, 590, 29);

		lbImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		tfName.setEnabled(false);
		tfAddr.setEnabled(false);
		tfPeople.setEnabled(false);
		tfHallPrice.setEnabled(false);
		tfPrice.setEnabled(false);
		
		tfDate.addMouseListener(new MouseAdapter() {
			CalendarFrame frame = new CalendarFrame(list.get(index).getRno());

			public void mouseClicked(MouseEvent e) {
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						tfDate.setText(selecteDate);
					}
				});
				
				frame.setVisible(true);
			}
		});

		btnLetter.addActionListener(e -> {
			if (tfDate.getText().isEmpty()) {
				eMessage("날짜를 선택해주세요");
			} else {
				LetterFrame frame = new LetterFrame("0", tfName.getText(), tfAddr.getText(), selecteDate);
				
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						tfLetter.setText(String.valueOf(selectedLetter));
					}
				});
				
				frame.setVisible(true);
			}
		});

		btnPrev.addActionListener(e -> prevWeddingHall());
		btnNext.addActionListener(e -> nextWeddingHall());
		btnReserve.addActionListener(e -> reserve());
		
		comMeal.addItemListener(e -> getMealPrcie());
		
		getWeddingHallInfo();		
		getMealPrcie();
	}

	private void prevWeddingHall() {
		if (index == 0) {
			index = list.size() - 1;
		} else {
			index--;
		}
		
		getWeddingHallInfo();
	}
	
	private void nextWeddingHall() {
		if (index == list.size() - 1) {
			index = 0;
		} else { 
			index++;
		}
		
		getWeddingHallInfo();
	}
	
	private void getWeddingHallInfo() {
		Wedding wedding = list.get(index);
		tfName.setText(wedding.getName());
		tfAddr.setText(wedding.getAddress());
		tfPeople.setText(String.valueOf(wedding.getPeople()));
		tfHallPrice.setText(String.valueOf(wedding.getPrice()));
	
		try {
			ResultSet rs = dbManager.executeQuery("select * from weddingtype inner join w_ty on weddingtype.tyNo = w_ty.tyNo where w_ty.wNo = ?", wedding.getRno());
			
			comType.removeAllItems();
			
			while(rs.next()) {
				comType.addItem(new WeddingType(rs.getInt(1), rs.getString(2)));
			}
			
			rs = dbManager.executeQuery("select * from mealtype inner join w_m on mealtype.mNo = w_m.mNo where w_m.wNo = ?", wedding.getRno());
			
			comMeal.removeAllItems();
			
			while(rs.next()) {
				comMeal.addItem(new MealType(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		lbImg.setIcon(getImageIcon(String.format("datafile/호텔이미지/%s/%s1.jpg", wedding.getName(), wedding.getName()), 460, 170));
		
		File[] files = new File(String.format("./datafile/호텔이미지/%s/", wedding.getName())).listFiles();
		
		pnlImg.setLayout(new GridLayout(1, files.length));
		pnlImg.removeAll();
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			
			JLabel lb = new JLabel();
			lb.setBorder(BorderFactory.createLineBorder(i == 0? Color.red: Color.BLACK));
			lb.setIcon(getImageIcon(file.getPath(), 460 / files.length, 60));
			lb.setCursor(new Cursor(Cursor.HAND_CURSOR));
			lb.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (Component comp : pnlImg.getComponents()) {
						((JLabel) comp).setBorder(BorderFactory.createLineBorder(Color.BLACK));
					}
					
					lbImg.setIcon(getImageIcon(file.getPath(),460,170));
					lb.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
			});
			
			pnlImg.add(lb);
		}
	}
	
	public static void main(String[] args) {
		ArrayList<Wedding> list = new ArrayList<Wedding>();
		
		new ReservationFrame(list, 0).setVisible(true);
	}

	private void getMealPrcie() {
		MealType meal = (MealType) comMeal.getSelectedItem();
		tfPrice.setText(String.valueOf(meal.getPrice()));
	}
	
	private void reserve() {
		String people = tfPeopleStr.getText();
		String date = tfDate.getText();
		String letter = tfLetter.getText().isEmpty() ? "0" : tfLetter.getText();
		
		pnl.removeAll();
		
		String code = getCode();
		
		pnl.add(new JLabel(String.format("예약이 완료되었습니다.\n 예약번호는 %s입니다.", code)));
		
		if(people.isEmpty() || people.matches("^[0-9]+$") == false || Integer.valueOf(tfPeople.getText()) < Integer.valueOf(people)) {
			eMessage("인원수를 바르게 입력해주세요.");
			
		}else if(date.isEmpty()) {
			eMessage("날짜를 선택해주세요.");
			
		}else {
			
			try {
				
				int m = JOptionPane.showConfirmDialog(null, pnl, "예약완료", JOptionPane.YES_NO_OPTION + JOptionPane.INFORMATION_MESSAGE);
				
				Wedding wedding = list.get(index);
				WeddingType weddingType = (WeddingType) comType.getSelectedItem();
				MealType mealType = (MealType) comMeal.getSelectedItem();
				
				dbManager.executeUpdate("insert into reservation values(?, ?, ?, ?, ?, 0, ?, 0, ?, 0)", code, wedding.getRno(), people, weddingType.getNo(), mealType.getNo(), letter, date);
			
				if(m == 0) {
					iMessage("복사가 완료되었습니다.");
					
					StringSelection stringSelection = new StringSelection(code);
					Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
					clpbrd.setContents(stringSelection, null);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
				
			openFrame(new MainFrame());
			
		}
	}
	
	private String getCode() {
		String random = String.valueOf((int) (Math.random() * 99999) + 10000);
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from reservation where rno = ?", random);
		
			if(rs.next()) {
				random = getCode();				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return random;
	}
}