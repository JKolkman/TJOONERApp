package ehi2vsa.tjoonerapp.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ehi2vsa.tjoonerapp.objects.Media;
import ehi2vsa.tjoonerapp.objects.Playlist;

/**
 * Created by joost on 29/09/2016.
 */
public class ParseStringToPlaylist extends AsyncTask<String, String, Playlist[]> {
    private String response;

    public ParseStringToPlaylist(String response) {
        this.response = response;
    }

    @Override
    protected Playlist[] doInBackground(String... strings) {
        try {
            JSONArray jArrayPlaylist = new JSONArray(response);
            Playlist[] arrayPlaylist = new Playlist[jArrayPlaylist.length()];
            for (int j = 0; j < jArrayPlaylist.length(); j++) {
                JSONObject jObjectPlaylist = jArrayPlaylist.getJSONObject(j);
                String id, title;
                id = jObjectPlaylist.getString("Id");
                title = jObjectPlaylist.getString("Title");
                JSONArray jArrayMedia = jObjectPlaylist.getJSONArray("Media");
                Media[] arrayMedia = new Media[jArrayMedia.length()];
                for (int i = 0; i < jArrayMedia.length(); i++) {
                    JSONObject jObjectMedia = jArrayMedia.getJSONObject(i);
                    String id2, previewId, resourceId, description, author, mediaType, preview;
                    id2 = jObjectMedia.getString("Id");
                    previewId = jObjectMedia.getString("PreviewId");
                    resourceId = jObjectMedia.getString("ResourceId");
                    description = jObjectMedia.getString("Description");
                    author = jObjectMedia.getString("Author");
                    mediaType = jObjectMedia.getString("MediaType");
                    preview = jObjectMedia.getString("Preview");
                    Media media = new Media(id2, previewId, resourceId, description, author, mediaType, preview);
                    arrayMedia[i] = media;
                    Log.d("Media", media.toString());
                }
                Playlist playlist = new Playlist(id, title, arrayMedia);
                arrayPlaylist[j] = playlist;
            }
            return arrayPlaylist;
        } catch (JSONException e) {
            Log.d("JSON", e.getMessage());
        }
        return null;
    }
}
