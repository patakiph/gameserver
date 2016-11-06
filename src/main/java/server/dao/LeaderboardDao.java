package server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.servlets.users_data.Leaderboard;
import server.servlets.users_data.LeaderboardComparator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public class LeaderboardDao  {
    private static final Logger log = LogManager.getLogger(LeaderboardDao.class);

    private static final String SELECT_ALL_PERSONS =
            "SELECT * FROM leaderboard;";
    private static final String INSERT_PERSON =
            "INSERT INTO leaderboard (id, user_id, score) VALUES(%d,%d,%d);";

    public static List<Leaderboard> getAll() {
        List<Leaderboard> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_PERSONS);
            while (rs.next()) {
                persons.add(mapToLeaderboard(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }
        Collections.sort( persons, new LeaderboardComparator());
        return persons;
    }



    public static List<Leaderboard> getAllWhere(String ... conditions) {
        StringBuffer SELECT_ALL_PERSONS_1 = new StringBuffer("SELECT * FROM leaderboard WHERE ");
        for (int i = 0 ; i < conditions.length; i++){
            SELECT_ALL_PERSONS_1.append(conditions[i] + "AND ");
        }
        SELECT_ALL_PERSONS_1.replace(SELECT_ALL_PERSONS_1.length() - 4,SELECT_ALL_PERSONS_1.length(),"");
        List<Leaderboard> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_PERSONS_1.toString());
            while (rs.next()) {
                persons.add(mapToLeaderboard(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }

        return persons;
    }


    public static void insert(Leaderboard person) {
        StringBuffer INSERT = new StringBuffer();
        INSERT.append("INSERT INTO leaderboard (id, user_id, score) " + "VALUES" + "(" +
                person.getId() + "," + person.getUser() + "," + person.getScore() + ");");
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeQuery(INSERT.toString());
        } catch (SQLException e) {
            log.error("Failed to insert.", e);
        }
    }

    public static void update(Leaderboard person) {
        StringBuffer UPDATE = new StringBuffer();
        UPDATE.append("UPDATE leaderboard set " + "score=" +
                person.getScore() +"WHERE user_id=" +person.getUser()+ ");");
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeQuery(UPDATE.toString());
        } catch (SQLException e) {
            log.error("Failed to update.", e);
        }
    }
    private static Leaderboard mapToLeaderboard(ResultSet rs) throws SQLException {
        return new Leaderboard()
                .setId(rs.getInt("id"))
                .setUser(rs.getInt("user_id"))
                .setScore(rs.getInt("score"));

    }
}
