package org.zette.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    public GamePanel() {
        // Create a timer that updates the game FPS times per second
        // Update logic happens in actionPerformed
        final int FPS = 60;
        Timer timer = new Timer(1000 / FPS, this);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    Random generator = new Random();
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // FIXME: This is for testing
        int x = generator.nextInt(100, 800);
        int y = generator.nextInt(100, 400);
        g.drawRect(x, y, 200, 200);
    }

}
