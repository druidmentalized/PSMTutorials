package org.psm.task9;

import javax.swing.*;

public class PlantApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int iterations = 6;
            double angleDegree = 25;
            double stepLength  = 225.0 / Math.pow(2, iterations);

            String lSystem = PlantSimulator.generate(iterations);
            PlantUI panel = new PlantUI(lSystem, angleDegree, stepLength);

            JFrame frame = new JFrame("Fractal Plant – Animated");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Timer timer = new Timer(1, event -> {
                panel.step();
                if (panel.drawIndex >= lSystem.length()) {
                    ((Timer)event.getSource()).stop();
                    System.out.println("Drawing finished.");
                }
            });
            timer.start();
        });
    }
}