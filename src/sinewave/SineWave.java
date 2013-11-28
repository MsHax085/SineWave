package sinewave;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class SineWave implements Runnable {
    
    private final JFrame window;

    public static void main(final String[] args) {
        final SineWave sw = new SineWave();
    }
    
    public SineWave() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("SineWave");
        window.setSize(1000, 600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        
        window.setVisible(true);
        window.createBufferStrategy(2);
        
        final Thread thread = new Thread(this);
        thread.start();
    }
    
    int angle = 0;
    int dx = -200;
    
    public void draw() {
        
        final BufferStrategy bf = window.getBufferStrategy();
        Graphics g = null;
        
        try {
            g = bf.getDrawGraphics();
            
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1000, 600);// Background
            
            g.setColor(Color.CYAN);
            g.drawLine(0, 300, 1000, 300);
            
            //  y = amplitude * sin(x + period)
            
            g.setColor(Color.WHITE);
            
            double pgx = 0;// Store specified point
            int pgy = 0;
            
            for (int gx = 200; gx < 1000; gx += 5) {
                
                double gx_rad = Math.toRadians(gx + dx);
                int y = (int) (300 - (100 * Math.sin(gx_rad * (Math.PI * 0.2))));
                
                g.drawLine(gx, y, gx, y);
                
                if (gx == 700) {
                    pgy = y;
                    pgx = gx_rad;
                }
                
            }
            
            g.setColor(Color.DARK_GRAY);
            g.fillOval(100, 200, 200, 200);
            
            g.setColor(Color.CYAN);
            g.drawOval(100, 200, 200, 200);
            
            int x2 = (int) (Math.cos(pgx * (Math.PI * 0.2)) * 100) + 200;
            g.setColor(Color.RED);
            g.drawLine(x2, pgy, 700, pgy);
            g.drawLine(200, 300, x2, pgy);
            
            g.setColor(Color.CYAN);
            g.fillOval(690, pgy - 10, 20, 20);
            
            dx += 5;
        
        } finally {
            if (g != null) {
                g.dispose();
            }
        }
        
        // Show buffer
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void run() {
        while (true) {
            
            draw();
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(SineWave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
