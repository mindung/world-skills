package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InsertStaffFrame extends BaseFrame{

	private JTextField tfName = new JTextField();
	private JTextField tfId= new JTextField();
	private JTextField tfPw= new JTextField();
	
	private JComboBox<Project> comPName = new JComboBox<Project>();
	
	private JTextArea tfNote = new JTextArea();
	
	private int com_num;
	
	private class Project {
		private int num;
		private String title;
		
		public Project(int num, String title) {
			this.num = num;
			this.title = title;
		}
		
		@Override
		public String toString() {
			return title;
		}
	}
	
	public InsertStaffFrame() {
		super("직원 추가", 400, 300);
		
		JPanel pnlCenter = new JPanel(new GridLayout(5, 2, -130, 10));
		JPanel pnlSouth = new JPanel();
		
		JButton btnInsert = new JButton("추가");
		JButton btnExit = new JButton("닫기");
		
		pnlCenter.add(new JLabel("Project_Name :",JLabel.LEFT ));
		pnlCenter.add(comPName);
		pnlCenter.add(new JLabel("Name :",JLabel.LEFT));
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel("ID :",JLabel.LEFT));
		pnlCenter.add(tfId);
		pnlCenter.add(new JLabel("PW :",JLabel.LEFT));
		pnlCenter.add(tfPw);
		pnlCenter.add(new JLabel("Note :",JLabel.LEFT));
		pnlCenter.add(tfNote);
			
		pnlSouth.add(btnInsert);
		pnlSouth.add(btnExit);
		
		pnlCenter.setPreferredSize(new Dimension(350, 300));
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
//		tfNote.setPreferredSize(new Dimension(100, 100));
		
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		comboInfo();
		
		btnInsert.addActionListener(e -> addStaff());
		btnExit.addActionListener(e -> dispose());
	}
	
	public static void main(String[] args) { 
		new InsertStaffFrame().setVisible(true);
	}
	
	private void comboInfo() {
		try {
			ResultSet rs = dbManager.executeQuery("select * from project where NOW() <= p_edate");
			
			while(rs.next()) {
				comPName.addItem(new Project(rs.getInt(1), rs.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addStaff() {
		
		Project project = (Project) comPName.getSelectedItem();
		
		int num = project.num; 
		String name = tfName.getText();
		String id = tfId.getText();
		String pw = tfPw.getText();
		String note = tfNote.getText();
		
		try {
			dbManager.executeUpdate("insert into member values(0,?,?,?,?,?)", num,id,pw,name,note);
			IMessage("추가 되었습니다.");
			
			dispose();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
