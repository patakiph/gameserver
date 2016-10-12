package model;

/**
 * Created by Ольга on 06.10.2016.
 */
public class Position {
    public double x = 0;
    public double y = 0;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double dx) {
        this.x = x;
    }

    public void setY(double dy) {
        this.y = y;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }
}
