package model;

import java.awt.*;

/**
 * Created by Ольга on 07.10.2016.
 */
public class EjectedMass extends Cell {
    public EjectedMass(String s, double x, double y, Color color, Shape shape){
        super(s,x,y,color,shape);
        this.mass = 3;
    }
    @Override
    public void update(){
    }
}
