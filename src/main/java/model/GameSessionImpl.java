package model;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by Ольга on 06.10.2016.
 */
public abstract class GameSessionImpl implements GameSession, GameConstants{
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Food> food = new ArrayList<>();
    public ArrayList<EjectedMass> ejectedMass = new ArrayList<>();
    public ArrayList<Shelter> shelters = new ArrayList<>();
    public Background bg;
    public LeaderBoard leaderBoard;

    public GameSessionImpl(){
        for (int i = 0; i < AMOUNT_OF_FOOD; i++){
            Food f = new Food("",0,0, Color.RED, new RoundRectangle2D.Float());
            food.add(f);
        }
        for (int i = 0; i < NUMBER_OF_SHELTERS; i++){
            Shelter sh = new Shelter("",0,0, Color.RED, new RoundRectangle2D.Float());
            shelters.add(sh);
        }
        bg = new Background("");

    }
    public void join(@NotNull Player player) {
        if (players.size()<MAX_PLAYERS_IN_SESSION){
        players.add(player);
        }
        else
        throw new MaxPlayersExeption("No more than 10 players in one session");
    };
}
