package ehi2vsa.tjoonerapp.async;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 18/10/2016.
 */

public class DeleteImage extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/Media?mediaId=" + strings[0] + "&userToken=" + LoginToken.getInstance().getToken()));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();
            Log.d("Response Code: ", "" + responseCode);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuilder response = new StringBuilder();

            while ((input = reader.readLine()) != null) {
                response.append(input);
            }
            reader.close();
            Log.d("DeleteResponse", response.toString());
            return response.toString();
        }catch (IOException e){
            Log.d("Delete Image", e.getMessage());
        }
        return null;
    }
}
