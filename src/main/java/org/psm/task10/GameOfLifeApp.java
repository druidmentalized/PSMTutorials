package org.psm.task10;

import javax.swing.*;

public class GameOfLifeApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameOfLifeUI().runGameOfLife());
    }
}
