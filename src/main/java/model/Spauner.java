package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ольга on 07.10.2016.
 */
public class Spauner extends Cell {

    public Spauner (String s, double x, double y, Color color, Shape shape){
        super(s,x,y,color,shape);
    }
    @Override
    public void update(){
    }
    @Override
    public ArrayList<EjectedMass> ejectMass(){
        ArrayList<EjectedMass> ejectedMasses = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        ejectedMasses.add(new EjectedMass("",position.x,position.y,color,shape));
        return ejectedMasses;
    }
}
