package Main;

import GameState.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Created by Ольга on 06.10.2016.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{

    //размеры окна
    public static final int WIDTH = 320;
    public static final int HIGHT = 280;
    public static final int SCALE = 2;

    //игровой поток(thread)
    private Thread thread;
    private boolean running;
    private int FPS = 60; //frames per second
    private long TargetTime = 1000 / FPS;

    //images

    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gsm;
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify(){
        super.addNotify();
        if (thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }
    public void init(){

        image = new BufferedImage(WIDTH,HIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
        gsm = new GameStateManager();
    }
    public void run(){
        init();

        long start;
        long elapsed;
        long wait;

//game loop
        while (running){

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

        }

    }

    private void update(){
        gsm.update();
    }
    private void draw(){
        gsm.draw(g);
    };
    private void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key){}
    public void keyPressed(KeyEvent key){
        gsm.keyPressed(key.getKeyCode());
    }
    public void keyReleased(KeyEvent key){
        gsm.keyReleased(key.getKeyCode());
    }

}
