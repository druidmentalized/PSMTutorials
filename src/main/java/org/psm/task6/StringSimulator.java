package org.psm.task6;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.HashSet;
import java.util.Set;

public class StringSimulator {

    public static SimulationResult runSimulation(int N, double L, double dt, double T) {
        int points = N + 1;
        double dx = L / N;
        int steps = (int) (T / dt);

        double[] y = new double[points];
        double[] v = new double[points];
        double[] a = new double[points];
        double[] yHalf = new double[points];
        double[] vHalf = new double[points];

        // record shapes at these times:
        Set<Double> recordTimes = new HashSet<>();
        double[] times = {0.05, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5};
        for (double t : times) recordTimes.add(t);

        XYSeriesCollection shapeDataset = new XYSeriesCollection();
        XYSeriesCollection energyDataset = new XYSeriesCollection();

        // initial shape
        XYSeries initial = new XYSeries("t=0");
        for (int i = 0; i < points; i++) {
            double x = i * dx;
            y[i] = Math.sin(x) / 1000.0;
            v[i] = 0;
            initial.add(x, y[i]);
        }
        shapeDataset.addSeries(initial);

        // energy series
        XYSeries epSeries = new XYSeries("Ep");
        XYSeries ekSeries = new XYSeries("Ek");
        XYSeries etSeries = new XYSeries("Et");

        // time‐march
        for (int step = 0; step <= steps; step++) {
            double t = step * dt;

            // compute acceleration at full step
            for (int i = 1; i < points - 1; i++) {
                a[i] = (y[i - 1] - 2 * y[i] + y[i + 1]) / (dx * dx);
            }
            a[0] = a[points - 1] = 0;

            // midpoint estimates
            for (int i = 0; i < points; i++) {
                yHalf[i] = y[i] + v[i] * dt / 2;
                vHalf[i] = v[i] + a[i] * dt / 2;
            }
            // recompute accel at midpoint
            for (int i = 1; i < points - 1; i++) {
                a[i] = (yHalf[i - 1] - 2 * yHalf[i] + yHalf[i + 1]) / (dx * dx);
            }

            // full update
            for (int i = 0; i < points; i++) {
                y[i] += vHalf[i] * dt;
                v[i] += a[i] * dt;
            }

            // energies
            double ek = 0, ep = 0;
            for (int i = 0; i < points; i++) {
                ek += 0.5 * v[i] * v[i] * dx;
            }
            for (int i = 0; i < points - 1; i++) {
                double dy = y[i + 1] - y[i];
                ep += dy * dy / (2 * dx);
            }
            epSeries.add(t, ep);
            ekSeries.add(t, ek);
            etSeries.add(t, ek + ep);

            // record shapes
            if (recordTimes.contains(Math.round(t * 100.0) / 100.0)) {
                XYSeries s = new XYSeries("t=" + t);
                for (int i = 0; i < points; i++) {
                    s.add(i * dx, y[i]);
                }
                shapeDataset.addSeries(s);
            }
        }

        energyDataset.addSeries(epSeries);
        energyDataset.addSeries(ekSeries);
        energyDataset.addSeries(etSeries);

        return new SimulationResult(shapeDataset, energyDataset);
    }
}