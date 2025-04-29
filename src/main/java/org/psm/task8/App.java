package org.psm.task8;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UI().createAndShowGUI();
        });
    }
}