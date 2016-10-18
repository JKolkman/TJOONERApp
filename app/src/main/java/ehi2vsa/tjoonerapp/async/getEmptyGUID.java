package ehi2vsa.tjoonerapp.async;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joost on 18/10/2016.
 */

public class getEmptyGUID extends AsyncTask<String, String, String[]> {
    @Override
    protected String[] doInBackground(String... strings) {
        String ids[] = new String[3];
        for (int i = 0; i < 3; i++) {
            try {
                URL url = new URL(("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/Guid?guidtype=new"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
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
                ids[i] = response.toString();
            } catch (IOException e) {
                Log.d("Getting GUID", e.getMessage());
            }
        }
        return ids;
    }
}
