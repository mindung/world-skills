package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import panel.BasePanel;
import panel.ProjectPanel;

public class SelecteProjectFrame extends BaseFrame{
	
	private JComboBox<Project> comProject = new JComboBox<Project>();

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
	
	public SelecteProjectFrame() {
		super("Project를 선택해주십시오.",350, 130);
		
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnOk = new JButton("확인");
		JButton btnExit = new JButton("취소");
		
		pnlSouth.add(btnOk);
		pnlSouth.add(btnExit);
	
		comProject.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		add(comProject,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
		
		getcombo();
		
		btnOk.addActionListener(e -> ok());
		btnExit.addActionListener(e -> dispose());
	} 
	
	public static void main(String[] args) {
		new SelecteProjectFrame().setVisible(true);
	}
	
	private void getcombo() {
		try {
			ResultSet rs = dbManager.executeQuery("select* from project");
			
			while (rs.next()) {
				comProject.addItem(new Project(rs.getInt(1), rs.getString(2)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void ok() {
		
		Project project = (Project) comProject.getSelectedItem();
		
		try {
		
			ResultSet rs = dbManager.executeQuery("select * from project where p_num = ?", project.num);
			
			if(rs.next()) {
				
				System.out.println("실행");
				
				BasePanel.project_no = project.num;
				BasePanel.frame.changePanel(new ProjectPanel());
				dispose();
				System.out.println(BasePanel.project_no);
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		};
		
	}
}
