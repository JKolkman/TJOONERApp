package ehi2vsa.tjoonerapp.intents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

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
    EditText et_Title, et_Description, et_Author, et_Date;
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
        et_Date = (EditText) findViewById(R.id.et_date);
        cb_yes = (CheckBox) findViewById(R.id.cb_authorCheckYes);
        cb_no = (CheckBox) findViewById(R.id.cb_authorCheckNo);
        cb_img = (CheckBox) findViewById(R.id.cb_checkImage);
        cb_movie = (CheckBox) findViewById(R.id.cb_checkVideo);

        et_Title.setText(imageInfo.getTitle());
        et_Description.setText(imageInfo.getDescription());
        iv_image.setImageBitmap(BitmapFactory.decodeFile(imageInfo.getLarge_image_path()));
        Long date_taken = Long.valueOf(imageInfo.getDate_taken());
        java.util.Date time=new java.util.Date((long)date_taken);

        et_Date.setText(time.toString());
        if (cb_yes.isChecked()&& sharedPref.getString(USER,null)!=null){
            cb_no.setChecked(false);
            et_Author.setText(sharedPref.getString(USER,null));
        } else {
            cb_no.setChecked(true);
        }

    }
}
