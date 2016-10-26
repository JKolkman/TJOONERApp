package ehi2vsa.tjoonerapp.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by joost on 12/10/2016.
 */

public class BitmapToBase64String extends AsyncTask<String,String, ArrayList<String>>{
    private Bitmap bitmap;

    public BitmapToBase64String(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> base64Strings = new ArrayList<>();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        String temp = "";
        for (int i = 0; i < encoded.length(); i++) {
            int counter = 0;
            temp += encoded.charAt(i);
            counter++;
            if (counter >= 2000 || i == encoded.length() - 1){
                base64Strings.add(temp);
                counter = 0;
                temp = "";
            }
        }
        return base64Strings;
    }
}
