package org.psm.task9;

import java.awt.Graphics2D;

public class PlantDrawer implements Cloneable {
    private double x, y;
    private double angleDeg;

    public PlantDrawer(double x, double y, double angleDeg) {
        this.x = x;
        this.y = y;
        this.angleDeg = angleDeg;
    }

    public void turnLeft(double d)  { angleDeg += d; }
    public void turnRight(double d) { angleDeg -= d; }

    public void forward(double dist, Graphics2D g) {
        double rad = Math.toRadians(angleDeg);
        double nx = x + dist * Math.cos(rad);
        double ny = y + dist * Math.sin(rad);
        g.drawLine((int)x, (int)y, (int)nx, (int)ny);
        x = nx;  y = ny;
    }

    @Override
    public PlantDrawer clone() {
        try { return (PlantDrawer)super.clone(); }
        catch (CloneNotSupportedException e) { throw new AssertionError(); }
    }
}