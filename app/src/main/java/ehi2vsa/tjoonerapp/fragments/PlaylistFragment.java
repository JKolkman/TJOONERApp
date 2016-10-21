package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.GetPlaylist;
import ehi2vsa.tjoonerapp.adapters.PlaylistAdapter;
import ehi2vsa.tjoonerapp.singletons.PlaylistSingleton;

/**
 * Created by joost on 21/10/2016.
 */

public class PlaylistFragment extends Fragment {
    ListView listview;
    private boolean firstTime = true;
    PlaylistAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        listview = (ListView) view.findViewById(R.id.playlist_listview);
        adapter = new PlaylistAdapter(PlaylistSingleton.getInstance().getList(), getActivity());
        if (firstTime) {
            LoadPlaylist();
            adapter.notifyDataSetChanged();
            firstTime = false;
        }
        listview.setAdapter(adapter);


        return view;
    }

    private void LoadPlaylist() {
        GetPlaylist getPlaylist = new GetPlaylist();
        getPlaylist.execute();
    }
}
