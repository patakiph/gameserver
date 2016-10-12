package model;

/**
 * Created by Ольга on 06.10.2016.
 */
public class Vector {
    public double dx = 0;
    public double dy = 0;

    public Vector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
}
