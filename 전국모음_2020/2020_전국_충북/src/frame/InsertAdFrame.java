package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import panel.BasePanel;

public class InsertAdFrame extends BaseFrame {

	private JTextField tfPath = new JTextField();
	private JTextField tfName = new JTextField();
	private JLabel lbImage = new JLabel();
	
	public InsertAdFrame() {
		super("광고 추가", 350, 480);
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel pnlCenter = new JPanel();
		JPanel pnlSouth = new JPanel();
		
		JButton btnInsert = new JButton("추가");
		JButton btnCancel = new JButton("취소");
		JButton btnImage = new JButton("...");
		
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnCancel);
		
		add(tfPath);
		add(btnImage);
		add(tfName);
		add(lbImage);
		add(btnInsert);
		add(btnCancel);
		
		tfPath.setPreferredSize(new Dimension(280, 30));
		btnImage.setPreferredSize(new Dimension(40, 30));
		tfName.setPreferredSize(new Dimension(327, 30));
		lbImage.setPreferredSize(new Dimension(327, 330));
		
		tfPath.setEnabled(false);
		lbImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		btnImage.addActionListener(e -> getImage());
		btnInsert.addActionListener(e -> insert());
		btnCancel.addActionListener(e -> dispose());
	}
	
	public static void main(String[] args) {
		new InsertAdFrame().setVisible(true);
	}
	
	private void getImage() {
		FileDialog dialog = new FileDialog(this, "", FileDialog.LOAD);
		
		dialog.setVisible(true);
		
		if (dialog.getFile() == null) {
			return;
		}
		
		File file = new File(dialog.getDirectory() + dialog.getFile());
		
		tfPath.setText(file.getPath());
		lbImage.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(file.getPath()).getScaledInstance(327, 330, Image.SCALE_SMOOTH)));
	}
	
	private void insert() {
		String path = tfPath.getText();
		String name = tfName.getText();
		
		if (path.isEmpty()) {
			eMessage("사진을 선택해주세요.");
		} else if (path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase().equals("jpg") == false) {
			eMessage("파일이 사진이 아닙니다.");
		} else if (name.isEmpty()) {
			eMessage("광고 이름을 적어주세요.");
		} else {
			try {
				int key = BasePanel.dbManager.executeUpdate("INSERT INTO image VALUES (0, '광고', ?)", path);
				BasePanel.dbManager.executeUpdate("INSERT INTO ad VALUES (0, ?, ?, ?)", name, BasePanel.sellerSerial, key);
				
				iMessage("광고가 등록되었습니다.");
				dispose();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
