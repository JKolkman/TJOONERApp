package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ehi2vsa.tjoonerapp.Media;
import ehi2vsa.tjoonerapp.Playlist;
import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

public class MediaFragment extends Fragment {
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        button = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getMedia media = new getMedia();
                    media.execute("Lol");
                    String result = media.get();

                    parseString parse = new parseString(result);
                    parse.execute();
                    Playlist[] pArray = parse.get();

                    Toast.makeText(getActivity(), "Retrieved Playlists", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.d("OnclickListener", e.getMessage());
                }
            }
        });


        return view;
    }

    private class getMedia extends AsyncTask<String, String, String> {
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
                return response.toString();
            } catch (Exception e) {
                Log.d("EndOfMediaFragment", e.getMessage());
            }
            return null;
        }
    }

    private class parseString extends AsyncTask<String, String, Playlist[]> {
        private String response;

        public parseString(String response) {
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

}
