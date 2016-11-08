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
private static final String CREATE_TABLE ="CREATE TABLE IF NOT EXISTS leaderboard" +
        " (user_id INTEGER PRIMARY KEY NOT NULL, " +
        " score INTEGER NOT NULL" +
        ");";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS leaderboard;";
    private static final String SELECT_TOP = "SELECT * FROM leaderboard order by score limit %d;";

    public List<Leaderboard> getAll() {
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


    public static List<Leaderboard> getTop(int N) {
        String SELECT_TOP_1 = new String("SELECT leaderboard.user_id as user_id, users.login as login, leaderboard.score " +
                "as score FROM leaderboard LEFT OUTER JOIN users ON users.id = leaderboard.user_id ORDER BY score DESC LIMIT " + N + ";");
        List<Leaderboard> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_TOP_1);
            while (rs.next()) {
                persons.add(mapToLeaderboard2(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to Top.", e);
            return Collections.emptyList();
        }
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
        INSERT.append("INSERT INTO leaderboard (user_id, score) " + "VALUES" + "(" +
                person.getUser() + ", " + person.getScore() + ");");
        System.out.println(INSERT);
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeUpdate(INSERT.toString());
        } catch (SQLException e) {
            log.error("Failed to insert.", e);
        }
    }
    public static int createTable() {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            System.out.println(CREATE_TABLE);
            stm.executeUpdate(CREATE_TABLE);
                        return 1;
        } catch (SQLException e) {
            log.error("Failed to insert.", e);
        }
              return -1;
    }
    public static int dropTable() {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            System.out.println(DROP_TABLE);
            stm.executeUpdate(DROP_TABLE);
            return 1;
        } catch (SQLException e) {
            log.error("Failed to insert.", e);
        }
        return -1;
    }
    public static void update(Leaderboard person) {
        StringBuffer UPDATE = new StringBuffer();
        UPDATE.append("UPDATE leaderboard set " + "score=" +
                person.getScore() +" WHERE user_id=" +person.getUser()+ ";");
        System.out.println(UPDATE);
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeUpdate(UPDATE.toString());
        } catch (SQLException e) {
            log.error("Failed to update.", e);
        }
    }

    private static Leaderboard mapToLeaderboard(ResultSet rs) throws SQLException {
        return new Leaderboard()
                .setUser(rs.getInt("user_id"))
                .setScore(rs.getInt("score"));

    }

    private static Leaderboard mapToLeaderboard2(ResultSet rs) throws SQLException {
        return new Leaderboard()
                .setUser(rs.getInt("user_id"))
                .setScore(rs.getInt("score"))
                .setLogin(rs.getString("login"));
    }
}
