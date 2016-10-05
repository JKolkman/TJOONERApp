package ehi2vsa.tjoonerapp.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.objects.Media;
import ehi2vsa.tjoonerapp.objects.Playlist;

/**
 * Created by joost on 29/09/2016.
 */
public class ParseStringToPlaylist extends AsyncTask<String, String, ArrayList<Playlist>> {
    private String response;
    String id, title;
    String id2, previewId, resourceId, description, author, mediaType, preview;

    public ParseStringToPlaylist(String response) {
        this.response = response;
    }

    @Override
    protected ArrayList<Playlist> doInBackground(String... strings) {
        try {
            JSONArray jArrayPlaylist = new JSONArray(response);
            ArrayList<Playlist> arrayPlaylist = new ArrayList<Playlist>();
            for (int j = 0; j < jArrayPlaylist.length(); j++) {
                JSONObject jObjectPlaylist = jArrayPlaylist.getJSONObject(j);
                //System.out.println(jObjectPlaylist.toString());
                id = jObjectPlaylist.getString("Id");
                title = jObjectPlaylist.getString("Title");
                JSONArray jArrayMedia = jObjectPlaylist.getJSONArray("Media");
                ArrayList<Media>arrayMedia = new ArrayList<Media>();
                for (int i = 0; i < jArrayMedia.length(); i++) {
                    JSONObject jObjectMedia = jArrayMedia.getJSONObject(i);
                    id2 = jObjectMedia.getString("Id");
                    previewId = jObjectMedia.getString("PreviewId");
                    resourceId = jObjectMedia.getString("ResourceId");
                    description = jObjectMedia.getString("Description");
                    author = jObjectMedia.getString("Author");
                    mediaType = jObjectMedia.getString("MediaType");
                    preview = jObjectMedia.getString("Preview");
                    Media media = new Media(id2, previewId, resourceId, description, author, mediaType, preview);
                    arrayMedia.add(media);
                }
                Playlist playlist = new Playlist(id, title, arrayMedia);
                arrayPlaylist.add(playlist);
            }
            return arrayPlaylist;
        } catch (JSONException e) {
            Log.d("JSON", e.getMessage());
        }
        return null;
    }
}
