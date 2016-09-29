package ehi2vsa.tjoonerapp.async;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 29/09/2016.
 */
public class GetPlaylist extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        try {
            String token = LoginToken.getInstance().getToken();

            URL url = new URL(("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/Playlists?categoryId=c6da9e11-f810-4751-8960-21fd70aa3a58&userToken=" + token));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuilder response = new StringBuilder();

            while ((input = reader.readLine()) != null) {
                response.append(input);
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            Log.d("EndOfMediaFragment", e.getMessage());
        }
        return null;
    }
}
