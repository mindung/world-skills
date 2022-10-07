package frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.MealType;
import model.Wedding;
import model.WeddingType;

public class UpdateFrame extends BaseFrame {
	
//	private String[] Wedding = {"�Ϲݿ���Ȧ", "����", "�Ͽ콺", "ȣ�ڿ���Ȧ", "�߿ܿ���", "������","�������","ȸ��","����","��ȸ"};
//	private String[] Meal = {"���","����","�ѽ�"};
	
	private JPanel pnlImg = new JPanel(new GridLayout(5,1));
	
	private JTextField tfName = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JTextField tfpeople = new JTextField();
	private JTextField tfAmount = new JTextField();
	
	private ArrayList<WeddingType> list_wty = new ArrayList<>();
	private ArrayList<MealType> list_mty = new ArrayList<>();
	private Wedding wedding;
	
	private File file1;
	
	private File[] imageFiles;
	
	private JPanel pnlFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel pnlFlow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	public UpdateFrame(Wedding wedding) {
		super("����", 700, 600);
		
		setLayout(null);
		
		this.wedding = wedding;
		
		JPanel pnlGird = new JPanel(new GridLayout(4, 2, -160, 20));
		JPanel pnlSouth = new JPanel();
		
		JButton btnUpdate = new JButton("����");
		JButton btnExit  = new JButton("���");
		
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
		btnUpdate.setPreferredSize(new Dimension(140, 30));
		
		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnExit);
		
		addC(pnlImg, 0, 10, 150, 400);
		addC(pnlGird, 160, 70, 350, 150);
		addC(new JLabel("��������"), 160, 240, 100, 25);
		addC(new JLabel("�Ļ�����"), 160, 350, 60, 25);
		addC(pnlFlow2,240, 350, 400, 35);
		addC(pnlFlow, 240, 240, 400, 100);
		addC(pnlSouth, 160, 430, 300, 30);
	
		btnUpdate.addActionListener(e -> infoUpdate());
		btnExit.addActionListener(e -> openFrame(new AdminFrame()));

		tfName.setEnabled(false);
		tfpeople.setEnabled(false);
		
		getinfo();
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM weddingType");
			
			while(rs.next()) {
				String name = rs.getString(2);
				
				JCheckBox cbWedding = new JCheckBox(name);
//				cbWeding.setText(name); // JCheckBox ���� �� �ٷ� �־��ָ� ��
				cbWedding.setName(rs.getString(1));
				cbWedding.setPreferredSize(new Dimension(90, 25));
				pnlFlow.add(cbWedding);
				
//				if (list_wty.contains(new WeddingType(0, name))) {
//					cbWedding.setSelected(true);
//				}
				
				// => �� �ּ��̶� �Ȱ���
				cbWedding.setSelected(list_wty.contains(new WeddingType(0, name)));
				
//				for (int j = 0; j < list_wty.size(); j++) {	
//					if (name.equals(list_wty.get(j).getName())) {
//						cbWedding.setSelected(true);
//					}
//				}
			}
			
			rs = dbManager.executeQuery("SELECT * FROM mealType");
			
			while(rs.next()) {
				String name = rs.getString(2);
				
				JCheckBox cbMeal = new JCheckBox(name);
//				cbMeal.setText(name);
				cbMeal.setName(rs.getString(1));
				cbMeal.setPreferredSize(new Dimension(90, 25));
				pnlFlow2.add(cbMeal);
				
				cbMeal.setSelected(list_mty.contains(new MealType(0, name, 0)));
				
//				for (int j = 0; j < list_mty.size(); j++) {
//					// �迭[i] ���� (��) = list_mty.get(j��°) �̸� 
//					if(Meal[i].equals(list_mty.get(j).getName())) {
//						cbMeal.setSelected(true);
//					}
//				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		new UpdateFrame(new Wedding(0, "", "", "", "", 0, 0)).setVisible(true);
		new AdminFrame().setVisible(true);
	}
	
	private void  infoUpdate() {
		
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
		} else if (price.matches("^[0-9]+$") == false) {
			eMessage("Ȧ���Ḧ �ٸ��� �Է����ּ���.");
		} else {
			iMessage("������ �Ϸ�Ǿ����ϴ�.");

			try {

				dbManager.executeUpdate("update weddinghall set wadd = ?, wprice = ? where wno = ?", address, price, wedding.getRno());

				dbManager.executeUpdate("delete from w_ty where wno = ?", wedding.getRno());
				dbManager.executeUpdate("delete from w_m where wno = ?", wedding.getRno());
				
				for (Component com : pnlFlow.getComponents()) {
					JCheckBox cb = (JCheckBox) com;
					if (cb.isSelected()) {
						dbManager.executeUpdate("insert into w_ty values(?, ?)", wedding.getRno(), Integer.valueOf(cb.getName()));
					}
				}
				
				for (Component com : pnlFlow2.getComponents()) {
					JCheckBox cb = (JCheckBox) com;
					if (cb.isSelected()) {
						dbManager.executeUpdate("insert into w_m values(?, ?)", wedding.getRno(), Integer.valueOf(cb.getName()));
					}
				}
				
//				for (int i = 0; i < pnlFlow.getComponentCount(); i++) {
//					if (((JCheckBox) pnlFlow.getComponent(i)).isSelected() == true) {
//						dbManager.executeUpdate("insert into w_ty values(?, ?)", wedding.getRno(), i + 1);
//					}
//				}

//				for (int i = 0; i < pnlFlow2.getComponentCount(); i++) {
//					if (((JCheckBox) pnlFlow2.getComponent(i)).isSelected() == true) {
//						dbManager.executeUpdate("insert into w_m values(?, ?)", wedding.getRno(), i + 1);
//					}
//				}
				
				for (int i = 0; i < imageFiles.length; i++) {
					String fileName = String.format(".\\datafile\\ȣ���̹���\\%s\\%s%d.jpg", wedding.getName(), wedding.getName(), i + 1);
					
					if (imageFiles[i].getPath().equals(fileName) == false) {	
						fileCopy(imageFiles[i].getPath(), fileName);						
					}
				}
				
				openFrame(new AdminFrame());
			
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	//fileCopy(file1.getPath(), String.format("./datafile/%s/%s6.jpg",name,name));
	
	private void getinfo() {

		tfName.setText(wedding.getName());
		tfAddress.setText(wedding.getAddress());
		tfpeople.setText(String.valueOf(wedding.getPeople()));
		tfAmount.setText(String.valueOf(wedding.getPrice()));
		
		try {
			ResultSet rs = dbManager.executeQuery("select weddingtype.* from w_ty inner join  weddingtype on weddingtype.tyno = w_ty.tyno where wno = ?", wedding.getRno());
			
			while (rs.next()) {
				list_wty.add(new WeddingType(rs.getInt(1), rs.getString(2)));
			}
			
			rs = dbManager.executeQuery("select mealtype.*  from mealtype inner join w_m on w_m.mno = mealtype.mno where wno = ?", wedding.getRno());
			
			while (rs.next()) {
				list_mty.add(new MealType(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		File[] files = new File(String.format("./datafile/ȣ���̹���/%s/", wedding.getName())).listFiles();
		
		imageFiles = files;
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			
			JLabel lb = new JLabel();
			lb.setIcon(getImageIcon(file.getPath(), 150, 80));
			//pnlImg.setPreferredSize(new Dimension(150, 80 * files.length));
			
			int index = i;
			
			lb.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					iMessage("���� ���̾�α� ȣ��");
					
					JFileChooser jfc = new JFileChooser();
					jfc.setFileFilter(new FileNameExtensionFilter("JPG ����", "jpg"));
					
					int open = jfc.showOpenDialog(null);
					
					if(open == 0) {
						 file1 = jfc.getSelectedFile();
						 System.out.println(file1.getPath());
						 lb.setIcon(getImageIcon(file1.getPath(), 150, 80));
						 files[index] = file1;
					}
				}
			});
			
			pnlImg.add(lb);
		}
	}
	
	public static void fileCopy(String inFileName, String outFileName) {
		try {
			FileInputStream fis = new FileInputStream(inFileName);
			FileOutputStream fos = new FileOutputStream(outFileName);
			
			int data = 0;
			
			while ((data=fis.read())!= -1) {
				fos.write(data);
			}
			
			fis.close();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
