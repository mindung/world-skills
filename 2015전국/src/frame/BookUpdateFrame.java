package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookUpdateFrame extends baseFrame{
	
	private JButton btnSearch = new JButton("�˻�");
	private JButton btnUpdate = new JButton("����");
	private JButton btnExit = new JButton("���ư���");
	
	private JTextField tfSearch = new JTextField(7);
	private JTextField tfcode = new JTextField(7);
	private JTextField tfname = new JTextField(8);
	private JTextField tfauthor = new JTextField(8);
	private JTextField tfpublisher = new JTextField(8);
	private JTextField tfprice = new JTextField(8);
	private JTextField tfState = new JTextField(8);
	
	public BookUpdateFrame() {
		super("�������� ����", 300, 250);

		JPanel pnlNorth = new JPanel();
		JPanel pnlSouth = new JPanel();
		JPanel pnlCenter = new JPanel(new GridLayout(6, 2));
		
		pnlNorth.add(new JLabel("ȸ���ֹι�ȣ:"));
		pnlNorth.add(tfSearch);
		pnlNorth.add(btnSearch);
		
		pnlCenter.add(new JLabel("������ȣ"));
		pnlCenter.add(tfcode);
		pnlCenter.add(new JLabel("����"));
		pnlCenter.add(tfname);
		pnlCenter.add(new JLabel("����"));
		pnlCenter.add(tfauthor);
		pnlCenter.add(new JLabel("���ǻ�"));
		pnlCenter.add(tfpublisher);
		pnlCenter.add(new JLabel("����"));
		pnlCenter.add(tfprice);
		pnlCenter.add(new JLabel("�뿩����"));
		pnlCenter.add(tfState);
		
		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnExit);
		
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlNorth,BorderLayout.NORTH);
		
		btnSearch.addActionListener(e -> getInfo());
		btnUpdate.addActionListener(e -> update());
		btnExit.addActionListener(e -> dispose());
		
	}
	
	public static void main(String[] args) {
		new BookUpdateFrame().setVisible(true);
	}

	private void getInfo() {
		
		String info = tfSearch.getText();
		
		ResultSet rs;
		try {
			rs = dbManager.executeQuery("select * from lib where lib_code = ?", info);
		
			if(rs.next()) {
				tfcode.setText(rs.getString(1));
				tfname.setText(rs.getString(2));
				tfauthor.setText(rs.getString(3));
				tfpublisher.setText(rs.getString(4));
				tfprice.setText(rs.getString(5));
				tfState.setText(rs.getString(6));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update() {
		String search = tfSearch.getText();
		String code = tfcode.getText();
		String name = tfname.getText();
		String author = tfauthor.getText();
		String publisher = tfpublisher.getText();
		String price  = tfprice.getText();
		String state = tfState.getText();
		
		if(name.isEmpty() || name.isEmpty()|| author.isEmpty()|| publisher.isEmpty()||price.isEmpty()||state.isEmpty()) {
			eMessage("��ĭ�� �����մϴ�.");
		}else {
			
			try {
				
				dbManager.executeUpdate("update lib set lib_code = ? , lib_name = ? , lib_author = ? ,lib_publisher =?, lib_price = ? , lib_state = ? where lib_code=? ", code,name,author,publisher,price,state,search);
				
				iMessage("������ �Ϸ�Ǿ����ϴ�.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	
	
}

