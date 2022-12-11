import java.io.*;

public class lab2 {
    public static void main(String[] args) throws Exception {
        double[] X_given = { 1, 1.5, 2, 2.5, 
                             3, 3.5, 4, 4.5, 
                             5, 5.5, 6, 6.5, 
                             7, 7.5, 8, 8.5 
                           };

        double[] Y_given = { 14, 18.222, 18, 17.216, 
                             16.444, 15.778, 15.219, 14.749, 
                             14.352, 14.014, 13.722, 13.469, 
                             13.248, 13.052, 12.879, 12.724 
                           };

        double[][] desired_B = new double[0][];
        double min_criterion = Math.pow(10, 100);
        for (int n = 0; n <= 100; n++) {
            double[][] B = find_b(X_given, Y_given, n);
            double criterion = 0;
            for (int i = 0; i < 16; i++) {
                criterion += Math.pow(function(X_given[i], B) - Y_given[i],
                        2);
            }
            if (criterion < min_criterion) {
                min_criterion = criterion;
                desired_B = B;
            }
        }
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        System.out.println("The value of the least squares test = " + BLUE + (float) min_criterion + RESET);
        System.out.println("The value of the coefficients b for the best model:");
        for (int i = 0; i < desired_B.length; i++) {
            System.out.println("B" + (i) + " = " + BLUE + (float) desired_B[i][0] + RESET);
        }
        System.out.println();
        double[] Y_found = new double[16];
        for (int i = 0; i < 16; i++) {
            Y_found[i] = function(X_given[i], desired_B);
        }
        for (int i = 0; i < 16; i++) {
            System.out.println("Observed Y" + (i + 1) + " = " + BLUE +
                    Y_given[i] + RESET +
                    "; Found Y" + (i + 1) + " = " + BLUE + (float) Y_found[i] + RESET);
        }
        FileWriter file = new FileWriter("lab2.txt");
        for (int i = 0; i < 16; i++) {
            file.write(String.format("%.2f", Y_given[i]) + "\n");
        }
        file.write("\n");
        for (int i = 0; i < 16; i++) {
            file.write(String.format("%.2f", Y_found[i]) + "\n");
        }
        file.close();
    }

    static double[][] find_b(double[] X_given, double[] Y_given, int n) {
        double[][] X = new double[16][n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < 16; j++) {
                X[j][0] = 1;
                X[j][i] = Math.pow(X_given[j], i);
            }
        }
        double[][] Y = new double[16][1];
        for (int i = 0; i < 16; i++) {
            Y[i][0] = Y_given[i];
        }
        double[][] X_t = transpose(X);
        double[][] X_inv = inversion(multiply(X_t, X), X_t.length);
        double[][] B = multiply(multiply(X_inv, X_t), Y);
        return B;
    }

    static double function(double x, double[][] b) {
        double f = 0;
        for (int i = 0; i < b.length; i++) {
            f += b[i][0] * (Math.pow(x, i));
        }
        return f;
    }

    static double[][] inversion(double[][] A, int N) {
        double temp;
        double[][] E = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                E[i][j] = 0f;
                if (i == j)
                    E[i][j] = 1f;
            }
        }
        for (int k = 0; k < N; k++) {
            temp = A[k][k];
            for (int j = 0; j < N; j++) {
                A[k][j] /= temp;
                E[k][j] /= temp;
            }
            for (int i = k + 1; i < N; i++) {
                temp = A[i][k];
                for (int j = 0; j < N; j++) {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        for (int k = N - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = A[i][k];
                for (int j = 0; j < N; j++) {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = E[i][j];
            }
        }
        return E;
    }

    private static double[][] multiply(double[][] a, double[][] b) {
        if (a[0].length != b.length)
            throw new IllegalStateException("invalid dimensions");
        double[][] matrix = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < a[i].length; k++)
                    sum += a[i][k] * b[k][j];
                matrix[i][j] = sum;
            }
        }
        return matrix;
    }

    private static double[][] transpose(double[][] matrix) {
        double[][] transpose = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                transpose[j][i] = matrix[i][j];
        return transpose;
    }
}
