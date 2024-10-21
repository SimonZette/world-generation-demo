package org.zette.core;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;

public class Camera {
    private Vector2D target; // The point on the screen where zooming happens
    private Vector2D offset; // The upper left corner of the cameras view
    private double scale;

    public Camera() {
        target = Vector2D.ZERO;
        offset = Vector2D.ZERO;
        scale = 1.0;
    }


    public void applyTransform(Graphics2D g2d) {
        g2d.translate((int) target.getX(), (int) target.getY());
        g2d.scale(scale, scale);
        g2d.translate((int) offset.getX(), (int) offset.getY());
    }


    public void setTarget(Vector2D newTarget) {
        target = newTarget;
    }

    public void setOffset(Vector2D newOffset) {
        offset = newOffset;
        offset = offset.subtract(target);
    }

    public void zoom(double scaleDiff) {
        scale = Math.clamp(scale + scaleDiff, 0.5, 2.5);
    }
}
