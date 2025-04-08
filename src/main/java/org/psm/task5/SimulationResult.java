package org.psm.task5;

import org.jfree.data.xy.XYSeriesCollection;

public class SimulationResult {
    private final XYSeriesCollection orbitsDataset;

    public SimulationResult(XYSeriesCollection dataset) {
        this.orbitsDataset = dataset;
    }

    public XYSeriesCollection getOrbitsDataset() {
        return orbitsDataset;
    }
}