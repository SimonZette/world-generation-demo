package org.zette.worldgen;

import de.articdive.jnoise.pipeline.JNoise;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;

public class Chunk {
    private static final int CHUNK_SIZE = 32; // Num of blocks in a chunk
    private static final int BLOCK_SIZE = 10; // Size of a block on the screen

    private final Vector2D position; // Represent which chunk this is, NOT where it is on the screen
    private final Color[] blocks;

    public Chunk(Vector2D position, JNoise noise) {
        this.position = position;

        blocks = new Color[CHUNK_SIZE * CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                double value = noise.evaluateNoise(x + position.getX()*CHUNK_SIZE,
                        y + position.getY()*CHUNK_SIZE);

                Color color;
                if (value < 0.35) {
                    color = Color.BLUE;
                } else if (value < 0.40) {
                    color = Color.ORANGE;
                } else {
                    color = Color.GREEN;
                }

                blocks[y* CHUNK_SIZE + x] = color;
            }
        }
    }

    public void draw(Graphics2D g2d) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                g2d.setColor(blocks[y* CHUNK_SIZE + x]);
                g2d.fillRect(x*BLOCK_SIZE + (int) position.getX()*CHUNK_SIZE*BLOCK_SIZE,
                        y*BLOCK_SIZE + (int) position.getY()*CHUNK_SIZE*BLOCK_SIZE,
                        BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }
}
