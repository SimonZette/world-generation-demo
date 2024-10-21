package org.zette.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {
    int x, y, width, height;
    BufferedImage image;

    public Entity(int x, int y, int width, int height, String imagePath) throws IOException {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //TODO: Probably better to share images between similar entities
        // Also ugly ahh method calls
        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
}
