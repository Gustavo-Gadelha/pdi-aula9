import tool.Convolution;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilterTask implements Runnable {
    private final File input;
    private final File output;
    private final double[][] filter;

    public FilterTask(File input, double[][] filter, String dst) {
        this.input = input;
        this.filter = filter;
        this.output = Path.of(dst, input.getName()).toFile();
    }

    @Override
    public void run() {
        // TODO: Check if directory exists, create if it doesn't
        try {
            BufferedImage image = ImageIO.read(input);
            BufferedImage result = Convolution.apply(image, filter);
            ImageIO.write(result, "png", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            System.err.println("Can't read file: " + input);
        }
    }
}
