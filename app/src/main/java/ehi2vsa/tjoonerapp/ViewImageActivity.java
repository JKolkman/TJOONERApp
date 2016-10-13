package ehi2vsa.tjoonerapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import ehi2vsa.tjoonerapp.objects.ImageInfo;
import ehi2vsa.tjoonerapp.singletons.ImagesOnPhone;

/**
 * Created by Thijs on 13-10-2016.
 */

public class ViewImageActivity extends AppCompatActivity {
    ImageView ivImage;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_info);
        Intent intent = getIntent();
        int position = (int) intent.getSerializableExtra("Position");
        ImageInfo imageInfo = ImagesOnPhone.getInstance().getImageInfo(position);
        ivImage = (ImageView) findViewById(R.id.iv_Image_Info);
        textView= (TextView) findViewById(R.id.tv_ImageInfo);
        ivImage.setImageBitmap(BitmapFactory.decodeFile(imageInfo.getLarge_image_path()));
//        ivImage.setImageResource(imageInfo.getLarge_image_path());
        textView.setText(imageInfo.getTitle());
    }
}
