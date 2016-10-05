package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.PlaylistInfoActivity;
import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.adapters.PlaylistAdapter;
import ehi2vsa.tjoonerapp.async.GetPlaylist;
import ehi2vsa.tjoonerapp.async.ParseStringToPlaylist;
import ehi2vsa.tjoonerapp.objects.Playlist;

public class MediaFragment extends Fragment {
    ArrayList<Playlist> playlistArray;
    private ListView listviewPlaylist;
    private PlaylistAdapter adapter;
    Intent intent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        listviewPlaylist = (ListView) view.findViewById(R.id.lv_playlist);
        try {
            GetPlaylist getPlaylist = new GetPlaylist();
            getPlaylist.execute();
            String playlistString = getPlaylist.get();
            System.out.println(getPlaylist.get());

            ParseStringToPlaylist parse = new ParseStringToPlaylist(playlistString);
            parse.execute();
            playlistArray = parse.get();
            for (int i = 0; i < playlistArray.size(); i++) {
                Log.d("MediaFragment", playlistArray.get(i).toString());
            }
            String toast = "Retrieved " + playlistArray.size() + " Playlists";
            Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.d("OnclickListener", e.getMessage());
        }
        adapter = new PlaylistAdapter(playlistArray, getActivity());
        listviewPlaylist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        intent = new Intent(getActivity(), PlaylistInfoActivity.class);
        listviewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("Arraylist", playlistArray.get(i));
                startActivity(intent);
            }
        });
        return view;
    }
}
