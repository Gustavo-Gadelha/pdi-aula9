package factory;

import java.util.Arrays;

public class KernelFactory {
    private static KernelFactory instance;

    private KernelFactory() {
    }

    public static KernelFactory getInstance() {
        if (instance == null) {
            instance = new KernelFactory();
        }

        return instance;
    }

    public double[][] median(int size) {
        double[][] convolutionMatrix = new double[size][size];

        for (double[] line : convolutionMatrix) {
            Arrays.fill(line, 1.0 / (size * size));
        }

        return convolutionMatrix;
    }

    public double[][] laplacianHighlight(int size) {
        double[][] convolutionMatrix = new double[size][size];

        Arrays.stream(convolutionMatrix).forEach(matrix -> Arrays.fill(matrix, -1));
        convolutionMatrix[size / 2][size / 2] = size * size;

        return convolutionMatrix;
    }

    public double[][] laplacianBorder(int size) {
        double[][] convolutionMatrix = new double[size][size];

        Arrays.stream(convolutionMatrix).forEach(matrix -> Arrays.fill(matrix, -1));
        convolutionMatrix[size / 2][size / 2] = size * size - 1;

        return convolutionMatrix;
    }

    public double[][] gaussian(int size, double sigma) {
        double[][] convolutionMatrix = new double[size][size];
        double sum = 0.0;
        int offset = size / 2;

        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                double exponent = -(i * i + j * j) / (2 * sigma * sigma);
                convolutionMatrix[i + offset][j + offset] = Math.exp(exponent) / (2 * Math.PI * sigma * sigma);
                sum += convolutionMatrix[i + offset][j + offset];
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                convolutionMatrix[i][j] /= sum;
            }
        }

        return convolutionMatrix;
    }
}
