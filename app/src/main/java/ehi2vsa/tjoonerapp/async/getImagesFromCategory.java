package ehi2vsa.tjoonerapp.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ehi2vsa.tjoonerapp.objects.Media;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 06/10/2016.
 */
public class GetImagesFromCategory extends AsyncTask<String, String, ArrayList<Media>> {
    private String categoryID;

    public GetImagesFromCategory(String categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    protected ArrayList<Media> doInBackground(String... strings) {
        ArrayList<Media> media = new ArrayList<Media>();
        String token = LoginToken.getInstance().getToken();
        //get JSON
        try {
            URL url = new URL(("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/Media?userToken=" + token));
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


            //Translate to items
            JSONArray jsonArray = new JSONArray(response.toString());
            String mediaID, mediaPreviewId, mediaResourceId, mediaDescription, mediaAuthor, mediaType, mediaPreview;
            for (int i = 0; i < jsonArray.length(); i++) {
                ArrayList<String> categoriesString = new ArrayList<>();
                boolean inCategory = false;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray jArray = jsonObject.getJSONArray("Categories");
                for (int j = 0; j < jArray.length(); j++) {
                    String categoryString2 = "";
                    JSONObject jObject = jArray.getJSONObject(j);
                    categoryString2 += jObject.getString("Description");
                    categoryString2 += ";";
                    categoryString2 += jObject.getString("BackgroundColor");
                    categoriesString.add(categoryString2);
                    if (jObject.getString("Id").equals(categoryID)) {
                        inCategory = true;

                    }
                }
                if (inCategory) {
                    mediaID = jsonObject.getString("Id");
                    mediaPreviewId = jsonObject.getString("PreviewId");
                    mediaResourceId = jsonObject.getString("ResourceId");
                    mediaDescription = jsonObject.getString("Description");
                    mediaAuthor = jsonObject.getString("Author");
                    mediaType = jsonObject.getString("MediaType");
                    mediaPreview = jsonObject.getString("Preview");
                    Media temp = new Media(mediaID, mediaPreviewId, mediaResourceId, mediaDescription, mediaAuthor, mediaType, mediaPreview, categoriesString);
                    media.add(temp);
                }
            }
            return media;
        } catch (IOException | JSONException e) {
            Log.d("getMediaFromCategory", e.getMessage());
        }
        return null;
    }
}
