package server.dao;

/**
 * Created by Ольга on 08.11.2016.
 */

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import server.servlets.users_data.User;


import javax.validation.constraints.AssertTrue;

import java.util.List;

import static org.junit.Assert.*;


public class UsersDaoTest {
    private UsersDAO usersDao = new UsersDAO();
    private User lolita = new User()
            .setName("Lolita")
            .setEmail("myemail@mail.ru")
            .setLogin("lolly")
            .setPassword("123");
    private User harry = new User()
            .setName("Harry Potter")
            .setEmail("ilovedambldor@mail.ru")
            .setLogin("potter")
            .setPassword("123");

    @Test
    public void getAllTest() throws Exception {
        usersDao.getAll();
        assertTrue(usersDao.getAll().size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        int before = usersDao.getAll().size();
        usersDao.insert(lolita);
        usersDao.insert(harry);
        assertEquals(before + 2, usersDao.getAll().size());
    }

    @Test
    public void findWhereTest() throws Exception {
        List<User> oldy = usersDao.getAllWhere("name = 'Lolita'");
        assertTrue(
                oldy.stream()
                        .map(User::getName)
                        .anyMatch(s -> s.equals("Lolita"))
        );
    }

    @Test
    public void updateTest() throws Exception {
        List<User> oldy = usersDao.getAllWhere("name = 'Lolita'");
        User lolitaOld = oldy.get(0);
        lolitaOld.setEmail("myNewEmail@mail.ru");
        usersDao.update(lolitaOld);
        User lolitaNew = usersDao.getAllWhere("name = 'Lolita'").get(0);
        assertEquals(lolitaNew.getEmail(),"myNewEmail@mail.ru");
    }

    @Test
    public void findByLogin() throws Exception {
        List<User> oldy = usersDao.getAllWhere("login = 'lolly'");
        List<User> newy = usersDao.findByLogin("lolly");
        assertEquals(oldy,newy);
    }

}