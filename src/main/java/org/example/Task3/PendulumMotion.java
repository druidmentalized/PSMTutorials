package org.example.Task3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class PendulumMotion {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(PendulumMotion::runApplication);

    }

    public static void runApplication() {
        double deltaTime = 0.05;
        double time = 5.0;

        XYSeriesCollection heunDataset = new Pendulum(1,1, 45).simulateHeunMotion(time, deltaTime);

        JFrame window = new JFrame("Pendulum Motion");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);


        window.setLayout(new GridBagLayout());
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
                heunDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        window.add(new ChartPanel(chart), gbc);


        XYSeriesCollection RK4Dataset = new Pendulum(1,1, 45).simulateRK4Motion(time, deltaTime);

        gbc.gridy = 1;
        JFreeChart chart2 = ChartFactory.createXYLineChart(
                "Pendulum RK4 energies motion",
                "Time",
                "Value",
                RK4Dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        window.add(new ChartPanel(chart2), gbc);

        window.setVisible(true);
    }
}
