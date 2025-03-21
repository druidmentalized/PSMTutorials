package org.psm.task3;

import org.jfree.data.xy.XYSeriesCollection;

public class PendulumSimulator {

    public static SimulationResult runSimulation(double mass, double length, double angleDegrees, double totalTime, double timeStep) {
        XYSeriesCollection heunMotionDataset = new XYSeriesCollection();
        XYSeriesCollection heunOmegaDataset = new XYSeriesCollection();
        XYSeriesCollection rk4MotionDataset = new XYSeriesCollection();
        XYSeriesCollection rk4OmegaDataset = new XYSeriesCollection();

        Pendulum pendulum = new Pendulum(mass, length, angleDegrees);

        pendulum.simulateHeunMotion(totalTime, timeStep);
        heunMotionDataset.addSeries(pendulum.getHeunMotionSeriesEp());
        heunMotionDataset.addSeries(pendulum.getHeunMotionSeriesEk());
        heunOmegaDataset.addSeries(pendulum.getHeunOmegaSeries());

        pendulum.simulateRK4Motion(totalTime, timeStep);
        rk4MotionDataset.addSeries(pendulum.getRK4MotionSeriesEp());
        rk4MotionDataset.addSeries(pendulum.getRK4MotionSeriesEk());
        rk4OmegaDataset.addSeries(pendulum.getRK4OmegaSeries());

        return new SimulationResult(heunMotionDataset, heunOmegaDataset, rk4MotionDataset, rk4OmegaDataset);
    }
}