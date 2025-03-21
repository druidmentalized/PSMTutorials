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
        Pendulum pendulum = new Pendulum(1,1, 45);
        double deltaTime = 0.05;
        double time = 5.0;

        XYSeriesCollection dataset = pendulum.simulateMotion(time, deltaTime);

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
                "Pendulum energies motion",
                "Time",
                "Val",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        window.add(new ChartPanel(chart), gbc);

        window.setVisible(true);
    }
}
