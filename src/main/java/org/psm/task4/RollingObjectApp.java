package org.psm.task4;

import javax.swing.*;

public class RollingObjectApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RollingObjectUI().createAndShowGUI());
    }
}
