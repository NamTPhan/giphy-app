package com.npdevelopment.gifslashapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.npdevelopment.gifslashapp.models.History;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    void insert(History history);

    @Insert
    void insert(List<History> historyList);

    @Delete
    void delete(History history);

    @Delete
    void delete(List<History> historyList);

    @Update
    void update(History history);

    @Query("SELECT * from history_table")
    LiveData<List<History>> getAllHistory();
}
