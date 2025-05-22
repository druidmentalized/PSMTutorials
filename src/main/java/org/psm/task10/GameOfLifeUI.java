package org.psm.task10;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameOfLifeUI {
    private final Random random = new Random();
    private final int GRID_SIZE = 100;
    private final Color aliveColor = new Color(144, 238, 144);

    private int underpopulationThreshold = 2;
    private int overpopulationThreshold = 3;
    private int reproductionThreshold = 3;

    public void runGameOfLife() {
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        frame.setLayout(new BorderLayout());

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.BLACK);
        frame.add(wrapperPanel, BorderLayout.CENTER);

        wrapperPanel.add(createRulesChangerPanel(), BorderLayout.NORTH);
        wrapperPanel.add(createLifeGrid(), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createRulesChangerPanel() {
        JPanel controllerPanel = new JPanel(new GridBagLayout());
        controllerPanel.setPreferredSize(new Dimension(800, 100));
        controllerPanel.setMinimumSize(new Dimension(800, 100));
        controllerPanel.setMaximumSize(new Dimension(800, 100));
        controllerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Labels and fields
        JLabel underLabel = new JLabel("Underpopulation:");
        JLabel overLabel = new JLabel("Overpopulation:");
        JLabel reproductionLabel = new JLabel("Reproduction:");
        JTextField underField = new JTextField(String.valueOf(underpopulationThreshold), 3);
        JTextField overField = new JTextField(String.valueOf(overpopulationThreshold), 3);
        JTextField reproductionField = new JTextField(String.valueOf(reproductionThreshold), 3);

        JButton applyButton = new JButton("Apply Rules");
        applyButton.addActionListener(e -> {
            try {
                int newUnder = Integer.parseInt(underField.getText());
                int newOver = Integer.parseInt(overField.getText());
                int newReproduction = Integer.parseInt(reproductionField.getText());

                underpopulationThreshold = newUnder;
                overpopulationThreshold = newOver;
                reproductionThreshold = newReproduction;

                JOptionPane.showMessageDialog(controllerPanel, "Rules updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(controllerPanel, "Please enter valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        controllerPanel.add(underLabel, gbc);
        gbc.gridx = 1; controllerPanel.add(underField, gbc);

        gbc.gridx = 2; controllerPanel.add(overLabel, gbc);
        gbc.gridx = 3; controllerPanel.add(overField, gbc);

        gbc.gridx = 4; controllerPanel.add(reproductionLabel, gbc);
        gbc.gridx = 5; controllerPanel.add(reproductionField, gbc);

        gbc.gridx = 6; gbc.gridwidth = 2;
        controllerPanel.add(applyButton, gbc);

        return controllerPanel;
    }

    private JPanel createLifeGrid() {
        JPanel lifeGridWrapper = new JPanel(new GridBagLayout());
        lifeGridWrapper.setBackground(Color.BLACK);

        JPanel lifeGrid = createGridPanel();
        JLabel[][] cells = new JLabel[GRID_SIZE][GRID_SIZE];
        boolean[][] cellsAlive = initializeGridCells(lifeGrid, cells);

        startSimulationTimer(cells, cellsAlive, lifeGrid);

        lifeGridWrapper.add(lifeGrid);
        return lifeGridWrapper;
    }

    private JPanel createGridPanel() {
        JPanel panel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        panel.setPreferredSize(new Dimension(700, 700));
        panel.setMinimumSize(new Dimension(700, 700));
        panel.setMaximumSize(new Dimension(700, 700));
        panel.setBackground(Color.BLACK);
        return panel;
    }

    private boolean[][] initializeGridCells(JPanel panel, JLabel[][] cells) {
        boolean[][] aliveMatrix = new boolean[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JLabel cell = new JLabel();
                cell.setOpaque(true);
                boolean isAlive = random.nextInt(5) == 3;
                cell.setBackground(isAlive ? aliveColor : Color.BLACK);
                cells[i][j] = cell;
                aliveMatrix[i][j] = isAlive;
                panel.add(cell);
            }
        }
        return aliveMatrix;
    }

    private void startSimulationTimer(JLabel[][] cells, boolean[][] cellsAlive, JPanel lifeGrid) {
        GameOfLifeSimulator simulator = new GameOfLifeSimulator(cellsAlive);
        Timer timer = new Timer(45, _ -> {
            simulator.makeEvolutionStep(
                    underpopulationThreshold, overpopulationThreshold, reproductionThreshold
            );

            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    Color color = cellsAlive[i][j] ? aliveColor : Color.BLACK;
                    cells[i][j].setBackground(color);
                }
            }
        });
        timer.start();
    }
}
