package org.zette.entities;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Player extends Entity {
    private Vector2D velocity;
    private double speed;

    public Player(double x, double y) {
        super(x, y, 50, 50, "/images/player.png");
        velocity = Vector2D.ZERO;
        speed = 0.0;
    }


    public void update() {
        // Only move if the velocity isn't zero
        if (velocity.getNorm() != 0.0) {
            velocity = velocity.normalize();
            velocity = velocity.scalarMultiply(speed);
            position = position.add(velocity);
            velocity = Vector2D.ZERO;
        }

        speed = 0.0;
    }

    public void moveLeft() {
        velocity = velocity.add(new Vector2D(-1.0, 0.0));
    }
    public void moveRight() {
        velocity = velocity.add(new Vector2D(1.0, 0.0));
    }
    public void moveUp() {
        velocity = velocity.add(new Vector2D(0.0, -1.0));
    }
    public void moveDown() {
        velocity = velocity.add(new Vector2D(0.0, 1.0));
    }

    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }
}
