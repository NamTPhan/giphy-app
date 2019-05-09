package com.npdevelopment.gifslashapp.models;

import com.google.gson.annotations.SerializedName;

public class ImageFixedHeight {

    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private String width;

    @SerializedName("height")
    private String height;

    @SerializedName("size")
    private String size;

    public String getUrl() {
        return url;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getSize() {
        return size;
    }
}
