package org.psm.task4;

import org.jfree.data.xy.XYSeriesCollection;

public class SimulationResult {
    private final XYSeriesCollection energies;
    private final XYSeriesCollection rollingResults;

    public SimulationResult(XYSeriesCollection energies, XYSeriesCollection rollingResults) {
        this.energies = energies;
        this.rollingResults = rollingResults;
    }

    public XYSeriesCollection getEnergies() {
        return energies;
    }
    public XYSeriesCollection getRollingResults() {
        return rollingResults;
    }
}
