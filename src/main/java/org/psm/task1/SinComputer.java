package org.psm.task1;

public class SinComputer {
    public static void main(String[] args) {
        computeSin(360,true);
    }

    public static void computeSin(double angle, boolean angleInRadians) {
        if (!angleInRadians) {
            angle = Math.toRadians(angle);
        }

        angle %= (2 * Math.PI);
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        } else if (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }

        double approximation = getApproximation(angle);

        System.out.println("sin(angle) ≈ " + approximation);
        System.out.println("sin(angle) from Math library = " + Math.sin(angle));
    }

    private static double getApproximation(double angle) {
        double approximation = angle;

        double currentTerm = angle;

        double denominator = 1.0;

        for (int i = 1; i < 5; i++) {
            currentTerm *= -angle * angle;

            denominator *= (2 * i) * (2 * i + 1);

            approximation += currentTerm / denominator;
        }
        return approximation;
    }
}
