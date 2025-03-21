package org.example.Task3;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;
import java.util.Set;

public class Pendulum {
    // Constants
    private static double g = 10;

    //basic data
    double mass;
    double length;


    // Angles
    private double alpha;
    private double omega = 0;
    private double epsilon;

    private double deltaAlpha;
    private double deltaOmega;

    // Position
    private double x;
    private double y;
    private double height;

    public Pendulum(double mass, double length, double angleDegrees) {
        this.mass = mass;
        this.length = length;

        alpha = Math.toRadians(angleDegrees);
    }

    public XYSeriesCollection simulateMotion(double time, double timeStep) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries EpSeries = new XYSeries("Ep");
        XYSeries EkSeries = new XYSeries("Ek");

        double passedTime = 0;

        for (int i = 0; i < time / timeStep; i++) {
            double[] energies = recomputeEnergies();
            EpSeries.add(passedTime, energies[0]);
            EkSeries.add(passedTime, energies[1]);

            recomputeDegrees(timeStep);

            passedTime += timeStep;
        }

        dataset.addSeries(EpSeries);
        dataset.addSeries(EkSeries);

        return dataset;
    }

    private void recomputeDegrees(double timeStep) {
        double alpha_n = alpha;    // old angle
        double omega_n = omega;    // old angular velocity

        // 1) Compute derivatives (k1) at the current state
        //    alpha' = omega, omega' = -g/length * sin(alpha)
        double k1_alpha = omega_n;
        double k1_omega = -g / length * Math.sin(alpha_n);

        // 2) Predict the state at t + timeStep using Euler
        double alpha_pred = alpha_n + k1_alpha * timeStep;
        double omega_pred = omega_n + k1_omega * timeStep;

        // 3) Compute derivatives (k2) at the predicted state
        double k2_alpha = omega_pred;
        double k2_omega = -g / length * Math.sin(alpha_pred);

        // 4) Final update by averaging k1 and k2
        alpha = alpha_n + 0.5 * timeStep * (k1_alpha + k2_alpha);
        omega = omega_n + 0.5 * timeStep * (k1_omega + k2_omega);
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

}
