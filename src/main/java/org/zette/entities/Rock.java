package org.zette.entities;

import org.zette.Util;

import java.awt.image.BufferedImage;

public class Rock extends Entity {
    private static final BufferedImage image = Util.loadImage("images/rock.png");

    public Rock(double x, double y) {
        super(x-5, y-5, 60, 60, image);
    }
}
