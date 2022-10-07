package panel;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import frame.InsertAdFrame;

public class AdPanel extends BasePanel {

	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"±¤°í¹øÈ£", "±¤°íÀÌ¸§" }, 0);
	private JTable table = new JTable(dtm);
	
	private int[] columnSize = {100, 400};
	
	public AdPanel() {
		setLayout(null);
		
		JLabel lbAd = new JLabel("±¤°í°ü¸®");
		
		JScrollPane jsp = new JScrollPane(table);
		
		JButton btnInsert = createButton("±¤°í Ãß°¡");
		JButton btnDelete = createButton("±¤°í Á¦°Å");
		
		lbAd.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		
		add(lbAd);
		add(jsp);
		add(btnInsert);
		add(btnDelete);
		
		lbAd.setBounds(30, 20, 250, 30);
		jsp.setBounds(30, 60, 830, 430);
		btnInsert.setBounds(690, 500, 80, 35);
		btnDelete.setBounds(780, 500, 80, 35);
		
		btnInsert.addActionListener(e -> insertAd());
		btnDelete.addActionListener(e -> deleteAd());
		
		for (int i = 0; i < columnSize.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnSize[i]);
			table.getColumnModel().getColumn(i).setCellRenderer(centerCR);
		}
		
		getAd();
	}
	
	private void getAd() {
		dtm.setRowCount(0);
		
		try {
			ResultSet rs = dbManager.executeQuery("SELECT * FROM ad WHERE seller = ?", sellerSerial);
			
			while(rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2)
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertAd() {
		InsertAdFrame frame = new InsertAdFrame();
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				getAd();
			}
		});
		
		frame.setVisible(true);
	}
	
	private void deleteAd() {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			eMessage("»èÁ¦ÇÒ ±¤°í¸¦ ¼±ÅÃÇÏ¿© ÁÖ¼¼¿ä.");
			return;
		}
		
		try {
			dbManager.executeUpdate("DELETE FROM ad WHERE serial = ?", table.getValueAt(row, 0));
			
			iMessage("±¤°í°¡ Á¦°ÅµÇ¾ú½À´Ï´Ù.");
			
			getAd();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
