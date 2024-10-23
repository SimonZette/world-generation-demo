package org.zette.entities;

import org.zette.Util;

import java.awt.image.BufferedImage;

public class Bush extends Entity {
    private static final BufferedImage image = Util.loadImage("images/bush.png");

    public Bush(double x, double y) {
        super(x, y-20, 50, 70, image);
    }
}
