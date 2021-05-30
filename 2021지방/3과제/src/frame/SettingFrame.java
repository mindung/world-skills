package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

public class SettingFrame extends BaseFrame {
    
    public SettingFrame() {
        super("", 300, 300);

        Chart chart = new Chart();

        add(chart, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new SettingFrame().setVisible(true);
    }

    class Chart extends JPanel {

        private int percent = 1;
        
        public Chart() {
            setBackground(Color.WHITE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    Thread thread = new Thread() {
                        public void run() {
                            while(percent < 100) {
                                percent += 1;
        
                                try {
                                    Thread.sleep(20);
                                    repaint();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            EventQueue.invokeLater(() -> {
                                iMessage("DB셋팅 완료");
                                dispose();
                            });
                        };
                    };
        
                    thread.start();
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(Color.BLUE);
            g.fillArc(50, 35, 200, 200, 90, (int) -(3.6 * percent));
            g.setColor(Color.WHITE);
            g.fillArc(75, 60, 150, 150, 0, 360);
            g.setColor(Color.BLACK);
            g.drawString(String.format("%d%%", percent), 135, 140);
        }
    }
}
