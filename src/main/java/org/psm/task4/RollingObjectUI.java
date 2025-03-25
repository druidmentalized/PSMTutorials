package org.psm.task4;

import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static org.psm.task3.PendulumSimulator.runSimulation;

public class RollingObjectUI {
    private JFrame window;

    // Text fields
    private JTextField massField;
    private JTextField heightField;
    private JTextField radiusField;
    private JTextField angleField;
    private JTextField timeField;
    private JTextField timeStepField;

    // Chart datasets
    //todo: create them later

    // Chart panels
    //todo make them later

    public void createAndShowGUI() {
        window = new JFrame("Rolling Object");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200, 900);
        window.setLocationRelativeTo(null);
        window.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        JPanel inputPanel = createInputPanel();
        window.add(inputPanel, gbc);
        JPanel chartsPanel = createChartsPanel();

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        window.add(chartsPanel, gbc);

        window.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        massField = createTextField("Mass");
        heightField = createTextField("Height");
        radiusField = createTextField("Radius");
        angleField = createTextField("Angle (°)");
        timeField = createTextField("Time");
        timeStepField = createTextField("Time Step");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(createLabeledField("Mass:", massField), gbc);

        gbc.gridx = 1;
        inputPanel.add(createLabeledField("Height:", heightField), gbc);

        gbc.gridx = 2;
        inputPanel.add(createLabeledField("Radius:", radiusField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(createLabeledField("Angle:", angleField), gbc);

        gbc.gridx = 1;
        inputPanel.add(createLabeledField("Time:", timeField), gbc);

        gbc.gridx = 2;
        inputPanel.add(createLabeledField("Time Step:", timeStepField), gbc);

        // Simulate button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(_ -> runSimulation());
        inputPanel.add(simulateButton, gbc);

        return inputPanel;
    }

    private JPanel createChartsPanel() {
        JPanel chartsPanel = new JPanel(new GridBagLayout());
        //todo make later
        return chartsPanel;
    }

    private void runSimulation() {
        try {
            double mass = Double.parseDouble(massField.getText());
            double height = Double.parseDouble(heightField.getText());
            double radius = Double.parseDouble(radiusField.getText());
            double angle = Double.parseDouble(angleField.getText());
            double time = Double.parseDouble(timeField.getText());
            double timeStep = Double.parseDouble(timeStepField.getText());

            SimulationResult result = RollingSimulator.runSimulation(mass, height, radius, angle, time, timeStep);

            //todo make dataset painting
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window, "Invalid input! Please enter numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(10);
        textField.setFont(new Font("Monospaced", Font.PLAIN, 20));
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        return textField;
    }

    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.PLAIN, 20));
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void updateDataset(XYSeriesCollection target, XYSeriesCollection source) {
        target.removeAllSeries();
        for (int i = 0; i < source.getSeriesCount(); i++) {
            target.addSeries(source.getSeries(i));
        }
    }
}
