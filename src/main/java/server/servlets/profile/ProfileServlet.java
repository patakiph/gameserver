package server.servlets.profile;

/**
 * Created by Ольга on 23.10.2016.
 */
import javax.ws.rs.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import server.dao.LeaderboardDao;
import server.dao.TokensDAO;
import server.dao.UsersDAO;
import server.servlets.auth.Authorized;
import server.servlets.users_data.Leaderboard;
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
    private static UsersDAO usersDAO = new UsersDAO();
    private static TokensDAO tokensDAO = new TokensDAO();
    private static LeaderboardDao leaderboardDAO = new LeaderboardDao();
    @POST
    @Path("name")
    @Authorized
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response changeName( ContainerRequestContext requestContext, @FormParam("name") String name,
                                @FormParam("email") String email,
                                @FormParam("password") String password,
                                @FormParam("login") String login) {

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

        User user = tokensDAO.getAllWhere("token=" + token).get(0).getUser();
        String oldName = user.getName();
        if (name != null)
            user.setName(name);
        if (email != null)
            user.setEmail(email);
        if (password != null)
            user.setPassword(password);
        if (login != null){
            if (usersDAO.findByLogin(login).size()==0)
            user.setLogin(login);
        }
        usersDAO.update(user);
        log.info("User '{}' changed his name to '{}'", oldName, name);
        return Response.ok("User " + oldName + " changed his name to " + name).build();

    }

    @POST
    @Path("leaderboard")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response changeScore( ContainerRequestContext requestContext,
                                @FormParam("login") String login, @FormParam("score") int score) {

        if (login == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            // Authenticate the user using the credentials provided
            if (usersDAO.findByLogin(login).size() <= 0) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            User user = usersDAO.findByLogin(login).get(0);
            if (LeaderboardDao.getAllWhere("user_id = " + user.getId()).size() <= 0) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        Leaderboard ldb = LeaderboardDao.getAllWhere("user_id = " + user.getId()).get(0);
        int newScore = score + ldb.getScore();
        ldb.setScore(newScore);
        leaderboardDAO.update(ldb);
        log.info("User '{}' changed his score", user.getLogin());
        return Response.ok("User " + user.getLogin() + " changed his score").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

}
