package model;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Ольга on 07.10.2016.
 */
public class Experimintal extends GameSessionImpl{
    public Experimintal(){
        super();
        leaderBoard = new LeaderBoard("", 0, 0, Color.WHITE, new Rectangle2D.Float());
    }
}
