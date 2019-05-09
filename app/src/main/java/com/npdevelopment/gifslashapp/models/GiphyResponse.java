package com.npdevelopment.gifslashapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiphyResponse {

    @SerializedName("data")
    private List<Giphy> data;

    @SerializedName("pagination")
    private Pagination pagination;

    public List<Giphy> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }
}