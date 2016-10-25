package ehi2vsa.tjoonerapp.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Media implements Serializable{
    private String id, previewId, resourceId;
    private String description,author, mediaType, preview;
    private ArrayList<String> categories;

    public Media(String id, String previewId, String resourceId, String description, String author, String mediaType, String preview, ArrayList<String> categories){
        this.id = id;
        this.previewId = previewId;
        this.resourceId = resourceId;
        this.description = description;
        this.author = author;
        this.mediaType = mediaType;
        this.preview = preview;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public String getPreviewId() {
        return previewId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getPreview() {
        return preview;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}

