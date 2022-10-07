package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProfessorFrame extends BaseFrame {
	
	private JTabbedPane tap = new JTabbedPane();
	
	public ProfessorFrame() {
		super(String.format("%s 교수님", Info), 600,600);
		
		panel panel = new panel();
		panel1 panel1 = new panel1();
		
		tap.addTab("성적입력", panel);
		tap.addTab("개인정보입력", panel1);
		
		add(tap,BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
//		new ProfessorFrame().setVisible(true);
		new LoginProFrame().setVisible(true);
	}
	
	private class Course {
		private String code;
		private String title;
		
		public Course(String code, String title) {
			this.code = code;
			this.title = title;
		}
		
		@Override
		public String toString() {
			return title;
		}
	}
	
	private class panel extends JPanel{
		
		private JComboBox<Course> ComTitle = new JComboBox<Course>();
		private JComboBox<String> ComCode = new JComboBox<String>();
		private JComboBox<String> comGrade = new JComboBox<String>(new String[] {"", "A", "B", "C", "D", "E", "F"});
		
		private JTextField tfName = new JTextField();
		private JTextArea tfRmk = new JTextArea();
		
		public panel() {
			
			setLayout(null);
			
			JPanel pnl = new JPanel(new GridLayout(4, 2, -110, 10));
			JPanel pnlCenter = new JPanel(new BorderLayout());
			JPanel pnlSouth = new JPanel();
			
			JButton btnSave = new JButton("저장");
			JButton btnExit = new JButton("닫기");
			
			pnl.add(new JLabel("과목"));
			pnl.add(ComTitle);
			pnl.add(new JLabel("학번"));
			pnl.add(ComCode);
			pnl.add(new JLabel("이름"));
			pnl.add(tfName);
			pnl.add(new JLabel("평점"));
			pnl.add(comGrade);
			
			pnlCenter.add(new JLabel("특기사항",JLabel.LEFT),BorderLayout.NORTH);
			pnlCenter.add(tfRmk,BorderLayout.CENTER);
			
			pnlSouth.add(btnSave);
			pnlSouth.add(btnExit);
			
			add(pnl);
			add(pnlCenter);
			add(pnlSouth);
			
			pnl.setBounds(60, 50, 450, 200);
			pnlCenter.setBounds(40, 280, 500, 150);
			pnlSouth.setBounds(200, 430, 200, 30);
			
			tfName.setEnabled(false);
			
			try {
				ResultSet rs = dbManager.executeQuery("select * from course where c_pCode = ?", p_Code);
				
				while (rs.next()) {
					ComTitle.addItem(new Course(rs.getString(1), rs.getString(2)));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ComTitle.addItemListener(e -> getSCodeInfo());
			ComCode.addItemListener(e -> getNameGrInfo());
			
			btnSave.addActionListener(e -> save());
			btnExit.addActionListener(e -> openFrame(new MainFrame()));
			
			getSCodeInfo();
		}
		
		private void getSCodeInfo() {
			
			ComCode.removeAllItems();
			
			Course course = (Course) ComTitle.getSelectedItem();
			
			if (course == null) {
				return;
			}
			
			try {
				ResultSet rs = dbManager.executeQuery("select i_sCode from info where i_cCode = ?", course.code);
				while (rs.next()) {
					ComCode.addItem(String.valueOf(rs.getInt(1)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		private void save() {
			Course course = (Course) ComTitle.getSelectedItem();
			String code = (String) ComCode.getSelectedItem();
			String gr = (String) comGrade.getSelectedItem();
			String rmk = tfRmk.getText();
			String name = tfName.getText();
			
			int yn = JOptionPane.showConfirmDialog(null, name + " 학생의 성적을 저장하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
			
			if(yn == JOptionPane.YES_OPTION) {
				try {
					dbManager.executeUpdate("update info set i_gr = ?, i_rmk = ? where i_sCode = ? and i_cCode = ?", gr,rmk,code, course.code);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private void getNameGrInfo() {
			Course course = (Course) ComTitle.getSelectedItem();
			String code = (String) ComCode.getSelectedItem();

			try {
				ResultSet rs = dbManager.executeQuery("select student.s_name, info.i_gr, info.i_rmk from info inner join student on info.i_sCode = student.s_code where i_sCode = ? and i_cCode = ?", code, course.code);
				if(rs.next()) {
					tfName.setText(rs.getString(1));
					comGrade.setSelectedItem(rs.getString(2));
					tfRmk.setText(rs.getString(3));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class panel1 extends JPanel{
		private JTextField tfcode = new JTextField();
		private JTextField tfName = new JTextField();
		private JTextField tfPhone = new JTextField();
		private JTextField tfPw = new JTextField();
		
		private JLabel lbImg = new JLabel();
		
		private File file;
		
		public panel1() {
			
			setLayout(null);
			
			JPanel pnl = new JPanel(new GridLayout(4, 2, -50, 5));
			JPanel pnlSouth = new JPanel();
			
			JButton btnImgUpdate = new JButton("사진 수정");
			JButton btnSave = new JButton("저장");
			JButton btnExit = new JButton("닫기");

			pnl.add(new JLabel("교수번호"));
			pnl.add(tfcode);
			pnl.add(new JLabel("이름"));
			pnl.add(tfName);
			pnl.add(new JLabel("전화번호"));
			pnl.add(tfPhone);
			pnl.add(new JLabel("암호"));
			pnl.add(tfPw);
			
			pnlSouth.add(btnSave);
			pnlSouth.add(btnExit);
			
			add(lbImg);
			add(btnImgUpdate);
			add(pnl);
			add(pnlSouth);
			
			tfcode.setEnabled(false);
			lbImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			lbImg.setBounds(30, 55, 200, 250);
			btnImgUpdate.setBounds(30, 345, 200, 25);
			pnl.setBounds(280, 100, 250, 200);
			pnlSouth.setBounds(200, 470, 200, 25);

			try {
				ResultSet rs = dbManager.executeQuery("SELECT * FROM hrddb.pro where p_code = ?", p_Code);
				
				if(rs.next()) {
					File file = new File(String.format("./지급자료/%s.jpg",p_Code));
					
					if (file.exists()) {
						lbImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(String.format("./지급자료/%s.jpg",p_Code)).getScaledInstance(200, 250, Image.SCALE_SMOOTH)));						
					} else {
						lbImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./지급자료/any.jpg").getScaledInstance(200, 250, Image.SCALE_SMOOTH)));
					}
					
					tfcode.setText(rs.getString(1));
					tfName.setText(rs.getString(2));
					tfPhone.setText(rs.getString(3));
					tfPw.setText(rs.getString(4));
				
//					lbImg.getText() == null ? lbImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./지급자료/any.jpg").getScaledInstance(200, 250, Image.SCALE_SMOOTH)));
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			btnImgUpdate.addActionListener(e -> {

				JFileChooser fc = new JFileChooser();
				int open = fc.showOpenDialog(null);
				
				if(open == 0) {
					file = fc.getSelectedFile();
					
					System.out.println(file.getPath());
					
					lbImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(file.getPath()).getScaledInstance(200, 250, Image.SCALE_SMOOTH)));
				}
			});

			btnSave.addActionListener(e -> updateInfo());
			btnExit.addActionListener(e -> openFrame(new MainFrame()));
		}
			
		private void updateInfo() {
			String name = tfName.getText();
			String phone = tfPhone.getText();
			String pw = tfPw.getText();
		
			int yn = JOptionPane.showConfirmDialog(null, "개인정보를 수정하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
			
			if(yn == JOptionPane.YES_OPTION) {
				
				try {
					dbManager.executeUpdate("update pro set p_name = ?, p_tel = ?, p_pw = ? where p_code = ?", name,phone,pw,p_Code);
				
					fileCopy(file.getPath(), String.format("./지급자료/%s.jpg",p_Code));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	

}



