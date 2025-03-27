package org.psm.task4;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Ellipse2D;

public class RollingObjectUI {
    private JFrame window;

    // Text fields
    private JTextField massField;
    private JTextField heightField;
    private JTextField radiusField;
    private JTextField angleField;
    private JTextField timeStepField;

    // Chart datasets
    private final XYSeriesCollection motionTrajectoriesDataset = new XYSeriesCollection();
    private final XYSeriesCollection energiesDataset = new XYSeriesCollection();


    // Chart panels
    private ChartPanel motionTrajectoriesChartPanel;
    private ChartPanel energiesChartPanel;

    public void createAndShowGUI() {
        window = new JFrame("Rolling Object");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200, 900);
        window.setLocationRelativeTo(null);
        window.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        JPanel inputPanel = createInputPanel();
        window.add(inputPanel, gbc);
        JPanel chartsPanel = createChartsPanel();

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        window.add(chartsPanel, gbc);

        window.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        massField = createTextField("Mass");
        heightField = createTextField("Height");
        radiusField = createTextField("Radius");
        angleField = createTextField("Angle (°)");
        timeStepField = createTextField("Time Step");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(createLabeledField("Mass:", massField), gbc);

        gbc.gridx = 1;
        inputPanel.add(createLabeledField("Height:", heightField), gbc);

        gbc.gridx = 2;
        inputPanel.add(createLabeledField("Radius:", radiusField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(createLabeledField("Angle:", angleField), gbc);

        gbc.gridx = 2;
        inputPanel.add(createLabeledField("Time Step:", timeStepField), gbc);

        // Simulate button
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(_ -> runSimulation());
        inputPanel.add(simulateButton, gbc);

        return inputPanel;
    }

    private JPanel createChartsPanel() {
        JPanel chartsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;


        //Creating motions trajectories chart
        JFreeChart trajectoriesChart = ChartFactory.createXYLineChart(
                "Rolling Object Motion Trajectories",
                "x",
                "y",
                motionTrajectoriesDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        trajectoriesChart.getXYPlot().setRenderer(createMotionRenderer());
        motionTrajectoriesChartPanel = new ChartPanel(trajectoriesChart);
        gbc.gridx = 0;
        gbc.gridy = 0;
        chartsPanel.add(motionTrajectoriesChartPanel, gbc);

        //Creating energies chart
        JFreeChart energiesChart = ChartFactory.createXYLineChart(
                "Rolling Object Energies",
                "Time",
                "Energies",
                energiesDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        energiesChart.getXYPlot().setRenderer(createEnergyRenderer());
        energiesChartPanel = new ChartPanel(energiesChart);
        gbc.gridx = 1;
        gbc.gridy = 0;
        chartsPanel.add(energiesChartPanel, gbc);

        return chartsPanel;
    }

    private void runSimulation() {
        try {
            double mass = Double.parseDouble(massField.getText());
            double height = Double.parseDouble(heightField.getText());
            double radius = Double.parseDouble(radiusField.getText());
            double angle = Double.parseDouble(angleField.getText());
            double timeStep = Double.parseDouble(timeStepField.getText());

            SimulationResult result = RollingSimulator.runSimulation(mass, height, radius, angle, timeStep);

            updateDataset(motionTrajectoriesDataset, result.getMotionTrajectories());
            updateDataset(energiesDataset, result.getEnergies());

            motionTrajectoriesChartPanel.repaint();
            energiesChartPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window, "Invalid input! Please enter numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(10);
        textField.setFont(new Font("Monospaced", Font.PLAIN, 20));
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        return textField;
    }

    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.PLAIN, 20));
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void updateDataset(XYSeriesCollection target, XYSeriesCollection source) {
        target.removeAllSeries();
        for (int i = 0; i < source.getSeriesCount(); i++) {
            target.addSeries(source.getSeries(i));
        }
    }

    private XYLineAndShapeRenderer createMotionRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));

        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShape(1, new Ellipse2D.Double(-1, -1, 3, 3));

        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShape(3, new Ellipse2D.Double(-2, -2, 4, 4));

        renderer.setSeriesLinesVisible(3, false);
        renderer.setSeriesShapesVisible(3, true);
        renderer.setSeriesShape(3, new Ellipse2D.Double(-2, -2, 4, 4));
        return renderer;
    }

    private XYLineAndShapeRenderer createEnergyRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesShapesVisible(0, false);

        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesShapesVisible(1, false);

        renderer.setSeriesStroke(2, new BasicStroke(3.0f));
        renderer.setSeriesShapesVisible(2, false);
        return renderer;
    }
}
