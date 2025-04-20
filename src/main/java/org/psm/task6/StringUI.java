package org.psm.task6;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;

public class StringUI {

    public void createAndShowGUI() {
        SimulationResult result =
                StringSimulator.runSimulation(
                        10,
                        Math.PI,
                        0.01,
                        10
                );

        XYSeriesCollection shapeData  = result.shapeDataset();
        XYSeriesCollection energyData = result.energyDataset();

        JFreeChart shapeChart = ChartFactory.createXYLineChart(
                "String Shape(x)", "x", "y",
                shapeData, PlotOrientation.VERTICAL,
                true,false,false
        );
        JFreeChart energyChart = ChartFactory.createXYLineChart(
                "String Energy(t)", "t", "Energy",
                energyData, PlotOrientation.VERTICAL,
                true,false,false
        );

        ChartPanel shapePanel  = new ChartPanel(shapeChart);
        ChartPanel energyPanel = new ChartPanel(energyChart);

        JFrame frame = new JFrame("String Wave Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1,2));
        frame.add(shapePanel);
        frame.add(energyPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}