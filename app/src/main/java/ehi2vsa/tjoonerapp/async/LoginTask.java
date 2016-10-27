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
import java.net.URL;

/**
 * Created by joost on 12/10/2016.
 */

public class LoginTask extends AsyncTask<String, String, String> {
    String username;
    String password;
    URL url;
    HttpURLConnection urlConnection;

    public LoginTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            url = new URL("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/login");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");


            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            String req = "{\n \"Username\": \"" + username + "\", \n \"Password\": \"" + password + "\"\n}";
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
            System.out.println(result.toString());
            return result.toString();
        } catch (IOException a) {
            System.out.println(a.getMessage());
        }
        return "Error";
    }
}
