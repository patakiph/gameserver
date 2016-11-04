package server.servlets.users_data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ольга on 23.10.2016.
 */
@Entity
@Table(name="token")
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Long token;
    private Date date = new Date();

    public Token(Long token){
        this.token = token;
    }

    public Long getToken(){
        return this.token;
    }
    public Date getDate(){
        return this.date;
    }
    public void setToken(Long token){
        this.token = token;
    }
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Token))return false;
        Token token = (Token) o;
        return this.token.equals(token.getToken());
    }
    @Override
    public int hashCode(){
        return this.token.hashCode();
    }
    @Override
    public String toString(){
        return this.token.toString();
    }
}
