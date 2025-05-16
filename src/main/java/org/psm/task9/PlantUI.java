package org.psm.task9;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class PlantUI extends JPanel {
    private final String instructions;
    private final double turnAngle;
    private final double baseStep;

    public int drawIndex = 0;

    public PlantUI(String instructions, double turnAngle, double baseStep) {
        this.instructions = instructions;
        this.turnAngle    = turnAngle;
        this.baseStep     = baseStep;
        setBackground(Color.WHITE);
    }

    public void step() {
        if (drawIndex < instructions.length()) {
            drawIndex++;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D)g0;

        int w = getWidth(), h = getHeight();
        g.translate(w/2.0, h);
        g.scale(1, -1);

        PlantDrawer drawer = new PlantDrawer(0, 0, 90);
        Stack<PlantDrawer> stack = new Stack<>();

        for (int i = 0; i < drawIndex; i++) {
            char c = instructions.charAt(i);
            switch (c) {
                case 'F' ->  drawer.forward(baseStep, g);
                case '+' -> drawer.turnLeft(turnAngle);
                case '-' -> drawer.turnRight(turnAngle);
                case '[' -> stack.push(drawer.clone());
                case ']' -> drawer = stack.pop();
            }
        }
    }
}