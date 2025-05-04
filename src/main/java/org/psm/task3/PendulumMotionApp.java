package org.psm.task3;

import javax.swing.*;

public class PendulumMotionApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PendulumMotionUI().createAndShowGUI());
    }
}