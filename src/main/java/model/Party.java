package model;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Ольга on 07.10.2016.
 */
public class Party extends GameSessionImpl{
    private int room = 0;
    private String code ="";
    public Party(){
        super();
        leaderBoard = new LeaderBoard("", 0, 0, Color.WHITE, new Rectangle2D.Float());
    }
    public void setRoom(int r){
        room = r;
    }
    public void setCode(String str){
        code = str;
    }
}
