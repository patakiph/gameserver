package server.servlets.users_data;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ольга on 23.10.2016.
 */
public class TokenStorage {
    private ConcurrentHashMap<String, Token> loginToken;
    private ConcurrentHashMap<String, String> loginTokenLong;
    private ConcurrentHashMap<Token, String> tokenLogin;

    public TokenStorage(){
        loginToken = new ConcurrentHashMap<>();
        loginTokenLong = new ConcurrentHashMap<>();
        tokenLogin = new ConcurrentHashMap<>();
    }

    public Token getToken(String login){

        return loginToken.get(login);
    }
    public ConcurrentHashMap<Token, String> getTokenLogin(){
        return tokenLogin;
    }
    public ConcurrentHashMap<String, Token> getLoginToken(){
        return loginToken;
    }
    public ConcurrentHashMap<String, String> getLoginTokenLong(){
        return loginTokenLong;
    }
    public String getLogin(Token token){
        return tokenLogin.get(token);
    }
    public void add(String login, Token token){
        loginToken.put(login,token);
        tokenLogin.put(token,login);
        loginTokenLong.put(login,token.toString());
    }
    public void remove(Token token){
        String login = this.getLogin(token);
        tokenLogin.remove(token);
        loginToken.remove(login);
    }
    public boolean containsToken(Token token){
        if (this.tokenLogin.containsKey(token))
            return true;
        else return false;
    }
}
