package ehi2vsa.tjoonerapp.currently_notused;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.currently_notused.SinglePlaylistAdapter;
import ehi2vsa.tjoonerapp.intents.MediaInfoActivity;
import ehi2vsa.tjoonerapp.objects.Playlist;

/**
 * Created by joost on 05/10/2016.
 */
public class PlaylistInfoActivity extends AppCompatActivity {
    TextView name, id;
    ListView images;
    SinglePlaylistAdapter adapter;
    Intent intent2;
    Playlist playlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_info);
        Intent intent = getIntent();
        playlist = (Playlist) intent.getSerializableExtra("Arraylist");

        name = (TextView) findViewById(R.id.tv_playlist_info_name);
        id = (TextView) findViewById(R.id.tv_playlist_info_id);
        images = (ListView) findViewById(R.id.lv_playlist_info);
        adapter = new SinglePlaylistAdapter(playlist.getMedia(), this);
        images.setAdapter(adapter);
        intent2 = new Intent(this, MediaInfoActivity.class);
        images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent2.putExtra("mediaObject", playlist.getMedia().get(i));
                startActivity(intent2);
            }
        });
    }
}
