package ehi2vsa.tjoonerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.async.PreviewIdToImage;
import ehi2vsa.tjoonerapp.objects.Media;

/**
 * Created by joost on 05/10/2016.
 */
public class MediaInfoActivity extends AppCompatActivity {
    TextView author, description;
    ImageView imageView;
    Media media;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_info);
        Intent intent = getIntent();
        media = (Media) intent.getSerializableExtra("mediaObject");
        author = (TextView) findViewById(R.id.tv_media_info_author);
        description = (TextView) findViewById(R.id.tv_media_info_desc);
        imageView = (ImageView) findViewById(R.id.iv_media_info_image);

        author.setText(media.getAuthor());
        description.setText(media.getDescription());
        PreviewIdToImage image = new PreviewIdToImage(media.getResourceId());
        image.execute();
        try {
            imageView.setImageBitmap(image.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.d("playlistadapter", e.getMessage());
        }
    }
}
