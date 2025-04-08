package org.psm.task5;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MoonEarthUI {
    public void createAndShowGUI() {
        JFrame window = new JFrame("Moon-Earth Simulation");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(900, 900);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        SimulationResult result = MoonEarthSimulator.runSimulation();
        XYSeriesCollection orbitsDataset = result.getOrbitsDataset();

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Orbits",
                "X (m)",
                "Y (m)",
                orbitsDataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        chart.setBackgroundPaint(Color.BLACK);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.BLACK);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.WHITE);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-1, -1, 2, 2));

        renderer.setSeriesPaint(1, new Color(0, 128, 0));
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShape(1, new Ellipse2D.Double(-1, -1, 2, 2));

        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesLinesVisible(2, false);
        int sunPoint = 80;
        renderer.setSeriesShape(2, new Ellipse2D.Double((double) sunPoint / -2, (double) sunPoint / -2, sunPoint, sunPoint));

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        window.add(chartPanel, BorderLayout.CENTER);

        window.setVisible(true);
    }
}