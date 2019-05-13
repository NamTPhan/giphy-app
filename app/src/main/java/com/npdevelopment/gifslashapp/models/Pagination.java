package com.npdevelopment.gifslashapp.models;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total_count")
    private int total_count;

    @SerializedName("count")
    private int count;

    public int getTotal_count() {
        return total_count;
    }

    public int getCount() {
        return count;
    }
}
