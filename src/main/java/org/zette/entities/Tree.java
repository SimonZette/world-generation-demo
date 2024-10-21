package org.zette.entities;

import java.io.IOException;

public class Tree extends Entity {
    public Tree(int x, int y) throws IOException {
        super(x, y, 50, 100, "/images/tree.png");
    }
}
