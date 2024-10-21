package org.zette.core;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("A Big Game");
        setSize(1280, 720);

        Game panel = new Game();
        add(panel);
        addComponentListener(panel);
        addKeyListener(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
