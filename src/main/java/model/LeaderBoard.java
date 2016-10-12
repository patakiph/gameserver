package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Created by Ольга on 07.10.2016.
 */
public class LeaderBoard extends Entity{

    private TreeSet<String> players;
    private Font font;
    private GameSession gameSession;

    public final int top = 10;
    public LeaderBoard(String s, double x, double y, Color color, Shape shape){
        super(s,x,y,color,shape);
    }
    public void setFont(Font font){
        this.font = font;
    }

    public void setGameSession(GameSession session){
        gameSession = session;
        //initialize view of the LeaderBoard
    }
    @Override
    public void update(){
        //gets array of players from gameSession
    };
}
