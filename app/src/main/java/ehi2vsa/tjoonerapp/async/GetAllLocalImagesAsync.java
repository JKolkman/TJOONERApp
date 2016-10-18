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
import ehi2vsa.tjoonerapp.singletons.ImagesOnPhone;

/**
 * Created by Thijs on 5-10-2016.
 */
public class GetAllLocalImagesAsync extends AsyncTask<Activity, Integer, ArrayList<ImageInfo>> {
    private ArrayList<ImageInfo> getAllShownImagesPath(Activity activity) {

        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_title,column_index_date_taken, column_index_description;
//        ArrayList<ImageInfo> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage,titleOfImage,description,date_taken ;
        Bitmap thumbnail=null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.DESCRIPTION};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_title = cursor.getColumnIndex(MediaStore.MediaColumns.TITLE);
        column_index_description = cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION);
        column_index_date_taken = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
        while (cursor.moveToNext()) {
            Log.d("ÏmagesPath", "getAllShownImagesPath: adding object");
            absolutePathOfImage = cursor.getString(column_index_data);
            titleOfImage = cursor.getString(column_index_title);
            description = cursor.getString(column_index_description);
            date_taken = cursor.getString(column_index_date_taken);
//            listOfAllImages.add(new ImageInfo(titleOfImage,absolutePathOfImage));
            thumbnail = getThumbnail(activity,absolutePathOfImage);
//            listOfAllImages.add(new ImageInfo(titleOfImage, absolutePathOfImage, thumbnail));
            ImagesOnPhone.getInstance().addImageInfo(new ImageInfo(titleOfImage, absolutePathOfImage, thumbnail,description,date_taken));
        }
//        Log.d("size", "getAllShownImagesPath: size " + listOfAllImages.size());
        cursor.close();

        return null;
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
        return getAllShownImagesPath(activities[0]);
    }

}
class GetThumbnailsAsync extends AsyncTask<Activity,Void,String>{

    @Override
    protected String doInBackground(Activity... activities) {
        for (ImageInfo imageInfo:ImagesOnPhone.getInstance().getImageInfos()
             ) {
            imageInfo.setThumbnail(getThumbnail(activities[0],imageInfo.getLarge_image_path()));
        }
        return null;
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
}


