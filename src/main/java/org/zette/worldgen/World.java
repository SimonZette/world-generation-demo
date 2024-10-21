package org.zette.worldgen;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;

import java.awt.*;

public class World {
    private final JNoise noise;

    public World() {
        noise = JNoise.newBuilder()
                .perlin(1337, Interpolation.QUADRATIC, FadeFunction.CUBIC_POLY)
                .scale(1.0 / 64.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();
    }

    public void draw(Graphics2D g2d) {
        int GRID_SIZE = 255;
        int RECT_SIZE = 50;

        for (int x = -GRID_SIZE; x < GRID_SIZE; x++) {
            for (int y = -GRID_SIZE; y < GRID_SIZE; y++) {
                double value = noise.evaluateNoise(x, y);

                Color color;
                if (value < 0.35) {
                    color = Color.BLUE;
                } else if (value < 0.40) {
                    color = Color.ORANGE;
                } else {
                    color = Color.GREEN;
                }

                g2d.setColor(color);
                g2d.fillRect(x*RECT_SIZE, y*RECT_SIZE, RECT_SIZE, RECT_SIZE);
            }
        }
    }
}
