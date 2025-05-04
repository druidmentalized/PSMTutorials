package org.psm.task4;

import org.jfree.data.xy.XYSeriesCollection;

public class SimulationResult {
    private final XYSeriesCollection motionTrajectories;
    private final XYSeriesCollection energies;

    public SimulationResult(XYSeriesCollection motionTrajectories, XYSeriesCollection energies) {
        this.energies = energies;
        this.motionTrajectories = motionTrajectories;
    }

    public XYSeriesCollection getEnergies() {
        return energies;
    }

    public XYSeriesCollection getMotionTrajectories() {
        return motionTrajectories;
    }
}
