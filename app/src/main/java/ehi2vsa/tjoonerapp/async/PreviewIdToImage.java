package ehi2vsa.tjoonerapp.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 29/09/2016.
 */
public class PreviewIdToImage extends AsyncTask<String, String, Bitmap> {
    private String previewId;

    public PreviewIdToImage(String previewId) {
        this.previewId = previewId;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            String token = LoginToken.getInstance().getToken();

            URL url = new URL(("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/TjoonerResource?resourceId=" + previewId + "&userToken=" + token));
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
            System.out.println(response);
            byte[] decodedString = Base64.decode(response.toString(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        } catch (IOException e) {
            Log.d("PreviewToImage", e.getMessage());
        }
        return null;
    }
}
