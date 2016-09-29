package ehi2vsa.tjoonerapp.objects;

/**
 * Created by joost on 28/09/2016.
 */
public class Playlist {
    private String id;
    private String title;
    private Media[] media;

    public Playlist(String id, String title, Media[] media) {
        this.id = id;
        this.title = title;
        this.media = media;
    }

    public Media[] getMedia() {
        return media;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail(){
        return media[0].getPreviewId();
    }
}
