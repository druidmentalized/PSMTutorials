package org.psm.task4;

import org.jfree.data.xy.XYSeriesCollection;

public class RollingSimulator {
    public static SimulationResult runSimulation(double mass, double height, double radius,
                                                 double angleDegrees, double timeStep) {
        XYSeriesCollection trajectoriesDataset = new XYSeriesCollection();
        XYSeriesCollection energiesDataset = new XYSeriesCollection();

        RollingObject rollingObject = new RollingObject(mass, height, radius, angleDegrees, 3.6);

        rollingObject.simulate(timeStep);
        trajectoriesDataset.addSeries(rollingObject.getInclinedSurfaceSeries());
        trajectoriesDataset.addSeries(rollingObject.getBallProjectionSeries());
        trajectoriesDataset.addSeries(rollingObject.getCenterSeries());
        trajectoriesDataset.addSeries(rollingObject.getRingSeries());

        energiesDataset.addSeries(rollingObject.getEpSeries());
        energiesDataset.addSeries(rollingObject.getEkSeries());
        energiesDataset.addSeries(rollingObject.getEtSeries());

        return new SimulationResult(trajectoriesDataset, energiesDataset);
    }
}
