package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.GetPlaylist;
import ehi2vsa.tjoonerapp.adapters.PlaylistAdapter;
import ehi2vsa.tjoonerapp.intents.PlaylistInfoActivity;
import ehi2vsa.tjoonerapp.singletons.PlaylistSingleton;

/**
 * Created by joost on 21/10/2016.
 */

public class PlaylistFragment extends Fragment {
    GridView listview;
    PlaylistAdapter adapter;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        listview = (GridView) view.findViewById(R.id.playlist_gridview);
        adapter = new PlaylistAdapter(PlaylistSingleton.getInstance().getList(), getActivity());
        listview.setAdapter(adapter);

        intent = new Intent(getActivity(), PlaylistInfoActivity.class);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("Arraylist", PlaylistSingleton.getInstance().getList().get(i));
                startActivity(intent);
            }
        });


        return view;
    }
}
