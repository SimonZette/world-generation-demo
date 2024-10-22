package org.zette.entities;

import java.awt.image.BufferedImage;

public class Rock extends Entity {
    public Rock(double x, double y, BufferedImage image) {
        super(x-5, y-5, 60, 60, image);
    }
}
