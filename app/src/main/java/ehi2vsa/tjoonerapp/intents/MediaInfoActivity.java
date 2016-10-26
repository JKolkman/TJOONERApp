package ehi2vsa.tjoonerapp.intents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.adapters.CategoryNameAdapter;
import ehi2vsa.tjoonerapp.adapters.PlaylistPickerAdapter;
import ehi2vsa.tjoonerapp.async.AddToPlaylist;
import ehi2vsa.tjoonerapp.async.DeleteImage;
import ehi2vsa.tjoonerapp.async.GetCategories;
import ehi2vsa.tjoonerapp.async.PreviewIdToImage;
import ehi2vsa.tjoonerapp.objects.Category;
import ehi2vsa.tjoonerapp.objects.Media;
import ehi2vsa.tjoonerapp.singletons.Categories;
import ehi2vsa.tjoonerapp.singletons.PlaylistSingleton;

/**
 * Created by joost on 05/10/2016.
 */
public class MediaInfoActivity extends AppCompatActivity {
    TextView name, description;
    ImageView imageView;
    Media media;
    GridView view;
    CategoryNameAdapter adapter;
    Button button, addtolist;
    PlaylistPickerAdapter playlistadapter;
    RelativeLayout pl_layout;
    ListView lv_playlist;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_info);
        Intent intent = getIntent();
        media = (Media) intent.getSerializableExtra("Media");
        name = (TextView) findViewById(R.id.tv_media_info_author);
        description = (TextView) findViewById(R.id.tv_media_info_desc);
        imageView = (ImageView) findViewById(R.id.iv_media_info_image);
        view = (GridView) findViewById(R.id.gridView_imageinfo);
        adapter = new CategoryNameAdapter(media.getCategories(), this);
        view.setAdapter(adapter);
        addtolist = (Button) findViewById(R.id.btn_addplaylist);
        lv_playlist = (ListView) findViewById(R.id.lv_playlist_choose);
        pl_layout = (RelativeLayout) findViewById(R.id.playlist_layout_id);
        playlistadapter = new PlaylistPickerAdapter(this);
        lv_playlist.setAdapter(playlistadapter);

        addtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pl_layout.setVisibility(View.VISIBLE);
            }
        });

        lv_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AddToPlaylist add = new AddToPlaylist(PlaylistSingleton.getInstance().getList().get(i), media);
                add.execute();
                pl_layout.setVisibility(View.GONE);
            }
        });

        button = (Button) findViewById(R.id.delete_image_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteImage delete = new DeleteImage();
                delete.execute(media.getId());
                Categories.getInstance().getCategories().clear();
                GetCategories get = new GetCategories();
                get.execute();
                finishActivity(1);
                finish();
            }
        });

        name.setText(media.getDescription());
        description.setText("");
        PreviewIdToImage image = new PreviewIdToImage(media.getResourceId());
        image.execute();
        try {
            imageView.setImageBitmap(image.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.d("playlistadapter", e.getMessage());
        }
    }
}
