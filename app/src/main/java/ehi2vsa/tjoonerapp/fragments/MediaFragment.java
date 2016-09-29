package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.adapters.PlaylistAdapter;
import ehi2vsa.tjoonerapp.async.GetPlaylist;
import ehi2vsa.tjoonerapp.async.ParseStringToPlaylist;
import ehi2vsa.tjoonerapp.objects.Playlist;

public class MediaFragment extends Fragment {
    Playlist[] playlistArray;
    private ListView lv_playlist;
    private PlaylistAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        try {
            GetPlaylist getPlaylist = new GetPlaylist();
            getPlaylist.execute();
            String playlistString = getPlaylist.get();

            ParseStringToPlaylist parse = new ParseStringToPlaylist(playlistString);
            parse.execute();

            playlistArray = parse.get();
            String toast = "Retrieved " + playlistArray.length + " Playlists";
            Toast.makeText(getActivity(),toast, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("OnclickListener", e.getMessage());
        }

        lv_playlist = (ListView) view.findViewById(R.id.lv_playlist);
        adapter = new PlaylistAdapter(getActivity(), playlistArray);
        lv_playlist.setAdapter(adapter);

        return view;
    }
}
