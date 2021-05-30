package frame;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RentInfoFrame extends baseFrame{

	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"�뿩��ȣ","ȸ���̸�","��ȭ��ȣ","�����̸�","������ȣ","��¥"},0);
	private JTable table = new JTable(dtm);
	
	public RentInfoFrame() {
		super("�뿩����", 440, 500);
		
		JPanel pnlNorth = new JPanel();
		
		JButton btnExit = new JButton("���ư���");
		
		JScrollPane jsp = new JScrollPane(table);
		
		pnlNorth.add(btnExit);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);
		
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
	}
	
	public static void main(String[] args) {
		new RentInfoFrame().setVisible(true);
	}
}
