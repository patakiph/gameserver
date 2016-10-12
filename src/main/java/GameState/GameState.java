package GameState;

import java.awt.*;

/**
 * Created by Ольга on 06.10.2016.
 */
public abstract class GameState {

    protected GameStateManager gsm;
    public abstract void update();
    public abstract void init();
    public abstract void draw(Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
}
