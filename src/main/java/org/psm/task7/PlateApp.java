package org.psm.task7;

public class PlateApp {
    public static void main(String[] args) {
        int N = 40;
        double tol = 1e-4;

        var result = PlateSimulator.simulate(N, tol);
        new PlateUI().show(result);
    }
}