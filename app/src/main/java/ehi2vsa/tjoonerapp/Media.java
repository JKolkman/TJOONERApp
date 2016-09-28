package ehi2vsa.tjoonerapp;

/**
 * Created by joost on 28/09/2016.
 */
public class Media {
    private String id, previewId, resourceId;
    private String description,  author, mediaType, preview;
    private MediaCategory[] categories;

    public Media(String id, String previewId, String resourceId, String description, String author, String mediaType, String preview, MediaCategory[] categories) {
        this.id = id;
        this.previewId = previewId;
        this.resourceId = resourceId;
        this.description = description;
        this.author = author;
        this.mediaType = mediaType;
        this.preview = preview;
        this.categories = categories;
    }
}
