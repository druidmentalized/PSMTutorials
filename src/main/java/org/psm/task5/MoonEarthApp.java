package org.psm.task5;

import javax.swing.*;

public class MoonEarthApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MoonEarthUI().createAndShowGUI());
    }
}