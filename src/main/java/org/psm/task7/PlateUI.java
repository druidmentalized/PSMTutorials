package org.psm.task7;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;

public class PlateUI {

    public void show(SimulationResult result) {
        var dataset = result.dataset();

        NumberAxis xAxis = new NumberAxis("X");
        xAxis.setLowerMargin(0);
        xAxis.setUpperMargin(0);
        NumberAxis yAxis = new NumberAxis("Y");
        yAxis.setLowerMargin(0);
        yAxis.setUpperMargin(0);

        XYBlockRenderer renderer = new XYBlockRenderer();
        renderer.setBlockWidth(1);
        renderer.setBlockHeight(1);

        LookupPaintScale scale = new LookupPaintScale(-300, 300, Color.BLACK);
        for (int i = 0; i <= 100; i++) {
            float hue = 0.7f - 0.7f * i / 100f;
            double v = -300 + i * 6.0;
            scale.add(v, Color.getHSBColor(hue, 1f, 1f));
        }
        renderer.setPaintScale(scale);

        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        JFreeChart chart = new JFreeChart(
                "Temperature Distribution",
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                false
        );
        chart.setPadding(new RectangleInsets(0, 0, 0, 0));

        NumberAxis scaleAxis = new NumberAxis("Temperature");
        scaleAxis.setRange(-300, 300);
        scaleAxis.setTickUnit(new NumberTickUnit(50));
        PaintScaleLegend legend = new PaintScaleLegend(scale, scaleAxis);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(15);
        chart.addSubtitle(legend);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(600, 600));

        JFrame frame = new JFrame("Task 7 – Plate Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}