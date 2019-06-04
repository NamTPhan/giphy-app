package com.npdevelopment.gifslashapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Giphy implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("rating")
    private String rating;

    @SerializedName("title")
    private String title;

    @SerializedName("is_sticker")
    private int isSticker;

    @SerializedName("images")
    private Images images;

    protected Giphy(Parcel in) {
        id = in.readString();
        type = in.readString();
        rating = in.readString();
        title = in.readString();
        isSticker = in.readInt();
        images = in.readParcelable(Images.class.getClassLoader());
    }

    public static final Creator<Giphy> CREATOR = new Creator<Giphy>() {
        @Override
        public Giphy createFromParcel(Parcel in) {
            return new Giphy(in);
        }

        @Override
        public Giphy[] newArray(int size) {
            return new Giphy[size];
        }
    };

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

    public int getIsSticker() {
        return isSticker;
    }

    public Images getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "Giphy{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", rating='" + rating + '\'' +
                ", title='" + title + '\'' +
                ", isSticker=" + isSticker +
                ", images=" + images +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(type);
        parcel.writeString(rating);
        parcel.writeString(title);
        parcel.writeInt(isSticker);
        parcel.writeParcelable(images, flags);
    }
}
