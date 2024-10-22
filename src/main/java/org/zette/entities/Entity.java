package org.zette.entities;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected Vector2D position;
    protected int width, height;
    private final BufferedImage image;

    public Entity(double x, double y, int width, int height, BufferedImage image) {
        position = new Vector2D(x, y);
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image,
                (int) position.getX(), (int) position.getY(),
                width, height,
                null);
    }


    public Vector2D getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
