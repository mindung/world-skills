package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ReservationNumFrame extends BaseFrame {

	private ArrayList<Integer> numbers = new ArrayList<>();
	
	private JTextField tfNum = new JTextField(10);
	private JPanel pnlNum = new JPanel(new GridLayout(4, 3, 5, 5));

	public ReservationNumFrame() {
		super("������ȣ Ȯ��", 400, 400);
	
		RandomNum();
		
		JButton btnOk = new JButton("Ȯ��");
		
		JPanel pnlN = new JPanel(new BorderLayout());
		JPanel pnlNN = new JPanel(new BorderLayout());
		
		pnlNN.add(new JLabel("������ȣ�� �Է��ϼ���"), BorderLayout.WEST);
		pnlNN.add(btnOk, BorderLayout.EAST);
		
		pnlN.add(pnlNN, BorderLayout.NORTH);
		pnlN.add(tfNum, BorderLayout.CENTER);
		
		add(pnlN, BorderLayout.NORTH);
		add(pnlNum, BorderLayout.CENTER);
		
		tfNum.setPreferredSize(new Dimension(200, 35));
		tfNum.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pnlNum.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlN.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlNN.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		tfNum.setEnabled(false);
		tfNum.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnOk.addActionListener(e -> btnCheck());
		
	}
	
	public static void main(String[] args) {
		new ReservationNumFrame().setVisible(true);
	}
	
	private JButton addBtn(String str) {
		
		JButton btn = new JButton("");
		
		if (str == null) {
			btn.setText(String.valueOf(getRandomIndex()));
		} else {
			btn.setText(str);
		}
		
		pnlNum.add(btn);
		
		btn.addActionListener(e -> {
			
			if (str == "��迭") {
				RandomNum();
			} else if (str == "��"){
				if (tfNum.getText().length() == 0) {
					eMessage("���� ��ȣ�� �����ϴ�.");
				} else {
					tfNum.setText(tfNum.getText().substring(0, tfNum.getText().length() - 1));
				}
			} else {
				tfNum.setText( tfNum.getText() + btn.getText());
			}
			
		});
		return btn;
	}
	
	private void RandomNum() {
		numbers.clear();
		pnlNum.removeAll();
		
		for (int i = 0; i < 10; i++) {
			numbers.add(i);
		}

		for (int i = 0; i < 12; i++) {
			if (i == 9) {
				addBtn("��迭");
			} else if (i == 11) {
				addBtn("��");
			} else {
				addBtn(null);
			}
		}
		pnlNum.updateUI();
	}
	
	private int getRandomIndex() {
		int index = (int) (Math.random() * numbers.size());
		int n = numbers.get(index);
		numbers.remove(index);
		return n;
	}
	
	private void btnCheck() {
		String num = tfNum.getText();
		
		if (num.isEmpty()) {
			eMessage("������ȣ�� �����ϴ�.");
			
		} else {
			
			try {
				ResultSet rs = dbManager.executeQuery("select * from payment where p_no = ? and u_no = ? ", num, u_no);
				
				if (rs.next()) {
				
					openFrame(new PayCheckFrame(rs.getInt(1)), new ReservationNumFrame());
				
				} else {
					eMessage("��ġ�ϴ� ������ȣ�� �����ϴ�.");
					tfNum.setText("");
					RandomNum();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
