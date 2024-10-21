package org.zette.worldgen;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;

import java.awt.*;

public class World {
    private JNoise noise;

    public World() {
        noise = JNoise.newBuilder()
                .perlin(1337, Interpolation.COSINE, FadeFunction.NONE)
                .scale(1.0 / 32.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();
    }

    public void draw(Graphics2D g2d) {
        int SIZE = 255;
        for (int x = -SIZE; x < SIZE; x++) {
            for (int y = -SIZE; y < SIZE; y++) {
                double value = noise.evaluateNoise(x, y);

                int colorValue = (int) (255 * value);
                g2d.setColor(new Color(colorValue, colorValue, colorValue));
                g2d.fillRect(50*x, 50*y, 50, 50);
            }
        }
    }
}
