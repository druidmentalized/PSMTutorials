package org.psm.task8;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class UI {
    private static final String EULER_CARD = "Euler";
    private static final String MIDPOINT_CARD = "Midpoint";
    private static final String RK4_CARD = "RK4";
    private static final String COMBINED_CARD = "Combined";

    private static XYLineAndShapeRenderer getXyLineAndShapeRenderer() {
        var renderer = new XYLineAndShapeRenderer();
        // Euler as line
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-1, -1, 2, 2));
        renderer.setSeriesPaint(0, Color.CYAN);

        // Midpoint as line
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShape(1, new Ellipse2D.Double(-1, -1, 2, 2));
        renderer.setSeriesPaint(1, Color.ORANGE);

        // RK4 as line
        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShape(2, new Ellipse2D.Double(-1, -1, 2, 2));
        renderer.setSeriesPaint(2, Color.MAGENTA);

        return renderer;
    }

    public void createAndShowGUI() {
        double dt = 0.005;
        int steps = (int) (50.0 / dt);

        var eulerSeries = Simulator.simulateEuler(dt, steps);
        var midpointSeries = Simulator.simulateMidpoint(dt, steps);
        var rk4Series = Simulator.simulateRK4(dt, steps);

        ChartPanel panelEuler = createChartPanel("Euler Method", new XYSeriesCollection(eulerSeries), Color.CYAN);
        ChartPanel panelMidpoint = createChartPanel("Midpoint Method", new XYSeriesCollection(midpointSeries), Color.ORANGE);
        ChartPanel panelRK4 = createChartPanel("RK4 Method", new XYSeriesCollection(rk4Series), Color.MAGENTA);
        ChartPanel panelCombined = createCombinedChartPanel(eulerSeries, midpointSeries, rk4Series);

        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.add(panelEuler, EULER_CARD);
        cardPanel.add(panelMidpoint, MIDPOINT_CARD);
        cardPanel.add(panelRK4, RK4_CARD);
        cardPanel.add(panelCombined, COMBINED_CARD);

        JButton prevBtn = new JButton("<< Prev");
        JButton nextBtn = new JButton("Next >>");
        JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);

        JPanel navPanel = new JPanel(new BorderLayout(10, 0));
        navPanel.add(prevBtn, BorderLayout.WEST);
        navPanel.add(titleLabel, BorderLayout.CENTER);
        navPanel.add(nextBtn, BorderLayout.EAST);
        navPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JFrame frame = new JFrame("Lab8");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(navPanel, BorderLayout.SOUTH);

        ActionListener switchCard = new ActionListener() {
            final String[] cards = {EULER_CARD, MIDPOINT_CARD, RK4_CARD, COMBINED_CARD};
            final CardLayout cl = (CardLayout) (cardPanel.getLayout());
            int index = 0;

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == nextBtn) {
                    index = (index + 1) % cards.length;
                } else if (e.getSource() == prevBtn) {
                    index = (index + cards.length - 1) % cards.length;
                }
                cl.show(cardPanel, cards[index]);
                titleLabel.setText(cards[index] + " Projection");
            }
        };
        prevBtn.addActionListener(switchCard);
        nextBtn.addActionListener(switchCard);

        ((CardLayout) cardPanel.getLayout()).show(cardPanel, EULER_CARD);
        titleLabel.setText("Euler Projection");

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ChartPanel createChartPanel(String title,
                                        XYSeriesCollection dataset, Color lineColor) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "x", "z",
                dataset, PlotOrientation.VERTICAL,
                false, true, false
        );
        var plot = chart.getXYPlot();
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-1, -1, 2, 2));
        renderer.setSeriesPaint(0, lineColor);
        plot.setRenderer(renderer);

        return new ChartPanel(chart);
    }

    private ChartPanel createCombinedChartPanel(XYSeries euler,
                                                XYSeries midpoint,
                                                XYSeries rk4) {
        var dataset = new XYSeriesCollection();
        dataset.addSeries(euler);
        dataset.addSeries(midpoint);
        dataset.addSeries(rk4);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "All Methods United", "x", "z",
                dataset, PlotOrientation.VERTICAL,
                true, true, false
        );
        var plot = chart.getXYPlot();
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        var renderer = getXyLineAndShapeRenderer();

        plot.setRenderer(renderer);
        return new ChartPanel(chart);
    }
}
