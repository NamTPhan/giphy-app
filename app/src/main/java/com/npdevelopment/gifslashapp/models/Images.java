package com.npdevelopment.gifslashapp.models;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("fixed_height_downsampled")
    private ImageFixedHeight imageFixedHeight;

    public ImageFixedHeight getImageFixedHeight() {
        return imageFixedHeight;
    }
}
