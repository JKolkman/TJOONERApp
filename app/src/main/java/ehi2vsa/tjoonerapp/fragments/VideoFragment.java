package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.PreviewIdToImage;

public class VideoFragment extends Fragment {
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        button = (Button) view.findViewById(R.id.testbuttonYalalalalala);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;

    }
}
