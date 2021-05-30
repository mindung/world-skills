package frame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BookFrame extends baseFrame {

	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"������ȣ","����","����","���ǻ�","����","���⿩��"},0);
	private JTable table = new JTable(dtm);
	
	public BookFrame() {
		super("�������", 490, 550);
			
		JPanel pnlCenter = new JPanel();
		JPanel pnlNorth = new JPanel();
		
		JScrollPane jsp = new JScrollPane(table);
		
		JButton btn1 = new JButton("���ΰ�ħ");
		JButton btn2 = new JButton("���");
		JButton btn3 = new JButton("��ȸ");
		JButton btn4 = new JButton("����");
		JButton btn5 = new JButton("����");
		JButton btn6 = new JButton("���ư���");
		
		pnlNorth.add(btn1);
		pnlNorth.add(btn2);
		pnlNorth.add(btn3);
		pnlNorth.add(btn4);
		pnlNorth.add(btn5);
		pnlNorth.add(btn6);
		
		pnlCenter.add(jsp);
		pnlCenter.setBorder(BorderFactory.createTitledBorder("ȸ�����"));
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlNorth,BorderLayout.NORTH);
		
		getBook();

		btn1.addActionListener(e -> getBook());
		btn2.addActionListener(e -> new BookInsertFrame().setVisible(true));
		btn3.addActionListener(e -> openSearchBookFrame());
		btn4.addActionListener(e -> new BookUpdateFrame().setVisible(true));
		btn5.addActionListener(e -> new BookDeleteFrame().setVisible(true));
		btn6.addActionListener(e -> openFrame(new MainFrame()));
		
	}

	public static void main(String[] args) {
		new BookFrame().setVisible(true);
	}
	
	private void getBook() {
		dtm.setRowCount(0);
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from lib");
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void openSearchBookFrame() {
		
		BookSearchFrame frame = new BookSearchFrame();
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
			
				if(name.isEmpty() == false) {
					dtm.setRowCount(0);
					
					try {
						ResultSet rs = dbManager.executeQuery("select * from lib where lib_name =? ", name);
						
						if(rs.next()) {
							dtm.addRow(new Object[] {
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6),
							});
						}
						name = "";
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		frame.setVisible(true);
	}
}
