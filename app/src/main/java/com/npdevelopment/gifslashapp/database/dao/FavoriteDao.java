package com.npdevelopment.gifslashapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.npdevelopment.gifslashapp.models.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Insert
    void insert(List<Favorite> favorites);

    @Delete
    void delete(Favorite favorite);

    @Delete
    void delete(List<Favorite> favorites);

    @Update
    void update(Favorite favorite);

    @Query("SELECT * from favorites_table")
    LiveData<List<Favorite>> getAllFavorites();
}
