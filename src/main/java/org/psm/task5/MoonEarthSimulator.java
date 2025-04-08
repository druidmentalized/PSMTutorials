package org.psm.task5;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.List;

public class MoonEarthSimulator {

    private static final double YEAR_SEC = 3.15576e7;
    private static final double DT = 3600;
    private static final int STEPS = (int)(YEAR_SEC / DT);

    private static final double GRAVITY_CONSTANT = 6.6743e-11;
    private static final double SUN_MASS = 1.989e+30;
    private static final double EARTH_MASS = 5.972e24;
    private static final double MOON_MASS = 7.347e+22;

    private static final double X_SUN = 0.0;
    private static final double Y_SUN = 0.0;

    private static final double R_ES = 1.5e11;
    private static final double V_ES = 29749.15427;

    private static final double R_EM = 384400000;
    private static final double V_EM = 1018.289046;

    public static SimulationResult runSimulation() {
        XYSeries sunSeries = new XYSeries("Sun");
        sunSeries.add(X_SUN, Y_SUN);

        List<Double> earthList = computeEarthSeries();
        XYSeries earthSeries = new XYSeries("Earth");
        for (int i = 0; i < earthList.size(); i += 2) {
            earthSeries.add(earthList.get(i), earthList.get(i + 1));
        }

        XYSeries moonSeries = computeMoonSeries(earthList);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(moonSeries);
        dataset.addSeries(earthSeries);
        dataset.addSeries(sunSeries);

        return new SimulationResult(dataset);
    }

    private static List<Double> computeEarthSeries() {
        List<Double> coords = new ArrayList<>();

        double xE = 0;
        double yE = R_ES;
        double vxE = V_ES;
        double vyE = 0;

        for (int i = 0; i < STEPS; i++) {
            double[] aE = accelEarthSun(xE, yE);
            double axE = aE[0], ayE = aE[1];

            double vxEHalf = vxE + axE * (DT / 2.0);
            double vyEHalf = vyE + ayE * (DT / 2.0);

            double xEHalf = xE + vxE * (DT / 2.0);
            double yEHalf = yE + vyE * (DT / 2.0);

            double[] aEHalf = accelEarthSun(xEHalf, yEHalf);
            double axEHalf = aEHalf[0], ayEHalf = aEHalf[1];

            xE += vxEHalf * DT;
            yE += vyEHalf * DT;

            vxE += axEHalf * DT;
            vyE += ayEHalf * DT;

            coords.add(xE / 10.0);
            coords.add(yE / 10.0);
        }
        return coords;
    }

    private static XYSeries computeMoonSeries(List<Double> earthList) {
        XYSeries moon = new XYSeries("Moon");
        double xM = 0;
        double yM = R_EM;

        double vxM = V_EM;
        double vyM = 0;

        int index = 0;
        for (int i = 0; i < STEPS; i++) {
            double dx = -xM;
            double dy = -yM;

            double rEM = Math.sqrt(dx * dx + dy * dy);
            double[] aM = accelLocalEarthMoon(dx, dy, rEM);
            double axM = aM[0], ayM = aM[1];

            double vxMHalf = vxM + axM * (DT / 2.0);
            double vyMHalf = vyM + ayM * (DT / 2.0);

            double xMHalf = xM + vxM * (DT / 2.0);
            double yMHalf = yM + vyM * (DT / 2.0);

            double dxHalf = -xMHalf;
            double dyHalf = -yMHalf;

            double rEMHalf = Math.sqrt(dxHalf * dxHalf + dyHalf * dyHalf);
            double[] aMHalf = accelLocalEarthMoon(dxHalf, dyHalf, rEMHalf);
            double axMHalf = aMHalf[0], ayMHalf = aMHalf[1];

            xM += vxMHalf * DT;
            yM += vyMHalf * DT;

            vxM += axMHalf * DT;
            vyM += ayMHalf * DT;

            double xEarthGlobal = earthList.get(index++);
            double yEarthGlobal = earthList.get(index++);

            double xTotal = xM + xEarthGlobal;
            double yTotal = yM + yEarthGlobal;
            moon.add(xTotal, yTotal);
        }
        return moon;
    }

    private static double[] accelEarthSun(double xE, double yE) {
        double dx = X_SUN - xE;
        double dy = Y_SUN - yE;

        double r = Math.sqrt(dx * dx + dy * dy);
        double force = GRAVITY_CONSTANT * SUN_MASS * EARTH_MASS / (r * r);
        double ax = (dx / r) * (force / EARTH_MASS);
        double ay = (dy / r) * (force / EARTH_MASS);

        return new double[]{ ax, ay };
    }

    private static double[] accelLocalEarthMoon(double dx, double dy, double rEM) {
        double force = GRAVITY_CONSTANT * EARTH_MASS * MOON_MASS / (rEM * rEM);
        double ax = (dx / rEM) * (force / MOON_MASS);
        double ay = (dy / rEM) * (force / MOON_MASS);

        return new double[]{ ax, ay };
    }
}