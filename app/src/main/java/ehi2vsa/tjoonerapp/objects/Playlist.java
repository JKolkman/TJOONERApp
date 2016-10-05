package ehi2vsa.tjoonerapp.objects;

import java.util.ArrayList;

/**
 * Created by joost on 28/09/2016.
 */
public class Playlist {
    private String id;
    private String title;
    private ArrayList<Media> media;

    public Playlist(String id, String title, ArrayList<Media> media) {
        this.id = id;
        this.title = title;
        this.media = media;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail(){
        if (media.size() > 0) {
            return media.get(0).getPreviewId();
        }
        return null;
    }

    @Override
    public String toString() {
        String response = "ID: " + id + "\nTitle: " + title + " \n";
        for (int i = 0; i < media.size(); i++) {
            response += media.get(i).toString() + "\n";
        }
        return response;
    }
}
