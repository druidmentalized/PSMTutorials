package org.psm.task4;

import org.jfree.data.xy.XYSeries;

public class RollingObject {
    private static final double G = 9.81;

    private final double mass;
    private final double height;
    private final double radius;
    private final double alpha;
    private final double inertia;

    // Plane length from top to bottom
    private final double planeLength;
    // Constant acceleration
    private final double a;
    private final double epsilon;
    // Output series
    private final XYSeries inclinedSurfaceSeries = new XYSeries("Incline Surface");
    private final XYSeries ballProjectionSeries = new XYSeries("Ball Projection");
    private final XYSeries centerSeries = new XYSeries("Ball Center");
    private final XYSeries ringSeries = new XYSeries("Ball Ring");
    private final XYSeries epSeries = new XYSeries("Ep");
    private final XYSeries ekSeries = new XYSeries("Ek");
    private final XYSeries etSeries = new XYSeries("Et");
    // State variables for Euler integration
    private double s;
    private double v;
    private double beta;
    private double omega;

    public RollingObject(double mass, double height, double radius,
                         double angleDeg, double inertiaFactor) {
        this.mass = mass;
        this.height = height;
        this.radius = radius;
        this.alpha = Math.toRadians(angleDeg);

        this.planeLength = height / Math.sin(alpha);

        this.inertia = inertiaFactor * mass * radius * radius;

        double denominator = 1.0 + (inertia / (mass * radius * radius));
        this.a = G * Math.sin(this.alpha) / denominator;
        this.epsilon = a / radius;

        reset();
    }

    private void reset() {
        s = 0.0;
        v = 0.0;
        beta = 0.0;
        omega = 0.0;

        inclinedSurfaceSeries.clear();
        ballProjectionSeries.clear();
        centerSeries.clear();
        ringSeries.clear();
        epSeries.clear();
        ekSeries.clear();
        etSeries.clear();
    }

    public void simulate(double dt) {
        reset();
        calculateInclinedSurface();

        double time = 0.0;

        while (true) {
            double xCenter = s * Math.cos(-alpha) - radius * Math.sin(-alpha);
            double yCenter = s * Math.sin(-alpha) + radius * Math.cos(-alpha) + height;

            if (xCenter >= 0 && yCenter >= 0) centerSeries.add(xCenter, yCenter);

            double xRing = radius * Math.sin(beta) + xCenter;
            double yRing = radius * Math.cos(beta) + yCenter;

            if (xRing >= 0 && yRing >= 0) ringSeries.add(xRing, yRing);

            double Ep = mass * G * yCenter;
            double EkTrans = 0.5 * mass * (v * v);
            double EkRot = 0.5 * inertia * (omega * omega);
            double Et = Ep + EkTrans + EkRot;

            epSeries.add(time, Ep);
            ekSeries.add(time, EkTrans + EkRot);
            etSeries.add(time, Et);

            double vHalf = v + a * dt / 2;
            double omegaHalf = omega + epsilon * dt / 2;

            s = s + vHalf * dt;
            v = v + a * dt;
            beta = beta + omegaHalf * dt;
            omega = omega + epsilon * dt;

            if (time == 0.0) {
                calculateBallProjection(xCenter, yCenter);
            }

            if (yCenter <= 0) {
                break;
            }

            time += dt;

            if (s >= planeLength) {
                s = planeLength;
                break;
            }
        }
    }

    private void calculateInclinedSurface() {
        double xBottom = height * Math.cos(alpha) / Math.sin(alpha);
        inclinedSurfaceSeries.add(0.0, height);
        inclinedSurfaceSeries.add(xBottom, 0.0);
    }

    private void calculateBallProjection(double xCenter, double yCenter) {
        for (double theta = 0; theta <= 2 * Math.PI; theta += 0.05) {
            double x = xCenter + radius * Math.cos(theta);
            double y = yCenter + radius * Math.sin(theta);
            ballProjectionSeries.add(x, y);
        }
    }

    // Getters for the data series
    public XYSeries getInclinedSurfaceSeries() {
        return inclinedSurfaceSeries;
    }

    public XYSeries getBallProjectionSeries() {
        return ballProjectionSeries;
    }

    public XYSeries getCenterSeries() {
        return centerSeries;
    }

    public XYSeries getRingSeries() {
        return ringSeries;
    }

    public XYSeries getEpSeries() {
        return epSeries;
    }

    public XYSeries getEkSeries() {
        return ekSeries;
    }

    public XYSeries getEtSeries() {
        return etSeries;
    }
}