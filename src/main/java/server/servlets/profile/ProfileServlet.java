package server.servlets.profile;

/**
 * Created by Ольга on 23.10.2016.
 */
import javax.ws.rs.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import server.servlets.auth.Authorized;
import server.servlets.users_data.Token;
import server.servlets.users_data.User;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static server.servlets.auth.AuthenticationServlet.*;


/**
 * Created by Ольга on 17.10.2016.
 */
@Path("/profile")
public class ProfileServlet {
    private static final Logger log = LogManager.getLogger(ProfileServlet.class);
    @POST
    @Path("name")
    @Authorized
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response changeName(@FormParam("name") String name, ContainerRequestContext requestContext) { //вот тут как фильтр применить

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (getUsers().containsKey(name)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
            String oldName = getTokens().getLogin(new Token(Long.parseLong(token)));
            getTokens().remove(new Token(Long.parseLong(token)));
            getTokens().add(name, new Token(Long.parseLong(token)));
            String password = getUsers().get(oldName).getPassword();
            getUsers().put(name,new User(name,password));
            getUsers().remove(oldName);
        log.info("User '{}' changed his name to '{}'", oldName, name);
        return Response.ok("User " + oldName + " changed his name to " + name).build();

    }
}
