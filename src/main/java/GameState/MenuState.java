package GameState;

import model.Background;

import java.awt.*;

/**
 * Created by Ольга on 06.10.2016.
 */
public class MenuState extends GameState {

    private Background bg;
    private int currentChoice;
    private String[] options = {"Start", "Help", "Quit"};
    public MenuState(GameStateManager gsm){
        this.gsm = gsm;
        try {
            bg = new Background("");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void update() {};
    public void init(){};
    public void draw(Graphics2D g){};
    public void keyPressed(int k){};
    public void keyReleased(int k){};
}
