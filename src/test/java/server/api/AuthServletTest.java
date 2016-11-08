package server.api;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request.Builder;

import com.google.gson.reflect.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.List;

import org.junit.Before;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import client.*;
import server.dao.LeaderboardDao;
import server.dao.TokensDAO;
import server.dao.UsersDAO;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthServletTest {
    private Integer i = 1;
    private String SERVICE_URL = "http://localhost:8080";
    private String NEW_USER_NAME = "Slon";
    private String NEW_NEW_USER_NAME = "Elephant";
    private String NEW_USER_PASS = "SuperPass";
    private String TEST_TOKEN = "";
    private boolean setUpDone = false;
    private RestClient client = new RestClientImp();

    public class parseUser {
        private String name;
        private String login;

        public parseUser(String name, String login) {
            this.name = name;
            this.login = login;
        }

        public String getName() {
            return this.name;
        }
    }

    public class parsePosition {
        private String user_id;
        private String login;
        private Long score;

        public parsePosition(String user_id, String login, Long score) {
            this.user_id = user_id;
            this.login = login;
            this.score = score;
        }

        public String getLogin() {
            return this.login;
        }

        public Long getScore() {
            return this.score;
        }
    }
//@Test
//    public void dropTables(){
//        if (setUpDone) {
//            return;
//        }
//        TokensDAO tokensDAO = new TokensDAO();
//        UsersDAO usersDAO = new UsersDAO();
//        LeaderboardDao leaderboardDao = new LeaderboardDao();
//        leaderboardDao.dropTable();
//        tokensDAO.dropTable();
//        usersDAO.dropTable();
//        setUpDone = true;
//    }

    @Test
    public void test01_testRegistrationWithExistingLogin() throws Exception {
        String user = "admin";
        String pass = "admin";
        assertEquals(client.register(user, pass), Long.valueOf(-1));
    }

    @Test
    public void test02_testRegistrationOfNewUser() throws Exception {
        assertEquals(client.register(NEW_USER_NAME, NEW_USER_PASS), Long.valueOf(200));
    }

    @Test
    public void test04_testAuthorizationOfNewUser() throws Exception {
        String token = client.login(NEW_USER_NAME, NEW_USER_PASS);
        assertNotEquals("", token);
    }

    @Test
    public void test05_testAuthorizationAdmin() throws Exception {
        String token = client.login("admin", "admin");
        System.out.println(token);
        assertEquals("1", token);
    }

    @Test
    public void test08_getAllUsers() throws Exception {
        String body = client.getAll("1");

        Type listType = new TypeToken<List<parseUser>>() {
        }.getType();
        Map jsonJavaRootObject = new Gson().fromJson(body, Map.class);
        List<parseUser> users = new Gson().fromJson(jsonJavaRootObject.get("users").toString(), listType);
        List<String> nameList = users.stream().map(parseUser::getName).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
        System.out.println(nameList);
        List<String> reallyExisting = Arrays.asList("admin", NEW_USER_NAME);
        reallyExisting.sort(String.CASE_INSENSITIVE_ORDER);

        assertArrayEquals(nameList.toArray(), reallyExisting.toArray());
    }

    @Test
    public void test09_changeNameTest() throws Exception {
        String token = client.login(NEW_USER_NAME, NEW_USER_PASS);
        Long code = client.changeName(NEW_NEW_USER_NAME, token);
        assertEquals(Long.valueOf(200), code);

        String body = client.getAll("1");

        Type listType = new TypeToken<List<parseUser>>() {
        }.getType();
        Map jsonJavaRootObject = new Gson().fromJson(body, Map.class);
        List<parseUser> users = new Gson().fromJson(jsonJavaRootObject.get("users").toString(), listType);
        List<String> nameList = users.stream()
                .map(parseUser::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
        System.out.println(nameList);

        List<String> reallyExisting = Arrays.asList("admin", NEW_NEW_USER_NAME);
        reallyExisting.sort(String.CASE_INSENSITIVE_ORDER);

        assertArrayEquals(nameList.toArray(), reallyExisting.toArray());
    }

    @Test
    public void test10_testLeaderoard() throws Exception {
        client.register(NEW_USER_NAME + "_2", NEW_USER_PASS);
        client.register(NEW_USER_NAME + "_3", NEW_USER_PASS);
        client.register(NEW_USER_NAME + "_4", NEW_USER_PASS);
        client.register(NEW_USER_NAME + "_5", NEW_USER_PASS);
        client.addScore(NEW_USER_NAME + "_2", 4);
        client.addScore(NEW_USER_NAME + "_3", 14);
        client.addScore(NEW_USER_NAME + "_4", 7);
        client.addScore(NEW_USER_NAME + "_5", 10);
        String body = client.getLb(Long.valueOf(3));
        Type listType = new TypeToken<List<parsePosition>>() {
        }.getType();
        Map jsonJavaRootObject = new Gson().fromJson(body, Map.class);
        List<parsePosition> users = new Gson().fromJson(jsonJavaRootObject.get("leaderboard").toString(), listType);
        List<String> nameList = users.stream().map(parsePosition::getLogin).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
        List<Long> scoreList = users.stream().map(parsePosition::getScore).sorted().collect(Collectors.toList());
        System.out.println(nameList);
        System.out.println(scoreList); //         List<String> realOrder = Arrays.asList(NEW_USER_NAME + "_3", NEW_USER_NAME + "_5", NEW_USER_NAME + "_4");         List<Long> realScores = Arrays.asList(14L, 10L, 7L);          assertArrayEquals(realOrder.toArray(), nameList.toArray());         assertArrayEquals(realScores.toArray(), scoreList.toArray());
    }

    }
