package ehi2vsa.tjoonerapp.singletons;

import android.util.Log;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.objects.ImageInfo;

/**
 * Created by Thijs on 6-10-2016.
 */
public class ImagesOnPhone {
    private ArrayList<ImageInfo> imageInfos;
    private static ImagesOnPhone instance;
    public static ImagesOnPhone getInstance(){
        if (instance == null){
            instance = new ImagesOnPhone();
        }
        return instance;
    }
    private ImagesOnPhone(){
        imageInfos = new ArrayList<>();
    }
    public void addImageInfo(ImageInfo imageInfo){
        Log.d("addImageInfo", "addImageInfo: adding images now size"+imageInfos.size());
        imageInfos.add(imageInfo);
    }
    public ArrayList<ImageInfo> getImageInfos(){
        return imageInfos;
    }
    public ImageInfo getImageInfo(int position){
        return imageInfos.get(position);
    }

}
