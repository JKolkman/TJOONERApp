package ehi2vsa.tjoonerapp.async;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by Thijs on 6-10-2016.
 */
public class GlobalAsync extends AsyncTask<Activity,Void,String> {
    Activity activity;
    GetAllLocalImagesAsync getAllLocalImagesAsync;
    GetThumbnailsAsync getThumbnailsAsync;
    @Override
    protected String doInBackground(Activity... activities) {
        activity =activities[0];
        runGetAllImages();
//        runGetAllThumbnails();
        return null;
    }
    private void runGetAllImages(){
        getAllLocalImagesAsync=new GetAllLocalImagesAsync();
        getAllLocalImagesAsync.execute(activity);
    }
    private void runGetAllThumbnails(){
        if (getThumbnailsAsync.getStatus() == Status.FINISHED) {
            getThumbnailsAsync = new GetThumbnailsAsync();
            getThumbnailsAsync.execute(activity);
        }
    }
}
