package org.example.Task3;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Pendulum {
    // Constants
    private static double g = 10;

    // Basic data
    double mass;
    double length;

    // Angles
    private double alpha;
    private double omega;
    private final double initAlpha;
    private final double initOmega = 0;

    //Series
    private final XYSeries heunMotionSeriesEp = new XYSeries("Ep");
    private final XYSeries heunMotionSeriesEk = new XYSeries("Ek");
    private final XYSeries RK4MotionSeriesEp = new XYSeries("Ep");
    private final XYSeries RK4MotionSeriesEk = new XYSeries("Ek");
    private final XYSeries heunOmegaSeries = new XYSeries("Omega");
    private final XYSeries RK4OmegaSeries = new XYSeries("Omega");

    public Pendulum(double mass, double length, double angleDegrees) {
        this.mass = mass;
        this.length = length;

        initAlpha = Math.toRadians(angleDegrees);

        alpha = initAlpha;
        omega = initOmega;
    }

    public void simulateHeunMotion(double time, double timeStep) {
        resetStates();

        double currentTime = 0;
        for (int i = 0; i < time / timeStep; i++) {
            double[] energies = recomputeEnergies();
            heunMotionSeriesEp.add(currentTime, energies[0]);
            heunMotionSeriesEk.add(currentTime, energies[1]);

            recomputeHeunDegrees(timeStep);

            currentTime += timeStep;
        }
    }

    private void recomputeHeunDegrees(double timeStep) {
        heunOmegaSeries.add(alpha, omega);

        double alpha_old = alpha;
        double omega_old = omega;

        double k1_omega = -g / length * Math.sin(alpha_old);

        double alpha_predict = alpha_old + omega_old * timeStep;

        double k2_alpha = omega_old + k1_omega * timeStep;
        double k2_omega = -g / length * Math.sin(alpha_predict);

        alpha = alpha_old + 0.5 * timeStep * (omega_old + k2_alpha);
        omega = omega_old + 0.5 * timeStep * (k1_omega + k2_omega);
    }

    public void simulateRK4Motion(double time, double timeStep) {
        resetStates();

        double currentTime = 0;
        for (int i = 0; i < time / timeStep; i++) {
            double[] energies = recomputeEnergies();
            RK4MotionSeriesEp.add(currentTime, energies[0]);
            RK4MotionSeriesEk.add(currentTime, energies[1]);

            rk4Step(timeStep);

            currentTime += timeStep;
        }
    }

    private void rk4Step(double dt) {
        RK4OmegaSeries.add(alpha, omega);

        double alpha_n = alpha;
        double omega_n = omega;

        // k1
        double k1_omega = -(g / length) * Math.sin(alpha_n);

        // k2
        double alpha_k2 = alpha_n + 0.5 * dt * omega_n;
        double k2_alpha = omega_n + 0.5 * dt * k1_omega;
        double k2_omega = -(g / length) * Math.sin(alpha_k2);

        // k3
        double alpha_k3 = alpha_n + 0.5 * dt * k2_alpha;
        double k3_alpha = omega_n + 0.5 * dt * k2_omega;
        double k3_omega = -(g / length) * Math.sin(alpha_k3);

        // k4
        double alpha_k4 = alpha_n + dt * k3_alpha;
        double k4_alpha = omega_n + dt * k3_omega;
        double k4_omega = -(g / length) * Math.sin(alpha_k4);

        alpha = alpha_n + (dt / 6.0) * (omega_n + 2*k2_alpha + 2*k3_alpha + k4_alpha);
        omega = omega_n + (dt / 6.0) * (k1_omega + 2*k2_omega + 2*k3_omega + k4_omega);
    }

    private double[] recomputeEnergies() {
        double Ek;
        double Ep;

        double y = length * Math.sin(alpha - Math.toRadians(90));

        double height = length + y;

        Ep = mass * Math.abs(g) * height;
        Ek = mass * Math.pow((omega * length), 2) / 2;

        return new double[]{Ep, Ek};
    }

    private void resetStates() {
        alpha = initAlpha;
        omega = initOmega;
    }

    public XYSeries getHeunMotionSeriesEp() {
        return heunMotionSeriesEp;
    }

    public XYSeries getHeunMotionSeriesEk() {
        return heunMotionSeriesEk;
    }

    public XYSeries getRK4MotionSeriesEp() {
        return RK4MotionSeriesEp;
    }

    public XYSeries getRK4MotionSeriesEk() {
        return RK4MotionSeriesEk;
    }

    public XYSeries getHeunOmegaSeries() {
        return heunOmegaSeries;
    }

    public XYSeries getRK4OmegaSeries() {
        return RK4OmegaSeries;
    }
}
