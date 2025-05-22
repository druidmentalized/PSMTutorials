package org.psm.task8;

import javax.swing.*;

public class LorenzApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LorenzUI().createAndShowGUI());
    }
}