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
		super("���� �߰�", 350, 480);
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel pnlCenter = new JPanel();
		JPanel pnlSouth = new JPanel();
		
		JButton btnInsert = new JButton("�߰�");
		JButton btnCancel = new JButton("���");
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
			eMessage("������ �������ּ���.");
		} else if (path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase().equals("jpg") == false) {
			eMessage("������ ������ �ƴմϴ�.");
		} else if (name.isEmpty()) {
			eMessage("���� �̸��� �����ּ���.");
		} else {
			try {
				int key = BasePanel.dbManager.executeUpdate("INSERT INTO image VALUES (0, '����', ?)", path);
				BasePanel.dbManager.executeUpdate("INSERT INTO ad VALUES (0, ?, ?, ?)", name, BasePanel.sellerSerial, key);
				
				iMessage("���� ��ϵǾ����ϴ�.");
				dispose();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
