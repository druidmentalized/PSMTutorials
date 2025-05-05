package org.psm.task7;

import org.jfree.data.xy.DefaultXYZDataset;

public class PlateSimulator {

    public static SimulationResult simulate(int N, double tol) {
        int size = N + 2;
        double[][] T = new double[size][size];

        for (int i = 0; i < size; i++) {
            T[i][0] = 200;
            T[i][size - 1] = 150;
            T[0][i] = 100;
            T[size - 1][i] = 50;
        }

        while (true) {
            double maxDiff = 0;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    double old = T[i][j];
                    T[i][j] = 0.25 * (T[i - 1][j] + T[i + 1][j] + T[i][j - 1] + T[i][j + 1]);
                    maxDiff = Math.max(maxDiff, Math.abs(T[i][j] - old));
                }
            }
            if (maxDiff < tol) break;
        }

        double[] x = new double[N * N];
        double[] y = new double[N * N];
        double[] z = new double[N * N];
        int idx = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                x[idx] = i;
                y[idx] = j;
                z[idx] = T[i][j];
                idx++;
            }
        }

        DefaultXYZDataset ds = new DefaultXYZDataset();
        ds.addSeries("Temperature", new double[][]{x, y, z});
        return new SimulationResult(ds);
    }
}