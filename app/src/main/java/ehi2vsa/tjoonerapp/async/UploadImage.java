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
import java.util.ArrayList;

import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 13/10/2016.
 */

public class UploadImage extends AsyncTask<String, String, String> {
    private ArrayList<String> base64Strings;
    private String mediaId;

    public UploadImage(ArrayList<String> base64Strings) {
        this.base64Strings = base64Strings;
    }

    @Override
    protected String doInBackground(String... strings) {
        mediaId = strings[0];

        //Upload Image----------------------------------------
        try {
            URL url2 = new URL("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/ChunkedMedia?userToken= " + LoginToken.getInstance().getToken() + "&rotate=false");
            boolean lastChunk = false;
            for (int i = 0; i < base64Strings.size(); i++) {
                if (i == base64Strings.size() - 1) {
                    lastChunk = true;
                }
                HttpURLConnection urlConnection = (HttpURLConnection) url2.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PUT");


                urlConnection.setRequestProperty("Content-type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                String req = "{\n" +
                        "  \"FileExtension\": \".bmp\",\n" +
                        "  \"MediaType\": \"image\",\n" +
                        "  \"Chunk\": \"" + base64Strings.get(i) + "\",\n" +
                        "  \"IsLastChunk\":" + lastChunk + ",\n" +
                        "  \"mediaId\": \"" + mediaId + "\"\n" +
                        "}";
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
            }
        } catch (IOException a) {
            Log.d("Sending the Image", a.getMessage());
        }
        return "Good Work";
    }
}
