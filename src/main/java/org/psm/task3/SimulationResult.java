package org.psm.task3;

import org.jfree.data.xy.XYSeriesCollection;

public class SimulationResult {
    private final XYSeriesCollection heunMotionDataset;
    private final XYSeriesCollection heunOmegaDataset;
    private final XYSeriesCollection rk4MotionDataset;
    private final XYSeriesCollection rk4OmegaDataset;

    public SimulationResult(XYSeriesCollection heunMotionDataset, XYSeriesCollection heunOmegaDataset,
                            XYSeriesCollection rk4MotionDataset, XYSeriesCollection rk4OmegaDataset) {
        this.heunMotionDataset = heunMotionDataset;
        this.heunOmegaDataset = heunOmegaDataset;
        this.rk4MotionDataset = rk4MotionDataset;
        this.rk4OmegaDataset = rk4OmegaDataset;
    }

    public XYSeriesCollection getHeunMotionDataset() {
        return heunMotionDataset;
    }
    public XYSeriesCollection getHeunOmegaDataset() {
        return heunOmegaDataset;
    }
    public XYSeriesCollection getRK4MotionDataset() {
        return rk4MotionDataset;
    }
    public XYSeriesCollection getRk4OmegaDataset() {
        return rk4OmegaDataset;
    }
}