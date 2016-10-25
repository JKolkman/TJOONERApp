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

import ehi2vsa.tjoonerapp.objects.Category;
import ehi2vsa.tjoonerapp.objects.Media;
import ehi2vsa.tjoonerapp.singletons.Categories;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 06/10/2016.
 */
public class GetCategories extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        ArrayList<Category> categories = Categories.getInstance().getCategories();
        String token = LoginToken.getInstance().getToken();
        try {
            //Getting the JSON String
            URL url = new URL(("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/Category?userToken=" + token));
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

            //Translating the JSON to Items
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                String id, backgroundcolor, description;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getString("Id");
                backgroundcolor = jsonObject.getString("BackgroundColor");
                description = jsonObject.getString("Description");
                GetImagesFromCategory getImages = new GetImagesFromCategory(id);
                ArrayList<Media>media = getImages.doInBackground();
                Category category = new Category(media, id, backgroundcolor, description);
                Categories.getInstance().addCategory(category);
                Log.d("Amount of pictures", "Category " + i + " has " + category.getMedia().size() + " images");
            }
            Log.d("Size", categories.size() + " categories");

            return "yay";
        } catch (IOException|JSONException e) {
            Log.d("GetCategoriesConnection", e.getMessage());
        }
        return null;
    }
}
