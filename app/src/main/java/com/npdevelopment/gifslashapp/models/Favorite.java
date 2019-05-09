package com.npdevelopment.gifslashapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favorites_table")
public class Favorite implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String date_saved;
    private String image_url;

    public Favorite(int id, String title, String description, String date_saved, String image_url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date_saved = date_saved;
        this.image_url = image_url;
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        date_saved = in.readString();
        image_url = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate_saved() {
        return date_saved;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate_saved(String date_saved) {
        this.date_saved = date_saved;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date_saved='" + date_saved + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(date_saved);
        parcel.writeString(image_url);
    }
}
