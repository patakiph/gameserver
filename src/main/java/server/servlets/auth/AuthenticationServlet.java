package server.servlets.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.dao.LeaderboardDao;
import server.dao.TokensDAO;
import server.dao.UsersDAO;
import server.servlets.users_data.Leaderboard;
import server.servlets.users_data.Token;
import server.servlets.users_data.User;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ольга on 17.10.2016.
 */
@Path("/auth")
public class AuthenticationServlet {

    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
    private static UsersDAO usersDAO = new UsersDAO();
    private static TokensDAO tokensDAO = new TokensDAO();
    private static LeaderboardDao leaderboardDao = new LeaderboardDao();

    // curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" -H "Host: {IP}:8080" -d "login={}&password={}" "{IP}:8080/auth/register"
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (usersDAO.findByLogin(user).size() > 0) {
            log.warn("User {} already exists", user);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        if (usersDAO.findByLogin(user) != null) {
            System.out.println("User " + user + " already exists");
        }
        Token tmp = new Token();
        User usr = new User(user, password);

        tmp.setUser(usr);
        usr.setToken(tmp);
        usersDAO.insert(usr);
        leaderboardDao.createTable();
        Leaderboard ldb = new Leaderboard();
        ldb.setScore(0);
        int user_id = usersDAO.findByLogin(user).get(0).getId();
        ldb.setLogin(user);
        ldb.setUser(user_id);
        leaderboardDao.insert(ldb);
//        System.out.println(leaderboardDao.getAll());
//        System.out.println(usersDAO.getAll());
//        System.out.println(tokensDAO.getAll());
        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    static {
        User admin = new User("admin", "admin");
        Token adminToken = new Token(1L);
        admin.setToken(adminToken);
        adminToken.setUser(admin);
        if (usersDAO.findByLogin("admin").size() == 0) {
            usersDAO.insert(admin);
            tokensDAO.insert(adminToken);
            log.info("New user '{}' registered", "admin");
                }


        }

        // curl -X POST
        //      -H "Content-Type: application/x-www-form-urlencoded"
        //      -H "Host: localhost:8080"
        //      -d "login=admin&password=admin"
        // "http://localhost:8080/auth/login"
        @POST
        @Path("login")
        @Consumes("application/x-www-form-urlencoded")
        @Produces("text/plain")
        public Response authenticateUser (@FormParam("user") String user,
                @FormParam("password") String password){

            if (user == null || password == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            try {
                // Authenticate the user using the credentials provided
                if (!authenticate(user, password)) {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }

                User usr = usersDAO.findByLogin(user).get(0);
                // Issue a token for the user
                long token = issueToken(usr);
                log.info("User '{}' logged in", user);

                // Return the token on the response
                return Response.ok(Long.toString(token)).build();

            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        }

    private boolean authenticate(String user, String password) throws Exception {
        String pwd = null;
        if (usersDAO.findByLogin(user).size() > 0) {
            pwd = usersDAO.findByLogin(user).get(0).getPassword();
        }
        return password.equals(pwd);
    }

    public static Long issueToken(User user) {
        Long token = null;
        int usr_id = user.getId();
        if (tokensDAO.getAllWhere("frn_user_id = " + usr_id).size() > 0)
            token = tokensDAO.getAllWhere("frn_user_id = " + usr_id).get(0).getToken();
        if (token != null) {
            return token;
        }

        token = ThreadLocalRandom.current().nextLong();
        Token tkn = new Token(token);
        tkn.setUser(user);
        tokensDAO.insert(tkn);
        return token;
    }

    static void validateToken(String rawToken) throws Exception {
        Long token = Long.parseLong(rawToken);
        if (tokensDAO.getAllWhere("token=" + token).size() <= 0)
            throw new Exception("Token validation exception");

        log.info("Correct token from '{}'", tokensDAO.getAllWhere("token=" + token).get(0).getUser().getLogin());
    }

    @POST
    @Path("logout")
    @Authorized
    @Produces("text/plain")
    public Response logout(ContainerRequestContext requestContext) { //вот тут как фильтр применить

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
        Token userToken = tokensDAO.getAllWhere("token=" + token).get(0);
        String userLogin = userToken.getUser().getLogin();
        tokensDAO.delete(userToken);
        log.info("User '{}' logged out", userLogin);
        return Response.ok("User " + userLogin + " logged out.").build();

    }

}