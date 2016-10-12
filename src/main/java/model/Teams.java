package model;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by Ольга on 07.10.2016.
 */
public class Teams extends GameSessionImpl {

    public Teams(){
        super();
        leaderBoard = new LeaderBoard("", 0, 0, Color.WHITE, new RoundRectangle2D.Float());
    }
}
