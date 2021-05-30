package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frame.AnkelFrame;
import frame.DrugFrame;
import frame.ExciseFrame;
import frame.HypochlorationFrame;
import frame.WeightFrame;
import frame.pressureFrame;

public class MainPanel extends BasePanel {

	private JLabel lbDate = new JLabel("", JLabel.CENTER);
	private LocalDate date = LocalDate.now();

	private Diary[] diaries = new Diary[6];

	public MainPanel() {

		String[] title = { "몸무게", "발목 부종", "혈압", "처방 약물", "운동", "저염식이" };
		Color[] colors = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.pink, Color.blue };

		JPanel pnlcenter = new JPanel(new GridLayout(2, 3, 10, 10));
		JPanel pnlSouth = new JPanel();
		JPanel pnlNorth = new JPanel();

		JButton btnPrevDate = new JButton("이전 날짜");
		JButton btnNextDate = new JButton("다른 날짜");
		JButton btnEtc = new JButton("기타 증상 입력");

		pnlNorth.add(btnPrevDate);
		pnlNorth.add(lbDate);
		pnlNorth.add(btnNextDate);

		pnlSouth.add(btnEtc);

		pnlcenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lbDate.setPreferredSize(new Dimension(350, 55));
		lbDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		for (int i = 0; i < diaries.length; i++) {
			diaries[i] = new Diary(title[i], colors[i]);
			pnlcenter.add(diaries[i]);
		}

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlcenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		btnPrevDate.addActionListener(e -> PrevDate());
		btnNextDate.addActionListener(e -> NextDate());
//		btnEtc.addActionListener(e -> new );
		getdiary();
	}

	private void PrevDate() {
		date = date.minusDays(1);
		getdiary();
	}
	
	private void NextDate() {
		date = date.plusDays(1);
		getdiary();
	}
	
	public void getdiary() {

		lbDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));

		try {
			ResultSet rs = dbManager.executeQuery("select * from tbl_diary where M_index = ? and D_date = ?", m_index,
					date.toString()); // 날짜 바뀐 대로 가져와야하니까 date.tosting

			if (rs.next()) {

				diaries[0].SetData(rs.getString(4));
				diaries[1].SetData(rs.getString(5));
				diaries[2].SetData(rs.getString(6));
				diaries[3].SetData(String.format("%s/%s", rs.getString(7), rs.getString(8)));
				diaries[4].SetData(rs.getString(9));
				diaries[5].SetData(rs.getString(12));
			} else {
				for (int i = 0; i < diaries.length; i++) {
					diaries[i].SetData("미입력");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public class Diary extends JPanel {

		private String data = "미입력";
		private JLabel lbData = new JLabel("", JLabel.CENTER);

		public Diary(String title, Color color) {

			setLayout(new BorderLayout());
			setBorder(BorderFactory.createLineBorder(Color.BLACK));

			JLabel lbTitle = new JLabel(title, JLabel.CENTER);
			lbTitle.setBackground(color);
			lbTitle.setOpaque(true);

			lbData.setOpaque(true);

			add(lbTitle, BorderLayout.NORTH);
			add(lbData, BorderLayout.CENTER);

			lbTitle.setBorder(BorderFactory.createLineBorder(color.BLACK));
			lbData.setBorder(BorderFactory.createLineBorder(color.BLACK));

			lbTitle.setPreferredSize(new Dimension(200, 30));
			lbData.setPreferredSize(new Dimension(200, 120));

			SetData(data);

			lbData.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					if (lbData.getText().equals("미입력") == false) {
						return;
					}

					if (m_index == 0) {
						eMessage("로그인을 해주세요.");
					} else {
						d_date = date.toString();

						try {
							ResultSet rs = dbManager.executeQuery(
									"select * from tbl_diary where M_index = ? and D_date = ?", m_index, d_date);

							if (rs.next()) {
								d_index = rs.getInt(1);
							} else {
								d_index = 0;
							}

							if(title.equals("몸무게")) { // title은 임의의 값 제목들!
								new WeightFrame().setVisible(true);
							}else if(title.equals("발목 부종")) {
								new AnkelFrame().setVisible(true);
							}else if(title.equals("혈압")) {
								new pressureFrame().setVisible(true);								
							}else if(title.equals("처방 약물")) {
								new DrugFrame().setVisible(true);								
							}else if(title.equals("운동")) {
								new ExciseFrame().setVisible(true);
							}else {
								new HypochlorationFrame().setVisible(true);
							}
							
						} catch (SQLException e1) {

							e1.printStackTrace();
						}
					}
				}
			});
		}

		public void SetData(String data) {
			if (data == null || data.isEmpty() || data.equals("/") || data.equals("Null/null")
					|| data.equals("null/null")) {
				data = "미입력";
			}
			
			this.data = data;

			lbData.setText(data);
			lbData.setBackground(data.equals("미입력") ? null : Color.WHITE);
			lbData.setFont(new Font("굴림", Font.BOLD, 20));
		}
	}
}
