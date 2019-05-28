package com.npdevelopment.gifslashapp.models;

import com.google.gson.annotations.SerializedName;

public class GiphyResponseObject {

    @SerializedName("data")
    private Giphy data;

    public Giphy getData() {
        return data;
    }

}
