package org.zette.worldgen;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class World {
    private final JNoise noise;
    private final ArrayList<Chunk> chunks;

    public World() {
        noise = JNoise.newBuilder()
                .perlin(1337, Interpolation.QUADRATIC, FadeFunction.CUBIC_POLY)
                .scale(1.0 / 64.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

        chunks = new ArrayList<>();
        chunks.add(new Chunk(new Vector2D(0.0, 0.0), noise));
        chunks.add(new Chunk(new Vector2D(-1.0, 0.0), noise));
        chunks.add(new Chunk(new Vector2D(0.0, -1.0), noise));
        chunks.add(new Chunk(new Vector2D(-1.0, -1.0), noise));
    }

    public void draw(Graphics2D g2d) {
        for (Chunk chunk : chunks) {
            chunk.draw(g2d);
        }
    }
}
