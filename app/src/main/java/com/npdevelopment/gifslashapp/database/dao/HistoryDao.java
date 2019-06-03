package com.npdevelopment.gifslashapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
