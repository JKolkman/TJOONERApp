package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.HttpURLConnection;
import java.net.URL;

import ehi2vsa.tjoonerapp.LoggedIn;
import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

/**
 * Created by joost on 28/09/2016.
 */
public class MediaFragment extends Fragment {
    URL url;
    HttpURLConnection urlConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        return view;
    }


    private class getMedia extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                String token = LoginToken.getInstance().getToken();
                url = new URL("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/Media?userToken={" + token + "}");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("GET");

            } catch (Exception e) {

            }
            return null;
        }
    }
}
