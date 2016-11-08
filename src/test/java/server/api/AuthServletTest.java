package server.servlets.auth;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request.Builder;
import com.sun.jdi.connect.Connector;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.List;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthServletTest {
    private Integer i = 1;
    private String SERVICE_URL = "http://localhost:8080";
    private String NEW_USER_NAME = "Slon";
    private String NEW_NEW_USER_NAME = "Elephant";
    private String NEW_USER_PASS = "SuperPass";
    private String TEST_TOKEN = "";

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

    private void setTestToken(String token) {
        this.TEST_TOKEN = token;
    }

    private String getTestToken() {
        return this.TEST_TOKEN;
    }

    @Test
    public void test01_testRegistrationWithExistingLogin() throws Exception {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setRetryOnConnectionFailure(true);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("user=%s&password=%s", "admin", "admin")
        );

        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = (new Builder())
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 406);
    }

    @Test
    public void test02_testRegistrationWithoutLogin() throws Exception {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("password=%s", new Object[]{"admin"}));

        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = (new Builder())
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded").build();
        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 400);
    }

    @Test
    public void test03_testRegistrationWithoutPassword() throws Exception {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.
                create(mediaType, String.format("user=%s", "admin"));
        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = (new Builder()).
                url(requestUrl).
                post(body).
                addHeader("content-type", "application/x-www-form-urlencoded").
                build();
        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 400);
    }

    @Test
    public void test04_testRegistrationOfNewUser() throws Exception {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.
                create(mediaType, String.format("user=%s&password=%s", NEW_USER_NAME, NEW_USER_PASS));
        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = (new Builder())
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 200);
    }

    @Test
    public void test05_testAuthorizationOfNewUser() throws Exception {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.
                create(mediaType, String.format("user=%s&password=%s", NEW_USER_NAME, NEW_USER_PASS));
        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = (new Builder())
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String token = response.body().string();
        System.out.println(token);
        this.setTestToken(token);
        assertEquals(response.code(), 200);
        System.out.println(this.getTestToken());
    }


    @Test
    public void test06_testAuthorizationAdmin() throws Exception {

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.
                create(mediaType, String.format("user=%s&password=%s", "admin", "admin"));
        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = (new Builder()).
                url(requestUrl).post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(response.body().string(), "1");
    }

    @Test
    public void test07_testAuthorizationWithIncorrectPass() throws Exception {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody
                .create(mediaType, String.format("user=%s&password=%s", "admin", "ne_admin("));
        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = (new Builder()).
                url(requestUrl).
                post(body).
                addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 401);
    }

    @Test
    public void test08_getAllUsers() throws Exception {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(30, TimeUnit.SECONDS);
        String requestUrl = SERVICE_URL + "/data/users";
        Request request = (new Builder())
                .url(requestUrl)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer: 1")
                .get()
                .build();
        Response response = client.newCall(request).execute();

        Type listType = new TypeToken<List<parseUser>>(){}.getType();
        Map jsonJavaRootObject = new Gson().fromJson(response.body().string(), Map.class);
        List<parseUser> users = new Gson().fromJson(jsonJavaRootObject.get("users").toString(), listType);
        List<String> nameList = users.stream().map(parseUser::getName).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
        System.out.println(nameList);

        List<String> reallyExisting = Arrays.asList("admin", NEW_USER_NAME);
        reallyExisting.sort(String.CASE_INSENSITIVE_ORDER);

        assertEquals(response.code(), 200);
        assertArrayEquals(nameList.toArray(), reallyExisting.toArray());
    }

    @Test
    public void test09_changeNameTest() throws Exception {
        OkHttpClient client = new OkHttpClient();
        String requestUrl = SERVICE_URL + "/profile/name";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("login=%s", NEW_NEW_USER_NAME)
        );
        System.out.println(this.getTestToken());
        System.out.println(getTestToken());
        Request request = (new Builder())
                .url(requestUrl)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer: " + this.getTestToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 200);


        String requestUrl_2 = SERVICE_URL + "/data/users";
        Request request_2 = (new Builder())
                .url(requestUrl_2)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer: 1")
                .get()
                .build();
        Response response_2 = client.newCall(request_2).execute();

        Type listType = new TypeToken<List<parseUser>>(){}.getType();
        Map jsonJavaRootObject = new Gson().fromJson(response_2.body().string(), Map.class);
        List<parseUser> users = new Gson().fromJson(jsonJavaRootObject.get("users").toString(), listType);
        List<String> nameList = users.stream().map(parseUser::getName).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
        // System.out.println(nameList);

        List<String> reallyExisting = Arrays.asList("admin", NEW_NEW_USER_NAME);
        reallyExisting.sort(String.CASE_INSENSITIVE_ORDER);
        assertArrayEquals(nameList.toArray(), reallyExisting.toArray());
    }


//    @Test
//    public void changeNameTest() throws Exception {
//        OkHttpClient client = new OkHttpClient();
//        String requestUrl = SERVICE_URL + "/profile/name";
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody
//                .create(mediaType, String.format("user=%s&password=%s", "admin", "ne_admin("));
//        Request request = (new Builder())
//                .url(requestUrl)
//                .addHeader("content-type", "application/x-www-form-urlencoded")
//                .addHeader("Authorization", "Bearer: " + getTestToken())
//                .get()
//                .build();
//        Response response = client.newCall(request).execute();
//        assertEquals(response.code(), 200);
//        // Think about comparing two lists
//    }
//
//    @Test
//    public void changeEmailTest() throws Exception {
//
//
//    }
//

//
//    @Test
//    public void testLeaderboard() throws Exception {
//
//    }
//
//


}
