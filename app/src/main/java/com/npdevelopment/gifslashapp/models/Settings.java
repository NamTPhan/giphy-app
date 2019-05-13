package com.npdevelopment.gifslashapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "settings_table")
public class Settings implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int limit;
    private String rating;
    private String language;

    public Settings(int limit, String rating, String language) {
        this.limit = limit;
        this.rating = rating;
        this.language = language;
    }

    protected Settings(Parcel in) {
        id = in.readInt();
        limit = in.readInt();
        rating = in.readString();
        language = in.readString();
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "id=" + id +
                ", limit=" + limit +
                ", rating='" + rating + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(limit);
        dest.writeString(rating);
        dest.writeString(language);
    }
}
