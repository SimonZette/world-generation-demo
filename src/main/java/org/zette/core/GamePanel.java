package org.zette.core;

import org.zette.entities.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    Tree tree;

    public GamePanel() {
        tree = new Tree(100, 200);

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

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        tree.draw(g);
    }
}
