package org.zette.core;

import org.zette.entities.Player;
import org.zette.entities.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    // Contains all the currently pressed keys
    private final HashSet<Integer> pressedKeys = new HashSet<>();

    private Player player;
    private Tree tree;

    public GamePanel() {
        player = new Player(400.0, 200.0, 7.0);
        tree = new Tree(100.0, 200.0);

        // Create a timer that updates the game FPS times per second
        // Update logic happens in actionPerformed
        final int FPS = 60;
        Timer timer = new Timer(1000 / FPS, this);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        handleKeys();

        player.update();

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        player.draw(g);
        tree.draw(g);
    }

    private void handleKeys() {
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            player.moveLeft();
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            player.moveRight();
        }
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            player.moveUp();
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            player.moveDown();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // Required by KeyListener, but not needed here
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}
