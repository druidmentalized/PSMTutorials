package org.psm.task4;

public class RollingObject {
    private final static double G = 10;

    private final double mass;
    private final double height;
    private final double radius;
    private final double initAlpha;

    private double inertia;
    private double length;
    private double alpha;
    private double omega;
    private double epsilon;

    private double acc;

    public RollingObject(double mass, double height, double radius, int angleDegrees) {
        this.mass = mass;
        this.height = height;
        this.radius = radius;
        this.initAlpha = Math.toRadians(angleDegrees);
        resetValues();
    }

    private void resetValues() {
        alpha = initAlpha;
        omega = 0.0;

        length = height / Math.tan(alpha);
        inertia = (double) 2 / 5 * mass * Math.pow(radius, 2);
        acc = (G * Math.sin(alpha)) / (1 + inertia / (mass * Math.pow(radius, 2)));
        epsilon = acc / radius;
    }


}
