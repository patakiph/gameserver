package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ольга on 06.10.2016.
 */
public class Cell extends Entity {
    protected int mass = 1;
    protected final int MAX_MASS = 100;
    protected int speedMassLoose = 0;
    public Cell (String s, double x, double y, Color color, Shape shape){
        super(s, x, y, color, shape);
    }
    public int getMass(){
        return mass;
    }
    public void setMass(int m){
        mass = m;
    }
    public int getSpeedMassLoose(){
        return speedMassLoose;
    }
    public void setSpeedMassLoose(int newSpeed){
        speedMassLoose = newSpeed;
    }

    public ArrayList<EjectedMass> ejectMass(){
        EjectedMass ejMass = new EjectedMass("",position.x,position.y,color,shape);
        ArrayList<EjectedMass> ejectedMasses = new ArrayList<>();
        ejectedMasses.add(ejMass);
        return ejectedMasses;
    }
    public void update(){

    }
}
