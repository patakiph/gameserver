package server.servlets.users_data;


/**
 * Created by Ольга on 23.10.2016.
 */
public class User {
    private String login;
    private String email;
    private String password;
    private Token token;

    public User(String login, String email, String password, Token token) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.token = token;
    }
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public int hashCode(){
        return this.login.hashCode();
    }
    @Override
    public boolean equals(Object u){
        if (u == null) return false;
        if (u == this) return true;
        if (!(u instanceof User))return false;
        User user = (User) u;
        return this.login.equals(user.getLogin());
    }
}

