package ehi2vsa.tjoonerapp.singletons;

/**
 * Created by joost on 28/09/2016.
 */
public class UserData {
    private static UserData instance;
    private UserData userdata;


    public static UserData getInstance(){
        if (instance == null){
            instance = new UserData();
        }
        return instance;
    }

    private UserData(){

    }

    public void logout(){
        instance = null;
    }
}
