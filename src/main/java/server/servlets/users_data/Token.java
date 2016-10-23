package server.servlets.users_data;

/**
 * Created by Ольга on 23.10.2016.
 */
public class Token {
    private Long token;

    public Token(Long token){
        this.token = token;
    }
    public Long getToken(){
        return this.token;
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
