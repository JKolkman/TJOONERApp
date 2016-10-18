package ehi2vsa.tjoonerapp.intents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.objects.ImageInfo;
import ehi2vsa.tjoonerapp.singletons.ImagesOnPhone;

/**
 * Created by Thijs on 18-10-2016.
 */

public class PrepareImageForSending extends AppCompatActivity {
    String description;
    String date;
    boolean authorRights;
    String author;
    String mediaType;
    int position;
    ImageInfo imageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_prepare_image);
        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("Position");
        ImageInfo imageInfo = ImagesOnPhone.getInstance().getImageInfo(position);

    }
}
