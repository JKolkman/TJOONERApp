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

    public String getJSON(){
        String json = "";
        String cat = "";
        String cats = "";

        for (int i = 0; i <categories.size() ; i++) {
            String[]strings = categories.get(i).split(";");
            cat += "{\n"+
                    "\"Id\": \"" + strings[0] + "\"\n" +
                    "\"BackgroundColor\": \"" + strings[2] + "\"\n" +
                    "\"Description\": \"" + strings[1] + "\"\n}";

            cats += cat;
            if (i != categories.size() - 1){
                cats += ",";
            }
        }
        json += "{\n" +
                "\"Id\": \"" + id + "\",\n" +
                "\"Description\": \"" + description + "\",\n" +
                "\"Categories\": [" +cats + "],\n" +
                "\"CreationDateTime\": \"2016-10-26T15:28:55.9792797+02:00\",\n" +
                "\"Author\": \"" + author + "\",\n" +
                "\"MediaType\": \"image\",\n" +
                "\"PreviewId\": \"" + previewId + "\",\n" +
                "\"ResourceId\": \"" + resourceId + "\",\n" +
                "\"Preview\": \"" + preview + "\"\n}";

        return json;
    }
}

