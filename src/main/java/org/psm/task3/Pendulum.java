package org.psm.task3;

import org.jfree.data.xy.XYSeries;

public class Pendulum {
    private static final double G = 10.0; // gravitational constant

    private final double mass;
    private final double length;
    private final double initAlpha;

    // State variables
    private double alpha;
    private double omega;

    // Data series for simulation results
    private final XYSeries heunMotionSeriesEp = new XYSeries("Ep");
    private final XYSeries heunMotionSeriesEk = new XYSeries("Ek");
    private final XYSeries heunMotionSeriesEt = new XYSeries("Et");
    private final XYSeries rk4MotionSeriesEp = new XYSeries("Ep");
    private final XYSeries rk4MotionSeriesEk = new XYSeries("Ek");
    private final XYSeries rk4MotionSeriesEt = new XYSeries("Et");
    private final XYSeries heunOmegaSeries = new XYSeries("Omega");
    private final XYSeries rk4OmegaSeries = new XYSeries("Omega");

    public Pendulum(double mass, double length, double angleDegrees) {
        this.mass = mass;
        this.length = length;
        this.initAlpha = Math.toRadians(angleDegrees);
        resetValues();
    }

    private void resetValues() {
        alpha = initAlpha;
        omega = 0.0;
    }

    public void simulateHeunMotion(double totalTime, double timeStep) {
        resetValues();
        double currentTime = 0;
        int steps = (int)(totalTime / timeStep);
        for (int i = 0; i < steps; i++) {
            double[] energies = computeEnergies();
            heunMotionSeriesEp.add(currentTime, energies[0]);
            heunMotionSeriesEk.add(currentTime, energies[1]);
            heunMotionSeriesEt.add(currentTime, energies[0] + energies[1]);

            heunOmegaSeries.add(alpha, omega);
            heunStep(timeStep);

            currentTime += timeStep;
        }
    }

    private void heunStep(double timeStep) {
        double alphaOld = alpha;
        double omegaOld = omega;

        double k1_omega = -G / length * Math.sin(alphaOld);
        double alphaPredict = alphaOld + omegaOld * timeStep;
        double k2_alpha = omegaOld + k1_omega * timeStep;
        double k2_omega = -G / length * Math.sin(alphaPredict);

        alpha = alphaOld + 0.5 * timeStep * (omegaOld + k2_alpha);
        omega = omegaOld + 0.5 * timeStep * (k1_omega + k2_omega);
    }

    public void simulateRK4Motion(double totalTime, double timeStep) {
        resetValues();
        double currentTime = 0;
        int steps = (int)(totalTime / timeStep);
        for (int i = 0; i < steps; i++) {
            double[] energies = computeEnergies();
            rk4MotionSeriesEp.add(currentTime, energies[0]);
            rk4MotionSeriesEk.add(currentTime, energies[1]);
            rk4MotionSeriesEt.add(currentTime, energies[0] + energies[1]);

            rk4OmegaSeries.add(alpha, omega);
            rk4Step(timeStep);

            currentTime += timeStep;
        }
    }

    private void rk4Step(double dt) {
        double alpha_n = alpha;
        double omega_n = omega;

        // k1
        double k1_omega = - (G / length) * Math.sin(alpha_n);

        // k2
        double alpha_k2 = alpha_n + 0.5 * dt * omega_n;
        double k2_alpha = omega_n + 0.5 * dt * k1_omega;
        double k2_omega = - (G / length) * Math.sin(alpha_k2);

        // k3
        double alpha_k3 = alpha_n + 0.5 * dt * k2_alpha;
        double k3_alpha = omega_n + 0.5 * dt * k2_omega;
        double k3_omega = - (G / length) * Math.sin(alpha_k3);

        // k4
        double alpha_k4 = alpha_n + dt * k3_alpha;
        double k4_alpha = omega_n + dt * k3_omega;
        double k4_omega = - (G / length) * Math.sin(alpha_k4);

        alpha = alpha_n + (dt / 6.0) * (omega_n + 2 * k2_alpha + 2 * k3_alpha + k4_alpha);
        omega = omega_n + (dt / 6.0) * (k1_omega + 2 * k2_omega + 2 * k3_omega + k4_omega);
    }

    private double[] computeEnergies() {
        double y = length * Math.sin(alpha - Math.toRadians(90));
        double height = length + y;
        double Ep = mass * G * height;
        double Ek = 0.5 * mass * Math.pow(omega * length, 2);
        return new double[] {Ep, Ek};
    }

    // Getters for the data series
    public XYSeries getHeunMotionSeriesEp() { return heunMotionSeriesEp; }
    public XYSeries getHeunMotionSeriesEk() { return heunMotionSeriesEk; }
    public XYSeries getHeunMotionSeriesEt() { return heunMotionSeriesEt; }
    public XYSeries getRK4MotionSeriesEp() { return rk4MotionSeriesEp; }
    public XYSeries getRK4MotionSeriesEk() { return rk4MotionSeriesEk; }
    public XYSeries getRK4MotionSeriesEt() { return rk4MotionSeriesEt; }
    public XYSeries getHeunOmegaSeries() { return heunOmegaSeries; }
    public XYSeries getRK4OmegaSeries() { return rk4OmegaSeries; }
}