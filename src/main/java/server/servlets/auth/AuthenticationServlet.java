package server.servlets.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;
<<<<<<< HEAD
import server.dao.UsersDao;
||||||| merged common ancestors
=======
import server.dao.TokensDAO;
import server.dao.UsersDAO;
>>>>>>> new
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

<<<<<<< HEAD
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);

    private static ConcurrentHashMap<String, User> users; //login - key
    private static TokenStorage tokenStorage;
    private static UsersDao usersDao = new UsersDao();

||||||| merged common ancestors
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class); //что значит запись ClassName.class просто имя класса?
    //    private static ConcurrentHashMap<String, String> credentials;
//    private static ConcurrentHashMap<String, Long> tokens;
//    private static ConcurrentHashMap<Long, String> tokensReversed;
    //////////////////////////////////////////
    private static ConcurrentHashMap<String, User> users; //login - key
    private static TokenStorage tokenStorage;
    //////////////////////////////////////////
=======
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
    private static UsersDAO usersDAO = new UsersDAO();
    private static TokensDAO tokensDAO = new TokensDAO();
>>>>>>> new

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

<<<<<<< HEAD
        if (users.putIfAbsent(user, new User(user, password)) != null) {
||||||| merged common ancestors
//        if (credentials.putIfAbsent(user, password) != null) {
//            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
//        }
        ////////////////////////////////////////////
        if (users.putIfAbsent(user, new User(user, password)) != null) {
=======
        if (usersDAO.findByLogin(user).size() > 0) {
            log.warn("User {} already exists", user);
>>>>>>> new
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
<<<<<<< HEAD
        usersDao.insert(new User(user, password));
        System.out.println(usersDao.getAll());
||||||| merged common ancestors
        ///////////////////////////////////////////
=======
        if (usersDAO.findByLogin(user) != null) {
            System.out.println("User " + user + " already exists");
        }
        Token tmp = new Token();
        User usr = new User(user, password);
        tmp.setUser(usr);
        usr.setToken(tmp);
        usersDAO.insert(usr);
        System.out.println(usersDAO.getAll());
        System.out.println(tokensDAO.getAll());
>>>>>>> new
        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    static {
<<<<<<< HEAD
        users = new ConcurrentHashMap<>();
        users.put("admin", new User("admin", "admin"));
        usersDao.insert(new User("admin", "admin"));
        tokenStorage = new TokenStorage();
        tokenStorage.add("admin", new Token(1L));
||||||| merged common ancestors
//        credentials = new ConcurrentHashMap<>();
//        credentials.put("admin", "admin");
//        tokens = new ConcurrentHashMap<>();
//        tokens.put("admin", 1L);
//        tokensReversed = new ConcurrentHashMap<>();
//        tokensReversed.put(1L, "admin");
        //////////////////////
        users = new ConcurrentHashMap<>();
        users.put("admin", new User("admin", "admin"));
        tokenStorage = new TokenStorage();
        tokenStorage.add("admin", new Token(1L));
        /////////////////////
=======
        User admin = new User("admin", "admin");
        Token adminToken = new Token(1L);
        admin.setToken(adminToken);
        adminToken.setUser(admin);
        if (usersDAO.findByLogin("admin").size() == 0) {
            usersDAO.insert(admin);
            tokensDAO.insert(adminToken);
            log.info("New user '{}' registered", "admin");
        }


>>>>>>> new
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

    private Long issueToken(User user) {
        Long token = null;
<<<<<<< HEAD
        if (tokenStorage.getToken(user) != null)
            token = tokenStorage.getToken(user).getToken();
||||||| merged common ancestors
        if (tokenStorage.getToken(user)!=null)
        token = tokenStorage.getToken(user).getToken();
=======
        int usr_id = user.getId();
        if (tokensDAO.getAllWhere("frn_user_id = " + usr_id).size() > 0)
            token = tokensDAO.getAllWhere("frn_user_id = " + usr_id).get(0).getToken();
>>>>>>> new
        if (token != null) {
            return token;
        }

        token = ThreadLocalRandom.current().nextLong();
<<<<<<< HEAD
        tokenStorage.add(user, new Token(token));
||||||| merged common ancestors
        tokenStorage.add(user, new Token(token));
//        tokensReversed.put(token, user);
=======
        Token tkn = new Token(token);
        tkn.setUser(user);
        tokensDAO.insert(tkn);
>>>>>>> new
        return token;
    }

    static void validateToken(String rawToken) throws Exception {
        Long token = Long.parseLong(rawToken);
<<<<<<< HEAD
        if (!tokenStorage.containsToken(new Token(token)))
            throw new Exception("Token validation exception");
        log.info("Correct token from '{}'", tokenStorage.getLogin(new Token(token)));
||||||| merged common ancestors
       if (!tokenStorage.containsToken(new Token(token)))
        throw new Exception("Token validation exception");
//        if (!tokensReversed.containsKey(token)) {
//            throw new Exception("Token validation exception");
//        }
        log.info("Correct token from '{}'", tokenStorage.getLogin(new Token(token)));
=======
        if (tokensDAO.getAllWhere("token=" + token).size() <= 0)
            throw new Exception("Token validation exception");

        log.info("Correct token from '{}'", tokensDAO.getAllWhere("token=" + token).get(0).getUser().getLogin());
>>>>>>> new
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
<<<<<<< HEAD
        String user = tokenStorage.getLogin(new Token((Long.parseLong(token))));
        tokenStorage.remove(new Token((Long.parseLong(token))));
        log.info("User '{}' logged out", user);
        return Response.ok("User " + user + " logged out.").build();
||||||| merged common ancestors
        String user = tokenStorage.getLogin(new Token((Long.parseLong(token))));
            tokenStorage.remove(new Token((Long.parseLong(token))));
        log.info("User '{}' logged out", user);
        return Response.ok("User " + user + " logged out.").build();
=======
        Token userToken = tokensDAO.getAllWhere("token=" + token).get(0);
        String userLogin = userToken.getUser().getLogin();
        tokensDAO.delete(userToken);
        log.info("User '{}' logged out", userLogin);
        return Response.ok("User " + userLogin + " logged out.").build();
>>>>>>> new

    }

<<<<<<< HEAD
    public static TokenStorage getTokens() {
        return tokenStorage;
    }

    public static ConcurrentHashMap<String, User> getUsers() {
        return users;
    }
||||||| merged common ancestors
    //    public static ConcurrentHashMap<String, Long> getTokens() {
//        return tokens;
//    }
//
//    public static ConcurrentHashMap<String, String> getCredentials() {
//        return credentials;
//    }
//
//    public static ConcurrentHashMap<Long, String> getTokensReversed() {
//        return tokensReversed;
//    }
    public static TokenStorage getTokens() {
        return tokenStorage;
    }
    public static ConcurrentHashMap<String, User> getUsers() {
        return users;
    }
=======
>>>>>>> new
}