package ehi2vsa.tjoonerapp.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
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
import ehi2vsa.tjoonerapp.objects.Playlist;
import ehi2vsa.tjoonerapp.singletons.Categories;
import ehi2vsa.tjoonerapp.singletons.LoginToken;
import ehi2vsa.tjoonerapp.singletons.PlaylistSingleton;

/**
 * Created by joost on 29/09/2016.
 */
public class GetPlaylist extends AsyncTask<String, String, String> {
    String id, title;
    String id2, previewId, resourceId, description, author, mediaType, preview;

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

            JSONArray jArrayPlaylist = new JSONArray(response.toString());
            ArrayList<Playlist> arrayPlaylist = new ArrayList<Playlist>();
            for (int j = 0; j < jArrayPlaylist.length(); j++) {
                JSONObject jObjectPlaylist = jArrayPlaylist.getJSONObject(j);
                //System.out.println(jObjectPlaylist.toString());
                id = jObjectPlaylist.getString("Id");
                title = jObjectPlaylist.getString("Title");
                JSONArray jArrayMedia = jObjectPlaylist.getJSONArray("Media");
                ArrayList<Media> arrayMedia = new ArrayList<Media>();
                for (int i = 0; i < jArrayMedia.length(); i++) {
                    JSONObject jObjectMedia = jArrayMedia.getJSONObject(i);
                    id2 = jObjectMedia.getString("Id");
                    previewId = jObjectMedia.getString("PreviewId");
                    resourceId = jObjectMedia.getString("ResourceId");
                    description = jObjectMedia.getString("Description");
                    author = jObjectMedia.getString("Author");
                    mediaType = jObjectMedia.getString("MediaType");
                    preview = jObjectMedia.getString("Preview");
                    ArrayList<String> categoriesString = new ArrayList<>();
                    try {
                        JSONArray jArray = jObjectPlaylist.getJSONArray("Categories");
                        for (int o = 0; o < jArray.length(); o++) {
                            String categoryString2 = "";
                            JSONObject jObject = jArray.getJSONObject(j);
                            categoryString2 += jObject.getString("Description");
                            categoryString2 += ";";
                            categoryString2 += jObject.getString("BackgroundColor");
                            categoriesString.add(categoryString2);
                        }
                    } catch (Exception e) {
                        Log.d("Categories: ", "No categories found");
                    }
                    Media media = new Media(id2, previewId, resourceId, description, author, mediaType, preview, categoriesString);
                    arrayMedia.add(media);
                }
                Playlist playlist;
                if (arrayMedia.size() > 0) {
                    Bitmap preview = getImage(arrayMedia.get(0).getPreviewId());
                    playlist = new Playlist(id, title, arrayMedia, preview);
                } else {
                    playlist = new Playlist(id, title, arrayMedia, null);
                }
                arrayPlaylist.add(playlist);
            }
            Log.d("playlist amount:", "" + arrayPlaylist.size());
            PlaylistSingleton.getInstance().setPlaylist(arrayPlaylist);

            return "Succes";
        } catch (IOException | JSONException e) {
            Log.d("JSON", e.getMessage());
        }
        return "Failed";
    }

    private Bitmap getImage(String previewId) {
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
