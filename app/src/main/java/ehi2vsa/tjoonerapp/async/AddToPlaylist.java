package ehi2vsa.tjoonerapp.async;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ehi2vsa.tjoonerapp.objects.Media;
import ehi2vsa.tjoonerapp.objects.Playlist;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 26/10/2016.
 */

public class AddToPlaylist extends AsyncTask<String, String, String> {
    private Playlist playlist;
    private Media mediaToAdd;

    public AddToPlaylist(Playlist playlist, Media mediaToAdd) {
        this.playlist = playlist;
        this.mediaToAdd = mediaToAdd;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/Playlist?userToken=" + LoginToken.getInstance().getToken());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            String mediaJSON = "";
            for (int i = 0; i < playlist.getMedia().size(); i++) {
                mediaJSON += playlist.getMedia().get(i).getJSON();
                mediaJSON += ",";
            }
            mediaJSON += mediaToAdd.getJSON();

            String req = "{\n" +
                    "\"Id\": \"" + playlist.getId() + "\",\n" +
                    "\"Description\": \"" + playlist.getTitle() + "\",\n" +
                    "\"Media\": [" + mediaJSON + "]}";
            urlConnection.setRequestProperty("Content-length", req.getBytes().length + "");
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            OutputStream out = urlConnection.getOutputStream();
            out.write(req.getBytes("UTF-8"));
            out.flush();
            out.close();

            int statusCode = urlConnection.getResponseCode();

            Log.d("failed", "doInBackground(): connection failed: statusCode: " + statusCode);
            String response = urlConnection.getResponseMessage();
            Log.d("ResponseConnection", response);

            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();

        } catch (IOException e) {
            Log.d("AddToPlaylist", e.getMessage());
        }
        return "Error";
    }
}
