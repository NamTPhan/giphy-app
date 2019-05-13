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
    private String dateSaved;
    private String imageUrl;

    public Favorite(String title, String description, String dateSaved, String imageUrl) {
        this.title = title;
        this.description = description;
        this.dateSaved = dateSaved;
        this.imageUrl = imageUrl;
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        dateSaved = in.readString();
        imageUrl = in.readString();
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

    public String getDateSaved() {
        return dateSaved;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public void setDateSaved(String dateSaved) {
        this.dateSaved = dateSaved;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date_saved='" + dateSaved + '\'' +
                ", image_url='" + imageUrl + '\'' +
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
        parcel.writeString(dateSaved);
        parcel.writeString(imageUrl);
    }
}
