package ehi2vsa.tjoonerapp.intents;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.objects.ImageInfo;
import ehi2vsa.tjoonerapp.singletons.ImagesOnPhone;

/**
 * Created by Thijs on 13-10-2016.
 */

public class ViewImageActivity extends AppCompatActivity {
    ImageView ivImage;
    TextView textView;
    Button clickButton;
    Intent editImageData;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_info);
        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("Position");
        ImageInfo imageInfo = ImagesOnPhone.getInstance().getImageInfo(position);
        ivImage = (ImageView) findViewById(R.id.iv_Image_Info);
        textView= (TextView) findViewById(R.id.tv_ImageInfo);
        clickButton = (Button) findViewById(R.id.btn_click);
        ivImage.setImageBitmap(BitmapFactory.decodeFile(imageInfo.getLarge_image_path()));
//        ivImage.setImageResource(imageInfo.getLarge_image_path());
        textView.setText(imageInfo.getTitle());
        editImageData= new Intent(this, PrepareImageForSending.class);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImageData.putExtra("Position",position);
                startActivity(editImageData);
            }
        });
    }
}
