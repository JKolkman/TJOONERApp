package ehi2vsa.tjoonerapp.singletons;

import android.os.AsyncTask;

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


    private class getUserData extends AsyncTask<String, String, String[]>{
        @Override
        protected String[] doInBackground(String... strings) {






            return new String[0];
        }
    }
}
