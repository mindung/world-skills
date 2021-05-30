package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;

public class munuUpdateFrame extends baseFrame{
	
	private JTextField tfMenu = new JTextField(24);
	private JTextField tfName = new JTextField(10);
	private JTextField tfPrice = new JTextField(10);
	
	private JComboBox<String> comFind = new JComboBox<>(new String[] {"전체","음료","푸드","상품"});
	private JComboBox<String> comGroup = new JComboBox<>(new String[] {"음료","푸드","상품"});
	
	private DefaultTableModel dtm = new DefaultTableModel();
	private JTable table = new JTable(dtm);
	
	private JLabel lbImage = new JLabel();
	
	public munuUpdateFrame() {
		super("메뉴수정", 850, 350);
		
		JPanel pnlWest = new JPanel(null);
		JPanel pnlSouth = new JPanel();
		JPanel pnlNorth = new JPanel();
		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlWestt = new JPanel(null);
		
		JLabel lbGroup = new JLabel("분류 ", JLabel.LEFT);
		JLabel lbMunu = new JLabel("메뉴명", JLabel.LEFT);
		JLabel lbPrice = new JLabel("가격", JLabel.LEFT);

		JButton btnFind = new JButton("찾기");
		JButton btnDelete = new JButton("삭제");
		JButton btnUpdate = new JButton("수정");
		JButton btnExit = new JButton("취소");
		JButton btnImageInsert = new JButton("사진등록");
		
		JScrollPane jsp = new JScrollPane(table);
		dtm.setColumnIdentifiers(new String[] {"","분류","메뉴명","가격"});
		
		pnlNorth.add(new JLabel("검색"));
		pnlNorth.add(comFind);
		pnlNorth.add(tfMenu);
		pnlNorth.add(btnFind);
		
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnExit);
		
		pnlCenter.add(pnlNorth,BorderLayout.NORTH);
		pnlCenter.add(jsp,BorderLayout.CENTER); // 왼쪽
		
	
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnExit);
		
		pnlWest.setPreferredSize(new Dimension(500, 300));
		btnImageInsert.setPreferredSize(new Dimension(40, 10));

		pnlWestt.add(lbGroup);
		pnlWestt.add(comGroup);
		pnlWestt.add(lbMunu);
		pnlWestt.add(tfName);
		pnlWestt.add(lbPrice);
		pnlWestt.add(tfPrice);

		pnlWestt.add(lbImage);
		pnlWestt.add(btnImageInsert);
		pnlWestt.add(pnlSouth);
	
		pnlWest.add(pnlWestt);
		
		lbGroup.setBounds(5, 50, 50, 25);
		lbMunu.setBounds(5, 110, 50, 25);
		lbPrice.setBounds(5, 170, 50, 25);
		comGroup.setBounds(60, 50, 50, 25);
		tfName.setBounds(60, 110, 140, 25);
		tfPrice.setBounds(60, 170, 140, 25);

		lbImage.setBounds(220, 40, 125, 135);
		btnImageInsert.setBounds(220, 175, 125, 30);

		pnlSouth.setBounds(70, 230, 220, 40);
		pnlWestt.setBounds(550, 100, 500, 500);

		lbImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(pnlCenter,BorderLayout.WEST);		
		add(pnlWestt,BorderLayout.CENTER);
				
		btnFind.addActionListener(e -> {
			search();
		});
		
		btnDelete.addActionListener(e -> {
			
		});
		
		btnUpdate.addActionListener(e -> {
			
		});
		
		btnExit.addActionListener(e -> openFrame(new adminFrame()));
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {

				comGroup.setSelectedItem(table.getValueAt(table.getSelectedRow(), 1));
				tfName.setText((String) table.getValueAt(table.getSelectedRow(), 2));
				tfPrice.setText(String.valueOf((int) table.getValueAt(table.getSelectedRow(), 3)));
				lbImage.setIcon(new ImageIcon(
						Toolkit.getDefaultToolkit().getImage(String.format("./DataFiles/이미지/%s.jpg", tfName.getText()))
								.getScaledInstance( 125, 135, Image.SCALE_SMOOTH)));
			};
		});
		
		search();
	}
	
	public static void main(String[] args) {
		new munuUpdateFrame().setVisible(true);

	}
	
	private void search() {
	
		String name = tfMenu.getText();
		String group = (String) comFind.getSelectedItem();
		dtm.setRowCount(0);
		
		try {
		
			if(comFind.getSelectedIndex() == 0) {
				group = "";
				
			}
			
			ResultSet rs = dbmanager.executeQuery("select * from menu where m_name like '%" + name + "%' and m_group like '%" + group + "%'");

			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getInt(4)
				});
				
//				search();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	
}


