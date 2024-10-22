package org.zette.worldgen;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class World {
    private final JNoise noise;
    private final ArrayList<Chunk> chunks;

    private int renderDistance;
    private Vector2D oldChunkPosition;

    public World(int renderDistance) {
        noise = JNoise.newBuilder()
                .perlin(1337, Interpolation.QUADRATIC, FadeFunction.CUBIC_POLY)
                .scale(1.0 / 64.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

        chunks = new ArrayList<>();

        this.renderDistance = renderDistance;
    }

    public void generateChunks(Vector2D position) {
        Vector2D centerChunkPosition = position
                .scalarMultiply(1.0 / Chunk.CHUNK_SIZE)
                .scalarMultiply(1.0 / Chunk.BLOCK_SIZE);
        centerChunkPosition = new Vector2D(Math.floor(centerChunkPosition.getX()), Math.floor(centerChunkPosition.getY()));

        if (!centerChunkPosition.equals(oldChunkPosition)) {
            for (int x = -renderDistance/2; x < (renderDistance+1)/2; x++) {
                for (int y = -renderDistance/2; y < (renderDistance+1)/2; y++) {

                    Vector2D chunkPosition = centerChunkPosition.add(new Vector2D(x, y));
                    if (!getChunkPositions().contains(chunkPosition)) { // As to not regenerate loaded chunks
                        chunks.add(new Chunk(chunkPosition, noise));
                    }
                }
            }
        }
        oldChunkPosition = centerChunkPosition;
    }

    public void draw(Graphics2D g2d) {
        for (Chunk chunk : chunks) {
            chunk.draw(g2d);
        }
    }

    private HashSet<Vector2D> getChunkPositions() {
        HashSet<Vector2D> chunkPositions = new HashSet<>();

        for (Chunk chunk : chunks) {
            chunkPositions.add(chunk.getPosition());
        }

        return chunkPositions;
    }
}
