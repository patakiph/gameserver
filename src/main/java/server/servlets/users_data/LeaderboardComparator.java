package server.servlets.users_data;

import server.servlets.users_data.*;
/**
 * Created by Ольга on 06.11.2016.
 */
import java.util.Comparator;

public class LeaderboardComparator implements Comparator {

    @Override
   public int compare(Object o1, Object o2) {
        Leaderboard l1 = (Leaderboard) o1;
        Leaderboard l2 = (Leaderboard) o2;
        // descending order (ascending order would be:
        // o1.getGrade()-o2.getGrade())
        return l2.getScore() - l1.getScore();
    }

}