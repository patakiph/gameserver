package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Created by Ольга on 06.10.2016.
 */
public abstract class Entity{
    protected static final Logger log = LogManager.getLogger(Player.class);
    //расположение и направление
    protected Position position = new Position(0,0);
    protected Vector vector = new Vector(0,0);
    protected Direction direction;
    protected State state = State.IDLE;
    //размеры
    protected Size size;
    //скорость
    protected double speed = 0;
    protected final double MAXSPEED = 6; //max speed

    //графика
    protected BufferedImage texture;
    protected Shape shape = new RoundRectangle2D.Float();
    protected Color color;

    public Entity(String s, double x, double y, Color color, Shape shape) {
        position.setPosition(x,y);
        this.color = color;
        this.shape = shape;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public Position getPosition(){
        return position;
    }
    public void setPosition(Position newPosition){
        position.setPosition(newPosition.getX(),newPosition.getY());
    }
    public Vector getVector(){
        return vector;
    }
    public void setVector(Vector newVector){
        vector.setVector(newVector.getDx(),newVector.getDy());
    }
    public int getWidth(){
        return size.WIDTH;
    }
    public int getHeight(){
        return size.HEIGHT;
    }
    public void setWidth(int w){
        size.WIDTH = w;
    }
    public void setHeight(int h){
        size.HEIGHT = h;
    }
    public void update() {
        position.setPosition(position.getX()+vector.getDx(),position.getY()+vector.getDy());
    }

    public double getSpeed(){
        return speed;
    }
    public void setSpeed(double newSpeed){
        speed = newSpeed;
    }
 //   public abstract void draw(Graphics2D g);

    public Rectangle getRectangle(){
        return new Rectangle((int)position.getX(),(int)position.getY(),size.WIDTH,size.HEIGHT);
    }
    public boolean intersects(Entity e){
        Rectangle r1 = this.getRectangle();
        Rectangle r2 = e.getRectangle();
        return r1.intersects(r2);
    }

}
