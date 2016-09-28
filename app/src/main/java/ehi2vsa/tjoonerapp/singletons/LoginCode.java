package ehi2vsa.tjoonerapp.singletons;

/**
 * Created by joost on 23/09/2016.
 */
public class LoginCode {
    private String code;
    public static LoginCode instance;
    private LoginCode loginCode;

    public static LoginCode getInstance(){
        if (instance == null){
            instance = new LoginCode();
        }
        return instance;
    }

    private LoginCode(){
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
