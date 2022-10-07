package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProductInsertFrame extends BaseFrame{
	
	private JLabel lbImage = new JLabel();
	
	private JTextField tfName = new JTextField(13);
	private JTextField tfCategory = new JTextField(13);
	private JTextField tfPrice = new JTextField(13);
	private JTextField tfStock = new JTextField(13);
	private JTextField tfInfo = new JTextField(20);
	
	private File file; // 파일 선언
	
	public ProductInsertFrame() { 
		super("상품등록", 370, 510);
	
		JPanel pnlN = new JPanel(new BorderLayout());
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlCC = new JPanel(new GridLayout(4, 2 ,-20 ,10 ));
		
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnImgInsert = new JButton("사진 넣기");
		JButton btnInsert = new JButton("등록");
		JButton btnExit = new JButton("취소");
		
		JLabel lbName = new JLabel("이름");
		JLabel lbCategoty = new JLabel("카테고리");
		JLabel lbPrice = new JLabel("가격");
		JLabel lbStock = new JLabel("재고");
		JLabel lbInfo = new JLabel("설명");
		
		pnlCC.add(lbName);
		pnlCC.add(tfName);
		pnlCC.add(lbCategoty);
		pnlCC.add(tfCategory);
		pnlCC.add(lbPrice);
		pnlCC.add(tfPrice);
		pnlCC.add(lbStock);
		pnlCC.add(tfStock);
		
		pnlCenter.add(pnlCC);
		pnlCenter.add(tfInfo);
		pnlCenter.add(lbInfo);
		
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);
		
		lbName.setPreferredSize(new Dimension(100, 30));
		lbCategoty.setPreferredSize(new Dimension(100, 30));
		lbPrice.setPreferredSize(new Dimension(100, 30));
		lbStock.setPreferredSize(new Dimension(100, 30));
		lbInfo.setPreferredSize(new Dimension(100, 30));
		
		lbImage.setPreferredSize(new Dimension(450, 210));
		lbImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pnlN.add(lbImage, BorderLayout.CENTER);
		pnlN.add(btnImgInsert,BorderLayout.SOUTH);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlN,BorderLayout.NORTH);
		add(pnlSouth,BorderLayout.SOUTH);
		
		pnlCC.setBounds(5, 8, 200, 134);
		lbInfo.setBounds(5, 135, 60, 60);
		tfInfo.setBounds(95, 155, 200, 25);
		
		btnImgInsert.addActionListener(e -> getImage());
		btnInsert.addActionListener(e -> insert());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new ProductInsertFrame().setVisible(true);
	}

	private void getImage() {
		
		JFileChooser jfc = new JFileChooser(); // 객체 생성
		
		jfc.setFileFilter(new FileNameExtensionFilter("JPG Image",".jpg")); // 확장자
		jfc.setFileFilter(new FileNameExtensionFilter("PNG Image",".jpg")); // 확장자
		
		int open = jfc.showOpenDialog(null); // 열기창 정의 
		
		if(open == 0) { // 열기 눌렀을 경우
			
			file = jfc.getSelectedFile(); // private 형식으로 파일 선언
			System.out.println(file);
			
			lbImage.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(file.getPath()).getScaledInstance(lbImage.getWidth(),lbImage.getHeight(), Image.SCALE_SMOOTH)));
			
		}
	}
	
	private void insert() {
		
		String name = tfName.getText();
		String categroy = tfCategory.getText();
		String price  = tfPrice.getText();
		String stork = tfStock.getText();
		String info = tfInfo.getText();
		int c_no = 0;
		
		if(name.isEmpty() || categroy.isEmpty() || price.isEmpty() || stork.isEmpty()||info.isEmpty()) {
			eMessage("빈칸이 있습니다.");
			
		} else if (file == null) {
			eMessage("이미지 파일을 선택해주세요.");

		} else {
			
			try {
				ResultSet rs = dbManager.executeQuery("select * from category where c_name = ? ", categroy);
				
				if (rs.next()) {
					c_no = rs.getInt(2);
				}
				
				System.out.println(c_no);
				
				dbManager.executeUpdate("insert into product values(0, ?, ?, ?, ?, ?)", c_no, name, price, stork, info);
				
				fileCopy(file.getPath(), String.format("./지급자료/이미지폴더/%s.jpg", name));
				
				openFrame(new ProductCareFrame());
				
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void fileCopy(String input, String output) throws IOException {
		//String.format("./지급자료/이미지자료/%s.jpg", tfName.getText())
		try {
			FileInputStream in = new FileInputStream(input);
			FileOutputStream out = new FileOutputStream(output);
			
			int data = 0;
			while ((data = in.read()) != -1) {
				out.write(data);
			}
			
			in.close();
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}

