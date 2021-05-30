package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import panel.BasePanel;
import panel.ButtonPanel;

public class DrugFrame extends BaseFrame{
	
	private ButtonPanel Button1 = new ButtonPanel();
	private ButtonPanel Button2 = new ButtonPanel();
	
	public DrugFrame() {
		super("ó�� �๰", 330, 240);
	
		JPanel pnlCenter = new JPanel(new GridLayout(2, 2, -200, 0));
		JPanel pnlSouth = new JPanel();
		
		JButton btnExit = new JButton("���");
		JButton btnOk = new JButton("Ȯ��");
		
		JLabel lbAm = new JLabel("    ����");
		JLabel lbPm = new JLabel("    ����");		
	

		btnExit.setPreferredSize(new Dimension(130, 35));
		btnOk.setPreferredSize(new Dimension(130, 35));
		
		pnlCenter.add(lbAm);
		pnlCenter.add(Button1);
		pnlCenter.add(lbPm);
		pnlCenter.add(Button2);
		
		pnlSouth.add(btnExit);
		pnlSouth.add(btnOk);
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		btnExit.addActionListener(a -> dispose());
		btnOk.addActionListener(e -> ok());
	}
	
	public static void main(String[] args) {
		new DrugFrame().setVisible(true);
	}
	
	private void ok() {
		String values = Button1.getvalue();
		String values1 = Button2.getvalue();
		
		if(values == null || values1 == null) {
			eMessage("��,�ƴϿ��� �������ּ���.");
		}else {
			try {
				
			if(BasePanel.d_index == 0) {
				dbManager.executeUpdate("insert into tbl_diary(M_index, D_date, D_drug_am,D_drug_pm) values(?,?,?,?)", BasePanel.m_index,BasePanel.d_date,values,values1);
			}else {
				dbManager.executeUpdate("update tbl_diary set D_drug_am = ? , D_drug_pm = ? where D_index = ?", values,values1,BasePanel.d_index);
				
			}
			
			iMessage("�Է��� �Ϸ�Ǿ����ϴ�.");
			dispose();
			
			BasePanel.mainPanel.getdiary();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}



