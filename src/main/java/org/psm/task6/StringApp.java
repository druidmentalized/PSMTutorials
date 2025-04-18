package org.psm.task6;

import javax.swing.*;

public class StringApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StringUI().createAndShowGUI());
    }
}