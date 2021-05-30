package frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

public class ReplyCareFrame extends BaseFrame {
	
	private JTextArea tfContents = new JTextArea();
	
	DefaultMutableTreeNode root = new DefaultMutableTreeNode ("회신문서");
	DefaultTreeModel treeModel = new DefaultTreeModel(root);
	DefaultMutableTreeNode[] node = new DefaultMutableTreeNode[100];
	
	private JTree tree = new JTree(root);
	public ReplyCareFrame() {
		super("회신문서관리", 650, 500);

		setLayout(null);

		JPanel pnlS = new JPanel(new GridLayout(1, 3, 5, 0));

		JButton btnInsert = createButton("추가");
		JButton btnDelete = createButton("삭제");
		JButton btnExit = createButton("닫기");

		pnlS.add(btnInsert);
		pnlS.add(btnDelete);
		pnlS.add(btnExit);

		tfContents.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		addC(tfContents, 330, 10, 300, 400);
		addC(pnlS, 200, 420, 250, 30);
		addC(tree, 5, 5, 300, 400);
		
		btnInsert.addActionListener(e -> {

			ReplyInsertFrame frame = new ReplyInsertFrame();

			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					getInfo();
				}
			});

			frame.setVisible(true);
		});
		
		btnExit.addActionListener(e -> dispose());
//		btnDelete.addActionListener(e ->);

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
//				
				String title = String.valueOf(tree.getLastSelectedPathComponent());
				tfContents.setText(title);
				
				String r_content = "";
				String content = "";
				
				try {
				
					ResultSet rs = dbManager.executeQuery("SELECT * FROM reply where r_title = ?", title);
					
					if (rs.next()) {
						r_content = rs.getString(3);
						tfContents.setText(r_content);
					} 
					
					for (int i = 0; i < r_content.length(); i++) {
						if (i != 0 && i % 25 == 0) {
							content += "\r\n";
						}
						
						content += r_content.substring(i, i+1);
						tfContents.setText(content);;
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				System.out.println(tree.getLastSelectedPathComponent());
			}
		});
		
		getInfo();
		
	}
	
	public static void main(String[] args) {
		new ReplyCareFrame().setVisible(true);
	}
	
	private void getInfo() {
		
		tree.removeAll(); // 원래 들어있던 값 지우는 함수가 ,,,
		//		 int cnt = 0;
		try {
			
			ResultSet rs = dbManager.executeQuery("SELECT * FROM reply");

			while (rs.next()) {
				node[rs.getInt(1) - 1] = new DefaultMutableTreeNode(rs.getString(2));
				root.add(node[rs.getInt(1) - 1]);
				
//				cnt++;
			}

			tree.updateUI();
//			cnt = cnt-1;

//			node = new DefaultMutableTreeNode[cnt];

		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
}
