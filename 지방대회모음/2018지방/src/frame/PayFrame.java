package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Menu;

public class PayFrame extends BaseFrame {

	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"��ǰ��ȣ","ǰ��","����","�ݾ�"},0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	
	private JTable table = new JTable(dtm);
	
	private JTextField tfSelectedName=  new JTextField();
	private JTextField tfCount = new JTextField();
	
	private JTextField tfPw = new JTextField();
	
	private JComboBox<Integer> comNum = new JComboBox<Integer>();
	
	private JLabel lbPrice = new JLabel("0��",JLabel.RIGHT) ;
	
	private JPanel pnlWest = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	
	private int no;
	private int mealNo;
	private int price;
	private String Name;
	
	private JButton btnSelected = null;
	
	private JButton btn1 = new JButton("�Է�");
	private JButton btn2 = new JButton("����");
	private JButton btn3 = new JButton("�ݱ�");
	
	private JPanel pnl = new JPanel(new GridLayout(2, 2));
	
	public PayFrame(int no, String group) {
		super("����", 1100, 500);
		
		this.no = no;
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();
		
		JScrollPane jsp = new JScrollPane(table);
		
		JLabel lbPriceStr=  new JLabel("�Ѱ����ݾ�:");
		JLabel lbTitle = new JLabel(group,JLabel.CENTER);
		
		JLabel lbName = new JLabel("����ǰ��:");
		JLabel lbCount = new JLabel("����:");
		
		lbTitle.setFont(new Font("����",Font.BOLD,30));
		lbPrice.setFont(new Font("����",Font.BOLD,20));
		lbPriceStr.setFont(new Font("����",Font.BOLD,20));
			
		pnlSouth.add(btn1);
		pnlSouth.add(btn2);
		pnlSouth.add(btn3);
		
		pnlCenter.add(pnlWest);
		pnlCenter.add(lbPriceStr);
		pnlCenter.add(lbPrice);
		pnlCenter.add(jsp);
		pnlCenter.add(pnlSouth);
		pnlCenter.add(lbName);
		pnlCenter.add(lbCount);
		pnlCenter.add(tfSelectedName);
		pnlCenter.add(tfCount);
		
		btn1.setPreferredSize(new Dimension(120, 35));
		btn2.setPreferredSize(new Dimension(120, 35));
		btn3.setPreferredSize(new Dimension(120, 35));
	
		pnl.add(new JLabel("�����ȣ "));
		pnl.add(comNum);
		pnl.add(new JLabel("��й�ȣ "));
		pnl.add(tfPw);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(lbTitle,BorderLayout.NORTH);
		
		pnlWest.setBounds(10, 10, 580, 400);
		
		lbPriceStr.setBounds(600, 40, 150, 30);
		lbPrice.setBounds(900, 40, 150, 30);
		
		jsp.setBounds(600, 75, 460, 250);
		
		lbName.setBounds(600, 330, 60, 30);
		tfSelectedName.setBounds(665, 330, 270, 25);
		lbCount.setBounds(940, 330, 40, 30);
		tfCount.setBounds(985, 330, 60, 25);
		
		pnlSouth.setBounds(625, 360, 400, 40);
	
		getinfo();

		tfSelectedName.setEditable(false);
		
		btn1.addActionListener(e -> insertMenu());
		btn2.addActionListener(e -> menuOrder());
		btn3.addActionListener(e-> dispose());

		
		try {
			ResultSet rs = dbManager.executeQuery("select memberNo from member");
			while (rs.next()) {
				comNum.addItem(rs.getInt(1));
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					for (Component com : pnlWest.getComponents()) {
						if (Integer.valueOf(com.getName()) == table.getValueAt(table.getSelectedRow(), 0)) {
							com.setEnabled(true);
						}
					}
					
					dtm.removeRow(table.getSelectedRow());
					updateTotalPrice();
				}
			}
		});
		
	}
	
	private void menuOrder() {
		
		if(table.getRowCount() == 0) {
			eMessage("�޴��� �������ּ���.");
		}else {
			int yn = JOptionPane.showConfirmDialog(null, pnl, "������ ����", JOptionPane.YES_NO_OPTION);
			
			if(yn == JOptionPane.YES_OPTION) {
				try {
					ResultSet rs = dbManager.executeQuery("select* from member where memberNo = ? and passWd = ?", comNum.getSelectedItem(),tfPw.getText().toString());
					if (rs.next()) {
						iMessage("������ �Ϸ�Ǿ����ϴ�. \n�ı��� ����մϴ�." );
						
						ArrayList<Menu> list = new ArrayList<>();
						
						for (int i = 0; i < dtm.getRowCount(); i++) {
							
							int mealNo = Integer.valueOf(table.getValueAt(i, 0).toString());
							int orderlistCount = Integer.valueOf(table.getValueAt(i, 2).toString());
							int amount =  Integer.valueOf(table.getValueAt(i, 3).toString());
							
							list.add(new Menu(table.getValueAt(i, 1).toString(), orderlistCount, mealNo));
							
							dbManager.executeUpdate("insert into orderlist values(0,?,?,?,?,?,?)", no,mealNo,comNum.getSelectedItem(),orderlistCount,amount,LocalDateTime.now().toString());
							dbManager.executeUpdate("update meal set maxCount = maxcount - ? where mealNo = ?",orderlistCount,mealNo);
							
						}
						
							openFrame(new TicketPrintFrame(list,no,(int) comNum.getSelectedItem()));
					}else {
						eMessage("�н����尡 ��ġ���� �ʽ��ϴ�.");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	public static void main(String[] args) {
		new PayFrame(1,"�ѽ�").setVisible(true);
		
	}
	
	private void getinfo() {
		pnlWest.removeAll();
		
		try {
			
			ResultSet rs = dbManager.executeQuery("select mealName, price,cuisineName,mealNo from meal"
					+ " inner join cuisine on cuisine.cuisineNo = meal.cuisineNo"
					+ " where meal.cuisineNo = ? and maxCount >= 1 and todayMeal = 1", no);
			
			while (rs.next()) {
				addButton(rs.getString(1), rs.getInt(2),rs.getInt(4));
			}
			
			System.out.println(no);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addButton(String name, int price, int mealNo) {
		JButton btn = new JButton(String.format("%s(%d)", name, price));
		
		btn.setPreferredSize(new Dimension(110, 60));
		btn.setName(String.valueOf(mealNo)); // ��ư�� no ������ ����� 
		
		pnlWest.add(btn);
		
		btn.addActionListener(e -> {
			
			tfSelectedName.setText(name);
			
			this.Name = name;
			this.price = price;
			this.mealNo = mealNo;
			this.btnSelected = btn;
		});
	}
	
	public void insertMenu() {
		if (tfSelectedName.getText().isEmpty()) {
			eMessage("�޴��� �������ּ���.");
			return;
		}
		
		String count = tfCount.getText();
		
		if(count.isEmpty() || count.matches("^[0-9]+$") == false || count.equals("0")) {
			eMessage("������ �Է����ּ���.");
		
		}else {
			try {
				ResultSet rs = dbManager.executeQuery("select * From meal where mealNo = ? and maxCount <= ?",mealNo,tfCount.getText().toString());
				
				if(rs.next()) {
					eMessage("�������ɼ����� �����մϴ�.");
					return;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int amount = price * Integer.valueOf(count);
			
			dtm.addRow(new Object[] {
				this.mealNo, this.Name, count, amount
			});
			
			this.btnSelected.setEnabled(false); // ���� �س��� ������ ��ư !
			updateTotalPrice();
			
			tfSelectedName.setText("");
			tfCount.setText("");
		}
	}
	
	private void updateTotalPrice() {
		
		int total = 0;
		
		for (int i = 0; i < dtm.getRowCount(); i++) {
			total += Integer.valueOf(table.getValueAt(i, 3).toString());
		}
		
		lbPrice.setText(String.format("%,d��", total));
	}
}

