package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ProductCareFrame extends BaseFrame {

	private JComboBox<String> comGroup = new JComboBox<>( new String[] {"전체", "정육", "과일", "채소", "해산물", "가공식품", "유제품", "생활용품", "주방용품" });

	private JPanel pnlProduct = new JPanel();

	private JButton btnInsert = new JButton("등록");
	private JButton btnUpdate = new JButton("수정");

	public ProductCareFrame() {

		super("상품관리", 900, 600);

		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JScrollPane jsp = new JScrollPane(pnlProduct, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pnlSouth.add(comGroup);
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnUpdate);

		add(jsp, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnInsert.addActionListener(e -> new ProductInsertFrame().setVisible(true));
		btnUpdate.addActionListener(e -> productUpdate());
		
		getProduct("전체");

		comGroup.addItemListener(e -> getProduct((String) comGroup.getSelectedItem()));
		
	}

	public static void main(String[] args) {
		new ProductCareFrame().setVisible(true);
	}

	private void productUpdate() {
		
		for (Component com : pnlProduct.getComponents()) {
			
			if (com instanceof ProductPnl) {
				ProductPnl pnl = (ProductPnl) com;
				
				if (pnl.isSelected) {
					pnl.update();
				}
			}
		}

		iMessage("업데이트 되었습니다.");

	}
	
	private class ProductPnl extends JPanel{
		private boolean isSelected = false;

		JTextField tfNo;
		JTextField tfgroup;
		JTextField tfName;
		JTextField tfPrice;
		JTextField tfStork;
		JTextField tfInfo;

		public ProductPnl(int no, String group, String name, int price, int stork, String info) {
			tfNo = new JTextField(String.valueOf(no));
			tfgroup = new JTextField(group);
			tfName = new JTextField(name);
			tfPrice = new JTextField(String.valueOf(price));
			tfStork = new JTextField(String.valueOf(stork));
			tfInfo = new JTextField(info);

			setLayout(new FlowLayout());

			add(tfNo);
			add(tfgroup);
			add(tfName);
			add(tfPrice);
			add(tfStork);
			add(tfInfo);
			
			for (Component com : getComponents()) {
				JTextField tf = (JTextField) com;
				tf.setEnabled(false);
				tf.setHorizontalAlignment(SwingConstants.CENTER);
				tf.setPreferredSize(new Dimension(135, 25 ));
			}
			
			setPreferredSize(new Dimension(880, 30));
			setBorder(BorderFactory.createLineBorder(Color.black));

			addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					
					if (e.getClickCount() == 2) {
						setBackground(Color.PINK);
						
						isSelected = true;
						
						tfName.setEnabled(true);
						tfPrice.setEnabled(true);
						tfStork.setEnabled(true);
						tfInfo.setEnabled(true);
						
					}
				}
			});
		}
		
		private void update() {
			try {
				dbManager.executeUpdate("update product set p_name = ? , p_price = ? , p_stock = ?, p_explanation = ? where p_no = ?" ,tfName.getText(), tfPrice.getText(), tfStork.getText(), tfInfo.getText(), tfNo.getText());
				
				setBackground(null);
				
				isSelected = false;
				
				tfName.setEnabled(false);
				tfPrice.setEnabled(false);
				tfStork.setEnabled(false);
				tfInfo.setEnabled(false);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getProduct(String group) {
		String[] str = { "상품 번호", "상품 카테고리", "상품명", "상품 가격", "상품 재고", "상품 설명" };
		
		pnlProduct.removeAll();

		JPanel pnl = new JPanel(new GridLayout(1, 6));

		for (String string : str) {
			JLabel lb = new JLabel(string, JLabel.CENTER);
			lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			pnl.add(lb);
		}
		
		pnl.setPreferredSize(new Dimension(880, 20));
		pnlProduct.add(pnl);
		
		group = group == "전체" ? "" : group;
		
		try {

			int cnt = 0;
			
			ResultSet rs = dbManager.executeQuery(
					"select p_no, c_name, p_name,p_price, p_stock,p_explanation FROM product inner join category on product.c_no = category.c_no where c_name like '%"
							+ group + "'");

			while (rs.next()) {
				pnlProduct.add(new ProductPnl(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));
				cnt++;
			}

			pnlProduct.setLayout(new GridLayout(cnt + 1, 1));
			pnlProduct.setPreferredSize(new Dimension(880, (40 * cnt)));

			pnlProduct.updateUI();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
