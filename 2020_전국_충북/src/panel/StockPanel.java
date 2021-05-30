package panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class StockPanel extends BasePanel {

	private DefaultListModel dlmProduct = new DefaultListModel();
	private DefaultListModel dlmStock = new DefaultListModel();
	
	private JList lstProduct = new JList(dlmProduct);
	private JList lstStock = new JList(dlmStock);
	
	private JLabel lbCurStock = createLabel("0개");
	private JTextField tfPrice = createComponent(new JTextField());
	private JComboBox<Integer> comStock = new JComboBox<>();
	
	private class Product {
		private int serial;
		private String name;
		
		public Product(int serial, String name) {
			super();
			this.serial = serial;
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	private class Stock {
		private int serial;
		private int productSerial;
		private String product;
		private int count;
		private int price;
		
		public Stock(int serial, int productSerial, String product, int count, int price) {
			this.serial = serial;
			this.productSerial = productSerial;
			this.product = product;
			this.count = count;
			this.price = price;
		}
		
		@Override
		public String toString() {
			return product;
		}
	}
	
	public StockPanel() {
		setLayout(null);
		
		JLabel lbProduct = new JLabel("제품 목록");
		JLabel lbStock = new JLabel("재고 목록");
		
		JScrollPane jspProduct = new JScrollPane(lstProduct);
		JScrollPane jspStock = new JScrollPane(lstStock);
		
		JButton btnDown = createButton("▼");
		JButton btnUp = createButton("▲");
		
		JPanel pnl = new JPanel(new GridLayout(3, 2, -200, 10));
		
		JButton btnAdd = createButton("추가");
		
		pnl.add(createLabel("현재 재고"));
		pnl.add(lbCurStock);
		pnl.add(createLabel("판매가"));
		pnl.add(tfPrice);
		pnl.add(createLabel("추가할 재고"));
		pnl.add(comStock);
		
		add(lbProduct);
		add(lbStock);
		add(jspProduct);
		add(jspStock);
		add(btnDown);
		add(btnUp);
		add(pnl);
		add(btnAdd);
		
		lbProduct.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbStock.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		
		lbProduct.setBounds(30, 20, 410, 30);
		jspProduct.setBounds(30, 50, 410, 200);
		
		lbStock.setBounds(30, 300, 410, 30);
		jspStock.setBounds(30, 330, 410, 200);
		
		btnDown.setBounds(200, 265, 30, 35);
		btnUp.setBounds(240, 265, 30, 35);
		
		pnl.setBounds(460, 20, 400, 100);
		btnAdd.setBounds(460, 130, 400, 30);
		
		pnl.setBackground(null);
		
		btnDown.addActionListener(e -> addStock());
		btnUp.addActionListener(e -> removeStock());
		btnAdd.addActionListener(e -> insertStock());
		
		lstStock.addListSelectionListener(e -> {
			Stock stock = (Stock) lstStock.getSelectedValue();
			
			lbCurStock.setText(String.format("%d개", stock.count));
			tfPrice.setText(String.valueOf(stock.price));
		});
		
		for (int i = 0; i <= 100; i++) {
			comStock.addItem(i);
		}
		
		getProducts();
		getStocks();
	}
	
	private void getProducts() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM product LEFT JOIN stock ON product.serial = stock.product GROUP BY product.serial HAVING stock.serial IS NULL OR stock.seller <> ? ORDER BY product.serial", sellerSerial);
			
			while(rs.next()) {
				dlmProduct.addElement(new Product(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getStocks() {
		try {
			ResultSet rs = dbManager.executeQuery("SELECT stock.serial, product.serial, product.name, stock.count, stock.price FROM stock INNER JOIN product ON product.serial = stock.product WHERE seller = ?", sellerSerial);
			
			while(rs.next()) {
				dlmStock.addElement(new Stock(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addStock() {
		Product product = (Product) lstProduct.getSelectedValue();
		
		if (product == null) {
			return;
		}
		
		dlmProduct.removeElement(product);
		dlmStock.addElement(new Stock(0, product.serial, product.name, 0, 0));
	}
	
	private void removeStock() {
		Stock stock = (Stock) lstStock.getSelectedValue();
		
		if (stock == null) {
			return;
		}
		
		if (stock.count != 0) {
			eMessage("재고가 남아있어 제거할 수 없습니다.");
		} else {
			dlmStock.removeElement(stock);
			dlmProduct.addElement(new Product(stock.productSerial, stock.product));
		}
	}
	
	private void insertStock() {
		String strPrice = tfPrice.getText();
		
		if (strPrice.isEmpty()) {
			eMessage("가격을 입력해주세요.");
		} else if (strPrice.matches("^[\\d]*$") == false) {
			eMessage("숫자만 입력해주세요.");
		} else {
			Stock stock = (Stock) lstStock.getSelectedValue();
			
			int count = stock.count + (int) comStock.getSelectedItem();
			int price = Integer.valueOf(strPrice);
			
			try {
				
				if (stock.serial == 0) {
					stock.serial = dbManager.executeUpdate("INSERT INTO stock VALUES (0, ?, ?, ?, ?)", sellerSerial, stock.productSerial, count, price);
				} else {
					dbManager.executeUpdate("UPDATE stock SET count = ?, price = ? WHERE serial = ?", count, price, stock.serial);
				}
				
				stock.count = count;
				stock.price = price;
				
				iMessage("재고를 추가했습니다.");
				
				lbCurStock.setText(String.format("%d개", stock.count));
				comStock.setSelectedIndex(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
