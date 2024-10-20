package org.zette.core;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("A Big Game");
        setSize(1280, 720);

        GamePanel panel = new GamePanel();
        add(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
