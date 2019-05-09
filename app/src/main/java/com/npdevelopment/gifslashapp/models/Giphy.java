package com.npdevelopment.gifslashapp.models;

import com.google.gson.annotations.SerializedName;

public class Giphy {

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("rating")
    private String rating;

    @SerializedName("title")
    private String title;

    @SerializedName("is_sticker")
    private int is_sticker;

    @SerializedName("images")
    private Images images;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public int getIs_sticker() {
        return is_sticker;
    }

    public Images getImages() {
        return images;
    }
}
