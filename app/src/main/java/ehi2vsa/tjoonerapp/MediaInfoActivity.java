package ehi2vsa.tjoonerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.adapters.CategoryNameAdapter;
import ehi2vsa.tjoonerapp.async.PreviewIdToImage;
import ehi2vsa.tjoonerapp.objects.Media;

/**
 * Created by joost on 05/10/2016.
 */
public class MediaInfoActivity extends AppCompatActivity {
    TextView name, description;
    ImageView imageView;
    Media media;
    GridView view;
    CategoryNameAdapter adapter;


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
