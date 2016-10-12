package model;

import java.awt.*;

/**
 * Created by Ольга on 06.10.2016.
 */
public class Shelter extends Entity{
    public Shelter (String s, double x, double y, Color color, Shape shape){
        super(s, x, y, color, shape);
        color = color.GREEN;
    }
}
