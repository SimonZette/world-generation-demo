package org.zette;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Util {
    private static final BufferedImage noTexture = loadImage("images/no_texture.png");
    public static BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(Objects.requireNonNull(Util.class.getResourceAsStream("/" + imagePath)));
        } catch (NullPointerException e) {
            return noTexture;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
