package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReservationListFrame extends BaseFrame {
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"","진료과","의사","진료날짜","시간"}, 0);
	private JTable table = new JTable(dtm);
	
	public ReservationListFrame() {
		super("진료예약현황", 600, 500);
	
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JScrollPane jsp = new JScrollPane(table);
		
		JLabel lbTitle = new JLabel(String.format("%s님 진료예약현황", p_name),JLabel.CENTER);
		
		JButton btnCancel = new JButton("예약취소");
		JButton btnExit = new JButton("닫기");
		
		lbTitle.setFont(new Font("굴림",Font.BOLD,20));
		
		pnlSouth.add(btnCancel);
		pnlSouth.add(btnExit);
		
		add(lbTitle,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
	
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		table.getColumn("진료날짜").setPreferredWidth(180);
		
		table.getColumn("").setMinWidth(0);
		table.getColumn("").setMaxWidth(0);
		
		getinfo();
		
		btnCancel.addActionListener(e -> cancel());
		btnExit.addActionListener(e -> openFrame(new MainFrame()));
		
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
	
	public static void main(String[] args) {
		new ReservationListFrame().setVisible(true);
	}

	private void cancel() {
		
		if(table.getSelectedRow() == -1) {
			eMessage("삭제할 예약을 선택해주세요.");
		}else {
			
			iMessage("취소되었습니다.");
			
			int row = (int) table.getValueAt(table.getSelectedRow(), 0);
			
			try {
				dbManager.executeUpdate("delete from reservation where r_no = ?", row);
		
				getinfo();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void getinfo() {
		
		dtm.setRowCount(0);
		
		try {
			
			ResultSet rs = dbManager.executeQuery("select r_no, r_section, d_name,r_date,r_time from reservation inner join doctor on reservation.d_no = doctor.d_no where p_no = ?", p_no);
			//and r_date >= now() 조건도 넣어야함..
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5)
				});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
