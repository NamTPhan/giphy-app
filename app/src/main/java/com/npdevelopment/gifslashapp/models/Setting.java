package com.npdevelopment.gifslashapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "settings_table")
public class Setting implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int limit;
    private String rating;
    private String language;

    public Setting(int id, int limit, String rating, String language) {
        this.id = id;
        this.limit = limit;
        this.rating = rating;
        this.language = language;
    }

    protected Setting(Parcel in) {
        id = in.readInt();
        limit = in.readInt();
        rating = in.readString();
        language = in.readString();
    }

    public static final Creator<Setting> CREATOR = new Creator<Setting>() {
        @Override
        public Setting createFromParcel(Parcel in) {
            return new Setting(in);
        }

        @Override
        public Setting[] newArray(int size) {
            return new Setting[size];
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
        return "Setting{" +
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
