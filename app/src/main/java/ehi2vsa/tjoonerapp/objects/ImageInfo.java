package ehi2vsa.tjoonerapp.objects;

import android.graphics.Bitmap;

/**
 * Created by Thijs on 29-9-2016.
 */
public class ImageInfo {
    private String large_image_path;
    private Bitmap thumbnail;

    public ImageInfo(String large_image_path,Bitmap thumbnail) {
        this.large_image_path = large_image_path;

        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public String getLarge_image_path() {
        return large_image_path;
    }


}
