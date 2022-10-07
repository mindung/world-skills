package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class text1 extends BaseFrame implements ActionListener,Runnable{

	private JProgressBar jpb = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
	
	public text1() {
		super("Test", 300, 120);
		Container con = getContentPane();
		
		con.setLayout(new BorderLayout());
		con.add("North",new JLabel("프로그래스 바 연습"));
		con.add("Center",jpb);
		
		jpb.setStringPainted(true);
		jpb.setString("0%");
		
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		
	
	}

	public static void main(String[] args) {
		new text1().setVisible(true);
		
	}
	
	private boolean bb = true;
	private static int ii;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		if(ii==100) {
			ii=0;
		}
		
		for (int i = ii; i <= 100; i++) {
			if(!bb)break;
			
			try {
				Thread.sleep(500);

				jpb.setValue(i);
				ii=i;
				
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
			
			jpb.setString(i+"%");
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(true) {
			new Thread(this).start();
			
		}
		
	}
}
