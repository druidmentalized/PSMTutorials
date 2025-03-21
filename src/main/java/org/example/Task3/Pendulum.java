package org.example.Task3;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Pendulum {
    // Constants
    private static double g = 10;

    //basic data
    double mass;
    double length;


    // Angles
    private double alpha;
    private double omega;

    private double initAlpha;
    private double initOmega = 0;

    // Position
    private double x;
    private double y;
    private double height;

    public Pendulum(double mass, double length, double angleDegrees) {
        this.mass = mass;
        this.length = length;


        initAlpha = Math.toRadians(angleDegrees);

        alpha = initAlpha;
        omega = initOmega;
    }

    public XYSeriesCollection simulateHeunMotion(double time, double timeStep) {
        resetStates();

        XYSeries EpSeries = new XYSeries("Ep");
        XYSeries EkSeries = new XYSeries("Ek");


        double currentTime = 0;
        for (int i = 0; i < time / timeStep; i++) {
            // Record energies at the current state
            double[] energies = recomputeEnergies();
            EpSeries.add(currentTime, energies[0]);
            EkSeries.add(currentTime, energies[1]);

            // Advance one time step using Heun's method
            recomputeHeunDegrees(timeStep);

            // Increment time
            currentTime += timeStep;
        }

        // Put both series into a dataset
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(EpSeries);
        dataset.addSeries(EkSeries);
        return dataset;
    }

    private void recomputeHeunDegrees(double timeStep) {
        double alpha_old = alpha;
        double omega_old = omega;

        double k1_omega = -g / length * Math.sin(alpha_old);

        double alpha_predict = alpha_old + omega_old * timeStep;

        double k2_alpha = omega_old + k1_omega * timeStep;
        double k2_omega = -g / length * Math.sin(alpha_predict);

        alpha = alpha_old + 0.5 * timeStep * (omega_old + k2_alpha);
        omega = omega_old + 0.5 * timeStep * (k1_omega + k2_omega);
    }

    public XYSeriesCollection simulateRK4Motion(double time, double timeStep) {
        resetStates();

        XYSeries EpSeries = new XYSeries("Ep");
        XYSeries EkSeries = new XYSeries("Ek");

        double currentTime = 0;
        for (int i = 0; i < time / timeStep; i++) {
            double[] energies = recomputeEnergies();
            EpSeries.add(currentTime, energies[0]);
            EkSeries.add(currentTime, energies[1]);

            rk4Step(timeStep);

            currentTime += timeStep;
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(EpSeries);
        dataset.addSeries(EkSeries);
        return dataset;
    }

    private void rk4Step(double dt) {
        double alpha_n = alpha;
        double omega_n = omega;

        // k1
        double k1_alpha = omega_n;
        double k1_omega = - (g / length) * Math.sin(alpha_n);

        // k2
        double alpha_k2 = alpha_n + 0.5 * dt * k1_alpha;
        double omega_k2 = omega_n + 0.5 * dt * k1_omega;
        double k2_alpha = omega_k2;
        double k2_omega = - (g / length) * Math.sin(alpha_k2);

        // k3
        double alpha_k3 = alpha_n + 0.5 * dt * k2_alpha;
        double omega_k3 = omega_n + 0.5 * dt * k2_omega;
        double k3_alpha = omega_k3;
        double k3_omega = - (g / length) * Math.sin(alpha_k3);

        // k4
        double alpha_k4 = alpha_n + dt * k3_alpha;
        double omega_k4 = omega_n + dt * k3_omega;
        double k4_alpha = omega_k4;
        double k4_omega = - (g / length) * Math.sin(alpha_k4);

        // Combine
        alpha = alpha_n + (dt / 6.0) * (k1_alpha + 2*k2_alpha + 2*k3_alpha + k4_alpha);
        omega = omega_n + (dt / 6.0) * (k1_omega + 2*k2_omega + 2*k3_omega + k4_omega);
    }

    private double[] recomputeEnergies() {
        double Ek;
        double Ep;

        x = length * Math.cos(alpha - Math.toRadians(90));
        y = length * Math.sin(alpha - Math.toRadians(90));

        height = length + y;

        Ep = mass * Math.abs(g) * height;
        Ek = mass * Math.pow((omega * length), 2) / 2;

        return new double[]{Ep, Ek};
    }

    private void resetStates() {
        alpha = initAlpha;
        omega = initOmega;
    }
}
