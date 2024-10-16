package tool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Convolution {
    private Convolution() {
    }

    public static BufferedImage apply(BufferedImage image, double[][] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        int offset = kernel.length / 2;

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        for (int i = offset; i < width - offset; i++) {
            for (int j = offset; j < height - offset; j++) {
                output.setRGB(i, j, Convolution.convolutePixel(image, kernel, offset, i, j).getRGB());

            }
        }

        return output;
    }

    public static Color convolutePixel(BufferedImage image, double[][] kernel, int offset, int row, int col) {
        int r = 0, g = 0, b = 0;

        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                int rgb = image.getRGB(row + i, col + j);
                double weight = kernel[i + offset][j + offset];

                Color color = new Color(rgb);
                r += (int) (weight * color.getRed());
                g += (int) (weight * color.getGreen());
                b += (int) (weight * color.getBlue());
            }
        }

        r = Math.clamp(r, 0, 255);
        g = Math.clamp(g, 0, 255);
        b = Math.clamp(b, 0, 255);

        return new Color(r, g, b);
    }
}
