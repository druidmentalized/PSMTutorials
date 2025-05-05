package org.psm.task7;

public class PlateApp {
    public static void main(String[] args) {
        int N = 40;

        var result = PlateSimulator.simulate(N);
        new PlateUI().show(result);
    }
}