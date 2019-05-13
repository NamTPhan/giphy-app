package com.npdevelopment.gifslashapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.npdevelopment.gifslashapp.models.Settings;

import java.util.List;

@Dao
public interface SettingsDao {

    @Insert
    void insert(Settings settings);

    @Update
    void update(Settings settings);

    @Query("SELECT * from settings_table")
    LiveData<List<Settings>> getSettings();
}
