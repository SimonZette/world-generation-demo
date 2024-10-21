package org.zette.entities;

public class Player extends Entity {
    private int speed;

    public Player(int x, int y, int speed) {
        super(x, y, 50, 50, "/images/player.png");
        this.speed = speed;
    }

    public void moveLeft() {
        x -= speed;
    }
    public void moveRight() {
        x += speed;
    }
    public void moveUp() {
        y -= speed;
    }
    public void moveDown() {
        y += speed;
    }
}
