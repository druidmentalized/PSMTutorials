package org.psm.task7;

import org.apache.commons.math3.linear.*;
import org.jfree.data.xy.DefaultXYZDataset;

public class PlateSimulator {

    public static SimulationResult simulate(int N) {
        RealMatrix A = buildCoefficientMatrix(N);
        RealVector b = buildRightHandSide(N);
        RealVector x = solveSystem(A, b);
        DefaultXYZDataset dataset = buildDatasetFromSolution(x, N);
        return new SimulationResult(dataset);
    }

    private static RealMatrix buildCoefficientMatrix(int N) {
        int size = N * N;
        RealMatrix A = new Array2DRowRealMatrix(size, size);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = index(i, j, N);
                A.setEntry(k, k, -4);
                setInteriorNeighbors(A, i, j, N, k);
            }
        }
        return A;
    }

    private static void setInteriorNeighbors(RealMatrix A, int i, int j, int N, int k) {
        if (j > 0)     A.setEntry(k, index(i, j - 1, N), 1); // Left
        if (j < N - 1) A.setEntry(k, index(i, j + 1, N), 1); // Right
        if (i > 0)     A.setEntry(k, index(i - 1, j, N), 1); // Top
        if (i < N - 1) A.setEntry(k, index(i + 1, j, N), 1); // Bottom
    }

    private static RealVector buildRightHandSide(int N) {
        RealVector b = new ArrayRealVector(N * N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = index(i, j, N);
                applyBoundaryCondition(b, i, j, N, k);
            }
        }
        return b;
    }

    private static void applyBoundaryCondition(RealVector b, int i, int j, int N, int k) {
        if (j == 0)     b.addToEntry(k, -200); // Left
        if (j == N - 1) b.addToEntry(k, -150); // Right
        if (i == 0)     b.addToEntry(k, -100); // Top
        if (i == N - 1) b.addToEntry(k, -50);  // Bottom
    }

    private static RealVector solveSystem(RealMatrix A, RealVector b) {
        DecompositionSolver solver = new LUDecomposition(A).getSolver();
        return solver.solve(b);
    }

    private static DefaultXYZDataset buildDatasetFromSolution(RealVector x, int N) {
        int size = N * N;
        double[] xCoord = new double[size];
        double[] yCoord = new double[size];
        double[] zCoord = new double[size];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = i * N + j;
                xCoord[k] = j + 1;
                yCoord[k] = N - i;
                zCoord[k] = x.getEntry(k);
            }
        }

        DefaultXYZDataset ds = new DefaultXYZDataset();
        ds.addSeries("Temperature", new double[][]{xCoord, yCoord, zCoord});
        return ds;
    }

    // Helper
    private static int index(int i, int j, int N) {
        return i * N + j;
    }
}