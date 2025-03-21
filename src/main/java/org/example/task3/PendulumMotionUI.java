package org.example.task3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Ellipse2D;

public class PendulumMotionUI {

    private JFrame window;
    private JTextField massField;
    private JTextField lengthField;
    private JTextField angleField;
    private JTextField timeField;
    private JTextField timeStepField;

    // Chart datasets
    private final XYSeriesCollection heunMotionDataset = new XYSeriesCollection();
    private final XYSeriesCollection heunOmegaDataset = new XYSeriesCollection();
    private final XYSeriesCollection rk4MotionDataset = new XYSeriesCollection();
    private final XYSeriesCollection rk4OmegaDataset = new XYSeriesCollection();

    // Chart panels
    private ChartPanel heunMotionChartPanel;
    private ChartPanel heunOmegaChartPanel;
    private ChartPanel rk4MotionChartPanel;
    private ChartPanel rk4OmegaChartPanel;

    public void createAndShowGUI() {
        window = new JFrame("Pendulum Motion");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200, 900);
        window.setLocationRelativeTo(null);
        window.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Create input panel and add it to window
        JPanel inputPanel = createInputPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        window.add(inputPanel, gbc);

        // Create charts panel and add it to window
        JPanel chartsPanel = createChartsPanel();
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        window.add(chartsPanel, gbc);

        window.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        massField = createTextField("Mass");
        lengthField = createTextField("Length");
        angleField = createTextField("Angle (°)");
        timeField = createTextField("Time");
        timeStepField = createTextField("Time Step");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createLabeledField("Mass:", massField), gbc);

        gbc.gridx = 1;
        panel.add(createLabeledField("Length:", lengthField), gbc);

        gbc.gridx = 2;
        panel.add(createLabeledField("Angle (°):", angleField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabeledField("Time:", timeField), gbc);

        gbc.gridx = 2;
        panel.add(createLabeledField("Time Step:", timeStepField), gbc);

        // Simulate button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runSimulation();
            }
        });
        panel.add(simulateButton, gbc);

        return panel;
    }

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

    private JPanel createChartsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Create Heun Motion Chart
        JFreeChart heunMotionChart = ChartFactory.createXYLineChart(
                "Pendulum Heun's Energies Motion",
                "Time",
                "Energies",
                heunMotionDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        heunMotionChartPanel = new ChartPanel(heunMotionChart);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(heunMotionChartPanel, gbc);

        // Create Heun Omega Chart
        JFreeChart heunOmegaChart = ChartFactory.createXYLineChart(
                "Pendulum Heun Omega",
                "Alpha",
                "Omega",
                heunOmegaDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYLineAndShapeRenderer renderer = createOmegaRenderer();
        heunOmegaChart.getXYPlot().setRenderer(renderer);
        heunOmegaChartPanel = new ChartPanel(heunOmegaChart);
        gbc.gridx = 1;
        panel.add(heunOmegaChartPanel, gbc);

        // Create RK4 Motion Chart
        JFreeChart rk4MotionChart = ChartFactory.createXYLineChart(
                "Pendulum RK4 Energies Motion",
                "Time",
                "Energies",
                rk4MotionDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        rk4MotionChartPanel = new ChartPanel(rk4MotionChart);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(rk4MotionChartPanel, gbc);

        // Create RK4 Omega Chart
        JFreeChart rk4OmegaChart = ChartFactory.createXYLineChart(
                "Pendulum RK4 Omega",
                "Alpha",
                "Omega",
                rk4OmegaDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        rk4OmegaChart.getXYPlot().setRenderer(renderer);
        rk4OmegaChartPanel = new ChartPanel(rk4OmegaChart);
        gbc.gridx = 1;
        panel.add(rk4OmegaChartPanel, gbc);

        return panel;
    }

    private XYLineAndShapeRenderer createOmegaRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 4, 4));
        return renderer;
    }

    private void runSimulation() {
        try {
            double mass = Double.parseDouble(massField.getText());
            double length = Double.parseDouble(lengthField.getText());
            double angle = Double.parseDouble(angleField.getText());
            double totalTime = Double.parseDouble(timeField.getText());
            double timeStep = Double.parseDouble(timeStepField.getText());

            SimulationResult result = PendulumSimulator.runSimulation(mass, length, angle, totalTime, timeStep);

            updateDataset(heunMotionDataset, result.getHeunMotionDataset());
            updateDataset(heunOmegaDataset, result.getHeunOmegaDataset());
            updateDataset(rk4MotionDataset, result.getRK4MotionDataset());
            updateDataset(rk4OmegaDataset, result.getRk4OmegaDataset());

            heunMotionChartPanel.repaint();
            heunOmegaChartPanel.repaint();
            rk4MotionChartPanel.repaint();
            rk4OmegaChartPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Invalid input! Please enter numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDataset(XYSeriesCollection target, XYSeriesCollection source) {
        target.removeAllSeries();
        for (int i = 0; i < source.getSeriesCount(); i++) {
            target.addSeries(source.getSeries(i));
        }
    }
}