package model;

/**
 * Created by Ольга on 06.10.2016.
 */
public class MaxPlayersExeption extends RuntimeException{

    public MaxPlayersExeption(String message) {
        super("MaxPlayersExeption: " + message);
    }
}
