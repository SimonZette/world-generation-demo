package org.zette.entities;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {
    protected Vector2D position;
    protected int width, height;
    private final BufferedImage image;

    public Entity(double x, double y, int width, int height, String imagePath) {
        position = new Vector2D(x, y);
        this.width = width;
        this.height = height;

        //TODO: Probably better to share images between similar entities
        // Also ugly ahh method calls
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image,
                (int) position.getX(), (int) position.getY(),
                width, height,
                null);
    }
}
