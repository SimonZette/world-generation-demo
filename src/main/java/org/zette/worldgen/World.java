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

    private Vector2D oldChunkPosition;

    public World() {
        noise = JNoise.newBuilder()
                .perlin(1337, Interpolation.QUADRATIC, FadeFunction.CUBIC_POLY)
                .scale(1.0 / 64.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

        chunks = new ArrayList<>();
    }

    public void generateChunks(Vector2D position) {
        Vector2D chunkPosition = position
                .scalarMultiply(1.0 / Chunk.CHUNK_SIZE)
                .scalarMultiply(1.0 / Chunk.BLOCK_SIZE);
        chunkPosition = new Vector2D(Math.floor(chunkPosition.getX()), Math.floor(chunkPosition.getY()));

        if (!chunkPosition.equals(oldChunkPosition)) {
            chunks.add(new Chunk(chunkPosition, noise));
        }
        oldChunkPosition = chunkPosition;
    }

    public void draw(Graphics2D g2d) {
        for (Chunk chunk : chunks) {
            chunk.draw(g2d);
        }
    }
}
