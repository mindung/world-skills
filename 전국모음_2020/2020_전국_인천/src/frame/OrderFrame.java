package frame;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OrderFrame extends BaseFrame implements Runnable{

	private ArrayList<Product> list = new ArrayList<>();
	private JTextField tfName = new JTextField(15);
	private JTextField tfPrice = new JTextField(15);
	private JTextField tfCount= new JTextField(15);
	private JTextArea tfInfo = new JTextArea();
	
	private static  String name = "";
	private static int price = 0;
	private static String info ;
	private static String group ;
	
	private JPanel pnlOrder = new JPanel(new GridLayout(1, 6));
	private JPanel[] pnlProduct = new JPanel[6];
	private JLabel lbImg;
	
	private int index = 0;
	
	public OrderFrame(String name, int price, String info, String group) {
		super("구매", 800, 600);
	
		this.name = name;
		this.price = price;
		this.info = info;
		this.group = group;
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlflow = new JPanel(new FlowLayout());
		JPanel pnlBorder = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel();
		
		JLabel lbName = new JLabel("제품명");
		JLabel lbPrice = new JLabel("가격");
		JLabel lbCount = new JLabel("수량");
		JLabel lbInfo = new JLabel("상품 설명");
		JLabel lbCategory = new JLabel("같은 카테고리 목록");
		
		lbImg = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("./지급자료/이미지폴더/%s.jpg", name)).getScaledInstance(170	, 170, Image.SCALE_SMOOTH)));

		JButton btnOrder = new JButton("구매하기");
		JButton btnExit = new JButton("취소하기");
		
		pnlflow.add(lbName);
		pnlflow.add(tfName);
		pnlflow.add(lbPrice);
		pnlflow.add(tfPrice);
		pnlflow.add(lbCount);
		pnlflow.add(tfCount);
		
		lbName.setPreferredSize(new Dimension(60, 50));
		lbPrice.setPreferredSize(new Dimension(60, 50));
		lbCount.setPreferredSize(new Dimension(60, 50));
		
		pnlBorder.setPreferredSize(new Dimension(300, 200));
		
		tfInfo.setPreferredSize(new Dimension(300, 180));

		btnOrder.setPreferredSize(new Dimension(100, 200));
		btnExit.setPreferredSize(new Dimension(100, 200));
		pnlOrder.setPreferredSize(new Dimension(700, 170));
		
		pnlBorder.add(lbInfo,BorderLayout.NORTH);
		pnlBorder.add(tfInfo,BorderLayout.CENTER);
		
		lbImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pnlCenter.add(lbImg);
		pnlCenter.add(pnlflow);
		pnlCenter.add(pnlBorder);
		pnlCenter.add(btnOrder);
		pnlCenter.add(btnExit);
		pnlCenter.add(lbCategory);
		
		add(pnlOrder,BorderLayout.SOUTH);
		add(pnlCenter);
		
		lbImg.setBounds(15, 15, 170, 170);
		lbCategory.setBounds(5, 350, 150, 50);
		pnlflow.setBounds(180, 15, 300, 150);
		pnlBorder.setBounds(15, 200, 300, 150);
		btnOrder.setBounds(400, 310, 120, 35);
		btnExit.setBounds(550, 310, 120, 35);
		
		tfName.setText(name);
		tfPrice.setText(String.valueOf(price));
		tfInfo.setText(info);
		
		btnOrder.addActionListener(e -> order());
		btnExit.addActionListener(e -> openFrame(new ProductFrame()));
		
		tfName.setEnabled(false);
		tfPrice.setEnabled(false);
		tfInfo.setLineWrap(true);
		tfInfo.setEditable(false);
		tfInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		getOrder(group);
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public static void main(String[] args) {
		new ProductFrame().setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				addOrder();
				Thread.sleep(3000);
				index ++;
				
				if (index == list.size()) {
					index = 0;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void order() {
		
		String count = tfCount.getText();
		int price = Integer.valueOf(tfPrice.getText());
		int pno = 0;
		int stock = 0;
		
		if (count.isEmpty() || count.equals("0")) {
			return;
		}
		
		if (count.matches("^[0-9]+$") == false) {
			eMessage("숫자로 입력해주세요.");

		} else {

			int amount = Integer.valueOf(price) * Integer.valueOf(tfCount.getText());
			
			int yn = JOptionPane.showConfirmDialog(null, String.format("총 가격이 %d입니다.\n 결제하시겠습니까?", amount), "결제", JOptionPane.YES_NO_OPTION);
			
			if (yn == JOptionPane.YES_OPTION) {
				
				try {
					ResultSet rs = dbManager.executeQuery("select * from product where p_name = ?", tfName.getText());
					
					if(rs.next()) {
						pno = rs.getInt(1);
						stock = rs.getInt(5);
					}
					
					dbManager.executeUpdate("insert into purchase values(0, ?, ?, ?, ?, ?)", pno, price,count, amount, BaseFrame.u_no);
					dbManager.executeUpdate("update product set p_stock = ? where p_no = ?", stock - Integer.valueOf(count), pno);
					
					iMessage("결제가 완료되었습니다.");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void addOrder() {
		
		pnlOrder.removeAll();
		
		for (int i = 0; i < pnlProduct.length; i++) {
			
			pnlProduct[i] = new JPanel(new BorderLayout());
			
			int idx = index + i;
			
			if (index + i >= list.size()) {
				idx = index + i - list.size();
			}

			Product product = list.get(idx); 
					
			JLabel lbName = new JLabel(product.name, JLabel.CENTER);
			JLabel lbImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("./지급자료/이미지폴더/%s.jpg", product.name)).getScaledInstance(155, 150, Image.SCALE_SMOOTH)));
			
			pnlProduct[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			pnlProduct[i].add(lbName,BorderLayout.SOUTH);
			pnlProduct[i].add(lbImage,BorderLayout.CENTER);
			
			pnlProduct[i].setPreferredSize(new Dimension(130, 150));
			
			pnlOrder.add(pnlProduct[i]);
			
			lbImage.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
					if (e.getClickCount() == 2) {
						
						tfName.setText(product.name);
						tfPrice.setText(String.valueOf(product.price));
						tfInfo.setText(product.explanation);
						
						lbImg.setIcon(new ImageIcon(
								Toolkit.getDefaultToolkit().getImage(String.format("./지급자료/이미지폴더/%s.jpg", product.name)).
								getScaledInstance(170, 170, Image.SCALE_SMOOTH)));
						
					}
				}
			});
		}
		
		pnlOrder.updateUI();
	}
	
	private class Product{
		
		private int no;
		private String name;
		private String category;
		private int price;
		private int stock;
		private String explanation;

		public Product(int no, String category, String name, int price, int stock, String explanation) {
			
			this.no = no;
			this.category = category;
			this.name = name;
			this.price = price;
			this.stock = stock;
			this.explanation = explanation;
		}
	}
	private void getOrder(String group) {
		
		p_group = group;
		pnlOrder.removeAll();
		
		try {
			int cnt = 0;
			
			ResultSet rs = dbManager.executeQuery("SELECT p_no, c_name, p_name, p_price, p_stock, p_explanation FROM product inner join category on product.c_no = category.c_no where c_name = ?", group);
			
			while (rs.next()) {
				list.add(new Product(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getInt(4),rs.getInt(5), rs.getString(6)));
				cnt++;
			}
			
			pnlOrder.setLayout(new GridLayout(1, cnt));
			pnlOrder.updateUI();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}