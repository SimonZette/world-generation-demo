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


    public void updateChunks(Vector2D worldPosition) {
        removeChunks(worldPosition);
        generateChunks(worldPosition);
    }

    private void removeChunks(Vector2D worldPosition) {
        Vector2D centerChunkPosition = worldToChunkPosition(worldPosition);

        for (Vector2D position : getChunkPositions()) {
            if (Math.abs(position.getX() - centerChunkPosition.getX()) > renderDistance/2.0 ||
                    Math.abs(position.getY() - centerChunkPosition.getY()) > renderDistance/2.0) {
               for (int i = 0; i < chunks.size(); i++) {
                   if (chunks.get(i).getPosition().equals(position)) {
                       chunks.remove(i);
                       break;
                   }
               }
            }
        }
    }

    private void generateChunks(Vector2D worldPosition) {
        Vector2D centerChunkPosition = worldToChunkPosition(worldPosition);

        HashSet<Vector2D> currentChunkPositions = getChunkPositions();

        if (!centerChunkPosition.equals(oldChunkPosition)) {
            for (int x = -renderDistance/2; x < (renderDistance+1)/2; x++) {
                for (int y = -renderDistance/2; y < (renderDistance+1)/2; y++) {

                    Vector2D chunkPosition = centerChunkPosition.add(new Vector2D(x, y));
                    if (!currentChunkPositions.contains(chunkPosition)) { // As to not regenerate loaded chunks
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


    private Vector2D worldToChunkPosition(Vector2D worldPosition) {
        Vector2D chunkPosition = worldPosition
                .scalarMultiply(1.0 / Chunk.CHUNK_SIZE)
                .scalarMultiply(1.0 / Chunk.BLOCK_SIZE);
        chunkPosition = new Vector2D(Math.floor(chunkPosition.getX()), Math.floor(chunkPosition.getY()));
        return chunkPosition;
    }

    private HashSet<Vector2D> getChunkPositions() {
        HashSet<Vector2D> chunkPositions = new HashSet<>();

        for (Chunk chunk : chunks) {
            chunkPositions.add(chunk.getPosition());
        }

        return chunkPositions;
    }
}
