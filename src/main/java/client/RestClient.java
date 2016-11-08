package client;

import java.util.Collection;

/**
 * Created by vladfedorenko on 08.11.16.
 */

public interface RestClient {
    Long register(String user, String password);
    String login(String user, String password);
    Long logout(String token);
    String getAll(String token);
    Long changeName(String newLogin, String token);
    //String getLb(String token);
    String addScore(String token);
}
