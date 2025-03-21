package org.psm.task2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ProjectileMotion {
    private final static double g   = 9.81;

    private static JTextArea sxTextArea;
    private static JTextArea syTextArea;
    private static JTextArea vxTextArea;
    private static JTextArea vyTextArea;
    private static JTextArea massTextArea;
    private static JTextArea dragCoefficientTextArea;
    private static JTextArea timeStepTextArea;

    private static final XYSeriesCollection dataset = new XYSeriesCollection();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProjectileMotion::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(addSettingsPanel(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        frame.add(addChartPanel(), gbc);

        frame.setVisible(true);
    }

    private static JPanel addSettingsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        sxTextArea = createPlaceholderTextArea("Sx");
        syTextArea = createPlaceholderTextArea("Sy");
        vxTextArea = createPlaceholderTextArea("Vx");
        vyTextArea = createPlaceholderTextArea("Vy");


        gbc.gridy = 0;
        gbc.gridx = 0;
        panel.add(sxTextArea, gbc);

        gbc.gridx = 1;
        panel.add(syTextArea, gbc);

        gbc.gridx = 2;
        panel.add(vxTextArea, gbc);

        gbc.gridx = 3;
        panel.add(vyTextArea, gbc);

        massTextArea = createPlaceholderTextArea("m");
        dragCoefficientTextArea = createPlaceholderTextArea("k");
        timeStepTextArea = createPlaceholderTextArea("Δt");

        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(massTextArea, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(timeStepTextArea, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        panel.add(dragCoefficientTextArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(_ -> fillDataset());
        panel.add(calculateButton, gbc);

        return panel;
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

    private static JPanel addChartPanel() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Euler and MidPoint method functions",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        return new ChartPanel(chart);
    }

    private static void fillDataset() {
        dataset.removeAllSeries();

        double Sx = 0;
        double Sy = 0;
        double Vx = 0;
        double Vy = 0;
        double mass = 0;
        double dragCoefficient = 0;
        double timeStep = 0;

        try {
            Sx = Double.parseDouble(sxTextArea.getText());
            Sy = Double.parseDouble(syTextArea.getText());
            Vx = Double.parseDouble(vxTextArea.getText());
            Vy = Double.parseDouble(vyTextArea.getText());
            mass = Double.parseDouble(massTextArea.getText());
            dragCoefficient = Double.parseDouble(dragCoefficientTextArea.getText());
            timeStep = Double.parseDouble(timeStepTextArea.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

        XYSeries series1 = calculateEuler(Sx, Sy, Vx, Vy, mass, dragCoefficient, timeStep);
        XYSeries series2 = calculateMidPoint(Sx, Sy, Vx, Vy, mass, dragCoefficient, timeStep);

        dataset.addSeries(series1);
        dataset.addSeries(series2);
    }

    private static XYSeries calculateEuler(double Sx, double Sy, double Vx, double Vy, double m, double k, double dt) {
        XYSeries series = new XYSeries("Euler");

        series.add(Sx, Sy);

        double gx = 0;
        double gy = -g;

        while (Sy >= 0.0) {
            //X
            double oldSx = Sx;
            double ax = (m * gx - k * Vx) / m;
            Vx = Vx + ax * dt;
            Sx = Sx + Vx * dt;

            //Y
            double oldSy = Sy;
            double ay = (m * gy - k * Vy) / m;
            Vy = Vy + ay * dt;
            Sy = Sy + Vy * dt;

            if (interpolate(Sx, Sy, series, oldSx, oldSy)) break;
        }

        return series;
    }

    private static XYSeries calculateMidPoint(double Sx, double Sy, double Vx, double Vy, double m, double k, double dt) {
        XYSeries series = new XYSeries("MidPoint");

        series.add(Sx, Sy);

        double gx = 0;
        double gy = -g;

        while (Sy >= 0.0) {
            double oldSx = Sx;
            double oldSy = Sy;
            double oldVx = Vx;
            double oldVy = Vy;

            //X
            double ax = (m * gx - k * oldVx) / m;
            double vxHalf = oldVx + 0.5 * dt * ax;
            double axHalf = (m * gx - k * vxHalf) / m;
            Vx = oldVx + dt * axHalf;
            Sx = oldSx + dt * vxHalf;

            //Y
            double ay = (m * gy - k * oldVy) / m;
            double vyHalf = oldVy + 0.5 * dt * ay;
            double ayHalf = (m * gy - k * vyHalf) / m;
            Vy = oldVy + dt * ayHalf;
            Sy = oldSy + dt * vyHalf;

            if (interpolate(Sx, Sy, series, oldSx, oldSy)) break;
        }

        return series;
    }

    //helper

    private static boolean interpolate(double Sx, double Sy, XYSeries series, double oldSx, double oldSy) {
        if (Sy < 0.0) {
            double dyOldNew = Sy - oldSy;
            double alpha = -oldSy / dyOldNew;

            double dxOldNew = Sx - oldSx;
            double finalSx = oldSx + alpha * dxOldNew;

            series.add(finalSx, 0.0);
            return true;
        }

        series.add(Sx, Sy);
        return false;
    }
}