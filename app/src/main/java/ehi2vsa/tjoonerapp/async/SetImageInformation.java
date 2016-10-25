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
import java.util.ArrayList;

import ehi2vsa.tjoonerapp.objects.Category;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 13/10/2016.
 */

public class SetImageInformation extends AsyncTask<String, String, String> {
    private String description, date, author, type, imageId, categoryJSON;
    private boolean authorRights;
    ArrayList<Category>categories;
    public SetImageInformation(String imageId, String description, String date, boolean authorRights, String author, String mediaType, ArrayList<Category>categories) {
        this.imageId = imageId;
        this.description = description;
        this.date = date;
        this.author = author;
        this.authorRights = authorRights;
        this.type = mediaType;
        this.categories = categories;

        categoryJSON = "[";
        for (int i = 0; i < categories.size(); i++) {
            categoryJSON += "\n{\n\"Id: \" " +categories.get(i).getId();
            categoryJSON += "\n\"BackgroundColor: \"" + categories.get(i).getBackgroundcolor();
            categoryJSON += "\n\"Description: \"" + categories.get(i).getDescription() + "\n}";
            if (i != (categories.size() - 1)){
                categoryJSON += ",";
            } else {
                categoryJSON += "\n]";
            }
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url2 = new URL("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/Media?userToken=" + LoginToken.getInstance().getToken());
            HttpURLConnection urlConnection = (HttpURLConnection) url2.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");


            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            String req = "{\n" +
                    "  \"Id\": \"" + imageId +"\",\n" +
                    "  \"Description\": \"" + description + "\",\n" +
                    "  \"Categories\": \"" + "" + "\",\n" +
                    "  \"CreationDateTime\":" + date + ",\n" +
                    "  \"AuthorRights\": \"" + authorRights + "\"\n" +
                    "  \"Author\": \"" + author + "\"\n" +
                    "  \"MediaType\": \"" + type + "\"\n" +
                    "  \"PreviewId\": \"" + null + "\"\n" +
                    "  \"ResourceId\": \"" + null + "\"\n" +
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

            return result.toString();
        } catch (IOException e){
            Log.d("ImageInfoUpload", e.getMessage());
        }

        return null;


    }
}
