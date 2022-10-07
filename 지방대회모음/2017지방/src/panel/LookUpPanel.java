 package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import frame.BaseFrame;

public class LookUpPanel extends BasePanel{
	
	private JTextField tfNumber = new JTextField(8);
	private JTextField tfNumber2 = new JTextField(8);
	private JTextField tfSdate = new JTextField(8);
	private JTextField tfSdateS = new JTextField(8);
	private JTextField tfNumberS = new JTextField(8);
	private JTextField tfSeat = new JTextField(8);
	private JTextField tfId = new JTextField(8);
	
	private DefaultTableModel dtm = new DefaultTableModel(new String[] {"좌측 창가","예약","좌측통로","예약","통로","우측창가","예약","우측통로","예약"}, 0);
	private JTable table = new JTable(dtm);
	
	private ArrayList<String> enteredSeat = new ArrayList<String>();
	private ArrayList<String> duplicatedSeat = new ArrayList<String>();
	private ArrayList<String> reservedSeat = new ArrayList<String>();
	private ArrayList<String> reservedCheckSeat = new ArrayList<String>();
	
	private JScrollPane jsp = new JScrollPane(table);
	private JPanel pnlcenter = new JPanel();
	
	public LookUpPanel() {
	
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(700, 400));
	
		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNSouth =new JPanel(new FlowLayout());
		JPanel pnlSouth = new JPanel(new FlowLayout());
		
		JButton btnSearch = new JButton("좌석 조회");
		JButton btnReservation = new JButton("예약");
		
		JLabel lbTitle = new JLabel("좌석표 선택",JLabel.LEFT);
		
		lbTitle.setFont(new Font("맑은 고딕",Font.BOLD,19));
		
		pnlNSouth.add(new JLabel("차량 번호"));
		pnlNSouth.add(tfNumber);
		pnlNSouth.add(new JLabel("출발일자"));
		pnlNSouth.add(tfSdate);
		pnlNSouth.add(new JLabel("호차 번호"));
		pnlNSouth.add(tfNumber2);
		pnlNSouth.add(btnSearch);
		
		pnlSouth.add(new JLabel("출발일자"));
		pnlSouth.add(tfSdateS);
		pnlSouth.add(new JLabel("차량번호"));
		pnlSouth.add(tfNumberS);
		pnlSouth.add(new JLabel("좌석번호"));
		pnlSouth.add(tfSeat);
		pnlSouth.add(new JLabel("회원ID"));
		pnlSouth.add(tfId);
		pnlSouth.add(btnReservation);
		
		pnlNorth.add(lbTitle,BorderLayout.NORTH);
		pnlNorth.add(pnlNSouth,BorderLayout.CENTER);
		
		pnlcenter.add(jsp);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlSouth,BorderLayout.SOUTH);
		add(pnlcenter,BorderLayout.CENTER);

		tfNumber.setText(BaseFrame.tfNum);
		tfSdate.setText(BaseFrame.date);
		tfNumber2.setText(BaseFrame.tfNum2);
		
		jsp.setPreferredSize(new Dimension(550, 200));

		btnReservation.addActionListener(e -> reserve());
		
		tfId.setText(BaseFrame.login);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(25);
		table.getColumnModel().getColumn(3).setPreferredWidth(25);
		table.getColumnModel().getColumn(4).setPreferredWidth(25);
		table.getColumnModel().getColumn(6).setPreferredWidth(25);
		table.getColumnModel().getColumn(8).setPreferredWidth(25);
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(BaseFrame.centerRenderer);
		}
		
		for (int i = 0; i < 5; i++) {
			int n = i * 4 + 1; // 초기값은 0 , 1, 2 ... 등
			
			dtm.addRow(new Object[] {
				n, "", n + 1, "", "---", n + 2, "", n + 3, ""
			});
		}
		
		jsp.setVisible(false);
		btnSearch.addActionListener(e -> SearchSeat());
		
	}

	private void reserve() {
		
			String seat = tfSeat.getText();
		
			if (seat.isEmpty()) {
				iMessage("좌석번호를 입력해주세요.");
				return;
			}
		
			enteredSeat.clear();
			duplicatedSeat.clear();
			reservedCheckSeat.clear();
			
			StringTokenizer st = new StringTokenizer(tfSeat.getText(),", ");
//			
//			duplicatedSeat 중복 좌석
//			enteredSeat 입력 좌석
//			reservedCheckSeat 예약 확인 좌석
			
			while(st.hasMoreTokens()) {
				String s = st.nextToken();
				
				if(enteredSeat.contains(s) && duplicatedSeat.contains(s) == false) { // 중복된 좌석의 값이 배열에 없는 경우
					duplicatedSeat.add(s);											// 중복 좌석에 쪼갠 값을 넣음
				}else if(reservedSeat.contains(s)){ 								/////// 예약된 좌석의 값과 해당되는 것이 있다면
					reservedCheckSeat.add(s);										// 예약된 좌석 체크 배열에 값을 넣는다.
				}else {																// 위에 조건이 다 아닌경우 
					enteredSeat.add(s);												// 입력좌석
				}
			}
			 
			if(duplicatedSeat.isEmpty() == false) { // 중복좌석 값이 들어있다면
				eMessage(String.format("좌석번호 %s이(가) 중복입력되었습니다.", String.join(", ", duplicatedSeat)));
				return;
			}
			
			if(reservedCheckSeat.isEmpty() == false) {	// 에약확인값이 비어 있지 않다면 
				eMessage(String.format("좌석번호 %s는 이미 예약되어 있는 좌석입니다.", String.join(", ", duplicatedSeat)));
				return;
			}
			
			iMessage(String.join(", ", enteredSeat));
			
	}
	
	private void SearchSeat() {
		
		String tfNum = tfNumber.getText();
		String Sdate = tfSdate.getText();
		String tfNum2 = tfNumber2.getText();
		
		if(tfNum.isEmpty() || Sdate.isEmpty() || tfNum2.isEmpty()) {
			iMessage("올바르지 못한좌석정보입니다.");
			return;
		}
		
		reservedCheckSeat.clear(); // 예약확인 좌석 변수 초기화 
		
		try {
			
			ResultSet rs = dbManager.executeQuery("select * from tbl_ticket where bNumber = ? and bDate = ? and bNumber2 = ?",tfNum,Sdate,tfNum2);
		
			while(rs.next()) { // 해당 조건이 존재하면
				
				String seat = rs.getString(4); 
				
				for (int i = 0; i < dtm.getRowCount(); i++) { //  총 행의 수
					for (int j = 0; j < dtm.getColumnCount(); j++) { // 총 열의 수
						if (table.getValueAt(i, j).toString().equals(seat)) { // 테이블에 해당하는 행과,열의 값이 seat 값과 같다면
							table.setValueAt("O", i, j+1);					// table "O" 넣어줌 해당 행과, 열 + 1 바로 옆칸에 값을 넣여ㅓ준다.
							reservedSeat.add(seat);							// 예약했던좌석의 배열 변수 에 그 값을 넣어줌
						}
					}
				}
			} 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pnlcenter.updateUI();
		jsp.setVisible(true);
		
		tfSdateS.setEnabled(false);
		tfNumberS.setEnabled(false);
		
		tfSdateS.setText(Sdate);
		tfNumberS.setText(tfNum);
		
		
	}
}

