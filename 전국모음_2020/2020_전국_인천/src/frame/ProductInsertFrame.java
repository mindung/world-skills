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
	
	private File file; // ���� ����
	
	public ProductInsertFrame() { 
		super("��ǰ���", 370, 510);
	
		JPanel pnlN = new JPanel(new BorderLayout());
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlCC = new JPanel(new GridLayout(4, 2 ,-20 ,10 ));
		
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnImgInsert = new JButton("���� �ֱ�");
		JButton btnInsert = new JButton("���");
		JButton btnExit = new JButton("���");
		
		JLabel lbName = new JLabel("�̸�");
		JLabel lbCategoty = new JLabel("ī�װ�");
		JLabel lbPrice = new JLabel("����");
		JLabel lbStock = new JLabel("���");
		JLabel lbInfo = new JLabel("����");
		
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
		
		JFileChooser jfc = new JFileChooser(); // ��ü ����
		
		jfc.setFileFilter(new FileNameExtensionFilter("JPG Image",".jpg")); // Ȯ����
		jfc.setFileFilter(new FileNameExtensionFilter("PNG Image",".jpg")); // Ȯ����
		
		int open = jfc.showOpenDialog(null); // ����â ���� 
		
		if(open == 0) { // ���� ������ ���
			
			file = jfc.getSelectedFile(); // private �������� ���� ����
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
			eMessage("��ĭ�� �ֽ��ϴ�.");
			
		} else if (file == null) {
			eMessage("�̹��� ������ �������ּ���.");

		} else {
			
			try {
				ResultSet rs = dbManager.executeQuery("select * from category where c_name = ? ", categroy);
				
				if (rs.next()) {
					c_no = rs.getInt(2);
				}
				
				System.out.println(c_no);
				
				dbManager.executeUpdate("insert into product values(0, ?, ?, ?, ?, ?)", c_no, name, price, stork, info);
				
				fileCopy(file.getPath(), String.format("./�����ڷ�/�̹�������/%s.jpg", name));
				
				openFrame(new ProductCareFrame());
				
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void fileCopy(String input, String output) throws IOException {
		//String.format("./�����ڷ�/�̹����ڷ�/%s.jpg", tfName.getText())
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

