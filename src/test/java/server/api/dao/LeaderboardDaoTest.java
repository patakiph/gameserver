package server.api.dao;

/**
 * Created by Ольга on 08.11.2016.
 */
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import server.dao.LeaderboardDao;
import server.dao.UsersDAO;
import server.servlets.users_data.Leaderboard;
import server.servlets.users_data.LeaderboardComparator;
import server.servlets.users_data.User;


import javax.validation.constraints.AssertTrue;

import java.util.List;

import static org.junit.Assert.*;


public class LeaderboardDaoTest {
    private LeaderboardDao leaderboardDao = new LeaderboardDao();
    private UsersDAO usersDao = new UsersDAO();
    Leaderboard ldb = new Leaderboard().setScore(15).setUser(usersDao.findByLogin("lolly").get(0).getId());

    @Test
    public void getTopTest() {
        int ldbTopSize = leaderboardDao.getTop(3).size();
        assertTrue(ldbTopSize <= 3);
        List<Leaderboard> ldb = leaderboardDao.getAll();
        List<Leaderboard> ldbTop = leaderboardDao.getTop(3);
        System.out.println(ldb + "\n" + ldbTop);
        Collections.sort(ldb, new LeaderboardComparator());
        for (int i = 0; i < ldbTopSize; i++) {
            assertEquals(ldb.get(i), ldbTop.get(i));
        }
    }

    @Test
    public void updateTest() {
        Leaderboard old = leaderboardDao.getAllWhere("user_id = 170").get(0);
        int score = old.getScore();
        old.setScore(20 + score);
        leaderboardDao.update(old);
        assertTrue(leaderboardDao.getAllWhere("user_id = 170").get(0).getScore() == score + 20);
    }

    @Test
    public void insertTest() {
        int before = leaderboardDao.getAll().size();
        leaderboardDao.insert(ldb);
        assertEquals(before + 1, leaderboardDao.getAll().size());
    }

    @Test
    public void createTest() {
        assertTrue(leaderboardDao.createTable() == 1);
    }
}