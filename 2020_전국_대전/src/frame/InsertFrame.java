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
	
	private String[] Weding = {"�Ϲݿ���","����","�Ͽ콺","ȣ�ڿ���Ȧ","�߿ܿ���","������","�������","ȸ��","����","��ȸ"};
	private String[] Meal = {"���","����","�ѽ�"};
	
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
		super("����", 700, 600);
		setLayout(null);
		JPanel pnlGird = new JPanel(new GridLayout(4, 2, -160, 20));
		JPanel pnlSouth = new JPanel();

		JButton btnInsert = new JButton("���");
		JButton btnExit = new JButton("���");

//		pnlImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlGird.add(new JLabel("����Ȧ��"));
		pnlGird.add(tfName);
		pnlGird.add(new JLabel("�ּ�"));
		pnlGird.add(tfAddress);
		pnlGird.add(new JLabel("�����ο�"));
		pnlGird.add(tfpeople);
		pnlGird.add(new JLabel("Ȧ����"));
		pnlGird.add(tfAmount);

		btnExit.setPreferredSize(new Dimension(140, 30));
		btnInsert.setPreferredSize(new Dimension(140, 30));

		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);

		addC(pnlImg, 0, 10, 150, 400);
		addC(pnlGird, 160, 70, 350, 150);
		addC(new JLabel("��������"), 160, 240, 100, 25);
		addC(new JLabel("�Ļ�����"), 160, 350, 60, 25);
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
			eMessage("��ĭ�� �Է����ּ���.");

		} else if (people.matches("^[0-9]+$") == false) {
			eMessage("�����ο��� �ٸ��� �Է����ּ���.");

		} else if (price.matches("^[0-9]+$") == false) {
			eMessage("Ȧ���Ḧ �ٸ��� �Է����ּ���.");

		} else {
			iMessage("����� �Ϸ�Ǿ����ϴ�.");

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
				
//				// vüũ�ڽ� ���ð�
//				dbManager.executeUpdate("insert into w_ty values(?, ?)", no, 0);
//
//				// üũ�ڽ� ���ð�
//
//				dbManager.executeUpdate("insert into w_m values(?, ?)", no, 0);

				File newDir = new File(String.format("./datafile/ȣ���̹���/%s",name));
				
				newDir.mkdir(); // ���� �����
				
				for (int i = 0; i < imageFiles.size(); i++) {
					fileCopy(imageFiles.get(i).getPath(), String.format("./datafile/ȣ���̹���/%s/%s%d.jpg", name, name, i+1));
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
			lb.setName(String.valueOf(i)); //�ε����� ���̺� �־� ���° ���̺����� �Ǻ�
			
			if(i == 0) { // ù��° ���̺��� �׵θ��� �ְ� event�� ��������
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
			JLabel lb = (JLabel) e.getSource(); //Ŭ���� ���̺��� ������
			
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("JPG ����", "jpg"));
			
			int open = fc.showOpenDialog(null);
			
			if(open == 0) {
				file  = fc.getSelectedFile();
				lb.setIcon(getImageIcon(file.getPath(), 150, 80));
				
				imageFiles.add(file); // imageFiles ����Ʈ�� �ش� ���� �߰�
				
				int index = Integer.valueOf(lb.getName()) + 1; // Ŭ���� ���̺��� ���� ���̺��� �������� ���� index
				
				if (index != 5) {
					JLabel lbNext = (JLabel) pnlImg.getComponent(index); // Ŭ���� ���̺��� ���� ���̺��� ������
					
					lbNext.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // ���� ���̺� �׵θ�
					lbNext.addMouseListener(mouseAdapter); // ���� ���̺� event �ֱ�
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
