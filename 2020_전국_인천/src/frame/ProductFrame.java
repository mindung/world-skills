package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductFrame extends BaseFrame{
	
	
	private JPanel pnlProduct = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	private JTextField tfName = new JTextField(8);
	private JTextField tfMinPrice = new JTextField(8);
	private JTextField tfMaxPrice = new JTextField(8);
	
	private String[] category = {"채소", "과일", "정육", "해산물", "가공식품", "유제품", "생활용품", "주방용품"};
	private JLabel[] lbCategory = new JLabel[8];
	
	private JLabel lbTitle = new JLabel("카테고리");
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"상품번호","상품 카테고리","상품 이름","상품 가격","상품 재고","상품 설명"},0) {
	
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	
	private JTable table = new JTable(dtm);
	
	private JPanel pnlCategory = new JPanel(new GridLayout(category.length + 9, 1));
	
	private String c_name;
	
	public ProductFrame() {
		
		super("상품목록", 1000, 775);
		
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlWest = new JPanel(null);
		JPanel pnlSearch = new JPanel(new FlowLayout());
		JPanel pnlSouth = new JPanel();
		
		JLabel lbUser = new JLabel(String.format("유저 : %s", u_name));
		
		JLabel lbName = new JLabel("이름",JLabel.LEFT);
		JLabel lbMinPrice= new JLabel("최저 가격",JLabel.LEFT);
		JLabel lbMaxPrice = new JLabel("최대 가격",JLabel.LEFT);
		
		JButton btnSearch = new JButton("검색");
		JButton btnHotProduct = new JButton("인기상품");
		JButton btnOrderlist = new JButton("구매목록");
		
		JScrollPane jsp = new JScrollPane(pnlProduct, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane jsp1 = new JScrollPane(table);
		
		dtm.setColumnIdentifiers(new String[] {"상품번호","상품 카테고리","상품 이름","상품 가격","상품 재고","상품 설명"});
		
		pnlSearch.add(lbName);
		pnlSearch.add(tfName);
		pnlSearch.add(lbMaxPrice);
		pnlSearch.add(tfMaxPrice);
		pnlSearch.add(lbMinPrice);
		pnlSearch.add(tfMinPrice);
		pnlSearch.add(btnSearch);
		
		pnlCategory.add(lbTitle);
		
		for (int i = 0; i < category.length; i++) {
			lbCategory[i] = createLabel(category[i]);
			pnlCategory.add(lbCategory[i]);
		}
		
		pnlNorth.add(lbUser);
		
		pnlSouth.add(btnHotProduct);
		pnlSouth.add(btnOrderlist);
		
		pnlWest.add(pnlSearch);
		pnlWest.add(pnlSouth);
		pnlWest.add(pnlCategory);
		pnlWest.add(jsp);
		pnlWest.add(jsp1);
		pnlWest.add(btnSearch);

		lbName.setPreferredSize(new Dimension(55, 45));
		lbMinPrice.setPreferredSize(new Dimension(58, 45));
		lbMaxPrice.setPreferredSize(new Dimension(58, 45));
		btnSearch.setPreferredSize(new Dimension(150, 35));
		
		
		for (Component com : pnlCategory.getComponents()) {
			JLabel lb = (JLabel) com;
			lb.setPreferredSize(new Dimension(200, 40));
		}
		
		lbUser.setFont(new Font("굴림",Font.BOLD,20));
		lbTitle.setFont(new Font("굴림",Font.BOLD,15));
		
		add(pnlWest);
		add(pnlNorth,BorderLayout.NORTH);
		
		jsp.setBounds(250, 40, 730, 490);
		jsp1.setBounds(250, 530, 730, 172);
		
		btnSearch.setBounds(20, 190, 150,35);
		pnlSearch.setBounds(20, 14, 160, 160);
		pnlSouth.setBounds(20, 660, 200, 50);
		pnlCategory.setBounds(5, 250, 200, 800);

		btnHotProduct.addActionListener(e -> openFrame(new ProductCareFrame(), new ProductFrame()));
		btnSearch.addActionListener(e -> search());
		btnOrderlist.addActionListener(e -> openFrame(new OrderListFrame(), new ProductFrame()));
		
		p_group = category[0];
		
		lbCategory[0].setForeground(Color.red);

		getAllProduct();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.getSelectedRow();
				
				if (e.getClickCount() == 2) {
					openFrame(new OrderFrame( (String) table.getValueAt(row, 2), (int) table.getValueAt(row, 3), (String) table.getValueAt(row, 5), (String) table.getValueAt(row, 1)));
				}
			}
		});
	
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				new LoginFrame().setVisible(true);
			}
		});
	}
	
	public static void main(String[] args) {
		new ProductFrame().setVisible(true);
	}

	private JLabel createLabel(String str) {
	
		JLabel lb = new JLabel(str);
		lb.setOpaque(true);
		
		lb.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				
				if (lb.getText().equals(p_group)) {
					lb.setForeground(Color.red);
				} else {
					lb.setForeground(Color.black);
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				lb.setForeground(Color.blue);
		
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2 ) {

					for (Component com: pnlCategory.getComponents()) {
						JLabel lb = (JLabel) com;
						lb.setForeground(Color.BLACK);
					}
					
					lb.setForeground(Color.red);
					
					p_group = str;
					getAllProduct();
					pnlProduct.updateUI();
					
				}
			}
		});
		
		return lb;
		
	}
	private void search() {
		String name = tfName.getText();
		String strMin = tfMinPrice.getText();
		String strMax = tfMaxPrice.getText();
		
		if ((strMin.isEmpty() == false && strMin.matches("^[0-9]+$") == false) || (strMax.isEmpty() == false && strMax.matches("^[0-9]+$") == false)) {
			eMessage("최대가격과 최소가격은 숫자로 입력해주세요.");
			return;
		}

		int max = strMax.isEmpty() ? 1000000 : Integer.valueOf(strMax);
		int min = strMin.isEmpty() ? 0 : Integer.valueOf(strMin);

		if (max < min) {
			eMessage("최소 가격은 최대 가격보다 낮아야 합니다.");
			return;
		}
	
		try {
			ResultSet rs = dbManager.executeQuery("SELECT p_no, c_name, p_name,p_price, p_stock,p_explanation FROM product inner join category on product.c_no = category.c_no where p_name like '%" + name + "%' and p_price >= ? and p_price <= ?", min, max);
			getProduct(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	} 
	
	private void getAllProduct() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT p_no, c_name, p_name,p_price, p_stock,p_explanation FROM product inner join category on product.c_no = category.c_no where c_name = ?", p_group);
			getProduct(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getProduct(ResultSet rs) {
		pnlProduct.removeAll();
		dtm.setRowCount(0);
		
		try {
			int cnt = 0;

			while (rs.next()) {
				
				addMenu(rs.getInt(1), rs.getString(3),rs.getInt(4),rs.getString(6),rs.getString(2));
				dtm.addRow(new Object[] { rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getInt(4),
						rs.getInt(5),
						rs.getString(6)
						
				});
				cnt++;	

			}

			pnlProduct.setLayout(new GridLayout((cnt / 3 + (cnt % 3 == 0 ? 0 : 1)), 3));
			pnlProduct.setPreferredSize(new Dimension(650, 245 * (cnt / 3 + (cnt % 3 == 0 ? 0 : 1)))); // 220 * ( 데이터 개수 / 3 + ( 데이터 개수 나눈 몫이 3 이면 0, 아니면 1))
			pnlProduct.updateUI();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void addMenu(int no, String name, int price, String info, String group) {
		
		JPanel pnl = new JPanel(new BorderLayout());
		
		JLabel lbName = new JLabel(name,JLabel.CENTER);
		JLabel lbImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("./지급자료/이미지폴더/%s.jpg", name)).getScaledInstance(240, 240, Image.SCALE_SMOOTH)));
		
		pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pnl.setPreferredSize(new Dimension(230, 200));
		pnl.add(lbName,BorderLayout.SOUTH);
		pnl.add(lbImage,BorderLayout.CENTER);
		
		pnlProduct.add(pnl);
		
		lbImage.addMouseListener(new MouseAdapter() {
	
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					openFrame(new OrderFrame(name, price, info, group));
				}
			}
		});
	}
}


