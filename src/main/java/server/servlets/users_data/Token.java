package server.servlets.users_data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ольга on 23.10.2016.
 */
@Entity
@Table(name = "tokens")
public class Token implements Serializable {

    @OneToOne
    @JoinColumn(name = "frn_user_id")
    private User user;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Long token;

    private Date date = new Date();

    public Token() {
    }

    public Token(Long token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getToken() {
        return this.token;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    ;

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(Long token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return this.token.equals(token.getToken());
    }

    @Override
    public int hashCode() {
        return this.token.hashCode();
    }

//    @Override
//    public String toString() {
//        return this.token.toString();
//    }

    public String toString() {
        String str = "id = " + id +
                "; token = " + token +
                "; user = " + user +
                "; date = " + date;
        return str;
    }
}
