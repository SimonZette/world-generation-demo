package org.zette.entities;

import java.awt.image.BufferedImage;

public class Bush extends Entity {
    public Bush(double x, double y, BufferedImage image) {
        super(x, y-20, 50, 70, image);
    }
}
