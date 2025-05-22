package org.psm.task8;

import org.jfree.data.xy.XYSeries;

public class LorenzSimulator {
    private static final double A = 10.0;
    private static final double B = 25.0;
    private static final double C = 8.0 / 3.0;

    public static XYSeries simulateEuler(double dt, int steps) {
        XYSeries s = new XYSeries("Euler");
        double x = 1, y = 1, z = 1;
        for (int i = 0; i <= steps; i++) {
            if (Double.isFinite(x) && Double.isFinite(z)) {
                s.add(x, z);
            }
            // k1
            double dx = A * (y - x);
            double dy = -x * z + B * x - y;
            double dz = x * y - C * z;

            // result
            x += dx * dt;
            y += dy * dt;
            z += dz * dt;
        }
        return s;
    }

    public static XYSeries simulateMidpoint(double dt, int steps) {
        XYSeries s = new XYSeries("Midpoint");
        double x = 1, y = 1, z = 1;
        for (int i = 0; i <= steps; i++) {
            s.add(x, z);
            // k1
            double dx1 = A * (y - x);
            double dy1 = -x * z + B * x - y;
            double dz1 = x * y - C * z;

            // mid‐point estimates
            double xm = x + dx1 * dt / 2;
            double ym = y + dy1 * dt / 2;
            double zm = z + dz1 * dt / 2;

            // k2 at midpoint
            double dx2 = A * (ym - xm);
            double dy2 = -xm * zm + B * xm - ym;
            double dz2 = xm * ym - C * zm;

            // full step
            x += dx2 * dt;
            y += dy2 * dt;
            z += dz2 * dt;
        }
        return s;
    }

    public static XYSeries simulateRK4(double dt, int steps) {
        XYSeries s = new XYSeries("RK4");
        double x = 1, y = 1, z = 1;
        for (int i = 0; i <= steps; i++) {
            s.add(x, z);
            // k1
            double dx1 = A * (y - x);
            double dy1 = -x * z + B * x - y;
            double dz1 = x * y - C * z;

            // k2
            double x2 = x + dx1 * dt / 2;
            double y2 = y + dy1 * dt / 2;
            double z2 = z + dz1 * dt / 2;

            double dx2 = A * (y2 - x2);
            double dy2 = -x2 * z2 + B * x2 - y2;
            double dz2 = x2 * y2 - C * z2;

            // k3
            double x3 = x + dx2 * dt / 2;
            double y3 = y + dy2 * dt / 2;
            double z3 = z + dz2 * dt / 2;

            double dx3 = A * (y3 - x3);
            double dy3 = -x3 * z3 + B * x3 - y3;
            double dz3 = x3 * y3 - C * z3;

            // k4
            double x4 = x + dx3 * dt;
            double y4 = y + dy3 * dt;
            double z4 = z + dz3 * dt;

            double dx4 = A * (y4 - x4);
            double dy4 = -x4 * z4 + B * x4 - y4;
            double dz4 = x4 * y4 - C * z4;

            // result
            x += dt * (dx1 + 2 * dx2 + 2 * dx3 + dx4) / 6.0;
            y += dt * (dy1 + 2 * dy2 + 2 * dy3 + dy4) / 6.0;
            z += dt * (dz1 + 2 * dz2 + 2 * dz3 + dz4) / 6.0;
        }
        return s;
    }
}
