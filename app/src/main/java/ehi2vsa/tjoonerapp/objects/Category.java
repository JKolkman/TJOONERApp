package ehi2vsa.tjoonerapp.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joost on 06/10/2016.
 */
public class Category implements Serializable{
    private ArrayList<Media> media;
    private String id, backgroundcolor, description;

    public Category(ArrayList<Media> media, String id, String backgroundcolor, String description) {
        this.media = media;
        this.id = id;
        this.backgroundcolor = backgroundcolor;
        this.description = description;
    }

    public String getThumbnail() {
        if (media.size() > 0) {
            return media.get(0).getPreviewId();
        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public String getId() {
        return id;
    }
}
