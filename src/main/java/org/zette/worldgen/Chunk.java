package org.zette.worldgen;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.zette.entities.Bush;
import org.zette.entities.Entity;
import org.zette.entities.Rock;

import java.awt.*;
import java.util.ArrayList;

public class Chunk {
    public static final int CHUNK_SIZE = 32; // Num of blocks in a chunk's side
    public static final int BLOCK_SIZE = 50; // Size of a side of a block on the screen

    private final Vector2D position; // Represent which chunk this is, NOT where it is on the screen

    private final Color[] blocks;
    private final ArrayList<Entity> entities;

    public Chunk(Vector2D position, WorldNoise noise) {
        this.position = position;

        blocks = new Color[CHUNK_SIZE * CHUNK_SIZE];
        entities = new ArrayList<>();

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                double chunkX = x + position.getX()*CHUNK_SIZE;
                double chunkY = y + position.getY()*CHUNK_SIZE;

                double height = noise.getHeight(chunkX, chunkY);
                double entityValue = noise.getEntity(chunkX, chunkY);

                // Calculate ground type based on height
                Color color;
                if (height < 0.35) {
                    color = Color.BLUE;
                } else if (height < 0.40) {
                    color = Color.ORANGE;
                } else {
                    color = Color.GREEN;
                }
                blocks[y*CHUNK_SIZE + x] = color;

                // Calculate where to put which entities
                if (height > 0.45) {
                    if (entityValue > 0.95) {
                        entities.add(new Rock(chunkX * BLOCK_SIZE, chunkY * BLOCK_SIZE));
                    } else if (entityValue > 0.90) {
                        entities.add(new Bush(chunkX * BLOCK_SIZE, chunkY * BLOCK_SIZE));
                    }
                }
            }
        }
    }

    public void drawGround(Graphics2D g2d) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                g2d.setColor(blocks[y*CHUNK_SIZE + x]);
                g2d.fillRect(x*BLOCK_SIZE + (int) position.getX()*CHUNK_SIZE*BLOCK_SIZE,
                        y*BLOCK_SIZE + (int) position.getY()*CHUNK_SIZE*BLOCK_SIZE,
                        BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    public void drawEntities(Graphics2D g2d) {
        for (Entity entity : entities) {
            entity.draw(g2d);
        }
    }

    public Vector2D getPosition() {
        return position;
    }
}
