package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Wedding;

public class WeddinghallFrame extends BaseFrame{

	private JTextField tfAddr = new JTextField();
	private JTextField tfPeople = new JTextField();
	private JTextField tfHallPrice = new JTextField();
	private JTextField tfMealPrice = new JTextField();
	private JTextField tfPeopleStr = new JTextField();
	private JTextField tfDate = new JTextField();
	private JTextField tfLetter = new JTextField(3);
	private JTextField tfWedding = new JTextField();
	private JTextField tfMeal = new JTextField();
	
	private ArrayList<Wedding> weddings = new ArrayList<Wedding>();
	
	private JLabel lbImg = new JLabel();
	
	private int index = 0;

	private JLabel lbTitle = new JLabel("", JLabel.CENTER);
	
	private JButton btnOrder = new JButton("결제");

	private boolean isActive = true;
	
	private String letter;
	
	public WeddinghallFrame(ArrayList<Wedding> weddings, int cnt) {
		
		super("웨딩홀", 900, 500);
		setLayout(null);
		
		this.weddings = weddings;
		this.index = cnt;
		
		JPanel pnlTitle = new JPanel(new GridLayout(2, 1, 0 , - 10));
		JPanel pnlInfo = new JPanel(new GridLayout(8, 2, -250, 20));
		JPanel pnlFlow = new JPanel(new FlowLayout());
		
		JButton btnPrev = new JButton("이전");
		JButton btnNext = new JButton("다음");
		JButton btnLetter = new JButton("청접장 선택");
		
		pnlInfo.add(new JLabel("주소"));
		pnlInfo.add(tfAddr);
		pnlInfo.add(new JLabel("수용인원"));
		pnlInfo.add(tfPeople);
		pnlInfo.add(new JLabel("홀사용료"));
		pnlInfo.add(tfHallPrice);
		pnlInfo.add(new JLabel("예식형태"));
		pnlInfo.add(tfWedding);
		pnlInfo.add(new JLabel("식사종류"));
		pnlInfo.add(tfMeal);
		pnlInfo.add(new JLabel("식사비용"));
		pnlInfo.add(tfMealPrice);
		pnlInfo.add(new JLabel("인원수"));
		pnlInfo.add(tfPeopleStr);
		pnlInfo.add(new JLabel("날짜"));
		pnlInfo.add(tfDate);
		
		pnlTitle.add(new JLabel("웨딩홀명", JLabel.CENTER));
		pnlTitle.add(lbTitle);

		pnlFlow.add(btnPrev);
		pnlFlow.add(btnOrder);
		pnlFlow.add(btnNext);
		pnlFlow.add(btnLetter);
		pnlFlow.add(tfLetter);
		
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		lbImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		btnLetter.setPreferredSize(new Dimension(100, 30));
		btnNext.setPreferredSize(new Dimension(100, 30));
		btnPrev.setPreferredSize(new Dimension(100, 30));
		btnOrder.setPreferredSize(new Dimension(100, 30));
		
		addC(pnlTitle, 30, 10, 300, 80);
		addC(lbImg, 10, 100, 350, 200);
		addC(pnlFlow, 15, 330, 350, 70);
		addC(pnlInfo, 400, 20, 400, 400);
		
		tfLetter.setHorizontalAlignment(JLabel.CENTER);
		
		btnOrder.setEnabled(false);
		btnLetter.setEnabled(false);
		
		tfPeopleStr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!tfPeopleStr.getText().isEmpty() && tfPeopleStr.getText().matches("^[0-9]+$") == false) {
					eMessage("인원수를 바르게 입력해주세요.");
					tfPeopleStr.setText("");
				}
				
				setOrderButtonEnabled();
			}
		});
		
		tfDate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfLetter.setText("");
				tfLetter.requestFocus();
				
				Wedding wedding = weddings.get(index);
				
				CalendarFrame frame = new CalendarFrame(wedding.wNo);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						tfDate.setText(selecteDate);
						tfLetter.requestFocus();
						
						if (!tfDate.getText().isEmpty()) {
							btnLetter.setEnabled(true);
						}
						
						setOrderButtonEnabled();
					}
				});
				
				frame.setVisible(true);
			}
		});
		
		String number;
		
		tfLetter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
					setLetter(letter);
				} else {
					setLetter("");
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				tfLetter.setText(letter);
			}
		});

		btnLetter.addActionListener(e -> {
			LetterFrame frame = new LetterFrame(tfLetter.getText(), lbTitle.getText(), tfAddr.getText(), tfDate.getText());
			
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if (selectedLetter > 0) {
						tfLetter.setText(String.valueOf(selectedLetter));	
						letter = String.valueOf(selectedLetter);
					}
				}
			});
			
			frame.setVisible(true);
		});
		
		btnPrev.addActionListener(e -> btnPrev());
		btnNext.addActionListener(e -> btnNext());
		btnOrder.addActionListener(e -> order());
		
		weddingInfo();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isActive = false;
			};
		});
		
		// https://stackoverflow.com/questions/20346661/java-fade-in-and-out-of-images
		// https://stackoverflow.com/questions/9417356/bufferedimage-resize
		
		Thread t = new Thread(){
			@Override
			public void run() {
				String name = weddings.get(index).name;

				File[] files = new File(String.format("./datafile/웨딩홀/%s/", name)).listFiles();
				
				while(isActive) {
					for (int i = 0; i < files.length; i++) {
						if (name.equals(weddings.get(index).name) == false) {
							name = weddings.get(index).name;
							files = new File(String.format("./datafile/웨딩홀/%s/", name)).listFiles();
							break;
						}

						try {
							BufferedImage bim = ImageIO.read(new File(String.format("./datafile/웨딩홀/%s/%s", name, files[i].getName())));
							Image temp = bim.getScaledInstance(lbImg.getWidth(), lbImg.getHeight(), Image.SCALE_SMOOTH);
							BufferedImage nbim = new BufferedImage(lbImg.getWidth(), lbImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
							Graphics2D g2d = nbim.createGraphics();

							for (int amp = 0; amp <=200; amp++) {
								sleep(5);
								g2d.drawImage(temp, 0, 0, null);
								RescaleOp r = new RescaleOp(new float[]{1f, 1f, 1f, (float) amp / 200}, new float[]{0, 0, 0, 0}, null);
								BufferedImage filter = r.filter(nbim, null);
								lbImg.setIcon(new ImageIcon(filter));
							}
							for (int amp = 200; amp >=0; amp--) {
								sleep(5);
								g2d.drawImage(temp, 0, 0, null);
								RescaleOp r = new RescaleOp(new float[]{1f, 1f, 1f, (float) amp / 200}, new float[]{0, 0, 0, 0}, null);
								BufferedImage filter = r.filter(nbim, null);
								lbImg.setIcon(new ImageIcon(filter));
							}
						} catch (Exception ex) {
							System.err.println(ex);
						}
					}

					System.out.println(name);
				}
			}
		};
		t.start();
	}
	
	public static void main(String[] args) {
		new SearchFrame().setVisible(true);
	}

	private void setLetter(String l) {
		this.letter = l;
	}
	
	private void setOrderButtonEnabled() {
		if (!tfPeopleStr.getText().isEmpty() && !tfDate.getText().isEmpty()) {
			btnOrder.setEnabled(true);
		} else {
			btnOrder.setEnabled(false);
		}
	}
	
	private void order() {
		
		if (Integer.valueOf(tfPeople.getText()) < Integer.valueOf(tfPeopleStr.getText())) {
			eMessage("수용인원보다 작게 입력하세요.");
			
		} else {
			
			Wedding wedding = weddings.get(index);
			
			int mealPrice = Integer.valueOf(tfMealPrice.getText());
			int people = Integer.valueOf(tfPeopleStr.getText());
			int letter = tfLetter.getText().equals("") ? 0 : Integer.valueOf(tfLetter.getText());
			
			openFrame(new PayFrame(wedding, mealPrice, people, letter, tfDate.getText()), new WeddinghallFrame(weddings, index));
		}
	}
	
	private void btnPrev() {
		if (index == 0) {
			index = weddings.size() -1;
		} else {
			index--;
		}
		
		weddingInfo();
	}
	
	private void btnNext() {
		if (index == weddings.size() -1 ) {
			index = 0;
		} else {
			index++;
		}
		weddingInfo();	
	}
	
	private void weddingInfo() {
		Wedding wedding = weddings.get(index);
		
		lbTitle.setText(wedding.name);
		tfAddr.setText(wedding.address);
		tfPeople.setText(String.valueOf(wedding.people));
		tfHallPrice.setText(String.valueOf(wedding.price));
		tfWedding.setText(wedding.w_ty);
		tfMeal.setText(wedding.m_ty);
		lbImg.setIcon(getImageIcon(String.format("./datafile/웨딩홀/%s/%s1.jpg", wedding.name, wedding.name), lbImg.getWidth(), lbImg.getHeight()));
		
		try {
			ResultSet rs = dbManager.executeQuery("select * from mealtype where m_name = ?", wedding.m_ty);
			if (rs.next()) {
				tfMealPrice.setText(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

