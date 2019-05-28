package com.npdevelopment.gifslashapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchData implements Parcelable {

    private String searchQuery;
    private int recordLimit;
    private String rating;
    private String language;

    public SearchData(String searchQuery, int recordLimit, String rating, String language) {
        this.searchQuery = searchQuery;
        this.recordLimit = recordLimit;
        this.rating = rating;
        this.language = language;
    }

    protected SearchData(Parcel in) {
        searchQuery = in.readString();
        recordLimit = in.readInt();
        rating = in.readString();
        language = in.readString();
    }

    public static final Creator<SearchData> CREATOR = new Creator<SearchData>() {
        @Override
        public SearchData createFromParcel(Parcel in) {
            return new SearchData(in);
        }

        @Override
        public SearchData[] newArray(int size) {
            return new SearchData[size];
        }
    };

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getRecordLimit() {
        return recordLimit;
    }

    public void setRecordLimit(int recordLimit) {
        this.recordLimit = recordLimit;
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
        return "SearchData{" +
                "searchQuery='" + searchQuery + '\'' +
                ", recordLimit=" + recordLimit +
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
        dest.writeString(searchQuery);
        dest.writeInt(recordLimit);
        dest.writeString(rating);
        dest.writeString(language);
    }
}
