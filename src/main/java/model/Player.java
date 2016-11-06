package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player{
  @NotNull
  private static final Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private String name;
  private ArrayList<Cell> cells = new ArrayList<Cell>();
  private boolean isAlive = true;
  private boolean isSplit = false;
  private String region;
  private boolean isLoggedIn = false;
  private int cellsEaten = 0;
  private int coins = 0;
  private int XP = 0;
  private int topPosition;
  private int score;
  private int foodEaten = 0;
  private Team team;
  private int room;
  private String roomCode;
  private GameMode gameMode= GameMode.FFA;

  //TODO maybe we need something else here?

  /**
   * Create new Player
   *
   * @param name        visible name
   */
  public Player(@NotNull String name, String region) {
    this.name = name;
    Cell cell = new Cell("",0,0, Color.BLUE,new RoundRectangle2D.Float());
    cells.add(cell);
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }

  }
  public void incrementCellsEaten(){
    cellsEaten++;
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }
}
