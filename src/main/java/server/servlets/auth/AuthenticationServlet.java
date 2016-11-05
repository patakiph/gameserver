package server.servlets.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;
import server.dao.UsersDAO;
import server.servlets.users_data.Token;
import server.servlets.users_data.TokenStorage;
import server.servlets.users_data.User;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ольга on 17.10.2016.
 */
@Path("/auth")
public class AuthenticationServlet {

    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
    private static ConcurrentHashMap<String, User> users; //login - key
    private static TokenStorage tokenStorage;
    private static UsersDAO usersDAO = new UsersDAO();

    // curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" -H "Host: {IP}:8080" -d "login={}&password={}" "{IP}:8080/auth/register"
    @POST
    @Path("register") //почему не /register
    @Consumes("application/x-www-form-urlencoded") //где эта форма?
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                             @FormParam("password") String password) {
        System.out.println(usersDAO.getAll());

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
        usersDAO.insert(new User(user,password));

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    static {

        users = new ConcurrentHashMap<>();
        if (usersDAO.findByLogin("admin").size() == 0) {
                        users.put("admin", new User("admin", "admin"));
                        usersDAO.insert(new User("admin", "admin"));
                        log.info("New user '{}' registered", "admin");
                    }
        tokenStorage = new TokenStorage();
        tokenStorage.add("admin", new Token(1L));

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
    public Response authenticateUser(@FormParam("user") String user,
                                     @FormParam("password") String password) {
        System.out.println(usersDAO.getAll());
        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            // Authenticate the user using the credentials provided
            if (!authenticate(user, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            // Issue a token for the user
            long token = issueToken(user);
            log.info("User '{}' logged in", user);

            // Return the token on the response
            return Response.ok(Long.toString(token)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

//    private boolean authenticate(String user, String password) throws Exception {
//        return password.equals(users.get(user).getPassword());
//    }
    private boolean authenticate(String user, String password) throws Exception {
        String pwd = null;
        if (usersDAO.findByLogin(user).size() > 0){
            pwd = usersDAO.findByLogin(user).get(0).getPassword();
        }
        return password.equals(pwd);
    }
//    private Long issueToken(String user) {
//        Long token = null;
//        if (tokenStorage.getToken(user) != null)
//            token = tokenStorage.getToken(user).getToken();
//        if (token != null) {
//            return token;
//        }
//
//        token = ThreadLocalRandom.current().nextLong();
//        tokenStorage.add(user, new Token(token));
////        tokensReversed.put(token, user);
//        return token;
//    }
    private Long issueToken(String user) {
        Long token = null;
        if (usersDAO.findByLogin(user).get(0)!=null)
       if (usersDAO.findByLogin(user).get(0).getToken()!=null){
           token = usersDAO.findByLogin(user).get(0).getToken().getToken();
       }
        if (token != null) {
            return token;
        }

        token = ThreadLocalRandom.current().nextLong();
        usersDAO.updateToken(user,new Token(token));
        return token;
    }
    static void validateToken(String rawToken) throws Exception {
        Long token = Long.parseLong(rawToken);
        System.out.println(usersDAO.getAll());
        System.out.println(usersDAO.getAllWhere("token LIKE " + "\'"+ token + "\'"));
        if (usersDAO.getAllWhere("token LIKE " + "\'"+ token + "\'").size() <= 0)
            throw new Exception("Token validation exception");

        log.info("Correct token from '{}'", usersDAO
                .getAllWhere("token=" + "\'"+ token + "\'")
                .get(0).getLogin());
    }

    @POST
    @Path("logout")
    @Authorized
    @Produces("text/plain")
    public Response logout(ContainerRequestContext requestContext) { //вот тут как фильтр применить

        System.out.println(usersDAO.getAll());
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
       // String user = tokenStorage.getLogin(new Token((Long.parseLong(token))));
        System.out.println(usersDAO.getAll());
        User user = null;
         if (usersDAO.getAllWhere("token LIKE " + "\'"+ Long.parseLong(token) + "\'").size() > 0)
        {user = usersDAO.getAllWhere("token LIKE " + "\'"+ Long.parseLong(token)+ "\'").get(0); }
  //      tokenStorage.remove(new Token((Long.parseLong(token))));
        usersDAO.updateToken(user.getLogin(),null);
        log.info("User '{}' logged out", user);
        return Response.ok("User " + user + " logged out.").build();

    }

    public static TokenStorage getTokens() {
        return tokenStorage;
    }

    public static ConcurrentHashMap<String, User> getUsers() {
        return users;
    }
}