package com.npdevelopment.giphyslash.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.npdevelopment.giphyslash.models.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Update
    void update(Favorite favorite);

//    @Query("SELECT * from favorite_table")
//    LiveData<List<Favorite>> getAllFavorites();
}
