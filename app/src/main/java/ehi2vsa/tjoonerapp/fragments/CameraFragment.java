package ehi2vsa.tjoonerapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ehi2vsa.tjoonerapp.R;

public class CameraFragment extends Fragment {
    private static final String TAG = CameraFragment.class.getSimpleName();
    public static int REQUEST_IMAGE_CAPTURE = 1;

    ImageView ivResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        ivResult = (ImageView) view.findViewById(R.id.ivResult);

        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivResult.setImageBitmap(imageBitmap);
        } else {
            Log.i(TAG, "requestCode: " + requestCode + "resultCode: " + resultCode);
        }
    }
}
