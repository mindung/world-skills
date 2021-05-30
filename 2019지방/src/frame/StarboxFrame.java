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
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StarboxFrame extends baseFrame {

	private JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));

	private JLabel lbImg = new JLabel();

	private JComboBox<Integer> comCount = new JComboBox<Integer>();
	private JComboBox<String> comSize = new JComboBox<String>(new String[] { "M", "L" });

	private JTextField tfMenu = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfAmount = new JTextField();

	private int m_no = 0;
	
	private String o_group = "";

	private JLabel lbGrade = new JLabel(u_grade);
	private JLabel lbPoint = new JLabel(String.valueOf(u_point));
	
	public StarboxFrame() {

		super("STARTBOX", 800, 600);

		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlWest = new JPanel(new GridLayout(3, 1, 0, 5));

		JPanel pnl1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnl2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JPanel pnlCenter2 = new JPanel(new GridLayout(5, 2, 10, 10));
		JPanel pnlSouth = new JPanel();

		JButton btn1 = new JButton("���ų���");
		JButton btn2 = new JButton("��ٱ���");
		JButton btn3 = new JButton("�α� ��ǰ Top5");
		JButton btn4 = new JButton("Logout");

		JButton btn11 = new JButton("����");
		JButton btn22 = new JButton("Ǫ��");
		JButton btn33 = new JButton("��ǰ");

		JButton btnShopping = new JButton("��ٱ��Ͽ� ���");
		JButton btnOrder = new JButton("�����ϱ�");
		JScrollPane jsp = new JScrollPane(pnlMenu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pnl1.add(new JLabel("ȸ���� : "));
		pnl1.add(new JLabel(u_name));
		pnl1.add(new JLabel(" / ȸ�����: "));
		pnl1.add(lbGrade);
		pnl1.add(new JLabel(" / �� ���� ����Ʈ : "));
		pnl1.add(lbPoint);

		pnl2.add(btn1);
		pnl2.add(btn2);
		pnl2.add(btn3);
		pnl2.add(btn4);

		pnlNorth.add(pnl1, BorderLayout.NORTH);
		pnlNorth.add(pnl2, BorderLayout.SOUTH);

		pnlWest.add(btn11);
		pnlWest.add(btn22);
		pnlWest.add(btn33);

		pnlCenter.add(pnlWest);
		pnlCenter.add(jsp);

		pnlWest.setBounds(0, 0, 70, 110);
		jsp.setBounds(70, 0, 700, 480);

		pnlCenter2.add(new JLabel("�ֹ��޴�:", JLabel.RIGHT));
		pnlCenter2.add(tfMenu);
		pnlCenter2.add(new JLabel("����:", JLabel.RIGHT));
		pnlCenter2.add(tfPrice);
		pnlCenter2.add(new JLabel("����:", JLabel.RIGHT));
		pnlCenter2.add(comCount);
		pnlCenter2.add(new JLabel("������:", JLabel.RIGHT));
		pnlCenter2.add(comSize);
		pnlCenter2.add(new JLabel("�ѱݾ�:", JLabel.RIGHT));
		pnlCenter2.add(tfAmount);

		pnlSouth.add(btnShopping);
		pnlSouth.add(btnOrder);

		pnlCenter.add(lbImg);
		pnlCenter.add(pnlCenter2);
		pnlCenter.add(pnlSouth);

		lbImg.setBounds(800, 100, 130, 130);
		pnlCenter2.setBounds(850, 100, 300, 200);
		pnlSouth.setBounds(820, 330, 300, 40);

		lbImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbImg.setPreferredSize(new Dimension(250, 250));

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);

		btn1.addActionListener(e -> openFrame(new OrderlistFrame()));
		btn2.addActionListener(e -> openFrame(new shoppingFrame()));
		btn3.addActionListener(e -> openFrame(new chartFrame()));
		btn4.addActionListener(e -> openFrame(new LoginFrame()));

		btn11.addActionListener(e -> getMenu("����"));
		btn22.addActionListener(e -> getMenu("Ǫ��"));
		btn33.addActionListener(e -> getMenu("��ǰ"));

		tfMenu.setEditable(false);
		tfAmount.setEditable(false);
		tfPrice.setEditable(false);

		for (int i = 1; i <= 10; i++) {
			comCount.addItem(i);
		}
		
		getMenu("����");
		

		comCount.addItemListener(e -> {
			getAmount();
		});

		comSize.addItemListener(e -> {
			getAmount();
		});
		
		btnShopping.addActionListener(e ->{
			addShopping();
		});
		
		btnOrder.addActionListener(e -> addOrder());
		
		
	}

	public static void main(String[] args) {
		new StarboxFrame().setVisible(true);

	}

	private void getMenu(String group) {

		o_group = group; // o_group ���� �� group �� �ֱ�
		
		pnlMenu.removeAll();

		try {

			int cnt = 0; // �������� ����
			ResultSet rs = dbmanager.executeQuery("select * from menu where m_group = ?", group);

			while (rs.next()) {
				addMenu(rs.getInt(1), rs.getString(3), rs.getInt(4));
				cnt++; // �ݺ��� ���鼭 ���� ���� ,,?
			}

			pnlMenu.setPreferredSize(new Dimension(680, 240 * (cnt / 3 + (cnt % 3 == 0 ? 0 : 1))));
			pnlMenu.validate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		setFrameSize(800, 600);

//		if(group.equals("��ǰ")) {
//			comCount.setEnabled(false);
//		}else {
//			comCount.setEnabled(true);
//		}
//		
//		comCount.setEnabled(group.equals("��ǰ") ? false : true);
//		
//		comCount.setEnabled(group.equals("��ǰ") == false); // ��ǰ�� ��� ture , �װſ� �ݴ�� false (!)
		comCount.setEnabled(!group.equals("��ǰ"));

	}

	private void addMenu(int no, String name, int price) {

		JPanel pnl = new JPanel(new BorderLayout());

		JLabel lbName = new JLabel(name, JLabel.CENTER);
		JLabel lbImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("./DataFiles/�̹���/%s.jpg", name)).getScaledInstance(220, 200, Image.SCALE_SMOOTH)));
		
		lbImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		pnl.setPreferredSize(new Dimension(220, 230));
		pnl.add(lbImage, BorderLayout.NORTH);
		pnl.add(lbName, BorderLayout.SOUTH);

		pnlMenu.add(pnl);

		lbImage.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				setFrameSize(1200, 600);

				m_no = no;
				
				tfMenu.setText(name);
				tfPrice.setText(String.valueOf(price));

				comCount.setSelectedIndex(0);
				comSize.setSelectedIndex(0);
				
				lbImg.setIcon(new ImageIcon(
						Toolkit.getDefaultToolkit().getImage(String.format("./DataFiles/�̹���/%s.jpg", name))
								.getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
				
				getAmount();
				
			}
		});

	}

	private void setFrameSize(int width, int height) {
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
	}

	private void getAmount() {
		int price = Integer.valueOf(tfPrice.getText());
		int count = (int) comCount.getSelectedItem();
		String size = (String) comSize.getSelectedItem();
		
		if(size.equals("L")) {
			
			price = price + 1000;
			System.out.println(price);
			
		}
		
		double amount = price * count; // int���� ���� ū ��
		
		if(u_grade.equals("Bronze")) {
			amount = amount * 0.97;
			
		}else if (u_grade.equals("Silver")){
			amount = amount * 0.95;

		}else if (u_grade.equals("Gold")){
			amount = amount * 0.9;
			
		}
		
		tfAmount.setText(String.valueOf((int)amount));
	}
	
	private void addShopping() {
		
		int price = Integer.valueOf(tfPrice.getText());
		int count = (int) comCount.getSelectedItem();
		String size = (String) comSize.getSelectedItem();
		int amount = Integer.valueOf(tfAmount.getText());
				
		if(size.equals("L")) {
			amount = amount + 1000;
			
		}
		
		try {
			
			dbmanager.executeUpate("insert into shopping values(0,?,?,?,?,?)", m_no,price,count,size,amount);
			Imessage("��ٱ��Ͽ� ��ҽ��ϴ�.");
			
			setFrameSize(800, 600);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addOrder() {
		
		int price = Integer.valueOf(tfPrice.getText());
		int count = (int) comCount.getSelectedItem();
		String size = (String) comSize.getSelectedItem();
		int amount = Integer.valueOf(tfAmount.getText());
		LocalDate date = LocalDate.now();
		
		if(u_point < amount) {
			
			Imessage("���ŵǾ����ϴ�.");
			
			u_point += (int) (amount * 0.05);
			
			try {
				dbmanager.executeUpate("insert into orderlist values(0,?,?,?,?,?,?,?,?)", date,u_no,m_no,o_group,size,price,count,amount);
				
				dbmanager.executeUpate("update user set u_point = ? where u_no = ?", u_point, u_no);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			
			int yn = JOptionPane.showConfirmDialog(null, String.format("ȸ������ �� ����Ʈ: %S\n����Ʈ�� �����Ͻðڽ��ϱ�?\n(�ƴϿ��� Ŭ�� �� ���ݰ����� �˴ϴ�)", u_point), "��������", JOptionPane.YES_NO_OPTION);
			
			if(yn == JOptionPane.YES_OPTION) { 
				
				u_point -= amount; // u_point - amount 
				
				try {
					
					dbmanager.executeUpate("update user set u_point = ? where u_no = ?", u_point, u_no);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				Imessage(String.format("����Ʈ�� ���� �Ϸ�Ǿ����ϴ�. \n ���� ����Ʈ : %s", u_point));
			}else if(yn == JOptionPane.NO_OPTION){ // �׳� â�� �ݾ��� ��츦 �������ش�..

				Imessage("���ŵǾ����ϴ�.");
				
				u_point += (int) (amount * 0.05);
				
				try {
					dbmanager.executeUpate("insert into orderlist values(0,?,?,?,?,?,?,?,?)", date,u_no,m_no,o_group,size,price,count,amount);
					
					dbmanager.executeUpate("update user set u_point = ? where u_no = ?", u_point, u_no);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
		}
		
		lbPoint.setText(String.valueOf(u_point));
		
		try {
			ResultSet rs = dbmanager.executeQuery("select sum(o_amount) from orderlist where u_no = ? ",u_no);
		
			if(rs.next()) {
				
				int sum = rs.getInt(1);
				String grade = "";
				
				if(u_grade.equals("�Ϲ�") && sum >= 300000) {
					grade = "Bronze";
				}else if (u_grade.equals("Bronze") && sum >= 500000) {
					grade = "Silver";
				}else if (u_grade.equals("Silver") && sum >= 800000) {
					grade = "Gold";
				}else {
					return;
				}
				
				u_grade = grade; // ����� ������Ʈ�� �� �Ǿ��־ iMessage�� �� �� ��. u_grade = grade�� ������Ʈ �����ش�.
				
				dbmanager.executeUpate("update user set u_grade = ? where u_no = ?", grade, u_no);
				Imessage(String.format("�����մϴ�.\n ȸ������ ����� %s�� �±��ϼ̽��ϴ�.", grade));
				
				lbGrade.setText(u_grade);
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
	
