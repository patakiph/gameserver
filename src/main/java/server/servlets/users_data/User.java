package server.servlets.users_data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ольга on 23.10.2016.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "registrationDate")
    private Date registrationDate = new Date(); // ! не предоставлять api для изменения даты регистрации !
    @Column(name = "name")
    private String name = "no_name";
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(nullable = true, name = "token")
    private Token token;


    public User() {
    }

//    public User(String login, String email, String password, Token token) {
//        this.login = login;
//        this.email = email;
//        this.password = password;
//        this.token = token;
//    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.name = login;
    }

    public int getId() {
        return id;
    }

    ;

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return this.registrationDate;
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

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
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
    public int hashCode() {
        return this.login.hashCode();
    }

    @Override
    public boolean equals(Object u) {
        if (u == null) return false;
        if (u == this) return true;
        if (!(u instanceof User)) return false;
        User user = (User) u;
        return this.login.equals(user.getLogin());
    }

    @Override
    public String toString() {
        String str = "id = " + id +
                "; login = " + login +
                "; password = " + password +
                "; token = " + token +
                "; email = " + email +
                "; name = " + name +
                "; reg date = " + registrationDate;
        return str;
    }
}

