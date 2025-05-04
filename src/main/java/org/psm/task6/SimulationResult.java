package org.psm.task6;

import org.jfree.data.xy.XYSeriesCollection;

public record SimulationResult(XYSeriesCollection shapeDataset, XYSeriesCollection energyDataset) {
}