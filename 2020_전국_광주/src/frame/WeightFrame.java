package frame;

import java.awt.BorderLayout;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.dbManager;
import panel.BasePanel;
import panel.MainPanel;

public class WeightFrame extends BaseFrame{
	
	private JTextField tfWeight = new JTextField();
	
	public WeightFrame() {
		super("������", 300, 160);
		
		JPanel pnlCenter = new JPanel(null);
		JPanel pnlSouth = new JPanel();
		
		JLabel lbKg = new JLabel("KG");
		
		JButton btnExit = new JButton("���");
		JButton btnOk= new JButton("Ȯ��");

		pnlCenter.add(tfWeight);
		pnlCenter.add(lbKg);
		
		pnlSouth.add(btnExit);
		pnlSouth.add(btnOk);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		tfWeight.setBounds(60, 20, 130, 45);
		lbKg.setBounds(200, 30, 40, 40);
		
		btnExit.addActionListener(e -> dispose());
		btnOk.addActionListener(e ->ok());
	}
	
	public static void main(String[] args) {
		new WeightFrame().setVisible(true);
	}
	
	private void ok() {
	
		String weight = tfWeight.getText();
		
		if(weight.isEmpty()) {
			eMessage("��ĭ�� �����մϴ�.");
		}else {
			if(weight.matches("^[0-9]+$")) {
				
				try {
					if(BasePanel.d_index == 0) { // diary ���� ������ ���� �־���
						dbManager.executeUpdate("insert into tbl_diary (M_index , D_date, D_weight) values(?,?,?)", BasePanel.m_index,BasePanel.d_date,weight);
						
					}else {
						dbManager.executeUpdate("update tbl_diary set D_weight = ? where D_index = ?", weight,BasePanel.d_index);
						
					}
					
					iMessage("�Է��� �Ϸ�Ǿ����ϴ�.");
					dispose();
					BasePanel.mainPanel.getdiary();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				eMessage("�����Դ� ���ڷ� �Է����ּ���.");
			}
		}

	}
}

