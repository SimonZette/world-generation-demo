package org.zette.core;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;

public class Camera {
    // The upper left corner of the cameras view
    private Vector2D offset;

    public Camera() {
        offset = Vector2D.ZERO;
    }


    public void applyTransform(Graphics g) {
        g.translate((int) offset.getX(), (int) offset.getY());
    }


    public void setOffset(Vector2D newTarget) {
        offset = newTarget;
    }
}
