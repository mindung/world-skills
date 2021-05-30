package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class sickRoomFrame extends BaseFrame{
	
	private int s_no;
	
	private JTextField tfDate = new JTextField(15);
	private JTextField tfRoom = new JTextField(6);
	private JTextField tfRoomNo = new JTextField(6);
	
	private JComboBox<Integer> comFloor = new JComboBox<Integer>();
	
	private JPanel pnlRoom = new JPanel();
	
	public sickRoomFrame() {
		super("�Կ�", 1000, 300);
		
		selectedBed = "";
		
		JPanel pnlNorth = new JPanel(new GridLayout(1, 5, 1, 5));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnSearch = new  JButton("�˻�");
		JButton btnReserve = new  JButton("����");
		
		pnlNorth.add(new JLabel("�Կ���¥",JLabel.CENTER));
		pnlNorth.add(tfDate);
		pnlNorth.add(new JLabel("��",JLabel.CENTER));
		pnlNorth.add(comFloor);
		pnlNorth.add(btnSearch);
		
		pnlSouth.add(new JLabel("ȣ��"));
		pnlSouth.add(tfRoom);
		pnlSouth.add(new JLabel("ħ���ȣ"));
		pnlSouth.add(tfRoomNo);
		pnlSouth.add(btnReserve);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlRoom,BorderLayout.CENTER);
		
		tfDate.setText(LocalDate.now().toString());
		tfDate.setEnabled(false);
		
		for (int i = 1; i <= 6; i++) {
			comFloor.addItem(i);
		}
	
		btnReserve.addActionListener(e -> reserve());
		btnSearch.addActionListener(e -> search());
		
		search();
	}
	
	public static void main(String[] args) {
		new sickRoomFrame().setVisible(true);
	}
	
	private void reserve() {

	}
	
	private void search() {
		
		int floor = (int) comFloor.getSelectedItem();
		
		pnlRoom.removeAll();
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM hospital.sickroom where s_room like '" + floor + "%'");
			
			
			int i = 0; // ȣ�� ���� �� 
			
			while (rs.next()) {
				
				JPanel panel = new JPanel(new BorderLayout());
				JLabel label = new JLabel(String.format("%dȣ", rs.getInt(3)),JLabel.CENTER);
				JButton button = new JButton();
				
				panel.add(label,BorderLayout.NORTH);
				panel.add(button,BorderLayout.CENTER);
				
				panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				
				button.setName(String.format("%s,%s", rs.getInt(1),rs.getInt(3)));
				button.addActionListener(e -> {
					
					JButton btn = (JButton) e.getSource();
					
					String[] info = btn.getName().split(",");
					
					s_no = Integer.valueOf(info[0]);
					
					tfRoom.setText(info[1]);
					
					System.out.println(s_no);
					
					BedFrame frame = new BedFrame(s_no);
					
					frame.addWindowListener(new WindowAdapter() {
						
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							tfRoomNo.setText(selectedBed);
						};
						
					});
					
					frame.setVisible(true);
					
				});
				
				pnlRoom.add(panel);
				
				i++; // ȣ�� ������ ������.
				
			}
			
			pnlRoom.setLayout(new GridLayout(1, i)); // ȣ�� ���� ��ŭ
			pnlRoom.revalidate();
			
			setSize(i * 100 , 300); // 1�� ���� 100 * ���� ��ŭ ���� �� ������
			
			setLocationRelativeTo(null);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
