package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.GetPlaylist;
import ehi2vsa.tjoonerapp.adapters.PlaylistAdapter;
import ehi2vsa.tjoonerapp.singletons.PlaylistSingleton;

/**
 * Created by joost on 21/10/2016.
 */

public class PlaylistFragment extends Fragment {
    GridView listview;
    private boolean firstTime = true;
    PlaylistAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        listview = (GridView) view.findViewById(R.id.playlist_gridview);
        if (firstTime) {
            LoadPlaylist();
            firstTime = false;
        }
        adapter = new PlaylistAdapter(PlaylistSingleton.getInstance().getList(), getActivity());
        listview.setAdapter(adapter);


        return view;
    }

    private void LoadPlaylist() {
        GetPlaylist getPlaylist = new GetPlaylist();
        getPlaylist.execute();
    }
}
