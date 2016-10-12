package model;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Created by Ольга on 07.10.2016.
 */
public class FFA extends GameSessionImpl implements GameConstants{

    public FFA(){
        super();
        leaderBoard = new LeaderBoard("", 0, 0, Color.WHITE, new Rectangle2D.Float());
    }
}
