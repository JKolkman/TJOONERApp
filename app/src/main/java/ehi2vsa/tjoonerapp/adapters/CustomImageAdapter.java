package ehi2vsa.tjoonerapp.adapters;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.objects.ImageInfo;

public class CustomImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ImageInfo> images;

    public CustomImageAdapter(Activity activity) {
        mContext = activity;

        images = getAllShownImagesPath(activity);
    }


    public int getCount() {

        return images.size();
//        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        public ViewHolder() {
        }

        TextView tvPath;
        ImageView ivImage;

    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_gridview_component, null);
            holder = new ViewHolder();
            holder.tvPath = (TextView) convertView.findViewById(R.id.gridview_textview);
            holder.ivImage = (ImageView) convertView.findViewById(R.id.gridview_imageview);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageInfo imageInfo = images.get(position);
        holder.ivImage.setImageBitmap(imageInfo.getThumbnail());
        holder.tvPath.setText(imageInfo.getLarge_image_path());
        return convertView;
    }


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
}
