package org.example.task3;

import javax.swing.SwingUtilities;

public class PendulumMotionApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PendulumMotionUI().createAndShowGUI());
    }
}