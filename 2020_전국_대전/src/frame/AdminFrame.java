package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Wedding;

public class AdminFrame extends BaseFrame{
	
	private JTextField tfName = new JTextField(17);
	
	private JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));

	public AdminFrame() {
		super("������", 850, 450);
		
		JPanel pnlNorth = new JPanel();
	
		JScrollPane jsp = new JScrollPane(pnl);
		
		JButton btnSearch = new JButton("�˻�"); 
		JButton btnInsert = new JButton("���");
		JButton btnAllShow = new JButton("��ü����");
		JButton btnExit = new JButton("�ݱ�");
	
		pnlNorth.add(tfName);
		pnlNorth.add(btnSearch);
		pnlNorth.add(btnInsert);
		pnlNorth.add(btnAllShow);
		pnlNorth.add(btnExit);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);
		
		getWedding();
		
		btnSearch.addActionListener(e ->{
			if(tfName.getText().isEmpty()) {
				eMessage("�˻�� �Է����ּ���.");
				return;
			}
			getWedding();
		});
		
		btnInsert.addActionListener(e -> {
			openFrame(new InsertFrame());
		});
		
		btnAllShow.addActionListener(e -> {
			tfName.setText("");
			getWedding();	
		});
		
		btnExit.addActionListener(e -> {
			openFrame(new MainFrame());
		});
	}
	
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
	
	private void addWedding(int wNo, String name,String address, String weddingType, String mealType, int people, int price) {
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel pnlCenter = new JPanel(new GridLayout(6, 1));
		
		JLabel lbImg = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("datafile/ȣ���̹���/%s/%s1.jpg", name,name)).getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		
		pnlCenter.add(new JLabel(String.format("�̸� :%s", name)));
		pnlCenter.add(new JLabel(String.format("�ּ� :%s", address)));
		pnlCenter.add(new JLabel(String.format("�������� :%s", weddingType)));
		pnlCenter.add(new JLabel(String.format("�Ļ����� :%s", mealType)));
		pnlCenter.add(new JLabel(String.format("�����ο� :%d", people)));
		pnlCenter.add(new JLabel(String.format("Ȧ���� :%,d��", price)));
		
		panel.add(lbImg,BorderLayout.NORTH);
		panel.add(pnlCenter,BorderLayout.CENTER);
		
		lbImg.setPreferredSize(new Dimension(110, 120));
		panel.setPreferredSize(new Dimension(250, 300));
		pnl.add(panel);

		lbImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.println(weddingType + mealType);
				openFrame(new UpdateFrame(new Wedding(wNo, name, address, weddingType, mealType, people, price)));
				
			}
		});
	}
	
	private void getWedding() {
		
		String sql = "select h.wNo, wname, wadd, group_concat(distinct(wty.tyname)) as tyname, group_concat(distinct(mty.mname)) as mname, wpeople, wprice from wedding.weddinghall as h"
				+ " inner join w_m on w_m.wno = h.wNo"
				+ " inner join  mealtype as mty on w_m.mNo = mty.mNo"
				+ " inner join w_ty on w_ty.wno = h.wNo"
				+ " inner join  weddingtype as wty on w_ty.tyNo = wty.tyNo "
				+ "group by h.wNo having wname like '%" + tfName.getText() + "%'";
		
		try {
			ResultSet rs = dbManager.executeQuery(sql);
			
			int cnt = 0;
			
			pnl.removeAll();
			
			while (rs.next()) {
				addWedding(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
				
				cnt++;
			}
			
			if (cnt == 0) {
				eMessage("�˻��� �����Ͱ� �����ϴ�. �˻�� �ùٸ��� �Է����ּ���.");
			}
			
			pnl.setPreferredSize(new Dimension(250, (int) (Math.ceil(cnt / 3f) * 300)));
			pnl.repaint();
			pnl.revalidate();
			
		} catch (SQLException e) { 
			
			e.printStackTrace();
		}
	}
}

