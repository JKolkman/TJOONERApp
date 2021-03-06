package ehi2vsa.tjoonerapp.intents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.adapters.CheckedTextViewItemAdapter;
import ehi2vsa.tjoonerapp.async.BitmapToBase64String;
import ehi2vsa.tjoonerapp.async.GetEmptyGUID;
import ehi2vsa.tjoonerapp.async.SetImageInformation;
import ehi2vsa.tjoonerapp.async.UploadImage;
import ehi2vsa.tjoonerapp.objects.Category;
import ehi2vsa.tjoonerapp.objects.ImageInfo;
import ehi2vsa.tjoonerapp.singletons.Categories;
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
    ListView listViewCatogries;
    ImageView iv_image;
    SharedPreferences sharedPref;
    ArrayList<Category> selectedCategories = new ArrayList<>();
    Button sendImage;
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
        listViewCatogries = (ListView) findViewById(R.id.lv_categories);
        sendImage = (Button) findViewById(R.id.btn_send);

        et_Title.setText(imageInfo.getTitle());
        et_Description.setText(imageInfo.getDescription());
        iv_image.setImageBitmap(BitmapFactory.decodeFile(imageInfo.getLarge_image_path()));
        final Long date_taken = Long.valueOf(imageInfo.getDate_taken());
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
        listViewCatogries.setAdapter(new CheckedTextViewItemAdapter(this));
        listViewCatogries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category tempCategory = Categories.getInstance().getCategoryFromPosition(i);

                if (!selectedCategories.contains(tempCategory)) {
                    selectedCategories.add(Categories.getInstance().getCategoryFromPosition(i));
                    view.setBackgroundColor(Color.parseColor(tempCategory.getBackgroundcolor()));
                }
                Log.d("selectedCategories", "onItemClick: selected "+ i+" to array"+selectedCategories.size());
            }

        });
        listViewCatogries.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category tempCategory = Categories.getInstance().getCategoryFromPosition(i);
                if (selectedCategories.contains(tempCategory)) {
                    view.setBackgroundColor(Color.TRANSPARENT);
                    selectedCategories.remove(Categories.getInstance().getCategoryFromPosition(i));
                    Log.d("selectedCategories", "onItemClick: removed " + i + " to array" + selectedCategories.size());
                    return true;
                } else {
                    return false;
                }
            }
        });

        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    GetEmptyGUID getGUID = new GetEmptyGUID();
                    getGUID.execute();
                    String emptyGUID = getGUID.get();
                    Drawable drawable = iv_image.getDrawable();
                    Bitmap mutableBitmap = Bitmap.createBitmap(drawable.getMinimumWidth(), drawable.getMinimumHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(mutableBitmap);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    drawable.draw(canvas);

                    BitmapToBase64String imageToString = new BitmapToBase64String(mutableBitmap);
                    imageToString.execute();
                    ArrayList<String>base64 = imageToString.get();
                    UploadImage upload = new UploadImage(base64);
                    upload.execute(emptyGUID);

                    while(upload.get() == null){

                    }

                    if (cb_yes.isActivated()) {
                        authorRights = true;
                    }else{
                        authorRights = false;
                    }
                    SetImageInformation setInfo = new SetImageInformation(emptyGUID, et_Description.getText().toString(), tv_Date.toString(), authorRights, et_Author.getText().toString(), mediaType, selectedCategories);
                    setInfo.execute();
                }catch (InterruptedException|ExecutionException e){
                    Log.d("Upload", e.getMessage());

                }
            }
        });

    }
}
