package org.zette.core;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.zette.Util;
import org.zette.entities.Player;
import org.zette.entities.Bush;
import org.zette.entities.Rock;
import org.zette.worldgen.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class Game extends JPanel implements ActionListener, ComponentListener, KeyListener {
    // Contains all the currently pressed keys
    private final HashSet<Integer> pressedKeys = new HashSet<>();
    private final Camera c = new Camera();
    private final World w = new World();

    private Player player;
    private Bush bush;
    private Rock rock;

    public Game() {
        player = new Player(0.0, 0.0);

        var image = Util.loadImage("images/bush.png");
        bush = new Bush(100.0, 100.0, image);

        image = Util.loadImage("images/rock.png");
        rock = new Rock(200.0, 100.0, image);

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
        c.setOffset(player.getPosition() // Start at the players upper left corner
                .subtract(new Vector2D(this.getWidth() / 2.0, this.getHeight() / 2.0)) // Translate to the middle of the screen
                .add(new Vector2D(player.getWidth() / 2.0, player.getHeight() / 2.0)) // Translate to the middle of the player
                .scalarMultiply(-1.0));
        w.updateChunks(player.getPosition());

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        c.applyTransform(g2d);

        w.draw(g2d);

        player.draw(g2d);
        bush.draw(g2d);
        rock.draw(g2d);
    }

    private void handleKeys() {
        // Player speed
        if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
            player.setSpeed(20.0);
        } else {
            player.setSpeed(7.0);
        }
        // Player movement
        if (pressedKeys.contains(KeyEvent.VK_LEFT) ||
                pressedKeys.contains(KeyEvent.VK_A)) {
            player.moveLeft();
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT) ||
                pressedKeys.contains(KeyEvent.VK_D)) {
            player.moveRight();
        }
        if (pressedKeys.contains(KeyEvent.VK_UP) ||
                pressedKeys.contains(KeyEvent.VK_W)) {
            player.moveUp();
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN) ||
                pressedKeys.contains(KeyEvent.VK_S)) {
            player.moveDown();
        }

        // Zooming
        if (pressedKeys.contains(KeyEvent.VK_PAGE_UP)) {
            c.zoom(0.05);
            w.updateRenderDistance(getWidth(), getHeight(), c.getScale());
            w.updateChunks(player.getPosition());
        }
        if (pressedKeys.contains(KeyEvent.VK_PAGE_DOWN)) {
            c.zoom(-0.05);
            w.updateRenderDistance(getWidth(), getHeight(), c.getScale());
            w.updateChunks(player.getPosition());
        }
    }


    // Required by KeyListener, but not needed here
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }


    @Override
    public void componentResized(ComponentEvent e) {
        c.setTarget(new Vector2D(getWidth() / 2.0, getHeight() / 2.0));
        w.updateRenderDistance(getWidth(), getHeight(), c.getScale());
        w.updateChunks(player.getPosition());
    }

    // These are all not needed
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
}
