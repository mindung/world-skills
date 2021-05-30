package frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.WeddingType;

public class InsertFrame extends BaseFrame{
	
	private String[] Weding = {"일반웨딩","강당","하우스","호텔웨딩홀","야외예식","컨벤션","레스토랑","회관","성당","교회"};
	private String[] Meal = {"양식","뷔페","한식"};
	
	private JPanel pnlImg = new JPanel(new GridLayout(5,1));
	
	private JTextField tfName = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JTextField tfpeople = new JTextField();
	private JTextField tfAmount = new JTextField();
	
	ArrayList<WeddingType> list = new ArrayList<WeddingType>();
	private File file;
	private File newFile;
	
	private ArrayList<File> imageFiles = new ArrayList<File>();
	
	private JPanel pnlFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel pnlFlow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	public InsertFrame() {
		super("수정", 700, 600);
		setLayout(null);
		JPanel pnlGird = new JPanel(new GridLayout(4, 2, -160, 20));
		JPanel pnlSouth = new JPanel();

		JButton btnInsert = new JButton("등록");
		JButton btnExit = new JButton("취소");

//		pnlImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlGird.add(new JLabel("웨딩홀명"));
		pnlGird.add(tfName);
		pnlGird.add(new JLabel("주소"));
		pnlGird.add(tfAddress);
		pnlGird.add(new JLabel("수용인원"));
		pnlGird.add(tfpeople);
		pnlGird.add(new JLabel("홀사용료"));
		pnlGird.add(tfAmount);

		btnExit.setPreferredSize(new Dimension(140, 30));
		btnInsert.setPreferredSize(new Dimension(140, 30));

		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);

		addC(pnlImg, 0, 10, 150, 400);
		addC(pnlGird, 160, 70, 350, 150);
		addC(new JLabel("예식형태"), 160, 240, 100, 25);
		addC(new JLabel("식사종류"), 160, 350, 60, 25);
		addC(pnlFlow2, 240, 350, 400, 35);
		addC(pnlFlow, 240, 240, 400, 100);
		addC(pnlSouth, 160, 430, 300, 30);

		btnInsert.addActionListener(e -> InsertInfo());
		btnExit.addActionListener(e -> openFrame(new AdminFrame()));

		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM weddingType");
			
			while(rs.next()) {
				JCheckBox cbWedding = new JCheckBox(rs.getString(2));
				cbWedding.setName(rs.getString(1));
				cbWedding.setPreferredSize(new Dimension(90, 25));
				pnlFlow.add(cbWedding);
				
			}
			
			rs = dbManager.executeQuery("SELECT * FROM mealType");
			
			while(rs.next()) {
				JCheckBox cbMeal = new JCheckBox(rs.getString(2));
				cbMeal.setName(rs.getString(1));
				cbMeal.setPreferredSize(new Dimension(90, 25));
				pnlFlow2.add(cbMeal);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		imageInsert();
	}

	public static void main(String[] args) {
		new InsertFrame().setVisible(true);
	}
	
	private void InsertInfo() {

		String name = tfName.getText();
		String address = tfAddress.getText();
		String people = tfpeople.getText();
		String price = tfAmount.getText();

		int wtyCnt = 0;
		int mtyCnt = 0;
		
		for (Component com : pnlFlow.getComponents()) {
			if (((JCheckBox) com).isSelected()) {
				wtyCnt++;
			}
		}
		
		for (Component com : pnlFlow2.getComponents()) {
			if (((JCheckBox) com).isSelected()) {
				mtyCnt++;
			}
		}
		
		if (name.isEmpty() || address.isEmpty() || people.isEmpty() || price.isEmpty() || wtyCnt == 0 || mtyCnt == 0) {
			eMessage("빈칸을 입력해주세요.");

		} else if (people.matches("^[0-9]+$") == false) {
			eMessage("수용인원을 바르게 입력해주세요.");

		} else if (price.matches("^[0-9]+$") == false) {
			eMessage("홀사용료를 바르게 입력해주세요.");

		} else {
			iMessage("등록이 완료되었습니다.");

			try {
				dbManager.executeUpdate("insert into weddinghall values(0, ?, ?, ?, ?)", name,address,people,price);
				
				ResultSet rs = dbManager.executeQuery("select max(wno) from weddinghall");
				
				rs.next();
				int no = rs.getInt(1);
				
//				dbManager.executeUpdate("delete from w_ty where wno = ?", no);
//				dbManager.executeUpdate("delete from w_m where wno = ?", no);

				for (Component com : pnlFlow.getComponents()) {
					JCheckBox cb = (JCheckBox) com;
					if (cb.isSelected()) {
						dbManager.executeUpdate("insert into w_ty values(?, ?)", no, Integer.valueOf(cb.getName()));
					}
				}
				
				for (Component com : pnlFlow2.getComponents()) {
					JCheckBox cb = (JCheckBox) com;
					if (cb.isSelected()) {
						dbManager.executeUpdate("insert into w_m values(?, ?)", no, Integer.valueOf(cb.getName()));
					}
				}
				
//				// v체크박스 선택값
//				dbManager.executeUpdate("insert into w_ty values(?, ?)", no, 0);
//
//				// 체크박스 선택값
//
//				dbManager.executeUpdate("insert into w_m values(?, ?)", no, 0);

				File newDir = new File(String.format("./datafile/호텔이미지/%s",name));
				
				newDir.mkdir(); // 폴더 만들기
				
				for (int i = 0; i < imageFiles.size(); i++) {
					fileCopy(imageFiles.get(i).getPath(), String.format("./datafile/호텔이미지/%s/%s%d.jpg", name, name, i+1));
				}
				
				openFrame(new AdminFrame());
				
			} catch (SQLException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void fileCopy(String input, String outPut) throws FileNotFoundException {
		try {
			FileInputStream in = new FileInputStream(input);
			FileOutputStream out = new FileOutputStream(outPut);

			int data = 0;

			while ((data = in.read()) != -1) {
				out.write(data);
			}

			in.close();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void imageInsert() {
		for (int i = 0; i < 5; i++) {
			JLabel lb = new JLabel();
			lb.setName(String.valueOf(i)); //인덱스를 레이블에 넣어 몇번째 레이블인지 판별
			
			if(i == 0) { // 첫번째 레이블에만 테두리를 주고 event를 연결해줌
				lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				lb.addMouseListener(mouseAdapter);
			}
			
			pnlImg.add(lb);
			pnlImg.setPreferredSize(new Dimension(150, i * 80));
		}
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel lb = (JLabel) e.getSource(); //클릭한 레이블을 가져옴
			
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("JPG 파일", "jpg"));
			
			int open = fc.showOpenDialog(null);
			
			if(open == 0) {
				file  = fc.getSelectedFile();
				lb.setIcon(getImageIcon(file.getPath(), 150, 80));
				
				imageFiles.add(file); // imageFiles 리스트에 해당 파일 추가
				
				int index = Integer.valueOf(lb.getName()) + 1; // 클릭한 레이블의 다음 레이블을 가져오기 위한 index
				
				if (index != 5) {
					JLabel lbNext = (JLabel) pnlImg.getComponent(index); // 클릭한 레이블의 다음 레이블을 가져옴
					
					lbNext.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 다음 레이블 테두리
					lbNext.addMouseListener(mouseAdapter); // 다음 레이블 event 주기
				}
			}
		}
	};
	
//	for (int i = 0; pnlImg.getComponentCount(); i++) {
//		JLabel lb = new JLabel();
//
//		//lb.setIcon(getImageIcon(file.getPath(), 150, 80));
//
//		if (i < 5) {
//			lb.setVisible(false);
//		} else {
//
//		}
//
//		pnlImg.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				JFileChooser fc = new JFileChooser();
////				int file = fc.setFileFilter(");
//				int open = fc.showOpenDialog(null);
//				
//				if(open == 0) {
//					file = fc.getSelectedFile();
//					lb.setIcon(getImageIcon(file.getPath()));
//				}
//				
//				pnlImg.add(lb);
//			}
//		});
//	}


}
