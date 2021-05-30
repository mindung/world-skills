package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class munuInsertFrame extends baseFrame {

	private JComboBox<String> comFood = new JComboBox<String>(new String[] { "����", "Ǫ��", "��ǰ" });
	private JTextField tfName = new JTextField(15);
	private JTextField tfPrice = new JTextField(15);

	private JLabel lbImage = new JLabel();

	private File file;
	
	public munuInsertFrame() {
		super("�޴��߰�", 361, 280);

		JPanel pnlWest = new JPanel(null);
		JPanel pnlSouth = new JPanel();

		JButton btnImageInsert = new JButton("�������");
		JButton btnInsert = new JButton("���");
		JButton btnExit = new JButton("���");
		btnImageInsert.setPreferredSize(new Dimension(40, 10));

		JLabel lbFood = new JLabel("�з� ", JLabel.LEFT);
		JLabel lbMunu = new JLabel("�޴���", JLabel.LEFT);
		JLabel lbPrice = new JLabel("����", JLabel.LEFT);

		pnlWest.add(lbFood);
		pnlWest.add(comFood);
		pnlWest.add(lbMunu);
		pnlWest.add(tfName);
		pnlWest.add(lbPrice);
		pnlWest.add(tfPrice);

		pnlWest.add(lbImage);
		pnlWest.add(btnImageInsert);

		lbFood.setBounds(5, 20, 50, 25);
		lbMunu.setBounds(5, 70, 50, 25);
		lbPrice.setBounds(5, 120, 50, 25);
		comFood.setBounds(60, 20, 50, 25);
		tfName.setBounds(60, 70, 140, 25);
		tfPrice.setBounds(60, 120, 140, 25);

		lbImage.setBounds(220, 10, 125, 135);
		btnImageInsert.setBounds(220, 145, 125, 30);

		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);

		lbImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(pnlWest,BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		btnImageInsert.addActionListener(e -> getImage());
		btnInsert.addActionListener(e -> Insert());
		btnExit.addActionListener(e -> openFrame(new adminFrame()));

	}

	public static void main(String[] args) {
		new munuInsertFrame().setVisible(true);

	}
	
	private void Insert() {
		
		String name = tfName.getText();
		String price  =tfPrice.getText();
		
		if(comFood.getSelectedItem() == null || name.isEmpty() || price.isEmpty()) {
			Emessage("��ĭ�� �����մϴ�.");
		}else {
			try {
				ResultSet rs = dbmanager.executeQuery("select * from menu where m_name = ?", name);
				
				if(rs.next()) {
					Emessage("�̹� �����ϴ� �޴����Դϴ�.");
					
				}else {
					
					if(price.matches("^[0-9]+$")) {
					
						Imessage("�޴��� ��ϵǾ����ϴ�.");
						
						dbmanager.executeUpate("insert into menu values(0,?,?,?)", comFood.getSelectedItem(),name,price);
						
						fileCopy(file.getPath(), String.format("./DataFiles/�̹���/%s.jpg", name));
						
						openFrame(new adminFrame());
					}else {
						Emessage("������ ���ڷ� �Է����ּ���.");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	private void getImage() {
		JFileChooser jfc = new JFileChooser(); // ��ü ����
		
		jfc.setFileFilter(new FileNameExtensionFilter("JPG Image", ".jpg")); // Ȯ����
		
		int open = jfc.showOpenDialog(null); // ����â ����
		
		if (open == 0) { // ���� ������ ��� 
			file = jfc.getSelectedFile(); // ���� ��� �˾ƿ�
			
			System.out.println(file.getPath()); // getPath() �Էµ� ��� ���
			
			lbImage.setIcon(new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(file.getPath())
							.getScaledInstance( 125, 135, Image.SCALE_SMOOTH)));
		}
	}
	
	 public static void fileCopy(String inFileName, String outFileName) {
		  try {
			  FileInputStream fis = new FileInputStream(inFileName);
			  FileOutputStream fos = new FileOutputStream(outFileName);
		   
			  int data = 0;
			  
			  while((data=fis.read())!=-1) {
				  fos.write(data);
			  }
			  
			  fis.close();
			  fos.close();
		   
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	 }
}
