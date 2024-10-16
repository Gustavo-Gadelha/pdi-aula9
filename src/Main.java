import factory.KernelFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // Escrever um código para ler todas as imagens limpas existentes em
        // uma pasta (pode chamar de "limpas") e aplicar ruído nelas de forma
        // automática e salvar em uma pasta chamada "sujas"
        File images = new File("images");
        File[] files = images.listFiles();

        assert files != null;
        for (File file : files) {
            String name = file.getName();
            if (name.endsWith(".png") || name.endsWith(".jpg")) {
                applyNoise(file, 0.05, "images/noise");
            }
        }

        // Escrever um código para ler todas as imagens com ruído existente na pasta e limpar
        // o ruído de todas ao máximo que for possível aplicando Kernel 3x3, 5x5, 7x7 ou 9x9,
        // e salvar em uma pasta chamada "limpas"
        KernelFactory factory = KernelFactory.getInstance();
        double[][] gaussian3x3 = factory.gaussian(3, 1);
        double[][] gaussian5x5 = factory.gaussian(5, 2);
        double[][] gaussian7x7 = factory.gaussian(7, 3);
        double[][] gaussian9x9 = factory.gaussian(9, 4);

        File noiseImages = new File("noise_images");
        File[] noiseFiles = noiseImages.listFiles();

        assert noiseFiles != null;

        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            for (File file : noiseFiles) {
                String name = file.getName();
                if (name.endsWith(".png") || name.endsWith(".jpg")) {
                    executor.submit(new FilterTask(file, gaussian3x3, "noise_images/3x3"));
                    executor.submit(new FilterTask(file, gaussian5x5, "noise_images/5x5"));
                    executor.submit(new FilterTask(file, gaussian7x7, "noise_images/7x7"));
                    executor.submit(new FilterTask(file, gaussian9x9, "noise_images/9x9"));
                }
            }
        }
    }

    private static void createNoise() {

    }

    private static void applyNoise(File input, double noise, String pathPrefix) {
        if (noise < 0 || noise > 1) throw new RuntimeException("Noise level should be between 0 and 1");

        // TODO: Check if directory exists, create if it doesn't
        File output = new File(pathPrefix, input.getName());

        try {
            BufferedImage image = ImageIO.read(input);
            BufferedImage result = Noise.apply(image, noise);
            ImageIO.write(result, "png", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            System.err.println("Can't read file: " + input);
        }
    }
}
