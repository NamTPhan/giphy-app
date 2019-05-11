package com.npdevelopment.gifslashapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Images implements Parcelable {

    @SerializedName("fixed_height_downsampled")
    private ImageFixedHeight imageFixedHeight;

    protected Images(Parcel in) {
        imageFixedHeight = in.readParcelable(ImageFixedHeight.class.getClassLoader());
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    public ImageFixedHeight getImageFixedHeight() {
        return imageFixedHeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.imageFixedHeight, flags);
    }
}
