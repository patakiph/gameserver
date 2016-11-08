package server.api.dao;

/**
 * Created by Ольга on 08.11.2016.
 */
import org.junit.Test;
import server.dao.TokensDAO;
import server.dao.UsersDAO;
import server.servlets.auth.AuthenticationServlet;
import server.servlets.users_data.Token;
import server.servlets.users_data.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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


public class TokensDaoTest {
    private UsersDAO usersDao = new UsersDAO();
    private TokensDAO tokensDao = new TokensDAO();
    private User lolita = usersDao.findByLogin("lolly").get(0);
//    private User harry = usersDao.findByLogin("potter").get(0);

    @Test
    public void getAllTest() throws Exception {
        tokensDao.getAll();
        assertTrue(tokensDao.getAll().size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        int before = tokensDao.getAll().size();

        AuthenticationServlet.issueToken(lolita);
        //       AuthenticationServlet.issueToken(harry);
        assertEquals(before + 1, tokensDao.getAll().size());
    }



    @Test
    public void deleteTest() throws Exception {
        Token oldy = tokensDao.getAllWhere("frn_user_id = " + lolita.getId()).get(0);
        tokensDao.delete(oldy);
        assertEquals(tokensDao.getAllWhere("frn_user_id = " + lolita.getId()).size(),0);
    }



}
