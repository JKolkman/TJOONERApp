package ehi2vsa.tjoonerapp.intents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    EditText et_Title, et_Description, et_Author;
    TextView tv_Date;
    CheckBox cb_yes, cb_no, cb_img, cb_movie;
    ImageView iv_image;
    SharedPreferences sharedPref;
    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String USER = "USER";
    final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_prepare_image);
        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("Position");
        sharedPref = this.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
        iv_image = (ImageView) findViewById(R.id.iv_prepare_image);
        imageInfo = ImagesOnPhone.getInstance().getImageInfo(position);
        et_Title = (EditText) findViewById(R.id.et_title);
        et_Description = (EditText) findViewById(R.id.et_description);
        et_Author = (EditText) findViewById(R.id.et_authorName);
        tv_Date = (TextView) findViewById(R.id.tv_dateItem);
        cb_yes = (CheckBox) findViewById(R.id.cb_authorCheckYes);
        cb_no = (CheckBox) findViewById(R.id.cb_authorCheckNo);
        cb_img = (CheckBox) findViewById(R.id.cb_checkImage);
        cb_movie = (CheckBox) findViewById(R.id.cb_checkVideo);

        et_Title.setText(imageInfo.getTitle());
        et_Description.setText(imageInfo.getDescription());
        iv_image.setImageBitmap(BitmapFactory.decodeFile(imageInfo.getLarge_image_path()));
        Long date_taken = Long.valueOf(imageInfo.getDate_taken());
        java.util.Date time = new java.util.Date(date_taken);
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        tv_Date.setText(dateFormat.format(time));
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton == cb_no) {
                    cb_yes.setChecked(!b);
                } else if (compoundButton == cb_yes) {
                    cb_no.setChecked(!b);
                } else if (compoundButton == cb_img) {
                    cb_movie.setChecked(!b);
                } else {
                    cb_img.setChecked(!b);
                }
            }
        };
        cb_yes.setOnCheckedChangeListener(onCheckedChangeListener);
        cb_no.setOnCheckedChangeListener(onCheckedChangeListener);
        cb_img.setOnCheckedChangeListener(onCheckedChangeListener);
        cb_movie.setOnCheckedChangeListener(onCheckedChangeListener);


    }
}
