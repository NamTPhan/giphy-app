package com.npdevelopment.gifslashapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageFixedHeight implements Parcelable {

    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private String width;

    @SerializedName("height")
    private String height;

    @SerializedName("size")
    private String size;

    protected ImageFixedHeight(Parcel in) {
        url = in.readString();
        width = in.readString();
        height = in.readString();
        size = in.readString();
    }

    public static final Creator<ImageFixedHeight> CREATOR = new Creator<ImageFixedHeight>() {
        @Override
        public ImageFixedHeight createFromParcel(Parcel in) {
            return new ImageFixedHeight(in);
        }

        @Override
        public ImageFixedHeight[] newArray(int size) {
            return new ImageFixedHeight[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(width);
        dest.writeString(height);
        dest.writeString(size);
    }
}
