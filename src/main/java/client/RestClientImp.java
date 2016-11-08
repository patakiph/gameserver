package client;

import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by vladfedorenko on 08.11.16.
 */
public class RestClientImp implements RestClient {
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private OkHttpClient client = new OkHttpClient();


    public Long register(String user, String password) {
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setRetryOnConnectionFailure(true);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("user=%s&password=%s", user, password)
        );

        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            return Long.valueOf(response.code());
        } catch (IOException e) {
            return -1L;
        }
    }

    public String login(String user, String pass) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.
                create(mediaType, String.format("user=%s&password=%s", user, pass));
        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = (new Request.Builder())
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String token = response.body().string();
            return token;
        } catch (IOException e) {
            return "";
        }
    }

    public Long logout(String token) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.
                create(mediaType, "");
        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = (new Request.Builder())
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", String.format("Bearer: {}", token))
                .build();
        try {
            Response response = client.newCall(request).execute();
            long code = Long.valueOf(response.code());
            return code;
        } catch (IOException e) {
            return -1L;
        }
    }

    public String getAll(String token) {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(30, TimeUnit.SECONDS);
        String requestUrl = SERVICE_URL + "/data/users";
        Request request = (new Request.Builder())
                .url(requestUrl)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return "";
        }
    }

    public Long changeName(String newLogin, String token) {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(30, TimeUnit.SECONDS);
        String requestUrl = SERVICE_URL + "/profile/name";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.
                create(mediaType, String.format("name=%s", newLogin));
        Request request = (new Request.Builder())
                .url(requestUrl)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return Long.valueOf(response.code());
        } catch (Exception e) {
            return -1L;
        }
    }

    public String getLb(String token) {
        return "";
    }

    public String addScore(String token) {
        return "";
    }

}
