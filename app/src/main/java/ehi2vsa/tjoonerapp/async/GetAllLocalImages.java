package ehi2vsa.tjoonerapp.async;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.objects.ImageInfo;

/**
 * Created by Thijs on 5-10-2016.
 */
public class GetAllLocalImages extends AsyncTask<Activity,String,ArrayList<ImageInfo>>{
    private ArrayList<ImageInfo> imageInfos;

    private ArrayList<ImageInfo> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_id;
        ArrayList<ImageInfo> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage = null;
        String titleOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(new ImageInfo(absolutePathOfImage,getThumbnail(activity, absolutePathOfImage)));
        }
        Log.d("size", "getAllShownImagesPath: size " + listOfAllImages.size());
        cursor.close();

        return listOfAllImages;
    }

    public static Bitmap getThumbnail(Activity activity, String path) {
        ContentResolver cr = activity.getContentResolver();
        Cursor ca = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns._ID}, MediaStore.MediaColumns.DATA + "=?", new String[]{path}, null);
        if (ca != null && ca.moveToFirst()) {
            int id = ca.getInt(ca.getColumnIndex(MediaStore.MediaColumns._ID));
            ca.close();
            return MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MINI_KIND, null);
        }

        ca.close();
        return null;

    }

    @Override
    protected ArrayList<ImageInfo> doInBackground(Activity... activities) {
        imageInfos = getAllShownImagesPath(activities[0]);
        return imageInfos;
    }


}

