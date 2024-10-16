import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Noise {
    public static final Random random = new Random();

    public static BufferedImage apply(BufferedImage image, double noise) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (random.nextDouble() < noise) {
                    Noise.saltOrPepper(output, i, j);
                } else {
                    output.setRGB(i, j, image.getRGB(i, j));
                }
            }
        }

        return output;
    }

    private static void saltOrPepper(BufferedImage image, int i, int j) {
        if (random.nextBoolean()) {
            image.setRGB(i, j, Color.WHITE.getRGB());
        } else {
            image.setRGB(i, j, Color.BLACK.getRGB());
        }
    }
}
