package org.example.Task3;

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

public class PendulumMotion {

    private static JTextArea massTextArea;
    private static JTextArea lengthTextArea;
    private static JTextArea angleTextArea;
    private static JTextArea timeTextArea;
    private static JTextArea timeStepTextArea;

    private static final XYSeriesCollection heunMotionDataset = new XYSeriesCollection();
    private static final XYSeriesCollection RK4MotionDataset = new XYSeriesCollection();
    private static final XYSeriesCollection heunOmegaDataset = new XYSeriesCollection();
    private static final XYSeriesCollection RK4OmegaDataset = new XYSeriesCollection();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PendulumMotion::runApplication);
    }

    public static void runApplication() {
        JFrame window = new JFrame("Pendulum Motion");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200, 900);
        window.setLocationRelativeTo(null);


        window.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.gridx = 0;
        gbc.gridy = 0;

        window.add(createInputArea(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        window.add(createChartsArea(), gbc);

        window.setVisible(true);
    }

    private static JPanel createInputArea() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        massTextArea = createPlaceholderTextArea("m");
        inputPanel.add(createInputTextArea("Mass", massTextArea), gbc);

        gbc.gridx = 1;
        lengthTextArea = createPlaceholderTextArea("l");
        inputPanel.add(createInputTextArea("Length", lengthTextArea), gbc);

        gbc.gridx = 2;
        angleTextArea = createPlaceholderTextArea("°");
        inputPanel.add(createInputTextArea("Angle in degrees", angleTextArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        timeTextArea = createPlaceholderTextArea("T");
        inputPanel.add(createInputTextArea("Time", timeTextArea), gbc);

        gbc.gridx = 2;
        timeStepTextArea = createPlaceholderTextArea("Δt");
        inputPanel.add(createInputTextArea("Time Step", timeStepTextArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(_ -> runSimulation());
        inputPanel.add(simulateButton, gbc);

        return inputPanel;
    }

    private static JPanel createInputTextArea(String attributeName, JTextArea textArea) {
        JPanel textAreaPanel = new JPanel(new BorderLayout());

        JLabel attributeLabel = new JLabel(attributeName + ":");
        attributeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        attributeLabel.setForeground(Color.BLACK);
        attributeLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));

        textAreaPanel.add(attributeLabel, BorderLayout.NORTH);
        textAreaPanel.add(textArea, BorderLayout.CENTER);

        return textAreaPanel;
    }

    private static JTextArea createPlaceholderTextArea(String placeholder) {
        JTextArea textArea = new JTextArea(1, 10);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        textArea.setText(placeholder);
        textArea.setForeground(Color.GRAY);

        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(placeholder)) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().trim().isEmpty()) {
                    textArea.setText(placeholder);
                    textArea.setForeground(Color.GRAY);
                }
            }
        });

        return textArea;
    }

    private static JPanel createChartsArea() {
        JPanel chartsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Pendulum Heun's energies motion",
                "Time",
                "Value",
                heunMotionDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chartsPanel.add(new ChartPanel(chart), gbc);

        gbc.gridx = 1;

        JFreeChart omegaHeunChart = ChartFactory.createXYLineChart(
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
        omegaHeunChart.getXYPlot().setRenderer(renderer);
        chartsPanel.add(new ChartPanel(omegaHeunChart), gbc);
        gbc.gridx = 0;

        gbc.gridy = 1;
        JFreeChart chart2 = ChartFactory.createXYLineChart(
                "Pendulum RK4 energies motion",
                "Time",
                "Value",
                RK4MotionDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chartsPanel.add(new ChartPanel(chart2), gbc);

        gbc.gridx = 1;

        JFreeChart omegaRK4Chart = ChartFactory.createXYLineChart(
                "Pendulum RK4 Omega",
                "Alpha",
                "Omega",
                RK4OmegaDataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        omegaRK4Chart.getXYPlot().setRenderer(renderer);
        chartsPanel.add(new ChartPanel(omegaRK4Chart), gbc);

        return chartsPanel;
    }

    private static XYLineAndShapeRenderer createOmegaRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 4, 4));

        return renderer;
    }

    private static void runSimulation() {
        resetDatasets();

        double mass = 0;
        double length = 0;
        double angle = 0;
        double time = 0;
        double timeStep = 0;

        try {
            mass = Double.parseDouble(massTextArea.getText());
            length = Double.parseDouble(lengthTextArea.getText());
            angle = Double.parseDouble(angleTextArea.getText());
            time = Double.parseDouble(timeTextArea.getText());
            timeStep = Double.parseDouble(timeStepTextArea.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

        Pendulum pendulum = new Pendulum(mass, length, angle);

        pendulum.simulateHeunMotion(time, timeStep);
        heunMotionDataset.addSeries(pendulum.getHeunMotionSeriesEp());
        heunMotionDataset.addSeries(pendulum.getHeunMotionSeriesEk());
        heunOmegaDataset.addSeries(pendulum.getHeunOmegaSeries());

        pendulum.simulateRK4Motion(time, timeStep);
        RK4MotionDataset.addSeries(pendulum.getRK4MotionSeriesEp());
        RK4MotionDataset.addSeries(pendulum.getRK4MotionSeriesEk());
        RK4OmegaDataset.addSeries(pendulum.getRK4OmegaSeries());
    }

    private static void resetDatasets() {
        heunMotionDataset.removeAllSeries();
        heunOmegaDataset.removeAllSeries();
        RK4MotionDataset.removeAllSeries();
        RK4OmegaDataset.removeAllSeries();
    }
}
