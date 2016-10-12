package ehi2vsa.tjoonerapp.objects;

import android.graphics.Bitmap;

/**
 * Created by Thijs on 29-9-2016.
 */
public class ImageInfo {
    private String title;
    private String large_image_path;
    private Bitmap thumbnail;

    public ImageInfo(String title,String large_image_path,Bitmap thumbnail) {
        this.title = title;
        this.large_image_path = large_image_path;
        this.thumbnail = thumbnail;
    }

    public ImageInfo(String title, String large_image_path) {
        this.title = title;
        this.large_image_path = large_image_path;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public String getLarge_image_path() {
        return large_image_path;
    }

    public String getTitle() {
        return title;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
