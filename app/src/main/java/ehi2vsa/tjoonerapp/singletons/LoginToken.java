package ehi2vsa.tjoonerapp.singletons;

/**
 * Created by joost on 23/09/2016.
 */
public class LoginToken {
    private String token;
    private static LoginToken instance;
    private LoginToken loginCode;

    public static LoginToken getInstance(){
        if (instance == null){
            instance = new LoginToken();
        }
        return instance;
    }

    private LoginToken(){
    }

    public void setCode(String token){
        this.token = token.replace("\"", "");

    }

    public String getToken(){
        return token;
    }

    public void logout(){
        token = null;
    }
}
