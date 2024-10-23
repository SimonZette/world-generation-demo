package org.zette.worldgen;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class World {
    private final WorldNoise noise;
    private final ArrayList<Chunk> chunks;

    private int renderDistanceX;
    private int renderDistanceY;

    public World() {
        noise = new WorldNoise(1337);

        chunks = new ArrayList<>();

        renderDistanceX = 1;
        renderDistanceY = 1;
    }


    public void updateChunks(Vector2D worldPosition) {
        Vector2D centerChunkPosition = worldToChunkPosition(worldPosition);
        removeChunks(centerChunkPosition);
        generateChunks(centerChunkPosition);
    }

    private void removeChunks(Vector2D centerChunkPosition) {
        for (Vector2D position : getChunkPositions()) {
            if (Math.abs(position.getX() - centerChunkPosition.getX()) > renderDistanceX ||
                    Math.abs(position.getY() - centerChunkPosition.getY()) > renderDistanceY) {
               for (int i = 0; i < chunks.size(); i++) {
                   if (chunks.get(i).getPosition().equals(position)) {
                       chunks.remove(i);
                       break;
                   }
               }
            }
        }
    }

    private void generateChunks(Vector2D centerChunkPosition) {
        HashSet<Vector2D> currentChunkPositions = getChunkPositions();

        for (int x = -renderDistanceX; x <= renderDistanceX; x++) {
            for (int y = -renderDistanceY; y <= renderDistanceY; y++) {

                Vector2D chunkPosition = centerChunkPosition.add(new Vector2D(x, y));
                if (!currentChunkPositions.contains(chunkPosition)) { // As to not regenerate loaded chunks
                    chunks.add(new Chunk(chunkPosition, noise));
                }
            }
        }
    }


    public void updateRenderDistance(int screenWidth, int screenHeight, double scale) {
        renderDistanceX = (int) ((double) screenWidth / Chunk.CHUNK_SIZE / Chunk.BLOCK_SIZE / scale / 2) + 1;
        renderDistanceY = (int) ((double) screenHeight / Chunk.CHUNK_SIZE / Chunk.BLOCK_SIZE / scale / 2) + 1;
    }


    public void draw(Graphics2D g2d) {
        for (Chunk chunk : chunks) {
            chunk.drawGround(g2d);
        }
        for (Chunk chunk : chunks) {
            chunk.drawEntities(g2d);
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
