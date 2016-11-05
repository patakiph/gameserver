package server.servlets.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.layout.StringBuilderEncoder;
import org.json.JSONObject;
import server.dao.TokensDAO;
import server.dao.UsersDAO;
import server.servlets.users_data.User;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ольга on 23.10.2016.
 */

@Path("/data")
public class DataServlet {

    private static final Logger log = LogManager.getLogger(DataServlet.class);
    private static TokensDAO tokensDAO = new TokensDAO();
    private static UsersDAO usersDAO = new UsersDAO();

    @GET
    @Path("users")
    @Produces("application/json")
    public Response getData() { //вот тут как фильтр применить
        List<User> users = usersDAO.getAll();
        StringBuilder str = new StringBuilder("{\"users\" : [");
        for (int i = 0; i < users.size(); i++) {

            String key = users.get(i).getLogin();
            String user_name = users.get(i).getName();
            str.append("{" + "name=" + user_name + ", "
                    + "login=" + key + "}" + ", ");

        }
        str.deleteCharAt(str.length() - 1);
        str.deleteCharAt(str.length() - 1);
        str.append(" ] }");
        String s = new String(str);
        log.info(s);
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
        return Response.ok(gson.toJson(obj)).build();
    }
}