package com.npdevelopment.gifslashapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.npdevelopment.gifslashapp.database.dao.FavoriteDao;
import com.npdevelopment.gifslashapp.database.dao.SettingsDao;
import com.npdevelopment.gifslashapp.models.Favorite;
import com.npdevelopment.gifslashapp.models.Settings;

@Database(entities = {Favorite.class, Settings.class}, version = 1, exportSchema = false)
public abstract class GiphyRoomDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "giphy_database";
    private static volatile GiphyRoomDatabase INSTANCE;

    public abstract FavoriteDao favoriteDao();
    public abstract SettingsDao settingsDao();

    public static GiphyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GiphyRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GiphyRoomDatabase.class, NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
