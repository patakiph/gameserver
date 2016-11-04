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
    private String login;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "date")
    private Date registrationDate; // ! не предоставлять api для изменения даты регистрации !
    @Column(name = "name")
    private String name = "no_name";
    @Column(nullable = true, name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(nullable = true, name = "token")
    private Token token;

    public User() {

    }

    public User(String login, String email, String password, Token token) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.token = token;
        registrationDate = new Date();
    }
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.name = login;
        registrationDate = new Date();
    }
    public String getLogin() {
        return login;
    }
    public int getId() {
        return id;
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
    public Date getRegistrationDate(){
        return registrationDate;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login=" + login +
                ", name='" + name + '\'' +
                ", registration date=" + registrationDate +
                ", email=" + email +
                ", token=" + token +
                '}';
    }
}

