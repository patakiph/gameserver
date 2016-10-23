package server.servlets.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.layout.StringBuilderEncoder;
import org.json.JSONObject;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static server.servlets.auth.AuthenticationServlet.getTokens;

/**
 * Created by Ольга on 23.10.2016.
 */

@Path("/data")
public class DataServlet{

    private static final Logger log = LogManager.getLogger(DataServlet.class);

    @GET
    @Path("users")
    @Produces("application/json")
    public Response changeName() { //вот тут как фильтр применить
        StringBuilder str = new StringBuilder("{\"users\" : [");
        for (String name: getTokens().getLoginToken().keySet()){

            String key =name.toString();
            String value = getTokens().getLoginToken().get(name).toString();
            str.append("{"+key+":"+value+"}" + ", ");
        }
        str.deleteCharAt(str.length()-1);
        str.deleteCharAt(str.length()-1);
        str.append(" ] }");
        String s = new String(str);
        log.info(s);
        Gson gson = new Gson();
    //    gson.toJsonTree(getTokens().getLoginTokenLong());
        JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
        return Response.ok(gson.toJson(obj)).build();
    }
}